package com.syntevo.z8asm;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import org.jetbrains.annotations.*;

import static com.syntevo.z8asm.Command.*;

/**
 * @author Thomas Singer
 */
public final class Parser {

	private static final int WORK_REG = 0xE0;

	@NotNull
	public static List<Command> parse(@NotNull Path file) throws IOException {
		final List<Command> commands;
		try (BufferedReader reader = Files.newBufferedReader(file)) {
			final Lexer lexer = new Lexer(() -> {
				try {
					return reader.read();
				}
				catch (IOException ex) {
					throw new UncheckedIOException(ex);
				}
			});
			final Parser parser = new Parser(lexer);
			commands = parser.process();
		}
		return commands;
	}

	private final Map<String, Integer> constToValue = new HashMap<>();
	private final Lexer lexer;

	private TokenType token;

	public Parser(@NotNull Lexer lexer) {
		this.lexer = lexer;
	}

	public List<Command> process() {
		consume();

		final List<Command> commands = new ArrayList<>();
		process(TokenType.EOF, commands);
		return commands;
	}

	private void process(@NotNull TokenType allowedEndType, @NotNull List<Command> commands) {
		while (token != allowedEndType) {
			if (token == TokenType.LABEL) {
				if (allowedEndType == TokenType.END) {
					throw new SyntaxException("Labels inside .repeat .end blocks are not allowed", getLocation());
				}
				final String label = getText();
				commands.add(label(label, getLocation()));
				consume();
			}

			switch (token) {
			case LINE_BREAK -> {
				consume();
				continue;
			}
			case CONST -> handleConst();
			case DATA -> {
				appendData(commands);
				continue;
			}
			case REPEAT -> handleRepeat(commands);
			case ORG -> commands.add(org());

			case DEC -> commands.add(unary(0x00));
			case RLC -> commands.add(unary(0x10));
			case INC -> commands.add(inc());
			case SRP -> commands.add(createSrp());
			case DA -> commands.add(unary(0x40));
			case POP -> commands.add(unary(0x50));
			case COM -> commands.add(unary(0x60));
			case PUSH -> commands.add(unary(0x70));
			case DECW -> commands.add(unary(0x80));
			case RL -> commands.add(unary(0x90));
			case INCW -> commands.add(unary(0xA0));
			case CLR -> commands.add(unary(0xB0));
			case RRC -> commands.add(unary(0xC0));
			case SRA -> commands.add(unary(0xD0));
			case RR -> commands.add(unary(0xE0));
			case SWAP -> commands.add(unary(0xF0));

			case ADD -> commands.add(createBinary(0x0));
			case ADC -> commands.add(createBinary(0x1));
			case SUB -> commands.add(createBinary(0x2));
			case SBC -> commands.add(createBinary(0x3));
			case OR -> commands.add(createBinary(0x4));
			case AND -> commands.add(createBinary(0x5));
			case TCM -> commands.add(createBinary(0x6));
			case TM -> commands.add(createBinary(0x7));
			case CP -> commands.add(createBinary(0xA));
			case XOR -> commands.add(createBinary(0xB));

			case LD -> commands.add(createLd());
			case LDC -> commands.add(createLdX(0xC));
			case LDE -> commands.add(createLdX(0x8));
			case LDCI -> commands.add(createLdXi(0xC));
			case LDEI -> commands.add(createLdXi(0x8));

			case CALL -> commands.add(createCall());
			case JP -> commands.add(createJp());
			case JR -> commands.add(createJr());
			case DJNZ -> commands.add(createDjnz());

			case DI -> commands.add(singleByteCommand(0x8F));
			case EI -> commands.add(singleByteCommand(0x9F));
			case RET -> commands.add(singleByteCommand(0xAF));
			case IRET -> commands.add(singleByteCommand(0xBF));
			case RCF -> commands.add(singleByteCommand(0xCF));
			case SCF -> commands.add(singleByteCommand(0xDF));
			case CCF -> commands.add(singleByteCommand(0xEF));
			case NOP -> commands.add(singleByteCommand(0xFF));

			default -> throw new SyntaxException("unexpected " + token + " " + getText(), getLocation());
			}

			if (token == TokenType.EOF) {
				break;
			}

			consume(TokenType.LINE_BREAK);
		}
	}

