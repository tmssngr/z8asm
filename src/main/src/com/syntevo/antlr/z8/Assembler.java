package com.syntevo.antlr.z8;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Thomas Singer
 */
public final class Assembler extends Z8AsmBaseListener {

	// Constants ==============================================================

	private static final int WORKING_REGISTER_HIGH_NIBBLE = 0xE0;
	private static final Map<String, Integer> JP_CONDITIONS = createJpConditionsMap();
	private static final Map<String, Integer> REGISTER_CONSTANTS = createRegisterConstantsMap();

	// Fields =================================================================

	private final Map<String, Integer> labels = new HashMap<>();

	private Output output;
	private int pc;
	private boolean abortForUnknownLabel;
	private boolean details;

	// Setup ==================================================================

	public Assembler() {
	}

	// Implemented ============================================================

	@Override
	public void visitErrorNode(ErrorNode node) {
		System.err.println(node.getText());
	}

	@Override
	public void enterCode(Z8AsmParser.CodeContext ctx) {
		output = new Output();
	}

	@Override
	public void enterCommand(Z8AsmParser.CommandContext ctx) {
		pc = output.getPc();
	}

	@Override
	public void exitCommand(Z8AsmParser.CommandContext ctx) {
		final int pc = output.getPc();
		if (pc == this.pc) {
			throw new IllegalStateException("command not processed " + ctx.getText());
		}

		if (details) {
			System.out.println(ctx.getText().strip());
			output.printFrom(this.pc);
			System.out.println();
		}
	}

	@Override
	public void enterDataItem(Z8AsmParser.DataItemContext ctx) {
		final TerminalNode byteNode = ctx.Byte();
		if (byteNode != null) {
			write(parseByte(byteNode));
			return;
		}

		final Z8AsmParser.AddressContext addressCtx = ctx.address();
		if (addressCtx != null) {
			final int address = parseAddress(addressCtx);
			write(address >> 8);
			write(address);
			return;
		}

		final TerminalNode stringNode = ctx.String();
		if (stringNode != null) {
			String text = stringNode.getText();
			final boolean leadingLengthByte = text.startsWith("l") || text.startsWith("L");
			if (leadingLengthByte) {
				text = text.substring(1);
			}

			final String string = parseString(text, "\"", ctx);

			if (leadingLengthByte) {
				if (string.length() > 255) {
					throw new SyntaxException("String too long - must be < 256", ctx);
				}

				write(string.length());
			}

			for (int i = 0; i < string.length(); i++) {
				write(string.charAt(i));
			}

			return;
		}

		final TerminalNode charNode = ctx.Char();
		if (charNode != null) {
			String text = charNode.getText();
			final String string = parseString(text, "'", ctx);
			if (string.length() != 1) {
				throw new SyntaxException("Char must have length of 1", ctx);
			}

			write(string.charAt(0));
			return;
		}

		throw new UnsupportedOperationException();
	}

	@Override
	public void enterLabelDefinition(Z8AsmParser.LabelDefinitionContext ctx) {
		final String label = ctx.Identifier().getText();
		labels.put(label, output.getPc());
	}

	@Override
	public void enterDi(Z8AsmParser.DiContext ctx) {
		command1(0x8F);
	}

	@Override
	public void enterEi(Z8AsmParser.EiContext ctx) {
		command1(0x9F);
	}

	@Override
	public void enterRet(Z8AsmParser.RetContext ctx) {
		command1(0xAF);
	}

	@Override
	public void enterIret(Z8AsmParser.IretContext ctx) {
		command1(0xBF);
	}

	@Override
	public void enterRcf(Z8AsmParser.RcfContext ctx) {
		command1(0xCF);
	}

	@Override
	public void enterScf(Z8AsmParser.ScfContext ctx) {
		command1(0xDF);
	}

	@Override
	public void enterCcf(Z8AsmParser.CcfContext ctx) {
		command1(0xEF);
	}

	@Override
	public void enterNop(Z8AsmParser.NopContext ctx) {
		command1(0xFF);
	}

