package com.syntevo.antlr.z8;

import java.io.*;
import java.util.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

/**
 * @author Thomas Singer
 */
public final class Assembler extends Z8AsmBaseVisitor<Object> {

	// Constants ==============================================================

	private static final int WORKING_REGISTER_HIGH_NIBBLE = 0xE0;
	private static final Map<String, Integer> JP_CONDITIONS = createJpConditionsMap();

	// Fields =================================================================

	private final Map<String, LabelAddress> globalLabels = new HashMap<>();
	private final Map<String, Map<String, LabelAddress>> globalToLocalLabels = new HashMap<>();
	private final Map<String, Integer> constants = new HashMap<>();

	private boolean details;
	private int pass;
	private boolean labelChanged;
	private String prevGlobalLabel;

	private Output output;
	private int pc;

	// Setup ==================================================================

	public Assembler() {
	}

	// Implemented ============================================================

	@Override
	public Object visitRoot(Z8AsmParser.RootContext ctx) {
		output = new Output();
		labelChanged = false;
		prevGlobalLabel = null;
		pass++;

		if (pass > 10) {
			throw new SyntaxException("Too many passes", ctx);
		}

		System.out.println("Pass " + pass + "...");

		super.visitRoot(ctx);

		return null;
	}

	@Override
	public Object visitParserInstruction(Z8AsmParser.ParserInstructionContext ctx) {
		if (details) {
			final int line = ctx.getStart().getLine();
			System.out.println(line + " " + ctx.getText().trim());
		}

		return super.visitParserInstruction(ctx);
	}

	@Override
	public Object visitCommand(Z8AsmParser.CommandContext ctx) {
		pc = output.getPc();
		if (details) {
			final int line = ctx.getStart().getLine();
			System.out.println(line + ": " + ctx.getText().trim());
		}

		final Object result = super.visitCommand(ctx);

		final int pc = output.getPc();
		if (pc == this.pc) {
			throw new SyntaxException("Command not processed " + ctx.getText(), ctx);
		}

		if (details) {
			output.printFrom(this.pc);
			System.out.println();
		}

		return result;
	}

	@Override
	public Object visitDefConst(Z8AsmParser.DefConstContext ctx) {
		final String text = ctx.name.getText();
		final int value = (Integer)visit(ctx.expr);

		final Integer prevValue = constants.put(text, value);
		if (pass > 1) {
			if (prevValue == null) {
				throw new SyntaxException("undefined constant '" + text + "'", ctx);
			}
			if (prevValue.intValue() != value) {
				throw new SyntaxException("constant '" + text + "' changed value", ctx);
			}
		}
		else {
			if (prevValue != null) {
				throw new SyntaxException("constant '" + text + "' already defined", ctx);
			}
		}
		return null;
	}

	@Override
	public Object visitLabelDefinition(Z8AsmParser.LabelDefinitionContext ctx) {
		final String text = ctx.label.getText();
		final int pc = output.getPc();
		if (details && !new Formatter().format("M_%04X", pc).toString().equals(text)) {
			System.err.println("address differs from label: " + text);
		}

		final LabelAddress value = new LabelAddress(pc, ctx.label.getLine(), ctx.label.getCharPositionInLine());

		final LabelAddress prevValue;
		// local label?
		if (text.startsWith(".")) {
			Map<String, LabelAddress> localLabels = getLocalLabels(ctx);
			if (localLabels == null) {
				labelChanged = true;
				localLabels = new HashMap<>();
				globalToLocalLabels.put(prevGlobalLabel, localLabels);
			}

			prevValue = localLabels.put(text, value);
		}
		else {
			prevValue = globalLabels.put(text, value);
			prevGlobalLabel = text;
		}

		if (prevValue != null) {
			if (value.line != prevValue.line
			    || value.column != prevValue.column) {
				throw new SyntaxException("Label '" + text + "' was already defined at " + prevValue.line + ":" + (prevValue.column + 1), ctx);
			}

			if (value.hasDifferentAddressThan(prevValue)) {
				labelChanged = true;
			}
			value.updateFrom(prevValue);
		}
		else {
			labelChanged = true;
		}
		return null;
	}

