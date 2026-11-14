package z8asm

import "fmt"

// Location is a zero-based source position. Its string form is one-based for
// display, matching the assembler's existing diagnostics.
type Location struct {
	Line   int
	Column int
}

var DummyLocation = Location{Line: -1, Column: -1}

func (l Location) String() string {
	return fmt.Sprintf("%d:%d", l.Line+1, l.Column+1)
}
