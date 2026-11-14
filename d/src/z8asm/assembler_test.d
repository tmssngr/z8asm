module z8asm.assembler_test;

import std.conv : to;
import std.exception : assertThrown;
import std.file : readText;
import std.path : buildNormalizedPath, dirName;
import std.string : replace;

import z8asm.assembler : Assembler;
import z8asm.output : Output, OutputStream, Writer;
import z8asm.parser : Parser;
import z8asm.syntax_exception : SyntaxException;
import z8asm.warning_out : WarningOut;

private class Warnings : WarningOut {
	string[] messages;
	override void print(string text) { messages ~= text; }
}

private class StringWriter : Writer {
	string text;
	override void write(string value) { text ~= value; }
}

private class Bytes : OutputStream {
	byte[] data;
	override void write(byte[] value) { data ~= value; }
}

private Output assemble(string source) {
	return Output.create(new Assembler().assemble(Parser.parse(source), new Warnings()));
}

private void assertBytes(ubyte[] expected, string source) {
	auto bytes = new Bytes();
	assemble(source).writeOutputStream(bytes);
	assert(bytes.data == cast(byte[])expected,
		"Expected " ~ to!string(expected) ~ ", got " ~ to!string(cast(ubyte[])bytes.data));
}

// Compare with the Java assembler's committed listings without rewriting them.
unittest {
	enum examples = buildNormalizedPath(dirName(__FILE_FULL_PATH__), "../../../src/main/examples");
	foreach (name; ["ub8830rom", "2k-1988", "4k-1988", "es2.3", "es4.0", "es4.5", "video", "forth", "asdis"]) {
		auto path = buildNormalizedPath(examples, name ~ ".asm");
		auto writer = new StringWriter();
		assemble(readText(path)).print(writer, "\n");
		assert(writer.text == readText(path ~ ".expected").replace("\r\n", "\n"), name);
	}
}

unittest {
	assertBytes([0x0c, 10, 0xd6, 0, 8, 0x0a, 0xfb, 0xaf, 0x1c, 10, 0xff, 0x1a, 0xfd, 0xaf],
		"main:\n ld r0, #10\n.1:\n call sub1\n djnz r0, .1\n ret\n"
		~ "sub1:\n ld r1, #10\n.1:\n nop\n djnz r1, .1\n ret\n");
	assertBytes([0x8b, 1, 0xff, 0x8b, 0xfe, 0x8b, 1, 0xff, 0x8b, 0xfe],
		"first:\n jr .loop\n nop\n.loop:\n jr .loop\n"
		~ "second:\n jr .loop\n nop\n.loop:\n jr .loop\n");
}

unittest {
	foreach (source; ["jp missing", "jp .1", ".1:\n nop", "main:\nmain:\n nop",
		"main:\n.1:\n.1:\n nop", "first:\n.1:\n nop\nsecond:\n jr .1",
		"main:\n djnz r0, far\n .repeat 128\n nop\n .end\nfar:\n ret"]) {
		assertThrown!SyntaxException(assemble(source), source);
	}
}

unittest {
	assertBytes([0x41, 0x42, 0x43, 0xe7, 0xe7, 0xe7, 0xe7, 0xe7],
		".data \"ABC\"\n.align 8, %e7");
	assertBytes([0x7b, 0xfe, 0x6d, 0x80, 0, 0x8b, 0xf9, 0x8d, 0x80, 0],
		"jr c, %0\njr z, %8000\njr %0\njr %8000");

	ubyte[] expected = new ubyte[130];
	expected[] = 0xff;
	expected[0 .. 4] = [0x6b, 0x7f, 0x7b, 0xfc];
	expected[$ - 1] = 0xaf;
	assertBytes(expected, "start:\n jr z, foo\n jr c, start\n .align %80, %ff\n nop\nfoo:\n ret");

	expected = new ubyte[131];
	expected[] = 0xff;
	expected[0 .. 5] = [0x6d, 0, 0x82, 0x7b, 0xfb];
	expected[$ - 1] = 0xaf;
	assertBytes(expected, "start:\n jr z, foo\n jr c, start\n .align %80, %ff\n nop\n nop\nfoo:\n ret");

	expected = new ubyte[135];
	expected[] = 0xff;
	expected[0 .. 6] = [0x6d, 0, 0x83, 0x7d, 0, 0x86];
	expected[131 .. $] = [0xd6, 0x80, 0, 0xaf];
	assertBytes(expected, "jr z, foo\njr c, bar\n.repeat %7d\n nop\n.end\nfoo:\n call %8000\nbar:\n ret");

	expected = new ubyte[133];
	expected[] = 0xff;
	expected[0 .. 3] = [0x8d, 0, 0x83];
	expected[131 .. $] = [0x8b, 0xfe];
	assertBytes(expected, "main:\n jr .far\n .repeat 128\n nop\n .end\n.far:\n jr .far");
}

unittest {
	assertBytes([0x0c, 0x80, 0x1c, 9, 0x2c, 0x80, 0x3c, 9, 0xaf],
		".org %8000\nmain:\n ld r0, #hi(data)\n ld r1, #lo(data)\n ld r2, #data\n ld r3, #data\n ret\ndata:\n");
	assertBytes([0xe6, 0x40, 0x80, 0xe6, 0x41, 13, 0xe6, 0x42, 0x80, 0xe6, 0x43, 13, 0xaf],
		".org %8000\nmain:\n ld %40, #hi(data)\n ld %41, #lo(data)\n ld %42, #data\n ld %43, #data\n ret\ndata:\n");
	assertBytes([6, 0xe1, 13, 0x16, 0xe0, 0x80, 6, 0x23, 13, 0x16, 0x22, 0x80, 0xaf],
		".org %8000\nmain:\n add r1, #lo(data)\n adc r0, #hi(data)\n add %23, #data\n adc %22, #data\n ret\ndata:\n");
	assertBytes([0x27, 0xe1, 13, 0x37, 0xe0, 0x80, 0x27, 0x13, 13, 0x37, 0x12, 0x80, 0xaf],
		".org %8000\nmain:\n sub @r1, #lo(data)\n sbc @r0, #hi(data)\n sub @%13, #data\n sbc @%12, #data\n ret\ndata:\n");
}

unittest {
	auto warnings = new Warnings();
	new Assembler().assemble(Parser.parse("zebra:\n.z:\n.a:\nalpha:\n"), warnings);
	assert(warnings.messages == ["1:1: Label zebra is unused", "2:1: Label zebra/.z is unused",
		"3:1: Label zebra/.a is unused", "4:1: Label alpha is unused"]);
}

unittest {
	assertBytes([0, 2, 0x6f, 0x6b, 0x34, 0xd6, 0x12, 0x34, 0xe9, 0xac, 0x3d, 0],
		".data L\"\" l\"ok\" \"\\x1234\"\ncall '\\x1234'\n.data \"é€😀\"");
	assertBytes([0x20, 0x62, 0x62, 0x62],
		".const BASE = %62\n.const ALIAS = BASE\ninc ALIAS\n.repeat 2\n.data \"b\"\n.end");
}