	@Override
	public Object visitOrg(Z8AsmParser.OrgContext ctx) {
		final int word = parseWord(ctx.Word());
		output.setPc(word);
		return null;
	}

	@Override
	public Object visitDataByte(Z8AsmParser.DataByteContext ctx) {
		write(parseByte(ctx.Byte()));
		return null;
	}

	@Override
	public Object visitDataAddress(Z8AsmParser.DataAddressContext ctx) {
		final int address = visitGlobalAddress(ctx.globalAddress());
		write(address >> 8);
		write(address);
		return null;
	}

	@Override
	public Object visitDataChar(Z8AsmParser.DataCharContext ctx) {
		write(getChar(ctx));
		return null;
	}

	@Override
	public Object visitDataLString(Z8AsmParser.DataLStringContext ctx) {
		String text = ctx.getText();
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
		return null;
	}

	@Override
	public Object visitDataString(Z8AsmParser.DataStringContext ctx) {
		final String text = ctx.getText();
		final String string = parseString(text, "\"", ctx);

		for (int i = 0; i < string.length(); i++) {
			write(string.charAt(i));
		}
		return null;
	}

	@Override
	public Object visitRepeat(Z8AsmParser.RepeatContext ctx) {
		int count = (Integer)visit(ctx.expression());
		if (count <= 0) {
			throw new SyntaxException("Expression needs to be a value > 0", ctx);
		}

		while (count-- > 0) {
			visit(ctx.repeatInstructions());
		}

		return null;
	}

	@Override
	public Object visitDi(Z8AsmParser.DiContext ctx) {
		return command1(0x8F);
	}

	@Override
	public Object visitEi(Z8AsmParser.EiContext ctx) {
		return command1(0x9F);
	}

	@Override
	public Object visitRet(Z8AsmParser.RetContext ctx) {
		return command1(0xAF);
	}

	@Override
	public Object visitIret(Z8AsmParser.IretContext ctx) {
		return command1(0xBF);
	}

	@Override
	public Object visitRcf(Z8AsmParser.RcfContext ctx) {
		return command1(0xCF);
	}

	@Override
	public Object visitScf(Z8AsmParser.ScfContext ctx) {
		return command1(0xDF);
	}

	@Override
	public Object visitCcf(Z8AsmParser.CcfContext ctx) {
		return command1(0xEF);
	}

	@Override
	public Object visitNop(Z8AsmParser.NopContext ctx) {
		return command1(0xFF);
	}

	@Override
	public Object visitLd1(Z8AsmParser.Ld1Context ctx) {
		final int register = (Integer)visit(ctx.register());
		final int value = (Integer)visit(ctx.valueByte());
		if (isWorkingRegister(register)) {
			return command2(toByte(register, 0x0C), value);
		}

		return command3(0xE6, register, value);
	}

	@Override
	public Object visitLd2(Z8AsmParser.Ld2Context ctx) {
		final int target = visitRegister(ctx.target);
		final int sourceRegister = visitRegister(ctx.source);
		if (isWorkingRegister(target)) {
			return command2(toByte(target, 0x08), sourceRegister);
		}

		if (isWorkingRegister(sourceRegister)) {
			return command2(toByte(sourceRegister, 0x09), target);
		}

		return command3(0xE4, sourceRegister, target);
	}

	@Override
	public Object visitLd3(Z8AsmParser.Ld3Context ctx) {
		final int target = visitRegister(ctx.register());
		final int source = visitIregister(ctx.iregister());
		if (isWorkingRegister(target) && isWorkingRegister(source)) {
			return command2(0xE3, toByte(target, source));
		}

		return command3(0xE5, source, target);
	}

	@Override
	public Object visitLd4(Z8AsmParser.Ld4Context ctx) {
		// the weird command
		// LD r1, -10(r2)
		final int register = parseWorkingRegister(ctx.WorkingRegister(0));
		final int offset = parseByte(ctx.Byte());
		final int sourceRegister = parseWorkingRegister(ctx.WorkingRegister(1));
		return command3(0xC7, toByte(register, sourceRegister), offset);
	}

