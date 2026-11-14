// Parser.d - translated from Java
module z8asm.parser;

import std.string : format, replace, toLower;
import std.conv : to;
import std.array : Appender;
import std.file : readText;
import std.path : dirName, baseName;
import std.range;

import z8asm.token_type : TokenType;
import z8asm.location : Location;
import z8asm.command : Command;
import z8asm.lexer : Lexer;
import z8asm.syntax_exception : SyntaxException;
import z8asm.char_source : CharSource;
import z8asm.utils : Utils;

class Parser {
	private static const int WORK_REG = 0xE0;

	public static Command[] parseFromFile(string file) {
		import std.file : readText;
		// Read file content
		string content = readText(file);
		return parse(content);
	}

	public static Command[] parse(string text) {
		Lexer lexer = new Lexer(text);
		Parser parser = new Parser(lexer);
		return parser.parse();
	}

	public static Command[] parse(CharSource source) {
		Lexer lexer = new Lexer(source);
		Parser parser = new Parser(lexer);
		return parser.parse();
	}

	private {
		int[string] constToValue;
		Lexer lexer;
		TokenType token;
	}

	public this(Lexer lexer) {
		this.lexer = lexer;
		constToValue = new int[string];
	}

	// Helper functions (must be defined before use)
	private static int command(int highNibble, int lowNibble) {
		return ((highNibble & 0xF) << 4) + (lowNibble & 0xF);
	}

	private Command content1(int value) {
		return Command.content1(value);
	}

	private Command content2(int first, int second) {
		return Command.content2(first, second);
	}

	private Command content3(int first, int second, int third) {
		return Command.content3(first, second, third);
	}

	private static bool isPowerOf2(int alignment) {
		return (alignment & (alignment - 1)) == 0;
	}

	public Command[] parse() {
		consume();

		Appender!(Command[]) commands;
		parse(TokenType.EOF, commands);
		return commands.data;
	}

	private void parse(TokenType allowedEndType, ref Appender!(Command[]) commands) {
		while (token != allowedEndType) {
			if (token == TokenType.LABEL) {
				if (allowedEndType == TokenType.END) {
					throw new SyntaxException("Labels inside .repeat .end blocks are not allowed", getLocation());
				}
				string label = getText();
				commands.put(Command.label(label, getLocation()));
				consume();
			}

			switch (token) {
			case TokenType.LINE_BREAK:
				consume();
				continue;
			case TokenType.ALIGN:
				commands.put(handleAlign());
				break;
			case TokenType.CONST:
				handleConst();
				break;
			case TokenType.DATA:
				appendData(commands);
				continue;
			case TokenType.REPEAT:
				handleRepeat(commands);
				break;
			case TokenType.ORG:
				commands.put(org());
				break;

			case TokenType.DEC:
				commands.put(unary(0x00));
				break;
			case TokenType.RLC:
				commands.put(unary(0x10));
				break;
			case TokenType.INC:
				commands.put(inc());
				break;
			case TokenType.SRP:
				commands.put(createSrp());
				break;
			case TokenType.DA:
				commands.put(unary(0x40));
				break;
			case TokenType.POP:
				commands.put(unary(0x50));
				break;
			case TokenType.COM:
				commands.put(unary(0x60));
				break;
			case TokenType.PUSH:
				commands.put(unary(0x70));
				break;
			case TokenType.DECW:
				commands.put(unary(0x80));
				break;
			case TokenType.RL:
				commands.put(unary(0x90));
				break;
			case TokenType.INCW:
				commands.put(unary(0xA0));
				break;
			case TokenType.CLR:
				commands.put(unary(0xB0));
				break;
			case TokenType.RRC:
				commands.put(unary(0xC0));
				break;
			case TokenType.SRA:
				commands.put(unary(0xD0));
				break;
			case TokenType.RR:
				commands.put(unary(0xE0));
				break;
			case TokenType.SWAP:
				commands.put(unary(0xF0));
				break;

			case TokenType.ADD:
				commands.put(createBinary(0x0));
				break;
			case TokenType.ADC:
				commands.put(createBinary(0x1));
				break;
			case TokenType.SUB:
				commands.put(createBinary(0x2));
				break;
			case TokenType.SBC:
				commands.put(createBinary(0x3));
				break;
			case TokenType.OR:
				commands.put(createBinary(0x4));
				break;
			case TokenType.AND:
				commands.put(createBinary(0x5));
				break;
			case TokenType.TCM:
				commands.put(createBinary(0x6));
				break;
			case TokenType.TM:
				commands.put(createBinary(0x7));
				break;
			case TokenType.CP:
				commands.put(createBinary(0xA));
				break;
			case TokenType.XOR:
				commands.put(createBinary(0xB));
				break;

			case TokenType.LD:
				commands.put(createLd());
				break;
			case TokenType.LDC:
				commands.put(createLdX(0xC));
				break;
			case TokenType.LDE:
				commands.put(createLdX(0x8));
				break;
			case TokenType.LDCI:
				commands.put(createLdXi(0xC));
				break;
			case TokenType.LDEI:
				commands.put(createLdXi(0x8));
				break;

			case TokenType.CALL:
				commands.put(createCall());
				break;
			case TokenType.JP:
				commands.put(createJp());
				break;
			case TokenType.JR:
				commands.put(createJr());
				break;
			case TokenType.DJNZ:
				commands.put(createDjnz());
				break;

			case TokenType.DI:
				commands.put(singleByteCommand(0x8F));
				break;
			case TokenType.EI:
				commands.put(singleByteCommand(0x9F));
				break;
			case TokenType.RET:
				commands.put(singleByteCommand(0xAF));
				break;
			case TokenType.IRET:
				commands.put(singleByteCommand(0xBF));
				break;
			case TokenType.RCF:
				commands.put(singleByteCommand(0xCF));
				break;
			case TokenType.SCF:
				commands.put(singleByteCommand(0xDF));
				break;
			case TokenType.CCF:
				commands.put(singleByteCommand(0xEF));
				break;
			case TokenType.NOP:
				commands.put(singleByteCommand(0xFF));
				break;

			default:
				throw new SyntaxException("unexpected " ~ to!string(token) ~ " " ~ getText(), getLocation());
			}

			if (token == TokenType.EOF) {
				break;
			}

			consume(TokenType.LINE_BREAK);
		}
	}

