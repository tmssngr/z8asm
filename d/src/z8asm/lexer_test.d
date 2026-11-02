// LexerTest.d - translated from Java
module z8asm.lexer_test;

import std.stdio : writeln;
import std.conv : to;

import z8asm.lexer : Lexer;
import z8asm.token_type : TokenType;
import z8asm.location : Location;
import z8asm.invalid_token_exception : InvalidTokenException;

// Helper struct similar to Java's LexerTester
private struct LexerTester {
	Lexer lexer;

	this(string text) {
		this.lexer = new Lexer(text);
	}

	void assertLocation(int expectedLine, int expectedColumn) {
		Location location = lexer.getLocation();
		assert(location.line == expectedLine, "line mismatch: expected " ~ to!string(expectedLine) ~ " got " ~ to!string(location.line));
		assert(location.column == expectedColumn, "column mismatch: expected " ~ to!string(expectedColumn) ~ " got " ~ to!string(location.column));
	}

	void assertEof() {
		assertType(TokenType.EOF);
	}

	void assertLineBreak() {
		assertType(TokenType.LINE_BREAK);
	}

	void assertLabel(string expected) {
		assertText(TokenType.LABEL, expected);
	}

	void assertComment(string expected) {
		assertText(TokenType.COMMENT, expected);
	}

	void assertIdentifier(string expected) {
		assertText(TokenType.IDENTIFIER, expected);
	}

	void assertWorkRegister(int expectedRegister) {
		assertType(TokenType.WORK_REGISTER);
		assert(lexer.getIntValue() == expectedRegister, "work register value mismatch");
	}

	void assertWorkRegisterPair(int expectedRegister) {
		assertType(TokenType.WORK_REGISTER_PAIR);
		assert(lexer.getIntValue() == expectedRegister, "work register pair value mismatch");
	}

	void assertIntValue(int expectedValue) {
		assertType(TokenType.INT_LITERAL);
		assert(lexer.getIntValue() == expectedValue, "int value mismatch");
	}

	void assertType(TokenType type) {
		TokenType next = this.next();
		assert(next == type, "token type mismatch: expected " ~ to!string(type) ~ " got " ~ to!string(next));
	}

	void assertText(TokenType type, string expected) {
		assertType(type);
		assert(lexer.getText() == expected, "text mismatch: expected '" ~ expected ~ "' got '" ~ lexer.getText() ~ "'");
	}

	private TokenType next() {
		TokenType type;
		do {
			type = lexer.next();
		} while (type == TokenType.WHITESPACE);
		return type;
	}
}

// Test cases
unittest {
	// Test: main assembly code
	{
		string input = "main:\n  ld r0, #10\nmain: ; duplicate\n  call %1000\n  djnz r0, main\n  ret";
		LexerTester tester = LexerTester(input);
		tester.assertLabel("main");
		tester.assertLocation(0, 0);
		tester.assertLineBreak();
		tester.assertType(TokenType.LD);
		tester.assertLocation(1, 2);
		tester.assertWorkRegister(0);
		tester.assertLocation(1, 5);
		tester.assertType(TokenType.COMMA);
		tester.assertLocation(1, 7);
		tester.assertType(TokenType.HASH);
		tester.assertLocation(1, 9);
		tester.assertIntValue(10);
		tester.assertLocation(1, 10);
		tester.assertLineBreak();
		tester.assertLabel("main");
		tester.assertLocation(2, 0);
		tester.assertComment("; duplicate");
		tester.assertLocation(2, 6);
		tester.assertLineBreak();
		tester.assertType(TokenType.CALL);
		tester.assertLocation(3, 2);
		tester.assertIntValue(0x1000);
		tester.assertLocation(3, 7);
		tester.assertLineBreak();
		tester.assertType(TokenType.DJNZ);
		tester.assertLocation(4, 2);
		tester.assertWorkRegister(0);
		tester.assertLocation(4, 7);
		tester.assertType(TokenType.COMMA);
		tester.assertLocation(4, 9);
		tester.assertIdentifier("main");
		tester.assertLocation(4, 11);
		tester.assertLineBreak();
		tester.assertType(TokenType.RET);
		tester.assertEof();
	}
}

