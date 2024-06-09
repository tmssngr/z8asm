package com.syntevo.z8asm;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Thomas Singer
 */
public class LexerTest {

	@Test
	public void test() {
		new LexerTester("""
				                main:
				                  ld r0, #10
				                main: ; duplicate
				                  call %1000
				                  djnz r0, main
				                  ret""") {
			@Override
			protected void test() {
				assertLabel("main");
				assertLocation(0, 0);

				assertLineBreak();

				assertType(TokenType.LD);
				assertLocation(1, 2);
				assertWorkRegister(0);
				assertLocation(1, 5);
				assertType(TokenType.COMMA);
				assertLocation(1, 7);
				assertType(TokenType.HASH);
				assertLocation(1, 9);
				assertIntValue(10);
				assertLocation(1, 10);
				assertLineBreak();

				assertLabel("main");
				assertLocation(2, 0);
				assertComment("; duplicate");
				assertLocation(2, 6);
				assertLineBreak();

				assertType(TokenType.CALL);
				assertLocation(3, 2);
				assertIntValue(0x1000);
				assertLocation(3, 7);
				assertLineBreak();

				assertType(TokenType.DJNZ);
				assertLocation(4, 2);
				assertWorkRegister(0);
				assertLocation(4, 7);
				assertType(TokenType.COMMA);
				assertLocation(4, 9);
				assertIdentifier("main");
				assertLocation(4, 11);
				assertLineBreak();

				assertType(TokenType.RET);
				assertEof();
			}
		}.test();

		new LexerTester("TM      R3, #0b0000_0100") {
			@Override
			protected void test() {
				assertType(TokenType.TM);
				assertWorkRegister(3);
				assertType(TokenType.COMMA);
				assertType(TokenType.HASH);
				assertIntValue(0x4);
				assertEof();
			}
		}.test();

		new LexerTester("LD %20, #'0'") {
			@Override
			protected void test() {
				assertType(TokenType.LD);
				assertIntValue(0x20);
				assertType(TokenType.COMMA);
				assertType(TokenType.HASH);
				assertIntValue(0x30);
				assertEof();
			}
		}.test();

		new LexerTester("LDEI @R6, @RR0") {
			@Override
			protected void test() {
				assertType(TokenType.LDEI);
				assertType(TokenType.AT);
				assertWorkRegister(6);
				assertType(TokenType.COMMA);
				assertType(TokenType.AT);
				assertWorkRegisterPair(0);
				assertEof();
			}
		}.test();

		new LexerTester(".data L\"foo bar\" \"bazz\" L\"\" \"\\\"\\x0d\" '\\x7f'") {
			@Override
			protected void test() {
				assertType(TokenType.DATA);
				assertLocation(0, 0);
				assertText(TokenType.LENGTH_STRING, "foo bar");
				assertLocation(0, 6);
				assertText(TokenType.STRING, "bazz");
				assertLocation(0, 17);
				assertText(TokenType.LENGTH_STRING, "");
				assertLocation(0, 24);
				assertText(TokenType.STRING, "\"\r");
				assertLocation(0, 28);
				assertIntValue(0x7f);
				assertLocation(0, 37);
				assertEof();
			}
		}.test();

		new LexerTester(".data \"\\0\"") {
			@Override
			protected void test() {
				assertType(TokenType.DATA);
				assertLocation(0, 0);
				assertText(TokenType.STRING, "\0");
				assertLocation(0, 6);
				assertEof();
			}
		}.test();
	}

	private abstract static class LexerTester {
		protected abstract void test();

		private final Lexer lexer;

		public LexerTester(String text) {
			this.lexer = new Lexer(text);
		}

		public void assertLocation(int expectedLine, int expectedColumn) {
			final Location location = lexer.getLocation();
			assertEquals(expectedLine, location.line());
			assertEquals(expectedColumn, location.column());
		}

		public void assertEof() {
			assertType(TokenType.EOF);
		}

		public void assertLineBreak() {
			assertType(TokenType.LINE_BREAK);
		}

		public void assertLabel(String expected) {
			assertText(TokenType.LABEL, expected);
		}

		public void assertComment(String expected) {
			assertText(TokenType.COMMENT, expected);
		}

		public void assertIdentifier(String expected) {
			assertText(TokenType.IDENTIFIER, expected);
		}

		public void assertWorkRegister(int expectedRegister) {
			assertType(TokenType.WORK_REGISTER);
			assertEquals(expectedRegister, lexer.getIntValue());
		}

		public void assertWorkRegisterPair(int expectedRegister) {
			assertType(TokenType.WORK_REGISTER_PAIR);
			assertEquals(expectedRegister, lexer.getIntValue());
		}

		public void assertIntValue(int expectedValue) {
			assertType(TokenType.INT_LITERAL);
			assertEquals(expectedValue, lexer.getIntValue());
		}

		public void assertInvalidTokenException(String expectedMsg, int expectedLine, int expectedColumn) {
			try {
				next();
				fail();
			}
			catch (InvalidTokenException ex) {
				assertEquals(expectedMsg, ex.getMessage());
				assertEquals(expectedLine, ex.location.line());
				assertEquals(expectedColumn, ex.location.column());
			}
		}

		public void assertType(TokenType type) {
			final TokenType next = next();
			assertEquals(type, next);
		}

		public void assertText(TokenType type, String expected) {
			assertType(type);
			assertEquals(expected, lexer.getText());
		}

		private TokenType next() {
			TokenType type;
			do {
				type = lexer.next();
			}
			while (type == TokenType.WHITESPACE);
			return type;
		}
	}
}