	private Command handleAlign() {
		consume();
		int alignment = consumeIntLiteral();
		if (alignment < 2 || !isPowerOf2(alignment)) {
			throw new SyntaxException("Expected a number the power of 2 (2, 4, 8, ...", getLocation());
		}

		consumeComma();
		int fillByte = consumeIntLiteral();
		return Command.createAlign(alignment, fillByte);
	}

	private void handleConst() {
		consume();
		string name = consumeIdentifier();
		if (name in constToValue) {
			throw new SyntaxException("Duplicate definition of " ~ name, getLocation());
		}

		if (consumeIdentifier() != "=") {
			throw new SyntaxException("Expected =", getLocation());
		}

		int value = consumeIntLiteral();
		constToValue[name] = value;
	}

	private void appendData(ref Appender!(Command[]) commands) {
		consume();
		bool isFirst = true;
		while (true) {
			if (token == TokenType.EOF || token == TokenType.LINE_BREAK) {
				if (isFirst) {
					throw new SyntaxException("Expected at least one byte", getLocation());
				}
				break;
			}

			isFirst = false;

			if (token == TokenType.INT_LITERAL) {
				int size = lexer.getSize();
				int value = consumeIntValue();
				if (size == 1) {
					commands.put(Command.content1(value));
				} else {
					commands.put(Command.content2(value >> 8, value));
				}
			} else if (token == TokenType.IDENTIFIER) {
				Location location = getLocation();
				string text = consumeText();
				commands.put(Command.lazyContent2(0, text, location));
			} else if (token == TokenType.STRING) {
				Location location = getLocation();
				string text = consumeText();
				if (text.empty) {
					throw new SyntaxException("Strings must not be empty.", location);
				}
				appendString(text, commands);
			} else if (token == TokenType.LENGTH_STRING) {
				string text = consumeText();
				commands.put(Command.content1(cast(int)to!wstring(text).length));
				if (!text.empty) {
					appendString(text, commands);
				}
			} else {
				throw new SyntaxException("unsupported constant", getLocation());
			}
		}
	}

	private Command org() {
		consume();
		Location location = getLocation();
		return Command.org(consumeIntLiteral(), location);
	}

