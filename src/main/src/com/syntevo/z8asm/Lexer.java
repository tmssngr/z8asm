package com.syntevo.z8asm;

import java.util.*;

import org.jetbrains.annotations.*;

/**
 * @author Thomas Singer
 */
public final class Lexer {

	private final CharSource source;

	private int line;
	private int column;
	private OptionalInt chr;

	public Lexer(@NotNull String text) {
		this(new CharSource() {
			private int index;

			@NotNull
			@Override
			public OptionalInt next() {
				return index == text.length()
						? OptionalInt.empty()
						: OptionalInt.of(text.charAt(index++));
			}
		});
	}

	public Lexer(@NotNull CharSource source) {
		this.source = source;

		chr = source.next();
	}

	@NotNull
	public Token next() {
		final Location location = new Location(line, column);

		if (isEmpty()) {
			return new Token(TokenType.EOF, "", 0, 0, location);
		}

		final int chr = getChr();
		if (isWhitespace(chr)) {
			final String text = detectWhitespace();
			return new Token(TokenType.WHITESPACE, text, 0, 0, location);
		}
		if (isLineBreak(chr)) {
			final String text = detectLineBreak();
			return new Token(TokenType.LINE_BREAK, text, 0, 0, location);
		}
		if (chr == ';') {
			final String text = detectLineComment(";");
			return new Token(TokenType.COMMENT, text, 0, 0, location);
		}
		if (chr == '/') {
			consume();
			if (this.chr.isPresent()) {
				final int chr2 = getChr();
				if (chr2 == '/') {
					final String text = detectLineComment("//");
					return new Token(TokenType.COMMENT, text, 0, 0, location);
				}

				if (chr2 == '*') {
					final String text = detectBlockComment(location);
					return new Token(TokenType.COMMENT, text, 0, 0, location);
				}
			}

			final String text = "/";
			return new Token(TokenType.IDENTIFIER, text, 0, 0, location);
		}

		if (chr == ',') {
			consume();
			return new Token(TokenType.COMMA, "", 0, 0, location);
		}
		if (chr == '#') {
			consume();
			return new Token(TokenType.HASH, "", 0, 0, location);
		}
		if (chr == '@') {
			consume();
			return new Token(TokenType.AT, "", 0, 0, location);
		}
		if (chr == '(') {
			consume();
			return new Token(TokenType.L_PAREN, "", 0, 0, location);
		}
		if (chr == ')') {
			consume();
			return new Token(TokenType.R_PAREN, "", 0, 0, location);
		}
		if (chr == '"') {
			consume();
			final String text = detectStringOrChar(false, '"', location);
			return new Token(TokenType.STRING, text, 0, 0, location);
		}
		if (chr == '\'') {
			consume();
			final String text = detectStringOrChar(false, '\'', location);
			if (text.length() != 1) {
				throw new InvalidTokenException("Invalid char literal", location);
			}
			final int intValue = text.charAt(0);
			return new Token(TokenType.INT_LITERAL, "", intValue, 1, location);
		}

		final StringBuilder buffer = new StringBuilder();
		append(chr, buffer);
		while (true) {
			consume();
			if (isEmpty()) {
				break;
			}

			final int chr2 = getChr();
			if (chr2 == '"') {
				if (buffer.length() == 1 && buffer.charAt(0) == 'L') {
					consume();
					final String text = detectStringOrChar(true, '"', location);
					return new Token(TokenType.LENGTH_STRING, text, 0, 0, location);
				}
				break;
			}
			if (chr2 == ','
			    || chr2 == ';'
			    || chr2 == '('
			    || chr2 == ')'
			    || chr2 == '#'
			    || isWhitespace(chr2) || isLineBreak(chr2)) {
				break;
			}

			append(chr2, buffer);
		}

		final String text = buffer.toString();
		if (text.length() > 1) {
			if (text.endsWith(":")) {
				final String label = buffer.substring(0, text.length() - 1);
				return new Token(TokenType.LABEL, label, 0, 0, location);
			}

			Token token = detectWorkRegister(text, "rr", TokenType.WORK_REGISTER_PAIR, location);
			if (token == null) {
				token = detectWorkRegister(text, "r", TokenType.WORK_REGISTER, location);
			}
			if (token != null) {
				return token;
			}
		}

		final Token token = detectInt(text, location);
		if (token != null) {
			return token;
		}

		return new Token(getKeywordOrIdentifier(text), text, 0, 0, location);
	}

