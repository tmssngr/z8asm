package z8asm

import (
	"fmt"
	"os"
	"strings"
	"unicode/utf16"
)

const workRegisterBase = 0xe0

type parseFailure struct{ err error }

// Parser converts tokens into Z8 commands and retains references that require
// labels from the complete source file.
type Parser struct {
	lexer       *Lexer
	constants   map[string]int
	token       Token
	maxCommands int
}

func NewParser(lexer *Lexer) *Parser {
	return &Parser{
		lexer:       lexer,
		constants:   make(map[string]int),
		maxCommands: 1_000_000,
	}
}

func Parse(source string) ([]Command, error) {
	lexer := NewLexer(source)
	parser := NewParser(lexer)
	return parser.Parse()
}

func ParseFile(path string) ([]Command, error) {
	source, err := os.ReadFile(path)
	if err != nil {
		return nil, err
	}
	return Parse(string(source))
}

// Parse reads the full source. Lexer and parser errors are returned as errors
// instead of terminating the process.
func (p *Parser) Parse() (commands []Command, err error) {
	defer func() {
		if recovered := recover(); recovered != nil {
			if parseErr, ok := recovered.(parseFailure); ok {
				commands = nil
				err = parseErr.err
				return
			}
			panic(recovered)
		}
	}()

	p.consume()
	commands = make([]Command, 0, 256)
	p.parse(TokenEOF, &commands)
	return commands, nil
}

func (p *Parser) parse(allowedEnd TokenType, commands *[]Command) {
	for p.token.Type != allowedEnd {
		if p.token.Type == TokenLabel {
			if allowedEnd == TokenEnd {
				p.syntax("Labels inside .repeat .end blocks are not allowed", p.token.Location)
			}
			*commands = append(*commands, LabelCommand{Name: p.token.Text, Location: p.token.Location})
			p.consume()
		}

		switch p.token.Type {
		case TokenLineBreak:
			p.consume()
			continue
		case TokenAlign:
			*commands = append(*commands, p.handleAlign())
		case TokenConst:
			p.handleConst()
		case TokenData:
			p.appendData(commands)
			if len(*commands) > p.maxCommands {
				p.syntax("program expands to too many commands", p.token.Location)
			}
			continue
		case TokenRepeat:
			p.handleRepeat(commands)
		case TokenOrg:
			*commands = append(*commands, p.parseOrg())
		case TokenDEC:
			*commands = append(*commands, p.unary(0x00))
		case TokenRLC:
			*commands = append(*commands, p.unary(0x10))
		case TokenINC:
			*commands = append(*commands, p.increment())
		case TokenSRP:
			*commands = append(*commands, p.createSRP())
		case TokenDA:
			*commands = append(*commands, p.unary(0x40))
		case TokenPOP:
			*commands = append(*commands, p.unary(0x50))
		case TokenCOM:
			*commands = append(*commands, p.unary(0x60))
		case TokenPUSH:
			*commands = append(*commands, p.unary(0x70))
		case TokenDECW:
			*commands = append(*commands, p.unary(0x80))
		case TokenRL:
			*commands = append(*commands, p.unary(0x90))
		case TokenINCW:
			*commands = append(*commands, p.unary(0xa0))
		case TokenCLR:
			*commands = append(*commands, p.unary(0xb0))
		case TokenRRC:
			*commands = append(*commands, p.unary(0xc0))
		case TokenSRA:
			*commands = append(*commands, p.unary(0xd0))
		case TokenRR:
			*commands = append(*commands, p.unary(0xe0))
		case TokenSWAP:
			*commands = append(*commands, p.unary(0xf0))
		case TokenADD:
			*commands = append(*commands, p.createBinary(0x0))
		case TokenADC:
			*commands = append(*commands, p.createBinary(0x1))
		case TokenSUB:
			*commands = append(*commands, p.createBinary(0x2))
		case TokenSBC:
			*commands = append(*commands, p.createBinary(0x3))
		case TokenOR:
			*commands = append(*commands, p.createBinary(0x4))
		case TokenAND:
			*commands = append(*commands, p.createBinary(0x5))
		case TokenTCM:
			*commands = append(*commands, p.createBinary(0x6))
		case TokenTM:
			*commands = append(*commands, p.createBinary(0x7))
		case TokenCP:
			*commands = append(*commands, p.createBinary(0xa))
		case TokenXOR:
			*commands = append(*commands, p.createBinary(0xb))
		case TokenLD:
			*commands = append(*commands, p.createLD())
		case TokenLDC:
			*commands = append(*commands, p.createLDX(0xc))
		case TokenLDE:
			*commands = append(*commands, p.createLDX(0x8))
		case TokenLDCI:
			*commands = append(*commands, p.createLDXI(0xc))
		case TokenLDEI:
			*commands = append(*commands, p.createLDXI(0x8))
		case TokenCALL:
			*commands = append(*commands, p.createCall())
		case TokenJP:
			*commands = append(*commands, p.createJP())
		case TokenJR:
			*commands = append(*commands, p.createJR())
		case TokenDJNZ:
			*commands = append(*commands, p.createDJNZ())
		case TokenDI:
			*commands = append(*commands, p.singleByte(0x8f))
		case TokenEI:
			*commands = append(*commands, p.singleByte(0x9f))
		case TokenRET:
			*commands = append(*commands, p.singleByte(0xaf))
		case TokenIRET:
			*commands = append(*commands, p.singleByte(0xbf))
		case TokenRCF:
			*commands = append(*commands, p.singleByte(0xcf))
		case TokenSCF:
			*commands = append(*commands, p.singleByte(0xdf))
		case TokenCCF:
			*commands = append(*commands, p.singleByte(0xef))
		case TokenNOP:
			*commands = append(*commands, p.singleByte(0xff))
		default:
			p.syntax(fmt.Sprintf("unexpected %s %s", p.token.Type, p.token.Text), p.token.Location)
		}

		if len(*commands) > p.maxCommands {
			p.syntax("program expands to too many commands", p.token.Location)
		}
		if p.token.Type == TokenEOF {
			break
		}
		p.consumeExpect(TokenLineBreak)
	}
}

