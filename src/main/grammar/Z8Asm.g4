grammar Z8Asm;

code
    :
    ( labelDefinition
    | command
    | NL
    )*
    ;

labelDefinition
    : Identifier Colon NL?
    ;

address
    : Word
    | Identifier
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
    | data
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

data
    : Data dataItem+
    ;

dataItem
    : Byte
    | address
    | String
    | Char
    ;

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
srp : Srp ValueByte ;

call : Call address
     | Call iregisterPair
     ;

djnz : Djnz WorkingRegister Comma address;

jp : Jp iregisterPair
   | Jp address
   | Jp JpCondition Comma address
   ;

jr : Jr address
   | Jr JpCondition Comma address
   ;

ld : ld1 | ld2;
ld1 : Ld register Comma (registerOrIregister | ValueByte) ;
ld2 : Ld iregister Comma register ;

ldc : ldc1 | ldc2;
ldc1: Ldc WorkingRegister Comma IWorkingRegisterPair ;
ldc2: Ldc IWorkingRegisterPair Comma WorkingRegister ;

ldci : ldci1 | ldci2;
ldci1: Ldci IWorkingRegister Comma IWorkingRegisterPair ;
ldci2: Ldci IWorkingRegisterPair Comma IWorkingRegister ;

lde : lde1 | lde2;
lde1 : Lde WorkingRegister Comma IWorkingRegisterPair ;
lde2 : Lde IWorkingRegisterPair Comma WorkingRegister ;

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
    ;

arithmeticParameters1
    : register Comma (register | ValueByte)
    ;

arithmeticParameters2
    : iregister Comma ValueByte
    ;

registerOrIregister
    : register
    | iregister
    ;

register
    : Byte
    | WorkingRegister
    | RegisterConstant
    ;

iregister
    : IndirectPrefix register
    | IWorkingRegister
    ;

iregisterPair
    : IndirectPrefix register
    | IWorkingRegisterPair
    ;

String
    : L '"' (EscapedChar | ~["\\])+ '"'
    ;

Char
    : '\'' (EscapedChar | ~[\\]) '\''
    ;

fragment EscapedChar
    : '\\' [nr\\]
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

Byte
    : HexPrefix HexDigit HexDigit?
    | ByteDecimal
    ;

Word
    : HexPrefix HexDigit HexDigit HexDigit HexDigit
    ;

fragment ValuePrefix
    : '#'
    ;

ValueByte
    : ValuePrefix
    ( HexPrefix HexDigit HexDigit
    | ByteDecimal
    )
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

Data : '.' D B ;

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

RegisterConstant
    : 'P01M'
    | 'P3M'
    | 'TMR'
    | 'RP'
    | 'SPH'
    | 'SPL'
    ;

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