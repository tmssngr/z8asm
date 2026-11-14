package z8asm

import "fmt"

// SyntaxError reports an invalid assembly statement or operand.
type SyntaxError struct {
	Message  string
	Location Location
}

func (e *SyntaxError) Error() string {
	return fmt.Sprintf("%s %s", e.Location, e.Message)
}

// InvalidTokenError reports a malformed token or literal.
type InvalidTokenError struct {
	Message  string
	Location Location
}

func (e *InvalidTokenError) Error() string {
	return fmt.Sprintf("%s %s", e.Location, e.Message)
}
