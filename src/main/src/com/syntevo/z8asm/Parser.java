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
					final int read = reader.read();
					if (read < 0) {
						return OptionalInt.empty();
					}
					return OptionalInt.of(read);
				}
				catch (IOException ex) {
					throw new UncheckedIOException(ex);
				}
			});
			final Parser parser = new Parser(lexer);
			commands = parser.parse();
		}
		return commands;
	}

	private final Map<String, Integer> constToValue = new HashMap<>();
	private final Lexer lexer;

	private Token token;

	public Parser(@NotNull Lexer lexer) {
		this.lexer = lexer;
	}

	public List<Command> parse() {
		consume();

		final List<Command> commands = new ArrayList<>();
		parse(TokenType.EOF, commands);
		return commands;
	}

	private void parse(@NotNull TokenType allowedEndType, @NotNull List<Command> commands) {
		while (token.type() != allowedEndType) {
			if (token.type() == TokenType.LABEL) {
				if (allowedEndType == TokenType.END) {
					throw new SyntaxException("Labels inside .repeat .end blocks are not allowed", token.location());
				}
				final String label = token.text();
				commands.add(new Label(label, token.location()));
				consume();
			}

			switch (token.type()) {
			case LINE_BREAK -> {
				consume();
				continue;
			}
			case ALIGN -> commands.add(handleAlign());
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

			default -> throw new SyntaxException("unexpected " + token + " " + token.text(), token.location());
			}

			if (token.type() == TokenType.EOF) {
				break;
			}

			consume(TokenType.LINE_BREAK);
		}
	}

	private Command handleAlign() {
		consume();
		final int alignment = consumeIntLiteral();
		if (alignment < 2 || !isPowerOf2(alignment)) {
			throw new SyntaxException("Expected a number the power of 2 (2, 4, 8, ...", token.location());
		}

		consumeComma();
		final int fillByte = consumeIntLiteral();
		return new Align(alignment, (byte)fillByte);
	}

	private void handleConst() {
		consume();
		final String name = consumeIdentifier();
		if (constToValue.containsKey(name)) {
			throw new SyntaxException("Duplicate definition of " + name, token.location());
		}

		if (!"=".equals(consumeIdentifier())) {
			throw new SyntaxException("Expected =", token.location());
		}

		final int value = consumeIntLiteral();
		constToValue.put(name, value);
	}

	private void appendData(List<Command> commands) {
		consume();
		boolean isFirst = true;
		while (true) {
			if (token.type() == TokenType.EOF || token.type() == TokenType.LINE_BREAK) {
				if (isFirst) {
					throw new SyntaxException("Expected at least one byte", token.location());
				}
				break;
			}

			isFirst = false;

			if (token.type() == TokenType.INT_LITERAL) {
				final int size = token.size();
				final int value = consumeIntValue();
				if (size == 1) {
					commands.add(new Content(value));
				}
				else {
					commands.add(new Content(value >> 8, value));
				}
			}
			else if (token.type() == TokenType.IDENTIFIER) {
				final Location location = token.location();
				final String text = consumeText();
				commands.add(new LazyContent2(0, text, location));
			}
			else if (token.type() == TokenType.STRING) {
				final Location location = token.location();
				final String text = consumeText();
				if (text.isEmpty()) {
					throw new SyntaxException("Strings must not be empty.", location);
				}
				appendString(text, commands);
			}
			else if (token.type() == TokenType.LENGTH_STRING) {
				final String text = consumeText();
				commands.add(new Content(text.length()));
				appendString(text, commands);
			}
			else {
				throw new SyntaxException("unsupported constant", token.location());
			}
		}
	}

	@NotNull
	private Command org() {
		consume();
		final Location location = token.location();
		final int address = consumeIntLiteral();
		return new Org(address, location);
	}

	private void handleRepeat(@NotNull List<Command> commands) {
		consume(TokenType.REPEAT);

		int repeatTimes = consumeIntLiteral();
		if (repeatTimes <= 1) {
			throw new SyntaxException("Expected a value > 1", token.location());
		}

		final int repeatStart = commands.size();

		parse(TokenType.END, commands);
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
				return createBinaryImmediate(command(highNibble, 7), dst);
			}
			final int src = consumeRegister();
			return new Content(command(highNibble, 5), src, dst);
		}

		final int dst = consumeRegister();
		consumeComma();
		if (consumeIfHash()) {
			return createBinaryImmediate(command(highNibble, 6), dst);
		}
		if (isWorkReg(dst) && consumeIfAt()) {
			final int src = consumeWorkRegister();
			return new Content(command(highNibble, 3), command(dst, src));
		}
		final int src = consumeRegister();
		if (isWorkReg(src) && isWorkReg(dst)) {
			return new Content(command(highNibble, 2), command(dst, src));
		}
		return new Content(command(highNibble, 4), src, dst);
	}

	@NotNull
	private Command createBinaryImmediate(int opCode, int dst) {
		final ImmediateCommandProvider provider = new ImmediateCommandProvider() {
			@NotNull
			@Override
			public Command create(int immediate) {
				return new Content(opCode, dst, immediate);
			}

			@NotNull
			@Override
			public Command createLazy(String identifier, Location location) {
				return new LazyContent3(opCode, dst, identifier, location);
			}
		};
		return provider.run();
	}

	private Command createCall() {
		consume();
		if (consumeIfAt()) {
			final int reg = consumeRegisterPair();
			return new Content(0xD4, reg);
		}

		if (token.type() == TokenType.INT_LITERAL) {
			final int address = consumeIntValue();
			return new Content(0xD6, address >> 8, address);
		}

		final Location location = token.location();
		final String target = consumeIdentifier();
		return new LazyContent3(0xD6, target, location);
	}

	private Command createJp() {
		consume();
		if (consumeIfAt()) {
			final int reg = consumeRegisterPair();
			return new Content(0x30, reg);
		}

		if (token.type() == TokenType.INT_LITERAL) {
			final int address = consumeIntValue();
			return new Content(0x8d, address >> 8, address);
		}

		Location location = token.location();
		final String jcOrTarget = consumeIdentifier();
		final String target;
		int jc = getJumpCondition(jcOrTarget);
		if (jc >= 0) {
			consumeComma();
			if (token.type() == TokenType.INT_LITERAL) {
				final int address = consumeIntValue();
				return new Content(jc + 0x0D, address >> 8, address);
			}

			location = token.location();
			target = consumeIdentifier();
		}
		else {
			jc = 0x80;
			target = jcOrTarget;
		}

		return new LazyContent3(jc + 0x0D, target, location);
	}

	private Command createJr() {
		consume();
		Location location = token.location();
		if (token.type() == TokenType.INT_LITERAL) {
			final int address = consumeIntValue();
			return new AutoJumpAddress((byte)0x8b, address);
		}

		final String jcOrTarget = consumeIdentifier();
		final String target;
		int jc = getJumpCondition(jcOrTarget);
		if (jc >= 0) {
			consumeComma();
			location = token.location();
			if (token.type() == TokenType.INT_LITERAL) {
				final int address = consumeIntValue();
				return new AutoJumpAddress((byte)(jc + 0x0b), address);
			}
			target = consumeIdentifier();
		}
		else {
			jc = 0x80;
			target = jcOrTarget;
		}

		return new AutoJumpString((byte)(jc + 0x0b), target, location);
	}

	private Command createDjnz() {
		consume();
		final int reg = consumeWorkRegister();
		consumeComma();
		final Location location = token.location();
		final String target = consumeIdentifier();
		return new LazyContent2(command(reg, 0xA), target, location);
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
				return new Content(0xE7, dst, immediate);
			}

			final int src = consumeRegister();
			if (isWorkReg(dst) && isWorkReg(src)) {
				return new Content(0xF3, command(dst, src));
			}

			return new Content(0xF5, src, dst);
		}

		final int dst = consumeRegister();
		if (consumeIf(TokenType.L_PAREN)) {
			final int wr = consumeWorkRegister();
			consume(TokenType.R_PAREN);
			consumeComma();
			final int src = consumeWorkRegister();
			return new Content(0xD7, command(src, wr), dst);
		}

		consumeComma();
		if (consumeIfHash()) {
			return createLdImmediate(dst);
		}

		if (consumeIfAt()) {
			final int src = consumeRegister();
			if (isWorkReg(dst) && isWorkReg(src)) {
				return new Content(0xe3, command(dst, src));
			}
			return new Content(0xE5, src, dst);
		}

		final int src = consumeRegister();
		if (isWorkReg(dst)) {
			if (consumeIf(TokenType.L_PAREN)) {
				final int wr = consumeWorkRegister();
				consume(TokenType.R_PAREN);
				return new Content(0xC7, command(dst, wr), src);
			}

			return new Content(command(dst, 8), src);
		}
		if (isWorkReg(src)) {
			return new Content(command(src, 9), dst);
		}
		return new Content(0xE4, src, dst);
	}

	@NotNull
	private Command createLdImmediate(int dst) {
		final ImmediateCommandProvider provider = new ImmediateCommandProvider() {
			@NotNull
			@Override
			public Command create(int immediate) {
				if (isWorkReg(dst)) {
					return new Content(command(dst, 0xC), immediate);
				}
				return new Content(0xE6, dst, immediate);
			}

			@NotNull
			@Override
			public Command createLazy(String identifier, Location location) {
				if (isWorkReg(dst)) {
					return new LazyContent2(command(dst, 0xC), identifier, location);
				}
				return new LazyContent3(0xE6, dst, identifier, location);
			}
		};
		return provider.run();
	}

	private Command createLdX(int highNibble) {
		consume();
		if (consumeIfAt()) {
			final int dest = consumeWorkRegisterPair();
			consumeComma();
			final int src = consumeWorkRegister();
			return new Content(command(highNibble + 1, 2), command(src, dest));
		}
		final int dest = consumeWorkRegister();
		consumeComma();
		consumeAt();
		final int src = consumeWorkRegisterPair();
		return new Content(command(highNibble, 2), command(dest, src));
	}

	private Command createLdXi(int highNibble) {
		consume();
		consumeAt();

		if (token.type() == TokenType.WORK_REGISTER_PAIR) {
			final int dest = consumeIntValue();
			consumeComma();
			consumeAt();
			final int src = consumeWorkRegister();
			return new Content(command(highNibble + 1, 3), command(src, dest));
		}
		final int dest = consumeWorkRegister();
		consumeComma();
		consumeAt();
		final int src = consumeWorkRegisterPair();
		return new Content(command(highNibble, 3), command(dest, src));
	}

	private Command unary(int opCode) {
		consume();
		if (consumeIfAt()) {
			opCode++;
		}
		final int register = consumeRegister();
		return new Content(opCode, register);
	}

	private Command inc() {
		consume();
		if (consumeIfAt()) {
			final int register = consumeRegister();
			return new Content(0x21, register);
		}

		final int register = consumeRegister();
		if (isWorkReg(register)) {
			return new Content(command(register, 0x0E));
		}
		return new Content(0x20, register);
	}

	@NotNull
	private Command singleByteCommand(int opCode) {
		consume();
		return new Content(opCode);
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
		if (token.type() != type) {
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
		if (token.type() == TokenType.INT_LITERAL) {
			return consumeIntValue();
		}

		if (pair && token.type() == TokenType.WORK_REGISTER_PAIR
		    || !pair && token.type() == TokenType.WORK_REGISTER) {
			return WORK_REG + (consumeIntValue() & 0xF);
		}

		return consumeConstant("expected register");
	}

	@NotNull
	private Integer consumeConstant(String msg) {
		final Location location = token.location();
		if (token.type() == TokenType.IDENTIFIER) {
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
		return new Content(0x31, value);
	}

	@NotNull
	private String consumeIdentifier() {
		expectType(TokenType.IDENTIFIER);
		return consumeText();
	}

	@NotNull
	private String consumeText() {
		final String text = token.text();
		consume();
		return text;
	}

	private int consumeIntLiteral() {
		if (token.type() == TokenType.INT_LITERAL) {
			return consumeIntValue();
		}
		return consumeConstant("expected int literal or constant");
	}

	private int consumeIntValue(TokenType type) {
		expectType(type);
		return consumeIntValue();
	}

	private int consumeIntValue() {
		final int value = token.intValue();
		consume();
		return value;
	}

	private void consume() {
		do {
			token = lexer.next();
		}
		while (token.type() == TokenType.WHITESPACE || token.type() == TokenType.COMMENT);
	}

	private void expectType(TokenType type) {
		if (token.type() != type) {
			throw new SyntaxException("Expected " + type + " but got " + token, token.location());
		}
	}

	private void appendString(@NotNull String text, @NotNull List<Command> commands) {
		final int length = text.length();
		Utils.assertTrue(length > 0);
		final byte[] bytes = new byte[length];
		for (int i = 0; i < length; i++) {
			bytes[i] = (byte)text.charAt(i);
		}
		commands.add(new Content(bytes));
	}

	private static boolean isPowerOf2(int alignment) {
		return (alignment & (alignment - 1)) == 0;
	}

	private static int command(int highNibble, int lowNibble) {
		return ((highNibble & 0xF) << 4) + (lowNibble & 0xF);
	}

	private abstract class ImmediateCommandProvider {
		@NotNull
		protected abstract Command create(int immediate);

		@NotNull
		protected abstract Command createLazy(String identifier, Location location);

		@NotNull
		public final Command run() {
			if (token.type() == TokenType.INT_LITERAL) {
				final int immediate = consumeIntValue();
				return create(immediate);
			}

			final Location location = token.location();
			String identifier = consumeIdentifier();
			if (consumeIf(TokenType.L_PAREN)) {
				identifier += "(" + consumeIdentifier() + ")";
				consume(TokenType.R_PAREN);
			}
			final Integer value = constToValue.get(identifier);
			if (value != null) {
				return create(value);
			}

			return createLazy(identifier, location);
		}
	}
}
