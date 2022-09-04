// Generated from grammar/Z8Asm.g4 by ANTLR 4.7.2
package com.syntevo.antlr.z8;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link Z8AsmParser}.
 */
public interface Z8AsmListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#root}.
	 * @param ctx the parse tree
	 */
	void enterRoot(Z8AsmParser.RootContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#root}.
	 * @param ctx the parse tree
	 */
	void exitRoot(Z8AsmParser.RootContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#code}.
	 * @param ctx the parse tree
	 */
	void enterCode(Z8AsmParser.CodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#code}.
	 * @param ctx the parse tree
	 */
	void exitCode(Z8AsmParser.CodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#labelDefinition}.
	 * @param ctx the parse tree
	 */
	void enterLabelDefinition(Z8AsmParser.LabelDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#labelDefinition}.
	 * @param ctx the parse tree
	 */
	void exitLabelDefinition(Z8AsmParser.LabelDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#address}.
	 * @param ctx the parse tree
	 */
	void enterAddress(Z8AsmParser.AddressContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#address}.
	 * @param ctx the parse tree
	 */
	void exitAddress(Z8AsmParser.AddressContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#parserInstruction}.
	 * @param ctx the parse tree
	 */
	void enterParserInstruction(Z8AsmParser.ParserInstructionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#parserInstruction}.
	 * @param ctx the parse tree
	 */
	void exitParserInstruction(Z8AsmParser.ParserInstructionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommand(Z8AsmParser.CommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommand(Z8AsmParser.CommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#defConst}.
	 * @param ctx the parse tree
	 */
	void enterDefConst(Z8AsmParser.DefConstContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#defConst}.
	 * @param ctx the parse tree
	 */
	void exitDefConst(Z8AsmParser.DefConstContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#data}.
	 * @param ctx the parse tree
	 */
	void enterData(Z8AsmParser.DataContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#data}.
	 * @param ctx the parse tree
	 */
	void exitData(Z8AsmParser.DataContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dataByte}
	 * labeled alternative in {@link Z8AsmParser#dataItem}.
	 * @param ctx the parse tree
	 */
	void enterDataByte(Z8AsmParser.DataByteContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dataByte}
	 * labeled alternative in {@link Z8AsmParser#dataItem}.
	 * @param ctx the parse tree
	 */
	void exitDataByte(Z8AsmParser.DataByteContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dataAddress}
	 * labeled alternative in {@link Z8AsmParser#dataItem}.
	 * @param ctx the parse tree
	 */
	void enterDataAddress(Z8AsmParser.DataAddressContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dataAddress}
	 * labeled alternative in {@link Z8AsmParser#dataItem}.
	 * @param ctx the parse tree
	 */
	void exitDataAddress(Z8AsmParser.DataAddressContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dataLString}
	 * labeled alternative in {@link Z8AsmParser#dataItem}.
	 * @param ctx the parse tree
	 */
	void enterDataLString(Z8AsmParser.DataLStringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dataLString}
	 * labeled alternative in {@link Z8AsmParser#dataItem}.
	 * @param ctx the parse tree
	 */
	void exitDataLString(Z8AsmParser.DataLStringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dataString}
	 * labeled alternative in {@link Z8AsmParser#dataItem}.
	 * @param ctx the parse tree
	 */
	void enterDataString(Z8AsmParser.DataStringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dataString}
	 * labeled alternative in {@link Z8AsmParser#dataItem}.
	 * @param ctx the parse tree
	 */
	void exitDataString(Z8AsmParser.DataStringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dataChar}
	 * labeled alternative in {@link Z8AsmParser#dataItem}.
	 * @param ctx the parse tree
	 */
	void enterDataChar(Z8AsmParser.DataCharContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dataChar}
	 * labeled alternative in {@link Z8AsmParser#dataItem}.
	 * @param ctx the parse tree
	 */
	void exitDataChar(Z8AsmParser.DataCharContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#org}.
	 * @param ctx the parse tree
	 */
	void enterOrg(Z8AsmParser.OrgContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#org}.
	 * @param ctx the parse tree
	 */
	void exitOrg(Z8AsmParser.OrgContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#repeat}.
	 * @param ctx the parse tree
	 */
	void enterRepeat(Z8AsmParser.RepeatContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#repeat}.
	 * @param ctx the parse tree
	 */
	void exitRepeat(Z8AsmParser.RepeatContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#repeatInstructions}.
	 * @param ctx the parse tree
	 */
	void enterRepeatInstructions(Z8AsmParser.RepeatInstructionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#repeatInstructions}.
	 * @param ctx the parse tree
	 */
	void exitRepeatInstructions(Z8AsmParser.RepeatInstructionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#dec}.
	 * @param ctx the parse tree
	 */
	void enterDec(Z8AsmParser.DecContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#dec}.
	 * @param ctx the parse tree
	 */
	void exitDec(Z8AsmParser.DecContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#rlc}.
	 * @param ctx the parse tree
	 */
	void enterRlc(Z8AsmParser.RlcContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#rlc}.
	 * @param ctx the parse tree
	 */
	void exitRlc(Z8AsmParser.RlcContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#inc}.
	 * @param ctx the parse tree
	 */
	void enterInc(Z8AsmParser.IncContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#inc}.
	 * @param ctx the parse tree
	 */
	void exitInc(Z8AsmParser.IncContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#da}.
	 * @param ctx the parse tree
	 */
	void enterDa(Z8AsmParser.DaContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#da}.
	 * @param ctx the parse tree
	 */
	void exitDa(Z8AsmParser.DaContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#pop}.
	 * @param ctx the parse tree
	 */
	void enterPop(Z8AsmParser.PopContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#pop}.
	 * @param ctx the parse tree
	 */
	void exitPop(Z8AsmParser.PopContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#com}.
	 * @param ctx the parse tree
	 */
	void enterCom(Z8AsmParser.ComContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#com}.
	 * @param ctx the parse tree
	 */
	void exitCom(Z8AsmParser.ComContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#push}.
	 * @param ctx the parse tree
	 */
	void enterPush(Z8AsmParser.PushContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#push}.
	 * @param ctx the parse tree
	 */
	void exitPush(Z8AsmParser.PushContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#decw}.
	 * @param ctx the parse tree
	 */
	void enterDecw(Z8AsmParser.DecwContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#decw}.
	 * @param ctx the parse tree
	 */
	void exitDecw(Z8AsmParser.DecwContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#rl}.
	 * @param ctx the parse tree
	 */
	void enterRl(Z8AsmParser.RlContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#rl}.
	 * @param ctx the parse tree
	 */
	void exitRl(Z8AsmParser.RlContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#incw}.
	 * @param ctx the parse tree
	 */
	void enterIncw(Z8AsmParser.IncwContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#incw}.
	 * @param ctx the parse tree
	 */
	void exitIncw(Z8AsmParser.IncwContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#clr}.
	 * @param ctx the parse tree
	 */
	void enterClr(Z8AsmParser.ClrContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#clr}.
	 * @param ctx the parse tree
	 */
	void exitClr(Z8AsmParser.ClrContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#rrc}.
	 * @param ctx the parse tree
	 */
	void enterRrc(Z8AsmParser.RrcContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#rrc}.
	 * @param ctx the parse tree
	 */
	void exitRrc(Z8AsmParser.RrcContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#sra}.
	 * @param ctx the parse tree
	 */
	void enterSra(Z8AsmParser.SraContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#sra}.
	 * @param ctx the parse tree
	 */
	void exitSra(Z8AsmParser.SraContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#rr}.
	 * @param ctx the parse tree
	 */
	void enterRr(Z8AsmParser.RrContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#rr}.
	 * @param ctx the parse tree
	 */
	void exitRr(Z8AsmParser.RrContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#swap}.
	 * @param ctx the parse tree
	 */
	void enterSwap(Z8AsmParser.SwapContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#swap}.
	 * @param ctx the parse tree
	 */
	void exitSwap(Z8AsmParser.SwapContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#srp}.
	 * @param ctx the parse tree
	 */
	void enterSrp(Z8AsmParser.SrpContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#srp}.
	 * @param ctx the parse tree
	 */
	void exitSrp(Z8AsmParser.SrpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code callAddress}
	 * labeled alternative in {@link Z8AsmParser#call}.
	 * @param ctx the parse tree
	 */
	void enterCallAddress(Z8AsmParser.CallAddressContext ctx);
	/**
	 * Exit a parse tree produced by the {@code callAddress}
	 * labeled alternative in {@link Z8AsmParser#call}.
	 * @param ctx the parse tree
	 */
	void exitCallAddress(Z8AsmParser.CallAddressContext ctx);
	/**
	 * Enter a parse tree produced by the {@code callIreg}
	 * labeled alternative in {@link Z8AsmParser#call}.
	 * @param ctx the parse tree
	 */
	void enterCallIreg(Z8AsmParser.CallIregContext ctx);
	/**
	 * Exit a parse tree produced by the {@code callIreg}
	 * labeled alternative in {@link Z8AsmParser#call}.
	 * @param ctx the parse tree
	 */
	void exitCallIreg(Z8AsmParser.CallIregContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#djnz}.
	 * @param ctx the parse tree
	 */
	void enterDjnz(Z8AsmParser.DjnzContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#djnz}.
	 * @param ctx the parse tree
	 */
	void exitDjnz(Z8AsmParser.DjnzContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jpIReg}
	 * labeled alternative in {@link Z8AsmParser#jp}.
	 * @param ctx the parse tree
	 */
	void enterJpIReg(Z8AsmParser.JpIRegContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jpIReg}
	 * labeled alternative in {@link Z8AsmParser#jp}.
	 * @param ctx the parse tree
	 */
	void exitJpIReg(Z8AsmParser.JpIRegContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jpAddress}
	 * labeled alternative in {@link Z8AsmParser#jp}.
	 * @param ctx the parse tree
	 */
	void enterJpAddress(Z8AsmParser.JpAddressContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jpAddress}
	 * labeled alternative in {@link Z8AsmParser#jp}.
	 * @param ctx the parse tree
	 */
	void exitJpAddress(Z8AsmParser.JpAddressContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jpConditionAddress}
	 * labeled alternative in {@link Z8AsmParser#jp}.
	 * @param ctx the parse tree
	 */
	void enterJpConditionAddress(Z8AsmParser.JpConditionAddressContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jpConditionAddress}
	 * labeled alternative in {@link Z8AsmParser#jp}.
	 * @param ctx the parse tree
	 */
	void exitJpConditionAddress(Z8AsmParser.JpConditionAddressContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#jr}.
	 * @param ctx the parse tree
	 */
	void enterJr(Z8AsmParser.JrContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#jr}.
	 * @param ctx the parse tree
	 */
	void exitJr(Z8AsmParser.JrContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#jpr}.
	 * @param ctx the parse tree
	 */
	void enterJpr(Z8AsmParser.JprContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#jpr}.
	 * @param ctx the parse tree
	 */
	void exitJpr(Z8AsmParser.JprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ld1}
	 * labeled alternative in {@link Z8AsmParser#ld}.
	 * @param ctx the parse tree
	 */
	void enterLd1(Z8AsmParser.Ld1Context ctx);
	/**
	 * Exit a parse tree produced by the {@code ld1}
	 * labeled alternative in {@link Z8AsmParser#ld}.
	 * @param ctx the parse tree
	 */
	void exitLd1(Z8AsmParser.Ld1Context ctx);
	/**
	 * Enter a parse tree produced by the {@code ld2}
	 * labeled alternative in {@link Z8AsmParser#ld}.
	 * @param ctx the parse tree
	 */
	void enterLd2(Z8AsmParser.Ld2Context ctx);
	/**
	 * Exit a parse tree produced by the {@code ld2}
	 * labeled alternative in {@link Z8AsmParser#ld}.
	 * @param ctx the parse tree
	 */
	void exitLd2(Z8AsmParser.Ld2Context ctx);
	/**
	 * Enter a parse tree produced by the {@code ld3}
	 * labeled alternative in {@link Z8AsmParser#ld}.
	 * @param ctx the parse tree
	 */
	void enterLd3(Z8AsmParser.Ld3Context ctx);
	/**
	 * Exit a parse tree produced by the {@code ld3}
	 * labeled alternative in {@link Z8AsmParser#ld}.
	 * @param ctx the parse tree
	 */
	void exitLd3(Z8AsmParser.Ld3Context ctx);
	/**
	 * Enter a parse tree produced by the {@code ld4}
	 * labeled alternative in {@link Z8AsmParser#ld}.
	 * @param ctx the parse tree
	 */
	void enterLd4(Z8AsmParser.Ld4Context ctx);
	/**
	 * Exit a parse tree produced by the {@code ld4}
	 * labeled alternative in {@link Z8AsmParser#ld}.
	 * @param ctx the parse tree
	 */
	void exitLd4(Z8AsmParser.Ld4Context ctx);
	/**
	 * Enter a parse tree produced by the {@code ld5}
	 * labeled alternative in {@link Z8AsmParser#ld}.
	 * @param ctx the parse tree
	 */
	void enterLd5(Z8AsmParser.Ld5Context ctx);
	/**
	 * Exit a parse tree produced by the {@code ld5}
	 * labeled alternative in {@link Z8AsmParser#ld}.
	 * @param ctx the parse tree
	 */
	void exitLd5(Z8AsmParser.Ld5Context ctx);
	/**
	 * Enter a parse tree produced by the {@code ld6}
	 * labeled alternative in {@link Z8AsmParser#ld}.
	 * @param ctx the parse tree
	 */
	void enterLd6(Z8AsmParser.Ld6Context ctx);
	/**
	 * Exit a parse tree produced by the {@code ld6}
	 * labeled alternative in {@link Z8AsmParser#ld}.
	 * @param ctx the parse tree
	 */
	void exitLd6(Z8AsmParser.Ld6Context ctx);
	/**
	 * Enter a parse tree produced by the {@code ldc1}
	 * labeled alternative in {@link Z8AsmParser#ldc}.
	 * @param ctx the parse tree
	 */
	void enterLdc1(Z8AsmParser.Ldc1Context ctx);
	/**
	 * Exit a parse tree produced by the {@code ldc1}
	 * labeled alternative in {@link Z8AsmParser#ldc}.
	 * @param ctx the parse tree
	 */
	void exitLdc1(Z8AsmParser.Ldc1Context ctx);
	/**
	 * Enter a parse tree produced by the {@code ldc2}
	 * labeled alternative in {@link Z8AsmParser#ldc}.
	 * @param ctx the parse tree
	 */
	void enterLdc2(Z8AsmParser.Ldc2Context ctx);
	/**
	 * Exit a parse tree produced by the {@code ldc2}
	 * labeled alternative in {@link Z8AsmParser#ldc}.
	 * @param ctx the parse tree
	 */
	void exitLdc2(Z8AsmParser.Ldc2Context ctx);
	/**
	 * Enter a parse tree produced by the {@code ldci1}
	 * labeled alternative in {@link Z8AsmParser#ldci}.
	 * @param ctx the parse tree
	 */
	void enterLdci1(Z8AsmParser.Ldci1Context ctx);
	/**
	 * Exit a parse tree produced by the {@code ldci1}
	 * labeled alternative in {@link Z8AsmParser#ldci}.
	 * @param ctx the parse tree
	 */
	void exitLdci1(Z8AsmParser.Ldci1Context ctx);
	/**
	 * Enter a parse tree produced by the {@code ldci2}
	 * labeled alternative in {@link Z8AsmParser#ldci}.
	 * @param ctx the parse tree
	 */
	void enterLdci2(Z8AsmParser.Ldci2Context ctx);
	/**
	 * Exit a parse tree produced by the {@code ldci2}
	 * labeled alternative in {@link Z8AsmParser#ldci}.
	 * @param ctx the parse tree
	 */
	void exitLdci2(Z8AsmParser.Ldci2Context ctx);
	/**
	 * Enter a parse tree produced by the {@code lde1}
	 * labeled alternative in {@link Z8AsmParser#lde}.
	 * @param ctx the parse tree
	 */
	void enterLde1(Z8AsmParser.Lde1Context ctx);
	/**
	 * Exit a parse tree produced by the {@code lde1}
	 * labeled alternative in {@link Z8AsmParser#lde}.
	 * @param ctx the parse tree
	 */
	void exitLde1(Z8AsmParser.Lde1Context ctx);
	/**
	 * Enter a parse tree produced by the {@code lde2}
	 * labeled alternative in {@link Z8AsmParser#lde}.
	 * @param ctx the parse tree
	 */
	void enterLde2(Z8AsmParser.Lde2Context ctx);
	/**
	 * Exit a parse tree produced by the {@code lde2}
	 * labeled alternative in {@link Z8AsmParser#lde}.
	 * @param ctx the parse tree
	 */
	void exitLde2(Z8AsmParser.Lde2Context ctx);
	/**
	 * Enter a parse tree produced by the {@code ldei1}
	 * labeled alternative in {@link Z8AsmParser#ldei}.
	 * @param ctx the parse tree
	 */
	void enterLdei1(Z8AsmParser.Ldei1Context ctx);
	/**
	 * Exit a parse tree produced by the {@code ldei1}
	 * labeled alternative in {@link Z8AsmParser#ldei}.
	 * @param ctx the parse tree
	 */
	void exitLdei1(Z8AsmParser.Ldei1Context ctx);
	/**
	 * Enter a parse tree produced by the {@code ldei2}
	 * labeled alternative in {@link Z8AsmParser#ldei}.
	 * @param ctx the parse tree
	 */
	void enterLdei2(Z8AsmParser.Ldei2Context ctx);
	/**
	 * Exit a parse tree produced by the {@code ldei2}
	 * labeled alternative in {@link Z8AsmParser#ldei}.
	 * @param ctx the parse tree
	 */
	void exitLdei2(Z8AsmParser.Ldei2Context ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#di}.
	 * @param ctx the parse tree
	 */
	void enterDi(Z8AsmParser.DiContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#di}.
	 * @param ctx the parse tree
	 */
	void exitDi(Z8AsmParser.DiContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#ei}.
	 * @param ctx the parse tree
	 */
	void enterEi(Z8AsmParser.EiContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#ei}.
	 * @param ctx the parse tree
	 */
	void exitEi(Z8AsmParser.EiContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#ret}.
	 * @param ctx the parse tree
	 */
	void enterRet(Z8AsmParser.RetContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#ret}.
	 * @param ctx the parse tree
	 */
	void exitRet(Z8AsmParser.RetContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#iret}.
	 * @param ctx the parse tree
	 */
	void enterIret(Z8AsmParser.IretContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#iret}.
	 * @param ctx the parse tree
	 */
	void exitIret(Z8AsmParser.IretContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#rcf}.
	 * @param ctx the parse tree
	 */
	void enterRcf(Z8AsmParser.RcfContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#rcf}.
	 * @param ctx the parse tree
	 */
	void exitRcf(Z8AsmParser.RcfContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#scf}.
	 * @param ctx the parse tree
	 */
	void enterScf(Z8AsmParser.ScfContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#scf}.
	 * @param ctx the parse tree
	 */
	void exitScf(Z8AsmParser.ScfContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#ccf}.
	 * @param ctx the parse tree
	 */
	void enterCcf(Z8AsmParser.CcfContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#ccf}.
	 * @param ctx the parse tree
	 */
	void exitCcf(Z8AsmParser.CcfContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#nop}.
	 * @param ctx the parse tree
	 */
	void enterNop(Z8AsmParser.NopContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#nop}.
	 * @param ctx the parse tree
	 */
	void exitNop(Z8AsmParser.NopContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#adc}.
	 * @param ctx the parse tree
	 */
	void enterAdc(Z8AsmParser.AdcContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#adc}.
	 * @param ctx the parse tree
	 */
	void exitAdc(Z8AsmParser.AdcContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#add}.
	 * @param ctx the parse tree
	 */
	void enterAdd(Z8AsmParser.AddContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#add}.
	 * @param ctx the parse tree
	 */
	void exitAdd(Z8AsmParser.AddContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#and}.
	 * @param ctx the parse tree
	 */
	void enterAnd(Z8AsmParser.AndContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#and}.
	 * @param ctx the parse tree
	 */
	void exitAnd(Z8AsmParser.AndContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#cp}.
	 * @param ctx the parse tree
	 */
	void enterCp(Z8AsmParser.CpContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#cp}.
	 * @param ctx the parse tree
	 */
	void exitCp(Z8AsmParser.CpContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#or}.
	 * @param ctx the parse tree
	 */
	void enterOr(Z8AsmParser.OrContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#or}.
	 * @param ctx the parse tree
	 */
	void exitOr(Z8AsmParser.OrContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#sbc}.
	 * @param ctx the parse tree
	 */
	void enterSbc(Z8AsmParser.SbcContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#sbc}.
	 * @param ctx the parse tree
	 */
	void exitSbc(Z8AsmParser.SbcContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#sub}.
	 * @param ctx the parse tree
	 */
	void enterSub(Z8AsmParser.SubContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#sub}.
	 * @param ctx the parse tree
	 */
	void exitSub(Z8AsmParser.SubContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#tcm}.
	 * @param ctx the parse tree
	 */
	void enterTcm(Z8AsmParser.TcmContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#tcm}.
	 * @param ctx the parse tree
	 */
	void exitTcm(Z8AsmParser.TcmContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#tm}.
	 * @param ctx the parse tree
	 */
	void enterTm(Z8AsmParser.TmContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#tm}.
	 * @param ctx the parse tree
	 */
	void exitTm(Z8AsmParser.TmContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#xor}.
	 * @param ctx the parse tree
	 */
	void enterXor(Z8AsmParser.XorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#xor}.
	 * @param ctx the parse tree
	 */
	void exitXor(Z8AsmParser.XorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#arithmeticParameters}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticParameters(Z8AsmParser.ArithmeticParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#arithmeticParameters}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticParameters(Z8AsmParser.ArithmeticParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#arithmeticParameters1}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticParameters1(Z8AsmParser.ArithmeticParameters1Context ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#arithmeticParameters1}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticParameters1(Z8AsmParser.ArithmeticParameters1Context ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#arithmeticParameters2}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticParameters2(Z8AsmParser.ArithmeticParameters2Context ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#arithmeticParameters2}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticParameters2(Z8AsmParser.ArithmeticParameters2Context ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#arithmeticParameters3}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticParameters3(Z8AsmParser.ArithmeticParameters3Context ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#arithmeticParameters3}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticParameters3(Z8AsmParser.ArithmeticParameters3Context ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#arithmeticParameters4}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticParameters4(Z8AsmParser.ArithmeticParameters4Context ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#arithmeticParameters4}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticParameters4(Z8AsmParser.ArithmeticParameters4Context ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#registerOrIregister}.
	 * @param ctx the parse tree
	 */
	void enterRegisterOrIregister(Z8AsmParser.RegisterOrIregisterContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#registerOrIregister}.
	 * @param ctx the parse tree
	 */
	void exitRegisterOrIregister(Z8AsmParser.RegisterOrIregisterContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#register}.
	 * @param ctx the parse tree
	 */
	void enterRegister(Z8AsmParser.RegisterContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#register}.
	 * @param ctx the parse tree
	 */
	void exitRegister(Z8AsmParser.RegisterContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#iregister}.
	 * @param ctx the parse tree
	 */
	void enterIregister(Z8AsmParser.IregisterContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#iregister}.
	 * @param ctx the parse tree
	 */
	void exitIregister(Z8AsmParser.IregisterContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#iregisterPair}.
	 * @param ctx the parse tree
	 */
	void enterIregisterPair(Z8AsmParser.IregisterPairContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#iregisterPair}.
	 * @param ctx the parse tree
	 */
	void exitIregisterPair(Z8AsmParser.IregisterPairContext ctx);
	/**
	 * Enter a parse tree produced by {@link Z8AsmParser#valueByte}.
	 * @param ctx the parse tree
	 */
	void enterValueByte(Z8AsmParser.ValueByteContext ctx);
	/**
	 * Exit a parse tree produced by {@link Z8AsmParser#valueByte}.
	 * @param ctx the parse tree
	 */
	void exitValueByte(Z8AsmParser.ValueByteContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprNumber}
	 * labeled alternative in {@link Z8AsmParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprNumber(Z8AsmParser.ExprNumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprNumber}
	 * labeled alternative in {@link Z8AsmParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprNumber(Z8AsmParser.ExprNumberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprChar}
	 * labeled alternative in {@link Z8AsmParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprChar(Z8AsmParser.ExprCharContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprChar}
	 * labeled alternative in {@link Z8AsmParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprChar(Z8AsmParser.ExprCharContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprIdentifier}
	 * labeled alternative in {@link Z8AsmParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprIdentifier(Z8AsmParser.ExprIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprIdentifier}
	 * labeled alternative in {@link Z8AsmParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprIdentifier(Z8AsmParser.ExprIdentifierContext ctx);
}