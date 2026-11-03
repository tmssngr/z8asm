package com.syntevo.z8asm;

import org.jetbrains.annotations.*;
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
				nextLabel("main", 0, 0);

				nextLineBreak("\n", 0, 5);

				nextType(TokenType.LD, "ld", 1, 2);
				nextValue(TokenType.WORK_REGISTER, 0, 1, 5);
				nextType(TokenType.COMMA, 1, 7);
				nextType(TokenType.HASH, 1, 9);
				nextIntValue(10, 2, 1, 10);
				nextLineBreak("\n", 1, 12);

				nextLabel("main", 2, 0);
				nextType(TokenType.COMMENT, "; duplicate", 2, 6);
				nextLineBreak("\n", 2, 17);

				nextType(TokenType.CALL, "call", 3, 2);
				nextIntValue(0x1000, 2, 3, 7);
				nextLineBreak("\n", 3, 12);

				nextType(TokenType.DJNZ, "djnz", 4, 2);
				nextValue(TokenType.WORK_REGISTER, 0, 4, 7);
				nextType(TokenType.COMMA, 4, 9);
				nextType(TokenType.IDENTIFIER, "main", 4, 11);
				nextLineBreak("\n", 4, 15);

				nextType(TokenType.RET, "ret", 5, 2);
				nextEof(5, 5);
			}
		}.test();

		//               01234567890123456789
		new LexerTester("TM R3, #0b0000_0100") {
			@Override
			protected void test() {
				nextType(TokenType.TM, "TM", 0, 0);
				nextValue(TokenType.WORK_REGISTER, 3, 0, 3);
				nextType(TokenType.COMMA, 0, 5);
				nextType(TokenType.HASH, 0, 7);
				nextIntValue(0x4, 1, 0, 8);
				nextEof(0, 19);
			}
		}.test();

		//               0123456789012
		new LexerTester("LD %20, #'0'") {
			@Override
			protected void test() {
				nextType(TokenType.LD, "LD", 0, 0);
				nextIntValue(0x20, 1, 0, 3);
				nextType(TokenType.COMMA, 0, 6);
				nextType(TokenType.HASH, 0, 8);
				nextIntValue(0x30, 1, 0, 9);
				nextEof(0, 12);
			}
		}.test();

		//               012345678901234
		new LexerTester("LDEI @R6, @RR0") {
			@Override
			protected void test() {
				nextType(TokenType.LDEI, "LDEI", 0, 0);
				nextType(TokenType.AT, 0, 5);
				nextValue(TokenType.WORK_REGISTER, 6, 0, 6);
				nextType(TokenType.COMMA, 0, 8);
				nextType(TokenType.AT, 0, 10);
				nextValue(TokenType.WORK_REGISTER_PAIR, 0, 0, 11);
				nextEof(0, 14);
			}
		}.test();

		//               0          1           2              3             4
		//               01234567 89012345 67 89012 345 6 78 9 0 1 2345 678 90123
		new LexerTester(".data L\"foo bar\" \"bazz\" L\"\" \"\\\"\\x0d\" '\\x7f'") {
			@Override
			protected void test() {
				nextType(TokenType.DATA, ".data", 0, 0);
				nextType(TokenType.LENGTH_STRING, "foo bar", 0, 6);
				nextType(TokenType.STRING, "bazz", 0, 17);
				nextType(TokenType.LENGTH_STRING, "", 0, 24);
				nextType(TokenType.STRING, "\"\r", 0, 28);
				nextIntValue(0x7f, 1, 0, 37);
				nextEof(0, 43);
			}
		}.test();

		//               0123456 7 89 0
		new LexerTester(".data \"\\0\"") {
			@Override
			protected void test() {
				nextType(TokenType.DATA, ".data", 0, 0);
				nextType(TokenType.STRING, "\0", 0, 6);
				nextEof(0, 10);
			}
		}.test();

		new LexerTester("''") {
			@Override
			protected void test() {
				assertInvalidTokenException("An empty string can only be done with L\"\"", 0, 0);
			}
		}.test();

		new LexerTester(" 'ab'") {
			@Override
			protected void test() {
				assertInvalidTokenException("Invalid char literal", 0, 1);
			}
		}.test();

		new LexerTester(" '") {
			@Override
			protected void test() {
				assertInvalidTokenException("A char must end with a quote", 0, 1);
			}
		}.test();

		new LexerTester(" \"") {
			@Override
			protected void test() {
				assertInvalidTokenException("String must end with a double-quote", 0, 2);
			}
		}.test();
	}

	@Test
	public void testComments() {
		new LexerTester("""
				                // line
				                ; comment
				                foo /* bar
				                bazz* / */ blupp""") {
			@Override
			protected void test() {
				nextType(TokenType.COMMENT, "// line", 0, 0);
				nextType(TokenType.LINE_BREAK, "\n", 0, 7);
				nextType(TokenType.COMMENT, "; comment", 1, 0);
				nextType(TokenType.LINE_BREAK, "\n", 1, 9);
				nextType(TokenType.IDENTIFIER, "foo", 2, 0);
				nextType(TokenType.COMMENT, "/* bar\nbazz* / */", 2, 4);
				nextType(TokenType.IDENTIFIER, "blupp", 3, 11);
			}
		}.test();
	}

	private abstract static class LexerTester {
		protected abstract void test();

		private final Lexer lexer;

		public LexerTester(String text) {
			this.lexer = new Lexer(text);
		}

		public void nextEof(int line, int column) {
			nextType(TokenType.EOF, "", line, column);
		}

		public void nextLineBreak(String text, int line, int column) {
			nextType(TokenType.LINE_BREAK, text, line, column);
		}

		public void nextLabel(String expected, int line, int column) {
			nextType(TokenType.LABEL, expected, line, column);
		}

		public void nextType(TokenType expectedType, int line, int column) {
			nextType(expectedType, "", line, column);
		}

		public void nextType(TokenType expectedType, String expected, int line, int column) {
			final Token token = next();
			assertToken(token, expectedType, expected, 0, 0, new Location(line, column));
		}

		private void assertToken(Token token, TokenType expectedType, String expectedText, int expectedIntValue, int expectedSize, @Nullable Location expectedLocation) {
			assertEquals(expectedType, token.type());
			assertEquals(expectedText, token.text());
			assertEquals(expectedIntValue, token.intValue());
			assertEquals(expectedSize, token.size());
			if (expectedLocation != null) {
				assertEquals(expectedLocation, token.location());
			}
		}

		public void nextValue(TokenType type, int expectedRegister, int expectedLine, int expectedColumn) {
			final Token token = next();
			assertToken(token, type, "", expectedRegister, 0, new Location(expectedLine, expectedColumn));
		}

		public void nextIntValue(int expectedValue, int expectedSize, int expectedLine, int expectedColumn) {
			final Token token = next();
			assertToken(token, TokenType.INT_LITERAL, "", expectedValue, expectedSize, new Location(expectedLine, expectedColumn));
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

		private Token next() {
			Token token;
			do {
				token = lexer.next();
			}
			while (token.type() == TokenType.WHITESPACE);
			return token;
		}
	}
}