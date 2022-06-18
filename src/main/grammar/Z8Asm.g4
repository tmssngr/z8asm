grammar Z8Asm;

root: (code | NL)*;

code
    : labelDefinition
    | parserInstruction
    | command
    ;

labelDefinition
    : Identifier Colon NL?
    ;

address
    : Word
    | label=Identifier
    ;

parserInstruction: ( defConst
                   | data
                   | org
                   | repeat
                   )
                   NL?
                   ;

command
    :
    ( adc
    | add
    | and
    | call
    | ccf
    | clr
    | com
    | cp
    | da
    | dec
    | decw
    | di
    | djnz
    | ei
    | inc
    | incw
    | iret
    | jp
    | jr
    | ld
    | ldc
    | ldci
    | lde
    | ldei
    | nop
    | or
    | pop
    | push
    | rcf
    | ret
    | rl
    | rlc
    | rr
    | rrc
    | sbc
    | scf
    | sra
    | srp
    | sub
    | swap
    | tcm
    | tm
    | xor
    ) (NL | EOF)
    ;

defConst : Const name=Identifier '=' expr=expression ;

data
    : Data dataItem+
    ;

dataItem
    : Byte       #dataByte
    | address    #dataAddress
    | LString    #dataLString
    | String     #dataString
    | Char       #dataChar
    ;

org
    : Org Word
    ;

repeat: Repeat expression
          repeatInstructions
        End
      ;

repeatInstructions: (code | NL)+;

dec : Dec registerOrIregister ;
rlc : Rlc registerOrIregister ;
inc : Inc registerOrIregister ;
da  : Da registerOrIregister ;
pop : Pop registerOrIregister ;
com : Com registerOrIregister ;
push: Push registerOrIregister ;
decw: Decw registerOrIregister ;
rl  : Rl registerOrIregister ;
incw: Incw registerOrIregister ;
clr : Clr registerOrIregister ;
rrc : Rrc registerOrIregister ;
sra : Sra registerOrIregister ;
rr  : Rr registerOrIregister ;
swap: Swap registerOrIregister ;
srp : Srp valueByte ;

call : Call address        #callAddress
     | Call iregisterPair  #callIreg
     ;

djnz : Djnz WorkingRegister Comma address;

jp : Jp iregisterPair             #jpIReg
   | Jp address                   #jpAddress
   | Jp JpCondition Comma address #jpConditionAddress
   ;

jr : Jr address
   | Jr JpCondition Comma address
   ;

ld : Ld register         Comma valueByte         #ld1
   | Ld target=register  Comma source=register   #ld2
   | Ld register         Comma iregister         #ld3
   | Ld WorkingRegister  Comma Byte '(' WorkingRegister ')'  #ld4
   | Ld Byte '(' WorkingRegister ')' Comma WorkingRegister   #ld5
   | Ld iregister        Comma register          #ld6
   ;

ldc : Ldc WorkingRegister Comma IWorkingRegisterPair #ldc1
    | Ldc IWorkingRegisterPair Comma WorkingRegister #ldc2
    ;

ldci : Ldci IWorkingRegister Comma IWorkingRegisterPair #ldci1
     | Ldci IWorkingRegisterPair Comma IWorkingRegister #ldci2
     ;

lde : Lde WorkingRegister Comma IWorkingRegisterPair #lde1
    | Lde IWorkingRegisterPair Comma WorkingRegister #lde2
    ;

ldei : Ldei IWorkingRegister Comma IWorkingRegisterPair #ldei1
     | Ldei IWorkingRegisterPair Comma IWorkingRegister #ldei2
     ;

di  : Di ;
ei  : Ei ;
ret : Ret ;
iret : Iret;
rcf : Rcf ;
scf : Scf;
ccf : Ccf ;
nop : Nop ;

adc : Adc arithmeticParameters ;
add : Add arithmeticParameters ;
and : And arithmeticParameters ;
cp  : Cp arithmeticParameters ;
or  : Or arithmeticParameters ;
sbc : Sbc arithmeticParameters ;
sub : Sub arithmeticParameters ;
tcm : Tcm arithmeticParameters ;
tm  : Tm arithmeticParameters ;
xor : Xor arithmeticParameters ;

arithmeticParameters
    : arithmeticParameters1
    | arithmeticParameters2
    | arithmeticParameters3
    | arithmeticParameters4
    ;
arithmeticParameters1 : register Comma valueByte ;
arithmeticParameters2 : target=register Comma source=register ;
arithmeticParameters3 : iregister Comma valueByte ;
arithmeticParameters4 : register Comma iregister ;

registerOrIregister
    : register
    | iregister
    ;

register
    : Byte
    | WorkingRegister
    | Identifier
    ;

iregister
    : IndirectPrefix register
    | IWorkingRegister
    ;

iregisterPair
    : IndirectPrefix register
    | IWorkingRegisterPair
    ;

