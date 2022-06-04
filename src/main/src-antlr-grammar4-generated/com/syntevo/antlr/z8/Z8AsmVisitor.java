// Generated from grammar/Z8Asm.g4 by ANTLR 4.7.2
package com.syntevo.antlr.z8;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link Z8AsmParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface Z8AsmVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#code}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCode(Z8AsmParser.CodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#labelDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabelDefinition(Z8AsmParser.LabelDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#address}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddress(Z8AsmParser.AddressContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#parserInstruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParserInstruction(Z8AsmParser.ParserInstructionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand(Z8AsmParser.CommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#defConst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefConst(Z8AsmParser.DefConstContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#data}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitData(Z8AsmParser.DataContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dataByte}
	 * labeled alternative in {@link Z8AsmParser#dataItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataByte(Z8AsmParser.DataByteContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dataAddress}
	 * labeled alternative in {@link Z8AsmParser#dataItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataAddress(Z8AsmParser.DataAddressContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dataLString}
	 * labeled alternative in {@link Z8AsmParser#dataItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataLString(Z8AsmParser.DataLStringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dataString}
	 * labeled alternative in {@link Z8AsmParser#dataItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataString(Z8AsmParser.DataStringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dataChar}
	 * labeled alternative in {@link Z8AsmParser#dataItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataChar(Z8AsmParser.DataCharContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#org}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrg(Z8AsmParser.OrgContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#dec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDec(Z8AsmParser.DecContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#rlc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRlc(Z8AsmParser.RlcContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#inc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInc(Z8AsmParser.IncContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#da}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDa(Z8AsmParser.DaContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#pop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPop(Z8AsmParser.PopContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#com}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCom(Z8AsmParser.ComContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#push}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPush(Z8AsmParser.PushContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#decw}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecw(Z8AsmParser.DecwContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#rl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRl(Z8AsmParser.RlContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#incw}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncw(Z8AsmParser.IncwContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#clr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClr(Z8AsmParser.ClrContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#rrc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRrc(Z8AsmParser.RrcContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#sra}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSra(Z8AsmParser.SraContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#rr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRr(Z8AsmParser.RrContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#swap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwap(Z8AsmParser.SwapContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#srp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSrp(Z8AsmParser.SrpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code callAddress}
	 * labeled alternative in {@link Z8AsmParser#call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallAddress(Z8AsmParser.CallAddressContext ctx);
	/**
	 * Visit a parse tree produced by the {@code callIreg}
	 * labeled alternative in {@link Z8AsmParser#call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallIreg(Z8AsmParser.CallIregContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#djnz}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDjnz(Z8AsmParser.DjnzContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jpIReg}
	 * labeled alternative in {@link Z8AsmParser#jp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJpIReg(Z8AsmParser.JpIRegContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jpAddress}
	 * labeled alternative in {@link Z8AsmParser#jp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJpAddress(Z8AsmParser.JpAddressContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jpConditionAddress}
	 * labeled alternative in {@link Z8AsmParser#jp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJpConditionAddress(Z8AsmParser.JpConditionAddressContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#jr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJr(Z8AsmParser.JrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ld1}
	 * labeled alternative in {@link Z8AsmParser#ld}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLd1(Z8AsmParser.Ld1Context ctx);
	/**
	 * Visit a parse tree produced by the {@code ld2}
	 * labeled alternative in {@link Z8AsmParser#ld}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLd2(Z8AsmParser.Ld2Context ctx);
	/**
	 * Visit a parse tree produced by the {@code ld3}
	 * labeled alternative in {@link Z8AsmParser#ld}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLd3(Z8AsmParser.Ld3Context ctx);
	/**
	 * Visit a parse tree produced by the {@code ld4}
	 * labeled alternative in {@link Z8AsmParser#ld}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLd4(Z8AsmParser.Ld4Context ctx);
	/**
	 * Visit a parse tree produced by the {@code ldc1}
	 * labeled alternative in {@link Z8AsmParser#ldc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLdc1(Z8AsmParser.Ldc1Context ctx);
	/**
	 * Visit a parse tree produced by the {@code ldc2}
	 * labeled alternative in {@link Z8AsmParser#ldc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLdc2(Z8AsmParser.Ldc2Context ctx);
	/**
	 * Visit a parse tree produced by the {@code ldci1}
	 * labeled alternative in {@link Z8AsmParser#ldci}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLdci1(Z8AsmParser.Ldci1Context ctx);
	/**
	 * Visit a parse tree produced by the {@code ldci2}
	 * labeled alternative in {@link Z8AsmParser#ldci}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLdci2(Z8AsmParser.Ldci2Context ctx);
	/**
	 * Visit a parse tree produced by the {@code lde1}
	 * labeled alternative in {@link Z8AsmParser#lde}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLde1(Z8AsmParser.Lde1Context ctx);
	/**
	 * Visit a parse tree produced by the {@code lde2}
	 * labeled alternative in {@link Z8AsmParser#lde}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLde2(Z8AsmParser.Lde2Context ctx);
	/**
	 * Visit a parse tree produced by the {@code ldei1}
	 * labeled alternative in {@link Z8AsmParser#ldei}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLdei1(Z8AsmParser.Ldei1Context ctx);
	/**
	 * Visit a parse tree produced by the {@code ldei2}
	 * labeled alternative in {@link Z8AsmParser#ldei}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLdei2(Z8AsmParser.Ldei2Context ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#di}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDi(Z8AsmParser.DiContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#ei}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEi(Z8AsmParser.EiContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#ret}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRet(Z8AsmParser.RetContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#iret}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIret(Z8AsmParser.IretContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#rcf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRcf(Z8AsmParser.RcfContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#scf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScf(Z8AsmParser.ScfContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#ccf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCcf(Z8AsmParser.CcfContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#nop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNop(Z8AsmParser.NopContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#adc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdc(Z8AsmParser.AdcContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#add}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdd(Z8AsmParser.AddContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#and}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd(Z8AsmParser.AndContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#cp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCp(Z8AsmParser.CpContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#or}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr(Z8AsmParser.OrContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#sbc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSbc(Z8AsmParser.SbcContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#sub}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSub(Z8AsmParser.SubContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#tcm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTcm(Z8AsmParser.TcmContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#tm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTm(Z8AsmParser.TmContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#xor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXor(Z8AsmParser.XorContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#arithmeticParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticParameters(Z8AsmParser.ArithmeticParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#arithmeticParameters1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticParameters1(Z8AsmParser.ArithmeticParameters1Context ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#arithmeticParameters2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticParameters2(Z8AsmParser.ArithmeticParameters2Context ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#arithmeticParameters3}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticParameters3(Z8AsmParser.ArithmeticParameters3Context ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#arithmeticParameters4}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticParameters4(Z8AsmParser.ArithmeticParameters4Context ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#registerOrIregister}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegisterOrIregister(Z8AsmParser.RegisterOrIregisterContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#register}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegister(Z8AsmParser.RegisterContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#iregister}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIregister(Z8AsmParser.IregisterContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#iregisterPair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIregisterPair(Z8AsmParser.IregisterPairContext ctx);
	/**
	 * Visit a parse tree produced by {@link Z8AsmParser#valueByte}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueByte(Z8AsmParser.ValueByteContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprNumber}
	 * labeled alternative in {@link Z8AsmParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprNumber(Z8AsmParser.ExprNumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprChar}
	 * labeled alternative in {@link Z8AsmParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprChar(Z8AsmParser.ExprCharContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprIdentifier}
	 * labeled alternative in {@link Z8AsmParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprIdentifier(Z8AsmParser.ExprIdentifierContext ctx);
}