func (p *Parser) handleAlign() Command {
	p.consume()
	alignment := p.consumeIntLiteral()
	if alignment < 2 || alignment&(alignment-1) != 0 {
		p.syntax("Expected a number the power of 2 (2, 4, 8, ...", p.token.Location)
	}
	p.consumeComma()
	fill := p.consumeIntLiteral()
	return Align{Alignment: alignment, FillByte: byte(fill)}
}

func (p *Parser) handleConst() {
	p.consume()
	name := p.consumeIdentifier()
	if _, exists := p.constants[name]; exists {
		p.syntax("Duplicate definition of "+name, p.token.Location)
	}
	if p.consumeIdentifier() != "=" {
		p.syntax("Expected =", p.token.Location)
	}
	p.constants[name] = p.consumeIntLiteral()
}

func (p *Parser) appendData(commands *[]Command) {
	p.consume()
	first := true
	for {
		if p.token.Type == TokenEOF || p.token.Type == TokenLineBreak {
			if first {
				p.syntax("Expected at least one byte", p.token.Location)
			}
			return
		}
		first = false
		switch p.token.Type {
		case TokenIntLiteral:
			size := p.token.Size
			value := p.consumeIntValue()
			if size == 1 {
				*commands = append(*commands, NewContent(value))
			} else {
				*commands = append(*commands, NewContent(value>>8, value))
			}
		case TokenIdentifier:
			location := p.token.Location
			text := p.consumeText()
			*commands = append(*commands, LazyContent2{OpCode: 0, Text: text, Location: location})
		case TokenString:
			location := p.token.Location
			text := p.consumeText()
			if text == "" {
				p.syntax("Strings must not be empty.", location)
			}
			p.appendString(text, commands)
		case TokenLengthString:
			text := p.consumeText()
			units := utf16Encode(text)
			*commands = append(*commands, NewContent(len(units)))
			if len(units) > 0 {
				p.appendString(text, commands)
			}
		default:
			p.syntax("unsupported constant", p.token.Location)
		}
	}
}

func (p *Parser) parseOrg() Command {
	p.consume()
	location := p.token.Location
	return Org{Address: p.consumeIntLiteral(), Location: location}
}