	@Override
	public Object visitLd5(Z8AsmParser.Ld5Context ctx) {
		// the weird command
		// LD -10(r1), r2
		final int register = parseWorkingRegister(ctx.WorkingRegister(0));
		final int offset = parseByte(ctx.Byte());
		final int sourceRegister = parseWorkingRegister(ctx.WorkingRegister(1));
		return command3(0xD7, toByte(register, sourceRegister), offset);
	}

	@Override
	public Object visitLd6(Z8AsmParser.Ld6Context ctx) {
		final int target = visitIregister(ctx.iregister());
		final int source = visitRegister(ctx.register());
		if (isWorkingRegister(target) && isWorkingRegister(source)) {
			return command2(0xF3, toByte(target, source));
		}

		return command3(0xF5, source, target);
	}

	@Override
	public Object visitLdc1(Z8AsmParser.Ldc1Context ctx) {
		final int register = parseWorkingRegister(ctx.WorkingRegister());
		final int sourceRegister = parseWorkingRegister(ctx.IWorkingRegisterPair());
		return command2(0xC2, toByte(register, sourceRegister));
	}

	@Override
	public Object visitLdc2(Z8AsmParser.Ldc2Context ctx) {
		final int register = parseWorkingRegister(ctx.IWorkingRegisterPair());
		final int sourceRegister = parseWorkingRegister(ctx.WorkingRegister());
		return command2(0xD2, toByte(sourceRegister, register));
	}

	@Override
	public Object visitLde1(Z8AsmParser.Lde1Context ctx) {
		final int register = parseWorkingRegister(ctx.WorkingRegister());
		final int sourceRegister = parseWorkingRegister(ctx.IWorkingRegisterPair());
		return command2(0x82, toByte(register, sourceRegister));
	}

	@Override
	public Object visitLde2(Z8AsmParser.Lde2Context ctx) {
		final int register = parseWorkingRegister(ctx.IWorkingRegisterPair());
		final int sourceRegister = parseWorkingRegister(ctx.WorkingRegister());
		return command2(0x92, toByte(sourceRegister, register));
	}

	@Override
	public Object visitLdci1(Z8AsmParser.Ldci1Context ctx) {
		final int register = parseWorkingRegister(ctx.IWorkingRegister());
		final int sourceRegister = parseWorkingRegister(ctx.IWorkingRegisterPair());
		return command2(0xC3, toByte(register, sourceRegister));
	}

	@Override
	public Object visitLdci2(Z8AsmParser.Ldci2Context ctx) {
		final int register = parseWorkingRegister(ctx.IWorkingRegisterPair());
		final int sourceRegister = parseWorkingRegister(ctx.IWorkingRegister());
		return command2(0xD3, toByte(sourceRegister, register));
	}

	@Override
	public Object visitLdei1(Z8AsmParser.Ldei1Context ctx) {
		final int register = parseWorkingRegister(ctx.IWorkingRegister());
		final int sourceRegister = parseWorkingRegister(ctx.IWorkingRegisterPair());
		return command2(0x83, toByte(register, sourceRegister));
	}

	@Override
	public Object visitLdei2(Z8AsmParser.Ldei2Context ctx) {
		final int register = parseWorkingRegister(ctx.IWorkingRegisterPair());
		final int sourceRegister = parseWorkingRegister(ctx.IWorkingRegister());
		return command2(0x93, toByte(sourceRegister, register));
	}

	@Override
	public Object visitSrp(Z8AsmParser.SrpContext ctx) {
		final int value = (Integer)visit(ctx.valueByte());
		if ((value & 0xF) != 0) {
			throw new SyntaxException("lower nibble must be 0", ctx);
		}

		return command2(0x31, value);
	}

	@Override
	public Object visitDec(Z8AsmParser.DecContext ctx) {
		return handleRegisterCommand(0x00, ctx.registerOrIregister());
	}

	@Override
	public Object visitRlc(Z8AsmParser.RlcContext ctx) {
		return handleRegisterCommand(0x10, ctx.registerOrIregister());
	}