	@Override
	public void enterCall(Z8AsmParser.CallContext ctx) {
		final Z8AsmParser.AddressContext address = ctx.address();
		if (address != null) {
			command3(0xD6, parseAddress(address));
			return;
		}

		command2(0xD4, parseRegisterPair(ctx.iregisterPair()));
	}

	@Override
	public void enterClr(Z8AsmParser.ClrContext ctx) {
		handleRegisterCommand(0xB0, ctx.registerOrIregister());
	}

	@Override
	public void enterCom(Z8AsmParser.ComContext ctx) {
		handleRegisterCommand(0x60, ctx.registerOrIregister());
	}

	@Override
	public void enterDa(Z8AsmParser.DaContext ctx) {
		handleRegisterCommand(0x40, ctx.registerOrIregister());
	}

	@Override
	public void enterDec(Z8AsmParser.DecContext ctx) {
		handleRegisterCommand(0x00, ctx.registerOrIregister());
	}

	@Override
	public void enterDecw(Z8AsmParser.DecwContext ctx) {
		handleRegisterCommand(0x80, ctx.registerOrIregister());
	}

	@Override
	public void enterDjnz(Z8AsmParser.DjnzContext ctx) {
		final int register = parseWorkingRegister(ctx.WorkingRegister());
		final int address = parseAddress(ctx.address());

		final int pc = output.getPc() + 2;
		final int delta = address - pc;
		if (delta <= -127 || delta >= 128) {
			throw new IllegalStateException(getPosition(ctx) + ": djnz needs to be replaced with dec & jp nz");
		}

		command2(register, 0x0A, delta);
	}

	@Override
	public void enterInc(Z8AsmParser.IncContext ctx) {
		final Z8AsmParser.RegisterOrIregisterContext context = ctx.registerOrIregister();
		final Z8AsmParser.RegisterContext registerContext = context.register();
		if (registerContext != null) {
			final TerminalNode wrNode = registerContext.WorkingRegister();
			if (wrNode != null) {
				final int register = parseWorkingRegister(wrNode);
				write(register, 0x0E);
				return;
			}
		}

		handleRegisterCommand(0x20, context);
	}

	@Override
	public void enterIncw(Z8AsmParser.IncwContext ctx) {
		handleRegisterCommand(0xA0, ctx.registerOrIregister());
	}

	@Override
	public void enterLd1(Z8AsmParser.Ld1Context ctx) {
		final int register = parseRegister(ctx.register());
		final Z8AsmParser.RegisterOrIregisterContext rOIrCtx = ctx.registerOrIregister();
		if (rOIrCtx != null) {
			final Z8AsmParser.RegisterContext sourceRegisterCtx = rOIrCtx.register();
			if (sourceRegisterCtx != null) {
				final int sourceRegister = parseRegister(sourceRegisterCtx);
				if (isWorkingRegister(register)) {
					command2(register, 0x08, sourceRegister);
				}
				else if (isWorkingRegister(sourceRegister)) {
					command2(sourceRegister, 0x09, register);
				}
				else {
					command3(0xE4, sourceRegister, register);
				}
				return;
			}

			final Z8AsmParser.IregisterContext sourceIRegisterCtx = rOIrCtx.iregister();
			if (sourceIRegisterCtx != null) {
				final int sourceRegister = parseRegister(sourceIRegisterCtx);
				if (isWorkingRegister(register) && isWorkingRegister(sourceRegister)) {
					command2w(0xE3, register, sourceRegister);
				}
				else {
					command3(0xF5, register, sourceRegister);
				}
				return;
			}
		}

		final TerminalNode valueNode = ctx.ValueByte();
		if (valueNode != null) {
			final int value = parseByte(valueNode);
			if ((register & 0xF0) == WORKING_REGISTER_HIGH_NIBBLE) {
				command2(register, 0x0C, value);
			}
			else {
				command3(0xE6, register, value);
			}
			return;
		}

		throw new IllegalStateException();
	}

	@Override
	public void enterLd2(Z8AsmParser.Ld2Context ctx) {
		final int iregister = parseRegister(ctx.iregister());
		final int register = parseRegister(ctx.register());
		if (isWorkingRegister(iregister) && isWorkingRegister(register)) {
			command2w(0xF3, iregister, register);
		}
		else {
			command3(0xE5, iregister, register);
		}
	}

