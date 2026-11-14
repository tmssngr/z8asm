package z8asm

import (
	"errors"
	"testing"
)

type tokenExpectation struct {
	typeOf TokenType
	text   string
	value  int
	size   int
	line   int
	column int
}

func assertTokens(t *testing.T, source string, expected []tokenExpectation) {
	t.Helper()
	lexer := NewLexer(source)
	index := 0
	for {
		token, err := lexer.Next()
		if err != nil {
			t.Fatalf("Next() returned unexpected error: %v", err)
		}
		if token.Type == TokenWhitespace {
			continue
		}
		if index >= len(expected) {
			t.Fatalf("unexpected token %d: %#v", index, token)
		}
		want := expected[index]
		if token.Type != want.typeOf || token.Text != want.text || token.IntValue != want.value || token.Size != want.size || token.Location != (Location{Line: want.line, Column: want.column}) {
			t.Errorf("token %d = %#v, want type=%s text=%q value=%d size=%d at %d:%d", index, token, want.typeOf, want.text, want.value, want.size, want.line, want.column)
		}
		index++
		if token.Type == TokenEOF {
			break
		}
	}
	if index != len(expected) {
		t.Errorf("got %d tokens, want %d", index, len(expected))
	}
}

func TestLexerLegacyBasic(t *testing.T) {
	assertTokens(t, "main:\n  ld r0, #10\nmain: ; duplicate\n  call %1000\n  djnz r0, main\n  ret", []tokenExpectation{
		{TokenLabel, "main", 0, 0, 0, 0},
		{TokenLineBreak, "\n", 0, 0, 0, 5},
		{TokenLD, "ld", 0, 0, 1, 2},
		{TokenWorkRegister, "", 0, 0, 1, 5},
		{TokenComma, "", 0, 0, 1, 7},
		{TokenHash, "", 0, 0, 1, 9},
		{TokenIntLiteral, "", 10, 2, 1, 10},
		{TokenLineBreak, "\n", 0, 0, 1, 12},
		{TokenLabel, "main", 0, 0, 2, 0},
		{TokenComment, "; duplicate", 0, 0, 2, 6},
		{TokenLineBreak, "\n", 0, 0, 2, 17},
		{TokenCALL, "call", 0, 0, 3, 2},
		{TokenIntLiteral, "", 0x1000, 2, 3, 7},
		{TokenLineBreak, "\n", 0, 0, 3, 12},
		{TokenDJNZ, "djnz", 0, 0, 4, 2},
		{TokenWorkRegister, "", 0, 0, 4, 7},
		{TokenComma, "", 0, 0, 4, 9},
		{TokenIdentifier, "main", 0, 0, 4, 11},
		{TokenLineBreak, "\n", 0, 0, 4, 15},
		{TokenRET, "ret", 0, 0, 5, 2},
		{TokenEOF, "", 0, 0, 5, 5},
	})

	assertTokens(t, "TM R3, #0b0000_0100", []tokenExpectation{
		{TokenTM, "TM", 0, 0, 0, 0},
		{TokenWorkRegister, "", 3, 0, 0, 3},
		{TokenComma, "", 0, 0, 0, 5},
		{TokenHash, "", 0, 0, 0, 7},
		{TokenIntLiteral, "", 4, 1, 0, 8},
		{TokenEOF, "", 0, 0, 0, 19},
	})

	assertTokens(t, "LD %20, #'0'", []tokenExpectation{
		{TokenLD, "LD", 0, 0, 0, 0},
		{TokenIntLiteral, "", 0x20, 1, 0, 3},
		{TokenComma, "", 0, 0, 0, 6},
		{TokenHash, "", 0, 0, 0, 8},
		{TokenIntLiteral, "", '0', 1, 0, 9},
		{TokenEOF, "", 0, 0, 0, 12},
	})

	assertTokens(t, `.data L"foo bar" "bazz" L"" "\"\x0d" '\x7f'`, []tokenExpectation{
		{TokenData, ".data", 0, 0, 0, 0},
		{TokenLengthString, "foo bar", 0, 0, 0, 6},
		{TokenString, "bazz", 0, 0, 0, 17},
		{TokenLengthString, "", 0, 0, 0, 24},
		{TokenString, "\"\r", 0, 0, 0, 28},
		{TokenIntLiteral, "", 0x7f, 1, 0, 37},
		{TokenEOF, "", 0, 0, 0, 43},
	})

	assertTokens(t, `.data "\0"`, []tokenExpectation{
		{TokenData, ".data", 0, 0, 0, 0},
		{TokenString, "\x00", 0, 0, 0, 6},
		{TokenEOF, "", 0, 0, 0, 10},
	})
}