	@Override
	public Object visitInc(Z8AsmParser.IncContext ctx) {
		final Z8AsmParser.RegisterOrIregisterContext context = ctx.registerOrIregister();
		final Z8AsmParser.RegisterContext registerContext = context.register();
		if (registerContext != null) {
			final TerminalNode wrNode = registerContext.WorkingRegister();
			if (wrNode != null) {
				final int register = parseWorkingRegister(wrNode);
				return command1(toByte(register, 0x0E));
			}
		}

		return handleRegisterCommand(0x20, ctx.registerOrIregister());
	}

	@Override
	public Object visitDa(Z8AsmParser.DaContext ctx) {
		return handleRegisterCommand(0x40, ctx.registerOrIregister());
	}

	@Override
	public Object visitPop(Z8AsmParser.PopContext ctx) {
		return handleRegisterCommand(0x50, ctx.registerOrIregister());
	}

	@Override
	public Object visitCom(Z8AsmParser.ComContext ctx) {
		return handleRegisterCommand(0x60, ctx.registerOrIregister());
	}

	@Override
	public Object visitPush(Z8AsmParser.PushContext ctx) {
		return handleRegisterCommand(0x70, ctx.registerOrIregister());
	}

	@Override
	public Object visitDecw(Z8AsmParser.DecwContext ctx) {
		return handleRegisterCommand(0x80, ctx.registerOrIregister());
	}

	@Override
	public Object visitRl(Z8AsmParser.RlContext ctx) {
		return handleRegisterCommand(0x90, ctx.registerOrIregister());
	}

	@Override
	public Object visitIncw(Z8AsmParser.IncwContext ctx) {
		return handleRegisterCommand(0xA0, ctx.registerOrIregister());
	}

	@Override
	public Object visitClr(Z8AsmParser.ClrContext ctx) {
		return handleRegisterCommand(0xB0, ctx.registerOrIregister());
	}

	@Override
	public Object visitRrc(Z8AsmParser.RrcContext ctx) {
		return handleRegisterCommand(0xC0, ctx.registerOrIregister());
	}

	@Override
	public Object visitSra(Z8AsmParser.SraContext ctx) {
		return handleRegisterCommand(0xD0, ctx.registerOrIregister());
	}

	@Override
	public Object visitRr(Z8AsmParser.RrContext ctx) {
		return handleRegisterCommand(0xE0, ctx.registerOrIregister());
	}

	@Override
	public Object visitSwap(Z8AsmParser.SwapContext ctx) {
		return handleRegisterCommand(0xF0, ctx.registerOrIregister());
	}

	@Override
	public Object visitAdd(Z8AsmParser.AddContext ctx) {
		return handleArithmeticCommand(0x0, ctx.arithmeticParameters());
	}

	@Override
	public Object visitAdc(Z8AsmParser.AdcContext ctx) {
		return handleArithmeticCommand(0x1, ctx.arithmeticParameters());
	}

	@Override
	public Object visitSub(Z8AsmParser.SubContext ctx) {
		return handleArithmeticCommand(0x2, ctx.arithmeticParameters());
	}

	@Override
	public Object visitSbc(Z8AsmParser.SbcContext ctx) {
		return handleArithmeticCommand(0x3, ctx.arithmeticParameters());
	}

	@Override
	public Object visitOr(Z8AsmParser.OrContext ctx) {
		return handleArithmeticCommand(0x4, ctx.arithmeticParameters());
	}

	@Override
	public Object visitAnd(Z8AsmParser.AndContext ctx) {
		return handleArithmeticCommand(0x5, ctx.arithmeticParameters());
	}

	@Override
	public Object visitTcm(Z8AsmParser.TcmContext ctx) {
		return handleArithmeticCommand(0x6, ctx.arithmeticParameters());
	}

	@Override
	public Object visitTm(Z8AsmParser.TmContext ctx) {
		return handleArithmeticCommand(0x7, ctx.arithmeticParameters());
	}

