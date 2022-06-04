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
		T__0=1, T__1=2, LString=3, String=4, Char=5, Byte=6, Word=7, WorkingRegister=8, 
		IndirectPrefix=9, IWorkingRegister=10, IWorkingRegisterPair=11, Colon=12, 
		Comma=13, Const=14, Data=15, Org=16, Adc=17, Add=18, And=19, Call=20, 
		Ccf=21, Clr=22, Com=23, Cp=24, Da=25, Dec=26, Decw=27, Di=28, Djnz=29, 
		Ei=30, Inc=31, Incw=32, Iret=33, Jp=34, Jr=35, Ld=36, Ldc=37, Ldci=38, 
		Lde=39, Ldei=40, Nop=41, Or=42, Pop=43, Push=44, Rcf=45, Ret=46, Rl=47, 
		Rlc=48, Rr=49, Rrc=50, Sbc=51, Scf=52, Sra=53, Srp=54, Sub=55, Swap=56, 
		Tcm=57, Tm=58, Xor=59, JpCondition=60, Identifier=61, Whitespace=62, NL=63, 
		LineComment=64, BlockComment=65;
	public static final int
		RULE_code = 0, RULE_labelDefinition = 1, RULE_address = 2, RULE_parserInstruction = 3, 
		RULE_command = 4, RULE_defConst = 5, RULE_data = 6, RULE_dataItem = 7, 
		RULE_org = 8, RULE_dec = 9, RULE_rlc = 10, RULE_inc = 11, RULE_da = 12, 
		RULE_pop = 13, RULE_com = 14, RULE_push = 15, RULE_decw = 16, RULE_rl = 17, 
		RULE_incw = 18, RULE_clr = 19, RULE_rrc = 20, RULE_sra = 21, RULE_rr = 22, 
		RULE_swap = 23, RULE_srp = 24, RULE_call = 25, RULE_djnz = 26, RULE_jp = 27, 
		RULE_jr = 28, RULE_ld = 29, RULE_ldc = 30, RULE_ldci = 31, RULE_lde = 32, 
		RULE_ldei = 33, RULE_di = 34, RULE_ei = 35, RULE_ret = 36, RULE_iret = 37, 
		RULE_rcf = 38, RULE_scf = 39, RULE_ccf = 40, RULE_nop = 41, RULE_adc = 42, 
		RULE_add = 43, RULE_and = 44, RULE_cp = 45, RULE_or = 46, RULE_sbc = 47, 
		RULE_sub = 48, RULE_tcm = 49, RULE_tm = 50, RULE_xor = 51, RULE_arithmeticParameters = 52, 
		RULE_arithmeticParameters1 = 53, RULE_arithmeticParameters2 = 54, RULE_arithmeticParameters3 = 55, 
		RULE_arithmeticParameters4 = 56, RULE_registerOrIregister = 57, RULE_register = 58, 
		RULE_iregister = 59, RULE_iregisterPair = 60, RULE_valueByte = 61, RULE_expression = 62;
	private static String[] makeRuleNames() {
		return new String[] {
			"code", "labelDefinition", "address", "parserInstruction", "command", 
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
			null, "'='", "'#'", null, null, null, null, null, null, "'@'", null, 
			null, "':'", "','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "LString", "String", "Char", "Byte", "Word", "WorkingRegister", 
			"IndirectPrefix", "IWorkingRegister", "IWorkingRegisterPair", "Colon", 
			"Comma", "Const", "Data", "Org", "Adc", "Add", "And", "Call", "Ccf", 
			"Clr", "Com", "Cp", "Da", "Dec", "Decw", "Di", "Djnz", "Ei", "Inc", "Incw", 
			"Iret", "Jp", "Jr", "Ld", "Ldc", "Ldci", "Lde", "Ldei", "Nop", "Or", 
			"Pop", "Push", "Rcf", "Ret", "Rl", "Rlc", "Rr", "Rrc", "Sbc", "Scf", 
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

	public static class CodeContext extends ParserRuleContext {
		public List<LabelDefinitionContext> labelDefinition() {
			return getRuleContexts(LabelDefinitionContext.class);
		}
		public LabelDefinitionContext labelDefinition(int i) {
			return getRuleContext(LabelDefinitionContext.class,i);
		}
		public List<ParserInstructionContext> parserInstruction() {
			return getRuleContexts(ParserInstructionContext.class);
		}
		public ParserInstructionContext parserInstruction(int i) {
			return getRuleContext(ParserInstructionContext.class,i);
		}
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public List<TerminalNode> NL() { return getTokens(Z8AsmParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(Z8AsmParser.NL, i);
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
		enterRule(_localctx, 0, RULE_code);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Const) | (1L << Data) | (1L << Org) | (1L << Adc) | (1L << Add) | (1L << And) | (1L << Call) | (1L << Ccf) | (1L << Clr) | (1L << Com) | (1L << Cp) | (1L << Da) | (1L << Dec) | (1L << Decw) | (1L << Di) | (1L << Djnz) | (1L << Ei) | (1L << Inc) | (1L << Incw) | (1L << Iret) | (1L << Jp) | (1L << Jr) | (1L << Ld) | (1L << Ldc) | (1L << Ldci) | (1L << Lde) | (1L << Ldei) | (1L << Nop) | (1L << Or) | (1L << Pop) | (1L << Push) | (1L << Rcf) | (1L << Ret) | (1L << Rl) | (1L << Rlc) | (1L << Rr) | (1L << Rrc) | (1L << Sbc) | (1L << Scf) | (1L << Sra) | (1L << Srp) | (1L << Sub) | (1L << Swap) | (1L << Tcm) | (1L << Tm) | (1L << Xor) | (1L << Identifier) | (1L << NL))) != 0)) {
				{
				setState(130);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case Identifier:
					{
					setState(126);
					labelDefinition();
					}
					break;
				case Const:
				case Data:
				case Org:
					{
					setState(127);
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
					{
					setState(128);
					command();
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
		enterRule(_localctx, 2, RULE_labelDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135);
			match(Identifier);
			setState(136);
			match(Colon);
			setState(138);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				setState(137);
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
		enterRule(_localctx, 4, RULE_address);
		try {
			setState(142);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Word:
				enterOuterAlt(_localctx, 1);
				{
				setState(140);
				match(Word);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(141);
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
		enterRule(_localctx, 6, RULE_parserInstruction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Const:
				{
				setState(144);
				defConst();
				}
				break;
			case Data:
				{
				setState(145);
				data();
				}
				break;
			case Org:
				{
				setState(146);
				org();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(150);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(149);
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
		enterRule(_localctx, 8, RULE_command);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(195);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Adc:
				{
				setState(152);
				adc();
				}
				break;
			case Add:
				{
				setState(153);
				add();
				}
				break;
			case And:
				{
				setState(154);
				and();
				}
				break;
			case Call:
				{
				setState(155);
				call();
				}
				break;
			case Ccf:
				{
				setState(156);
				ccf();
				}
				break;
			case Clr:
				{
				setState(157);
				clr();
				}
				break;
			case Com:
				{
				setState(158);
				com();
				}
				break;
			case Cp:
				{
				setState(159);
				cp();
				}
				break;
			case Da:
				{
				setState(160);
				da();
				}
				break;
			case Dec:
				{
				setState(161);
				dec();
				}
				break;
			case Decw:
				{
				setState(162);
				decw();
				}
				break;
			case Di:
				{
				setState(163);
				di();
				}
				break;
			case Djnz:
				{
				setState(164);
				djnz();
				}
				break;
			case Ei:
				{
				setState(165);
				ei();
				}
				break;
			case Inc:
				{
				setState(166);
				inc();
				}
				break;
			case Incw:
				{
				setState(167);
				incw();
				}
				break;
			case Iret:
				{
				setState(168);
				iret();
				}
				break;
			case Jp:
				{
				setState(169);
				jp();
				}
				break;
			case Jr:
				{
				setState(170);
				jr();
				}
				break;
			case Ld:
				{
				setState(171);
				ld();
				}
				break;
			case Ldc:
				{
				setState(172);
				ldc();
				}
				break;
			case Ldci:
				{
				setState(173);
				ldci();
				}
				break;
			case Lde:
				{
				setState(174);
				lde();
				}
				break;
			case Ldei:
				{
				setState(175);
				ldei();
				}
				break;
			case Nop:
				{
				setState(176);
				nop();
				}
				break;
			case Or:
				{
				setState(177);
				or();
				}
				break;
			case Pop:
				{
				setState(178);
				pop();
				}
				break;
			case Push:
				{
				setState(179);
				push();
				}
				break;
			case Rcf:
				{
				setState(180);
				rcf();
				}
				break;
			case Ret:
				{
				setState(181);
				ret();
				}
				break;
			case Rl:
				{
				setState(182);
				rl();
				}
				break;
			case Rlc:
				{
				setState(183);
				rlc();
				}
				break;
			case Rr:
				{
				setState(184);
				rr();
				}
				break;
			case Rrc:
				{
				setState(185);
				rrc();
				}
				break;
			case Sbc:
				{
				setState(186);
				sbc();
				}
				break;
			case Scf:
				{
				setState(187);
				scf();
				}
				break;
			case Sra:
				{
				setState(188);
				sra();
				}
				break;
			case Srp:
				{
				setState(189);
				srp();
				}
				break;
			case Sub:
				{
				setState(190);
				sub();
				}
				break;
			case Swap:
				{
				setState(191);
				swap();
				}
				break;
			case Tcm:
				{
				setState(192);
				tcm();
				}
				break;
			case Tm:
				{
				setState(193);
				tm();
				}
				break;
			case Xor:
				{
				setState(194);
				xor();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(197);
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
		enterRule(_localctx, 10, RULE_defConst);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(199);
			match(Const);
			setState(200);
			((DefConstContext)_localctx).name = match(Identifier);
			setState(201);
			match(T__0);
			setState(202);
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
		enterRule(_localctx, 12, RULE_data);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(204);
			match(Data);
			setState(206); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(205);
					dataItem();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(208); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
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
		enterRule(_localctx, 14, RULE_dataItem);
		try {
			setState(215);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Byte:
				_localctx = new DataByteContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(210);
				match(Byte);
				}
				break;
			case Word:
			case Identifier:
				_localctx = new DataAddressContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(211);
				address();
				}
				break;
			case LString:
				_localctx = new DataLStringContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(212);
				match(LString);
				}
				break;
			case String:
				_localctx = new DataStringContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(213);
				match(String);
				}
				break;
			case Char:
				_localctx = new DataCharContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(214);
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
		enterRule(_localctx, 16, RULE_org);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(217);
			match(Org);
			setState(218);
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
		enterRule(_localctx, 18, RULE_dec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(220);
			match(Dec);
			setState(221);
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
		enterRule(_localctx, 20, RULE_rlc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(223);
			match(Rlc);
			setState(224);
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
		enterRule(_localctx, 22, RULE_inc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(226);
			match(Inc);
			setState(227);
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
		enterRule(_localctx, 24, RULE_da);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(229);
			match(Da);
			setState(230);
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
		enterRule(_localctx, 26, RULE_pop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(232);
			match(Pop);
			setState(233);
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
		enterRule(_localctx, 28, RULE_com);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(235);
			match(Com);
			setState(236);
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
		enterRule(_localctx, 30, RULE_push);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(238);
			match(Push);
			setState(239);
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
		enterRule(_localctx, 32, RULE_decw);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(241);
			match(Decw);
			setState(242);
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
		enterRule(_localctx, 34, RULE_rl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(244);
			match(Rl);
			setState(245);
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
		enterRule(_localctx, 36, RULE_incw);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			match(Incw);
			setState(248);
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
		enterRule(_localctx, 38, RULE_clr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(250);
			match(Clr);
			setState(251);
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
		enterRule(_localctx, 40, RULE_rrc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(253);
			match(Rrc);
			setState(254);
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
		enterRule(_localctx, 42, RULE_sra);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256);
			match(Sra);
			setState(257);
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
		enterRule(_localctx, 44, RULE_rr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(259);
			match(Rr);
			setState(260);
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
		enterRule(_localctx, 46, RULE_swap);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(262);
			match(Swap);
			setState(263);
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
		enterRule(_localctx, 48, RULE_srp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(265);
			match(Srp);
			setState(266);
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
		enterRule(_localctx, 50, RULE_call);
		try {
			setState(272);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				_localctx = new CallAddressContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(268);
				match(Call);
				setState(269);
				address();
				}
				break;
			case 2:
				_localctx = new CallIregContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(270);
				match(Call);
				setState(271);
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
		enterRule(_localctx, 52, RULE_djnz);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(274);
			match(Djnz);
			setState(275);
			match(WorkingRegister);
			setState(276);
			match(Comma);
			setState(277);
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
		enterRule(_localctx, 54, RULE_jp);
		try {
			setState(287);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				_localctx = new JpIRegContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(279);
				match(Jp);
				setState(280);
				iregisterPair();
				}
				break;
			case 2:
				_localctx = new JpAddressContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(281);
				match(Jp);
				setState(282);
				address();
				}
				break;
			case 3:
				_localctx = new JpConditionAddressContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(283);
				match(Jp);
				setState(284);
				match(JpCondition);
				setState(285);
				match(Comma);
				setState(286);
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
		enterRule(_localctx, 56, RULE_jr);
		try {
			setState(295);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(289);
				match(Jr);
				setState(290);
				address();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(291);
				match(Jr);
				setState(292);
				match(JpCondition);
				setState(293);
				match(Comma);
				setState(294);
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
	public static class Ld4Context extends LdContext {
		public TerminalNode Ld() { return getToken(Z8AsmParser.Ld, 0); }
		public IregisterContext iregister() {
			return getRuleContext(IregisterContext.class,0);
		}
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
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

	public final LdContext ld() throws RecognitionException {
		LdContext _localctx = new LdContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_ld);
		try {
			setState(317);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				_localctx = new Ld1Context(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(297);
				match(Ld);
				setState(298);
				register();
				setState(299);
				match(Comma);
				setState(300);
				valueByte();
				}
				break;
			case 2:
				_localctx = new Ld2Context(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(302);
				match(Ld);
				setState(303);
				((Ld2Context)_localctx).target = register();
				setState(304);
				match(Comma);
				setState(305);
				((Ld2Context)_localctx).source = register();
				}
				break;
			case 3:
				_localctx = new Ld3Context(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(307);
				match(Ld);
				setState(308);
				register();
				setState(309);
				match(Comma);
				setState(310);
				iregister();
				}
				break;
			case 4:
				_localctx = new Ld4Context(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(312);
				match(Ld);
				setState(313);
				iregister();
				setState(314);
				match(Comma);
				setState(315);
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
		enterRule(_localctx, 60, RULE_ldc);
		try {
			setState(327);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				_localctx = new Ldc1Context(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(319);
				match(Ldc);
				setState(320);
				match(WorkingRegister);
				setState(321);
				match(Comma);
				setState(322);
				match(IWorkingRegisterPair);
				}
				break;
			case 2:
				_localctx = new Ldc2Context(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(323);
				match(Ldc);
				setState(324);
				match(IWorkingRegisterPair);
				setState(325);
				match(Comma);
				setState(326);
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
		enterRule(_localctx, 62, RULE_ldci);
		try {
			setState(337);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				_localctx = new Ldci1Context(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(329);
				match(Ldci);
				setState(330);
				match(IWorkingRegister);
				setState(331);
				match(Comma);
				setState(332);
				match(IWorkingRegisterPair);
				}
				break;
			case 2:
				_localctx = new Ldci2Context(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(333);
				match(Ldci);
				setState(334);
				match(IWorkingRegisterPair);
				setState(335);
				match(Comma);
				setState(336);
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
		enterRule(_localctx, 64, RULE_lde);
		try {
			setState(347);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				_localctx = new Lde1Context(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(339);
				match(Lde);
				setState(340);
				match(WorkingRegister);
				setState(341);
				match(Comma);
				setState(342);
				match(IWorkingRegisterPair);
				}
				break;
			case 2:
				_localctx = new Lde2Context(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(343);
				match(Lde);
				setState(344);
				match(IWorkingRegisterPair);
				setState(345);
				match(Comma);
				setState(346);
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
		enterRule(_localctx, 66, RULE_ldei);
		try {
			setState(357);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				_localctx = new Ldei1Context(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(349);
				match(Ldei);
				setState(350);
				match(IWorkingRegister);
				setState(351);
				match(Comma);
				setState(352);
				match(IWorkingRegisterPair);
				}
				break;
			case 2:
				_localctx = new Ldei2Context(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(353);
				match(Ldei);
				setState(354);
				match(IWorkingRegisterPair);
				setState(355);
				match(Comma);
				setState(356);
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
		enterRule(_localctx, 68, RULE_di);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(359);
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
		enterRule(_localctx, 70, RULE_ei);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(361);
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
		enterRule(_localctx, 72, RULE_ret);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(363);
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
		enterRule(_localctx, 74, RULE_iret);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(365);
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
		enterRule(_localctx, 76, RULE_rcf);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(367);
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
		enterRule(_localctx, 78, RULE_scf);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(369);
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
		enterRule(_localctx, 80, RULE_ccf);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(371);
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
		enterRule(_localctx, 82, RULE_nop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(373);
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
		enterRule(_localctx, 84, RULE_adc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(375);
			match(Adc);
			setState(376);
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
		enterRule(_localctx, 86, RULE_add);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(378);
			match(Add);
			setState(379);
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
		enterRule(_localctx, 88, RULE_and);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(381);
			match(And);
			setState(382);
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
		enterRule(_localctx, 90, RULE_cp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(384);
			match(Cp);
			setState(385);
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
		enterRule(_localctx, 92, RULE_or);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(387);
			match(Or);
			setState(388);
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
		enterRule(_localctx, 94, RULE_sbc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(390);
			match(Sbc);
			setState(391);
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
		enterRule(_localctx, 96, RULE_sub);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(393);
			match(Sub);
			setState(394);
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
		enterRule(_localctx, 98, RULE_tcm);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(396);
			match(Tcm);
			setState(397);
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
		enterRule(_localctx, 100, RULE_tm);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(399);
			match(Tm);
			setState(400);
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
		enterRule(_localctx, 102, RULE_xor);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(402);
			match(Xor);
			setState(403);
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
		enterRule(_localctx, 104, RULE_arithmeticParameters);
		try {
			setState(409);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(405);
				arithmeticParameters1();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(406);
				arithmeticParameters2();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(407);
				arithmeticParameters3();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(408);
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
		enterRule(_localctx, 106, RULE_arithmeticParameters1);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(411);
			register();
			setState(412);
			match(Comma);
			setState(413);
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
		enterRule(_localctx, 108, RULE_arithmeticParameters2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(415);
			((ArithmeticParameters2Context)_localctx).target = register();
			setState(416);
			match(Comma);
			setState(417);
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
		enterRule(_localctx, 110, RULE_arithmeticParameters3);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(419);
			iregister();
			setState(420);
			match(Comma);
			setState(421);
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
		enterRule(_localctx, 112, RULE_arithmeticParameters4);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(423);
			register();
			setState(424);
			match(Comma);
			setState(425);
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
		enterRule(_localctx, 114, RULE_registerOrIregister);
		try {
			setState(429);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Byte:
			case WorkingRegister:
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(427);
				register();
				}
				break;
			case IndirectPrefix:
			case IWorkingRegister:
				enterOuterAlt(_localctx, 2);
				{
				setState(428);
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
		enterRule(_localctx, 116, RULE_register);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(431);
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
		enterRule(_localctx, 118, RULE_iregister);
		try {
			setState(436);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IndirectPrefix:
				enterOuterAlt(_localctx, 1);
				{
				setState(433);
				match(IndirectPrefix);
				setState(434);
				register();
				}
				break;
			case IWorkingRegister:
				enterOuterAlt(_localctx, 2);
				{
				setState(435);
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
		enterRule(_localctx, 120, RULE_iregisterPair);
		try {
			setState(441);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IndirectPrefix:
				enterOuterAlt(_localctx, 1);
				{
				setState(438);
				match(IndirectPrefix);
				setState(439);
				register();
				}
				break;
			case IWorkingRegisterPair:
				enterOuterAlt(_localctx, 2);
				{
				setState(440);
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
		enterRule(_localctx, 122, RULE_valueByte);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(443);
			match(T__1);
			setState(444);
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
		enterRule(_localctx, 124, RULE_expression);
		int _la;
		try {
			setState(448);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Byte:
			case Word:
				_localctx = new ExprNumberContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(446);
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
				setState(447);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3C\u01c5\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\3\2\3\2\3\2\3\2\7\2\u0085\n\2\f\2\16\2\u0088\13\2\3"+
		"\3\3\3\3\3\5\3\u008d\n\3\3\4\3\4\5\4\u0091\n\4\3\5\3\5\3\5\5\5\u0096\n"+
		"\5\3\5\5\5\u0099\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\u00c6\n\6\3\6"+
		"\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\6\b\u00d1\n\b\r\b\16\b\u00d2\3\t\3\t"+
		"\3\t\3\t\3\t\5\t\u00da\n\t\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\r"+
		"\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21\3"+
		"\22\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3"+
		"\26\3\27\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\31\3\32\3\32\3\32\3\33\3"+
		"\33\3\33\3\33\5\33\u0113\n\33\3\34\3\34\3\34\3\34\3\34\3\35\3\35\3\35"+
		"\3\35\3\35\3\35\3\35\3\35\5\35\u0122\n\35\3\36\3\36\3\36\3\36\3\36\3\36"+
		"\5\36\u012a\n\36\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37"+
		"\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\5\37\u0140\n\37\3 \3 \3"+
		" \3 \3 \3 \3 \3 \5 \u014a\n \3!\3!\3!\3!\3!\3!\3!\3!\5!\u0154\n!\3\"\3"+
		"\"\3\"\3\"\3\"\3\"\3\"\3\"\5\"\u015e\n\"\3#\3#\3#\3#\3#\3#\3#\3#\5#\u0168"+
		"\n#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3,\3-\3-\3"+
		"-\3.\3.\3.\3/\3/\3/\3\60\3\60\3\60\3\61\3\61\3\61\3\62\3\62\3\62\3\63"+
		"\3\63\3\63\3\64\3\64\3\64\3\65\3\65\3\65\3\66\3\66\3\66\3\66\5\66\u019c"+
		"\n\66\3\67\3\67\3\67\3\67\38\38\38\38\39\39\39\39\3:\3:\3:\3:\3;\3;\5"+
		";\u01b0\n;\3<\3<\3=\3=\3=\5=\u01b7\n=\3>\3>\3>\5>\u01bc\n>\3?\3?\3?\3"+
		"@\3@\5@\u01c3\n@\3@\2\2A\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&("+
		"*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\2\5\3\3AA\5\2\b\b"+
		"\n\n??\3\2\b\t\2\u01cf\2\u0086\3\2\2\2\4\u0089\3\2\2\2\6\u0090\3\2\2\2"+
		"\b\u0095\3\2\2\2\n\u00c5\3\2\2\2\f\u00c9\3\2\2\2\16\u00ce\3\2\2\2\20\u00d9"+
		"\3\2\2\2\22\u00db\3\2\2\2\24\u00de\3\2\2\2\26\u00e1\3\2\2\2\30\u00e4\3"+
		"\2\2\2\32\u00e7\3\2\2\2\34\u00ea\3\2\2\2\36\u00ed\3\2\2\2 \u00f0\3\2\2"+
		"\2\"\u00f3\3\2\2\2$\u00f6\3\2\2\2&\u00f9\3\2\2\2(\u00fc\3\2\2\2*\u00ff"+
		"\3\2\2\2,\u0102\3\2\2\2.\u0105\3\2\2\2\60\u0108\3\2\2\2\62\u010b\3\2\2"+
		"\2\64\u0112\3\2\2\2\66\u0114\3\2\2\28\u0121\3\2\2\2:\u0129\3\2\2\2<\u013f"+
		"\3\2\2\2>\u0149\3\2\2\2@\u0153\3\2\2\2B\u015d\3\2\2\2D\u0167\3\2\2\2F"+
		"\u0169\3\2\2\2H\u016b\3\2\2\2J\u016d\3\2\2\2L\u016f\3\2\2\2N\u0171\3\2"+
		"\2\2P\u0173\3\2\2\2R\u0175\3\2\2\2T\u0177\3\2\2\2V\u0179\3\2\2\2X\u017c"+
		"\3\2\2\2Z\u017f\3\2\2\2\\\u0182\3\2\2\2^\u0185\3\2\2\2`\u0188\3\2\2\2"+
		"b\u018b\3\2\2\2d\u018e\3\2\2\2f\u0191\3\2\2\2h\u0194\3\2\2\2j\u019b\3"+
		"\2\2\2l\u019d\3\2\2\2n\u01a1\3\2\2\2p\u01a5\3\2\2\2r\u01a9\3\2\2\2t\u01af"+
		"\3\2\2\2v\u01b1\3\2\2\2x\u01b6\3\2\2\2z\u01bb\3\2\2\2|\u01bd\3\2\2\2~"+
		"\u01c2\3\2\2\2\u0080\u0085\5\4\3\2\u0081\u0085\5\b\5\2\u0082\u0085\5\n"+
		"\6\2\u0083\u0085\7A\2\2\u0084\u0080\3\2\2\2\u0084\u0081\3\2\2\2\u0084"+
		"\u0082\3\2\2\2\u0084\u0083\3\2\2\2\u0085\u0088\3\2\2\2\u0086\u0084\3\2"+
		"\2\2\u0086\u0087\3\2\2\2\u0087\3\3\2\2\2\u0088\u0086\3\2\2\2\u0089\u008a"+
		"\7?\2\2\u008a\u008c\7\16\2\2\u008b\u008d\7A\2\2\u008c\u008b\3\2\2\2\u008c"+
		"\u008d\3\2\2\2\u008d\5\3\2\2\2\u008e\u0091\7\t\2\2\u008f\u0091\7?\2\2"+
		"\u0090\u008e\3\2\2\2\u0090\u008f\3\2\2\2\u0091\7\3\2\2\2\u0092\u0096\5"+
		"\f\7\2\u0093\u0096\5\16\b\2\u0094\u0096\5\22\n\2\u0095\u0092\3\2\2\2\u0095"+
		"\u0093\3\2\2\2\u0095\u0094\3\2\2\2\u0096\u0098\3\2\2\2\u0097\u0099\7A"+
		"\2\2\u0098\u0097\3\2\2\2\u0098\u0099\3\2\2\2\u0099\t\3\2\2\2\u009a\u00c6"+
		"\5V,\2\u009b\u00c6\5X-\2\u009c\u00c6\5Z.\2\u009d\u00c6\5\64\33\2\u009e"+
		"\u00c6\5R*\2\u009f\u00c6\5(\25\2\u00a0\u00c6\5\36\20\2\u00a1\u00c6\5\\"+
		"/\2\u00a2\u00c6\5\32\16\2\u00a3\u00c6\5\24\13\2\u00a4\u00c6\5\"\22\2\u00a5"+
		"\u00c6\5F$\2\u00a6\u00c6\5\66\34\2\u00a7\u00c6\5H%\2\u00a8\u00c6\5\30"+
		"\r\2\u00a9\u00c6\5&\24\2\u00aa\u00c6\5L\'\2\u00ab\u00c6\58\35\2\u00ac"+
		"\u00c6\5:\36\2\u00ad\u00c6\5<\37\2\u00ae\u00c6\5> \2\u00af\u00c6\5@!\2"+
		"\u00b0\u00c6\5B\"\2\u00b1\u00c6\5D#\2\u00b2\u00c6\5T+\2\u00b3\u00c6\5"+
		"^\60\2\u00b4\u00c6\5\34\17\2\u00b5\u00c6\5 \21\2\u00b6\u00c6\5N(\2\u00b7"+
		"\u00c6\5J&\2\u00b8\u00c6\5$\23\2\u00b9\u00c6\5\26\f\2\u00ba\u00c6\5.\30"+
		"\2\u00bb\u00c6\5*\26\2\u00bc\u00c6\5`\61\2\u00bd\u00c6\5P)\2\u00be\u00c6"+
		"\5,\27\2\u00bf\u00c6\5\62\32\2\u00c0\u00c6\5b\62\2\u00c1\u00c6\5\60\31"+
		"\2\u00c2\u00c6\5d\63\2\u00c3\u00c6\5f\64\2\u00c4\u00c6\5h\65\2\u00c5\u009a"+
		"\3\2\2\2\u00c5\u009b\3\2\2\2\u00c5\u009c\3\2\2\2\u00c5\u009d\3\2\2\2\u00c5"+
		"\u009e\3\2\2\2\u00c5\u009f\3\2\2\2\u00c5\u00a0\3\2\2\2\u00c5\u00a1\3\2"+
		"\2\2\u00c5\u00a2\3\2\2\2\u00c5\u00a3\3\2\2\2\u00c5\u00a4\3\2\2\2\u00c5"+
		"\u00a5\3\2\2\2\u00c5\u00a6\3\2\2\2\u00c5\u00a7\3\2\2\2\u00c5\u00a8\3\2"+
		"\2\2\u00c5\u00a9\3\2\2\2\u00c5\u00aa\3\2\2\2\u00c5\u00ab\3\2\2\2\u00c5"+
		"\u00ac\3\2\2\2\u00c5\u00ad\3\2\2\2\u00c5\u00ae\3\2\2\2\u00c5\u00af\3\2"+
		"\2\2\u00c5\u00b0\3\2\2\2\u00c5\u00b1\3\2\2\2\u00c5\u00b2\3\2\2\2\u00c5"+
		"\u00b3\3\2\2\2\u00c5\u00b4\3\2\2\2\u00c5\u00b5\3\2\2\2\u00c5\u00b6\3\2"+
		"\2\2\u00c5\u00b7\3\2\2\2\u00c5\u00b8\3\2\2\2\u00c5\u00b9\3\2\2\2\u00c5"+
		"\u00ba\3\2\2\2\u00c5\u00bb\3\2\2\2\u00c5\u00bc\3\2\2\2\u00c5\u00bd\3\2"+
		"\2\2\u00c5\u00be\3\2\2\2\u00c5\u00bf\3\2\2\2\u00c5\u00c0\3\2\2\2\u00c5"+
		"\u00c1\3\2\2\2\u00c5\u00c2\3\2\2\2\u00c5\u00c3\3\2\2\2\u00c5\u00c4\3\2"+
		"\2\2\u00c6\u00c7\3\2\2\2\u00c7\u00c8\t\2\2\2\u00c8\13\3\2\2\2\u00c9\u00ca"+
		"\7\20\2\2\u00ca\u00cb\7?\2\2\u00cb\u00cc\7\3\2\2\u00cc\u00cd\5~@\2\u00cd"+
		"\r\3\2\2\2\u00ce\u00d0\7\21\2\2\u00cf\u00d1\5\20\t\2\u00d0\u00cf\3\2\2"+
		"\2\u00d1\u00d2\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d2\u00d3\3\2\2\2\u00d3\17"+
		"\3\2\2\2\u00d4\u00da\7\b\2\2\u00d5\u00da\5\6\4\2\u00d6\u00da\7\5\2\2\u00d7"+
		"\u00da\7\6\2\2\u00d8\u00da\7\7\2\2\u00d9\u00d4\3\2\2\2\u00d9\u00d5\3\2"+
		"\2\2\u00d9\u00d6\3\2\2\2\u00d9\u00d7\3\2\2\2\u00d9\u00d8\3\2\2\2\u00da"+
		"\21\3\2\2\2\u00db\u00dc\7\22\2\2\u00dc\u00dd\7\t\2\2\u00dd\23\3\2\2\2"+
		"\u00de\u00df\7\34\2\2\u00df\u00e0\5t;\2\u00e0\25\3\2\2\2\u00e1\u00e2\7"+
		"\62\2\2\u00e2\u00e3\5t;\2\u00e3\27\3\2\2\2\u00e4\u00e5\7!\2\2\u00e5\u00e6"+
		"\5t;\2\u00e6\31\3\2\2\2\u00e7\u00e8\7\33\2\2\u00e8\u00e9\5t;\2\u00e9\33"+
		"\3\2\2\2\u00ea\u00eb\7-\2\2\u00eb\u00ec\5t;\2\u00ec\35\3\2\2\2\u00ed\u00ee"+
		"\7\31\2\2\u00ee\u00ef\5t;\2\u00ef\37\3\2\2\2\u00f0\u00f1\7.\2\2\u00f1"+
		"\u00f2\5t;\2\u00f2!\3\2\2\2\u00f3\u00f4\7\35\2\2\u00f4\u00f5\5t;\2\u00f5"+
		"#\3\2\2\2\u00f6\u00f7\7\61\2\2\u00f7\u00f8\5t;\2\u00f8%\3\2\2\2\u00f9"+
		"\u00fa\7\"\2\2\u00fa\u00fb\5t;\2\u00fb\'\3\2\2\2\u00fc\u00fd\7\30\2\2"+
		"\u00fd\u00fe\5t;\2\u00fe)\3\2\2\2\u00ff\u0100\7\64\2\2\u0100\u0101\5t"+
		";\2\u0101+\3\2\2\2\u0102\u0103\7\67\2\2\u0103\u0104\5t;\2\u0104-\3\2\2"+
		"\2\u0105\u0106\7\63\2\2\u0106\u0107\5t;\2\u0107/\3\2\2\2\u0108\u0109\7"+
		":\2\2\u0109\u010a\5t;\2\u010a\61\3\2\2\2\u010b\u010c\78\2\2\u010c\u010d"+
		"\5|?\2\u010d\63\3\2\2\2\u010e\u010f\7\26\2\2\u010f\u0113\5\6\4\2\u0110"+
		"\u0111\7\26\2\2\u0111\u0113\5z>\2\u0112\u010e\3\2\2\2\u0112\u0110\3\2"+
		"\2\2\u0113\65\3\2\2\2\u0114\u0115\7\37\2\2\u0115\u0116\7\n\2\2\u0116\u0117"+
		"\7\17\2\2\u0117\u0118\5\6\4\2\u0118\67\3\2\2\2\u0119\u011a\7$\2\2\u011a"+
		"\u0122\5z>\2\u011b\u011c\7$\2\2\u011c\u0122\5\6\4\2\u011d\u011e\7$\2\2"+
		"\u011e\u011f\7>\2\2\u011f\u0120\7\17\2\2\u0120\u0122\5\6\4\2\u0121\u0119"+
		"\3\2\2\2\u0121\u011b\3\2\2\2\u0121\u011d\3\2\2\2\u01229\3\2\2\2\u0123"+
		"\u0124\7%\2\2\u0124\u012a\5\6\4\2\u0125\u0126\7%\2\2\u0126\u0127\7>\2"+
		"\2\u0127\u0128\7\17\2\2\u0128\u012a\5\6\4\2\u0129\u0123\3\2\2\2\u0129"+
		"\u0125\3\2\2\2\u012a;\3\2\2\2\u012b\u012c\7&\2\2\u012c\u012d\5v<\2\u012d"+
		"\u012e\7\17\2\2\u012e\u012f\5|?\2\u012f\u0140\3\2\2\2\u0130\u0131\7&\2"+
		"\2\u0131\u0132\5v<\2\u0132\u0133\7\17\2\2\u0133\u0134\5v<\2\u0134\u0140"+
		"\3\2\2\2\u0135\u0136\7&\2\2\u0136\u0137\5v<\2\u0137\u0138\7\17\2\2\u0138"+
		"\u0139\5x=\2\u0139\u0140\3\2\2\2\u013a\u013b\7&\2\2\u013b\u013c\5x=\2"+
		"\u013c\u013d\7\17\2\2\u013d\u013e\5v<\2\u013e\u0140\3\2\2\2\u013f\u012b"+
		"\3\2\2\2\u013f\u0130\3\2\2\2\u013f\u0135\3\2\2\2\u013f\u013a\3\2\2\2\u0140"+
		"=\3\2\2\2\u0141\u0142\7\'\2\2\u0142\u0143\7\n\2\2\u0143\u0144\7\17\2\2"+
		"\u0144\u014a\7\r\2\2\u0145\u0146\7\'\2\2\u0146\u0147\7\r\2\2\u0147\u0148"+
		"\7\17\2\2\u0148\u014a\7\n\2\2\u0149\u0141\3\2\2\2\u0149\u0145\3\2\2\2"+
		"\u014a?\3\2\2\2\u014b\u014c\7(\2\2\u014c\u014d\7\f\2\2\u014d\u014e\7\17"+
		"\2\2\u014e\u0154\7\r\2\2\u014f\u0150\7(\2\2\u0150\u0151\7\r\2\2\u0151"+
		"\u0152\7\17\2\2\u0152\u0154\7\f\2\2\u0153\u014b\3\2\2\2\u0153\u014f\3"+
		"\2\2\2\u0154A\3\2\2\2\u0155\u0156\7)\2\2\u0156\u0157\7\n\2\2\u0157\u0158"+
		"\7\17\2\2\u0158\u015e\7\r\2\2\u0159\u015a\7)\2\2\u015a\u015b\7\r\2\2\u015b"+
		"\u015c\7\17\2\2\u015c\u015e\7\n\2\2\u015d\u0155\3\2\2\2\u015d\u0159\3"+
		"\2\2\2\u015eC\3\2\2\2\u015f\u0160\7*\2\2\u0160\u0161\7\f\2\2\u0161\u0162"+
		"\7\17\2\2\u0162\u0168\7\r\2\2\u0163\u0164\7*\2\2\u0164\u0165\7\r\2\2\u0165"+
		"\u0166\7\17\2\2\u0166\u0168\7\f\2\2\u0167\u015f\3\2\2\2\u0167\u0163\3"+
		"\2\2\2\u0168E\3\2\2\2\u0169\u016a\7\36\2\2\u016aG\3\2\2\2\u016b\u016c"+
		"\7 \2\2\u016cI\3\2\2\2\u016d\u016e\7\60\2\2\u016eK\3\2\2\2\u016f\u0170"+
		"\7#\2\2\u0170M\3\2\2\2\u0171\u0172\7/\2\2\u0172O\3\2\2\2\u0173\u0174\7"+
		"\66\2\2\u0174Q\3\2\2\2\u0175\u0176\7\27\2\2\u0176S\3\2\2\2\u0177\u0178"+
		"\7+\2\2\u0178U\3\2\2\2\u0179\u017a\7\23\2\2\u017a\u017b\5j\66\2\u017b"+
		"W\3\2\2\2\u017c\u017d\7\24\2\2\u017d\u017e\5j\66\2\u017eY\3\2\2\2\u017f"+
		"\u0180\7\25\2\2\u0180\u0181\5j\66\2\u0181[\3\2\2\2\u0182\u0183\7\32\2"+
		"\2\u0183\u0184\5j\66\2\u0184]\3\2\2\2\u0185\u0186\7,\2\2\u0186\u0187\5"+
		"j\66\2\u0187_\3\2\2\2\u0188\u0189\7\65\2\2\u0189\u018a\5j\66\2\u018aa"+
		"\3\2\2\2\u018b\u018c\79\2\2\u018c\u018d\5j\66\2\u018dc\3\2\2\2\u018e\u018f"+
		"\7;\2\2\u018f\u0190\5j\66\2\u0190e\3\2\2\2\u0191\u0192\7<\2\2\u0192\u0193"+
		"\5j\66\2\u0193g\3\2\2\2\u0194\u0195\7=\2\2\u0195\u0196\5j\66\2\u0196i"+
		"\3\2\2\2\u0197\u019c\5l\67\2\u0198\u019c\5n8\2\u0199\u019c\5p9\2\u019a"+
		"\u019c\5r:\2\u019b\u0197\3\2\2\2\u019b\u0198\3\2\2\2\u019b\u0199\3\2\2"+
		"\2\u019b\u019a\3\2\2\2\u019ck\3\2\2\2\u019d\u019e\5v<\2\u019e\u019f\7"+
		"\17\2\2\u019f\u01a0\5|?\2\u01a0m\3\2\2\2\u01a1\u01a2\5v<\2\u01a2\u01a3"+
		"\7\17\2\2\u01a3\u01a4\5v<\2\u01a4o\3\2\2\2\u01a5\u01a6\5x=\2\u01a6\u01a7"+
		"\7\17\2\2\u01a7\u01a8\5|?\2\u01a8q\3\2\2\2\u01a9\u01aa\5v<\2\u01aa\u01ab"+
		"\7\17\2\2\u01ab\u01ac\5x=\2\u01acs\3\2\2\2\u01ad\u01b0\5v<\2\u01ae\u01b0"+
		"\5x=\2\u01af\u01ad\3\2\2\2\u01af\u01ae\3\2\2\2\u01b0u\3\2\2\2\u01b1\u01b2"+
		"\t\3\2\2\u01b2w\3\2\2\2\u01b3\u01b4\7\13\2\2\u01b4\u01b7\5v<\2\u01b5\u01b7"+
		"\7\f\2\2\u01b6\u01b3\3\2\2\2\u01b6\u01b5\3\2\2\2\u01b7y\3\2\2\2\u01b8"+
		"\u01b9\7\13\2\2\u01b9\u01bc\5v<\2\u01ba\u01bc\7\r\2\2\u01bb\u01b8\3\2"+
		"\2\2\u01bb\u01ba\3\2\2\2\u01bc{\3\2\2\2\u01bd\u01be\7\4\2\2\u01be\u01bf"+
		"\5~@\2\u01bf}\3\2\2\2\u01c0\u01c3\t\4\2\2\u01c1\u01c3\7\7\2\2\u01c2\u01c0"+
		"\3\2\2\2\u01c2\u01c1\3\2\2\2\u01c3\177\3\2\2\2\30\u0084\u0086\u008c\u0090"+
		"\u0095\u0098\u00c5\u00d2\u00d9\u0112\u0121\u0129\u013f\u0149\u0153\u015d"+
		"\u0167\u019b\u01af\u01b6\u01bb\u01c2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}