func (p *Parser) handleRepeat(commands *[]Command) {
	p.consumeExpect(TokenRepeat)
	repeatTimes := p.consumeIntLiteral()
	if repeatTimes <= 1 {
		p.syntax("Expected a value > 1", p.token.Location)
	}
	start := len(*commands)
	p.parse(TokenEnd, commands)
	p.consumeExpect(TokenEnd)
	body := append([]Command(nil), (*commands)[start:]...)
	if repeatTimes > p.maxCommands || len(body) > 0 && repeatTimes > (p.maxCommands-start)/len(body) {
		p.syntax("program expands to too many commands", p.token.Location)
	}
	if len(body) == 0 {
		return
	}
	for repeat := 1; repeat < repeatTimes; repeat++ {
		*commands = append(*commands, body...)
	}
}

func (p *Parser) createBinary(highNibble int) Command {
	p.consume()
	if p.consumeIf(TokenAt) {
		dst := p.consumeRegister()
		p.consumeComma()
		if p.consumeIf(TokenHash) {
			return p.createBinaryImmediate(command(highNibble, 7), dst)
		}
		src := p.consumeRegister()
		return NewContent(command(highNibble, 5), src, dst)
	}
	dst := p.consumeRegister()
	p.consumeComma()
	if p.consumeIf(TokenHash) {
		return p.createBinaryImmediate(command(highNibble, 6), dst)
	}
	if isWorkRegister(dst) && p.consumeIf(TokenAt) {
		src := p.consumeWorkRegister()
		return NewContent(command(highNibble, 3), command(dst, src))
	}
	src := p.consumeRegister()
	if isWorkRegister(src) && isWorkRegister(dst) {
		return NewContent(command(highNibble, 2), command(dst, src))
	}
	return NewContent(command(highNibble, 4), src, dst)
}

func (p *Parser) createBinaryImmediate(opCode, dst int) Command {
	return p.immediate(func(value int) Command {
		return NewContent(opCode, dst, value)
	}, func(text string, location Location) Command {
		return LazyContent3{OpCode: opCode, Second: dst, Text: text, Location: location}
	})
}

func (p *Parser) createCall() Command {
	p.consume()
	if p.consumeIf(TokenAt) {
		return NewContent(0xd4, p.consumeRegisterPair())
	}
	if p.token.Type == TokenIntLiteral {
		address := p.consumeIntValue()
		return NewContent(0xd6, address>>8, address)
	}
	location := p.token.Location
	return LazyContent3{OpCode: 0xd6, Text: p.consumeIdentifier(), Location: location}
}

func (p *Parser) createJP() Command {
	p.consume()
	if p.consumeIf(TokenAt) {
		return NewContent(0x30, p.consumeRegisterPair())
	}
	if p.token.Type == TokenIntLiteral {
		address := p.consumeIntValue()
		return NewContent(0x8d, address>>8, address)
	}
	location := p.token.Location
	conditionOrTarget := p.consumeIdentifier()
	condition := jumpCondition(conditionOrTarget)
	var target string
	if condition >= 0 {
		p.consumeComma()
		if p.token.Type == TokenIntLiteral {
			address := p.consumeIntValue()
			return NewContent(condition+0x0d, address>>8, address)
		}
		location = p.token.Location
		target = p.consumeIdentifier()
	} else {
		condition = 0x80
		target = conditionOrTarget
	}
	return LazyContent3{OpCode: condition + 0x0d, Text: target, Location: location}
}

func (p *Parser) createJR() Command {
	p.consume()
	location := p.token.Location
	if p.token.Type == TokenIntLiteral {
		return AutoJumpAddress{JROpCode: 0x8b, Address: p.consumeIntValue()}
	}
	conditionOrTarget := p.consumeIdentifier()
	condition := jumpCondition(conditionOrTarget)
	var target string
	if condition >= 0 {
		p.consumeComma()
		location = p.token.Location
		if p.token.Type == TokenIntLiteral {
			address := p.consumeIntValue()
			return AutoJumpAddress{JROpCode: byte(condition + 0x0b), Address: address}
		}
		target = p.consumeIdentifier()
	} else {
		condition = 0x80
		target = conditionOrTarget
	}
	return AutoJumpString{JROpCode: byte(condition + 0x0b), Target: target, Location: location}
}

func (p *Parser) createDJNZ() Command {
	p.consume()
	register := p.consumeWorkRegister()
	p.consumeComma()
	location := p.token.Location
	return LazyContent2{OpCode: command(register, 0x0a), Text: p.consumeIdentifier(), Location: location}
}