	private void handleConst() {
		consume();
		final String name = consumeIdentifier();
		if (constToValue.containsKey(name)) {
			throw new SyntaxException("Duplicate definition of " + name, getLocation());
		}

		if (!"=".equals(consumeIdentifier())) {
			throw new SyntaxException("Expected =", getLocation());
		}

		final int value = consumeIntLiteral();
		constToValue.put(name, value);
	}

	private void appendData(List<Command> commands) {
		consume();
		boolean isFirst = true;
		while (true) {
			if (token == TokenType.EOF || token == TokenType.LINE_BREAK) {
				if (isFirst) {
					throw new SyntaxException("Expected at least one byte", getLocation());
				}
				break;
			}

			isFirst = false;

			if (token == TokenType.INT_LITERAL) {
				final int size = lexer.getSize();
				final int value = consumeIntValue();
				if (size == 1) {
					commands.add(Command.content1(value));
				}
				else {
					commands.add(Command.content2(value >> 8, value));
				}
			}
			else if (token == TokenType.IDENTIFIER) {
				final Location location = getLocation();
				final String text = consumeText();
				commands.add(Command.lazyContent2(0, text, location));
			}
			else if (token == TokenType.STRING) {
				final String text = consumeText();
				appendString(text, commands);
			}
			else if (token == TokenType.LENGTH_STRING) {
				final String text = consumeText();
				commands.add(Command.content1(text.length()));
				appendString(text, commands);
			}
			else {
				throw new SyntaxException("unsupported constant", getLocation());
			}
		}
	}

	@NotNull
	private Command org() {
		consume();
		final Location location = getLocation();
		return Command.org(consumeIntLiteral(), location);
	}

	private void handleRepeat(@NotNull List<Command> commands) {
		consume(TokenType.REPEAT);

		int repeatTimes = consumeIntLiteral();
		if (repeatTimes <= 1) {
			throw new SyntaxException("Expected a value > 1", getLocation());
		}

		final int repeatStart = commands.size();

		process(TokenType.END, commands);
		consume(TokenType.END);

		final List<Command> repeatingCommands = new ArrayList<>(commands.subList(repeatStart, commands.size()));
		while (--repeatTimes > 0) {
			commands.addAll(repeatingCommands);
		}
	}

	private Command createBinary(int highNibble) {
		consume();
		if (consumeIfAt()) {
			final int dst = consumeRegister();
			consumeComma();
			if (consumeIfHash()) {
				final int value = consumeIntLiteral();
				return content3(command(highNibble, 7), dst, value);
			}
			final int src = consumeRegister();
			return content3(command(highNibble, 5), src, dst);
		}

		final int dst = consumeRegister();
		consumeComma();
		if (consumeIfHash()) {
			final int value = consumeIntLiteral();
			return content3(command(highNibble, 6), dst, value);
		}
		if (isWorkReg(dst) && consumeIfAt()) {
			final int src = consumeWorkRegister();
			return content2(command(highNibble, 3), command(dst, src));
		}
		final int src = consumeRegister();
		if (isWorkReg(src) && isWorkReg(dst)) {
			return content2(command(highNibble, 2), command(dst, src));
		}
		return content3(command(highNibble, 4), src, dst);
	}

	private Command createCall() {
		consume();
		if (consumeIfAt()) {
			final int reg = consumeRegisterPair();
			return content2(0xD4, reg);
		}

		if (token == TokenType.INT_LITERAL) {
			final int address = consumeIntValue();
			return content3(0xD6, address >> 8, address);
		}

		final Location location = getLocation();
		final String target = consumeIdentifier();
		return lazyContent3(0xD6, target, location);
	}

