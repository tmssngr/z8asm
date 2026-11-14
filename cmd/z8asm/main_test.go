package main

import (
	"bytes"
	"os"
	"path/filepath"
	"strings"
	"testing"
)

func writeTestFile(t *testing.T, path, contents string) {
	t.Helper()
	if err := os.WriteFile(path, []byte(contents), 0o600); err != nil {
		t.Fatal(err)
	}
}

func runForTest(args ...string) (int, string, string) {
	var stdout, stderr bytes.Buffer
	code := run(args, &stdout, &stderr)
	return code, stdout.String(), stderr.String()
}

func TestRunWritesDefaultBinary(t *testing.T) {
	dir := t.TempDir()
	input := filepath.Join(dir, "rom.asm")
	writeTestFile(t, input, ".data %ff %31\n")
	code, stdout, stderr := runForTest(input)
	if code != 0 || stdout != "" || stderr != "" {
		t.Fatalf("run() = (%d, %q, %q), want success with no output", code, stdout, stderr)
	}
	actual, err := os.ReadFile(filepath.Join(dir, "rom.bin"))
	if err != nil {
		t.Fatal(err)
	}
	if !bytes.Equal(actual, []byte{0xff, 0x31}) {
		t.Errorf("binary = % x, want ff 31", actual)
	}
}

func TestRunWritesSelectedFormatsAndMultipleInputs(t *testing.T) {
	root := t.TempDir()
	inputs := []string{
		filepath.Join(root, "first.asm"),
		filepath.Join(root, "second.asm"),
	}
	writeTestFile(t, inputs[0], "start:\njr start\n")
	writeTestFile(t, inputs[1], "begin:\njr begin\n")
	outputDir := filepath.Join(root, "out")
	if err := os.Mkdir(outputDir, 0o700); err != nil {
		t.Fatal(err)
	}
	args := append([]string{"--ascii", "--labels", "--output=" + outputDir}, inputs...)
	code, stdout, stderr := runForTest(args...)
	if code != 0 || stdout != "" || stderr != "" {
		t.Fatalf("run() = (%d, %q, %q), want success with no output", code, stdout, stderr)
	}
	for _, expected := range []struct {
		name string
		text string
	}{
		{"first.ascii", "0000  8b fe\n"}, {"first.labels", "0000 start\n"},
		{"second.ascii", "0000  8b fe\n"}, {"second.labels", "0000 begin\n"},
	} {
		actual, err := os.ReadFile(filepath.Join(outputDir, expected.name))
		if err != nil {
			t.Fatal(err)
		}
		if string(actual) != expected.text {
			t.Errorf("%s = %q, want %q", expected.name, actual, expected.text)
		}
	}
	if _, err := os.Stat(filepath.Join(outputDir, "first.bin")); !os.IsNotExist(err) {
		t.Errorf("--labels/--ascii unexpectedly wrote binary output: %v", err)
	}
}

func TestRunLabelsOnly(t *testing.T) {
	dir := t.TempDir()
	input := filepath.Join(dir, "rom.asm")
	writeTestFile(t, input, "start:\njr start\n")
	code, stdout, stderr := runForTest("--labels", input)
	if code != 0 || stdout != "" || stderr != "" {
		t.Fatalf("run() = (%d, %q, %q), want success with no output", code, stdout, stderr)
	}
	labels, err := os.ReadFile(filepath.Join(dir, "rom.labels"))
	if err != nil {
		t.Fatal(err)
	}
	if string(labels) != "0000 start\n" {
		t.Errorf("labels = %q, want %q", labels, "0000 start\n")
	}
	if _, err := os.Stat(filepath.Join(dir, "rom.bin")); !os.IsNotExist(err) {
		t.Errorf("--labels unexpectedly wrote binary output: %v", err)
	}
}

func TestRunUsesExplicitOutputFile(t *testing.T) {
	root := t.TempDir()
	input := filepath.Join(root, "source.asm")
	output := filepath.Join(root, "named.bin")
	writeTestFile(t, input, "ret")
	code, _, stderr := runForTest("--output="+output, input)
	if code != 0 || stderr != "" {
		t.Fatalf("run() = (%d, %q), want success", code, stderr)
	}
	actual, err := os.ReadFile(output)
	if err != nil {
		t.Fatal(err)
	}
	if !bytes.Equal(actual, []byte{0xaf}) {
		t.Errorf("explicit output = % x, want af", actual)
	}
}

