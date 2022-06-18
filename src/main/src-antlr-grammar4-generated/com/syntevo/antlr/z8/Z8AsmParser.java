// Generated from grammar/Z8Asm.g4 by ANTLR 4.7.2
package com.syntevo.antlr.z8;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Z8AsmParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, LString=5, String=6, Char=7, Byte=8, Word=9, 
		WorkingRegister=10, IndirectPrefix=11, IWorkingRegister=12, IWorkingRegisterPair=13, 
		Colon=14, Comma=15, Const=16, Data=17, Org=18, Adc=19, Add=20, And=21, 
		Call=22, Ccf=23, Clr=24, Com=25, Cp=26, Da=27, Dec=28, Decw=29, Di=30, 
		Djnz=31, Ei=32, Inc=33, Incw=34, Iret=35, Jp=36, Jr=37, Ld=38, Ldc=39, 
		Ldci=40, Lde=41, Ldei=42, Nop=43, Or=44, Pop=45, Push=46, Rcf=47, Ret=48, 
		Rl=49, Rlc=50, Rr=51, Rrc=52, Sbc=53, Scf=54, Sra=55, Srp=56, Sub=57, 
		Swap=58, Tcm=59, Tm=60, Xor=61, JpCondition=62, Identifier=63, Whitespace=64, 
		NL=65, LineComment=66, BlockComment=67;
	public static final int
		RULE_root = 0, RULE_code = 1, RULE_labelDefinition = 2, RULE_address = 3, 
		RULE_parserInstruction = 4, RULE_command = 5, RULE_defConst = 6, RULE_data = 7, 
		RULE_dataItem = 8, RULE_org = 9, RULE_dec = 10, RULE_rlc = 11, RULE_inc = 12, 
		RULE_da = 13, RULE_pop = 14, RULE_com = 15, RULE_push = 16, RULE_decw = 17, 
		RULE_rl = 18, RULE_incw = 19, RULE_clr = 20, RULE_rrc = 21, RULE_sra = 22, 
		RULE_rr = 23, RULE_swap = 24, RULE_srp = 25, RULE_call = 26, RULE_djnz = 27, 
		RULE_jp = 28, RULE_jr = 29, RULE_ld = 30, RULE_ldc = 31, RULE_ldci = 32, 
		RULE_lde = 33, RULE_ldei = 34, RULE_di = 35, RULE_ei = 36, RULE_ret = 37, 
		RULE_iret = 38, RULE_rcf = 39, RULE_scf = 40, RULE_ccf = 41, RULE_nop = 42, 
		RULE_adc = 43, RULE_add = 44, RULE_and = 45, RULE_cp = 46, RULE_or = 47, 
		RULE_sbc = 48, RULE_sub = 49, RULE_tcm = 50, RULE_tm = 51, RULE_xor = 52, 
		RULE_arithmeticParameters = 53, RULE_arithmeticParameters1 = 54, RULE_arithmeticParameters2 = 55, 
		RULE_arithmeticParameters3 = 56, RULE_arithmeticParameters4 = 57, RULE_registerOrIregister = 58, 
		RULE_register = 59, RULE_iregister = 60, RULE_iregisterPair = 61, RULE_valueByte = 62, 
		RULE_expression = 63;
	private static String[] makeRuleNames() {
		return new String[] {
			"root", "code", "labelDefinition", "address", "parserInstruction", "command", 
			"defConst", "data", "dataItem", "org", "dec", "rlc", "inc", "da", "pop", 
			"com", "push", "decw", "rl", "incw", "clr", "rrc", "sra", "rr", "swap", 
			"srp", "call", "djnz", "jp", "jr", "ld", "ldc", "ldci", "lde", "ldei", 
			"di", "ei", "ret", "iret", "rcf", "scf", "ccf", "nop", "adc", "add", 
			"and", "cp", "or", "sbc", "sub", "tcm", "tm", "xor", "arithmeticParameters", 
			"arithmeticParameters1", "arithmeticParameters2", "arithmeticParameters3", 
			"arithmeticParameters4", "registerOrIregister", "register", "iregister", 
			"iregisterPair", "valueByte", "expression"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'='", "'('", "')'", "'#'", null, null, null, null, null, null, 
			"'@'", null, null, "':'", "','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, "LString", "String", "Char", "Byte", "Word", 
			"WorkingRegister", "IndirectPrefix", "IWorkingRegister", "IWorkingRegisterPair", 
			"Colon", "Comma", "Const", "Data", "Org", "Adc", "Add", "And", "Call", 
			"Ccf", "Clr", "Com", "Cp", "Da", "Dec", "Decw", "Di", "Djnz", "Ei", "Inc", 
			"Incw", "Iret", "Jp", "Jr", "Ld", "Ldc", "Ldci", "Lde", "Ldei", "Nop", 
			"Or", "Pop", "Push", "Rcf", "Ret", "Rl", "Rlc", "Rr", "Rrc", "Sbc", "Scf", 
			"Sra", "Srp", "Sub", "Swap", "Tcm", "Tm", "Xor", "JpCondition", "Identifier", 
			"Whitespace", "NL", "LineComment", "BlockComment"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Z8Asm.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public Z8AsmParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class RootContext extends ParserRuleContext {
		public List<CodeContext> code() {
			return getRuleContexts(CodeContext.class);
		}
		public CodeContext code(int i) {
			return getRuleContext(CodeContext.class,i);
		}
		public List<TerminalNode> NL() { return getTokens(Z8AsmParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(Z8AsmParser.NL, i);
		}
		public RootContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_root; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterRoot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitRoot(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitRoot(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RootContext root() throws RecognitionException {
		RootContext _localctx = new RootContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_root);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 16)) & ~0x3f) == 0 && ((1L << (_la - 16)) & ((1L << (Const - 16)) | (1L << (Data - 16)) | (1L << (Org - 16)) | (1L << (Adc - 16)) | (1L << (Add - 16)) | (1L << (And - 16)) | (1L << (Call - 16)) | (1L << (Ccf - 16)) | (1L << (Clr - 16)) | (1L << (Com - 16)) | (1L << (Cp - 16)) | (1L << (Da - 16)) | (1L << (Dec - 16)) | (1L << (Decw - 16)) | (1L << (Di - 16)) | (1L << (Djnz - 16)) | (1L << (Ei - 16)) | (1L << (Inc - 16)) | (1L << (Incw - 16)) | (1L << (Iret - 16)) | (1L << (Jp - 16)) | (1L << (Jr - 16)) | (1L << (Ld - 16)) | (1L << (Ldc - 16)) | (1L << (Ldci - 16)) | (1L << (Lde - 16)) | (1L << (Ldei - 16)) | (1L << (Nop - 16)) | (1L << (Or - 16)) | (1L << (Pop - 16)) | (1L << (Push - 16)) | (1L << (Rcf - 16)) | (1L << (Ret - 16)) | (1L << (Rl - 16)) | (1L << (Rlc - 16)) | (1L << (Rr - 16)) | (1L << (Rrc - 16)) | (1L << (Sbc - 16)) | (1L << (Scf - 16)) | (1L << (Sra - 16)) | (1L << (Srp - 16)) | (1L << (Sub - 16)) | (1L << (Swap - 16)) | (1L << (Tcm - 16)) | (1L << (Tm - 16)) | (1L << (Xor - 16)) | (1L << (Identifier - 16)) | (1L << (NL - 16)))) != 0)) {
				{
				setState(130);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case Const:
				case Data:
				case Org:
				case Adc:
				case Add:
				case And:
				case Call:
				case Ccf:
				case Clr:
				case Com:
				case Cp:
				case Da:
				case Dec:
				case Decw:
				case Di:
				case Djnz:
				case Ei:
				case Inc:
				case Incw:
				case Iret:
				case Jp:
				case Jr:
				case Ld:
				case Ldc:
				case Ldci:
				case Lde:
				case Ldei:
				case Nop:
				case Or:
				case Pop:
				case Push:
				case Rcf:
				case Ret:
				case Rl:
				case Rlc:
				case Rr:
				case Rrc:
				case Sbc:
				case Scf:
				case Sra:
				case Srp:
				case Sub:
				case Swap:
				case Tcm:
				case Tm:
				case Xor:
				case Identifier:
					{
					setState(128);
					code();
					}
					break;
				case NL:
					{
					setState(129);
					match(NL);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(134);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CodeContext extends ParserRuleContext {
		public LabelDefinitionContext labelDefinition() {
			return getRuleContext(LabelDefinitionContext.class,0);
		}
		public ParserInstructionContext parserInstruction() {
			return getRuleContext(ParserInstructionContext.class,0);
		}
		public CommandContext command() {
			return getRuleContext(CommandContext.class,0);
		}
		public CodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_code; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterCode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitCode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitCode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CodeContext code() throws RecognitionException {
		CodeContext _localctx = new CodeContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_code);
		try {
			setState(138);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(135);
				labelDefinition();
				}
				break;
			case Const:
			case Data:
			case Org:
				enterOuterAlt(_localctx, 2);
				{
				setState(136);
				parserInstruction();
				}
				break;
			case Adc:
			case Add:
			case And:
			case Call:
			case Ccf:
			case Clr:
			case Com:
			case Cp:
			case Da:
			case Dec:
			case Decw:
			case Di:
			case Djnz:
			case Ei:
			case Inc:
			case Incw:
			case Iret:
			case Jp:
			case Jr:
			case Ld:
			case Ldc:
			case Ldci:
			case Lde:
			case Ldei:
			case Nop:
			case Or:
			case Pop:
			case Push:
			case Rcf:
			case Ret:
			case Rl:
			case Rlc:
			case Rr:
			case Rrc:
			case Sbc:
			case Scf:
			case Sra:
			case Srp:
			case Sub:
			case Swap:
			case Tcm:
			case Tm:
			case Xor:
				enterOuterAlt(_localctx, 3);
				{
				setState(137);
				command();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LabelDefinitionContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(Z8AsmParser.Identifier, 0); }
		public TerminalNode Colon() { return getToken(Z8AsmParser.Colon, 0); }
		public TerminalNode NL() { return getToken(Z8AsmParser.NL, 0); }
		public LabelDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labelDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLabelDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLabelDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitLabelDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelDefinitionContext labelDefinition() throws RecognitionException {
		LabelDefinitionContext _localctx = new LabelDefinitionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_labelDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			match(Identifier);
			setState(141);
			match(Colon);
			setState(143);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(142);
				match(NL);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AddressContext extends ParserRuleContext {
		public Token label;
		public TerminalNode Word() { return getToken(Z8AsmParser.Word, 0); }
		public TerminalNode Identifier() { return getToken(Z8AsmParser.Identifier, 0); }
		public AddressContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_address; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterAddress(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitAddress(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitAddress(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AddressContext address() throws RecognitionException {
		AddressContext _localctx = new AddressContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_address);
		try {
			setState(147);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Word:
				enterOuterAlt(_localctx, 1);
				{
				setState(145);
				match(Word);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(146);
				((AddressContext)_localctx).label = match(Identifier);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParserInstructionContext extends ParserRuleContext {
		public DefConstContext defConst() {
			return getRuleContext(DefConstContext.class,0);
		}
		public DataContext data() {
			return getRuleContext(DataContext.class,0);
		}
		public OrgContext org() {
			return getRuleContext(OrgContext.class,0);
		}
		public TerminalNode NL() { return getToken(Z8AsmParser.NL, 0); }
		public ParserInstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parserInstruction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterParserInstruction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitParserInstruction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitParserInstruction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParserInstructionContext parserInstruction() throws RecognitionException {
		ParserInstructionContext _localctx = new ParserInstructionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_parserInstruction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Const:
				{
				setState(149);
				defConst();
				}
				break;
			case Data:
				{
				setState(150);
				data();
				}
				break;
			case Org:
				{
				setState(151);
				org();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(155);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(154);
				match(NL);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CommandContext extends ParserRuleContext {
		public TerminalNode NL() { return getToken(Z8AsmParser.NL, 0); }
		public TerminalNode EOF() { return getToken(Z8AsmParser.EOF, 0); }
		public AdcContext adc() {
			return getRuleContext(AdcContext.class,0);
		}
		public AddContext add() {
			return getRuleContext(AddContext.class,0);
		}
		public AndContext and() {
			return getRuleContext(AndContext.class,0);
		}
		public CallContext call() {
			return getRuleContext(CallContext.class,0);
		}
		public CcfContext ccf() {
			return getRuleContext(CcfContext.class,0);
		}
		public ClrContext clr() {
			return getRuleContext(ClrContext.class,0);
		}
		public ComContext com() {
			return getRuleContext(ComContext.class,0);
		}
		public CpContext cp() {
			return getRuleContext(CpContext.class,0);
		}
		public DaContext da() {
			return getRuleContext(DaContext.class,0);
		}
		public DecContext dec() {
			return getRuleContext(DecContext.class,0);
		}
		public DecwContext decw() {
			return getRuleContext(DecwContext.class,0);
		}
		public DiContext di() {
			return getRuleContext(DiContext.class,0);
		}
		public DjnzContext djnz() {
			return getRuleContext(DjnzContext.class,0);
		}
		public EiContext ei() {
			return getRuleContext(EiContext.class,0);
		}
		public IncContext inc() {
			return getRuleContext(IncContext.class,0);
		}
		public IncwContext incw() {
			return getRuleContext(IncwContext.class,0);
		}
		public IretContext iret() {
			return getRuleContext(IretContext.class,0);
		}
		public JpContext jp() {
			return getRuleContext(JpContext.class,0);
		}
		public JrContext jr() {
			return getRuleContext(JrContext.class,0);
		}
		public LdContext ld() {
			return getRuleContext(LdContext.class,0);
		}
		public LdcContext ldc() {
			return getRuleContext(LdcContext.class,0);
		}
		public LdciContext ldci() {
			return getRuleContext(LdciContext.class,0);
		}
		public LdeContext lde() {
			return getRuleContext(LdeContext.class,0);
		}
		public LdeiContext ldei() {
			return getRuleContext(LdeiContext.class,0);
		}
		public NopContext nop() {
			return getRuleContext(NopContext.class,0);
		}
		public OrContext or() {
			return getRuleContext(OrContext.class,0);
		}
		public PopContext pop() {
			return getRuleContext(PopContext.class,0);
		}
		public PushContext push() {
			return getRuleContext(PushContext.class,0);
		}
		public RcfContext rcf() {
			return getRuleContext(RcfContext.class,0);
		}
		public RetContext ret() {
			return getRuleContext(RetContext.class,0);
		}
		public RlContext rl() {
			return getRuleContext(RlContext.class,0);
		}
		public RlcContext rlc() {
			return getRuleContext(RlcContext.class,0);
		}
		public RrContext rr() {
			return getRuleContext(RrContext.class,0);
		}
		public RrcContext rrc() {
			return getRuleContext(RrcContext.class,0);
		}
		public SbcContext sbc() {
			return getRuleContext(SbcContext.class,0);
		}
		public ScfContext scf() {
			return getRuleContext(ScfContext.class,0);
		}
		public SraContext sra() {
			return getRuleContext(SraContext.class,0);
		}
		public SrpContext srp() {
			return getRuleContext(SrpContext.class,0);
		}
		public SubContext sub() {
			return getRuleContext(SubContext.class,0);
		}
		public SwapContext swap() {
			return getRuleContext(SwapContext.class,0);
		}
		public TcmContext tcm() {
			return getRuleContext(TcmContext.class,0);
		}
		public TmContext tm() {
			return getRuleContext(TmContext.class,0);
		}
		public XorContext xor() {
			return getRuleContext(XorContext.class,0);
		}
		public CommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_command; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitCommand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommandContext command() throws RecognitionException {
		CommandContext _localctx = new CommandContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_command);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Adc:
				{
				setState(157);
				adc();
				}
				break;
			case Add:
				{
				setState(158);
				add();
				}
				break;
			case And:
				{
				setState(159);
				and();
				}
				break;
			case Call:
				{
				setState(160);
				call();
				}
				break;
			case Ccf:
				{
				setState(161);
				ccf();
				}
				break;
			case Clr:
				{
				setState(162);
				clr();
				}
				break;
			case Com:
				{
				setState(163);
				com();
				}
				break;
			case Cp:
				{
				setState(164);
				cp();
				}
				break;
			case Da:
				{
				setState(165);
				da();
				}
				break;
			case Dec:
				{
				setState(166);
				dec();
				}
				break;
			case Decw:
				{
				setState(167);
				decw();
				}
				break;
			case Di:
				{
				setState(168);
				di();
				}
				break;
			case Djnz:
				{
				setState(169);
				djnz();
				}
				break;
			case Ei:
				{
				setState(170);
				ei();
				}
				break;
			case Inc:
				{
				setState(171);
				inc();
				}
				break;
			case Incw:
				{
				setState(172);
				incw();
				}
				break;
			case Iret:
				{
				setState(173);
				iret();
				}
				break;
			case Jp:
				{
				setState(174);
				jp();
				}
				break;
			case Jr:
				{
				setState(175);
				jr();
				}
				break;
			case Ld:
				{
				setState(176);
				ld();
				}
				break;
			case Ldc:
				{
				setState(177);
				ldc();
				}
				break;
			case Ldci:
				{
				setState(178);
				ldci();
				}
				break;
			case Lde:
				{
				setState(179);
				lde();
				}
				break;
			case Ldei:
				{
				setState(180);
				ldei();
				}
				break;
			case Nop:
				{
				setState(181);
				nop();
				}
				break;
			case Or:
				{
				setState(182);
				or();
				}
				break;
			case Pop:
				{
				setState(183);
				pop();
				}
				break;
			case Push:
				{
				setState(184);
				push();
				}
				break;
			case Rcf:
				{
				setState(185);
				rcf();
				}
				break;
			case Ret:
				{
				setState(186);
				ret();
				}
				break;
			case Rl:
				{
				setState(187);
				rl();
				}
				break;
			case Rlc:
				{
				setState(188);
				rlc();
				}
				break;
			case Rr:
				{
				setState(189);
				rr();
				}
				break;
			case Rrc:
				{
				setState(190);
				rrc();
				}
				break;
			case Sbc:
				{
				setState(191);
				sbc();
				}
				break;
			case Scf:
				{
				setState(192);
				scf();
				}
				break;
			case Sra:
				{
				setState(193);
				sra();
				}
				break;
			case Srp:
				{
				setState(194);
				srp();
				}
				break;
			case Sub:
				{
				setState(195);
				sub();
				}
				break;
			case Swap:
				{
				setState(196);
				swap();
				}
				break;
			case Tcm:
				{
				setState(197);
				tcm();
				}
				break;
			case Tm:
				{
				setState(198);
				tm();
				}
				break;
			case Xor:
				{
				setState(199);
				xor();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(202);
			_la = _input.LA(1);
			if ( !(_la==EOF || _la==NL) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DefConstContext extends ParserRuleContext {
		public Token name;
		public ExpressionContext expr;
		public TerminalNode Const() { return getToken(Z8AsmParser.Const, 0); }
		public TerminalNode Identifier() { return getToken(Z8AsmParser.Identifier, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public DefConstContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defConst; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterDefConst(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitDefConst(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitDefConst(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefConstContext defConst() throws RecognitionException {
		DefConstContext _localctx = new DefConstContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_defConst);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(204);
			match(Const);
			setState(205);
			((DefConstContext)_localctx).name = match(Identifier);
			setState(206);
			match(T__0);
			setState(207);
			((DefConstContext)_localctx).expr = expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DataContext extends ParserRuleContext {
		public TerminalNode Data() { return getToken(Z8AsmParser.Data, 0); }
		public List<DataItemContext> dataItem() {
			return getRuleContexts(DataItemContext.class);
		}
		public DataItemContext dataItem(int i) {
			return getRuleContext(DataItemContext.class,i);
		}
		public DataContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_data; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterData(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitData(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitData(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DataContext data() throws RecognitionException {
		DataContext _localctx = new DataContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_data);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			match(Data);
			setState(211); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(210);
					dataItem();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(213); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DataItemContext extends ParserRuleContext {
		public DataItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataItem; }
	 
		public DataItemContext() { }
		public void copyFrom(DataItemContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class DataLStringContext extends DataItemContext {
		public TerminalNode LString() { return getToken(Z8AsmParser.LString, 0); }
		public DataLStringContext(DataItemContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterDataLString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitDataLString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitDataLString(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DataAddressContext extends DataItemContext {
		public AddressContext address() {
			return getRuleContext(AddressContext.class,0);
		}
		public DataAddressContext(DataItemContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterDataAddress(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitDataAddress(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitDataAddress(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DataStringContext extends DataItemContext {
		public TerminalNode String() { return getToken(Z8AsmParser.String, 0); }
		public DataStringContext(DataItemContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterDataString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitDataString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitDataString(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DataByteContext extends DataItemContext {
		public TerminalNode Byte() { return getToken(Z8AsmParser.Byte, 0); }
		public DataByteContext(DataItemContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterDataByte(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitDataByte(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitDataByte(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DataCharContext extends DataItemContext {
		public TerminalNode Char() { return getToken(Z8AsmParser.Char, 0); }
		public DataCharContext(DataItemContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterDataChar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitDataChar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitDataChar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DataItemContext dataItem() throws RecognitionException {
		DataItemContext _localctx = new DataItemContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_dataItem);
		try {
			setState(220);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Byte:
				_localctx = new DataByteContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(215);
				match(Byte);
				}
				break;
			case Word:
			case Identifier:
				_localctx = new DataAddressContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(216);
				address();
				}
				break;
			case LString:
				_localctx = new DataLStringContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(217);
				match(LString);
				}
				break;
			case String:
				_localctx = new DataStringContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(218);
				match(String);
				}
				break;
			case Char:
				_localctx = new DataCharContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(219);
				match(Char);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrgContext extends ParserRuleContext {
		public TerminalNode Org() { return getToken(Z8AsmParser.Org, 0); }
		public TerminalNode Word() { return getToken(Z8AsmParser.Word, 0); }
		public OrgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_org; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterOrg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitOrg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitOrg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrgContext org() throws RecognitionException {
		OrgContext _localctx = new OrgContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_org);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
			match(Org);
			setState(223);
			match(Word);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DecContext extends ParserRuleContext {
		public TerminalNode Dec() { return getToken(Z8AsmParser.Dec, 0); }
		public RegisterOrIregisterContext registerOrIregister() {
			return getRuleContext(RegisterOrIregisterContext.class,0);
		}
		public DecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterDec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitDec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitDec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DecContext dec() throws RecognitionException {
		DecContext _localctx = new DecContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_dec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(225);
			match(Dec);
			setState(226);
			registerOrIregister();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RlcContext extends ParserRuleContext {
		public TerminalNode Rlc() { return getToken(Z8AsmParser.Rlc, 0); }
		public RegisterOrIregisterContext registerOrIregister() {
			return getRuleContext(RegisterOrIregisterContext.class,0);
		}
		public RlcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rlc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterRlc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitRlc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitRlc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RlcContext rlc() throws RecognitionException {
		RlcContext _localctx = new RlcContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_rlc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(228);
			match(Rlc);
			setState(229);
			registerOrIregister();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IncContext extends ParserRuleContext {
		public TerminalNode Inc() { return getToken(Z8AsmParser.Inc, 0); }
		public RegisterOrIregisterContext registerOrIregister() {
			return getRuleContext(RegisterOrIregisterContext.class,0);
		}
		public IncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterInc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitInc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitInc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IncContext inc() throws RecognitionException {
		IncContext _localctx = new IncContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_inc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(231);
			match(Inc);
			setState(232);
			registerOrIregister();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DaContext extends ParserRuleContext {
		public TerminalNode Da() { return getToken(Z8AsmParser.Da, 0); }
		public RegisterOrIregisterContext registerOrIregister() {
			return getRuleContext(RegisterOrIregisterContext.class,0);
		}
		public DaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_da; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterDa(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitDa(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitDa(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DaContext da() throws RecognitionException {
		DaContext _localctx = new DaContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_da);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(234);
			match(Da);
			setState(235);
			registerOrIregister();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PopContext extends ParserRuleContext {
		public TerminalNode Pop() { return getToken(Z8AsmParser.Pop, 0); }
		public RegisterOrIregisterContext registerOrIregister() {
			return getRuleContext(RegisterOrIregisterContext.class,0);
		}
		public PopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterPop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitPop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitPop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PopContext pop() throws RecognitionException {
		PopContext _localctx = new PopContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_pop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(237);
			match(Pop);
			setState(238);
			registerOrIregister();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComContext extends ParserRuleContext {
		public TerminalNode Com() { return getToken(Z8AsmParser.Com, 0); }
		public RegisterOrIregisterContext registerOrIregister() {
			return getRuleContext(RegisterOrIregisterContext.class,0);
		}
		public ComContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_com; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterCom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitCom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitCom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComContext com() throws RecognitionException {
		ComContext _localctx = new ComContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_com);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(240);
			match(Com);
			setState(241);
			registerOrIregister();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PushContext extends ParserRuleContext {
		public TerminalNode Push() { return getToken(Z8AsmParser.Push, 0); }
		public RegisterOrIregisterContext registerOrIregister() {
			return getRuleContext(RegisterOrIregisterContext.class,0);
		}
		public PushContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_push; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterPush(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitPush(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitPush(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PushContext push() throws RecognitionException {
		PushContext _localctx = new PushContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_push);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(243);
			match(Push);
			setState(244);
			registerOrIregister();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DecwContext extends ParserRuleContext {
		public TerminalNode Decw() { return getToken(Z8AsmParser.Decw, 0); }
		public RegisterOrIregisterContext registerOrIregister() {
			return getRuleContext(RegisterOrIregisterContext.class,0);
		}
		public DecwContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decw; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterDecw(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitDecw(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitDecw(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DecwContext decw() throws RecognitionException {
		DecwContext _localctx = new DecwContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_decw);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(246);
			match(Decw);
			setState(247);
			registerOrIregister();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RlContext extends ParserRuleContext {
		public TerminalNode Rl() { return getToken(Z8AsmParser.Rl, 0); }
		public RegisterOrIregisterContext registerOrIregister() {
			return getRuleContext(RegisterOrIregisterContext.class,0);
		}
		public RlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterRl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitRl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitRl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RlContext rl() throws RecognitionException {
		RlContext _localctx = new RlContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_rl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(249);
			match(Rl);
			setState(250);
			registerOrIregister();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IncwContext extends ParserRuleContext {
		public TerminalNode Incw() { return getToken(Z8AsmParser.Incw, 0); }
		public RegisterOrIregisterContext registerOrIregister() {
			return getRuleContext(RegisterOrIregisterContext.class,0);
		}
		public IncwContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_incw; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterIncw(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitIncw(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitIncw(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IncwContext incw() throws RecognitionException {
		IncwContext _localctx = new IncwContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_incw);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(252);
			match(Incw);
			setState(253);
			registerOrIregister();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClrContext extends ParserRuleContext {
		public TerminalNode Clr() { return getToken(Z8AsmParser.Clr, 0); }
		public RegisterOrIregisterContext registerOrIregister() {
			return getRuleContext(RegisterOrIregisterContext.class,0);
		}
		public ClrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_clr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterClr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitClr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitClr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClrContext clr() throws RecognitionException {
		ClrContext _localctx = new ClrContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_clr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(255);
			match(Clr);
			setState(256);
			registerOrIregister();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RrcContext extends ParserRuleContext {
		public TerminalNode Rrc() { return getToken(Z8AsmParser.Rrc, 0); }
		public RegisterOrIregisterContext registerOrIregister() {
			return getRuleContext(RegisterOrIregisterContext.class,0);
		}
		public RrcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rrc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterRrc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitRrc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitRrc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RrcContext rrc() throws RecognitionException {
		RrcContext _localctx = new RrcContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_rrc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(258);
			match(Rrc);
			setState(259);
			registerOrIregister();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SraContext extends ParserRuleContext {
		public TerminalNode Sra() { return getToken(Z8AsmParser.Sra, 0); }
		public RegisterOrIregisterContext registerOrIregister() {
			return getRuleContext(RegisterOrIregisterContext.class,0);
		}
		public SraContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sra; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterSra(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitSra(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitSra(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SraContext sra() throws RecognitionException {
		SraContext _localctx = new SraContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_sra);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(261);
			match(Sra);
			setState(262);
			registerOrIregister();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RrContext extends ParserRuleContext {
		public TerminalNode Rr() { return getToken(Z8AsmParser.Rr, 0); }
		public RegisterOrIregisterContext registerOrIregister() {
			return getRuleContext(RegisterOrIregisterContext.class,0);
		}
		public RrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterRr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitRr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitRr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RrContext rr() throws RecognitionException {
		RrContext _localctx = new RrContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_rr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(264);
			match(Rr);
			setState(265);
			registerOrIregister();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SwapContext extends ParserRuleContext {
		public TerminalNode Swap() { return getToken(Z8AsmParser.Swap, 0); }
		public RegisterOrIregisterContext registerOrIregister() {
			return getRuleContext(RegisterOrIregisterContext.class,0);
		}
		public SwapContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_swap; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterSwap(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitSwap(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitSwap(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwapContext swap() throws RecognitionException {
		SwapContext _localctx = new SwapContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_swap);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(267);
			match(Swap);
			setState(268);
			registerOrIregister();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SrpContext extends ParserRuleContext {
		public TerminalNode Srp() { return getToken(Z8AsmParser.Srp, 0); }
		public ValueByteContext valueByte() {
			return getRuleContext(ValueByteContext.class,0);
		}
		public SrpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_srp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterSrp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitSrp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitSrp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SrpContext srp() throws RecognitionException {
		SrpContext _localctx = new SrpContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_srp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(270);
			match(Srp);
			setState(271);
			valueByte();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CallContext extends ParserRuleContext {
		public CallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_call; }
	 
		public CallContext() { }
		public void copyFrom(CallContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CallAddressContext extends CallContext {
		public TerminalNode Call() { return getToken(Z8AsmParser.Call, 0); }
		public AddressContext address() {
			return getRuleContext(AddressContext.class,0);
		}
		public CallAddressContext(CallContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterCallAddress(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitCallAddress(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitCallAddress(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CallIregContext extends CallContext {
		public TerminalNode Call() { return getToken(Z8AsmParser.Call, 0); }
		public IregisterPairContext iregisterPair() {
			return getRuleContext(IregisterPairContext.class,0);
		}
		public CallIregContext(CallContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterCallIreg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitCallIreg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitCallIreg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CallContext call() throws RecognitionException {
		CallContext _localctx = new CallContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_call);
		try {
			setState(277);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				_localctx = new CallAddressContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(273);
				match(Call);
				setState(274);
				address();
				}
				break;
			case 2:
				_localctx = new CallIregContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(275);
				match(Call);
				setState(276);
				iregisterPair();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DjnzContext extends ParserRuleContext {
		public TerminalNode Djnz() { return getToken(Z8AsmParser.Djnz, 0); }
		public TerminalNode WorkingRegister() { return getToken(Z8AsmParser.WorkingRegister, 0); }
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public AddressContext address() {
			return getRuleContext(AddressContext.class,0);
		}
		public DjnzContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_djnz; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterDjnz(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitDjnz(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitDjnz(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DjnzContext djnz() throws RecognitionException {
		DjnzContext _localctx = new DjnzContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_djnz);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(279);
			match(Djnz);
			setState(280);
			match(WorkingRegister);
			setState(281);
			match(Comma);
			setState(282);
			address();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JpContext extends ParserRuleContext {
		public JpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jp; }
	 
		public JpContext() { }
		public void copyFrom(JpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class JpConditionAddressContext extends JpContext {
		public TerminalNode Jp() { return getToken(Z8AsmParser.Jp, 0); }
		public TerminalNode JpCondition() { return getToken(Z8AsmParser.JpCondition, 0); }
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public AddressContext address() {
			return getRuleContext(AddressContext.class,0);
		}
		public JpConditionAddressContext(JpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterJpConditionAddress(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitJpConditionAddress(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitJpConditionAddress(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class JpAddressContext extends JpContext {
		public TerminalNode Jp() { return getToken(Z8AsmParser.Jp, 0); }
		public AddressContext address() {
			return getRuleContext(AddressContext.class,0);
		}
		public JpAddressContext(JpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterJpAddress(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitJpAddress(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitJpAddress(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class JpIRegContext extends JpContext {
		public TerminalNode Jp() { return getToken(Z8AsmParser.Jp, 0); }
		public IregisterPairContext iregisterPair() {
			return getRuleContext(IregisterPairContext.class,0);
		}
		public JpIRegContext(JpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterJpIReg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitJpIReg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitJpIReg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JpContext jp() throws RecognitionException {
		JpContext _localctx = new JpContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_jp);
		try {
			setState(292);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				_localctx = new JpIRegContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(284);
				match(Jp);
				setState(285);
				iregisterPair();
				}
				break;
			case 2:
				_localctx = new JpAddressContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(286);
				match(Jp);
				setState(287);
				address();
				}
				break;
			case 3:
				_localctx = new JpConditionAddressContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(288);
				match(Jp);
				setState(289);
				match(JpCondition);
				setState(290);
				match(Comma);
				setState(291);
				address();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JrContext extends ParserRuleContext {
		public TerminalNode Jr() { return getToken(Z8AsmParser.Jr, 0); }
		public AddressContext address() {
			return getRuleContext(AddressContext.class,0);
		}
		public TerminalNode JpCondition() { return getToken(Z8AsmParser.JpCondition, 0); }
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public JrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterJr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitJr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitJr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JrContext jr() throws RecognitionException {
		JrContext _localctx = new JrContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_jr);
		try {
			setState(300);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(294);
				match(Jr);
				setState(295);
				address();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(296);
				match(Jr);
				setState(297);
				match(JpCondition);
				setState(298);
				match(Comma);
				setState(299);
				address();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LdContext extends ParserRuleContext {
		public LdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ld; }
	 
		public LdContext() { }
		public void copyFrom(LdContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Ld1Context extends LdContext {
		public TerminalNode Ld() { return getToken(Z8AsmParser.Ld, 0); }
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public ValueByteContext valueByte() {
			return getRuleContext(ValueByteContext.class,0);
		}
		public Ld1Context(LdContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLd1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLd1(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitLd1(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Ld3Context extends LdContext {
		public TerminalNode Ld() { return getToken(Z8AsmParser.Ld, 0); }
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public IregisterContext iregister() {
			return getRuleContext(IregisterContext.class,0);
		}
		public Ld3Context(LdContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLd3(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLd3(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitLd3(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Ld2Context extends LdContext {
		public RegisterContext target;
		public RegisterContext source;
		public TerminalNode Ld() { return getToken(Z8AsmParser.Ld, 0); }
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public List<RegisterContext> register() {
			return getRuleContexts(RegisterContext.class);
		}
		public RegisterContext register(int i) {
			return getRuleContext(RegisterContext.class,i);
		}
		public Ld2Context(LdContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLd2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLd2(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitLd2(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Ld5Context extends LdContext {
		public TerminalNode Ld() { return getToken(Z8AsmParser.Ld, 0); }
		public TerminalNode Byte() { return getToken(Z8AsmParser.Byte, 0); }
		public List<TerminalNode> WorkingRegister() { return getTokens(Z8AsmParser.WorkingRegister); }
		public TerminalNode WorkingRegister(int i) {
			return getToken(Z8AsmParser.WorkingRegister, i);
		}
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public Ld5Context(LdContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLd5(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLd5(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitLd5(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Ld4Context extends LdContext {
		public TerminalNode Ld() { return getToken(Z8AsmParser.Ld, 0); }
		public List<TerminalNode> WorkingRegister() { return getTokens(Z8AsmParser.WorkingRegister); }
		public TerminalNode WorkingRegister(int i) {
			return getToken(Z8AsmParser.WorkingRegister, i);
		}
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public TerminalNode Byte() { return getToken(Z8AsmParser.Byte, 0); }
		public Ld4Context(LdContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLd4(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLd4(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitLd4(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Ld6Context extends LdContext {
		public TerminalNode Ld() { return getToken(Z8AsmParser.Ld, 0); }
		public IregisterContext iregister() {
			return getRuleContext(IregisterContext.class,0);
		}
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public Ld6Context(LdContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLd6(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLd6(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitLd6(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LdContext ld() throws RecognitionException {
		LdContext _localctx = new LdContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_ld);
		try {
			setState(336);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				_localctx = new Ld1Context(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(302);
				match(Ld);
				setState(303);
				register();
				setState(304);
				match(Comma);
				setState(305);
				valueByte();
				}
				break;
			case 2:
				_localctx = new Ld2Context(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(307);
				match(Ld);
				setState(308);
				((Ld2Context)_localctx).target = register();
				setState(309);
				match(Comma);
				setState(310);
				((Ld2Context)_localctx).source = register();
				}
				break;
			case 3:
				_localctx = new Ld3Context(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(312);
				match(Ld);
				setState(313);
				register();
				setState(314);
				match(Comma);
				setState(315);
				iregister();
				}
				break;
			case 4:
				_localctx = new Ld4Context(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(317);
				match(Ld);
				setState(318);
				match(WorkingRegister);
				setState(319);
				match(Comma);
				setState(320);
				match(Byte);
				setState(321);
				match(T__1);
				setState(322);
				match(WorkingRegister);
				setState(323);
				match(T__2);
				}
				break;
			case 5:
				_localctx = new Ld5Context(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(324);
				match(Ld);
				setState(325);
				match(Byte);
				setState(326);
				match(T__1);
				setState(327);
				match(WorkingRegister);
				setState(328);
				match(T__2);
				setState(329);
				match(Comma);
				setState(330);
				match(WorkingRegister);
				}
				break;
			case 6:
				_localctx = new Ld6Context(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(331);
				match(Ld);
				setState(332);
				iregister();
				setState(333);
				match(Comma);
				setState(334);
				register();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LdcContext extends ParserRuleContext {
		public LdcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ldc; }
	 
		public LdcContext() { }
		public void copyFrom(LdcContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Ldc1Context extends LdcContext {
		public TerminalNode Ldc() { return getToken(Z8AsmParser.Ldc, 0); }
		public TerminalNode WorkingRegister() { return getToken(Z8AsmParser.WorkingRegister, 0); }
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public TerminalNode IWorkingRegisterPair() { return getToken(Z8AsmParser.IWorkingRegisterPair, 0); }
		public Ldc1Context(LdcContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLdc1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLdc1(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitLdc1(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Ldc2Context extends LdcContext {
		public TerminalNode Ldc() { return getToken(Z8AsmParser.Ldc, 0); }
		public TerminalNode IWorkingRegisterPair() { return getToken(Z8AsmParser.IWorkingRegisterPair, 0); }
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public TerminalNode WorkingRegister() { return getToken(Z8AsmParser.WorkingRegister, 0); }
		public Ldc2Context(LdcContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLdc2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLdc2(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitLdc2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LdcContext ldc() throws RecognitionException {
		LdcContext _localctx = new LdcContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_ldc);
		try {
			setState(346);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				_localctx = new Ldc1Context(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(338);
				match(Ldc);
				setState(339);
				match(WorkingRegister);
				setState(340);
				match(Comma);
				setState(341);
				match(IWorkingRegisterPair);
				}
				break;
			case 2:
				_localctx = new Ldc2Context(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(342);
				match(Ldc);
				setState(343);
				match(IWorkingRegisterPair);
				setState(344);
				match(Comma);
				setState(345);
				match(WorkingRegister);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LdciContext extends ParserRuleContext {
		public LdciContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ldci; }
	 
		public LdciContext() { }
		public void copyFrom(LdciContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Ldci1Context extends LdciContext {
		public TerminalNode Ldci() { return getToken(Z8AsmParser.Ldci, 0); }
		public TerminalNode IWorkingRegister() { return getToken(Z8AsmParser.IWorkingRegister, 0); }
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public TerminalNode IWorkingRegisterPair() { return getToken(Z8AsmParser.IWorkingRegisterPair, 0); }
		public Ldci1Context(LdciContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLdci1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLdci1(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitLdci1(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Ldci2Context extends LdciContext {
		public TerminalNode Ldci() { return getToken(Z8AsmParser.Ldci, 0); }
		public TerminalNode IWorkingRegisterPair() { return getToken(Z8AsmParser.IWorkingRegisterPair, 0); }
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public TerminalNode IWorkingRegister() { return getToken(Z8AsmParser.IWorkingRegister, 0); }
		public Ldci2Context(LdciContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLdci2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLdci2(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitLdci2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LdciContext ldci() throws RecognitionException {
		LdciContext _localctx = new LdciContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_ldci);
		try {
			setState(356);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				_localctx = new Ldci1Context(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(348);
				match(Ldci);
				setState(349);
				match(IWorkingRegister);
				setState(350);
				match(Comma);
				setState(351);
				match(IWorkingRegisterPair);
				}
				break;
			case 2:
				_localctx = new Ldci2Context(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(352);
				match(Ldci);
				setState(353);
				match(IWorkingRegisterPair);
				setState(354);
				match(Comma);
				setState(355);
				match(IWorkingRegister);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LdeContext extends ParserRuleContext {
		public LdeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lde; }
	 
		public LdeContext() { }
		public void copyFrom(LdeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Lde1Context extends LdeContext {
		public TerminalNode Lde() { return getToken(Z8AsmParser.Lde, 0); }
		public TerminalNode WorkingRegister() { return getToken(Z8AsmParser.WorkingRegister, 0); }
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public TerminalNode IWorkingRegisterPair() { return getToken(Z8AsmParser.IWorkingRegisterPair, 0); }
		public Lde1Context(LdeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLde1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLde1(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitLde1(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Lde2Context extends LdeContext {
		public TerminalNode Lde() { return getToken(Z8AsmParser.Lde, 0); }
		public TerminalNode IWorkingRegisterPair() { return getToken(Z8AsmParser.IWorkingRegisterPair, 0); }
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public TerminalNode WorkingRegister() { return getToken(Z8AsmParser.WorkingRegister, 0); }
		public Lde2Context(LdeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLde2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLde2(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitLde2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LdeContext lde() throws RecognitionException {
		LdeContext _localctx = new LdeContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_lde);
		try {
			setState(366);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				_localctx = new Lde1Context(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(358);
				match(Lde);
				setState(359);
				match(WorkingRegister);
				setState(360);
				match(Comma);
				setState(361);
				match(IWorkingRegisterPair);
				}
				break;
			case 2:
				_localctx = new Lde2Context(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(362);
				match(Lde);
				setState(363);
				match(IWorkingRegisterPair);
				setState(364);
				match(Comma);
				setState(365);
				match(WorkingRegister);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LdeiContext extends ParserRuleContext {
		public LdeiContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ldei; }
	 
		public LdeiContext() { }
		public void copyFrom(LdeiContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Ldei1Context extends LdeiContext {
		public TerminalNode Ldei() { return getToken(Z8AsmParser.Ldei, 0); }
		public TerminalNode IWorkingRegister() { return getToken(Z8AsmParser.IWorkingRegister, 0); }
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public TerminalNode IWorkingRegisterPair() { return getToken(Z8AsmParser.IWorkingRegisterPair, 0); }
		public Ldei1Context(LdeiContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLdei1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLdei1(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitLdei1(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Ldei2Context extends LdeiContext {
		public TerminalNode Ldei() { return getToken(Z8AsmParser.Ldei, 0); }
		public TerminalNode IWorkingRegisterPair() { return getToken(Z8AsmParser.IWorkingRegisterPair, 0); }
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public TerminalNode IWorkingRegister() { return getToken(Z8AsmParser.IWorkingRegister, 0); }
		public Ldei2Context(LdeiContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLdei2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLdei2(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitLdei2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LdeiContext ldei() throws RecognitionException {
		LdeiContext _localctx = new LdeiContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_ldei);
		try {
			setState(376);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				_localctx = new Ldei1Context(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(368);
				match(Ldei);
				setState(369);
				match(IWorkingRegister);
				setState(370);
				match(Comma);
				setState(371);
				match(IWorkingRegisterPair);
				}
				break;
			case 2:
				_localctx = new Ldei2Context(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(372);
				match(Ldei);
				setState(373);
				match(IWorkingRegisterPair);
				setState(374);
				match(Comma);
				setState(375);
				match(IWorkingRegister);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DiContext extends ParserRuleContext {
		public TerminalNode Di() { return getToken(Z8AsmParser.Di, 0); }
		public DiContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_di; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterDi(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitDi(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitDi(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DiContext di() throws RecognitionException {
		DiContext _localctx = new DiContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_di);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(378);
			match(Di);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EiContext extends ParserRuleContext {
		public TerminalNode Ei() { return getToken(Z8AsmParser.Ei, 0); }
		public EiContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ei; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterEi(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitEi(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitEi(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EiContext ei() throws RecognitionException {
		EiContext _localctx = new EiContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_ei);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(380);
			match(Ei);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RetContext extends ParserRuleContext {
		public TerminalNode Ret() { return getToken(Z8AsmParser.Ret, 0); }
		public RetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ret; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterRet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitRet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitRet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RetContext ret() throws RecognitionException {
		RetContext _localctx = new RetContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_ret);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(382);
			match(Ret);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IretContext extends ParserRuleContext {
		public TerminalNode Iret() { return getToken(Z8AsmParser.Iret, 0); }
		public IretContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iret; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterIret(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitIret(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitIret(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IretContext iret() throws RecognitionException {
		IretContext _localctx = new IretContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_iret);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(384);
			match(Iret);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RcfContext extends ParserRuleContext {
		public TerminalNode Rcf() { return getToken(Z8AsmParser.Rcf, 0); }
		public RcfContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rcf; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterRcf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitRcf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitRcf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RcfContext rcf() throws RecognitionException {
		RcfContext _localctx = new RcfContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_rcf);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(386);
			match(Rcf);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ScfContext extends ParserRuleContext {
		public TerminalNode Scf() { return getToken(Z8AsmParser.Scf, 0); }
		public ScfContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scf; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterScf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitScf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitScf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ScfContext scf() throws RecognitionException {
		ScfContext _localctx = new ScfContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_scf);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(388);
			match(Scf);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CcfContext extends ParserRuleContext {
		public TerminalNode Ccf() { return getToken(Z8AsmParser.Ccf, 0); }
		public CcfContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ccf; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterCcf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitCcf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitCcf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CcfContext ccf() throws RecognitionException {
		CcfContext _localctx = new CcfContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_ccf);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(390);
			match(Ccf);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NopContext extends ParserRuleContext {
		public TerminalNode Nop() { return getToken(Z8AsmParser.Nop, 0); }
		public NopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterNop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitNop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitNop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NopContext nop() throws RecognitionException {
		NopContext _localctx = new NopContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_nop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(392);
			match(Nop);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AdcContext extends ParserRuleContext {
		public TerminalNode Adc() { return getToken(Z8AsmParser.Adc, 0); }
		public ArithmeticParametersContext arithmeticParameters() {
			return getRuleContext(ArithmeticParametersContext.class,0);
		}
		public AdcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_adc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterAdc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitAdc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitAdc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AdcContext adc() throws RecognitionException {
		AdcContext _localctx = new AdcContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_adc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(394);
			match(Adc);
			setState(395);
			arithmeticParameters();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AddContext extends ParserRuleContext {
		public TerminalNode Add() { return getToken(Z8AsmParser.Add, 0); }
		public ArithmeticParametersContext arithmeticParameters() {
			return getRuleContext(ArithmeticParametersContext.class,0);
		}
		public AddContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_add; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterAdd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitAdd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitAdd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AddContext add() throws RecognitionException {
		AddContext _localctx = new AddContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_add);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(397);
			match(Add);
			setState(398);
			arithmeticParameters();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AndContext extends ParserRuleContext {
		public TerminalNode And() { return getToken(Z8AsmParser.And, 0); }
		public ArithmeticParametersContext arithmeticParameters() {
			return getRuleContext(ArithmeticParametersContext.class,0);
		}
		public AndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_and; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitAnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitAnd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AndContext and() throws RecognitionException {
		AndContext _localctx = new AndContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_and);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(400);
			match(And);
			setState(401);
			arithmeticParameters();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CpContext extends ParserRuleContext {
		public TerminalNode Cp() { return getToken(Z8AsmParser.Cp, 0); }
		public ArithmeticParametersContext arithmeticParameters() {
			return getRuleContext(ArithmeticParametersContext.class,0);
		}
		public CpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterCp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitCp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitCp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CpContext cp() throws RecognitionException {
		CpContext _localctx = new CpContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_cp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(403);
			match(Cp);
			setState(404);
			arithmeticParameters();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrContext extends ParserRuleContext {
		public TerminalNode Or() { return getToken(Z8AsmParser.Or, 0); }
		public ArithmeticParametersContext arithmeticParameters() {
			return getRuleContext(ArithmeticParametersContext.class,0);
		}
		public OrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_or; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitOr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitOr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrContext or() throws RecognitionException {
		OrContext _localctx = new OrContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_or);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(406);
			match(Or);
			setState(407);
			arithmeticParameters();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SbcContext extends ParserRuleContext {
		public TerminalNode Sbc() { return getToken(Z8AsmParser.Sbc, 0); }
		public ArithmeticParametersContext arithmeticParameters() {
			return getRuleContext(ArithmeticParametersContext.class,0);
		}
		public SbcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sbc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterSbc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitSbc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitSbc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SbcContext sbc() throws RecognitionException {
		SbcContext _localctx = new SbcContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_sbc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(409);
			match(Sbc);
			setState(410);
			arithmeticParameters();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SubContext extends ParserRuleContext {
		public TerminalNode Sub() { return getToken(Z8AsmParser.Sub, 0); }
		public ArithmeticParametersContext arithmeticParameters() {
			return getRuleContext(ArithmeticParametersContext.class,0);
		}
		public SubContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sub; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterSub(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitSub(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitSub(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubContext sub() throws RecognitionException {
		SubContext _localctx = new SubContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_sub);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(412);
			match(Sub);
			setState(413);
			arithmeticParameters();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TcmContext extends ParserRuleContext {
		public TerminalNode Tcm() { return getToken(Z8AsmParser.Tcm, 0); }
		public ArithmeticParametersContext arithmeticParameters() {
			return getRuleContext(ArithmeticParametersContext.class,0);
		}
		public TcmContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tcm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterTcm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitTcm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitTcm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TcmContext tcm() throws RecognitionException {
		TcmContext _localctx = new TcmContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_tcm);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(415);
			match(Tcm);
			setState(416);
			arithmeticParameters();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TmContext extends ParserRuleContext {
		public TerminalNode Tm() { return getToken(Z8AsmParser.Tm, 0); }
		public ArithmeticParametersContext arithmeticParameters() {
			return getRuleContext(ArithmeticParametersContext.class,0);
		}
		public TmContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterTm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitTm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitTm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TmContext tm() throws RecognitionException {
		TmContext _localctx = new TmContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_tm);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(418);
			match(Tm);
			setState(419);
			arithmeticParameters();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class XorContext extends ParserRuleContext {
		public TerminalNode Xor() { return getToken(Z8AsmParser.Xor, 0); }
		public ArithmeticParametersContext arithmeticParameters() {
			return getRuleContext(ArithmeticParametersContext.class,0);
		}
		public XorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_xor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterXor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitXor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitXor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final XorContext xor() throws RecognitionException {
		XorContext _localctx = new XorContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_xor);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(421);
			match(Xor);
			setState(422);
			arithmeticParameters();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArithmeticParametersContext extends ParserRuleContext {
		public ArithmeticParameters1Context arithmeticParameters1() {
			return getRuleContext(ArithmeticParameters1Context.class,0);
		}
		public ArithmeticParameters2Context arithmeticParameters2() {
			return getRuleContext(ArithmeticParameters2Context.class,0);
		}
		public ArithmeticParameters3Context arithmeticParameters3() {
			return getRuleContext(ArithmeticParameters3Context.class,0);
		}
		public ArithmeticParameters4Context arithmeticParameters4() {
			return getRuleContext(ArithmeticParameters4Context.class,0);
		}
		public ArithmeticParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithmeticParameters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterArithmeticParameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitArithmeticParameters(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitArithmeticParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArithmeticParametersContext arithmeticParameters() throws RecognitionException {
		ArithmeticParametersContext _localctx = new ArithmeticParametersContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_arithmeticParameters);
		try {
			setState(428);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(424);
				arithmeticParameters1();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(425);
				arithmeticParameters2();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(426);
				arithmeticParameters3();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(427);
				arithmeticParameters4();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArithmeticParameters1Context extends ParserRuleContext {
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public ValueByteContext valueByte() {
			return getRuleContext(ValueByteContext.class,0);
		}
		public ArithmeticParameters1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithmeticParameters1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterArithmeticParameters1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitArithmeticParameters1(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitArithmeticParameters1(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArithmeticParameters1Context arithmeticParameters1() throws RecognitionException {
		ArithmeticParameters1Context _localctx = new ArithmeticParameters1Context(_ctx, getState());
		enterRule(_localctx, 108, RULE_arithmeticParameters1);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(430);
			register();
			setState(431);
			match(Comma);
			setState(432);
			valueByte();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArithmeticParameters2Context extends ParserRuleContext {
		public RegisterContext target;
		public RegisterContext source;
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public List<RegisterContext> register() {
			return getRuleContexts(RegisterContext.class);
		}
		public RegisterContext register(int i) {
			return getRuleContext(RegisterContext.class,i);
		}
		public ArithmeticParameters2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithmeticParameters2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterArithmeticParameters2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitArithmeticParameters2(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitArithmeticParameters2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArithmeticParameters2Context arithmeticParameters2() throws RecognitionException {
		ArithmeticParameters2Context _localctx = new ArithmeticParameters2Context(_ctx, getState());
		enterRule(_localctx, 110, RULE_arithmeticParameters2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(434);
			((ArithmeticParameters2Context)_localctx).target = register();
			setState(435);
			match(Comma);
			setState(436);
			((ArithmeticParameters2Context)_localctx).source = register();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArithmeticParameters3Context extends ParserRuleContext {
		public IregisterContext iregister() {
			return getRuleContext(IregisterContext.class,0);
		}
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public ValueByteContext valueByte() {
			return getRuleContext(ValueByteContext.class,0);
		}
		public ArithmeticParameters3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithmeticParameters3; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterArithmeticParameters3(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitArithmeticParameters3(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitArithmeticParameters3(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArithmeticParameters3Context arithmeticParameters3() throws RecognitionException {
		ArithmeticParameters3Context _localctx = new ArithmeticParameters3Context(_ctx, getState());
		enterRule(_localctx, 112, RULE_arithmeticParameters3);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(438);
			iregister();
			setState(439);
			match(Comma);
			setState(440);
			valueByte();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArithmeticParameters4Context extends ParserRuleContext {
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public IregisterContext iregister() {
			return getRuleContext(IregisterContext.class,0);
		}
		public ArithmeticParameters4Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithmeticParameters4; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterArithmeticParameters4(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitArithmeticParameters4(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitArithmeticParameters4(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArithmeticParameters4Context arithmeticParameters4() throws RecognitionException {
		ArithmeticParameters4Context _localctx = new ArithmeticParameters4Context(_ctx, getState());
		enterRule(_localctx, 114, RULE_arithmeticParameters4);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(442);
			register();
			setState(443);
			match(Comma);
			setState(444);
			iregister();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RegisterOrIregisterContext extends ParserRuleContext {
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public IregisterContext iregister() {
			return getRuleContext(IregisterContext.class,0);
		}
		public RegisterOrIregisterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_registerOrIregister; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterRegisterOrIregister(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitRegisterOrIregister(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitRegisterOrIregister(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RegisterOrIregisterContext registerOrIregister() throws RecognitionException {
		RegisterOrIregisterContext _localctx = new RegisterOrIregisterContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_registerOrIregister);
		try {
			setState(448);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Byte:
			case WorkingRegister:
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(446);
				register();
				}
				break;
			case IndirectPrefix:
			case IWorkingRegister:
				enterOuterAlt(_localctx, 2);
				{
				setState(447);
				iregister();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RegisterContext extends ParserRuleContext {
		public TerminalNode Byte() { return getToken(Z8AsmParser.Byte, 0); }
		public TerminalNode WorkingRegister() { return getToken(Z8AsmParser.WorkingRegister, 0); }
		public TerminalNode Identifier() { return getToken(Z8AsmParser.Identifier, 0); }
		public RegisterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_register; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterRegister(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitRegister(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitRegister(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RegisterContext register() throws RecognitionException {
		RegisterContext _localctx = new RegisterContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_register);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(450);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Byte) | (1L << WorkingRegister) | (1L << Identifier))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IregisterContext extends ParserRuleContext {
		public TerminalNode IndirectPrefix() { return getToken(Z8AsmParser.IndirectPrefix, 0); }
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public TerminalNode IWorkingRegister() { return getToken(Z8AsmParser.IWorkingRegister, 0); }
		public IregisterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iregister; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterIregister(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitIregister(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitIregister(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IregisterContext iregister() throws RecognitionException {
		IregisterContext _localctx = new IregisterContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_iregister);
		try {
			setState(455);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IndirectPrefix:
				enterOuterAlt(_localctx, 1);
				{
				setState(452);
				match(IndirectPrefix);
				setState(453);
				register();
				}
				break;
			case IWorkingRegister:
				enterOuterAlt(_localctx, 2);
				{
				setState(454);
				match(IWorkingRegister);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IregisterPairContext extends ParserRuleContext {
		public TerminalNode IndirectPrefix() { return getToken(Z8AsmParser.IndirectPrefix, 0); }
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public TerminalNode IWorkingRegisterPair() { return getToken(Z8AsmParser.IWorkingRegisterPair, 0); }
		public IregisterPairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iregisterPair; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterIregisterPair(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitIregisterPair(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitIregisterPair(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IregisterPairContext iregisterPair() throws RecognitionException {
		IregisterPairContext _localctx = new IregisterPairContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_iregisterPair);
		try {
			setState(460);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IndirectPrefix:
				enterOuterAlt(_localctx, 1);
				{
				setState(457);
				match(IndirectPrefix);
				setState(458);
				register();
				}
				break;
			case IWorkingRegisterPair:
				enterOuterAlt(_localctx, 2);
				{
				setState(459);
				match(IWorkingRegisterPair);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueByteContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ValueByteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueByte; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterValueByte(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitValueByte(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitValueByte(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueByteContext valueByte() throws RecognitionException {
		ValueByteContext _localctx = new ValueByteContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_valueByte);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(462);
			match(T__3);
			setState(463);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ExprIdentifierContext extends ExpressionContext {
		public TerminalNode Identifier() { return getToken(Z8AsmParser.Identifier, 0); }
		public ExprIdentifierContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterExprIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitExprIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitExprIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprNumberContext extends ExpressionContext {
		public TerminalNode Byte() { return getToken(Z8AsmParser.Byte, 0); }
		public TerminalNode Word() { return getToken(Z8AsmParser.Word, 0); }
		public ExprNumberContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterExprNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitExprNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitExprNumber(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprCharContext extends ExpressionContext {
		public TerminalNode Char() { return getToken(Z8AsmParser.Char, 0); }
		public ExprCharContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterExprChar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitExprChar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Z8AsmVisitor ) return ((Z8AsmVisitor<? extends T>)visitor).visitExprChar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_expression);
		int _la;
		try {
			setState(468);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Byte:
			case Word:
				_localctx = new ExprNumberContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(465);
				_la = _input.LA(1);
				if ( !(_la==Byte || _la==Word) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case Char:
				_localctx = new ExprCharContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(466);
				match(Char);
				}
				break;
			case Identifier:
				_localctx = new ExprIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(467);
				match(Identifier);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3E\u01d9\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\3\2\3\2\7\2\u0085\n\2\f\2\16\2\u0088\13\2\3\3"+
		"\3\3\3\3\5\3\u008d\n\3\3\4\3\4\3\4\5\4\u0092\n\4\3\5\3\5\5\5\u0096\n\5"+
		"\3\6\3\6\3\6\5\6\u009b\n\6\3\6\5\6\u009e\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\5\7\u00cb\n\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\6\t\u00d6\n\t"+
		"\r\t\16\t\u00d7\3\n\3\n\3\n\3\n\3\n\5\n\u00df\n\n\3\13\3\13\3\13\3\f\3"+
		"\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3"+
		"\21\3\21\3\22\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3"+
		"\26\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\31\3\32\3\32\3"+
		"\32\3\33\3\33\3\33\3\34\3\34\3\34\3\34\5\34\u0118\n\34\3\35\3\35\3\35"+
		"\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\5\36\u0127\n\36\3\37"+
		"\3\37\3\37\3\37\3\37\3\37\5\37\u012f\n\37\3 \3 \3 \3 \3 \3 \3 \3 \3 \3"+
		" \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3"+
		" \3 \5 \u0153\n \3!\3!\3!\3!\3!\3!\3!\3!\5!\u015d\n!\3\"\3\"\3\"\3\"\3"+
		"\"\3\"\3\"\3\"\5\"\u0167\n\"\3#\3#\3#\3#\3#\3#\3#\3#\5#\u0171\n#\3$\3"+
		"$\3$\3$\3$\3$\3$\3$\5$\u017b\n$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*"+
		"\3+\3+\3,\3,\3-\3-\3-\3.\3.\3.\3/\3/\3/\3\60\3\60\3\60\3\61\3\61\3\61"+
		"\3\62\3\62\3\62\3\63\3\63\3\63\3\64\3\64\3\64\3\65\3\65\3\65\3\66\3\66"+
		"\3\66\3\67\3\67\3\67\3\67\5\67\u01af\n\67\38\38\38\38\39\39\39\39\3:\3"+
		":\3:\3:\3;\3;\3;\3;\3<\3<\5<\u01c3\n<\3=\3=\3>\3>\3>\5>\u01ca\n>\3?\3"+
		"?\3?\5?\u01cf\n?\3@\3@\3@\3A\3A\3A\5A\u01d7\nA\3A\2\2B\2\4\6\b\n\f\16"+
		"\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bd"+
		"fhjlnprtvxz|~\u0080\2\5\3\3CC\5\2\n\n\f\fAA\3\2\n\13\2\u01e5\2\u0086\3"+
		"\2\2\2\4\u008c\3\2\2\2\6\u008e\3\2\2\2\b\u0095\3\2\2\2\n\u009a\3\2\2\2"+
		"\f\u00ca\3\2\2\2\16\u00ce\3\2\2\2\20\u00d3\3\2\2\2\22\u00de\3\2\2\2\24"+
		"\u00e0\3\2\2\2\26\u00e3\3\2\2\2\30\u00e6\3\2\2\2\32\u00e9\3\2\2\2\34\u00ec"+
		"\3\2\2\2\36\u00ef\3\2\2\2 \u00f2\3\2\2\2\"\u00f5\3\2\2\2$\u00f8\3\2\2"+
		"\2&\u00fb\3\2\2\2(\u00fe\3\2\2\2*\u0101\3\2\2\2,\u0104\3\2\2\2.\u0107"+
		"\3\2\2\2\60\u010a\3\2\2\2\62\u010d\3\2\2\2\64\u0110\3\2\2\2\66\u0117\3"+
		"\2\2\28\u0119\3\2\2\2:\u0126\3\2\2\2<\u012e\3\2\2\2>\u0152\3\2\2\2@\u015c"+
		"\3\2\2\2B\u0166\3\2\2\2D\u0170\3\2\2\2F\u017a\3\2\2\2H\u017c\3\2\2\2J"+
		"\u017e\3\2\2\2L\u0180\3\2\2\2N\u0182\3\2\2\2P\u0184\3\2\2\2R\u0186\3\2"+
		"\2\2T\u0188\3\2\2\2V\u018a\3\2\2\2X\u018c\3\2\2\2Z\u018f\3\2\2\2\\\u0192"+
		"\3\2\2\2^\u0195\3\2\2\2`\u0198\3\2\2\2b\u019b\3\2\2\2d\u019e\3\2\2\2f"+
		"\u01a1\3\2\2\2h\u01a4\3\2\2\2j\u01a7\3\2\2\2l\u01ae\3\2\2\2n\u01b0\3\2"+
		"\2\2p\u01b4\3\2\2\2r\u01b8\3\2\2\2t\u01bc\3\2\2\2v\u01c2\3\2\2\2x\u01c4"+
		"\3\2\2\2z\u01c9\3\2\2\2|\u01ce\3\2\2\2~\u01d0\3\2\2\2\u0080\u01d6\3\2"+
		"\2\2\u0082\u0085\5\4\3\2\u0083\u0085\7C\2\2\u0084\u0082\3\2\2\2\u0084"+
		"\u0083\3\2\2\2\u0085\u0088\3\2\2\2\u0086\u0084\3\2\2\2\u0086\u0087\3\2"+
		"\2\2\u0087\3\3\2\2\2\u0088\u0086\3\2\2\2\u0089\u008d\5\6\4\2\u008a\u008d"+
		"\5\n\6\2\u008b\u008d\5\f\7\2\u008c\u0089\3\2\2\2\u008c\u008a\3\2\2\2\u008c"+
		"\u008b\3\2\2\2\u008d\5\3\2\2\2\u008e\u008f\7A\2\2\u008f\u0091\7\20\2\2"+
		"\u0090\u0092\7C\2\2\u0091\u0090\3\2\2\2\u0091\u0092\3\2\2\2\u0092\7\3"+
		"\2\2\2\u0093\u0096\7\13\2\2\u0094\u0096\7A\2\2\u0095\u0093\3\2\2\2\u0095"+
		"\u0094\3\2\2\2\u0096\t\3\2\2\2\u0097\u009b\5\16\b\2\u0098\u009b\5\20\t"+
		"\2\u0099\u009b\5\24\13\2\u009a\u0097\3\2\2\2\u009a\u0098\3\2\2\2\u009a"+
		"\u0099\3\2\2\2\u009b\u009d\3\2\2\2\u009c\u009e\7C\2\2\u009d\u009c\3\2"+
		"\2\2\u009d\u009e\3\2\2\2\u009e\13\3\2\2\2\u009f\u00cb\5X-\2\u00a0\u00cb"+
		"\5Z.\2\u00a1\u00cb\5\\/\2\u00a2\u00cb\5\66\34\2\u00a3\u00cb\5T+\2\u00a4"+
		"\u00cb\5*\26\2\u00a5\u00cb\5 \21\2\u00a6\u00cb\5^\60\2\u00a7\u00cb\5\34"+
		"\17\2\u00a8\u00cb\5\26\f\2\u00a9\u00cb\5$\23\2\u00aa\u00cb\5H%\2\u00ab"+
		"\u00cb\58\35\2\u00ac\u00cb\5J&\2\u00ad\u00cb\5\32\16\2\u00ae\u00cb\5("+
		"\25\2\u00af\u00cb\5N(\2\u00b0\u00cb\5:\36\2\u00b1\u00cb\5<\37\2\u00b2"+
		"\u00cb\5> \2\u00b3\u00cb\5@!\2\u00b4\u00cb\5B\"\2\u00b5\u00cb\5D#\2\u00b6"+
		"\u00cb\5F$\2\u00b7\u00cb\5V,\2\u00b8\u00cb\5`\61\2\u00b9\u00cb\5\36\20"+
		"\2\u00ba\u00cb\5\"\22\2\u00bb\u00cb\5P)\2\u00bc\u00cb\5L\'\2\u00bd\u00cb"+
		"\5&\24\2\u00be\u00cb\5\30\r\2\u00bf\u00cb\5\60\31\2\u00c0\u00cb\5,\27"+
		"\2\u00c1\u00cb\5b\62\2\u00c2\u00cb\5R*\2\u00c3\u00cb\5.\30\2\u00c4\u00cb"+
		"\5\64\33\2\u00c5\u00cb\5d\63\2\u00c6\u00cb\5\62\32\2\u00c7\u00cb\5f\64"+
		"\2\u00c8\u00cb\5h\65\2\u00c9\u00cb\5j\66\2\u00ca\u009f\3\2\2\2\u00ca\u00a0"+
		"\3\2\2\2\u00ca\u00a1\3\2\2\2\u00ca\u00a2\3\2\2\2\u00ca\u00a3\3\2\2\2\u00ca"+
		"\u00a4\3\2\2\2\u00ca\u00a5\3\2\2\2\u00ca\u00a6\3\2\2\2\u00ca\u00a7\3\2"+
		"\2\2\u00ca\u00a8\3\2\2\2\u00ca\u00a9\3\2\2\2\u00ca\u00aa\3\2\2\2\u00ca"+
		"\u00ab\3\2\2\2\u00ca\u00ac\3\2\2\2\u00ca\u00ad\3\2\2\2\u00ca\u00ae\3\2"+
		"\2\2\u00ca\u00af\3\2\2\2\u00ca\u00b0\3\2\2\2\u00ca\u00b1\3\2\2\2\u00ca"+
		"\u00b2\3\2\2\2\u00ca\u00b3\3\2\2\2\u00ca\u00b4\3\2\2\2\u00ca\u00b5\3\2"+
		"\2\2\u00ca\u00b6\3\2\2\2\u00ca\u00b7\3\2\2\2\u00ca\u00b8\3\2\2\2\u00ca"+
		"\u00b9\3\2\2\2\u00ca\u00ba\3\2\2\2\u00ca\u00bb\3\2\2\2\u00ca\u00bc\3\2"+
		"\2\2\u00ca\u00bd\3\2\2\2\u00ca\u00be\3\2\2\2\u00ca\u00bf\3\2\2\2\u00ca"+
		"\u00c0\3\2\2\2\u00ca\u00c1\3\2\2\2\u00ca\u00c2\3\2\2\2\u00ca\u00c3\3\2"+
		"\2\2\u00ca\u00c4\3\2\2\2\u00ca\u00c5\3\2\2\2\u00ca\u00c6\3\2\2\2\u00ca"+
		"\u00c7\3\2\2\2\u00ca\u00c8\3\2\2\2\u00ca\u00c9\3\2\2\2\u00cb\u00cc\3\2"+
		"\2\2\u00cc\u00cd\t\2\2\2\u00cd\r\3\2\2\2\u00ce\u00cf\7\22\2\2\u00cf\u00d0"+
		"\7A\2\2\u00d0\u00d1\7\3\2\2\u00d1\u00d2\5\u0080A\2\u00d2\17\3\2\2\2\u00d3"+
		"\u00d5\7\23\2\2\u00d4\u00d6\5\22\n\2\u00d5\u00d4\3\2\2\2\u00d6\u00d7\3"+
		"\2\2\2\u00d7\u00d5\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\21\3\2\2\2\u00d9"+
		"\u00df\7\n\2\2\u00da\u00df\5\b\5\2\u00db\u00df\7\7\2\2\u00dc\u00df\7\b"+
		"\2\2\u00dd\u00df\7\t\2\2\u00de\u00d9\3\2\2\2\u00de\u00da\3\2\2\2\u00de"+
		"\u00db\3\2\2\2\u00de\u00dc\3\2\2\2\u00de\u00dd\3\2\2\2\u00df\23\3\2\2"+
		"\2\u00e0\u00e1\7\24\2\2\u00e1\u00e2\7\13\2\2\u00e2\25\3\2\2\2\u00e3\u00e4"+
		"\7\36\2\2\u00e4\u00e5\5v<\2\u00e5\27\3\2\2\2\u00e6\u00e7\7\64\2\2\u00e7"+
		"\u00e8\5v<\2\u00e8\31\3\2\2\2\u00e9\u00ea\7#\2\2\u00ea\u00eb\5v<\2\u00eb"+
		"\33\3\2\2\2\u00ec\u00ed\7\35\2\2\u00ed\u00ee\5v<\2\u00ee\35\3\2\2\2\u00ef"+
		"\u00f0\7/\2\2\u00f0\u00f1\5v<\2\u00f1\37\3\2\2\2\u00f2\u00f3\7\33\2\2"+
		"\u00f3\u00f4\5v<\2\u00f4!\3\2\2\2\u00f5\u00f6\7\60\2\2\u00f6\u00f7\5v"+
		"<\2\u00f7#\3\2\2\2\u00f8\u00f9\7\37\2\2\u00f9\u00fa\5v<\2\u00fa%\3\2\2"+
		"\2\u00fb\u00fc\7\63\2\2\u00fc\u00fd\5v<\2\u00fd\'\3\2\2\2\u00fe\u00ff"+
		"\7$\2\2\u00ff\u0100\5v<\2\u0100)\3\2\2\2\u0101\u0102\7\32\2\2\u0102\u0103"+
		"\5v<\2\u0103+\3\2\2\2\u0104\u0105\7\66\2\2\u0105\u0106\5v<\2\u0106-\3"+
		"\2\2\2\u0107\u0108\79\2\2\u0108\u0109\5v<\2\u0109/\3\2\2\2\u010a\u010b"+
		"\7\65\2\2\u010b\u010c\5v<\2\u010c\61\3\2\2\2\u010d\u010e\7<\2\2\u010e"+
		"\u010f\5v<\2\u010f\63\3\2\2\2\u0110\u0111\7:\2\2\u0111\u0112\5~@\2\u0112"+
		"\65\3\2\2\2\u0113\u0114\7\30\2\2\u0114\u0118\5\b\5\2\u0115\u0116\7\30"+
		"\2\2\u0116\u0118\5|?\2\u0117\u0113\3\2\2\2\u0117\u0115\3\2\2\2\u0118\67"+
		"\3\2\2\2\u0119\u011a\7!\2\2\u011a\u011b\7\f\2\2\u011b\u011c\7\21\2\2\u011c"+
		"\u011d\5\b\5\2\u011d9\3\2\2\2\u011e\u011f\7&\2\2\u011f\u0127\5|?\2\u0120"+
		"\u0121\7&\2\2\u0121\u0127\5\b\5\2\u0122\u0123\7&\2\2\u0123\u0124\7@\2"+
		"\2\u0124\u0125\7\21\2\2\u0125\u0127\5\b\5\2\u0126\u011e\3\2\2\2\u0126"+
		"\u0120\3\2\2\2\u0126\u0122\3\2\2\2\u0127;\3\2\2\2\u0128\u0129\7\'\2\2"+
		"\u0129\u012f\5\b\5\2\u012a\u012b\7\'\2\2\u012b\u012c\7@\2\2\u012c\u012d"+
		"\7\21\2\2\u012d\u012f\5\b\5\2\u012e\u0128\3\2\2\2\u012e\u012a\3\2\2\2"+
		"\u012f=\3\2\2\2\u0130\u0131\7(\2\2\u0131\u0132\5x=\2\u0132\u0133\7\21"+
		"\2\2\u0133\u0134\5~@\2\u0134\u0153\3\2\2\2\u0135\u0136\7(\2\2\u0136\u0137"+
		"\5x=\2\u0137\u0138\7\21\2\2\u0138\u0139\5x=\2\u0139\u0153\3\2\2\2\u013a"+
		"\u013b\7(\2\2\u013b\u013c\5x=\2\u013c\u013d\7\21\2\2\u013d\u013e\5z>\2"+
		"\u013e\u0153\3\2\2\2\u013f\u0140\7(\2\2\u0140\u0141\7\f\2\2\u0141\u0142"+
		"\7\21\2\2\u0142\u0143\7\n\2\2\u0143\u0144\7\4\2\2\u0144\u0145\7\f\2\2"+
		"\u0145\u0153\7\5\2\2\u0146\u0147\7(\2\2\u0147\u0148\7\n\2\2\u0148\u0149"+
		"\7\4\2\2\u0149\u014a\7\f\2\2\u014a\u014b\7\5\2\2\u014b\u014c\7\21\2\2"+
		"\u014c\u0153\7\f\2\2\u014d\u014e\7(\2\2\u014e\u014f\5z>\2\u014f\u0150"+
		"\7\21\2\2\u0150\u0151\5x=\2\u0151\u0153\3\2\2\2\u0152\u0130\3\2\2\2\u0152"+
		"\u0135\3\2\2\2\u0152\u013a\3\2\2\2\u0152\u013f\3\2\2\2\u0152\u0146\3\2"+
		"\2\2\u0152\u014d\3\2\2\2\u0153?\3\2\2\2\u0154\u0155\7)\2\2\u0155\u0156"+
		"\7\f\2\2\u0156\u0157\7\21\2\2\u0157\u015d\7\17\2\2\u0158\u0159\7)\2\2"+
		"\u0159\u015a\7\17\2\2\u015a\u015b\7\21\2\2\u015b\u015d\7\f\2\2\u015c\u0154"+
		"\3\2\2\2\u015c\u0158\3\2\2\2\u015dA\3\2\2\2\u015e\u015f\7*\2\2\u015f\u0160"+
		"\7\16\2\2\u0160\u0161\7\21\2\2\u0161\u0167\7\17\2\2\u0162\u0163\7*\2\2"+
		"\u0163\u0164\7\17\2\2\u0164\u0165\7\21\2\2\u0165\u0167\7\16\2\2\u0166"+
		"\u015e\3\2\2\2\u0166\u0162\3\2\2\2\u0167C\3\2\2\2\u0168\u0169\7+\2\2\u0169"+
		"\u016a\7\f\2\2\u016a\u016b\7\21\2\2\u016b\u0171\7\17\2\2\u016c\u016d\7"+
		"+\2\2\u016d\u016e\7\17\2\2\u016e\u016f\7\21\2\2\u016f\u0171\7\f\2\2\u0170"+
		"\u0168\3\2\2\2\u0170\u016c\3\2\2\2\u0171E\3\2\2\2\u0172\u0173\7,\2\2\u0173"+
		"\u0174\7\16\2\2\u0174\u0175\7\21\2\2\u0175\u017b\7\17\2\2\u0176\u0177"+
		"\7,\2\2\u0177\u0178\7\17\2\2\u0178\u0179\7\21\2\2\u0179\u017b\7\16\2\2"+
		"\u017a\u0172\3\2\2\2\u017a\u0176\3\2\2\2\u017bG\3\2\2\2\u017c\u017d\7"+
		" \2\2\u017dI\3\2\2\2\u017e\u017f\7\"\2\2\u017fK\3\2\2\2\u0180\u0181\7"+
		"\62\2\2\u0181M\3\2\2\2\u0182\u0183\7%\2\2\u0183O\3\2\2\2\u0184\u0185\7"+
		"\61\2\2\u0185Q\3\2\2\2\u0186\u0187\78\2\2\u0187S\3\2\2\2\u0188\u0189\7"+
		"\31\2\2\u0189U\3\2\2\2\u018a\u018b\7-\2\2\u018bW\3\2\2\2\u018c\u018d\7"+
		"\25\2\2\u018d\u018e\5l\67\2\u018eY\3\2\2\2\u018f\u0190\7\26\2\2\u0190"+
		"\u0191\5l\67\2\u0191[\3\2\2\2\u0192\u0193\7\27\2\2\u0193\u0194\5l\67\2"+
		"\u0194]\3\2\2\2\u0195\u0196\7\34\2\2\u0196\u0197\5l\67\2\u0197_\3\2\2"+
		"\2\u0198\u0199\7.\2\2\u0199\u019a\5l\67\2\u019aa\3\2\2\2\u019b\u019c\7"+
		"\67\2\2\u019c\u019d\5l\67\2\u019dc\3\2\2\2\u019e\u019f\7;\2\2\u019f\u01a0"+
		"\5l\67\2\u01a0e\3\2\2\2\u01a1\u01a2\7=\2\2\u01a2\u01a3\5l\67\2\u01a3g"+
		"\3\2\2\2\u01a4\u01a5\7>\2\2\u01a5\u01a6\5l\67\2\u01a6i\3\2\2\2\u01a7\u01a8"+
		"\7?\2\2\u01a8\u01a9\5l\67\2\u01a9k\3\2\2\2\u01aa\u01af\5n8\2\u01ab\u01af"+
		"\5p9\2\u01ac\u01af\5r:\2\u01ad\u01af\5t;\2\u01ae\u01aa\3\2\2\2\u01ae\u01ab"+
		"\3\2\2\2\u01ae\u01ac\3\2\2\2\u01ae\u01ad\3\2\2\2\u01afm\3\2\2\2\u01b0"+
		"\u01b1\5x=\2\u01b1\u01b2\7\21\2\2\u01b2\u01b3\5~@\2\u01b3o\3\2\2\2\u01b4"+
		"\u01b5\5x=\2\u01b5\u01b6\7\21\2\2\u01b6\u01b7\5x=\2\u01b7q\3\2\2\2\u01b8"+
		"\u01b9\5z>\2\u01b9\u01ba\7\21\2\2\u01ba\u01bb\5~@\2\u01bbs\3\2\2\2\u01bc"+
		"\u01bd\5x=\2\u01bd\u01be\7\21\2\2\u01be\u01bf\5z>\2\u01bfu\3\2\2\2\u01c0"+
		"\u01c3\5x=\2\u01c1\u01c3\5z>\2\u01c2\u01c0\3\2\2\2\u01c2\u01c1\3\2\2\2"+
		"\u01c3w\3\2\2\2\u01c4\u01c5\t\3\2\2\u01c5y\3\2\2\2\u01c6\u01c7\7\r\2\2"+
		"\u01c7\u01ca\5x=\2\u01c8\u01ca\7\16\2\2\u01c9\u01c6\3\2\2\2\u01c9\u01c8"+
		"\3\2\2\2\u01ca{\3\2\2\2\u01cb\u01cc\7\r\2\2\u01cc\u01cf\5x=\2\u01cd\u01cf"+
		"\7\17\2\2\u01ce\u01cb\3\2\2\2\u01ce\u01cd\3\2\2\2\u01cf}\3\2\2\2\u01d0"+
		"\u01d1\7\6\2\2\u01d1\u01d2\5\u0080A\2\u01d2\177\3\2\2\2\u01d3\u01d7\t"+
		"\4\2\2\u01d4\u01d7\7\t\2\2\u01d5\u01d7\7A\2\2\u01d6\u01d3\3\2\2\2\u01d6"+
		"\u01d4\3\2\2\2\u01d6\u01d5\3\2\2\2\u01d7\u0081\3\2\2\2\31\u0084\u0086"+
		"\u008c\u0091\u0095\u009a\u009d\u00ca\u00d7\u00de\u0117\u0126\u012e\u0152"+
		"\u015c\u0166\u0170\u017a\u01ae\u01c2\u01c9\u01ce\u01d6";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}