	private Command createJp() {
		consume();
		if (consumeIfAt()) {
			final int reg = consumeRegisterPair();
			return content2(0x30, reg);
		}

		if (token == TokenType.INT_LITERAL) {
			final int address = consumeIntValue();
			return content3(0x8d, address >> 8, address);
		}

		Location location = getLocation();
		final String jcOrTarget = consumeIdentifier();
		final String target;
		int jc = getJumpCondition(jcOrTarget);
		if (jc >= 0) {
			consumeComma();
			if (token == TokenType.INT_LITERAL) {
				final int address = consumeIntValue();
				return content3(jc + 0x0D, address >> 8, address);
			}

			location = getLocation();
			target = consumeIdentifier();
		}
		else {
			jc = 0x80;
			target = jcOrTarget;
		}

		return lazyContent3(jc + 0x0D, target, location);
	}

	private Command createJr() {
		consume();
		Location location = getLocation();
		final String jcOrTarget = consumeIdentifier();
		final String target;
		int jc = getJumpCondition(jcOrTarget);
		if (jc >= 0) {
			consumeComma();
			location = getLocation();
			target = consumeIdentifier();
		}
		else {
			jc = 0x80;
			target = jcOrTarget;
		}

		return lazyContent2(jc + 0x0b, target, location);
	}

	private Command createDjnz() {
		consume();
		final int reg = consumeWorkRegister();
		consumeComma();
		final Location location = getLocation();
		final String target = consumeIdentifier();
		return lazyContent2(command(reg, 0xA), target, location);
	}

	private int getJumpCondition(String text) {
		return switch (text.toLowerCase()) {
			case "f" -> 0x00;
			case "lt" -> 0x10;
			case "le" -> 0x20;
			case "ule" -> 0x30;
			case "ov" -> 0x40;
			case "mi" -> 0x50;
			case "eq",
			     "z" -> 0x60;
			case "ult",
			     "c" -> 0x70;
			case "ge" -> 0x90;
			case "gt" -> 0xa0;
			case "ugt" -> 0xb0;
			case "nov" -> 0xc0;
			case "pl" -> 0xd0;
			case "ne",
			     "nz" -> 0xe0;
			case "uge",
			     "nc" -> 0xf0;
			default -> -1;
		};
	}