func TestRunReportsUsageErrors(t *testing.T) {
	for _, test := range []struct {
		args []string
		want string
	}{
		{nil, "asm-file missing"},
		{[]string{"--unknown", "source.asm"}, "unknown option"},
		{[]string{"--output="}, "--output needs a path"},
	} {
		code, _, stderr := runForTest(test.args...)
		if code != 2 || !strings.Contains(stderr, test.want) {
			t.Errorf("run(%v) = (%d, %q), want usage error containing %q", test.args, code, stderr, test.want)
		}
	}
	code, stdout, _ := runForTest("--help")
	if code != 0 || !strings.Contains(stdout, "Usage: z8asm") {
		t.Errorf("help = (%d, %q), want usage on stdout", code, stdout)
	}
}

func TestRunDoesNotWritePartialResultsAfterAssemblyError(t *testing.T) {
	root := t.TempDir()
	first := filepath.Join(root, "first.asm")
	second := filepath.Join(root, "second.asm")
	writeTestFile(t, first, "nop")
	writeTestFile(t, second, "jp missing")
	code, _, stderr := runForTest(first, second)
	if code != 1 || !strings.Contains(stderr, "No target 'missing' found") {
		t.Fatalf("run() = (%d, %q), want missing-label failure", code, stderr)
	}
	if _, err := os.Stat(filepath.Join(root, "first.bin")); !os.IsNotExist(err) {
		t.Errorf("run wrote first result before the second input failed: %v", err)
	}
}

func TestRunRejectsOutputCollisions(t *testing.T) {
	root := t.TempDir()
	leftDir, rightDir := filepath.Join(root, "left"), filepath.Join(root, "right")
	if err := os.Mkdir(leftDir, 0o700); err != nil {
		t.Fatal(err)
	}
	if err := os.Mkdir(rightDir, 0o700); err != nil {
		t.Fatal(err)
	}
	left, right := filepath.Join(leftDir, "same.asm"), filepath.Join(rightDir, "same.asm")
	writeTestFile(t, left, "nop")
	writeTestFile(t, right, "ret")
	outputDir := filepath.Join(root, "out")
	if err := os.Mkdir(outputDir, 0o700); err != nil {
		t.Fatal(err)
	}
	code, _, stderr := runForTest("--output="+outputDir, left, right)
	if code != 2 || !strings.Contains(stderr, "multiple outputs map") {
		t.Fatalf("run() = (%d, %q), want output collision", code, stderr)
	}
	if _, err := os.Stat(filepath.Join(outputDir, "same.bin")); !os.IsNotExist(err) {
		t.Errorf("run wrote a file despite a detected collision: %v", err)
	}
}

func TestRunRejectsOverwritingInput(t *testing.T) {
	root := t.TempDir()
	input := filepath.Join(root, "source.asm")
	writeTestFile(t, input, "nop")
	code, _, stderr := runForTest("--output="+input, input)
	if code != 2 || !strings.Contains(stderr, "would overwrite an input file") {
		t.Fatalf("run() = (%d, %q), want input/output collision", code, stderr)
	}
}

func TestRunWritesWarningsToStderr(t *testing.T) {
	root := t.TempDir()
	input := filepath.Join(root, "source.asm")
	writeTestFile(t, input, "jp target\ntarget:\nret")
	code, stdout, stderr := runForTest(input)
	if code != 0 || stdout != "" || !strings.Contains(stderr, "jp could be jr") {
		t.Fatalf("run() = (%d, %q, %q), want advisory on stderr", code, stdout, stderr)
	}
}

func TestParseOptionsHonorsTerminator(t *testing.T) {
	parsed, err := parseOptions([]string{"--", "--ascii", "--source.asm"})
	if err != nil {
		t.Fatal(err)
	}
	if parsed.ascii || !reflectDeepEqual(parsed.files, []string{"--ascii", "--source.asm"}) {
		t.Errorf("parsed options = %#v, want both arguments as file names", parsed)
	}
}

func reflectDeepEqual(left, right []string) bool {
	if len(left) != len(right) {
		return false
	}
	for i := range left {
		if left[i] != right[i] {
			return false
		}
	}
	return true
}