	@Override
	public Object visitCp(Z8AsmParser.CpContext ctx) {
		return handleArithmeticCommand(0xA, ctx.arithmeticParameters());
	}

	@Override
	public Object visitXor(Z8AsmParser.XorContext ctx) {
		return handleArithmeticCommand(0xB, ctx.arithmeticParameters());
	}

	@Override
	public Object visitCallIreg(Z8AsmParser.CallIregContext ctx) {
		return command2(0xD4, visitIregisterPair(ctx.iregisterPair()));
	}

	@Override
	public Object visitCallAddress(Z8AsmParser.CallAddressContext ctx) {
		return command3(0xD6, visitGlobalAddress(ctx.globalAddress()));
	}

	@Override
	public Object visitJpIReg(Z8AsmParser.JpIRegContext ctx) {
		return command2(0x30, visitIregisterPair(ctx.iregisterPair()));
	}

	@Override
	public Object visitJpAddress(Z8AsmParser.JpAddressContext ctx) {
		final int address = visitLocalOrGlobalAddress(ctx.localOrGlobalAddress());
		jpCheckJr(ctx, address);
		return command3(toByte(JP_CONDITIONS.get(""), 0x0d), address);
	}

	@Override
	public Object visitJpConditionAddress(Z8AsmParser.JpConditionAddressContext ctx) {
		final TerminalNode condition = ctx.JpCondition();
		final String text = condition.getText().toLowerCase(Locale.ROOT);
		int highNibble = JP_CONDITIONS.get(text);
		final int address = visitLocalOrGlobalAddress(ctx.localOrGlobalAddress());
		jpCheckJr(ctx, address);
		return command3(toByte(highNibble, 0x0d), address);
	}

	@Override
	public Object visitJr(Z8AsmParser.JrContext ctx) {
		final TerminalNode condition = ctx.JpCondition();
		int highNibble = JP_CONDITIONS.get("");
		if (condition != null) {
			final String text = condition.getText().toLowerCase(Locale.ROOT);
			highNibble = JP_CONDITIONS.get(text);
		}

		final int address = visitLocalOrGlobalAddress(ctx.localOrGlobalAddress());

		final int pc = output.getPc() + 2;
		final int delta = address - pc;
		if (!isValidJpDelta(delta)) {
			throw new SyntaxException("jr needs to be jp", ctx);
		}

		return command2(toByte(highNibble, 0x0b), delta);
	}

	@Override
	public Object visitDjnz(Z8AsmParser.DjnzContext ctx) {
		final int register = parseWorkingRegister(ctx.WorkingRegister());
		final int address = visitLocalOrGlobalAddress(ctx.localOrGlobalAddress());

		final int pc = output.getPc() + 2;
		final int delta = address - pc;
		if (!isValidJpDelta(delta)) {
			throw new IllegalStateException(getPosition(ctx) + ": djnz needs to be replaced with dec & jp nz");
		}

		return command2(toByte(register, 0x0A), delta);
	}

	@Override
	public Object visitJpr(Z8AsmParser.JprContext ctx) {
		final TerminalNode condition = ctx.JpCondition();
		int highNibble = JP_CONDITIONS.get("");
		if (condition != null) {
			final String text = condition.getText().toLowerCase(Locale.ROOT);
			highNibble = JP_CONDITIONS.get(text);
		}

		final int address = visitLocalOrGlobalAddress(ctx.localOrGlobalAddress());

		if (pass > 1) {
			final int pc = output.getPc() + 2;
			final int delta = address - pc;
			if (isValidJpDelta(delta)) {
				return command2(toByte(highNibble, 0x0b), delta);
			}
		}

		return command3(toByte(highNibble, 0x0d), address);
	}

	@Override
	public Integer visitIregisterPair(Z8AsmParser.IregisterPairContext ctx) {
		final TerminalNode node = ctx.IWorkingRegisterPair();
		if (node != null) {
			return WORKING_REGISTER_HIGH_NIBBLE | parseWorkingRegister(node);
		}

		return visitRegister(ctx.register());
	}