	@Override
	public void enterLdc1(Z8AsmParser.Ldc1Context ctx) {
		final int register = parseWorkingRegister(ctx.WorkingRegister());
		final int sourceRegister = parseWorkingRegister(ctx.IWorkingRegisterPair());
		command2w(0xC2, register, sourceRegister);
	}

	@Override
	public void enterLdc2(Z8AsmParser.Ldc2Context ctx) {
		final int register = parseWorkingRegister(ctx.IWorkingRegisterPair());
		final int sourceRegister = parseWorkingRegister(ctx.WorkingRegister());
		command2w(0xD2, sourceRegister, register);
	}

	@Override
	public void enterLdci1(Z8AsmParser.Ldci1Context ctx) {
		final int register = parseWorkingRegister(ctx.IWorkingRegister());
		final int sourceRegister = parseWorkingRegister(ctx.IWorkingRegisterPair());
		command2w(0xC3, register, sourceRegister);
	}

	@Override
	public void enterLdci2(Z8AsmParser.Ldci2Context ctx) {
		final int register = parseWorkingRegister(ctx.IWorkingRegisterPair());
		final int sourceRegister = parseWorkingRegister(ctx.IWorkingRegister());
		command2w(0xD3, sourceRegister, register);
	}

	@Override
	public void enterLde1(Z8AsmParser.Lde1Context ctx) {
		final int register = parseWorkingRegister(ctx.WorkingRegister());
		final int sourceRegister = parseWorkingRegister(ctx.IWorkingRegisterPair());
		command2w(0x82, register, sourceRegister);
	}

	@Override
	public void enterLde2(Z8AsmParser.Lde2Context ctx) {
		final int register = parseWorkingRegister(ctx.IWorkingRegisterPair());
		final int sourceRegister = parseWorkingRegister(ctx.WorkingRegister());
		command2w(0x92, sourceRegister, register);
	}

	@Override
	public void enterJp(Z8AsmParser.JpContext ctx) {
		final Z8AsmParser.IregisterPairContext iregisterPairContext = ctx.iregisterPair();
		if (iregisterPairContext != null) {
			final int register = parseRegisterPair(iregisterPairContext);
			command2(0x30, register);
			return;
		}

		final TerminalNode condition = ctx.JpCondition();
		int highNibble = JP_CONDITIONS.get("");
		if (condition != null) {
			final String text = condition.getText().toLowerCase(Locale.ROOT);
			highNibble = JP_CONDITIONS.get(text);
		}
		final int address = parseAddress(ctx.address());

		final int pc = output.getPc() + 2;
		final int delta = address - pc;
		if (delta > -127 && delta < 128) {
			System.out.println(getPosition(ctx) + ": jp can be jr");
		}

		command3(highNibble + 0x0d, address);
	}

	@Override
	public void enterJr(Z8AsmParser.JrContext ctx) {
		final TerminalNode condition = ctx.JpCondition();
		int highNibble = JP_CONDITIONS.get("");
		if (condition != null) {
			final String text = condition.getText().toLowerCase(Locale.ROOT);
			highNibble = JP_CONDITIONS.get(text);
		}
		final int address = parseAddress(ctx.address());

		final int pc = output.getPc() + 2;
		final int delta = address - pc;
		if (delta <= -127 || delta >= 128) {
			throw new IllegalStateException(getPosition(ctx) + ": jr needs to be jp");
		}

		command2(highNibble + 0x0b, delta);
	}

	@Override
	public void enterPop(Z8AsmParser.PopContext ctx) {
		handleRegisterCommand(0x50, ctx.registerOrIregister());
	}

	@Override
	public void enterPush(Z8AsmParser.PushContext ctx) {
		handleRegisterCommand(0x70, ctx.registerOrIregister());
	}

	@Override
	public void enterRl(Z8AsmParser.RlContext ctx) {
		handleRegisterCommand(0x90, ctx.registerOrIregister());
	}

	@Override
	public void enterRlc(Z8AsmParser.RlcContext ctx) {
		handleRegisterCommand(0x10, ctx.registerOrIregister());
	}

	@Override
	public void enterRr(Z8AsmParser.RrContext ctx) {
		handleRegisterCommand(0xE0, ctx.registerOrIregister());
	}

