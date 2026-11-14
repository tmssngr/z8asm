package z8asm

import (
	"bytes"
	"errors"
	"os"
	"path/filepath"
	"reflect"
	"strings"
	"testing"
)

func TestUB8830(t *testing.T) {
	assembleFileBinaryAscii(t, "ub8830rom.asm")
}

func Test2k(t *testing.T) {
	assembleFileBinaryAscii(t, "2k-1988.asm")
}

func Test4k(t *testing.T) {
	assembleFileBinaryAscii(t, "4k-1988.asm")
}

func TestES23(t *testing.T) {
	assembleFileBinaryAscii(t, "es2.3.asm")
}

func TestES40(t *testing.T) {
	assembleFileBinaryAscii(t, "es4.0.asm")
}

func TestES45(t *testing.T) {
	assembleFileBinaryAscii(t, "es4.5.asm")
}

func TestVideo(t *testing.T) {
	assembleFileBinaryAscii(t, "video.asm")
}

func TestForth(t *testing.T) {
	assembleFileBinaryAscii(t, "forth.asm")
}

func TestAsDis(t *testing.T) {
	assembleFileBinaryAscii(t, "asdis.asm")
}

func assembleFileBinaryAscii(t *testing.T, asmFilename string) {
	t.Helper()
	base := filepath.Join("examples")
	source, err := os.ReadFile(filepath.Join(base, asmFilename))
	if err != nil {
		t.Fatal(err)
	}
	commands, err := Parse(string(source))
	if err != nil {
		t.Fatalf("Parse() error: %v", err)
	}
	assembly, err := Assemble(commands, nil)
	if err != nil {
		t.Fatalf("Assemble() error: %v", err)
	}
	if assembly.Org < 0 || assembly.Org > 0xffff {
		t.Fatalf("origin %#x is outside the 16-bit address space", assembly.Org)
	}
	output, err := CreateOutput(assembly.Commands)
	if err != nil {
		t.Fatalf("CreateOutput() error: %v", err)
	}

	name := strings.TrimSuffix(filepath.Base(asmFilename), filepath.Ext(asmFilename))
	origin := toHex(assembly.Org, 4)
	binaryPath := filepath.Join(base, name+"_"+origin+".bin")
	wantBinary, err := os.ReadFile(binaryPath)
	if os.IsNotExist(err) && strings.ToUpper(origin) != origin {
		binaryPath = filepath.Join(base, name+"_"+strings.ToUpper(origin)+".bin")
		wantBinary, err = os.ReadFile(binaryPath)
	}
	if err != nil {
		t.Fatalf("reading expected binary %s: %v", binaryPath, err)
	}
	if !bytes.Equal(output.Bytes, wantBinary) {
		t.Errorf("assembled binary differs from %s", binaryPath)
	}

	asciiPath := filepath.Join(base, name+"_"+origin+".bin.ascii")
	wantASCII, err := os.ReadFile(asciiPath)
	if err != nil {
		t.Fatalf("reading expected ASCII listing %s: %v", asciiPath, err)
	}
	wantASCII = bytes.ReplaceAll(wantASCII, []byte("\r\n"), []byte("\n"))
	if !bytes.Equal([]byte(output.ASCII()), wantASCII) {
		t.Errorf("ASCII listing differs from %s", asciiPath)
	}
}

func TestMissingLabel(t *testing.T) {
	for _, source := range []string{"jp missing", "jp .1"} {
		commands := mustParse(t, source)
		if _, err := Assemble(commands, nil); err == nil {
			t.Errorf("Assemble(%q) succeeded, want missing-label error", source)
		} else {
			var syntax *SyntaxError
			if !errors.As(err, &syntax) {
				t.Errorf("Assemble(%q) error = %T, want SyntaxError: %v", source, err, err)
			}
		}
	}
}

func TestDuplicateLabels(t *testing.T) {
	source := "main:\nld r0, #10\nmain:\ncall %1000\ndjnz r0, main\nret"
	commands := mustParse(t, source)
	if _, err := Assemble(commands, nil); err == nil {
		t.Fatal("duplicate global label was accepted")
	}
}

func TestLocalLabels(t *testing.T) {
	expected := "0000  0c 0a d6 00 08 0a fb af  1c 0a ff 1a fd af\n"
	assertions := []string{
		"main:\nld r0, #10\nmain1:\ncall sub1\ndjnz r0, main1\nret\n\n" +
			"sub1:\nld r1, #10\nsub2:\nnop\ndjnz r1, sub2\nret\n",
		"main:\nld r0, #10\n.1:\ncall sub1\ndjnz r0, .1\nret\n\n" +
			"sub1:\nld r1, #10\n.1:\nnop\ndjnz r1, .1\nret\n",
	}
	for _, source := range assertions {
		if actual := assembleASCII(t, source); actual != expected {
			t.Errorf("assembly listing = %q, want %q", actual, expected)
		}
	}
	for _, source := range []string{
		".1:\nld r0, #10\ncall %1000\n.2:\ndjnz r0, .1\nret",
		"main:\nld r0, #10\n.1:\ncall %1000\n.1:\ndjnz r0, .1\nret",
	} {
		commands := mustParse(t, source)
		if _, err := Assemble(commands, nil); err == nil {
			t.Errorf("invalid local-label program was accepted:\n%s", source)
		}
	}
}

func TestAlign(t *testing.T) {
	if actual, want := assembleASCII(t, ".data \"ABC\"\n.align 8, %e7\n"), "0000  41 42 43 e7 e7 e7 e7 e7\n"; actual != want {
		t.Errorf("listing = %q, want %q", actual, want)
	}
}

