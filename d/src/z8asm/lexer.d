// Lexer.d - translated from Java
module z8asm.lexer;

import std.string : format, replace, toLower, startsWith;
import std.range;
import std.array : Appender;
import std.conv;
import object : Exception;

import z8asm.token_type : TokenType;
import z8asm.location : Location;
import z8asm.char_source : CharSource;
import z8asm.invalid_token_exception : InvalidTokenException;

// StringCharSource implementation
private class StringCharSource : CharSource {
	private wstring text;
	private size_t index = 0;

	this(string text) {
		this.text = to!wstring(text);
	}

	override int next() {
		if (index >= text.length) return -1;
		return cast(int)text[index++];
	}
}

class Lexer {
	private {
		CharSource source;
		int line = 0;
		int column = 0;
		int chr = 0;
		int intValue = 0;
		int size = 0;
		string text = "";
		Location location;
	}

	public this(string text) {
		this(new StringCharSource(text));
	}

	public this(CharSource source) {
		this.source = source;
		this.chr = source.next();
	}

	public TokenType next() {
		text = "";
		intValue = 0;
		size = 0;
		location = Location(line, column);

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
			wstring text = to!wstring(detectStringOrChar(false, '\''));
			if (text.length != 1) {
				throw new InvalidTokenException("Invalid char literal", location);
			}
			intValue = text[0];
			size = 1;
			return TokenType.INT_LITERAL;
		}

		Appender!(wchar[]) buffer;
		do {
			buffer.put(cast(wchar)chr);
			consume();
		} while (chr >= 0
		         && chr != ','
		         && chr != ';'
		         && chr != '"'
		         && chr != '(' 
		         && chr != ')'
		         && chr != '#'
		         && !isWhitespace()
		         && !isLineBreak()
		);
		text = to!string(buffer.data);

		if ((text == "L" || text == "l") && chr == '"') {
			consume();
			text = detectStringOrChar(true, '"');
			return TokenType.LENGTH_STRING;
		}