	@NotNull
	private TokenType getKeywordOrIdentifier(String text) {
		return switch (text.toLowerCase()) {
			case ".align" -> TokenType.ALIGN;
			case ".const" -> TokenType.CONST;
			case ".data" -> TokenType.DATA;
			case ".end" -> TokenType.END;
			case ".org" -> TokenType.ORG;
			case ".repeat" -> TokenType.REPEAT;

			case "ld" -> TokenType.LD;
			case "ldc" -> TokenType.LDC;
			case "ldci" -> TokenType.LDCI;
			case "lde" -> TokenType.LDE;
			case "ldei" -> TokenType.LDEI;

			case "dec" -> TokenType.DEC;
			case "rlc" -> TokenType.RLC;
			case "inc" -> TokenType.INC;
			case "srp" -> TokenType.SRP;
			case "da" -> TokenType.DA;
			case "pop" -> TokenType.POP;
			case "com" -> TokenType.COM;
			case "push" -> TokenType.PUSH;
			case "decw" -> TokenType.DECW;
			case "rl" -> TokenType.RL;
			case "incw" -> TokenType.INCW;
			case "clr" -> TokenType.CLR;
			case "rrc" -> TokenType.RRC;
			case "sra" -> TokenType.SRA;
			case "rr" -> TokenType.RR;
			case "swap" -> TokenType.SWAP;

			case "add" -> TokenType.ADD;
			case "adc" -> TokenType.ADC;
			case "sub" -> TokenType.SUB;
			case "sbc" -> TokenType.SBC;
			case "or" -> TokenType.OR;
			case "and" -> TokenType.AND;
			case "tcm" -> TokenType.TCM;
			case "tm" -> TokenType.TM;
			case "cp" -> TokenType.CP;
			case "xor" -> TokenType.XOR;

			case "call" -> TokenType.CALL;
			case "djnz" -> TokenType.DJNZ;
			case "jp" -> TokenType.JP;
			case "jr" -> TokenType.JR;

			case "di" -> TokenType.DI;
			case "ei" -> TokenType.EI;
			case "ret" -> TokenType.RET;
			case "iret" -> TokenType.IRET;
			case "rcf" -> TokenType.RCF;
			case "scf" -> TokenType.SCF;
			case "ccf" -> TokenType.CCF;
			case "nop" -> TokenType.NOP;
			default -> TokenType.IDENTIFIER;
		};
	}

	@Nullable
	private Token detectWorkRegister(String text, String prefix, TokenType type, Location location) {
		text = text.toLowerCase(Locale.ROOT);
		if (text.startsWith(prefix)) {
			text = text.substring(prefix.length());

			try {
				final int intValue = Integer.parseInt(text);

				if (intValue >= 0 && intValue < 16) {
					return new Token(type, "", intValue, 0, location);
				}
			}
			catch (NumberFormatException ignored) {
			}
		}
		return null;
	}

	@Nullable
	private Token detectInt(String text, Location location) {
		try {
			final int intValue = Integer.parseInt(text);
			return new Token(TokenType.INT_LITERAL, "", intValue, 2, location);
		}
		catch (NumberFormatException ignored) {
		}

		if (text.startsWith("0b")) {
			final String possibleNumberString = text.substring(2).replace("_", "");
			try {
				final int intValue = Integer.parseInt(possibleNumberString, 2);
				return new Token(TokenType.INT_LITERAL, "", intValue, 1, location);
			}
			catch (NumberFormatException ignored) {
				throw new InvalidTokenException("Invalid number", location);
			}
		}

		if (text.startsWith("%")) {
			final String possibleNumberString = text.substring(1);
			try {
				final int intValue = Integer.parseInt(possibleNumberString,
				                            16);
				final int size = possibleNumberString.length() <= 2 ? 1 : 2;
				return new Token(TokenType.INT_LITERAL, "", intValue, size, location);
			}
			catch (NumberFormatException ignored) {
			}
		}
		return null;
	}

	private String detectWhitespace() {
		final StringBuilder buffer = new StringBuilder();
		while (chr.isPresent()) {
			final int chr = getChr();
			if (!isWhitespace(chr)) {
				break;
			}
			append(chr, buffer);
			consume();
		}
		return buffer.toString();
	}