func TestLexerLegacyInvalidLiterals(t *testing.T) {
	cases := []struct {
		source  string
		message string
		line    int
		column  int
	}{
		{"''", "An empty string can only be done with L\"\"", 0, 0},
		{" 'ab'", "Invalid char literal", 0, 1},
		{" '", "A char literal must end with a quote", 0, 1},
		{" \"", "String must end with a double-quote", 0, 2},
		{"0b12", "Invalid number", 0, 0},
		{".data \"\\x\"", "Invalid char escape", 0, 7},
	}
	for _, test := range cases {
		t.Run(test.source, func(t *testing.T) {
			lexer := NewLexer(test.source)
			var err error
			for {
				var token Token
				token, err = lexer.Next()
				if err != nil || token.Type == TokenEOF {
					break
				}
			}
			var invalid *InvalidTokenError
			if !errors.As(err, &invalid) {
				t.Fatalf("Next() error = %v, want InvalidTokenError", err)
			}
			if invalid.Message != test.message || invalid.Location != (Location{Line: test.line, Column: test.column}) {
				t.Errorf("error = (%q, %v), want (%q, %d:%d)", invalid.Message, invalid.Location, test.message, test.line, test.column)
			}
		})
	}

	if _, err := NewLexer("L" + string('"') + repeatRune('x', 256) + string('"')).Next(); err == nil {
		t.Fatal("256-character length string accepted")
	}
	if token, err := NewLexer("L" + string('"') + repeatRune('x', 255) + string('"')).Next(); err != nil || token.Type != TokenLengthString {
		t.Fatalf("255-character length string = (%v, %v), want a length string", token, err)
	}
}

func TestLexerLegacyComments(t *testing.T) {
	assertTokens(t, "// line\n; comment\nfoo /* bar\nbazz* / */ blupp", []tokenExpectation{
		{TokenComment, "// line", 0, 0, 0, 0},
		{TokenLineBreak, "\n", 0, 0, 0, 7},
		{TokenComment, "; comment", 0, 0, 1, 0},
		{TokenLineBreak, "\n", 0, 0, 1, 9},
		{TokenIdentifier, "foo", 0, 0, 2, 0},
		{TokenComment, "/* bar\nbazz* / */", 0, 0, 2, 4},
		{TokenIdentifier, "blupp", 0, 0, 3, 11},
		{TokenEOF, "", 0, 0, 3, 16},
	})
	assertTokens(t, "a\r\nb", []tokenExpectation{
		{TokenIdentifier, "a", 0, 0, 0, 0},
		{TokenLineBreak, "\r\n", 0, 0, 0, 1},
		{TokenIdentifier, "b", 0, 0, 1, 0},
		{TokenEOF, "", 0, 0, 1, 1},
	})
	assertTokens(t, "𐐀 nop", []tokenExpectation{
		{TokenIdentifier, "𐐀", 0, 0, 0, 0},
		{TokenNOP, "nop", 0, 0, 0, 3},
		{TokenEOF, "", 0, 0, 0, 6},
	})
}

func repeatRune(value rune, count int) string {
	var out = make([]rune, count)
	for i := range out {
		out[i] = value
	}
	return string(out)
}