	private Command createLd() {
		consume();
		if (consumeIfAt()) {
			final int dst = consumeRegister();
			consumeComma();
			if (consumeIfHash()) {
				final int immediate = consumeIntLiteral();
				return content3(0xE7, dst, immediate);
			}

			final int src = consumeRegister();
			if (isWorkReg(dst) && isWorkReg(src)) {
				return content2(0xF3, command(dst, src));
			}

			return content3(0xF5, src, dst);
		}

		final int dst = consumeRegister();
		if (consumeIf(TokenType.L_PAREN)) {
			final int wr = consumeWorkRegister();
			consume(TokenType.R_PAREN);
			consumeComma();
			final int src = consumeWorkRegister();
			return content3(0xD7, command(src, wr), dst);
		}

		consumeComma();
		if (consumeIfHash()) {
			final int immediate = consumeIntLiteral();
			if (isWorkReg(dst)) {
				return content2(command(dst, 0xC), immediate);
			}
			return content3(0xE6, dst, immediate);
		}

		if (consumeIfAt()) {
			final int src = consumeRegister();
			if (isWorkReg(dst) && isWorkReg(src)) {
				return content2(0xe3, command(dst, src));
			}
			return content3(0xE5, src, dst);
		}

		final int src = consumeRegister();
		if (isWorkReg(dst)) {
			if (consumeIf(TokenType.L_PAREN)) {
				final int wr = consumeWorkRegister();
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

	private Command createLdX(int highNibble) {
		consume();
		if (consumeIfAt()) {
			final int dest = consumeWorkRegisterPair();
			consumeComma();
			final int src = consumeWorkRegister();
			return content2(command(highNibble + 1, 2), command(src, dest));
		}
		final int dest = consumeWorkRegister();
		consumeComma();
		consumeAt();
		final int src = consumeWorkRegisterPair();
		return content2(command(highNibble, 2), command(dest, src));
	}

	private Command createLdXi(int highNibble) {
		consume();
		consumeAt();

		if (token == TokenType.WORK_REGISTER_PAIR) {
			final int dest = consumeIntValue();
			consumeComma();
			consumeAt();
			final int src = consumeWorkRegister();
			return content2(command(highNibble + 1, 3), command(src, dest));
		}
		final int dest = consumeWorkRegister();
		consumeComma();
		consumeAt();
		final int src = consumeWorkRegisterPair();
		return content2(command(highNibble, 3), command(dest, src));
	}

	private Command unary(int opCode) {
		consume();
		if (consumeIfAt()) {
			opCode++;
		}
		final int register = consumeRegister();
		return content2(opCode, register);
	}

	private Command inc() {
		consume();
		if (consumeIfAt()) {
			final int register = consumeRegister();
			return content2(0x21, register);
		}

		final int register = consumeRegister();
		if (isWorkReg(register)) {
			return content1(command(register, 0x0E));
		}
		return content2(0x20, register);
	}

	@NotNull
	private Command singleByteCommand(int opCode) {
		consume();
		return content1(opCode);
	}

	private boolean isWorkReg(int reg) {
		return (reg & 0xF0) == WORK_REG;
	}

	private int consumeWorkRegister() {
		return consumeIntValue(TokenType.WORK_REGISTER);
	}

	private int consumeWorkRegisterPair() {
		return consumeIntValue(TokenType.WORK_REGISTER_PAIR);
	}

	private boolean consumeIfAt() {
		return consumeIf(TokenType.AT);
	}

	private boolean consumeIfHash() {
		return consumeIf(TokenType.HASH);
	}

	private boolean consumeIf(TokenType type) {
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

	private int consumeRegister(boolean pair) {
		if (token == TokenType.INT_LITERAL) {
			return consumeIntValue();
		}

		if (pair && token == TokenType.WORK_REGISTER_PAIR
		    || !pair && token == TokenType.WORK_REGISTER) {
			return WORK_REG + (consumeIntValue() & 0xF);
		}

		return consumeConstant("expected register");
	}

	@NotNull
	private Integer consumeConstant(String msg) {
		final Location location = getLocation();
		if (token == TokenType.IDENTIFIER) {
			final Integer value = constToValue.get(consumeText());
			if (value != null) {
				return value;
			}
		}
		throw new SyntaxException(msg, location);
	}

	private Command createSrp() {
		consume();
		consume(TokenType.HASH);
		final int value = consumeIntLiteral();
		return content2(0x31, value);
	}

	@NotNull
	private String consumeIdentifier() {
		expectType(TokenType.IDENTIFIER);
		return consumeText();
	}

	@NotNull
	private String consumeText() {
		final String text = getText();
		consume();
		return text;
	}

	@NotNull
	private String getText() {
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
		final int value = lexer.getIntValue();
		consume();
		return value;
	}

	private Location getLocation() {
		return lexer.getLocation();
	}

	private void consume() {
		do {
			token = lexer.next();
		}
		while (token == TokenType.WHITESPACE || token == TokenType.COMMENT);
	}

	private void expectType(TokenType type) {
		if (token != type) {
			throw new SyntaxException("Expected " + type + " but got " + token, getLocation());
		}
	}

	private void appendString(@NotNull String text, @NotNull List<Command> commands) {
		int start = 0;
		final int length = text.length();
		for (int i = 0; i < length; i++) {
			if (i - start == 2) {
				commands.add(Command.content3(text.charAt(i - 2),
				                              text.charAt(i - 1),
				                              text.charAt(i)));
				start = i + 1;
			}
		}
		while (start < length) {
			commands.add(Command.content1(text.charAt(start)));
			start++;
		}
	}

	private static int command(int highNibble, int lowNibble) {
		return ((highNibble & 0xF) << 4) + (lowNibble & 0xF);
	}
}
