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
		LString=1, String=2, Char=3, Byte=4, Word=5, ValueByte=6, WorkingRegister=7, 
		IndirectPrefix=8, IWorkingRegister=9, IWorkingRegisterPair=10, Colon=11, 
		Comma=12, Data=13, Org=14, Adc=15, Add=16, And=17, Call=18, Ccf=19, Clr=20, 
		Com=21, Cp=22, Da=23, Dec=24, Decw=25, Di=26, Djnz=27, Ei=28, Inc=29, 
		Incw=30, Iret=31, Jp=32, Jr=33, Ld=34, Ldc=35, Ldci=36, Lde=37, Ldei=38, 
		Nop=39, Or=40, Pop=41, Push=42, Rcf=43, Ret=44, Rl=45, Rlc=46, Rr=47, 
		Rrc=48, Sbc=49, Scf=50, Sra=51, Srp=52, Sub=53, Swap=54, Tcm=55, Tm=56, 
		Xor=57, RegisterConstant=58, JpCondition=59, Identifier=60, Whitespace=61, 
		NL=62, LineComment=63, BlockComment=64;
	public static final int
		RULE_code = 0, RULE_labelDefinition = 1, RULE_address = 2, RULE_command = 3, 
		RULE_data = 4, RULE_dataItem = 5, RULE_org = 6, RULE_dec = 7, RULE_rlc = 8, 
		RULE_inc = 9, RULE_da = 10, RULE_pop = 11, RULE_com = 12, RULE_push = 13, 
		RULE_decw = 14, RULE_rl = 15, RULE_incw = 16, RULE_clr = 17, RULE_rrc = 18, 
		RULE_sra = 19, RULE_rr = 20, RULE_swap = 21, RULE_srp = 22, RULE_call = 23, 
		RULE_djnz = 24, RULE_jp = 25, RULE_jr = 26, RULE_ld = 27, RULE_ld1 = 28, 
		RULE_ld2 = 29, RULE_ldc = 30, RULE_ldc1 = 31, RULE_ldc2 = 32, RULE_ldci = 33, 
		RULE_ldci1 = 34, RULE_ldci2 = 35, RULE_lde = 36, RULE_lde1 = 37, RULE_lde2 = 38, 
		RULE_ldei = 39, RULE_ldei1 = 40, RULE_ldei2 = 41, RULE_di = 42, RULE_ei = 43, 
		RULE_ret = 44, RULE_iret = 45, RULE_rcf = 46, RULE_scf = 47, RULE_ccf = 48, 
		RULE_nop = 49, RULE_adc = 50, RULE_add = 51, RULE_and = 52, RULE_cp = 53, 
		RULE_or = 54, RULE_sbc = 55, RULE_sub = 56, RULE_tcm = 57, RULE_tm = 58, 
		RULE_xor = 59, RULE_arithmeticParameters = 60, RULE_arithmeticParameters1 = 61, 
		RULE_arithmeticParameters2 = 62, RULE_arithmeticParameters3 = 63, RULE_registerOrIregister = 64, 
		RULE_register = 65, RULE_iregister = 66, RULE_iregisterPair = 67;
	private static String[] makeRuleNames() {
		return new String[] {
			"code", "labelDefinition", "address", "command", "data", "dataItem", 
			"org", "dec", "rlc", "inc", "da", "pop", "com", "push", "decw", "rl", 
			"incw", "clr", "rrc", "sra", "rr", "swap", "srp", "call", "djnz", "jp", 
			"jr", "ld", "ld1", "ld2", "ldc", "ldc1", "ldc2", "ldci", "ldci1", "ldci2", 
			"lde", "lde1", "lde2", "ldei", "ldei1", "ldei2", "di", "ei", "ret", "iret", 
			"rcf", "scf", "ccf", "nop", "adc", "add", "and", "cp", "or", "sbc", "sub", 
			"tcm", "tm", "xor", "arithmeticParameters", "arithmeticParameters1", 
			"arithmeticParameters2", "arithmeticParameters3", "registerOrIregister", 
			"register", "iregister", "iregisterPair"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, "'@'", null, null, "':'", 
			"','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "LString", "String", "Char", "Byte", "Word", "ValueByte", "WorkingRegister", 
			"IndirectPrefix", "IWorkingRegister", "IWorkingRegisterPair", "Colon", 
			"Comma", "Data", "Org", "Adc", "Add", "And", "Call", "Ccf", "Clr", "Com", 
			"Cp", "Da", "Dec", "Decw", "Di", "Djnz", "Ei", "Inc", "Incw", "Iret", 
			"Jp", "Jr", "Ld", "Ldc", "Ldci", "Lde", "Ldei", "Nop", "Or", "Pop", "Push", 
			"Rcf", "Ret", "Rl", "Rlc", "Rr", "Rrc", "Sbc", "Scf", "Sra", "Srp", "Sub", 
			"Swap", "Tcm", "Tm", "Xor", "RegisterConstant", "JpCondition", "Identifier", 
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
	}

	public final CodeContext code() throws RecognitionException {
		CodeContext _localctx = new CodeContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_code);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Data) | (1L << Org) | (1L << Adc) | (1L << Add) | (1L << And) | (1L << Call) | (1L << Ccf) | (1L << Clr) | (1L << Com) | (1L << Cp) | (1L << Da) | (1L << Dec) | (1L << Decw) | (1L << Di) | (1L << Djnz) | (1L << Ei) | (1L << Inc) | (1L << Incw) | (1L << Iret) | (1L << Jp) | (1L << Jr) | (1L << Ld) | (1L << Ldc) | (1L << Ldci) | (1L << Lde) | (1L << Ldei) | (1L << Nop) | (1L << Or) | (1L << Pop) | (1L << Push) | (1L << Rcf) | (1L << Ret) | (1L << Rl) | (1L << Rlc) | (1L << Rr) | (1L << Rrc) | (1L << Sbc) | (1L << Scf) | (1L << Sra) | (1L << Srp) | (1L << Sub) | (1L << Swap) | (1L << Tcm) | (1L << Tm) | (1L << Xor) | (1L << Identifier) | (1L << NL))) != 0)) {
				{
				setState(139);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case Identifier:
					{
					setState(136);
					labelDefinition();
					}
					break;
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
					{
					setState(137);
					command();
					}
					break;
				case NL:
					{
					setState(138);
					match(NL);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(143);
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
	}

	public final LabelDefinitionContext labelDefinition() throws RecognitionException {
		LabelDefinitionContext _localctx = new LabelDefinitionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_labelDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144);
			match(Identifier);
			setState(145);
			match(Colon);
			setState(147);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				setState(146);
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
	}

	public final AddressContext address() throws RecognitionException {
		AddressContext _localctx = new AddressContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_address);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			_la = _input.LA(1);
			if ( !(_la==Word || _la==Identifier) ) {
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

	public static class CommandContext extends ParserRuleContext {
		public TerminalNode NL() { return getToken(Z8AsmParser.NL, 0); }
		public TerminalNode EOF() { return getToken(Z8AsmParser.EOF, 0); }
		public DataContext data() {
			return getRuleContext(DataContext.class,0);
		}
		public OrgContext org() {
			return getRuleContext(OrgContext.class,0);
		}
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
	}

	public final CommandContext command() throws RecognitionException {
		CommandContext _localctx = new CommandContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_command);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(196);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Data:
				{
				setState(151);
				data();
				}
				break;
			case Org:
				{
				setState(152);
				org();
				}
				break;
			case Adc:
				{
				setState(153);
				adc();
				}
				break;
			case Add:
				{
				setState(154);
				add();
				}
				break;
			case And:
				{
				setState(155);
				and();
				}
				break;
			case Call:
				{
				setState(156);
				call();
				}
				break;
			case Ccf:
				{
				setState(157);
				ccf();
				}
				break;
			case Clr:
				{
				setState(158);
				clr();
				}
				break;
			case Com:
				{
				setState(159);
				com();
				}
				break;
			case Cp:
				{
				setState(160);
				cp();
				}
				break;
			case Da:
				{
				setState(161);
				da();
				}
				break;
			case Dec:
				{
				setState(162);
				dec();
				}
				break;
			case Decw:
				{
				setState(163);
				decw();
				}
				break;
			case Di:
				{
				setState(164);
				di();
				}
				break;
			case Djnz:
				{
				setState(165);
				djnz();
				}
				break;
			case Ei:
				{
				setState(166);
				ei();
				}
				break;
			case Inc:
				{
				setState(167);
				inc();
				}
				break;
			case Incw:
				{
				setState(168);
				incw();
				}
				break;
			case Iret:
				{
				setState(169);
				iret();
				}
				break;
			case Jp:
				{
				setState(170);
				jp();
				}
				break;
			case Jr:
				{
				setState(171);
				jr();
				}
				break;
			case Ld:
				{
				setState(172);
				ld();
				}
				break;
			case Ldc:
				{
				setState(173);
				ldc();
				}
				break;
			case Ldci:
				{
				setState(174);
				ldci();
				}
				break;
			case Lde:
				{
				setState(175);
				lde();
				}
				break;
			case Ldei:
				{
				setState(176);
				ldei();
				}
				break;
			case Nop:
				{
				setState(177);
				nop();
				}
				break;
			case Or:
				{
				setState(178);
				or();
				}
				break;
			case Pop:
				{
				setState(179);
				pop();
				}
				break;
			case Push:
				{
				setState(180);
				push();
				}
				break;
			case Rcf:
				{
				setState(181);
				rcf();
				}
				break;
			case Ret:
				{
				setState(182);
				ret();
				}
				break;
			case Rl:
				{
				setState(183);
				rl();
				}
				break;
			case Rlc:
				{
				setState(184);
				rlc();
				}
				break;
			case Rr:
				{
				setState(185);
				rr();
				}
				break;
			case Rrc:
				{
				setState(186);
				rrc();
				}
				break;
			case Sbc:
				{
				setState(187);
				sbc();
				}
				break;
			case Scf:
				{
				setState(188);
				scf();
				}
				break;
			case Sra:
				{
				setState(189);
				sra();
				}
				break;
			case Srp:
				{
				setState(190);
				srp();
				}
				break;
			case Sub:
				{
				setState(191);
				sub();
				}
				break;
			case Swap:
				{
				setState(192);
				swap();
				}
				break;
			case Tcm:
				{
				setState(193);
				tcm();
				}
				break;
			case Tm:
				{
				setState(194);
				tm();
				}
				break;
			case Xor:
				{
				setState(195);
				xor();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(198);
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
	}

	public final DataContext data() throws RecognitionException {
		DataContext _localctx = new DataContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_data);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			match(Data);
			setState(202); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(201);
				dataItem();
				}
				}
				setState(204); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LString) | (1L << String) | (1L << Char) | (1L << Byte) | (1L << Word) | (1L << Identifier))) != 0) );
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
		public TerminalNode Byte() { return getToken(Z8AsmParser.Byte, 0); }
		public AddressContext address() {
			return getRuleContext(AddressContext.class,0);
		}
		public TerminalNode LString() { return getToken(Z8AsmParser.LString, 0); }
		public TerminalNode String() { return getToken(Z8AsmParser.String, 0); }
		public TerminalNode Char() { return getToken(Z8AsmParser.Char, 0); }
		public DataItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterDataItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitDataItem(this);
		}
	}

	public final DataItemContext dataItem() throws RecognitionException {
		DataItemContext _localctx = new DataItemContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_dataItem);
		try {
			setState(211);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Byte:
				enterOuterAlt(_localctx, 1);
				{
				setState(206);
				match(Byte);
				}
				break;
			case Word:
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(207);
				address();
				}
				break;
			case LString:
				enterOuterAlt(_localctx, 3);
				{
				setState(208);
				match(LString);
				}
				break;
			case String:
				enterOuterAlt(_localctx, 4);
				{
				setState(209);
				match(String);
				}
				break;
			case Char:
				enterOuterAlt(_localctx, 5);
				{
				setState(210);
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
	}

	public final OrgContext org() throws RecognitionException {
		OrgContext _localctx = new OrgContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_org);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			match(Org);
			setState(214);
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
	}

	public final DecContext dec() throws RecognitionException {
		DecContext _localctx = new DecContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_dec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			match(Dec);
			setState(217);
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
	}

	public final RlcContext rlc() throws RecognitionException {
		RlcContext _localctx = new RlcContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_rlc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(219);
			match(Rlc);
			setState(220);
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
	}

	public final IncContext inc() throws RecognitionException {
		IncContext _localctx = new IncContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_inc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
			match(Inc);
			setState(223);
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
	}

	public final DaContext da() throws RecognitionException {
		DaContext _localctx = new DaContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_da);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(225);
			match(Da);
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
	}

	public final PopContext pop() throws RecognitionException {
		PopContext _localctx = new PopContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_pop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(228);
			match(Pop);
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
	}

	public final ComContext com() throws RecognitionException {
		ComContext _localctx = new ComContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_com);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(231);
			match(Com);
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
	}

	public final PushContext push() throws RecognitionException {
		PushContext _localctx = new PushContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_push);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(234);
			match(Push);
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
	}

	public final DecwContext decw() throws RecognitionException {
		DecwContext _localctx = new DecwContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_decw);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(237);
			match(Decw);
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
	}

	public final RlContext rl() throws RecognitionException {
		RlContext _localctx = new RlContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_rl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(240);
			match(Rl);
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
	}

	public final IncwContext incw() throws RecognitionException {
		IncwContext _localctx = new IncwContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_incw);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(243);
			match(Incw);
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
	}

	public final ClrContext clr() throws RecognitionException {
		ClrContext _localctx = new ClrContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_clr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(246);
			match(Clr);
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
	}

	public final RrcContext rrc() throws RecognitionException {
		RrcContext _localctx = new RrcContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_rrc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(249);
			match(Rrc);
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
	}

	public final SraContext sra() throws RecognitionException {
		SraContext _localctx = new SraContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_sra);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(252);
			match(Sra);
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
	}

	public final RrContext rr() throws RecognitionException {
		RrContext _localctx = new RrContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_rr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(255);
			match(Rr);
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
	}

	public final SwapContext swap() throws RecognitionException {
		SwapContext _localctx = new SwapContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_swap);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(258);
			match(Swap);
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

	public static class SrpContext extends ParserRuleContext {
		public TerminalNode Srp() { return getToken(Z8AsmParser.Srp, 0); }
		public TerminalNode ValueByte() { return getToken(Z8AsmParser.ValueByte, 0); }
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
	}

	public final SrpContext srp() throws RecognitionException {
		SrpContext _localctx = new SrpContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_srp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(261);
			match(Srp);
			setState(262);
			match(ValueByte);
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
		public TerminalNode Call() { return getToken(Z8AsmParser.Call, 0); }
		public AddressContext address() {
			return getRuleContext(AddressContext.class,0);
		}
		public IregisterPairContext iregisterPair() {
			return getRuleContext(IregisterPairContext.class,0);
		}
		public CallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_call; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitCall(this);
		}
	}

	public final CallContext call() throws RecognitionException {
		CallContext _localctx = new CallContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_call);
		try {
			setState(268);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(264);
				match(Call);
				setState(265);
				address();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(266);
				match(Call);
				setState(267);
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
	}

	public final DjnzContext djnz() throws RecognitionException {
		DjnzContext _localctx = new DjnzContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_djnz);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(270);
			match(Djnz);
			setState(271);
			match(WorkingRegister);
			setState(272);
			match(Comma);
			setState(273);
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
		public TerminalNode Jp() { return getToken(Z8AsmParser.Jp, 0); }
		public IregisterPairContext iregisterPair() {
			return getRuleContext(IregisterPairContext.class,0);
		}
		public AddressContext address() {
			return getRuleContext(AddressContext.class,0);
		}
		public TerminalNode JpCondition() { return getToken(Z8AsmParser.JpCondition, 0); }
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public JpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterJp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitJp(this);
		}
	}

	public final JpContext jp() throws RecognitionException {
		JpContext _localctx = new JpContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_jp);
		try {
			setState(283);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(275);
				match(Jp);
				setState(276);
				iregisterPair();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(277);
				match(Jp);
				setState(278);
				address();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(279);
				match(Jp);
				setState(280);
				match(JpCondition);
				setState(281);
				match(Comma);
				setState(282);
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
	}

	public final JrContext jr() throws RecognitionException {
		JrContext _localctx = new JrContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_jr);
		try {
			setState(291);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(285);
				match(Jr);
				setState(286);
				address();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(287);
				match(Jr);
				setState(288);
				match(JpCondition);
				setState(289);
				match(Comma);
				setState(290);
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
		public Ld1Context ld1() {
			return getRuleContext(Ld1Context.class,0);
		}
		public Ld2Context ld2() {
			return getRuleContext(Ld2Context.class,0);
		}
		public LdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ld; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLd(this);
		}
	}

	public final LdContext ld() throws RecognitionException {
		LdContext _localctx = new LdContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_ld);
		try {
			setState(295);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(293);
				ld1();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(294);
				ld2();
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

	public static class Ld1Context extends ParserRuleContext {
		public TerminalNode Ld() { return getToken(Z8AsmParser.Ld, 0); }
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public RegisterOrIregisterContext registerOrIregister() {
			return getRuleContext(RegisterOrIregisterContext.class,0);
		}
		public TerminalNode ValueByte() { return getToken(Z8AsmParser.ValueByte, 0); }
		public Ld1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ld1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLd1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLd1(this);
		}
	}

	public final Ld1Context ld1() throws RecognitionException {
		Ld1Context _localctx = new Ld1Context(_ctx, getState());
		enterRule(_localctx, 56, RULE_ld1);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(297);
			match(Ld);
			setState(298);
			register();
			setState(299);
			match(Comma);
			setState(302);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Byte:
			case WorkingRegister:
			case IndirectPrefix:
			case IWorkingRegister:
			case RegisterConstant:
				{
				setState(300);
				registerOrIregister();
				}
				break;
			case ValueByte:
				{
				setState(301);
				match(ValueByte);
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class Ld2Context extends ParserRuleContext {
		public TerminalNode Ld() { return getToken(Z8AsmParser.Ld, 0); }
		public IregisterContext iregister() {
			return getRuleContext(IregisterContext.class,0);
		}
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public Ld2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ld2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLd2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLd2(this);
		}
	}

	public final Ld2Context ld2() throws RecognitionException {
		Ld2Context _localctx = new Ld2Context(_ctx, getState());
		enterRule(_localctx, 58, RULE_ld2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(304);
			match(Ld);
			setState(305);
			iregister();
			setState(306);
			match(Comma);
			setState(307);
			register();
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
		public Ldc1Context ldc1() {
			return getRuleContext(Ldc1Context.class,0);
		}
		public Ldc2Context ldc2() {
			return getRuleContext(Ldc2Context.class,0);
		}
		public LdcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ldc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLdc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLdc(this);
		}
	}

	public final LdcContext ldc() throws RecognitionException {
		LdcContext _localctx = new LdcContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_ldc);
		try {
			setState(311);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(309);
				ldc1();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(310);
				ldc2();
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

	public static class Ldc1Context extends ParserRuleContext {
		public TerminalNode Ldc() { return getToken(Z8AsmParser.Ldc, 0); }
		public TerminalNode WorkingRegister() { return getToken(Z8AsmParser.WorkingRegister, 0); }
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public TerminalNode IWorkingRegisterPair() { return getToken(Z8AsmParser.IWorkingRegisterPair, 0); }
		public Ldc1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ldc1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLdc1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLdc1(this);
		}
	}

	public final Ldc1Context ldc1() throws RecognitionException {
		Ldc1Context _localctx = new Ldc1Context(_ctx, getState());
		enterRule(_localctx, 62, RULE_ldc1);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(313);
			match(Ldc);
			setState(314);
			match(WorkingRegister);
			setState(315);
			match(Comma);
			setState(316);
			match(IWorkingRegisterPair);
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

	public static class Ldc2Context extends ParserRuleContext {
		public TerminalNode Ldc() { return getToken(Z8AsmParser.Ldc, 0); }
		public TerminalNode IWorkingRegisterPair() { return getToken(Z8AsmParser.IWorkingRegisterPair, 0); }
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public TerminalNode WorkingRegister() { return getToken(Z8AsmParser.WorkingRegister, 0); }
		public Ldc2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ldc2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLdc2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLdc2(this);
		}
	}

	public final Ldc2Context ldc2() throws RecognitionException {
		Ldc2Context _localctx = new Ldc2Context(_ctx, getState());
		enterRule(_localctx, 64, RULE_ldc2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(318);
			match(Ldc);
			setState(319);
			match(IWorkingRegisterPair);
			setState(320);
			match(Comma);
			setState(321);
			match(WorkingRegister);
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
		public Ldci1Context ldci1() {
			return getRuleContext(Ldci1Context.class,0);
		}
		public Ldci2Context ldci2() {
			return getRuleContext(Ldci2Context.class,0);
		}
		public LdciContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ldci; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLdci(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLdci(this);
		}
	}

	public final LdciContext ldci() throws RecognitionException {
		LdciContext _localctx = new LdciContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_ldci);
		try {
			setState(325);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(323);
				ldci1();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(324);
				ldci2();
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

	public static class Ldci1Context extends ParserRuleContext {
		public TerminalNode Ldci() { return getToken(Z8AsmParser.Ldci, 0); }
		public TerminalNode IWorkingRegister() { return getToken(Z8AsmParser.IWorkingRegister, 0); }
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public TerminalNode IWorkingRegisterPair() { return getToken(Z8AsmParser.IWorkingRegisterPair, 0); }
		public Ldci1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ldci1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLdci1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLdci1(this);
		}
	}

	public final Ldci1Context ldci1() throws RecognitionException {
		Ldci1Context _localctx = new Ldci1Context(_ctx, getState());
		enterRule(_localctx, 68, RULE_ldci1);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(327);
			match(Ldci);
			setState(328);
			match(IWorkingRegister);
			setState(329);
			match(Comma);
			setState(330);
			match(IWorkingRegisterPair);
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

	public static class Ldci2Context extends ParserRuleContext {
		public TerminalNode Ldci() { return getToken(Z8AsmParser.Ldci, 0); }
		public TerminalNode IWorkingRegisterPair() { return getToken(Z8AsmParser.IWorkingRegisterPair, 0); }
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public TerminalNode IWorkingRegister() { return getToken(Z8AsmParser.IWorkingRegister, 0); }
		public Ldci2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ldci2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLdci2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLdci2(this);
		}
	}

	public final Ldci2Context ldci2() throws RecognitionException {
		Ldci2Context _localctx = new Ldci2Context(_ctx, getState());
		enterRule(_localctx, 70, RULE_ldci2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(332);
			match(Ldci);
			setState(333);
			match(IWorkingRegisterPair);
			setState(334);
			match(Comma);
			setState(335);
			match(IWorkingRegister);
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
		public Lde1Context lde1() {
			return getRuleContext(Lde1Context.class,0);
		}
		public Lde2Context lde2() {
			return getRuleContext(Lde2Context.class,0);
		}
		public LdeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lde; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLde(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLde(this);
		}
	}

	public final LdeContext lde() throws RecognitionException {
		LdeContext _localctx = new LdeContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_lde);
		try {
			setState(339);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(337);
				lde1();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(338);
				lde2();
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

	public static class Lde1Context extends ParserRuleContext {
		public TerminalNode Lde() { return getToken(Z8AsmParser.Lde, 0); }
		public TerminalNode WorkingRegister() { return getToken(Z8AsmParser.WorkingRegister, 0); }
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public TerminalNode IWorkingRegisterPair() { return getToken(Z8AsmParser.IWorkingRegisterPair, 0); }
		public Lde1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lde1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLde1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLde1(this);
		}
	}

	public final Lde1Context lde1() throws RecognitionException {
		Lde1Context _localctx = new Lde1Context(_ctx, getState());
		enterRule(_localctx, 74, RULE_lde1);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(341);
			match(Lde);
			setState(342);
			match(WorkingRegister);
			setState(343);
			match(Comma);
			setState(344);
			match(IWorkingRegisterPair);
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

	public static class Lde2Context extends ParserRuleContext {
		public TerminalNode Lde() { return getToken(Z8AsmParser.Lde, 0); }
		public TerminalNode IWorkingRegisterPair() { return getToken(Z8AsmParser.IWorkingRegisterPair, 0); }
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public TerminalNode WorkingRegister() { return getToken(Z8AsmParser.WorkingRegister, 0); }
		public Lde2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lde2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLde2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLde2(this);
		}
	}

	public final Lde2Context lde2() throws RecognitionException {
		Lde2Context _localctx = new Lde2Context(_ctx, getState());
		enterRule(_localctx, 76, RULE_lde2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(346);
			match(Lde);
			setState(347);
			match(IWorkingRegisterPair);
			setState(348);
			match(Comma);
			setState(349);
			match(WorkingRegister);
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
		public Ldei1Context ldei1() {
			return getRuleContext(Ldei1Context.class,0);
		}
		public Ldei2Context ldei2() {
			return getRuleContext(Ldei2Context.class,0);
		}
		public LdeiContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ldei; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLdei(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLdei(this);
		}
	}

	public final LdeiContext ldei() throws RecognitionException {
		LdeiContext _localctx = new LdeiContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_ldei);
		try {
			setState(353);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(351);
				ldei1();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(352);
				ldei2();
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

	public static class Ldei1Context extends ParserRuleContext {
		public TerminalNode Ldei() { return getToken(Z8AsmParser.Ldei, 0); }
		public TerminalNode IWorkingRegister() { return getToken(Z8AsmParser.IWorkingRegister, 0); }
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public TerminalNode IWorkingRegisterPair() { return getToken(Z8AsmParser.IWorkingRegisterPair, 0); }
		public Ldei1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ldei1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLdei1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLdei1(this);
		}
	}

	public final Ldei1Context ldei1() throws RecognitionException {
		Ldei1Context _localctx = new Ldei1Context(_ctx, getState());
		enterRule(_localctx, 80, RULE_ldei1);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(355);
			match(Ldei);
			setState(356);
			match(IWorkingRegister);
			setState(357);
			match(Comma);
			setState(358);
			match(IWorkingRegisterPair);
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

	public static class Ldei2Context extends ParserRuleContext {
		public TerminalNode Ldei() { return getToken(Z8AsmParser.Ldei, 0); }
		public TerminalNode IWorkingRegisterPair() { return getToken(Z8AsmParser.IWorkingRegisterPair, 0); }
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public TerminalNode IWorkingRegister() { return getToken(Z8AsmParser.IWorkingRegister, 0); }
		public Ldei2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ldei2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).enterLdei2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Z8AsmListener ) ((Z8AsmListener)listener).exitLdei2(this);
		}
	}

	public final Ldei2Context ldei2() throws RecognitionException {
		Ldei2Context _localctx = new Ldei2Context(_ctx, getState());
		enterRule(_localctx, 82, RULE_ldei2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(360);
			match(Ldei);
			setState(361);
			match(IWorkingRegisterPair);
			setState(362);
			match(Comma);
			setState(363);
			match(IWorkingRegister);
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
	}

	public final DiContext di() throws RecognitionException {
		DiContext _localctx = new DiContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_di);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(365);
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
	}

	public final EiContext ei() throws RecognitionException {
		EiContext _localctx = new EiContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_ei);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(367);
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
	}

	public final RetContext ret() throws RecognitionException {
		RetContext _localctx = new RetContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_ret);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(369);
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
	}

	public final IretContext iret() throws RecognitionException {
		IretContext _localctx = new IretContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_iret);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(371);
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
	}

	public final RcfContext rcf() throws RecognitionException {
		RcfContext _localctx = new RcfContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_rcf);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(373);
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
	}

	public final ScfContext scf() throws RecognitionException {
		ScfContext _localctx = new ScfContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_scf);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(375);
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
	}

	public final CcfContext ccf() throws RecognitionException {
		CcfContext _localctx = new CcfContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_ccf);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(377);
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
	}

	public final NopContext nop() throws RecognitionException {
		NopContext _localctx = new NopContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_nop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(379);
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
	}

	public final AdcContext adc() throws RecognitionException {
		AdcContext _localctx = new AdcContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_adc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(381);
			match(Adc);
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
	}

	public final AddContext add() throws RecognitionException {
		AddContext _localctx = new AddContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_add);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(384);
			match(Add);
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
	}

	public final AndContext and() throws RecognitionException {
		AndContext _localctx = new AndContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_and);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(387);
			match(And);
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
	}

	public final CpContext cp() throws RecognitionException {
		CpContext _localctx = new CpContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_cp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(390);
			match(Cp);
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
	}

	public final OrContext or() throws RecognitionException {
		OrContext _localctx = new OrContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_or);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(393);
			match(Or);
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
	}

	public final SbcContext sbc() throws RecognitionException {
		SbcContext _localctx = new SbcContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_sbc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(396);
			match(Sbc);
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
	}

	public final SubContext sub() throws RecognitionException {
		SubContext _localctx = new SubContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_sub);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(399);
			match(Sub);
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
	}

	public final TcmContext tcm() throws RecognitionException {
		TcmContext _localctx = new TcmContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_tcm);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(402);
			match(Tcm);
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
	}

	public final TmContext tm() throws RecognitionException {
		TmContext _localctx = new TmContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_tm);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(405);
			match(Tm);
			setState(406);
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
	}

	public final XorContext xor() throws RecognitionException {
		XorContext _localctx = new XorContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_xor);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(408);
			match(Xor);
			setState(409);
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
	}

	public final ArithmeticParametersContext arithmeticParameters() throws RecognitionException {
		ArithmeticParametersContext _localctx = new ArithmeticParametersContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_arithmeticParameters);
		try {
			setState(414);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(411);
				arithmeticParameters1();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(412);
				arithmeticParameters2();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(413);
				arithmeticParameters3();
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
		public List<RegisterContext> register() {
			return getRuleContexts(RegisterContext.class);
		}
		public RegisterContext register(int i) {
			return getRuleContext(RegisterContext.class,i);
		}
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public TerminalNode ValueByte() { return getToken(Z8AsmParser.ValueByte, 0); }
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
	}

	public final ArithmeticParameters1Context arithmeticParameters1() throws RecognitionException {
		ArithmeticParameters1Context _localctx = new ArithmeticParameters1Context(_ctx, getState());
		enterRule(_localctx, 122, RULE_arithmeticParameters1);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(416);
			register();
			setState(417);
			match(Comma);
			setState(420);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Byte:
			case WorkingRegister:
			case RegisterConstant:
				{
				setState(418);
				register();
				}
				break;
			case ValueByte:
				{
				setState(419);
				match(ValueByte);
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class ArithmeticParameters2Context extends ParserRuleContext {
		public IregisterContext iregister() {
			return getRuleContext(IregisterContext.class,0);
		}
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public TerminalNode ValueByte() { return getToken(Z8AsmParser.ValueByte, 0); }
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
	}

	public final ArithmeticParameters2Context arithmeticParameters2() throws RecognitionException {
		ArithmeticParameters2Context _localctx = new ArithmeticParameters2Context(_ctx, getState());
		enterRule(_localctx, 124, RULE_arithmeticParameters2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(422);
			iregister();
			setState(423);
			match(Comma);
			setState(424);
			match(ValueByte);
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
		public RegisterContext register() {
			return getRuleContext(RegisterContext.class,0);
		}
		public TerminalNode Comma() { return getToken(Z8AsmParser.Comma, 0); }
		public IregisterContext iregister() {
			return getRuleContext(IregisterContext.class,0);
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
	}

	public final ArithmeticParameters3Context arithmeticParameters3() throws RecognitionException {
		ArithmeticParameters3Context _localctx = new ArithmeticParameters3Context(_ctx, getState());
		enterRule(_localctx, 126, RULE_arithmeticParameters3);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(426);
			register();
			setState(427);
			match(Comma);
			setState(428);
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
	}

	public final RegisterOrIregisterContext registerOrIregister() throws RecognitionException {
		RegisterOrIregisterContext _localctx = new RegisterOrIregisterContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_registerOrIregister);
		try {
			setState(432);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Byte:
			case WorkingRegister:
			case RegisterConstant:
				enterOuterAlt(_localctx, 1);
				{
				setState(430);
				register();
				}
				break;
			case IndirectPrefix:
			case IWorkingRegister:
				enterOuterAlt(_localctx, 2);
				{
				setState(431);
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
		public TerminalNode RegisterConstant() { return getToken(Z8AsmParser.RegisterConstant, 0); }
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
	}

	public final RegisterContext register() throws RecognitionException {
		RegisterContext _localctx = new RegisterContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_register);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(434);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Byte) | (1L << WorkingRegister) | (1L << RegisterConstant))) != 0)) ) {
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
	}

	public final IregisterContext iregister() throws RecognitionException {
		IregisterContext _localctx = new IregisterContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_iregister);
		try {
			setState(439);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IndirectPrefix:
				enterOuterAlt(_localctx, 1);
				{
				setState(436);
				match(IndirectPrefix);
				setState(437);
				register();
				}
				break;
			case IWorkingRegister:
				enterOuterAlt(_localctx, 2);
				{
				setState(438);
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
	}

	public final IregisterPairContext iregisterPair() throws RecognitionException {
		IregisterPairContext _localctx = new IregisterPairContext(_ctx, getState());
		enterRule(_localctx, 134, RULE_iregisterPair);
		try {
			setState(444);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IndirectPrefix:
				enterOuterAlt(_localctx, 1);
				{
				setState(441);
				match(IndirectPrefix);
				setState(442);
				register();
				}
				break;
			case IWorkingRegisterPair:
				enterOuterAlt(_localctx, 2);
				{
				setState(443);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3B\u01c1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\3\2\3\2\3\2\7\2\u008e"+
		"\n\2\f\2\16\2\u0091\13\2\3\3\3\3\3\3\5\3\u0096\n\3\3\4\3\4\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u00c7\n\5\3\5\3\5\3\6\3\6\6\6\u00cd\n"+
		"\6\r\6\16\6\u00ce\3\7\3\7\3\7\3\7\3\7\5\7\u00d6\n\7\3\b\3\b\3\b\3\t\3"+
		"\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3"+
		"\16\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\23\3"+
		"\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\27\3"+
		"\30\3\30\3\30\3\31\3\31\3\31\3\31\5\31\u010f\n\31\3\32\3\32\3\32\3\32"+
		"\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u011e\n\33\3\34\3\34"+
		"\3\34\3\34\3\34\3\34\5\34\u0126\n\34\3\35\3\35\5\35\u012a\n\35\3\36\3"+
		"\36\3\36\3\36\3\36\5\36\u0131\n\36\3\37\3\37\3\37\3\37\3\37\3 \3 \5 \u013a"+
		"\n \3!\3!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3#\3#\5#\u0148\n#\3$\3$\3$\3$\3"+
		"$\3%\3%\3%\3%\3%\3&\3&\5&\u0156\n&\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3("+
		"\3)\3)\5)\u0164\n)\3*\3*\3*\3*\3*\3+\3+\3+\3+\3+\3,\3,\3-\3-\3.\3.\3/"+
		"\3/\3\60\3\60\3\61\3\61\3\62\3\62\3\63\3\63\3\64\3\64\3\64\3\65\3\65\3"+
		"\65\3\66\3\66\3\66\3\67\3\67\3\67\38\38\38\39\39\39\3:\3:\3:\3;\3;\3;"+
		"\3<\3<\3<\3=\3=\3=\3>\3>\3>\5>\u01a1\n>\3?\3?\3?\3?\5?\u01a7\n?\3@\3@"+
		"\3@\3@\3A\3A\3A\3A\3B\3B\5B\u01b3\nB\3C\3C\3D\3D\3D\5D\u01ba\nD\3E\3E"+
		"\3E\5E\u01bf\nE\3E\2\2F\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*"+
		",.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084"+
		"\u0086\u0088\2\5\4\2\7\7>>\3\3@@\5\2\6\6\t\t<<\2\u01c1\2\u008f\3\2\2\2"+
		"\4\u0092\3\2\2\2\6\u0097\3\2\2\2\b\u00c6\3\2\2\2\n\u00ca\3\2\2\2\f\u00d5"+
		"\3\2\2\2\16\u00d7\3\2\2\2\20\u00da\3\2\2\2\22\u00dd\3\2\2\2\24\u00e0\3"+
		"\2\2\2\26\u00e3\3\2\2\2\30\u00e6\3\2\2\2\32\u00e9\3\2\2\2\34\u00ec\3\2"+
		"\2\2\36\u00ef\3\2\2\2 \u00f2\3\2\2\2\"\u00f5\3\2\2\2$\u00f8\3\2\2\2&\u00fb"+
		"\3\2\2\2(\u00fe\3\2\2\2*\u0101\3\2\2\2,\u0104\3\2\2\2.\u0107\3\2\2\2\60"+
		"\u010e\3\2\2\2\62\u0110\3\2\2\2\64\u011d\3\2\2\2\66\u0125\3\2\2\28\u0129"+
		"\3\2\2\2:\u012b\3\2\2\2<\u0132\3\2\2\2>\u0139\3\2\2\2@\u013b\3\2\2\2B"+
		"\u0140\3\2\2\2D\u0147\3\2\2\2F\u0149\3\2\2\2H\u014e\3\2\2\2J\u0155\3\2"+
		"\2\2L\u0157\3\2\2\2N\u015c\3\2\2\2P\u0163\3\2\2\2R\u0165\3\2\2\2T\u016a"+
		"\3\2\2\2V\u016f\3\2\2\2X\u0171\3\2\2\2Z\u0173\3\2\2\2\\\u0175\3\2\2\2"+
		"^\u0177\3\2\2\2`\u0179\3\2\2\2b\u017b\3\2\2\2d\u017d\3\2\2\2f\u017f\3"+
		"\2\2\2h\u0182\3\2\2\2j\u0185\3\2\2\2l\u0188\3\2\2\2n\u018b\3\2\2\2p\u018e"+
		"\3\2\2\2r\u0191\3\2\2\2t\u0194\3\2\2\2v\u0197\3\2\2\2x\u019a\3\2\2\2z"+
		"\u01a0\3\2\2\2|\u01a2\3\2\2\2~\u01a8\3\2\2\2\u0080\u01ac\3\2\2\2\u0082"+
		"\u01b2\3\2\2\2\u0084\u01b4\3\2\2\2\u0086\u01b9\3\2\2\2\u0088\u01be\3\2"+
		"\2\2\u008a\u008e\5\4\3\2\u008b\u008e\5\b\5\2\u008c\u008e\7@\2\2\u008d"+
		"\u008a\3\2\2\2\u008d\u008b\3\2\2\2\u008d\u008c\3\2\2\2\u008e\u0091\3\2"+
		"\2\2\u008f\u008d\3\2\2\2\u008f\u0090\3\2\2\2\u0090\3\3\2\2\2\u0091\u008f"+
		"\3\2\2\2\u0092\u0093\7>\2\2\u0093\u0095\7\r\2\2\u0094\u0096\7@\2\2\u0095"+
		"\u0094\3\2\2\2\u0095\u0096\3\2\2\2\u0096\5\3\2\2\2\u0097\u0098\t\2\2\2"+
		"\u0098\7\3\2\2\2\u0099\u00c7\5\n\6\2\u009a\u00c7\5\16\b\2\u009b\u00c7"+
		"\5f\64\2\u009c\u00c7\5h\65\2\u009d\u00c7\5j\66\2\u009e\u00c7\5\60\31\2"+
		"\u009f\u00c7\5b\62\2\u00a0\u00c7\5$\23\2\u00a1\u00c7\5\32\16\2\u00a2\u00c7"+
		"\5l\67\2\u00a3\u00c7\5\26\f\2\u00a4\u00c7\5\20\t\2\u00a5\u00c7\5\36\20"+
		"\2\u00a6\u00c7\5V,\2\u00a7\u00c7\5\62\32\2\u00a8\u00c7\5X-\2\u00a9\u00c7"+
		"\5\24\13\2\u00aa\u00c7\5\"\22\2\u00ab\u00c7\5\\/\2\u00ac\u00c7\5\64\33"+
		"\2\u00ad\u00c7\5\66\34\2\u00ae\u00c7\58\35\2\u00af\u00c7\5> \2\u00b0\u00c7"+
		"\5D#\2\u00b1\u00c7\5J&\2\u00b2\u00c7\5P)\2\u00b3\u00c7\5d\63\2\u00b4\u00c7"+
		"\5n8\2\u00b5\u00c7\5\30\r\2\u00b6\u00c7\5\34\17\2\u00b7\u00c7\5^\60\2"+
		"\u00b8\u00c7\5Z.\2\u00b9\u00c7\5 \21\2\u00ba\u00c7\5\22\n\2\u00bb\u00c7"+
		"\5*\26\2\u00bc\u00c7\5&\24\2\u00bd\u00c7\5p9\2\u00be\u00c7\5`\61\2\u00bf"+
		"\u00c7\5(\25\2\u00c0\u00c7\5.\30\2\u00c1\u00c7\5r:\2\u00c2\u00c7\5,\27"+
		"\2\u00c3\u00c7\5t;\2\u00c4\u00c7\5v<\2\u00c5\u00c7\5x=\2\u00c6\u0099\3"+
		"\2\2\2\u00c6\u009a\3\2\2\2\u00c6\u009b\3\2\2\2\u00c6\u009c\3\2\2\2\u00c6"+
		"\u009d\3\2\2\2\u00c6\u009e\3\2\2\2\u00c6\u009f\3\2\2\2\u00c6\u00a0\3\2"+
		"\2\2\u00c6\u00a1\3\2\2\2\u00c6\u00a2\3\2\2\2\u00c6\u00a3\3\2\2\2\u00c6"+
		"\u00a4\3\2\2\2\u00c6\u00a5\3\2\2\2\u00c6\u00a6\3\2\2\2\u00c6\u00a7\3\2"+
		"\2\2\u00c6\u00a8\3\2\2\2\u00c6\u00a9\3\2\2\2\u00c6\u00aa\3\2\2\2\u00c6"+
		"\u00ab\3\2\2\2\u00c6\u00ac\3\2\2\2\u00c6\u00ad\3\2\2\2\u00c6\u00ae\3\2"+
		"\2\2\u00c6\u00af\3\2\2\2\u00c6\u00b0\3\2\2\2\u00c6\u00b1\3\2\2\2\u00c6"+
		"\u00b2\3\2\2\2\u00c6\u00b3\3\2\2\2\u00c6\u00b4\3\2\2\2\u00c6\u00b5\3\2"+
		"\2\2\u00c6\u00b6\3\2\2\2\u00c6\u00b7\3\2\2\2\u00c6\u00b8\3\2\2\2\u00c6"+
		"\u00b9\3\2\2\2\u00c6\u00ba\3\2\2\2\u00c6\u00bb\3\2\2\2\u00c6\u00bc\3\2"+
		"\2\2\u00c6\u00bd\3\2\2\2\u00c6\u00be\3\2\2\2\u00c6\u00bf\3\2\2\2\u00c6"+
		"\u00c0\3\2\2\2\u00c6\u00c1\3\2\2\2\u00c6\u00c2\3\2\2\2\u00c6\u00c3\3\2"+
		"\2\2\u00c6\u00c4\3\2\2\2\u00c6\u00c5\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8"+
		"\u00c9\t\3\2\2\u00c9\t\3\2\2\2\u00ca\u00cc\7\17\2\2\u00cb\u00cd\5\f\7"+
		"\2\u00cc\u00cb\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce\u00cc\3\2\2\2\u00ce\u00cf"+
		"\3\2\2\2\u00cf\13\3\2\2\2\u00d0\u00d6\7\6\2\2\u00d1\u00d6\5\6\4\2\u00d2"+
		"\u00d6\7\3\2\2\u00d3\u00d6\7\4\2\2\u00d4\u00d6\7\5\2\2\u00d5\u00d0\3\2"+
		"\2\2\u00d5\u00d1\3\2\2\2\u00d5\u00d2\3\2\2\2\u00d5\u00d3\3\2\2\2\u00d5"+
		"\u00d4\3\2\2\2\u00d6\r\3\2\2\2\u00d7\u00d8\7\20\2\2\u00d8\u00d9\7\7\2"+
		"\2\u00d9\17\3\2\2\2\u00da\u00db\7\32\2\2\u00db\u00dc\5\u0082B\2\u00dc"+
		"\21\3\2\2\2\u00dd\u00de\7\60\2\2\u00de\u00df\5\u0082B\2\u00df\23\3\2\2"+
		"\2\u00e0\u00e1\7\37\2\2\u00e1\u00e2\5\u0082B\2\u00e2\25\3\2\2\2\u00e3"+
		"\u00e4\7\31\2\2\u00e4\u00e5\5\u0082B\2\u00e5\27\3\2\2\2\u00e6\u00e7\7"+
		"+\2\2\u00e7\u00e8\5\u0082B\2\u00e8\31\3\2\2\2\u00e9\u00ea\7\27\2\2\u00ea"+
		"\u00eb\5\u0082B\2\u00eb\33\3\2\2\2\u00ec\u00ed\7,\2\2\u00ed\u00ee\5\u0082"+
		"B\2\u00ee\35\3\2\2\2\u00ef\u00f0\7\33\2\2\u00f0\u00f1\5\u0082B\2\u00f1"+
		"\37\3\2\2\2\u00f2\u00f3\7/\2\2\u00f3\u00f4\5\u0082B\2\u00f4!\3\2\2\2\u00f5"+
		"\u00f6\7 \2\2\u00f6\u00f7\5\u0082B\2\u00f7#\3\2\2\2\u00f8\u00f9\7\26\2"+
		"\2\u00f9\u00fa\5\u0082B\2\u00fa%\3\2\2\2\u00fb\u00fc\7\62\2\2\u00fc\u00fd"+
		"\5\u0082B\2\u00fd\'\3\2\2\2\u00fe\u00ff\7\65\2\2\u00ff\u0100\5\u0082B"+
		"\2\u0100)\3\2\2\2\u0101\u0102\7\61\2\2\u0102\u0103\5\u0082B\2\u0103+\3"+
		"\2\2\2\u0104\u0105\78\2\2\u0105\u0106\5\u0082B\2\u0106-\3\2\2\2\u0107"+
		"\u0108\7\66\2\2\u0108\u0109\7\b\2\2\u0109/\3\2\2\2\u010a\u010b\7\24\2"+
		"\2\u010b\u010f\5\6\4\2\u010c\u010d\7\24\2\2\u010d\u010f\5\u0088E\2\u010e"+
		"\u010a\3\2\2\2\u010e\u010c\3\2\2\2\u010f\61\3\2\2\2\u0110\u0111\7\35\2"+
		"\2\u0111\u0112\7\t\2\2\u0112\u0113\7\16\2\2\u0113\u0114\5\6\4\2\u0114"+
		"\63\3\2\2\2\u0115\u0116\7\"\2\2\u0116\u011e\5\u0088E\2\u0117\u0118\7\""+
		"\2\2\u0118\u011e\5\6\4\2\u0119\u011a\7\"\2\2\u011a\u011b\7=\2\2\u011b"+
		"\u011c\7\16\2\2\u011c\u011e\5\6\4\2\u011d\u0115\3\2\2\2\u011d\u0117\3"+
		"\2\2\2\u011d\u0119\3\2\2\2\u011e\65\3\2\2\2\u011f\u0120\7#\2\2\u0120\u0126"+
		"\5\6\4\2\u0121\u0122\7#\2\2\u0122\u0123\7=\2\2\u0123\u0124\7\16\2\2\u0124"+
		"\u0126\5\6\4\2\u0125\u011f\3\2\2\2\u0125\u0121\3\2\2\2\u0126\67\3\2\2"+
		"\2\u0127\u012a\5:\36\2\u0128\u012a\5<\37\2\u0129\u0127\3\2\2\2\u0129\u0128"+
		"\3\2\2\2\u012a9\3\2\2\2\u012b\u012c\7$\2\2\u012c\u012d\5\u0084C\2\u012d"+
		"\u0130\7\16\2\2\u012e\u0131\5\u0082B\2\u012f\u0131\7\b\2\2\u0130\u012e"+
		"\3\2\2\2\u0130\u012f\3\2\2\2\u0131;\3\2\2\2\u0132\u0133\7$\2\2\u0133\u0134"+
		"\5\u0086D\2\u0134\u0135\7\16\2\2\u0135\u0136\5\u0084C\2\u0136=\3\2\2\2"+
		"\u0137\u013a\5@!\2\u0138\u013a\5B\"\2\u0139\u0137\3\2\2\2\u0139\u0138"+
		"\3\2\2\2\u013a?\3\2\2\2\u013b\u013c\7%\2\2\u013c\u013d\7\t\2\2\u013d\u013e"+
		"\7\16\2\2\u013e\u013f\7\f\2\2\u013fA\3\2\2\2\u0140\u0141\7%\2\2\u0141"+
		"\u0142\7\f\2\2\u0142\u0143\7\16\2\2\u0143\u0144\7\t\2\2\u0144C\3\2\2\2"+
		"\u0145\u0148\5F$\2\u0146\u0148\5H%\2\u0147\u0145\3\2\2\2\u0147\u0146\3"+
		"\2\2\2\u0148E\3\2\2\2\u0149\u014a\7&\2\2\u014a\u014b\7\13\2\2\u014b\u014c"+
		"\7\16\2\2\u014c\u014d\7\f\2\2\u014dG\3\2\2\2\u014e\u014f\7&\2\2\u014f"+
		"\u0150\7\f\2\2\u0150\u0151\7\16\2\2\u0151\u0152\7\13\2\2\u0152I\3\2\2"+
		"\2\u0153\u0156\5L\'\2\u0154\u0156\5N(\2\u0155\u0153\3\2\2\2\u0155\u0154"+
		"\3\2\2\2\u0156K\3\2\2\2\u0157\u0158\7\'\2\2\u0158\u0159\7\t\2\2\u0159"+
		"\u015a\7\16\2\2\u015a\u015b\7\f\2\2\u015bM\3\2\2\2\u015c\u015d\7\'\2\2"+
		"\u015d\u015e\7\f\2\2\u015e\u015f\7\16\2\2\u015f\u0160\7\t\2\2\u0160O\3"+
		"\2\2\2\u0161\u0164\5R*\2\u0162\u0164\5T+\2\u0163\u0161\3\2\2\2\u0163\u0162"+
		"\3\2\2\2\u0164Q\3\2\2\2\u0165\u0166\7(\2\2\u0166\u0167\7\13\2\2\u0167"+
		"\u0168\7\16\2\2\u0168\u0169\7\f\2\2\u0169S\3\2\2\2\u016a\u016b\7(\2\2"+
		"\u016b\u016c\7\f\2\2\u016c\u016d\7\16\2\2\u016d\u016e\7\13\2\2\u016eU"+
		"\3\2\2\2\u016f\u0170\7\34\2\2\u0170W\3\2\2\2\u0171\u0172\7\36\2\2\u0172"+
		"Y\3\2\2\2\u0173\u0174\7.\2\2\u0174[\3\2\2\2\u0175\u0176\7!\2\2\u0176]"+
		"\3\2\2\2\u0177\u0178\7-\2\2\u0178_\3\2\2\2\u0179\u017a\7\64\2\2\u017a"+
		"a\3\2\2\2\u017b\u017c\7\25\2\2\u017cc\3\2\2\2\u017d\u017e\7)\2\2\u017e"+
		"e\3\2\2\2\u017f\u0180\7\21\2\2\u0180\u0181\5z>\2\u0181g\3\2\2\2\u0182"+
		"\u0183\7\22\2\2\u0183\u0184\5z>\2\u0184i\3\2\2\2\u0185\u0186\7\23\2\2"+
		"\u0186\u0187\5z>\2\u0187k\3\2\2\2\u0188\u0189\7\30\2\2\u0189\u018a\5z"+
		">\2\u018am\3\2\2\2\u018b\u018c\7*\2\2\u018c\u018d\5z>\2\u018do\3\2\2\2"+
		"\u018e\u018f\7\63\2\2\u018f\u0190\5z>\2\u0190q\3\2\2\2\u0191\u0192\7\67"+
		"\2\2\u0192\u0193\5z>\2\u0193s\3\2\2\2\u0194\u0195\79\2\2\u0195\u0196\5"+
		"z>\2\u0196u\3\2\2\2\u0197\u0198\7:\2\2\u0198\u0199\5z>\2\u0199w\3\2\2"+
		"\2\u019a\u019b\7;\2\2\u019b\u019c\5z>\2\u019cy\3\2\2\2\u019d\u01a1\5|"+
		"?\2\u019e\u01a1\5~@\2\u019f\u01a1\5\u0080A\2\u01a0\u019d\3\2\2\2\u01a0"+
		"\u019e\3\2\2\2\u01a0\u019f\3\2\2\2\u01a1{\3\2\2\2\u01a2\u01a3\5\u0084"+
		"C\2\u01a3\u01a6\7\16\2\2\u01a4\u01a7\5\u0084C\2\u01a5\u01a7\7\b\2\2\u01a6"+
		"\u01a4\3\2\2\2\u01a6\u01a5\3\2\2\2\u01a7}\3\2\2\2\u01a8\u01a9\5\u0086"+
		"D\2\u01a9\u01aa\7\16\2\2\u01aa\u01ab\7\b\2\2\u01ab\177\3\2\2\2\u01ac\u01ad"+
		"\5\u0084C\2\u01ad\u01ae\7\16\2\2\u01ae\u01af\5\u0086D\2\u01af\u0081\3"+
		"\2\2\2\u01b0\u01b3\5\u0084C\2\u01b1\u01b3\5\u0086D\2\u01b2\u01b0\3\2\2"+
		"\2\u01b2\u01b1\3\2\2\2\u01b3\u0083\3\2\2\2\u01b4\u01b5\t\4\2\2\u01b5\u0085"+
		"\3\2\2\2\u01b6\u01b7\7\n\2\2\u01b7\u01ba\5\u0084C\2\u01b8\u01ba\7\13\2"+
		"\2\u01b9\u01b6\3\2\2\2\u01b9\u01b8\3\2\2\2\u01ba\u0087\3\2\2\2\u01bb\u01bc"+
		"\7\n\2\2\u01bc\u01bf\5\u0084C\2\u01bd\u01bf\7\f\2\2\u01be\u01bb\3\2\2"+
		"\2\u01be\u01bd\3\2\2\2\u01bf\u0089\3\2\2\2\26\u008d\u008f\u0095\u00c6"+
		"\u00ce\u00d5\u010e\u011d\u0125\u0129\u0130\u0139\u0147\u0155\u0163\u01a0"+
		"\u01a6\u01b2\u01b9\u01be";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}