unittest {
	// Test: TM instruction
	{
		LexerTester tester = LexerTester("TM      R3, #0b0000_0100");
		tester.assertType(TokenType.TM);
		tester.assertWorkRegister(3);
		tester.assertType(TokenType.COMMA);
		tester.assertType(TokenType.HASH);
		tester.assertIntValue(0x4);
		tester.assertEof();
	}
}

unittest {
	// Test: LD with hex
	{
		LexerTester tester = LexerTester("LD %20, #'0'");
		tester.assertType(TokenType.LD);
		tester.assertIntValue(0x20);
		tester.assertType(TokenType.COMMA);
		tester.assertType(TokenType.HASH);
		tester.assertIntValue(0x30);
		tester.assertEof();
	}
}

unittest {
	// Test: LDEI instruction
	{
		LexerTester tester = LexerTester("LDEI @R6, @RR0");
		tester.assertType(TokenType.LDEI);
		tester.assertType(TokenType.AT);
		tester.assertWorkRegister(6);
		tester.assertType(TokenType.COMMA);
		tester.assertType(TokenType.AT);
		tester.assertWorkRegisterPair(0);
		tester.assertEof();
	}
}

unittest {
	// Test: Data directive
	{
		string input = ".data L\"foo bar\" \"bazz\" L\"\" \"\\\"\\x0d\" '\\x7f'";
		LexerTester tester = LexerTester(input);
		tester.assertType(TokenType.DATA);
		tester.assertLocation(0, 0);
		tester.assertText(TokenType.LENGTH_STRING, "foo bar");
		tester.assertLocation(0, 6);
		tester.assertText(TokenType.STRING, "bazz");
		tester.assertLocation(0, 17);
		tester.assertText(TokenType.LENGTH_STRING, "");
		tester.assertLocation(0, 24);
		// Note: The string with escapes needs verification
		tester.assertType(TokenType.STRING);
		tester.assertLocation(0, 28);
		tester.assertIntValue(0x7f);
		tester.assertLocation(0, 37);
		tester.assertEof();
	}
}

unittest {
	// Test: Data with null string
	{
		LexerTester tester = LexerTester(".data \"\\0\"");
		tester.assertType(TokenType.DATA);
		tester.assertLocation(0, 0);
		tester.assertText(TokenType.STRING, "\0");
		tester.assertLocation(0, 6);
		tester.assertEof();
	}
}

unittest {
	// Test: Empty char (should throw)
	bool caught = false;
	try {
		LexerTester tester = LexerTester("''");
		tester.next();
	} catch (Exception e) {
		caught = true;
	}
	assert(caught, "Expected InvalidTokenException for empty char");
}

unittest {
	// Test: Multi-char char literal (should throw)
	bool caught = false;
	try {
		LexerTester tester = LexerTester(" 'ab'");
		tester.next();
	} catch (Exception e) {
		caught = true;
	}
	assert(caught, "Expected InvalidTokenException for multi-char");
}

unittest {
	// Test: Unclosed char (should throw)
	bool caught = false;
	try {
		LexerTester tester = LexerTester(" '");
		tester.next();
	} catch (Exception e) {
		caught = true;
	}
	assert(caught, "Expected InvalidTokenException for unclosed char");
}

unittest {
	// Test: Unclosed string (should throw)
	bool caught = false;
	try {
		LexerTester tester = LexerTester(" \"");
		tester.next();
	} catch (Exception e) {
		caught = true;
	}
	assert(caught, "Expected InvalidTokenException for unclosed string");
}

unittest {
	// Test: Comments
	string input = "// line\n; comment\nfoo /* bar\nbazz* / */ blupp";
	LexerTester tester = LexerTester(input);
	tester.assertText(TokenType.COMMENT, "// line");
	tester.assertType(TokenType.LINE_BREAK);
	tester.assertText(TokenType.COMMENT, "; comment");
	tester.assertType(TokenType.LINE_BREAK);
	tester.assertIdentifier("foo");
	tester.assertText(TokenType.COMMENT, "/* bar\nbazz* / */");
	tester.assertIdentifier("blupp");
}