	@Override
	public Integer visitIregister(Z8AsmParser.IregisterContext ctx) {
		final Z8AsmParser.RegisterContext register = ctx.register();
		if (register != null) {
			return visitRegister(register);
		}

		final TerminalNode node = ctx.IWorkingRegister();
		if (node != null) {
			return WORKING_REGISTER_HIGH_NIBBLE + parseWorkingRegister(node);
		}

		throw new UnsupportedOperationException(ctx.getText());
	}

	@Override
	public Integer visitRegister(Z8AsmParser.RegisterContext ctx) {
		final TerminalNode byteNode = ctx.Byte();
		if (byteNode != null) {
			return parseByte(byteNode);
		}

		final TerminalNode workingRegisterNode = ctx.WorkingRegister();
		if (workingRegisterNode != null) {
			return WORKING_REGISTER_HIGH_NIBBLE + parseWorkingRegister(workingRegisterNode);
		}

		final TerminalNode identifierNode = ctx.Identifier();
		if (identifierNode != null) {
			return getIdentifierValue(identifierNode, ctx);
		}

		throw new UnsupportedOperationException(ctx.getText());
	}

	@Override
	public Integer visitGlobalAddress(Z8AsmParser.GlobalAddressContext ctx) {
		TerminalNode addressValue = ctx.Word();
		if (addressValue != null) {
			return parseWord(addressValue);
		}

		final Token label = ctx.label;
		if (label != null) {
			final String text = label.getText();
			final LabelAddress labelAddress = globalLabels.get(text);
			if (labelAddress == null) {
				if (pass > 1) {
					throw new SyntaxException("Unknown label '" + text + "'", ctx);
				}
				return pc + 2;
			}
			return labelAddress.readValue();
		}

		throw new SyntaxException("Unknown target", ctx);
	}

	@Override
	public Integer visitLocalOrGlobalAddress(Z8AsmParser.LocalOrGlobalAddressContext ctx) {
		final TerminalNode localAddressValue = ctx.LocalLabel();
		if (localAddressValue == null) {
			return visitGlobalAddress(ctx.globalAddress());
		}

		final Map<String, LabelAddress> localLabels = getLocalLabels(ctx);

		final String text = localAddressValue.getText();
		final LabelAddress labelAddress = localLabels.get(text);
		if (labelAddress == null) {
			if (pass > 1) {
				throw new SyntaxException("Unknown local label '" + text + "' after '" + prevGlobalLabel + "'", ctx);
			}
			return pc + 2;
		}
		return labelAddress.readValue();
	}

	@Override
	public Integer visitExprNumber(Z8AsmParser.ExprNumberContext ctx) {
		TerminalNode node = ctx.Byte();
		if (node == null) {
			node = ctx.Word();
		}
		String text = node.getText();
		if (text.startsWith("0b")) {
			return Integer.parseUnsignedInt(text.substring(2).replace("_", ""), 2);
		}
		return parseNumber(text);
	}

	@Override
	public Integer visitExprChar(Z8AsmParser.ExprCharContext ctx) {
		return (int)getChar(ctx);
	}

	@Override
	public Integer visitExprIdentifier(Z8AsmParser.ExprIdentifierContext ctx) {
		return getIdentifierValue(ctx.Identifier(), ctx);
	}

	@Override
	public Integer visitValueByte(Z8AsmParser.ValueByteContext ctx) {
		return (Integer)visit(ctx.expression());
	}

	// Accessing ==============================================================

	public void process(ParseTree root) {
		do {
			visit(root);
		}
		while (isLabelChanged());
		reportUnusedLabels();
	}

	public void setDetails(boolean details) {
		this.details = details;
	}

	public boolean isLabelChanged() {
		return labelChanged;
	}

	public void print(Writer writer) throws IOException {
		output.print(writer);
	}

	public void print(Writer writer, String newLine) throws IOException {
		output.print(writer, newLine);
	}

	public int printC(Writer writer) throws IOException {
		return output.printC(writer);
	}

	public void printVerilog(Writer writer) throws IOException {
		output.printVerilog(writer);
	}

	public void write(OutputStream stream) throws IOException {
		output.write(stream);
	}