valueByte : '#' expression ;

expression : (Byte | Word) #exprNumber
           | Char          #exprChar
           | Identifier    #exprIdentifier
           ;

// lexer rules:

LString
    : L '"' (EscapedChar | ~["\\])+ '"'
    ;

String
    : '"' (EscapedChar | ~["\\])+ '"'
    ;

Char
    : '\'' (EscapedChar | ~[\\]) '\''
    ;

fragment EscapedChar
    : '\\' ["nr\\0]
    ;

fragment Digit
    : [0-9]
    ;

fragment BinDigit
    : [01]
    ;

fragment HexDigit
    : [0-9a-fA-F]
    ;

fragment HexPrefix
    : '%'
    ;

fragment ByteDecimal
    : Digit
    | [1-9] Digit
    | '1' Digit Digit
    | '2' [0-4] Digit
    | '25' [0-5]
    ;

fragment BinaryNibble: BinDigit BinDigit BinDigit BinDigit ;
fragment BinaryLiteral: '0' B BinaryNibble '_' BinaryNibble ;


Byte
    : HexPrefix  HexDigit HexDigit?
    | ByteDecimal
    | BinaryLiteral
    ;

fragment WordDecimal
	: Digit
	| [1-9] Digit
	| [1-9] Digit Digit
	| [1-9] Digit Digit Digit
	| [1-5] Digit Digit Digit Digit
	| '6' [0-4] Digit Digit Digit
	| '65' [0-4] Digit Digit
	| '655' [0-2] Digit
	| '6553' [0-5]
	;

Word
    : HexPrefix  HexDigit HexDigit HexDigit HexDigit
    | WordDecimal
    ;

fragment NibbleDec :
    ( [0-9]
    | '1' [0-5]
    )
    ;

WorkingRegister
    : R NibbleDec ;

IndirectPrefix
    : '@'
    ;

IWorkingRegister
    : IndirectPrefix R NibbleDec ;

IWorkingRegisterPair
    : IndirectPrefix R R NibbleDec ;

Colon
    : ':'
    ;

Comma
    : ','
    ;

Const  : '.' C O N S T ;
Data   : '.' D A T A ;
Org    : '.' O R G ;
Repeat : '.' R E P E A T;
End    : '.' E N D;

Adc  : A D C ;
Add  : A D D ;
And  : A N D ;
Call : C A L L ;
Ccf  : C C F ;
Clr  : C L R ;
Com  : C O M ;
Cp   : C P ;
Da   : D A ;
Dec  : D E C ;
Decw : D E C W ;
Di   : D I ;
Djnz : D J N Z ;
Ei   : E I ;
Inc  : I N C ;
Incw : I N C W ;
Iret : I R E T ;
Jp   : J P ;
Jr   : J R;
Ld   : L D ;
Ldc  : L D C ;
Ldci : L D C I ;
Lde  : L D E ;
Ldei : L D E I ;
Nop  : N O P ;
Or   : O R ;
Pop  : P O P ;
Push : P U S H ;
Rcf  : R C F ;
Ret  : R E T ;
Rl   : R L ;
Rlc  : R L C ;
Rr   : R R ;
Rrc  : R R C ;
Sbc  : S B C ;
Scf  : S C F ;
Sra  : S R A ;
Srp  : S R P ;
Sub  : S U B ;
Swap : S W A P ;
Tcm  : T C M ;
Tm   : T M ;
Xor  : X O R ;

JpCondition
    : F
    | L T
    | L E
    | U L E
    | O V
    | M I
    | Z
    | E Q
    | C
    | U L T
    | G E
    | G T
    | U G T
    | N O V
    | P L
    | N Z
    | N E
    | N C
    | U G E
    ;

fragment A : [aA] ;
fragment B : [bB] ;
fragment C : [cC] ;
fragment D : [dD] ;
fragment E : [eE] ;
fragment F : [fF] ;
fragment G : [gG] ;
fragment H : [hH] ;
fragment I : [iI] ;
fragment J : [jJ] ;
fragment K : [kK] ;
fragment L : [lL] ;
fragment M : [mM] ;
fragment N : [nN] ;
fragment O : [oO] ;
fragment P : [pP] ;
fragment Q : [qQ] ;
fragment R : [rR] ;
fragment S : [sS] ;
fragment T : [tT] ;
fragment U : [uU] ;
fragment V : [vV] ;
fragment W : [wW] ;
fragment X : [xX] ;
fragment Y : [yY] ;
fragment Z : [zZ] ;

Identifier
    : [a-zA-Z] [0-9a-zA-Z_]*
    ;

Whitespace
	: [ \t]+
	  -> skip
	;

NL
	: '\r' '\n'?
	| '\n'
	;

LineComment
	: ('//' ~[\r\n]*
	  | ';' ~[\r\n]*
	  )
	  -> skip
	;

BlockComment
	: '/*' .*? '*/'
	  -> skip
	;