	private String detectLineBreak() {
		final StringBuilder buffer = new StringBuilder();
		while (chr.isPresent()) {
			final int chr = getChr();
			if (!isLineBreak(chr)) {
				break;
			}

			append(chr, buffer);
			consume();
		}
		return buffer.toString();
	}

	private String detectLineComment(String start) {
		final StringBuilder buffer = new StringBuilder();
		buffer.append(start);
		consume();
		while (chr.isPresent()) {
			final int chr = getChr();
			if (isLineBreak(chr)) {
				break;
			}

			append(chr, buffer);
			consume();
		}
		return buffer.toString();
	}

	private String detectBlockComment(Location location) {
		final StringBuilder buffer = new StringBuilder();
		buffer.append("/*");
		int prev = 0;
		while (true) {
			consume();
			if (isEmpty()) {
				throw new InvalidTokenException("Block comment needs to be closed", location);
			}

			final int chr = getChr();
			append(chr, buffer);
			if (prev == '*' && chr == '/') {
				consume();
				return buffer.toString();
			}

			prev = chr;
		}
	}

	private String detectStringOrChar(boolean isLengthString, char endChar, Location location) {
		final StringBuilder buffer = new StringBuilder();
		Location escapeStartLocation = null;
		while (true) {
			if (isEmpty() || isLineBreak(getChr())) {
				if (endChar == '\'') {
					throw new InvalidTokenException("A char literal must end with a quote", location);
				}
				throw new InvalidTokenException("String must end with a double-quote", new Location(line, column));
			}

			final int chr = getChr();
			if (escapeStartLocation != null) {
				switch (chr) {
				case 't' -> consumeAppend('\t', buffer);
				case 'n' -> consumeAppend('\n', buffer);
				case 'r' -> consumeAppend('\r', buffer);
				case '0' -> consumeAppend(0, buffer);
				case 'x' -> {
					consume();
					boolean tooShort = true;
					int hexValue = 0;
					while (true) {
						if (isEmpty()) {
							if (tooShort) {
								throw new InvalidTokenException("Invalid char escape", escapeStartLocation);
							}
							break;
						}

						final int nibble = toHexNibble(getChr());
						if (nibble < 0) {
							if (tooShort) {
								throw new InvalidTokenException("Invalid char escape", escapeStartLocation);
							}
							break;
						}

						consume();
						hexValue = (16 * hexValue + nibble) & 0xFFFF;
						tooShort = false;
					}
					append(hexValue, buffer);
				}
				default -> consumeAppend(chr, buffer);
				}
				escapeStartLocation = null;
				continue;
			}

			if (chr == endChar) {
				consume();
				break;
			}

			if (chr == '\\') {
				escapeStartLocation = new Location(line, column);
			}
			else {
				append(chr, buffer);
			}
			consume();
		}
		if (isLengthString) {
			if (buffer.length() > 255) {
				throw new InvalidTokenException("A length-string only can be up to 255 characters long", location);
			}
		}
		else if (buffer.isEmpty()) {
			throw new InvalidTokenException("An empty string can only be done with L\"\"", location);
		}
		return buffer.toString();
	}

	private int getChr() {
		return chr.orElseThrow();
	}

	private boolean isEmpty() {
		return chr.isEmpty();
	}

	private int toHexNibble(int chr) {
		if (chr < '0') {
			return -1;
		}
		if (chr <= '9') {
			return chr - '0';
		}
		if (chr < 'A') {
			return -1;
		}
		if (chr <= 'F') {
			return chr - 'A' + 10;
		}
		if (chr < 'a') {
			return -1;
		}
		if (chr <= 'f') {
			return chr - 'a' + 10;
		}
		return -1;
	}

	private void consume() {
		if (isEmpty()) {
			return;
		}

		final int prevChr = getChr();
		chr = source.next();

		if (prevChr == '\n') {
			line++;
			column = 0;
		}
		else {
			column++;
		}
	}

	private static boolean isWhitespace(int chr) {
		return chr == ' ' || chr == '\t';
	}

	private static boolean isLineBreak(int chr) {
		return chr == '\r' || chr == '\n';
	}

	private void consumeAppend(int chr, StringBuilder buffer) {
		append(chr, buffer);
		consume();
	}

	private static void append(int chr, StringBuilder buffer) {
		buffer.append((char)chr);
	}
}
