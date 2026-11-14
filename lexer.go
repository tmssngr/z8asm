package z8asm

import (
	"strconv"
	"strings"
	"unicode/utf16"
)

// Lexer tokenizes Z8 assembly source. Columns and string lengths count UTF-16
// code units to match the Java implementation.
type Lexer struct {
	chars  []rune
	index  int
	line   int
	column int
}

func NewLexer(source string) *Lexer {
	return &Lexer{chars: []rune(source)}
}

// Next returns the next token or a positioned error for an invalid literal.
func (l *Lexer) Next() (Token, error) {
	location := Location{Line: l.line, Column: l.column}
	if l.empty() {
		return Token{Type: TokenEOF, Location: location}, nil
	}

	ch := l.current()
	switch {
	case isWhitespace(ch):
		return Token{Type: TokenWhitespace, Text: l.scanWhitespace(), Location: location}, nil
	case isLineBreak(ch):
		return Token{Type: TokenLineBreak, Text: l.scanLineBreak(), Location: location}, nil
	case ch == ';':
		return Token{Type: TokenComment, Text: l.scanLineComment(";"), Location: location}, nil
	case ch == '/':
		l.consume()
		if !l.empty() {
			switch l.current() {
			case '/':
				return Token{Type: TokenComment, Text: l.scanLineComment("//"), Location: location}, nil
			case '*':
				text, err := l.scanBlockComment(location)
				if err != nil {
					return Token{}, err
				}
				return Token{Type: TokenComment, Text: text, Location: location}, nil
			}
		}
		return Token{Type: TokenIdentifier, Text: "/", Location: location}, nil
	case ch == ',':
		l.consume()
		return Token{Type: TokenComma, Location: location}, nil
	case ch == '#':
		l.consume()
		return Token{Type: TokenHash, Location: location}, nil
	case ch == '@':
		l.consume()
		return Token{Type: TokenAt, Location: location}, nil
	case ch == '(':
		l.consume()
		return Token{Type: TokenLParen, Location: location}, nil
	case ch == ')':
		l.consume()
		return Token{Type: TokenRParen, Location: location}, nil
	case ch == '"':
		l.consume()
		text, err := l.scanString(false, '"', location)
		if err != nil {
			return Token{}, err
		}
		return Token{Type: TokenString, Text: text, Location: location}, nil
	case ch == '\'':
		l.consume()
		text, err := l.scanString(false, '\'', location)
		if err != nil {
			return Token{}, err
		}
		units := utf16.Encode([]rune(text))
		if len(units) != 1 {
			return Token{}, &InvalidTokenError{Message: "Invalid char literal", Location: location}
		}
		return Token{Type: TokenIntLiteral, IntValue: int(units[0]), Size: 1, Location: location}, nil
	}

	var word strings.Builder
	word.WriteRune(ch)
	for {
		l.consume()
		if l.empty() {
			break
		}
		ch = l.current()
		if ch == '"' {
			if word.String() == "L" {
				l.consume()
				text, err := l.scanString(true, '"', location)
				if err != nil {
					return Token{}, err
				}
				return Token{Type: TokenLengthString, Text: text, Location: location}, nil
			}
			break
		}
		if isWordDelimiter(ch) {
			break
		}
		word.WriteRune(ch)
	}

	text := word.String()
	if len([]rune(text)) > 1 {
		if strings.HasSuffix(text, ":") {
			return Token{Type: TokenLabel, Text: strings.TrimSuffix(text, ":"), Location: location}, nil
		}
		if value, ok := parseWorkRegister(text, "rr"); ok {
			return Token{Type: TokenWorkRegisterPair, IntValue: value, Location: location}, nil
		}
		if value, ok := parseWorkRegister(text, "r"); ok {
			return Token{Type: TokenWorkRegister, IntValue: value, Location: location}, nil
		}
	}
	if value, size, ok, err := parseInteger(text, location); err != nil {
		return Token{}, err
	} else if ok {
		return Token{Type: TokenIntLiteral, IntValue: value, Size: size, Location: location}, nil
	}
	return Token{Type: keywordOrIdentifier(text), Text: text, Location: location}, nil
}