	@Override
	public void enterRrc(Z8AsmParser.RrcContext ctx) {
		handleRegisterCommand(0xC0, ctx.registerOrIregister());
	}

	@Override
	public void enterSra(Z8AsmParser.SraContext ctx) {
		handleRegisterCommand(0xD0, ctx.registerOrIregister());
	}

	@Override
	public void enterSrp(Z8AsmParser.SrpContext ctx) {
		final int value = parseByte(ctx.ValueByte());
		if ((value & 0xF) != 0) {
			throw new SyntaxException("lower nibble must be 0", ctx);
		}

		command2(0x31, value);
	}

	@Override
	public void enterSwap(Z8AsmParser.SwapContext ctx) {
		handleRegisterCommand(0xF0, ctx.registerOrIregister());
	}

	@Override
	public void enterAdd(Z8AsmParser.AddContext ctx) {
		handleArithmeticCommand(0x0, ctx.arithmeticParameters());
	}

	@Override
	public void enterAdc(Z8AsmParser.AdcContext ctx) {
		handleArithmeticCommand(0x1, ctx.arithmeticParameters());
	}

	@Override
	public void enterSub(Z8AsmParser.SubContext ctx) {
		handleArithmeticCommand(0x2, ctx.arithmeticParameters());
	}

	@Override
	public void enterSbc(Z8AsmParser.SbcContext ctx) {
		handleArithmeticCommand(0x3, ctx.arithmeticParameters());
	}

	@Override
	public void enterOr(Z8AsmParser.OrContext ctx) {
		handleArithmeticCommand(0x4, ctx.arithmeticParameters());
	}

	@Override
	public void enterAnd(Z8AsmParser.AndContext ctx) {
		handleArithmeticCommand(0x5, ctx.arithmeticParameters());
	}

	@Override
	public void enterTcm(Z8AsmParser.TcmContext ctx) {
		handleArithmeticCommand(0x6, ctx.arithmeticParameters());
	}

	@Override
	public void enterTm(Z8AsmParser.TmContext ctx) {
		handleArithmeticCommand(0x7, ctx.arithmeticParameters());
	}

	@Override
	public void enterCp(Z8AsmParser.CpContext ctx) {
		handleArithmeticCommand(0xA, ctx.arithmeticParameters());
	}

	@Override
	public void enterXor(Z8AsmParser.XorContext ctx) {
		handleArithmeticCommand(0xB, ctx.arithmeticParameters());
	}

	// Accessing ==============================================================

	public void setDetails(boolean details) {
		this.details = details;
	}

	public void print(Writer writer) throws IOException {
		output.print(writer);
	}

	public void setAbortForUnknownLabel(boolean abortForUnknownLabel) {
		this.abortForUnknownLabel = abortForUnknownLabel;
	}

	// Utils ==================================================================

	private String parseString(String text, String prefixSuffix, Z8AsmParser.DataItemContext ctx) {
		if (text.length() < 2 || !text.startsWith(prefixSuffix) || !text.endsWith(prefixSuffix)) {
			throw new SyntaxException("Unknown string", ctx);
		}

		final StringBuilder buffer = new StringBuilder(text.length());
		boolean prevWasBackslash = false;
		for (int i = 1; i < text.length() - 1; i++) {
			final char chr = text.charAt(i);
			if ((chr & 0xFF) != chr) {
				throw new SyntaxException("String contains non-US-ASCII", ctx);
			}
			if (prevWasBackslash) {
				prevWasBackslash = false;
				if (chr == '\\') {
					buffer.append(chr);
				}
				else if (chr == 'n') {
					buffer.append('\n');
				}
				else if (chr == 'r') {
					buffer.append('\r');
				}
				else {
					throw new SyntaxException("Invalid escape " + chr, ctx);
				}
			}
			else if (chr == '\\') {
				prevWasBackslash = true;
			}
			else {
				buffer.append(chr);
			}
		}
		if (prevWasBackslash) {
			throw new SyntaxException("String must not end with escape", ctx);
		}

		return buffer.toString();
	}