	private void handleRepeat(ref Appender!(Command[]) commands) {
		consume(); // consume REPEAT

		int repeatTimes = consumeIntLiteral();
		if (repeatTimes <= 1) {
			throw new SyntaxException("Expected a value > 1", getLocation());
		}

		int repeatStart = cast(int)commands.data.length;

		parse(TokenType.END, commands);
		consume(); // consume END

		Command[] repeatingCommands = commands.data[repeatStart .. $].dup;
		while (--repeatTimes > 0) {
			foreach (cmd; repeatingCommands) {
				commands.put(cmd);
			}
		}
	}

	private Command createBinary(int highNibble) {
		consume();
		if (consumeIfAt()) {
			int dst = consumeRegister();
			consumeComma();
			if (consumeIfHash()) {
				return createBinaryImmediate(command(highNibble, 7), dst);
			}
			int src = consumeRegister();
			return content3(command(highNibble, 5), src, dst);
		}

		int dst = consumeRegister();
		consumeComma();
		if (consumeIfHash()) {
			return createBinaryImmediate(command(highNibble, 6), dst);
		}
		if (isWorkReg(dst) && consumeIfAt()) {
			int src = consumeWorkRegister();
			return content2(command(highNibble, 3), command(dst, src));
		}
		int src = consumeRegister();
		if (isWorkReg(src) && isWorkReg(dst)) {
			return content2(command(highNibble, 2), command(dst, src));
		}
		return content3(command(highNibble, 4), src, dst);
	}

	private Command createBinaryImmediate(int opCode, int dst) {
		return new BinaryImmediateCommandProvider(opCode, dst, this).run();
	}

	private Command createCall() {
		consume();
		if (consumeIfAt()) {
			int reg = consumeRegisterPair();
			return content2(0xD4, reg);
		}

		if (token == TokenType.INT_LITERAL) {
			int address = consumeIntValue();
			return content3(0xD6, address >> 8, address);
		}

		Location location = getLocation();
		string target = consumeIdentifier();
		return Command.lazyContent3(0xD6, target, location);
	}

	private Command createJp() {
		consume();
		if (consumeIfAt()) {
			int reg = consumeRegisterPair();
			return content2(0x30, reg);
		}

		if (token == TokenType.INT_LITERAL) {
			int address = consumeIntValue();
			return content3(0x8d, address >> 8, address);
		}

		Location location = getLocation();
		string jcOrTarget = consumeIdentifier();
		string target;
		int jc = getJumpCondition(jcOrTarget);
		if (jc >= 0) {
			consumeComma();
			if (token == TokenType.INT_LITERAL) {
				int address = consumeIntValue();
				return content3(jc + 0x0D, address >> 8, address);
			}

			location = getLocation();
			target = consumeIdentifier();
		} else {
			jc = 0x80;
			target = jcOrTarget;
		}

		return Command.lazyContent3(jc + 0x0D, target, location);
	}

	private Command createJr() {
		consume();
		Location location = getLocation();
		if (token == TokenType.INT_LITERAL) {
			int address = consumeIntValue();
			return Command.autoJump(0x8b, address);
		}

		string jcOrTarget = consumeIdentifier();
		string target;
		int jc = getJumpCondition(jcOrTarget);
		if (jc >= 0) {
			consumeComma();
			location = getLocation();
			if (token == TokenType.INT_LITERAL) {
				int address = consumeIntValue();
				return Command.autoJump(jc + 0x0b, address);
			}
			target = consumeIdentifier();
		} else {
			jc = 0x80;
			target = jcOrTarget;
		}

		return Command.autoJump(jc + 0x0b, target, location);
	}

	private Command createDjnz() {
		consume();
		int reg = consumeWorkRegister();
		consumeComma();
		Location location = getLocation();
		string target = consumeIdentifier();
		return Command.lazyContent2(command(reg, 0xA), target, location);
	}

	private int getJumpCondition(string text) {
		string lower = text.toLower();
		if (lower == "f") return 0x00;
		if (lower == "lt") return 0x10;
		if (lower == "le") return 0x20;
		if (lower == "ule") return 0x30;
		if (lower == "ov") return 0x40;
		if (lower == "mi") return 0x50;
		if (lower == "eq" || lower == "z") return 0x60;
		if (lower == "ult" || lower == "c") return 0x70;
		if (lower == "ge") return 0x90;
		if (lower == "gt") return 0xa0;
		if (lower == "ugt") return 0xb0;
		if (lower == "nov") return 0xc0;
		if (lower == "pl") return 0xd0;
		if (lower == "ne" || lower == "nz") return 0xe0;
		if (lower == "uge" || lower == "nc") return 0xf0;
		return -1;
	}

