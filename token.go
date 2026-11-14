package z8asm

import "fmt"

// TokenType identifies a token produced by Lexer.
type TokenType uint8

const (
	TokenEOF TokenType = iota
	TokenWhitespace
	TokenLineBreak
	TokenComment
	TokenIntLiteral
	TokenLabel
	TokenAt
	TokenComma
	TokenHash
	TokenLParen
	TokenRParen
	TokenWorkRegister
	TokenWorkRegisterPair
	TokenString
	TokenLengthString
	TokenConst
	TokenData
	TokenEnd
	TokenOrg
	TokenRepeat
	TokenAlign
	TokenLD
	TokenLDE
	TokenLDC
	TokenLDEI
	TokenLDCI
	TokenCALL
	TokenDJNZ
	TokenJP
	TokenJR
	TokenDI
	TokenEI
	TokenRET
	TokenIRET
	TokenRCF
	TokenSCF
	TokenCCF
	TokenNOP
	TokenDEC
	TokenRLC
	TokenINC
	TokenSRP
	TokenDA
	TokenPOP
	TokenCOM
	TokenPUSH
	TokenDECW
	TokenRL
	TokenINCW
	TokenCLR
	TokenRRC
	TokenSRA
	TokenRR
	TokenSWAP
	TokenADD
	TokenADC
	TokenSUB
	TokenSBC
	TokenOR
	TokenAND
	TokenTCM
	TokenTM
	TokenCP
	TokenXOR
	TokenIdentifier
)

var tokenTypeNames = [...]string{
	"EOF", "WHITESPACE", "LINE_BREAK", "COMMENT", "INT_LITERAL", "LABEL",
	"AT", "COMMA", "HASH", "L_PAREN", "R_PAREN", "WORK_REGISTER",
	"WORK_REGISTER_PAIR", "STRING", "LENGTH_STRING", "CONST", "DATA", "END",
	"ORG", "REPEAT", "ALIGN", "LD", "LDE", "LDC", "LDEI", "LDCI", "CALL",
	"DJNZ", "JP", "JR", "DI", "EI", "RET", "IRET", "RCF", "SCF", "CCF",
	"NOP", "DEC", "RLC", "INC", "SRP", "DA", "POP", "COM", "PUSH", "DECW",
	"RL", "INCW", "CLR", "RRC", "SRA", "RR", "SWAP", "ADD", "ADC", "SUB",
	"SBC", "OR", "AND", "TCM", "TM", "CP", "XOR", "IDENTIFIER",
}

func (t TokenType) String() string {
	if int(t) < len(tokenTypeNames) {
		return tokenTypeNames[t]
	}
	return fmt.Sprintf("TokenType(%d)", t)
}

// Token carries the decoded value, source spelling, and source position.
type Token struct {
	Type     TokenType
	Text     string
	IntValue int
	Size     int
	Location Location
}

func (t Token) String() string {
	return fmt.Sprintf("Token[type=%s, text=%q, intValue=%d, size=%d, location=%s]", t.Type, t.Text, t.IntValue, t.Size, t.Location)
}