func TestAutoJump(t *testing.T) {
	_, output := assembleSource(t, "jr c, %0\njr z, %8000\njr %0\njr %8000")
	want := []byte{0x7b, 0xfe, 0x6d, 0x80, 0x00, 0x8b, 0xf9, 0x8d, 0x80, 0x00}
	if !bytes.Equal(output.Bytes, want) {
		t.Errorf("numeric jump bytes = % x, want % x", output.Bytes, want)
	}

	_, output = assembleSource(t, "start:\njr z, foo\njr c, start\n.align %80, %ff\nnop\nfoo:\nret")
	want = append([]byte{0x6b, 0x7f, 0x7b, 0xfc}, bytes.Repeat([]byte{0xff}, 125)...)
	want = append(want, 0xaf)
	if !bytes.Equal(output.Bytes, want) {
		t.Errorf("short boundary jumps = % x, want % x", output.Bytes, want)
	}

	_, output = assembleSource(t, "start:\njr z, foo\njr c, start\n.align %80, %ff\nnop\nnop\nfoo:\nret")
	want = append([]byte{0x6d, 0x00, 0x82, 0x7b, 0xfb}, bytes.Repeat([]byte{0xff}, 124)...)
	want = append(want, 0xff, 0xaf)
	if !bytes.Equal(output.Bytes, want) {
		t.Errorf("aligned short jumps = % x, want % x", output.Bytes, want)
	}

	_, output = assembleSource(t, "jr z, foo\njr c, bar\n.repeat %7d\nnop\n.end\nfoo:\ncall %8000\nbar:\nret")
	want = []byte{0x6d, 0x00, 0x83, 0x7d, 0x00, 0x86}
	want = append(want, bytes.Repeat([]byte{0xff}, 125)...)
	want = append(want, 0xd6, 0x80, 0x00, 0xaf)
	if !bytes.Equal(output.Bytes, want) {
		t.Errorf("cascading jump bytes = % x, want % x", output.Bytes, want)
	}
}

func TestHighLowImmediateLabels(t *testing.T) {
	cases := []struct {
		source string
		want   []byte
	}{
		{".org %8000\nmain:\nld r0, #hi(data)\nld r1, #lo(data)\nld r2, #data\nld r3, #data\nret\ndata:\n", []byte{0x0c, 0x80, 0x1c, 0x09, 0x2c, 0x80, 0x3c, 0x09, 0xaf}},
		{".org %8000\nmain:\nld %40, #hi(data)\nld %41, #lo(data)\nld %42, #data\nld %43, #data\nret\ndata:\n", []byte{0xe6, 0x40, 0x80, 0xe6, 0x41, 0x0d, 0xe6, 0x42, 0x80, 0xe6, 0x43, 0x0d, 0xaf}},
		{".org %8000\nmain:\nadd r1, #lo(data)\nadc r0, #hi(data)\nadd %23, #data\nadc %22, #data\nret\ndata:\n", []byte{0x06, 0xe1, 0x0d, 0x16, 0xe0, 0x80, 0x06, 0x23, 0x0d, 0x16, 0x22, 0x80, 0xaf}},
		{".org %8000\nmain:\nsub @r1, #lo(data)\nsbc @r0, #hi(data)\nsub @%13, #data\nsbc @%12, #data\nret\ndata:\n", []byte{0x27, 0xe1, 0x0d, 0x37, 0xe0, 0x80, 0x27, 0x13, 0x0d, 0x37, 0x12, 0x80, 0xaf}},
	}
	for _, test := range cases {
		_, output := assembleSource(t, test.source)
		if !bytes.Equal(output.Bytes, test.want) {
			t.Errorf("bytes = % x, want % x", output.Bytes, test.want)
		}
	}
}

func TestAssemblerLabelListingAndOrigin(t *testing.T) {
	assembly, output := assembleSource(t, ".org %0800\nstart:\nnop\nnext:\nret\n")
	if assembly.Org != 0x0800 || !reflect.DeepEqual(output.Bytes, []byte{0xff, 0xaf}) {
		t.Fatalf("origin/output = %#x/% x, want 0x0800/ff af", assembly.Org, output.Bytes)
	}
	var labels strings.Builder
	if err := assembly.WriteLabels(&labels); err != nil {
		t.Fatal(err)
	}
	if got, want := labels.String(), "0800 start\n0801 next\n"; got != want {
		t.Errorf("labels = %q, want %q", got, want)
	}
}

func TestAssemblerRejectsAddressOverflow(t *testing.T) {
	for _, source := range []string{
		".org %10000\nnop",
		".org %ffff\n.data %00 %00",
	} {
		commands := mustParse(t, source)
		if _, err := Assemble(commands, nil); err == nil {
			t.Errorf("out-of-range program was accepted:\n%s", source)
		}
	}
}

func assembleSource(t *testing.T, source string) (*Assembly, Output) {
	t.Helper()
	commands, err := Parse(source)
	if err != nil {
		t.Fatalf("Parse() error: %v", err)
	}
	assembly, err := Assemble(commands, nil)
	if err != nil {
		t.Fatalf("Assemble() error: %v", err)
	}
	output, err := CreateOutput(assembly.Commands)
	if err != nil {
		t.Fatalf("CreateOutput() error: %v", err)
	}
	return assembly, output
}

func assembleASCII(t *testing.T, source string) string {
	t.Helper()
	_, output := assembleSource(t, source)
	return output.ASCII()
}
