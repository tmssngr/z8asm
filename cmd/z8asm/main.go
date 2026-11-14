package main

import (
	"bytes"
	"errors"
	"fmt"
	"io"
	"os"
	"path/filepath"
	"strings"

	z8asm "github.com/tmssngr/z8asm"
)

type options struct {
	files  []string
	ascii  bool
	bin    bool
	labels bool
	output string
	help   bool
}

type artifact struct {
	path string
	data []byte
}

func main() { 
	result := run(os.Args[1:], os.Stdout, os.Stderr)
	os.Exit(result) 
}

func run(args []string, stdout, stderr io.Writer) int {
	parsed, err := parseOptions(args)
	if err != nil {
		fmt.Fprintln(stderr, err)
		printUsage(stderr)
		return 2
	}
	if parsed.help {
		printUsage(stdout)
		return 0
	}
	if len(parsed.files) == 0 {
		fmt.Fprintln(stderr, "asm-file missing")
		printUsage(stderr)
		return 2
	}
	if !parsed.ascii && !parsed.bin && !parsed.labels {
		parsed.bin = true
	}

	targetCount := 0
	for _, selected := range []bool{parsed.ascii, parsed.bin, parsed.labels} {
		if selected {
			targetCount++
		}
	}

	var outputIsDir bool
	if parsed.output != "" {
		info, statErr := os.Stat(parsed.output)
		outputIsDir = statErr == nil && info.IsDir()
		if len(parsed.files) > 1 || targetCount > 1 {
			if !outputIsDir {
				fmt.Fprintln(stderr, "for multiple inputs or output formats, --output needs to be an existing directory")
				return 2
			}
		}
	}

	paths := make(map[string]struct{})
	inputs := make(map[string]struct{}, len(parsed.files))
	for _, input := range parsed.files {
		absolute, absErr := filepath.Abs(input)
		if absErr != nil {
			fmt.Fprintf(stderr, "%s: %v\n", input, absErr)
			return 1
		}
		inputs[filepath.Clean(absolute)] = struct{}{}
	}

	var artifacts []artifact
	for _, filename := range parsed.files {
		source, readErr := os.ReadFile(filename)
		if readErr != nil {
			fmt.Fprintf(stderr, "%s: %v\n", filename, readErr)
			return 1
		}
		commands, parseErr := z8asm.Parse(string(source))
		if parseErr != nil {
			fmt.Fprintf(stderr, "%s: %v\n", filename, parseErr)
			return 1
		}
		assembly, assembleErr := z8asm.Assemble(commands, func(warning string) {
			fmt.Fprintf(stdout, "%s: %s\n", filename, warning)
		})
		if assembleErr != nil {
			fmt.Fprintf(stderr, "%s: %v\n", filename, assembleErr)
			return 1
		}
		output, outputErr := z8asm.CreateOutput(assembly.Commands)
		if outputErr != nil {
			fmt.Fprintf(stderr, "%s: %v\n", filename, outputErr)
			return 1
		}

		for _, target := range []struct {
			selected bool
			suffix   string
			bytes    []byte
		}{{parsed.ascii, ".ascii", []byte(output.ASCII())}, {parsed.bin, ".bin", output.Bytes}} {
			if !target.selected {
				continue
			}
			path := outputPath(filename, target.suffix, parsed.output, outputIsDir)
			if err := checkDestination(path, paths, inputs); err != nil {
				fmt.Fprintln(stderr, err)
				return 2
			}
			artifacts = append(artifacts, artifact{path: path, data: append([]byte(nil), target.bytes...)})
		}
		if parsed.labels {
			var listing bytes.Buffer
			if err := assembly.WriteLabels(&listing); err != nil {
				fmt.Fprintf(stderr, "%s: %v\n", filename, err)
				return 1
			}
			path := outputPath(filename, ".labels", parsed.output, outputIsDir)
			if err := checkDestination(path, paths, inputs); err != nil {
				fmt.Fprintln(stderr, err)
				return 2
			}
			artifacts = append(artifacts, artifact{path: path, data: listing.Bytes()})
		}
	}

	for _, item := range artifacts {
		if err := os.WriteFile(item.path, item.data, 0o666); err != nil {
			fmt.Fprintf(stderr, "%s: %v\n", item.path, err)
			return 1
		}
	}
	return 0
}

func parseOptions(args []string) (options, error) {
	var result options
	parseFlags := true
	for _, arg := range args {
		if parseFlags && arg == "--" {
			parseFlags = false
			continue
		}
		if !parseFlags {
			result.files = append(result.files, arg)
			continue
		}
		switch {
		case arg == "--ascii":
			result.ascii = true
		case arg == "--bin":
			result.bin = true
		case arg == "--labels":
			result.labels = true
		case arg == "--help" || arg == "-h":
			result.help = true
		case strings.HasPrefix(arg, "--output="):
			result.output = strings.TrimPrefix(arg, "--output=")
			if result.output == "" {
				return options{}, errors.New("--output needs a path")
			}
		case strings.HasPrefix(arg, "-"):
			return options{}, fmt.Errorf("unknown option %q", arg)
		default:
			result.files = append(result.files, arg)
		}
	}
	return result, nil
}

func outputPath(input, suffix, output string, outputIsDir bool) string {
	name := filepath.Base(input)
	if extension := filepath.Ext(name); extension != "" && len(name)-len(extension) > 0 {
		name = name[:len(name)-len(extension)]
	}
	filename := name + suffix
	if output == "" {
		return filepath.Join(filepath.Dir(input), filename)
	}
	if outputIsDir {
		return filepath.Join(output, filename)
	}
	return output
}

func checkDestination(path string, reserved, inputs map[string]struct{}) error {
	absolute, err := filepath.Abs(path)
	if err != nil {
		return fmt.Errorf("%s: %w", path, err)
	}
	clean := filepath.Clean(absolute)
	if _, exists := reserved[clean]; exists {
		return fmt.Errorf("multiple outputs map to %q", path)
	}
	if _, exists := inputs[clean]; exists {
		return fmt.Errorf("output %q would overwrite an input file", path)
	}
	reserved[clean] = struct{}{}
	return nil
}

func printUsage(writer io.Writer) {
	fmt.Fprintln(writer, "Usage: z8asm [--ascii] [--bin] [--labels] [--output=<path>] [--] asm-file...")
	fmt.Fprintln(writer, "By default, writes a binary file next to each input.")
}
