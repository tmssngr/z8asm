package z8asm

// Command is one parsed instruction, directive, symbol, or unresolved value.
type Command interface{ commandNode() }

type Align struct {
	Alignment int
	FillByte  byte
}

func (Align) commandNode() {}

type AutoJumpString struct {
	JROpCode byte
	Target   string
	Location Location
}

func (AutoJumpString) commandNode() {}

type AutoJumpAddress struct {
	JROpCode byte
	Address  int
}

func (AutoJumpAddress) commandNode() {}

type Content struct{ Bytes []byte }

func (Content) commandNode() {}

func NewContent(values ...int) Content {
	bytes := make([]byte, len(values))
	for i, value := range values {
		bytes[i] = byte(value)
	}
	return Content{Bytes: bytes}
}

func NewContentBytes(values []byte) Content {
	return Content{Bytes: append([]byte(nil), values...)}
}

func (c Content) Size() int { return len(c.Bytes) }

type LabelCommand struct {
	Name     string
	Location Location
}

func (LabelCommand) commandNode() {}

type LazyContent2 struct {
	OpCode   int
	Text     string
	Location Location
}

func (LazyContent2) commandNode() {}

type LazyContent3 struct {
	OpCode   int
	Second   int
	Text     string
	Location Location
}

func (LazyContent3) commandNode() {}

type Org struct {
	Address  int
	Location Location
}

func (Org) commandNode() {}
