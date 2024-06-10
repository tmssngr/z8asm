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
	private int chr;
	private int intValue;
	private int size;
	private String text;
	private Location location;

	public Lexer(@NotNull String text) {
		this(new CharSource() {
			private int index;

			@Override
			public int next() {
				return index == text.length()
						? -1
						: text.charAt(index++);
			}
		});
	}

	public Lexer(@NotNull CharSource source) {
		this.source = source;

		chr = source.next();
	}

	public TokenType next() {
		text = null;
		intValue = 0;
		size = 0;
		location = new Location(line, column);

		if (chr < 0) {
			return TokenType.EOF;
		}

		if (isWhitespace()) {
			text = detectWhitespace();
			return TokenType.WHITESPACE;
		}
		if (isLineBreak()) {
			text = detectLineBreak();
			return TokenType.LINE_BREAK;
		}
		if (chr == ';') {
			text = detectLineComment("");
			return TokenType.COMMENT;
		}
		if (chr == '/') {
			consume();
			if (chr == '/') {
				text = detectLineComment("/");
				return TokenType.COMMENT;
			}

			if (chr == '*') {
				text = detectBlockComment();
				return TokenType.COMMENT;
			}

			text = "/";
			return TokenType.IDENTIFIER;
		}

		if (chr == ',') {
			consume();
			return TokenType.COMMA;
		}
		if (chr == '#') {
			consume();
			return TokenType.HASH;
		}
		if (chr == '@') {
			consume();
			return TokenType.AT;
		}
		if (chr == '(') {
			consume();
			return TokenType.L_PAREN;
		}
		if (chr == ')') {
			consume();
			return TokenType.R_PAREN;
		}
		if (chr == '"') {
			consume();
			text = detectStringOrChar(false, '"');
			return TokenType.STRING;
		}
		if (chr == '\'') {
			consume();
			final String text = detectStringOrChar(false, '\'');
			intValue = text.charAt(0);
			size = 1;
			return TokenType.INT_LITERAL;
		}

		final StringBuilder buffer = new StringBuilder();
		do {
			append(buffer);
			consume();
		}
		while (chr >= 0
		       && chr != ','
		       && chr != ';'
		       && chr != '"'
		       && chr != '('
		       && chr != ')'
		       && chr != '#'
		       && !isWhitespace()
		       && !isLineBreak()
		);
		text = buffer.toString();

		if (text.equalsIgnoreCase("L") && chr == '"') {
			consume();
			text = detectStringOrChar(true, '"');
			return TokenType.LENGTH_STRING;
		}

		if (text.length() > 1) {
			if (text.endsWith(":")) {
				text = buffer.substring(0, text.length() - 1);
				return TokenType.LABEL;
			}
			if (isWorkRegister(text)) {
				return TokenType.WORK_REGISTER;
			}
			if (isWorkRegisterPair(text)) {
				return TokenType.WORK_REGISTER_PAIR;
			}
		}

		if (detectInt(text)) {
			return TokenType.INT_LITERAL;
		}

		return switch (text.toLowerCase()) {
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

	@NotNull
	public String getText() {
		return text;
	}

	public int getIntValue() {
		return intValue;
	}

	public int getSize() {
		return size;
	}

	public Location getLocation() {
		return location;
	}

	private boolean isWorkRegister(String text) {
		text = text.toLowerCase(Locale.ROOT);
		if (!text.startsWith("r")) {
			return false;
		}

		text = text.substring(1);

		try {
			intValue = Integer.parseInt(text);
			return intValue >= 0
			       && intValue < 16;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean isWorkRegisterPair(String text) {
		text = text.toLowerCase(Locale.ROOT);
		if (!text.startsWith("rr")) {
			return false;
		}

		text = text.substring(2);

		try {
			intValue = Integer.parseInt(text);
			return intValue >= 0
			       && intValue < 16;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean detectInt(String text) {
		try {
			intValue = Integer.parseInt(text);
			size = 2;
			return true;
		}
		catch (NumberFormatException ignored) {
		}

		if (text.startsWith("0b")) {
			final String possibleNumberString = text.substring(2).replace("_", "");
			try {
				intValue = Integer.parseInt(possibleNumberString, 2);
				size = 1;
				return true;
			}
			catch (NumberFormatException ignored) {
				throw new InvalidTokenException("Invalid number", location);
			}
		}

		if (text.startsWith("%")) {
			final String possibleNumberString = text.substring(1);
			try {
				intValue = Integer.parseInt(possibleNumberString,
				                            16);
				size = possibleNumberString.length() <= 2 ? 1 : 2;
				return true;
			}
			catch (NumberFormatException ignored) {
			}
		}
		return false;
	}

	private String detectWhitespace() {
		final StringBuilder buffer = new StringBuilder();
		do {
			append(buffer);
			consume();
		}
		while (isWhitespace());
		return buffer.toString();
	}

	private String detectLineBreak() {
		final StringBuilder buffer = new StringBuilder();
		do {
			append(buffer);
			consume();
		}
		while (isLineBreak());
		return buffer.toString();
	}

	private String detectLineComment(String start) {
		final StringBuilder buffer = new StringBuilder();
		buffer.append(start);
		do {
			append(buffer);
			consume();
		}
		while (chr >= 0 && !isLineBreak());
		return buffer.toString();
	}

	private String detectBlockComment() {
		final StringBuilder buffer = new StringBuilder();
		buffer.append("/*");
		int prev = 0;
		while (true) {
			consume();
			if (chr < 0) {
				throw new InvalidTokenException("Block comment needs to be closed", location);
			}

			append(buffer);
			if (prev == '*' && chr == '/') {
				consume();
				return buffer.toString();
			}

			prev = chr;
		}
	}

	private String detectStringOrChar(boolean isLengthString, char endChar) {
		final StringBuilder buffer = new StringBuilder();
		Location escapeStartLocation = null;
		while (true) {
			if (chr < 0 || isLineBreak()) {
				if (endChar == '\'') {
					throw new InvalidTokenException("A char must end with a quote", location);
				}
				throw new InvalidTokenException("String must end with a double-quote", new Location(line, column));
			}

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
						if (chr < 0) {
							if (tooShort) {
								throw new InvalidTokenException("Invalid char escape", escapeStartLocation);
							}
							break;
						}

						final int nibble = toHexNibble(chr);
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
				;
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
				append(buffer);
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
		if (chr < 0) {
			return;
		}

		final int prevChr = chr;
		chr = source.next();

		if (prevChr == '\n') {
			line++;
			column = 0;
		}
		else {
			column++;
		}
	}

	private boolean isWhitespace() {
		return " \t".indexOf(chr) >= 0;
	}

	private boolean isLineBreak() {
		return "\r\n".indexOf(chr) >= 0;
	}

	private void append(StringBuilder buffer) {
		append(chr, buffer);
	}

	private void consumeAppend(int chr, StringBuilder buffer) {
		append(chr, buffer);
		consume();
	}

	private static void append(int chr, StringBuilder buffer) {
		buffer.append((char)chr);
	}
}