func jumpCondition(text string) int {
	switch strings.ToLower(text) {
	case "f":
		return 0x00
	case "lt":
		return 0x10
	case "le":
		return 0x20
	case "ule":
		return 0x30
	case "ov":
		return 0x40
	case "mi":
		return 0x50
	case "eq", "z":
		return 0x60
	case "ult", "c":
		return 0x70
	case "ge":
		return 0x90
	case "gt":
		return 0xa0
	case "ugt":
		return 0xb0
	case "nov":
		return 0xc0
	case "pl":
		return 0xd0
	case "ne", "nz":
		return 0xe0
	case "uge", "nc":
		return 0xf0
	default:
		return -1
	}
}

func (p *Parser) createLD() Command {
	p.consume()
	if p.consumeIf(TokenAt) {
		dst := p.consumeRegister()
		p.consumeComma()
		if p.consumeIf(TokenHash) {
			return NewContent(0xe7, dst, p.consumeIntLiteral())
		}
		src := p.consumeRegister()
		if isWorkRegister(dst) && isWorkRegister(src) {
			return NewContent(0xf3, command(dst, src))
		}
		return NewContent(0xf5, src, dst)
	}
	dst := p.consumeRegister()
	if p.consumeIf(TokenLParen) {
		work := p.consumeWorkRegister()
		p.consumeExpect(TokenRParen)
		p.consumeComma()
		src := p.consumeWorkRegister()
		return NewContent(0xd7, command(src, work), dst)
	}
	p.consumeComma()
	if p.consumeIf(TokenHash) {
		return p.createLDImmediate(dst)
	}
	if p.consumeIf(TokenAt) {
		src := p.consumeRegister()
		if isWorkRegister(dst) && isWorkRegister(src) {
			return NewContent(0xe3, command(dst, src))
		}
		return NewContent(0xe5, src, dst)
	}
	src := p.consumeRegister()
	if isWorkRegister(dst) {
		if p.consumeIf(TokenLParen) {
			work := p.consumeWorkRegister()
			p.consumeExpect(TokenRParen)
			return NewContent(0xc7, command(dst, work), src)
		}
		return NewContent(command(dst, 8), src)
	}
	if isWorkRegister(src) {
		return NewContent(command(src, 9), dst)
	}
	return NewContent(0xe4, src, dst)
}

func (p *Parser) createLDImmediate(dst int) Command {
	return p.immediate(func(value int) Command {
		if isWorkRegister(dst) {
			return NewContent(command(dst, 0x0c), value)
		}
		return NewContent(0xe6, dst, value)
	}, func(text string, location Location) Command {
		if isWorkRegister(dst) {
			return LazyContent2{OpCode: command(dst, 0x0c), Text: text, Location: location}
		}
		return LazyContent3{OpCode: 0xe6, Second: dst, Text: text, Location: location}
	})
}

func (p *Parser) createLDX(highNibble int) Command {
	p.consume()
	if p.consumeIf(TokenAt) {
		destination := p.consumeWorkRegisterPair()
		p.consumeComma()
		source := p.consumeWorkRegister()
		return NewContent(command(highNibble+1, 2), command(source, destination))
	}
	destination := p.consumeWorkRegister()
	p.consumeComma()
	p.consumeExpect(TokenAt)
	source := p.consumeWorkRegisterPair()
	return NewContent(command(highNibble, 2), command(destination, source))
}

func (p *Parser) createLDXI(highNibble int) Command {
	p.consume()
	p.consumeExpect(TokenAt)
	if p.token.Type == TokenWorkRegisterPair {
		destination := p.consumeIntValue()
		p.consumeComma()
		p.consumeExpect(TokenAt)
		source := p.consumeWorkRegister()
		return NewContent(command(highNibble+1, 3), command(source, destination))
	}
	destination := p.consumeWorkRegister()
	p.consumeComma()
	p.consumeExpect(TokenAt)
	source := p.consumeWorkRegisterPair()
	return NewContent(command(highNibble, 3), command(destination, source))
}

func (p *Parser) unary(opCode int) Command {
	p.consume()
	if p.consumeIf(TokenAt) {
		opCode++
	}
	return NewContent(opCode, p.consumeRegister())
}

func (p *Parser) increment() Command {
	p.consume()
	if p.consumeIf(TokenAt) {
		return NewContent(0x21, p.consumeRegister())
	}
	register := p.consumeRegister()
	if isWorkRegister(register) {
		return NewContent(command(register, 0x0e))
	}
	return NewContent(0x20, register)
}

