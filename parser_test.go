package z8asm

import (
	"reflect"
	"strings"
	"testing"
)

func mustParse(t *testing.T, source string) []Command {
	t.Helper()
	commands, err := Parse(source)
	if err != nil {
		t.Fatalf("Parse() error: %v", err)
	}
	return commands
}

func assertParsedCommand(t *testing.T, expected Command, source string) {
	t.Helper()
	assertParsedCommands(t, []Command{expected}, source)
}

func assertParsedCommands(t *testing.T, expected []Command, source string) []Command {
	t.Helper()
	actual := mustParse(t, source)
	if !reflect.DeepEqual(expected, actual) {
		t.Errorf("Parse(%q) = %#v, want %#v", source, actual, expected)
	}
	return actual
}

func TestParserLegacyUnary(t *testing.T) {
	for _, test := range []struct {
		mnemonic string
		opCode   int
	}{
		{"dec", 0x00}, {"rlc", 0x10}, {"da", 0x40}, {"pop", 0x50},
		{"com", 0x60}, {"push", 0x70}, {"decw", 0x80}, {"rl", 0x90},
		{"incw", 0xa0}, {"clr", 0xb0}, {"rrc", 0xc0}, {"sra", 0xd0},
		{"rr", 0xe0}, {"swap", 0xf0},
	} {
		t.Run(test.mnemonic, func(t *testing.T) {
			assertParsedCommand(t, NewContent(test.opCode, 0x60), test.mnemonic+" %60")
			assertParsedCommand(t, NewContent(test.opCode, 0xe2), test.mnemonic+" r2")
			assertParsedCommand(t, NewContent(test.opCode+1, 0x60), test.mnemonic+" @%60")
			assertParsedCommand(t, NewContent(test.opCode+1, 0xe2), test.mnemonic+" @r2")
			assertParsedCommand(t, NewContent(test.opCode, 0xfd), ".const RP = %FD\n"+test.mnemonic+" RP")
		})
	}
	assertParsedCommand(t, NewContent(0x20, 0x60), "inc %60")
	assertParsedCommand(t, NewContent(0x2e), "inc r2")
	assertParsedCommand(t, NewContent(0x21, 0xe1), "inc @r1")
	assertParsedCommand(t, NewContent(0x20, 0x62), ".const FOO = %62\ninc FOO")
	assertParsedCommand(t, NewContent(0x31, 0x40), "srp #%40")
}

func TestParserLegacyBinary(t *testing.T) {
	for _, test := range []struct {
		mnemonic string
		high     int
	}{
		{"add", 0x00}, {"adc", 0x10}, {"sub", 0x20}, {"sbc", 0x30},
		{"or", 0x40}, {"and", 0x50}, {"tcm", 0x60}, {"tm", 0x70},
		{"cp", 0xa0}, {"xor", 0xb0},
	} {
		t.Run(test.mnemonic, func(t *testing.T) {
			base := test.high
			assertParsedCommand(t, NewContent(base+2, 0x23), test.mnemonic+" r2, r3")
			assertParsedCommand(t, NewContent(base+3, 0x4f), test.mnemonic+" r4, @r15")
			assertParsedCommand(t, NewContent(base+4, 0x62, 0x60), test.mnemonic+" %60, %62")
			assertParsedCommand(t, NewContent(base+4, 0x66, 0x64), ".const FOO = %64\n.const BAR = %66\n"+test.mnemonic+" FOO, BAR\n")
			assertParsedCommand(t, NewContent(base+5, 0x12, 0x43), test.mnemonic+" @%43, %12")
			assertParsedCommand(t, NewContent(base+6, 0x61, 0x0d), test.mnemonic+" %61, #%0d")
			assertParsedCommand(t, NewContent(base+6, 0xea, 0x40), test.mnemonic+" r10, #%40")
			assertParsedCommand(t, NewContent(base+6, 0x64, 0x0d), ".const FOO = %64\n"+test.mnemonic+" FOO, #%0d\n")
			assertParsedCommand(t, NewContent(base+6, 0x65, 0x0d), ".const NEWLINE = %0d\n"+test.mnemonic+" %65, #NEWLINE")
			assertParsedCommand(t, NewContent(base+7, 0xeb, 0x0f), test.mnemonic+" @R11, #%0F")
			assertParsedCommand(t, NewContent(base+7, 0x65, 0x0d), ".const NEWLINE = %0d\n"+test.mnemonic+" @%65, #NEWLINE")

			source := test.mnemonic + " %50, #foo"
			assertParsedCommand(t, LazyContent3{OpCode: base + 6, Second: 0x50, Text: "foo", Location: Location{Column: strings.Index(source, "foo")}}, source)
			source = test.mnemonic + " @%51, #foo"
			assertParsedCommand(t, LazyContent3{OpCode: base + 7, Second: 0x51, Text: "foo", Location: Location{Column: strings.Index(source, "foo")}}, source)
		})
	}
}

