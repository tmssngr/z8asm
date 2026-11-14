package z8asm

import (
	"fmt"
	"io"
	"strings"
)

// Output contains the bytes emitted by an assembled source file.
type Output struct{ Bytes []byte }

func CreateOutput(commands []Command) (Output, error) {
	var out Output
	for _, command := range commands {
		content, ok := command.(Content)
		if !ok {
			return Output{}, fmt.Errorf("internal error: output contains unresolved command %T", command)
		}
		out.Bytes = append(out.Bytes, content.Bytes...)
		if len(out.Bytes) > 0x10000 {
			return Output{}, fmt.Errorf("assembled image exceeds the 16-bit address space")
		}
	}
	return out, nil
}

func (o Output) WriteBinary(writer io.Writer) error {
	n, err := writer.Write(o.Bytes)
	if err == nil && n != len(o.Bytes) {
		return io.ErrShortWrite
	}
	return err
}

func (o Output) ASCII() string {
	var out strings.Builder
	for i, value := range o.Bytes {
		if i%16 == 0 {
			if i > 0 {
				out.WriteByte('\n')
			}
			out.WriteString(toHex(i, 4))
		}
		if i%8 == 0 {
			out.WriteByte(' ')
		}
		fmt.Fprintf(&out, " %02x", value)
	}
	if len(o.Bytes) > 0 {
		out.WriteByte('\n')
	}
	return out.String()
}

func (o Output) WriteASCII(writer io.Writer) error {
	text := o.ASCII()
	n, err := io.WriteString(writer, text)
	if err == nil && n != len(text) {
		return io.ErrShortWrite
	}
	return err
}

func toHex(value, digits int) string {
	const hex = "0123456789abcdef"
	result := make([]byte, digits)
	for index := digits - 1; index >= 0; index-- {
		result[index] = hex[value&0xf]
		value >>= 4
	}
	return string(result)
}