func (p *Parser) singleByte(opCode int) Command {
	p.consume()
	return NewContent(opCode)
}

func isWorkRegister(register int) bool { return register&0xf0 == workRegisterBase }

func (p *Parser) consumeWorkRegister() int { return p.consumeIntValueOf(TokenWorkRegister) }

func (p *Parser) consumeWorkRegisterPair() int { return p.consumeIntValueOf(TokenWorkRegisterPair) }

func (p *Parser) consumeIf(kind TokenType) bool {
	if p.token.Type != kind {
		return false
	}
	p.consume()
	return true
}

func (p *Parser) consumeComma() { p.consumeExpect(TokenComma) }

func (p *Parser) consumeExpect(kind TokenType) {
	if p.token.Type != kind {
		p.syntax(fmt.Sprintf("Expected %s but got %s", kind, p.token), p.token.Location)
	}
	p.consume()
}

func (p *Parser) consumeRegisterPair() int { return p.consumeRegisterKind(true) }

func (p *Parser) consumeRegister() int { return p.consumeRegisterKind(false) }

func (p *Parser) consumeRegisterKind(pair bool) int {
	if p.token.Type == TokenIntLiteral {
		return p.consumeIntValue()
	}
	if pair && p.token.Type == TokenWorkRegisterPair || !pair && p.token.Type == TokenWorkRegister {
		return workRegisterBase + (p.consumeIntValue() & 0xf)
	}
	return p.consumeConstant("expected register")
}

func (p *Parser) consumeConstant(message string) int {
	location := p.token.Location
	if p.token.Type == TokenIdentifier {
		name := p.consumeText()
		if value, ok := p.constants[name]; ok {
			return value
		}
	}
	p.syntax(message, location)
	return 0
}

func (p *Parser) createSRP() Command {
	p.consume()
	p.consumeExpect(TokenHash)
	return NewContent(0x31, p.consumeIntLiteral())
}

func (p *Parser) consumeIdentifier() string {
	if p.token.Type != TokenIdentifier {
		p.syntax(fmt.Sprintf("Expected IDENTIFIER but got %s", p.token), p.token.Location)
	}
	return p.consumeText()
}

func (p *Parser) consumeText() string {
	text := p.token.Text
	p.consume()
	return text
}

func (p *Parser) consumeIntLiteral() int {
	if p.token.Type == TokenIntLiteral {
		return p.consumeIntValue()
	}
	return p.consumeConstant("expected int literal or constant")
}

func (p *Parser) consumeIntValueOf(kind TokenType) int {
	if p.token.Type != kind {
		p.syntax(fmt.Sprintf("Expected %s but got %s", kind, p.token), p.token.Location)
	}
	return p.consumeIntValue()
}

func (p *Parser) consumeIntValue() int {
	value := p.token.IntValue
	p.consume()
	return value
}

func (p *Parser) consume() {
	for {
		token, err := p.lexer.Next()
		if err != nil {
			panic(parseFailure{err: err})
		}
		p.token = token
		if token.Type != TokenWhitespace && token.Type != TokenComment {
			return
		}
	}
}

func (p *Parser) appendString(text string, commands *[]Command) {
	units := utf16Encode(text)
	bytes := make([]byte, len(units))
	for i, unit := range units {
		bytes[i] = byte(unit)
	}
	*commands = append(*commands, Content{Bytes: bytes})
}

func (p *Parser) immediate(create func(int) Command, createLazy func(string, Location) Command) Command {
	if p.token.Type == TokenIntLiteral {
		return create(p.consumeIntValue())
	}
	location := p.token.Location
	identifier := p.consumeIdentifier()
	if p.consumeIf(TokenLParen) {
		identifier += "(" + p.consumeIdentifier() + ")"
		p.consumeExpect(TokenRParen)
	}
	if value, ok := p.constants[identifier]; ok {
		return create(value)
	}
	return createLazy(identifier, location)
}

func (p *Parser) syntax(message string, location Location) {
	panic(parseFailure{err: &SyntaxError{Message: message, Location: location}})
}

func command(highNibble, lowNibble int) int {
	return ((highNibble & 0xf) << 4) + (lowNibble & 0xf)
}

func utf16Encode(text string) []uint16 {
	return utf16.Encode([]rune(text))
}