func TestParserLegacyLD(t *testing.T) {
	assertParsedCommand(t, NewContent(0x68, 0x5a), "LD R6, %5a")
	assertParsedCommand(t, NewContent(0x18, 0xe2), "LD R1, R2")
	assertParsedCommand(t, NewContent(0x79, 0x32), "LD %32, R7")
	assertParsedCommand(t, NewContent(0xfc, 0x16), "LD R15, #%16")
	assertParsedCommand(t, NewContent(0xe3, 0x26), "LD R2, @R6")
	assertParsedCommand(t, NewContent(0xf3, 0x74), "LD @R7, R4")
	assertParsedCommand(t, NewContent(0xe4, 0x15, 0x5a), "LD %5A, %15")
	assertParsedCommand(t, NewContent(0xe5, 0x45, 0x34), "LD %34, @%45")
	assertParsedCommand(t, NewContent(0xf5, 0x15, 0x5a), "LD @%5A, %15")
	assertParsedCommand(t, NewContent(0xe6, 0x5a, 0x3d), "LD %5A, #%3D")
	assertParsedCommand(t, NewContent(0xe7, 0x5a, 0x3d), "LD @%5A, #%3D")
	assertParsedCommand(t, NewContent(0xc7, 0x26, 0x9e), "LD R2, %9E(R6)")
	assertParsedCommand(t, NewContent(0xd7, 0xa0, 0xf0), "LD %F0(R0), R10")
	assertParsedCommand(t, LazyContent2{OpCode: 0x2c, Text: "foo", Location: Location{Column: 8}}, "LD R2, #foo")
	assertParsedCommand(t, LazyContent3{OpCode: 0xe6, Second: 0x20, Text: "foo", Location: Location{Column: 9}}, "LD %20, #foo")
}

func TestParserLegacyLDX(t *testing.T) {
	assertParsedCommand(t, NewContent(0x82, 0x60), "LDE R6, @RR0")
	assertParsedCommand(t, NewContent(0x92, 0x60), "LDE @RR0, R6")
	assertParsedCommand(t, NewContent(0xc2, 0x60), "LDC R6, @RR0")
	assertParsedCommand(t, NewContent(0xd2, 0x60), "LDC @RR0, R6")
	assertParsedCommand(t, NewContent(0x83, 0x60), "LDEI @R6, @RR0")
	assertParsedCommand(t, NewContent(0x93, 0x60), "LDEI @RR0, @R6")
	assertParsedCommand(t, NewContent(0xc3, 0x9e), "LDCI @R9, @RR14")
	assertParsedCommand(t, NewContent(0xd3, 0x60), "LDCI @RR0, @R6")
}