func (l *Lexer) empty() bool { return l.index >= len(l.chars) }

func (l *Lexer) current() rune { return l.chars[l.index] }

func (l *Lexer) consume() {
	if l.empty() {
		return
	}
	previous := l.current()
	l.index++
	if previous == '\n' {
		l.line++
		l.column = 0
		return
	}
	if previous > 0xffff {
		l.column += 2
	} else {
		l.column++
	}
}

func (l *Lexer) scanWhitespace() string {
	var out strings.Builder
	for !l.empty() && isWhitespace(l.current()) {
		out.WriteRune(l.current())
		l.consume()
	}
	return out.String()
}

func (l *Lexer) scanLineBreak() string {
	var out strings.Builder
	for !l.empty() && isLineBreak(l.current()) {
		out.WriteRune(l.current())
		l.consume()
	}
	return out.String()
}

func (l *Lexer) scanLineComment(start string) string {
	var out strings.Builder
	out.WriteString(start)
	l.consume()
	for !l.empty() && !isLineBreak(l.current()) {
		out.WriteRune(l.current())
		l.consume()
	}
	return out.String()
}

func (l *Lexer) scanBlockComment(location Location) (string, error) {
	var out strings.Builder
	out.WriteString("/*")
	var previous rune
	for {
		l.consume()
		if l.empty() {
			return "", &InvalidTokenError{Message: "Block comment needs to be closed", Location: location}
		}
		ch := l.current()
		out.WriteRune(ch)
		if previous == '*' && ch == '/' {
			l.consume()
			return out.String(), nil
		}
		previous = ch
	}
}

func (l *Lexer) scanString(lengthString bool, end rune, start Location) (string, error) {
	var out strings.Builder
	var escapeStart *Location
	for {
		if l.empty() || isLineBreak(l.current()) {
			if end == '\'' {
				return "", &InvalidTokenError{Message: "A char literal must end with a quote", Location: start}
			}
			return "", &InvalidTokenError{Message: "String must end with a double-quote", Location: Location{Line: l.line, Column: l.column}}
		}
		ch := l.current()
		if escapeStart != nil {
			switch ch {
			case 't':
				out.WriteByte('\t')
				l.consume()
			case 'n':
				out.WriteByte('\n')
				l.consume()
			case 'r':
				out.WriteByte('\r')
				l.consume()
			case '0':
				out.WriteByte(0)
				l.consume()
			case 'x':
				l.consume()
				digits := 0
				value := 0
				for !l.empty() {
					nibble := hexNibble(l.current())
					if nibble < 0 {
						break
					}
					value = (16*value + nibble) & 0xffff
					digits++
					l.consume()
				}
				if digits == 0 {
					return "", &InvalidTokenError{Message: "Invalid char escape", Location: *escapeStart}
				}
				out.WriteRune(rune(value))
			default:
				out.WriteRune(ch)
				l.consume()
			}
			escapeStart = nil
			continue
		}
		if ch == end {
			l.consume()
			break
		}
		if ch == '\\' {
			loc := Location{Line: l.line, Column: l.column}
			escapeStart = &loc
			l.consume()
		} else {
			out.WriteRune(ch)
			l.consume()
		}
	}
	text := out.String()
	if lengthString {
		if utf16Length(text) > 255 {
			return "", &InvalidTokenError{Message: "A length-string only can be up to 255 characters long", Location: start}
		}
	} else if text == "" {
		return "", &InvalidTokenError{Message: "An empty string can only be done with L\"\"", Location: start}
	}
	return text, nil
}

func isWhitespace(ch rune) bool { return ch == ' ' || ch == '\t' }

func isLineBreak(ch rune) bool { return ch == '\r' || ch == '\n' }