	public void reportUnusedLabels() {
		for (Map.Entry<String, LabelAddress> entry : globalLabels.entrySet()) {
			if (entry.getValue().isUnused()) {
				System.out.println("label " + entry.getKey() + " is unused");
			}
		}
	}

	private Map<String, LabelAddress> getLocalLabels(ParserRuleContext ctx) {
		if (prevGlobalLabel == null) {
			throw new SyntaxException("A local label (with a leading dot) can only be used after a global label", ctx);
		}

		return globalToLocalLabels.get(prevGlobalLabel);
	}

	// Utils ==================================================================

	private Integer getIdentifierValue(TerminalNode identifierNode, ParserRuleContext ctx) {
		final String text = identifierNode.getText();
		final Integer value = constants.get(text);
		if (value == null) {
			throw new SyntaxException("Undefined constant " + text, ctx);
		}
		return value;
	}

	private char getChar(ParserRuleContext ctx) {
		String text = ctx.getText();
		final String string = parseString(text, "'", ctx);
		if (string.length() != 1) {
			throw new SyntaxException("Char must have length of 1", ctx);
		}

		return string.charAt(0);
	}

	private String parseString(String text, String prefixSuffix, ParserRuleContext ctx) {
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
				if (chr == '\\'
				    || chr == '"') {
					buffer.append(chr);
				}
				else if (chr == 'n') {
					buffer.append('\n');
				}
				else if (chr == 'r') {
					buffer.append('\r');
				}
				else if (chr == '0') {
					buffer.append('\0');
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

	private Object handleRegisterCommand(int opCode, Z8AsmParser.RegisterOrIregisterContext context) {
		final Z8AsmParser.RegisterContext register = context.register();
		if (register != null) {
			return command2(opCode, visitRegister(register));
		}

		final Z8AsmParser.IregisterContext iregister = context.iregister();
		return command2(opCode + 1, visitIregister(iregister));
	}

	private boolean isValidJpDelta(int delta) {
		return delta >= -128 && delta < 128;
	}

	private void jpCheckJr(ParserRuleContext ctx, int address) {
		final int pc = output.getPc() + 2;
		final int delta = address - pc;
		if (isValidJpDelta(delta)) {
			System.out.println(getPosition(ctx) + ": jp can be jr");
		}
	}

	private Object handleArithmeticCommand(int highNibble, Z8AsmParser.ArithmeticParametersContext p) {
		final Z8AsmParser.ArithmeticParameters1Context p1 = p.arithmeticParameters1();
		if (p1 != null) {
			return handleArithmeticCommand(highNibble, p1);
		}

		final Z8AsmParser.ArithmeticParameters2Context p2 = p.arithmeticParameters2();
		if (p2 != null) {
			return handleArithmeticCommand(highNibble, p2);
		}

		final Z8AsmParser.ArithmeticParameters3Context p3 = p.arithmeticParameters3();
		if (p3 != null) {
			return handleArithmeticCommand(highNibble, p3);
		}

		final Z8AsmParser.ArithmeticParameters4Context p4 = p.arithmeticParameters4();
		if (p4 != null) {
			return handleArithmeticCommand(highNibble, p4);
		}

		throw new IllegalStateException(p.getText());
	}

	private Object handleArithmeticCommand(int highNibble, Z8AsmParser.ArithmeticParameters1Context ctx) {
		final int register = visitRegister(ctx.register());
		final int value = visitValueByte(ctx.valueByte());
		return command3(toByte(highNibble, 0x6), register, value);
	}

	private Object handleArithmeticCommand(int highNibble, Z8AsmParser.ArithmeticParameters2Context ctx) {
		final int target = visitRegister(ctx.target);
		final int source = visitRegister(ctx.source);
		if (isWorkingRegister(target) && isWorkingRegister(source)) {
			return command2(toByte(highNibble, 0x2), toByte(target, source));
		}

		return command3(toByte(highNibble, 0x4), source, target);
	}

	private Object handleArithmeticCommand(int highNibble, Z8AsmParser.ArithmeticParameters3Context ctx) {
		final int register = visitIregister(ctx.iregister());
		final int value = visitValueByte(ctx.valueByte());
		return command3(toByte(highNibble, 0x7), register, value);
	}

	private Object handleArithmeticCommand(int highNibble, Z8AsmParser.ArithmeticParameters4Context ctx) {
		final int register = visitRegister(ctx.register());
		final int iregister = visitIregister(ctx.iregister());
		if (isWorkingRegister(register) && isWorkingRegister(iregister)) {
			return command2(toByte(highNibble, 0x3), toByte(register, iregister));
		}

		throw new IllegalStateException(ctx.getText());
	}

	private Object command1(int opCode) {
		write(opCode);
		return null;
	}

	private Object command2(int opCode, int value) {
		write(opCode);
		write(value);
		return null;
	}

	private Object command3(int opCode, int p1, int p2) {
		write(opCode);
		write(p1);
		write(p2);
		return null;
	}

	private Object command3(int opCode, int address) {
		return command3(opCode, address >> 8, address);
	}

	private void write(int value) {
		output.write(value);
	}

	private int parseByte(TerminalNode node) {
		String text = node.getText();
		return parseNumber(text);
	}

	private int parseNumber(String text) {
		if (text.startsWith("%")) {
			text = text.substring(1);
			return Integer.valueOf(text, 16);
		}
		return Integer.parseInt(text);
	}

	private int parseWord(TerminalNode node) {
		String text = node.getText();
		return parseHex(text);
	}

	private Integer parseHex(String text) {
		if (text.startsWith("%")) {
			text = text.substring(1);
		}
		return Integer.valueOf(text, 16);
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

	private int parseNibble(String text) {
		final int value = Integer.parseInt(text);
		if (value < 0 || value > 0xF) {
			throw new NumberFormatException(text);
		}
		return value;
	}

	private boolean isWorkingRegister(int register) {
		return (register & 0xF0) == WORKING_REGISTER_HIGH_NIBBLE;
	}

	private int toByte(int highNibble, int lowNibble) {
		return (highNibble & 0x0F) << 4 | lowNibble & 0x0F;
	}

	private static String getPosition(ParserRuleContext context) {
		final Token start = context.getStart();
		return start.getLine() + ":" + start.getCharPositionInLine();
	}

	private static Map<String, Integer> createJpConditionsMap() {
		final Map<String, Integer> map = new HashMap<>();
		map.put("f", 0);
		map.put("lt", 0x1);
		map.put("le", 0x2);
		map.put("ule", 0x3);
		map.put("ov", 0x4);
		map.put("mi", 0x5);
		map.put("z", 0x6);
		map.put("eq", 0x6);
		map.put("c", 0x7);
		map.put("ult", 0x7);
		map.put("", 0x8);
		map.put("ge", 0x9);
		map.put("gt", 0xA);
		map.put("ugt", 0xB);
		map.put("nov", 0xC);
		map.put("pl", 0xD);
		map.put("nz", 0xE);
		map.put("ne", 0xE);
		map.put("nc", 0xF);
		map.put("uge", 0xF);
		//noinspection Java9CollectionFactory
		return Collections.unmodifiableMap(map);
	}

	// Inner Classes ==========================================================

	public static class SyntaxException extends RuntimeException {
		private final ParserRuleContext context;

		public SyntaxException(String error, ParserRuleContext context) {
			super(error);
			this.context = context;
		}

		@Override
		public String getMessage() {
			return getPosition() + " " + super.getMessage();
		}

		public String getPosition() {
			return Assembler.getPosition(context);
		}
	}

	private static class LabelAddress {
		private final int address;
		private final int line;
		private final int column;

		private boolean used;

		public LabelAddress(int address, int line, int column) {
			this.address = address;
			this.line = line;
			this.column = column;
		}

		public int readValue() {
			used = true;
			return address;
		}

		public boolean isUnused() {
			return !used;
		}

		public void updateFrom(LabelAddress other) {
			used = other.used;
		}

		public boolean hasDifferentAddressThan(LabelAddress otherAdress) {
			return address != otherAdress.address;
		}
	}
}