	private void handleRegisterCommand(int opCode, Z8AsmParser.RegisterOrIregisterContext context) {
		final Z8AsmParser.RegisterContext register = context.register();
		if (register != null) {
			command2(opCode, parseRegister(register));
			return;
		}

		final Z8AsmParser.IregisterContext iregister = context.iregister();
		command2(opCode + 1, parseRegister(iregister));
	}

	private void handleArithmeticCommand(int hiNibble, Z8AsmParser.ArithmeticParametersContext p) {
		final Z8AsmParser.ArithmeticParameters1Context p1 = p.arithmeticParameters1();
		if (p1 != null) {
			final int register = parseRegister(p1.register(0));
			final Z8AsmParser.RegisterContext sourceRegisterCtx = p1.register(1);
			if (sourceRegisterCtx != null) {
				final int sourceRegister = parseRegister(sourceRegisterCtx);
				if (isWorkingRegister(register) && isWorkingRegister(sourceRegister)) {
					command2(hiNibble, 0x2, register, sourceRegister);
					return;
				}

				command3(hiNibble, 0x4, sourceRegister, register);
				return;
			}

			final TerminalNode valueByte = p1.ValueByte();
			if (valueByte != null) {
				command3(hiNibble, 0x6, register, parseByte(valueByte));
				return;
			}
		}

		final Z8AsmParser.ArithmeticParameters2Context p2 = p.arithmeticParameters2();
		if (p2 != null) {
			final int register = parseRegister(p2.iregister());
			final TerminalNode valueByte = p2.ValueByte();
			if (valueByte != null) {
				command3(hiNibble, 0x7, register, parseByte(valueByte));
				return;
			}
		}

		throw new IllegalStateException(p.getText());
	}

	private void command1(int opCode) {
		write(opCode);
	}

	private void command2(int opCode, int value) {
		write(opCode);
		write(value);
	}

	private void command2(int highNibble, int lowNibble, int value) {
		write(highNibble, lowNibble);
		write(value);
	}

	private void command2w(int opCode, int r1, int r2) {
		write(opCode);
		write(r1, r2);
	}

	private void command2(int highNibble, int lowNibble, int r1, int r2) {
		write(highNibble, lowNibble);
		write(r1, r2);
	}

	private void command3(int opCode, int address) {
		write(opCode);
		write(address >> 8);
		write(address);
	}

	private void command3(int opCode, int p1, int p2) {
		write(opCode);
		write(p1);
		write(p2);
	}

	private void command3(int hiNibble, int lowNibble, int byte2, int byte3) {
		write(hiNibble, lowNibble);
		write(byte2);
		write(byte3);
	}

	private void write(int value) {
		output.write(value);
	}

	private void write(int highNibble, int lowNibble) {
		output.write((highNibble & 0x0F) << 4 | lowNibble & 0x0F);
	}

	private boolean isWorkingRegister(int register) {
		return (register & 0xF0) == WORKING_REGISTER_HIGH_NIBBLE;
	}

	private int parseAddress(Z8AsmParser.AddressContext addressCtx) {
		TerminalNode addressValue = addressCtx.Word();
		if (addressValue != null) {
			return parseAddress(addressValue);
		}

		final TerminalNode identifier = addressCtx.Identifier();
		if (identifier != null) {
			final String text = identifier.getText();
			final Integer labelAddress = labels.get(text);
			if (labelAddress == null) {
				if (abortForUnknownLabel) {
					throw new SyntaxException("Unknown label '" + text + "'", addressCtx);
				}
				return pc + 2;
			}
			return labelAddress.intValue();
		}

		throw new SyntaxException("Unknown target", addressCtx);
	}

	private int parseByte(TerminalNode valueNode) {
		String text = valueNode.getText();
		if (text.startsWith("#")) {
			text = text.substring(1);
		}
		if (text.startsWith("%")) {
			text = text.substring(1);
			return Integer.valueOf(text, 16);
		}
		return Integer.parseInt(text);
	}