	private Command createLd() {
		consume();
		if (consumeIfAt()) {
			int dst = consumeRegister();
			consumeComma();
			if (consumeIfHash()) {
				int immediate = consumeIntLiteral();
				return content3(0xE7, dst, immediate);
			}

			int src = consumeRegister();
			if (isWorkReg(dst) && isWorkReg(src)) {
				return content2(0xF3, command(dst, src));
			}

			return content3(0xF5, src, dst);
		}

		int dst = consumeRegister();
		if (consumeIf(TokenType.L_PAREN)) {
			int wr = consumeWorkRegister();
			consume(TokenType.R_PAREN);
			consumeComma();
			int src = consumeWorkRegister();
			return content3(0xD7, command(src, wr), dst);
		}

		consumeComma();
		if (consumeIfHash()) {
			return createLdImmediate(dst);
		}

		if (consumeIfAt()) {
			int src = consumeRegister();
			if (isWorkReg(dst) && isWorkReg(src)) {
				return content2(0xe3, command(dst, src));
			}
			return content3(0xE5, src, dst);
		}

		int src = consumeRegister();
		if (isWorkReg(dst)) {
			if (consumeIf(TokenType.L_PAREN)) {
				int wr = consumeWorkRegister();
				consume(TokenType.R_PAREN);
				return content3(0xC7, command(dst, wr), src);
			}

			return content2(command(dst, 8), src);
		}
		if (isWorkReg(src)) {
			return content2(command(src, 9), dst);
		}
		return content3(0xE4, src, dst);
	}

	private Command createLdImmediate(int dst) {
		return new LdImmediateCommandProvider(dst, this).run();
	}

	private Command createLdX(int highNibble) {
		consume();
		if (consumeIfAt()) {
			int dest = consumeWorkRegisterPair();
			consumeComma();
			int src = consumeWorkRegister();
			return content2(command(highNibble + 1, 2), command(src, dest));
		}
		int dest = consumeWorkRegister();
		consumeComma();
		consumeAt();
		int src = consumeWorkRegisterPair();
		return content2(command(highNibble, 2), command(dest, src));
	}

	private Command createLdXi(int highNibble) {
		consume();
		consumeAt();

		if (token == TokenType.WORK_REGISTER_PAIR) {
			int dest = consumeIntValue();
			consumeComma();
			consumeAt();
			int src = consumeWorkRegister();
			return content2(command(highNibble + 1, 3), command(src, dest));
		}
		int dest = consumeWorkRegister();
		consumeComma();
		consumeAt();
		int src = consumeWorkRegisterPair();
		return content2(command(highNibble, 3), command(dest, src));
	}

	private Command unary(int opCode) {
		consume();
		if (consumeIfAt()) {
			opCode++;
		}
		int register = consumeRegister();
		return content2(opCode, register);
	}

	private Command inc() {
		consume();
		if (consumeIfAt()) {
			int register = consumeRegister();
			return content2(0x21, register);
		}

		int register = consumeRegister();
		if (isWorkReg(register)) {
			return content1(command(register, 0x0E));
		}
		return content2(0x20, register);
	}

	private Command singleByteCommand(int opCode) {
		consume();
		return content1(opCode);
	}

	private bool isWorkReg(int reg) {
		return (reg & 0xF0) == WORK_REG;
	}

	private int consumeWorkRegister() {
		return consumeIntValue(TokenType.WORK_REGISTER);
	}

	private int consumeWorkRegisterPair() {
		return consumeIntValue(TokenType.WORK_REGISTER_PAIR);
	}

	private bool consumeIfAt() {
		return consumeIf(TokenType.AT);
	}

	private bool consumeIfHash() {
		return consumeIf(TokenType.HASH);
	}

	private bool consumeIf(TokenType type) {
		if (token != type) {
			return false;
		}

		consume();
		return true;
	}

	private void consumeAt() {
		consume(TokenType.AT);
	}

	private void consumeComma() {
		consume(TokenType.COMMA);
	}

	private void consume(TokenType type) {
		expectType(type);
		consume();
	}

	private int consumeRegisterPair() {
		return consumeRegister(true);
	}

	private int consumeRegister() {
		return consumeRegister(false);
	}