		if (text.length > 1) {
			if (text[text.length - 1] == ':') {
				text = text[0 .. text.length - 1];
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

		// Keyword matching
		string lowerText = text.toLower();
		
		if (lowerText == ".align") return TokenType.ALIGN;
		if (lowerText == ".const") return TokenType.CONST;
		if (lowerText == ".data") return TokenType.DATA;
		if (lowerText == ".end") return TokenType.END;
		if (lowerText == ".org") return TokenType.ORG;
		if (lowerText == ".repeat") return TokenType.REPEAT;

		if (lowerText == "ld") return TokenType.LD;
		if (lowerText == "ldc") return TokenType.LDC;
		if (lowerText == "ldci") return TokenType.LDCI;
		if (lowerText == "lde") return TokenType.LDE;
		if (lowerText == "ldei") return TokenType.LDEI;

		if (lowerText == "dec") return TokenType.DEC;
		if (lowerText == "rlc") return TokenType.RLC;
		if (lowerText == "inc") return TokenType.INC;
		if (lowerText == "srp") return TokenType.SRP;
		if (lowerText == "da") return TokenType.DA;
		if (lowerText == "pop") return TokenType.POP;
		if (lowerText == "com") return TokenType.COM;
		if (lowerText == "push") return TokenType.PUSH;
		if (lowerText == "decw") return TokenType.DECW;
		if (lowerText == "rl") return TokenType.RL;
		if (lowerText == "incw") return TokenType.INCW;
		if (lowerText == "clr") return TokenType.CLR;
		if (lowerText == "rrc") return TokenType.RRC;
		if (lowerText == "sra") return TokenType.SRA;
		if (lowerText == "rr") return TokenType.RR;
		if (lowerText == "swap") return TokenType.SWAP;

		if (lowerText == "add") return TokenType.ADD;
		if (lowerText == "adc") return TokenType.ADC;
		if (lowerText == "sub") return TokenType.SUB;
		if (lowerText == "sbc") return TokenType.SBC;
		if (lowerText == "or") return TokenType.OR;
		if (lowerText == "and") return TokenType.AND;
		if (lowerText == "tcm") return TokenType.TCM;
		if (lowerText == "tm") return TokenType.TM;
		if (lowerText == "cp") return TokenType.CP;
		if (lowerText == "xor") return TokenType.XOR;

		if (lowerText == "call") return TokenType.CALL;
		if (lowerText == "djnz") return TokenType.DJNZ;
		if (lowerText == "jp") return TokenType.JP;
		if (lowerText == "jr") return TokenType.JR;

		if (lowerText == "di") return TokenType.DI;
		if (lowerText == "ei") return TokenType.EI;
		if (lowerText == "ret") return TokenType.RET;
		if (lowerText == "iret") return TokenType.IRET;
		if (lowerText == "rcf") return TokenType.RCF;
		if (lowerText == "scf") return TokenType.SCF;
		if (lowerText == "ccf") return TokenType.CCF;
		if (lowerText == "nop") return TokenType.NOP;

		return TokenType.IDENTIFIER;
	}

	public string getText() { return text; }
	public int getIntValue() { return intValue; }
	public int getSize() { return size; }
	public Location getLocation() { return location; }

	// Private helper methods
	private bool isWorkRegister(string text) {
		text = text.toLower();
		if (text.length == 0 || text[0] != 'r') return false;
		text = text[1 .. $];
		try {
			intValue = std.conv.to!int(text);
			return intValue >= 0 && intValue < 16;
		} catch (Exception e) {
			return false;
		}
	}

	private bool isWorkRegisterPair(string text) {
		text = text.toLower();
		if (text.length < 2 || text[0 .. 2] != "rr") return false;
		text = text[2 .. $];
		try {
			intValue = std.conv.to!int(text);
			return intValue >= 0 && intValue < 16;
		} catch (Exception e) {
			return false;
		}
	}

	private bool detectInt(string text) {
		try {
			intValue = std.conv.to!int(text);
			size = 2;
			return true;
		} catch (Exception e) {}

		if (text.startsWith("0b")) {
			string possibleNumberString = text[2 .. $].replace("_", "");
			try {
				intValue = std.conv.to!int(possibleNumberString, 2);
				size = 1;
				return true;
			} catch (Exception e) {
				throw new InvalidTokenException("Invalid number", location);
			}
		}

		if (text.startsWith("%")) {
			string possibleNumberString = text[1 .. $];
			try {
				intValue = std.conv.to!int(possibleNumberString, 16);
				size = possibleNumberString.length <= 2 ? 1 : 2;
				return true;
			} catch (Exception e) {}
		}
		return false;
	}

	private string detectWhitespace() {
		Appender!(wchar[]) buffer;
		do {
			buffer.put(cast(wchar)chr);
			consume();
		} while (isWhitespace());
		return to!string(buffer.data);
	}

	private string detectLineBreak() {
		Appender!(wchar[]) buffer;
		do {
			buffer.put(cast(wchar)chr);
			consume();
		} while (isLineBreak());
		return to!string(buffer.data);
	}

	private string detectLineComment(string start) {
		Appender!(wchar[]) buffer;
		buffer.put(to!wstring(start));
		do {
			buffer.put(cast(wchar)chr);
			consume();
		} while (chr >= 0 && !isLineBreak());
		return to!string(buffer.data);
	}

	private string detectBlockComment() {
		Appender!(wchar[]) buffer;
		buffer.put('/');
		buffer.put('*');
		int prev = 0;
		while (true) {
			consume();
			if (chr < 0) {
				throw new InvalidTokenException("Block comment needs to be closed", location);
			}

			buffer.put(cast(wchar)chr);
			if (prev == '*' && chr == '/') {
				consume();
				return to!string(buffer.data);
			}

			prev = chr;
		}
	}

	private string detectStringOrChar(bool isLengthString, char endChar) {
		Appender!(wchar[]) buffer;
		Location* escapeStartLocation;
		while (true) {
			if (chr < 0 || isLineBreak()) {
				if (endChar == '\'') {
					throw new InvalidTokenException("A char must end with a quote", location);
				}
				throw new InvalidTokenException("String must end with a double-quote", Location(line, column));
			}

			if (escapeStartLocation !is null) {
				switch (chr) {
					case 't': consumeAppend('\t', buffer); break;
					case 'n': consumeAppend('\n', buffer); break;
					case 'r': consumeAppend('\r', buffer); break;
					case '0': consumeAppend(0, buffer); break;
					case 'x': 
						consume();
						bool tooShort = true;
						int hexValue = 0;
						while (true) {
							if (chr < 0) {
								if (tooShort) {
									throw new InvalidTokenException("Invalid char escape", *escapeStartLocation);
								}
								break;
							}

							int nibble = toHexNibble(chr);
							if (nibble < 0) {
								if (tooShort) {
									throw new InvalidTokenException("Invalid char escape", *escapeStartLocation);
								}
								break;
							}

							consume();
							hexValue = (16 * hexValue + nibble) & 0xFFFF;
							tooShort = false;
						}
						append(buffer, hexValue);
						break;
					default: consumeAppend(chr, buffer); break;
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
			} else {
				buffer.put(cast(wchar)chr);
			}
			consume();
		}
		if (isLengthString) {
			if (buffer.data.length > 255) {
				throw new InvalidTokenException("A length-string only can be up to 255 characters long", location);
			}
		} else if (buffer.data.empty) {
			throw new InvalidTokenException("An empty string can only be done with L\"\"", location);
		}
		return to!string(buffer.data);
	}

	private int toHexNibble(int chr) {
		if (chr < '0') return -1;
		if (chr <= '9') return chr - '0';
		if (chr < 'A') return -1;
		if (chr <= 'F') return chr - 'A' + 10;
		if (chr < 'a') return -1;
		if (chr <= 'f') return chr - 'a' + 10;
		return -1;
	}

	private void consume() {
		if (chr < 0) return;

		int prevChr = chr;
		chr = source.next();

		if (prevChr == '\n') {
			line++;
			column = 0;
		} else {
			column++;
		}
	}

	private bool isWhitespace() {
		return chr == ' ' || chr == '\t';
	}

	private bool isLineBreak() {
		return chr == '\r' || chr == '\n';
	}

	private void append(ref Appender!(wchar[]) buffer, int c) {
		buffer.put(cast(wchar)c);
	}

	private void consumeAppend(int c, ref Appender!(wchar[]) buffer) {
		append(buffer, c);
		consume();
	}
}
