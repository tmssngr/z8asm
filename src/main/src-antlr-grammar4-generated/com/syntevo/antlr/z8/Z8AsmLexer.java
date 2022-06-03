// Generated from grammar/Z8Asm.g4 by ANTLR 4.7.2
package com.syntevo.antlr.z8;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Z8AsmLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "LString", "String", "Char", "EscapedChar", "Digit", 
			"BinDigit", "HexDigit", "HexPrefix", "ByteDecimal", "BinaryNibble", "BinaryLiteral", 
			"Byte", "Word", "NibbleDec", "WorkingRegister", "IndirectPrefix", "IWorkingRegister", 
			"IWorkingRegisterPair", "Colon", "Comma", "Const", "Data", "Org", "Adc", 
			"Add", "And", "Call", "Ccf", "Clr", "Com", "Cp", "Da", "Dec", "Decw", 
			"Di", "Djnz", "Ei", "Inc", "Incw", "Iret", "Jp", "Jr", "Ld", "Ldc", "Ldci", 
			"Lde", "Ldei", "Nop", "Or", "Pop", "Push", "Rcf", "Ret", "Rl", "Rlc", 
			"Rr", "Rrc", "Sbc", "Scf", "Sra", "Srp", "Sub", "Swap", "Tcm", "Tm", 
			"Xor", "JpCondition", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", 
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", 
			"Y", "Z", "Identifier", "Whitespace", "NL", "LineComment", "BlockComment"
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


	public Z8AsmLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Z8Asm.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2C\u0294\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\4U\tU\4V\tV\4W\tW\4X\tX\4Y\tY\4Z\tZ\4[\t[\4\\\t\\\4]\t]\4^\t^\4_\t_\4"+
		"`\t`\4a\ta\4b\tb\4c\tc\4d\td\4e\te\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\6\4"+
		"\u00d4\n\4\r\4\16\4\u00d5\3\4\3\4\3\5\3\5\3\5\6\5\u00dd\n\5\r\5\16\5\u00de"+
		"\3\5\3\5\3\6\3\6\3\6\5\6\u00e6\n\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\t\3\t"+
		"\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\5\f\u0103\n\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3"+
		"\17\3\17\3\17\5\17\u0113\n\17\3\17\3\17\5\17\u0117\n\17\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\21\3\21\3\21\5\21\u0122\n\21\3\22\3\22\3\22\3\23\3\23"+
		"\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\27\3\27\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32"+
		"\3\32\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\36"+
		"\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3!\3!\3!\3!\3\"\3"+
		"\"\3\"\3#\3#\3#\3$\3$\3$\3$\3%\3%\3%\3%\3%\3&\3&\3&\3\'\3\'\3\'\3\'\3"+
		"\'\3(\3(\3(\3)\3)\3)\3)\3*\3*\3*\3*\3*\3+\3+\3+\3+\3+\3,\3,\3,\3-\3-\3"+
		"-\3.\3.\3.\3/\3/\3/\3/\3\60\3\60\3\60\3\60\3\60\3\61\3\61\3\61\3\61\3"+
		"\62\3\62\3\62\3\62\3\62\3\63\3\63\3\63\3\63\3\64\3\64\3\64\3\65\3\65\3"+
		"\65\3\65\3\66\3\66\3\66\3\66\3\66\3\67\3\67\3\67\3\67\38\38\38\38\39\3"+
		"9\39\3:\3:\3:\3:\3;\3;\3;\3<\3<\3<\3<\3=\3=\3=\3=\3>\3>\3>\3>\3?\3?\3"+
		"?\3?\3@\3@\3@\3@\3A\3A\3A\3A\3B\3B\3B\3B\3B\3C\3C\3C\3C\3D\3D\3D\3E\3"+
		"E\3E\3E\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3"+
		"F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3"+
		"F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\3F\5F\u0228\nF\3G\3G\3H\3H\3I\3I\3"+
		"J\3J\3K\3K\3L\3L\3M\3M\3N\3N\3O\3O\3P\3P\3Q\3Q\3R\3R\3S\3S\3T\3T\3U\3"+
		"U\3V\3V\3W\3W\3X\3X\3Y\3Y\3Z\3Z\3[\3[\3\\\3\\\3]\3]\3^\3^\3_\3_\3`\3`"+
		"\3a\3a\7a\u0260\na\fa\16a\u0263\13a\3b\6b\u0266\nb\rb\16b\u0267\3b\3b"+
		"\3c\3c\5c\u026e\nc\3c\5c\u0271\nc\3d\3d\3d\3d\7d\u0277\nd\fd\16d\u027a"+
		"\13d\3d\3d\7d\u027e\nd\fd\16d\u0281\13d\5d\u0283\nd\3d\3d\3e\3e\3e\3e"+
		"\7e\u028b\ne\fe\16e\u028e\13e\3e\3e\3e\3e\3e\3\u028c\2f\3\3\5\4\7\5\t"+
		"\6\13\7\r\2\17\2\21\2\23\2\25\2\27\2\31\2\33\2\35\b\37\t!\2#\n%\13\'\f"+
		")\r+\16-\17/\20\61\21\63\22\65\23\67\249\25;\26=\27?\30A\31C\32E\33G\34"+
		"I\35K\36M\37O Q!S\"U#W$Y%[&]\'_(a)c*e+g,i-k.m/o\60q\61s\62u\63w\64y\65"+
		"{\66}\67\1778\u00819\u0083:\u0085;\u0087<\u0089=\u008b>\u008d\2\u008f"+
		"\2\u0091\2\u0093\2\u0095\2\u0097\2\u0099\2\u009b\2\u009d\2\u009f\2\u00a1"+
		"\2\u00a3\2\u00a5\2\u00a7\2\u00a9\2\u00ab\2\u00ad\2\u00af\2\u00b1\2\u00b3"+
		"\2\u00b5\2\u00b7\2\u00b9\2\u00bb\2\u00bd\2\u00bf\2\u00c1?\u00c3@\u00c5"+
		"A\u00c7B\u00c9C\3\2)\4\2$$^^\3\2^^\7\2$$\62\62^^pptt\3\2\62;\3\2\62\63"+
		"\5\2\62;CHch\3\2\63;\3\2\62\66\3\2\62\67\4\2CCcc\4\2DDdd\4\2EEee\4\2F"+
		"Fff\4\2GGgg\4\2HHhh\4\2IIii\4\2JJjj\4\2KKkk\4\2LLll\4\2MMmm\4\2NNnn\4"+
		"\2OOoo\4\2PPpp\4\2QQqq\4\2RRrr\4\2SSss\4\2TTtt\4\2UUuu\4\2VVvv\4\2WWw"+
		"w\4\2XXxx\4\2YYyy\4\2ZZzz\4\2[[{{\4\2\\\\||\4\2C\\c|\6\2\62;C\\aac|\4"+
		"\2\13\13\"\"\4\2\f\f\17\17\2\u0297\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2"+
		"\2\t\3\2\2\2\2\13\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2#\3\2\2\2\2%\3\2"+
		"\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2"+
		"\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3"+
		"\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2"+
		"\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2"+
		"W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3"+
		"\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2"+
		"\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2"+
		"}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2\2\2\u0083\3\2\2\2\2\u0085\3\2\2"+
		"\2\2\u0087\3\2\2\2\2\u0089\3\2\2\2\2\u008b\3\2\2\2\2\u00c1\3\2\2\2\2\u00c3"+
		"\3\2\2\2\2\u00c5\3\2\2\2\2\u00c7\3\2\2\2\2\u00c9\3\2\2\2\3\u00cb\3\2\2"+
		"\2\5\u00cd\3\2\2\2\7\u00cf\3\2\2\2\t\u00d9\3\2\2\2\13\u00e2\3\2\2\2\r"+
		"\u00e9\3\2\2\2\17\u00ec\3\2\2\2\21\u00ee\3\2\2\2\23\u00f0\3\2\2\2\25\u00f2"+
		"\3\2\2\2\27\u0102\3\2\2\2\31\u0104\3\2\2\2\33\u0109\3\2\2\2\35\u0116\3"+
		"\2\2\2\37\u0118\3\2\2\2!\u0121\3\2\2\2#\u0123\3\2\2\2%\u0126\3\2\2\2\'"+
		"\u0128\3\2\2\2)\u012c\3\2\2\2+\u0131\3\2\2\2-\u0133\3\2\2\2/\u0135\3\2"+
		"\2\2\61\u013c\3\2\2\2\63\u0140\3\2\2\2\65\u0145\3\2\2\2\67\u0149\3\2\2"+
		"\29\u014d\3\2\2\2;\u0151\3\2\2\2=\u0156\3\2\2\2?\u015a\3\2\2\2A\u015e"+
		"\3\2\2\2C\u0162\3\2\2\2E\u0165\3\2\2\2G\u0168\3\2\2\2I\u016c\3\2\2\2K"+
		"\u0171\3\2\2\2M\u0174\3\2\2\2O\u0179\3\2\2\2Q\u017c\3\2\2\2S\u0180\3\2"+
		"\2\2U\u0185\3\2\2\2W\u018a\3\2\2\2Y\u018d\3\2\2\2[\u0190\3\2\2\2]\u0193"+
		"\3\2\2\2_\u0197\3\2\2\2a\u019c\3\2\2\2c\u01a0\3\2\2\2e\u01a5\3\2\2\2g"+
		"\u01a9\3\2\2\2i\u01ac\3\2\2\2k\u01b0\3\2\2\2m\u01b5\3\2\2\2o\u01b9\3\2"+
		"\2\2q\u01bd\3\2\2\2s\u01c0\3\2\2\2u\u01c4\3\2\2\2w\u01c7\3\2\2\2y\u01cb"+
		"\3\2\2\2{\u01cf\3\2\2\2}\u01d3\3\2\2\2\177\u01d7\3\2\2\2\u0081\u01db\3"+
		"\2\2\2\u0083\u01df\3\2\2\2\u0085\u01e4\3\2\2\2\u0087\u01e8\3\2\2\2\u0089"+
		"\u01eb\3\2\2\2\u008b\u0227\3\2\2\2\u008d\u0229\3\2\2\2\u008f\u022b\3\2"+
		"\2\2\u0091\u022d\3\2\2\2\u0093\u022f\3\2\2\2\u0095\u0231\3\2\2\2\u0097"+
		"\u0233\3\2\2\2\u0099\u0235\3\2\2\2\u009b\u0237\3\2\2\2\u009d\u0239\3\2"+
		"\2\2\u009f\u023b\3\2\2\2\u00a1\u023d\3\2\2\2\u00a3\u023f\3\2\2\2\u00a5"+
		"\u0241\3\2\2\2\u00a7\u0243\3\2\2\2\u00a9\u0245\3\2\2\2\u00ab\u0247\3\2"+
		"\2\2\u00ad\u0249\3\2\2\2\u00af\u024b\3\2\2\2\u00b1\u024d\3\2\2\2\u00b3"+
		"\u024f\3\2\2\2\u00b5\u0251\3\2\2\2\u00b7\u0253\3\2\2\2\u00b9\u0255\3\2"+
		"\2\2\u00bb\u0257\3\2\2\2\u00bd\u0259\3\2\2\2\u00bf\u025b\3\2\2\2\u00c1"+
		"\u025d\3\2\2\2\u00c3\u0265\3\2\2\2\u00c5\u0270\3\2\2\2\u00c7\u0282\3\2"+
		"\2\2\u00c9\u0286\3\2\2\2\u00cb\u00cc\7?\2\2\u00cc\4\3\2\2\2\u00cd\u00ce"+
		"\7%\2\2\u00ce\6\3\2\2\2\u00cf\u00d0\5\u00a3R\2\u00d0\u00d3\7$\2\2\u00d1"+
		"\u00d4\5\r\7\2\u00d2\u00d4\n\2\2\2\u00d3\u00d1\3\2\2\2\u00d3\u00d2\3\2"+
		"\2\2\u00d4\u00d5\3\2\2\2\u00d5\u00d3\3\2\2\2\u00d5\u00d6\3\2\2\2\u00d6"+
		"\u00d7\3\2\2\2\u00d7\u00d8\7$\2\2\u00d8\b\3\2\2\2\u00d9\u00dc\7$\2\2\u00da"+
		"\u00dd\5\r\7\2\u00db\u00dd\n\2\2\2\u00dc\u00da\3\2\2\2\u00dc\u00db\3\2"+
		"\2\2\u00dd\u00de\3\2\2\2\u00de\u00dc\3\2\2\2\u00de\u00df\3\2\2\2\u00df"+
		"\u00e0\3\2\2\2\u00e0\u00e1\7$\2\2\u00e1\n\3\2\2\2\u00e2\u00e5\7)\2\2\u00e3"+
		"\u00e6\5\r\7\2\u00e4\u00e6\n\3\2\2\u00e5\u00e3\3\2\2\2\u00e5\u00e4\3\2"+
		"\2\2\u00e6\u00e7\3\2\2\2\u00e7\u00e8\7)\2\2\u00e8\f\3\2\2\2\u00e9\u00ea"+
		"\7^\2\2\u00ea\u00eb\t\4\2\2\u00eb\16\3\2\2\2\u00ec\u00ed\t\5\2\2\u00ed"+
		"\20\3\2\2\2\u00ee\u00ef\t\6\2\2\u00ef\22\3\2\2\2\u00f0\u00f1\t\7\2\2\u00f1"+
		"\24\3\2\2\2\u00f2\u00f3\7\'\2\2\u00f3\26\3\2\2\2\u00f4\u0103\5\17\b\2"+
		"\u00f5\u00f6\t\b\2\2\u00f6\u0103\5\17\b\2\u00f7\u00f8\7\63\2\2\u00f8\u00f9"+
		"\5\17\b\2\u00f9\u00fa\5\17\b\2\u00fa\u0103\3\2\2\2\u00fb\u00fc\7\64\2"+
		"\2\u00fc\u00fd\t\t\2\2\u00fd\u0103\5\17\b\2\u00fe\u00ff\7\64\2\2\u00ff"+
		"\u0100\7\67\2\2\u0100\u0101\3\2\2\2\u0101\u0103\t\n\2\2\u0102\u00f4\3"+
		"\2\2\2\u0102\u00f5\3\2\2\2\u0102\u00f7\3\2\2\2\u0102\u00fb\3\2\2\2\u0102"+
		"\u00fe\3\2\2\2\u0103\30\3\2\2\2\u0104\u0105\5\21\t\2\u0105\u0106\5\21"+
		"\t\2\u0106\u0107\5\21\t\2\u0107\u0108\5\21\t\2\u0108\32\3\2\2\2\u0109"+
		"\u010a\7\62\2\2\u010a\u010b\5\u008fH\2\u010b\u010c\5\31\r\2\u010c\u010d"+
		"\7a\2\2\u010d\u010e\5\31\r\2\u010e\34\3\2\2\2\u010f\u0110\5\25\13\2\u0110"+
		"\u0112\5\23\n\2\u0111\u0113\5\23\n\2\u0112\u0111\3\2\2\2\u0112\u0113\3"+
		"\2\2\2\u0113\u0117\3\2\2\2\u0114\u0117\5\27\f\2\u0115\u0117\5\33\16\2"+
		"\u0116\u010f\3\2\2\2\u0116\u0114\3\2\2\2\u0116\u0115\3\2\2\2\u0117\36"+
		"\3\2\2\2\u0118\u0119\5\25\13\2\u0119\u011a\5\23\n\2\u011a\u011b\5\23\n"+
		"\2\u011b\u011c\5\23\n\2\u011c\u011d\5\23\n\2\u011d \3\2\2\2\u011e\u0122"+
		"\t\5\2\2\u011f\u0120\7\63\2\2\u0120\u0122\t\n\2\2\u0121\u011e\3\2\2\2"+
		"\u0121\u011f\3\2\2\2\u0122\"\3\2\2\2\u0123\u0124\5\u00afX\2\u0124\u0125"+
		"\5!\21\2\u0125$\3\2\2\2\u0126\u0127\7B\2\2\u0127&\3\2\2\2\u0128\u0129"+
		"\5%\23\2\u0129\u012a\5\u00afX\2\u012a\u012b\5!\21\2\u012b(\3\2\2\2\u012c"+
		"\u012d\5%\23\2\u012d\u012e\5\u00afX\2\u012e\u012f\5\u00afX\2\u012f\u0130"+
		"\5!\21\2\u0130*\3\2\2\2\u0131\u0132\7<\2\2\u0132,\3\2\2\2\u0133\u0134"+
		"\7.\2\2\u0134.\3\2\2\2\u0135\u0136\7\60\2\2\u0136\u0137\5\u0091I\2\u0137"+
		"\u0138\5\u00a9U\2\u0138\u0139\5\u00a7T\2\u0139\u013a\5\u00b1Y\2\u013a"+
		"\u013b\5\u00b3Z\2\u013b\60\3\2\2\2\u013c\u013d\7\60\2\2\u013d\u013e\5"+
		"\u0093J\2\u013e\u013f\5\u008fH\2\u013f\62\3\2\2\2\u0140\u0141\7\60\2\2"+
		"\u0141\u0142\5\u00a9U\2\u0142\u0143\5\u00afX\2\u0143\u0144\5\u0099M\2"+
		"\u0144\64\3\2\2\2\u0145\u0146\5\u008dG\2\u0146\u0147\5\u0093J\2\u0147"+
		"\u0148\5\u0091I\2\u0148\66\3\2\2\2\u0149\u014a\5\u008dG\2\u014a\u014b"+
		"\5\u0093J\2\u014b\u014c\5\u0093J\2\u014c8\3\2\2\2\u014d\u014e\5\u008d"+
		"G\2\u014e\u014f\5\u00a7T\2\u014f\u0150\5\u0093J\2\u0150:\3\2\2\2\u0151"+
		"\u0152\5\u0091I\2\u0152\u0153\5\u008dG\2\u0153\u0154\5\u00a3R\2\u0154"+
		"\u0155\5\u00a3R\2\u0155<\3\2\2\2\u0156\u0157\5\u0091I\2\u0157\u0158\5"+
		"\u0091I\2\u0158\u0159\5\u0097L\2\u0159>\3\2\2\2\u015a\u015b\5\u0091I\2"+
		"\u015b\u015c\5\u00a3R\2\u015c\u015d\5\u00afX\2\u015d@\3\2\2\2\u015e\u015f"+
		"\5\u0091I\2\u015f\u0160\5\u00a9U\2\u0160\u0161\5\u00a5S\2\u0161B\3\2\2"+
		"\2\u0162\u0163\5\u0091I\2\u0163\u0164\5\u00abV\2\u0164D\3\2\2\2\u0165"+
		"\u0166\5\u0093J\2\u0166\u0167\5\u008dG\2\u0167F\3\2\2\2\u0168\u0169\5"+
		"\u0093J\2\u0169\u016a\5\u0095K\2\u016a\u016b\5\u0091I\2\u016bH\3\2\2\2"+
		"\u016c\u016d\5\u0093J\2\u016d\u016e\5\u0095K\2\u016e\u016f\5\u0091I\2"+
		"\u016f\u0170\5\u00b9]\2\u0170J\3\2\2\2\u0171\u0172\5\u0093J\2\u0172\u0173"+
		"\5\u009dO\2\u0173L\3\2\2\2\u0174\u0175\5\u0093J\2\u0175\u0176\5\u009f"+
		"P\2\u0176\u0177\5\u00a7T\2\u0177\u0178\5\u00bf`\2\u0178N\3\2\2\2\u0179"+
		"\u017a\5\u0095K\2\u017a\u017b\5\u009dO\2\u017bP\3\2\2\2\u017c\u017d\5"+
		"\u009dO\2\u017d\u017e\5\u00a7T\2\u017e\u017f\5\u0091I\2\u017fR\3\2\2\2"+
		"\u0180\u0181\5\u009dO\2\u0181\u0182\5\u00a7T\2\u0182\u0183\5\u0091I\2"+
		"\u0183\u0184\5\u00b9]\2\u0184T\3\2\2\2\u0185\u0186\5\u009dO\2\u0186\u0187"+
		"\5\u00afX\2\u0187\u0188\5\u0095K\2\u0188\u0189\5\u00b3Z\2\u0189V\3\2\2"+
		"\2\u018a\u018b\5\u009fP\2\u018b\u018c\5\u00abV\2\u018cX\3\2\2\2\u018d"+
		"\u018e\5\u009fP\2\u018e\u018f\5\u00afX\2\u018fZ\3\2\2\2\u0190\u0191\5"+
		"\u00a3R\2\u0191\u0192\5\u0093J\2\u0192\\\3\2\2\2\u0193\u0194\5\u00a3R"+
		"\2\u0194\u0195\5\u0093J\2\u0195\u0196\5\u0091I\2\u0196^\3\2\2\2\u0197"+
		"\u0198\5\u00a3R\2\u0198\u0199\5\u0093J\2\u0199\u019a\5\u0091I\2\u019a"+
		"\u019b\5\u009dO\2\u019b`\3\2\2\2\u019c\u019d\5\u00a3R\2\u019d\u019e\5"+
		"\u0093J\2\u019e\u019f\5\u0095K\2\u019fb\3\2\2\2\u01a0\u01a1\5\u00a3R\2"+
		"\u01a1\u01a2\5\u0093J\2\u01a2\u01a3\5\u0095K\2\u01a3\u01a4\5\u009dO\2"+
		"\u01a4d\3\2\2\2\u01a5\u01a6\5\u00a7T\2\u01a6\u01a7\5\u00a9U\2\u01a7\u01a8"+
		"\5\u00abV\2\u01a8f\3\2\2\2\u01a9\u01aa\5\u00a9U\2\u01aa\u01ab\5\u00af"+
		"X\2\u01abh\3\2\2\2\u01ac\u01ad\5\u00abV\2\u01ad\u01ae\5\u00a9U\2\u01ae"+
		"\u01af\5\u00abV\2\u01afj\3\2\2\2\u01b0\u01b1\5\u00abV\2\u01b1\u01b2\5"+
		"\u00b5[\2\u01b2\u01b3\5\u00b1Y\2\u01b3\u01b4\5\u009bN\2\u01b4l\3\2\2\2"+
		"\u01b5\u01b6\5\u00afX\2\u01b6\u01b7\5\u0091I\2\u01b7\u01b8\5\u0097L\2"+
		"\u01b8n\3\2\2\2\u01b9\u01ba\5\u00afX\2\u01ba\u01bb\5\u0095K\2\u01bb\u01bc"+
		"\5\u00b3Z\2\u01bcp\3\2\2\2\u01bd\u01be\5\u00afX\2\u01be\u01bf\5\u00a3"+
		"R\2\u01bfr\3\2\2\2\u01c0\u01c1\5\u00afX\2\u01c1\u01c2\5\u00a3R\2\u01c2"+
		"\u01c3\5\u0091I\2\u01c3t\3\2\2\2\u01c4\u01c5\5\u00afX\2\u01c5\u01c6\5"+
		"\u00afX\2\u01c6v\3\2\2\2\u01c7\u01c8\5\u00afX\2\u01c8\u01c9\5\u00afX\2"+
		"\u01c9\u01ca\5\u0091I\2\u01cax\3\2\2\2\u01cb\u01cc\5\u00b1Y\2\u01cc\u01cd"+
		"\5\u008fH\2\u01cd\u01ce\5\u0091I\2\u01cez\3\2\2\2\u01cf\u01d0\5\u00b1"+
		"Y\2\u01d0\u01d1\5\u0091I\2\u01d1\u01d2\5\u0097L\2\u01d2|\3\2\2\2\u01d3"+
		"\u01d4\5\u00b1Y\2\u01d4\u01d5\5\u00afX\2\u01d5\u01d6\5\u008dG\2\u01d6"+
		"~\3\2\2\2\u01d7\u01d8\5\u00b1Y\2\u01d8\u01d9\5\u00afX\2\u01d9\u01da\5"+
		"\u00abV\2\u01da\u0080\3\2\2\2\u01db\u01dc\5\u00b1Y\2\u01dc\u01dd\5\u00b5"+
		"[\2\u01dd\u01de\5\u008fH\2\u01de\u0082\3\2\2\2\u01df\u01e0\5\u00b1Y\2"+
		"\u01e0\u01e1\5\u00b9]\2\u01e1\u01e2\5\u008dG\2\u01e2\u01e3\5\u00abV\2"+
		"\u01e3\u0084\3\2\2\2\u01e4\u01e5\5\u00b3Z\2\u01e5\u01e6\5\u0091I\2\u01e6"+
		"\u01e7\5\u00a5S\2\u01e7\u0086\3\2\2\2\u01e8\u01e9\5\u00b3Z\2\u01e9\u01ea"+
		"\5\u00a5S\2\u01ea\u0088\3\2\2\2\u01eb\u01ec\5\u00bb^\2\u01ec\u01ed\5\u00a9"+
		"U\2\u01ed\u01ee\5\u00afX\2\u01ee\u008a\3\2\2\2\u01ef\u0228\5\u0097L\2"+
		"\u01f0\u01f1\5\u00a3R\2\u01f1\u01f2\5\u00b3Z\2\u01f2\u0228\3\2\2\2\u01f3"+
		"\u01f4\5\u00a3R\2\u01f4\u01f5\5\u0095K\2\u01f5\u0228\3\2\2\2\u01f6\u01f7"+
		"\5\u00b5[\2\u01f7\u01f8\5\u00a3R\2\u01f8\u01f9\5\u0095K\2\u01f9\u0228"+
		"\3\2\2\2\u01fa\u01fb\5\u00a9U\2\u01fb\u01fc\5\u00b7\\\2\u01fc\u0228\3"+
		"\2\2\2\u01fd\u01fe\5\u00a5S\2\u01fe\u01ff\5\u009dO\2\u01ff\u0228\3\2\2"+
		"\2\u0200\u0228\5\u00bf`\2\u0201\u0202\5\u0095K\2\u0202\u0203\5\u00adW"+
		"\2\u0203\u0228\3\2\2\2\u0204\u0228\5\u0091I\2\u0205\u0206\5\u00b5[\2\u0206"+
		"\u0207\5\u00a3R\2\u0207\u0208\5\u00b3Z\2\u0208\u0228\3\2\2\2\u0209\u020a"+
		"\5\u0099M\2\u020a\u020b\5\u0095K\2\u020b\u0228\3\2\2\2\u020c\u020d\5\u0099"+
		"M\2\u020d\u020e\5\u00b3Z\2\u020e\u0228\3\2\2\2\u020f\u0210\5\u00b5[\2"+
		"\u0210\u0211\5\u0099M\2\u0211\u0212\5\u00b3Z\2\u0212\u0228\3\2\2\2\u0213"+
		"\u0214\5\u00a7T\2\u0214\u0215\5\u00a9U\2\u0215\u0216\5\u00b7\\\2\u0216"+
		"\u0228\3\2\2\2\u0217\u0218\5\u00abV\2\u0218\u0219\5\u00a3R\2\u0219\u0228"+
		"\3\2\2\2\u021a\u021b\5\u00a7T\2\u021b\u021c\5\u00bf`\2\u021c\u0228\3\2"+
		"\2\2\u021d\u021e\5\u00a7T\2\u021e\u021f\5\u0095K\2\u021f\u0228\3\2\2\2"+
		"\u0220\u0221\5\u00a7T\2\u0221\u0222\5\u0091I\2\u0222\u0228\3\2\2\2\u0223"+
		"\u0224\5\u00b5[\2\u0224\u0225\5\u0099M\2\u0225\u0226\5\u0095K\2\u0226"+
		"\u0228\3\2\2\2\u0227\u01ef\3\2\2\2\u0227\u01f0\3\2\2\2\u0227\u01f3\3\2"+
		"\2\2\u0227\u01f6\3\2\2\2\u0227\u01fa\3\2\2\2\u0227\u01fd\3\2\2\2\u0227"+
		"\u0200\3\2\2\2\u0227\u0201\3\2\2\2\u0227\u0204\3\2\2\2\u0227\u0205\3\2"+
		"\2\2\u0227\u0209\3\2\2\2\u0227\u020c\3\2\2\2\u0227\u020f\3\2\2\2\u0227"+
		"\u0213\3\2\2\2\u0227\u0217\3\2\2\2\u0227\u021a\3\2\2\2\u0227\u021d\3\2"+
		"\2\2\u0227\u0220\3\2\2\2\u0227\u0223\3\2\2\2\u0228\u008c\3\2\2\2\u0229"+
		"\u022a\t\13\2\2\u022a\u008e\3\2\2\2\u022b\u022c\t\f\2\2\u022c\u0090\3"+
		"\2\2\2\u022d\u022e\t\r\2\2\u022e\u0092\3\2\2\2\u022f\u0230\t\16\2\2\u0230"+
		"\u0094\3\2\2\2\u0231\u0232\t\17\2\2\u0232\u0096\3\2\2\2\u0233\u0234\t"+
		"\20\2\2\u0234\u0098\3\2\2\2\u0235\u0236\t\21\2\2\u0236\u009a\3\2\2\2\u0237"+
		"\u0238\t\22\2\2\u0238\u009c\3\2\2\2\u0239\u023a\t\23\2\2\u023a\u009e\3"+
		"\2\2\2\u023b\u023c\t\24\2\2\u023c\u00a0\3\2\2\2\u023d\u023e\t\25\2\2\u023e"+
		"\u00a2\3\2\2\2\u023f\u0240\t\26\2\2\u0240\u00a4\3\2\2\2\u0241\u0242\t"+
		"\27\2\2\u0242\u00a6\3\2\2\2\u0243\u0244\t\30\2\2\u0244\u00a8\3\2\2\2\u0245"+
		"\u0246\t\31\2\2\u0246\u00aa\3\2\2\2\u0247\u0248\t\32\2\2\u0248\u00ac\3"+
		"\2\2\2\u0249\u024a\t\33\2\2\u024a\u00ae\3\2\2\2\u024b\u024c\t\34\2\2\u024c"+
		"\u00b0\3\2\2\2\u024d\u024e\t\35\2\2\u024e\u00b2\3\2\2\2\u024f\u0250\t"+
		"\36\2\2\u0250\u00b4\3\2\2\2\u0251\u0252\t\37\2\2\u0252\u00b6\3\2\2\2\u0253"+
		"\u0254\t \2\2\u0254\u00b8\3\2\2\2\u0255\u0256\t!\2\2\u0256\u00ba\3\2\2"+
		"\2\u0257\u0258\t\"\2\2\u0258\u00bc\3\2\2\2\u0259\u025a\t#\2\2\u025a\u00be"+
		"\3\2\2\2\u025b\u025c\t$\2\2\u025c\u00c0\3\2\2\2\u025d\u0261\t%\2\2\u025e"+
		"\u0260\t&\2\2\u025f\u025e\3\2\2\2\u0260\u0263\3\2\2\2\u0261\u025f\3\2"+
		"\2\2\u0261\u0262\3\2\2\2\u0262\u00c2\3\2\2\2\u0263\u0261\3\2\2\2\u0264"+
		"\u0266\t\'\2\2\u0265\u0264\3\2\2\2\u0266\u0267\3\2\2\2\u0267\u0265\3\2"+
		"\2\2\u0267\u0268\3\2\2\2\u0268\u0269\3\2\2\2\u0269\u026a\bb\2\2\u026a"+
		"\u00c4\3\2\2\2\u026b\u026d\7\17\2\2\u026c\u026e\7\f\2\2\u026d\u026c\3"+
		"\2\2\2\u026d\u026e\3\2\2\2\u026e\u0271\3\2\2\2\u026f\u0271\7\f\2\2\u0270"+
		"\u026b\3\2\2\2\u0270\u026f\3\2\2\2\u0271\u00c6\3\2\2\2\u0272\u0273\7\61"+
		"\2\2\u0273\u0274\7\61\2\2\u0274\u0278\3\2\2\2\u0275\u0277\n(\2\2\u0276"+
		"\u0275\3\2\2\2\u0277\u027a\3\2\2\2\u0278\u0276\3\2\2\2\u0278\u0279\3\2"+
		"\2\2\u0279\u0283\3\2\2\2\u027a\u0278\3\2\2\2\u027b\u027f\7=\2\2\u027c"+
		"\u027e\n(\2\2\u027d\u027c\3\2\2\2\u027e\u0281\3\2\2\2\u027f\u027d\3\2"+
		"\2\2\u027f\u0280\3\2\2\2\u0280\u0283\3\2\2\2\u0281\u027f\3\2\2\2\u0282"+
		"\u0272\3\2\2\2\u0282\u027b\3\2\2\2\u0283\u0284\3\2\2\2\u0284\u0285\bd"+
		"\2\2\u0285\u00c8\3\2\2\2\u0286\u0287\7\61\2\2\u0287\u0288\7,\2\2\u0288"+
		"\u028c\3\2\2\2\u0289\u028b\13\2\2\2\u028a\u0289\3\2\2\2\u028b\u028e\3"+
		"\2\2\2\u028c\u028d\3\2\2\2\u028c\u028a\3\2\2\2\u028d\u028f\3\2\2\2\u028e"+
		"\u028c\3\2\2\2\u028f\u0290\7,\2\2\u0290\u0291\7\61\2\2\u0291\u0292\3\2"+
		"\2\2\u0292\u0293\be\2\2\u0293\u00ca\3\2\2\2\25\2\u00d3\u00d5\u00dc\u00de"+
		"\u00e5\u0102\u0112\u0116\u0121\u0227\u0261\u0267\u026d\u0270\u0278\u027f"+
		"\u0282\u028c\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}