	private int consumeRegister(bool pair) {
		if (token == TokenType.INT_LITERAL) {
			return consumeIntValue();
		}

		if (pair && token == TokenType.WORK_REGISTER_PAIR
		    || !pair && token == TokenType.WORK_REGISTER) {
			return WORK_REG + (consumeIntValue() & 0xF);
		}

		return consumeConstant("expected register");
	}

	private int consumeConstant(string msg) {
		Location location = getLocation();
		if (token == TokenType.IDENTIFIER) {
			string name = consumeText();
			if (name in constToValue) {
				return constToValue[name];
			}
		}
		throw new SyntaxException(msg, location);
	}

	private Command createSrp() {
		consume();
		consume(TokenType.HASH);
		int value = consumeIntLiteral();
		return content2(0x31, value);
	}

	private string consumeIdentifier() {
		expectType(TokenType.IDENTIFIER);
		return consumeText();
	}

	private string consumeText() {
		string text = getText();
		consume();
		return text;
	}

	private string getText() {
		return lexer.getText();
	}

	private int consumeIntLiteral() {
		if (token == TokenType.INT_LITERAL) {
			return consumeIntValue();
		}
		return consumeConstant("expected int literal or constant");
	}

	private int consumeIntValue(TokenType type) {
		expectType(type);
		return consumeIntValue();
	}

	private int consumeIntValue() {
		int value = lexer.getIntValue();
		consume();
		return value;
	}

	private Location getLocation() {
		return lexer.getLocation();
	}

	private void consume() {
		do {
			token = lexer.next();
		} while (token == TokenType.WHITESPACE || token == TokenType.COMMENT);
	}

	private void expectType(TokenType type) {
		if (token != type) {
			throw new SyntaxException("Expected " ~ to!string(type) ~ " but got " ~ to!string(token), getLocation());
		}
	}

	private void appendString(string text, ref Appender!(Command[]) commands) {
		wstring characters = to!wstring(text);
		int length = cast(int)characters.length;
		Utils.assertTrue(length > 0);
		byte[] bytes = new byte[length];
		foreach (i, ch; characters) {
			bytes[i] = cast(byte)ch;
		}
		commands.put(Command.content(bytes));
	}

	// Inner classes translated to private classes
	private class BinaryImmediateCommandProvider {
		int opCode;
		int dst;
		Parser parser;

		this(int opCode, int dst, Parser parser) {
			this.opCode = opCode;
			this.dst = dst;
			this.parser = parser;
		}

		public Command run() {
			if (parser.token == TokenType.INT_LITERAL) {
				int immediate = parser.consumeIntValue();
				return parser.content3(opCode, dst, immediate);
			}

			Location location = parser.getLocation();
			string identifier = parser.consumeIdentifier();
			if (parser.consumeIf(TokenType.L_PAREN)) {
				identifier ~= "(" ~ parser.consumeIdentifier() ~ ")";
				parser.consume(TokenType.R_PAREN);
			}
			if (identifier in parser.constToValue) {
				int value = parser.constToValue[identifier];
				return parser.content3(opCode, dst, value);
			}

			return Command.lazyContent3(opCode, dst, identifier, location);
		}
	}

	private class LdImmediateCommandProvider {
		int dst;
		Parser parser;

		this(int dst, Parser parser) {
			this.dst = dst;
			this.parser = parser;
		}

		public Command run() {
			if (parser.token == TokenType.INT_LITERAL) {
				int immediate = parser.consumeIntValue();
				if (parser.isWorkReg(dst)) {
					return parser.content2(parser.command(dst, 0xC), immediate);
				}
				return parser.content3(0xE6, dst, immediate);
			}

			Location location = parser.getLocation();
			string identifier = parser.consumeIdentifier();
			if (parser.consumeIf(TokenType.L_PAREN)) {
				identifier ~= "(" ~ parser.consumeIdentifier() ~ ")";
				parser.consume(TokenType.R_PAREN);
			}
			if (identifier in parser.constToValue) {
				int value = parser.constToValue[identifier];
				if (parser.isWorkReg(dst)) {
					return parser.content2(parser.command(dst, 0xC), value);
				}
				return parser.content3(0xE6, dst, value);
			}

			if (parser.isWorkReg(dst)) {
				return Command.lazyContent2(parser.command(dst, 0xC), identifier, location);
			}
			return Command.lazyContent3(0xE6, dst, identifier, location);
		}
	}
}