func TestParserLegacyJumpCallAndDJNZ(t *testing.T) {
	assertParsedCommand(t, NewContent(0x30, 0x6e), "jp @%6e")
	assertParsedCommand(t, NewContent(0x30, 0xee), "jp @rr14")
	assertParsedCommand(t, NewContent(0x8d, 0x08, 0x12), "jp %0812")
	assertParsedCommand(t, LazyContent3{OpCode: 0x8d, Text: "M_0812", Location: Location{Column: 3}}, "jp M_0812")
	assertParsedCommand(t, NewContent(0xed, 0x08, 0x12), "jp nz, %0812")
	assertParsedCommand(t, LazyContent3{OpCode: 0x7d, Text: "M_0812", Location: Location{Column: 6}}, "jp c, M_0812")
	assertParsedCommand(t, AutoJumpAddress{JROpCode: 0x8b, Address: 0x0812}, "jr %0812")
	assertParsedCommand(t, AutoJumpAddress{JROpCode: 0x7b, Address: 0x0812}, "jr c, %0812")
	assertParsedCommand(t, AutoJumpString{JROpCode: 0x8b, Target: "M_0812", Location: Location{Column: 3}}, "jr M_0812")
	assertParsedCommand(t, AutoJumpString{JROpCode: 0x7b, Target: "M_0812", Location: Location{Column: 6}}, "jr c, M_0812")
	assertParsedCommand(t, LazyContent2{OpCode: 0x1a, Text: "FOO", Location: Location{Column: 9}}, "djnz r1, FOO")
	assertParsedCommand(t, NewContent(0xd4, 0x6e), "call @%6e")
	assertParsedCommand(t, NewContent(0xd4, 0xee), "call @rr14")
	assertParsedCommand(t, NewContent(0xd6, 0x08, 0x12), "call %0812")
	assertParsedCommand(t, LazyContent3{OpCode: 0xd6, Text: "M_0812", Location: Location{Column: 5}}, "call M_0812")
}

func TestParserLegacySingleByteInstructions(t *testing.T) {
	for _, test := range []struct {
		mnemonic string
		opCode   int
	}{
		{"di", 0x8f}, {"ei", 0x9f}, {"ret", 0xaf}, {"iret", 0xbf},
		{"rcf", 0xcf}, {"scf", 0xdf}, {"ccf", 0xef}, {"nop", 0xff},
	} {
		assertParsedCommand(t, NewContent(test.opCode), test.mnemonic)
	}
}

func TestParserLegacyData(t *testing.T) {
	assertParsedCommands(t, []Command{
		NewContent(0x46), NewContent(0x16), NewContent(0x40), NewContent(0x50),
		NewContent(0x60), NewContent(0x20, 0),
		LazyContent2{OpCode: 0, Text: "FOO", Location: Location{Line: 1, Column: 16}},
	}, ".data %46 %16 %40 %50\n.data %60 \" \\0\" FOO")
	assertParsedCommands(t, []Command{NewContent(0), NewContent(0x41)}, `.data L"" "A"`)
}

func TestParserLegacyRepeat(t *testing.T) {
	assertParsedCommands(t, []Command{NewContent(0xff), NewContent(0xff), NewContent(0xff)}, ".repeat 3\nnop\n.end")
	assertParsedCommands(t, []Command{
		NewContent(0x22), NewContent(0x22), NewContent(0x22),
		NewContent(0x22), NewContent(0x22), NewContent(0x22),
	}, ".repeat 3\n.repeat 2\n.data %22\n.end\n.end")
	if _, err := Parse(".repeat 2\nlabel:\nnop\n.end"); err == nil {
		t.Fatal("label inside .repeat block was accepted")
	}
}

func TestParserLegacyOriginAndLabels(t *testing.T) {
	commands := assertParsedCommands(t, []Command{
		Org{Address: 0x0800, Location: Location{Line: 0, Column: 8}},
		NewContent(0x8d, 0xe0, 0), NewContent(0x8d, 0xe0, 3),
		LabelCommand{Name: "M_0812", Location: Location{Line: 4, Column: 0}},
		LazyContent3{OpCode: 0x8d, Text: "M_0812", Location: Location{Line: 4, Column: 11}},
	}, "   .org %0800\n   jp %e000\n   jp %e003\n   ; boot\nM_0812: jp M_0812\n")
	assembled, err := Assemble(commands, nil)
	if err != nil {
		t.Fatalf("Assemble() error: %v", err)
	}
	want := []Command{NewContent(0x8d, 0xe0, 0), NewContent(0x8d, 0xe0, 3), NewContent(0x8d, 0x08, 6)}
	if !reflect.DeepEqual(want, assembled.Commands) {
		t.Errorf("Assemble() = %#v, want %#v", assembled.Commands, want)
	}
}