func isWordDelimiter(ch rune) bool {
	return ch == ',' || ch == ';' || ch == '(' || ch == ')' || ch == '#' || isWhitespace(ch) || isLineBreak(ch)
}

func utf16Length(s string) int { return len(utf16.Encode([]rune(s))) }

func hexNibble(ch rune) int {
	switch {
	case ch >= '0' && ch <= '9':
		return int(ch - '0')
	case ch >= 'A' && ch <= 'F':
		return int(ch-'A') + 10
	case ch >= 'a' && ch <= 'f':
		return int(ch-'a') + 10
	default:
		return -1
	}
}

func parseWorkRegister(text, prefix string) (int, bool) {
	text = strings.ToLower(text)
	if !strings.HasPrefix(text, prefix) {
		return 0, false
	}
	suffix := text[len(prefix):]
	value, err := strconv.Atoi(suffix)
	if err != nil || value < 0 || value >= 16 {
		return 0, false
	}
	return value, true
}

func parseInteger(text string, location Location) (int, int, bool, error) {
	if value, err := strconv.ParseInt(text, 10, 32); err == nil {
		return int(value), 2, true, nil
	}
	if strings.HasPrefix(text, "0b") {
		digits := strings.ReplaceAll(text[2:], "_", "")
		value, err := strconv.ParseInt(digits, 2, 32)
		if err != nil {
			return 0, 0, false, &InvalidTokenError{Message: "Invalid number", Location: location}
		}
		return int(value), 1, true, nil
	}
	if strings.HasPrefix(text, "%") {
		digits := text[1:]
		value, err := strconv.ParseInt(digits, 16, 32)
		if err == nil {
			size := 2
			if len([]rune(digits)) <= 2 {
				size = 1
			}
			return int(value), size, true, nil
		}
	}
	return 0, 0, false, nil
}

func keywordOrIdentifier(text string) TokenType {
	switch strings.ToLower(text) {
	case ".align":
		return TokenAlign
	case ".const":
		return TokenConst
	case ".data":
		return TokenData
	case ".end":
		return TokenEnd
	case ".org":
		return TokenOrg
	case ".repeat":
		return TokenRepeat
	case "ld":
		return TokenLD
	case "lde":
		return TokenLDE
	case "ldc":
		return TokenLDC
	case "ldei":
		return TokenLDEI
	case "ldci":
		return TokenLDCI
	case "call":
		return TokenCALL
	case "djnz":
		return TokenDJNZ
	case "jp":
		return TokenJP
	case "jr":
		return TokenJR
	case "di":
		return TokenDI
	case "ei":
		return TokenEI
	case "ret":
		return TokenRET
	case "iret":
		return TokenIRET
	case "rcf":
		return TokenRCF
	case "scf":
		return TokenSCF
	case "ccf":
		return TokenCCF
	case "nop":
		return TokenNOP
	case "dec":
		return TokenDEC
	case "rlc":
		return TokenRLC
	case "inc":
		return TokenINC
	case "srp":
		return TokenSRP
	case "da":
		return TokenDA
	case "pop":
		return TokenPOP
	case "com":
		return TokenCOM
	case "push":
		return TokenPUSH
	case "decw":
		return TokenDECW
	case "rl":
		return TokenRL
	case "incw":
		return TokenINCW
	case "clr":
		return TokenCLR
	case "rrc":
		return TokenRRC
	case "sra":
		return TokenSRA
	case "rr":
		return TokenRR
	case "swap":
		return TokenSWAP
	case "add":
		return TokenADD
	case "adc":
		return TokenADC
	case "sub":
		return TokenSUB
	case "sbc":
		return TokenSBC
	case "or":
		return TokenOR
	case "and":
		return TokenAND
	case "tcm":
		return TokenTCM
	case "tm":
		return TokenTM
	case "cp":
		return TokenCP
	case "xor":
		return TokenXOR
	default:
		return TokenIdentifier
	}
}