	private int parseRegister(Z8AsmParser.RegisterContext ctx) {
		final TerminalNode byteNode = ctx.Byte();
		if (byteNode != null) {
			return parseByte(byteNode);
		}

		final TerminalNode workingRegisterNode = ctx.WorkingRegister();
		if (workingRegisterNode != null) {
			return WORKING_REGISTER_HIGH_NIBBLE + parseWorkingRegister(workingRegisterNode);
		}

		final TerminalNode constant = ctx.RegisterConstant();
		if (constant != null) {
			return REGISTER_CONSTANTS.get(constant.getText().toLowerCase(Locale.ROOT));
		}

		throw new UnsupportedOperationException(ctx.getText());
	}

	private int parseRegister(Z8AsmParser.IregisterContext ctx) {
		final Z8AsmParser.RegisterContext register = ctx.register();
		if (register != null) {
			return parseRegister(register);
		}

		final TerminalNode node = ctx.IWorkingRegister();
		if (node != null) {
			return WORKING_REGISTER_HIGH_NIBBLE + parseWorkingRegister(node);
		}

		throw new UnsupportedOperationException(ctx.getText());
	}

	private int parseWorkingRegister(TerminalNode workingRegisterNode) {
		String text = workingRegisterNode.getText().toLowerCase(Locale.ROOT);
		if (text.startsWith("@")) {
			text = text.substring(1);
		}
		if (text.startsWith("r")) {
			text = text.substring(1);
		}
		if (text.startsWith("r")) {
			text = text.substring(1);
		}

		return parseNibble(text);
	}

	private int parseRegisterPair(Z8AsmParser.IregisterPairContext ctx) {
		final TerminalNode node = ctx.IWorkingRegisterPair();
		final int register;
		if (node != null) {
			return WORKING_REGISTER_HIGH_NIBBLE | parseWorkingRegister(node);
		}

		return parseRegister(ctx.register());
	}

	private int parseNibble(String text) {
		final int value = Integer.parseInt(text);
		if (value < 0 || value > 0xF) {
			throw new NumberFormatException(text);
		}
		return value;
	}

	private int parseAddress(TerminalNode node) {
		final String text = node.getText();
		return Integer.valueOf(text.substring(1), 16);
	}

	private int parseByte(String s) {
		return Integer.valueOf(s, 16);
	}

	private static Map<String, Integer> createJpConditionsMap() {
		final Map<String, Integer> map = new HashMap<>();
		map.put("f", 0);
		map.put("lt", 0x10);
		map.put("le", 0x20);
		map.put("ule", 0x30);
		map.put("ov", 0x40);
		map.put("mi", 0x50);
		map.put("z", 0x60);
		map.put("eq", 0x60);
		map.put("c", 0x70);
		map.put("ult", 0x70);
		map.put("", 0x80);
		map.put("ge", 0x90);
		map.put("gt", 0xA0);
		map.put("ugt", 0xB0);
		map.put("nov", 0xC0);
		map.put("pl", 0xD0);
		map.put("nz", 0xE0);
		map.put("ne", 0xE0);
		map.put("nc", 0xF0);
		map.put("uge", 0xF0);
		return Collections.unmodifiableMap(map);
	}

	private static Map<String, Integer> createRegisterConstantsMap() {
		final Map<String, Integer> map = new HashMap<>();
		map.put("sio", 0xF0);
		map.put("tmr", 0xF1);
		map.put("t1", 0xF2);
		map.put("pre1", 0xF3);
		map.put("t0", 0xF4);
		map.put("pre0", 0xF5);
		map.put("p2m", 0xF6);
		map.put("p3m", 0xF7);
		map.put("p01m", 0xF8);
		map.put("ipr", 0xF9);
		map.put("irq", 0xFA);
		map.put("imr", 0xFB);
		map.put("flag", 0xFC);
		map.put("rp", 0xFD);
		map.put("sph", 0xFE);
		map.put("spl", 0xFF);
		return Collections.unmodifiableMap(map);
	}

	private static String getPosition(ParserRuleContext context) {
		final Token start = context.getStart();
		return start.getLine() + ":" + start.getCharPositionInLine();
	}

	// Inner Classes ==========================================================

	public static class SyntaxException extends RuntimeException {
		private final ParserRuleContext context;

		public SyntaxException(String error, ParserRuleContext context) {
			super(error);
			this.context = context;
		}

		public String getPosition() {
			return Assembler.getPosition(context);
		}
	}
}
