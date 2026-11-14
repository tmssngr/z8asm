package z8asm

import (
	"fmt"
	"io"
	"sort"
	"strings"
)

// WarningOut receives non-fatal assembler diagnostics.
type WarningOut func(string)

// Assembly is the resolved machine code and symbol table for one source file.
type Assembly struct {
	Commands []Command
	Labels   map[string]int
	Org      int
	Warnings []string
}

// Assemble lays out commands, widens out-of-range JR instructions, resolves
// labels, and returns the final bytes as Content commands.
func Assemble(commands []Command, warningOut WarningOut) (*Assembly, error) {
	resolved := append([]Command(nil), commands...)
	symbols, org, err := determineLabels(resolved)
	if err != nil {
		return nil, err
	}
	if err := resolveAutoJumps(resolved, symbols); err != nil {
		return nil, err
	}
	resolved, warnings, err := resolveLazyCommands(resolved, symbols)
	if err != nil {
		return nil, err
	}
	warnings = append(warnings, symbols.reportUnused()...)
	result := &Assembly{
		Commands: resolved,
		Labels:   symbols.mapping(),
		Org:      org,
		Warnings: warnings,
	}
	if warningOut != nil {
		for _, warning := range warnings {
			warningOut(warning)
		}
	}
	return result, nil
}

func (a *Assembly) WriteLabels(writer io.Writer) error {
	names := make([]string, 0, len(a.Labels))
	for name := range a.Labels {
		names = append(names, name)
	}
	sort.Slice(names, func(i, j int) bool {
		left, right := a.Labels[names[i]], a.Labels[names[j]]
		if left != right {
			return left < right
		}
		return names[i] < names[j]
	})
	for _, name := range names {
		if _, err := fmt.Fprintf(writer, "%s %s\n", toHex(a.Labels[name], 4), name); err != nil {
			return err
		}
	}
	return nil
}

func determineLabels(commands []Command) (*symbolTable, int, error) {
	symbols := newSymbolTable()
	first := true
	org, pc := 0, 0
	for _, command := range commands {
		if first {
			first = false
			if origin, ok := command.(Org); ok {
				if origin.Address < 0 || origin.Address > 0xffff {
					return nil, 0, &SyntaxError{Message: "origin is outside the 16-bit address space", Location: origin.Location}
				}
				org, pc = origin.Address, origin.Address
				continue
			}
		}
		switch current := command.(type) {
		case Align:
			padding, err := alignmentOffset(current.Alignment, pc)
			if err != nil {
				return nil, 0, err
			}
			pc += padding
		case AutoJumpAddress, AutoJumpString:
			pc += 2
		case Content:
			pc += current.Size()
		case LabelCommand:
			if pc < 0 || pc > 0xffff {
				return nil, 0, &SyntaxError{Message: "label is outside the 16-bit address space", Location: current.Location}
			}
			if err := symbols.add(current.Name, pc, current.Location); err != nil {
				return nil, 0, err
			}
		case LazyContent2:
			pc += 2
		case LazyContent3:
			pc += 3
		case Org:
			return nil, 0, &SyntaxError{Message: ".org is only allowed before any content or label", Location: current.Location}
		default:
			return nil, 0, fmt.Errorf("internal error: unsupported command %T", command)
		}
		if pc > 0x10000 {
			return nil, 0, fmt.Errorf("assembled image exceeds the 16-bit address space")
		}
	}
	symbols.finishedInitialization()
	return symbols, org, nil
}

func resolveAutoJumps(commands []Command, symbols *symbolTable) error {
	maxRounds := len(commands) + 1
	for round := 0; round <= maxRounds; round++ {
		changed, err := resolveAutoJumpsPass(commands, symbols, false)
		if err != nil {
			return err
		}
		if !changed {
			_, err = resolveAutoJumpsPass(commands, symbols, true)
			return err
		}
	}
	return fmt.Errorf("internal error: automatic jump layout did not converge")
}

func resolveAutoJumpsPass(commands []Command, symbols *symbolTable, finalize bool) (bool, error) {
	pc := 0
	changed := false
	for index, command := range commands {
		switch current := command.(type) {
		case Align:
			padding, err := alignmentOffset(current.Alignment, pc)
			if err != nil {
				return false, err
			}
			pc += padding
		case AutoJumpAddress:
			if current.Address < 0 || current.Address > 0xffff {
				return false, fmt.Errorf("jump target %d is outside the 16-bit address space", current.Address)
			}
			pc += 2
			relative := current.Address - pc
			higherNibble := int(current.JROpCode) & 0xf0
			if !isValidRelative(relative) {
				commands[index] = NewContent(higherNibble+0x0d, current.Address>>8, current.Address&0xff)
				pc++
				changed = true
			} else if finalize {
				commands[index] = NewContent(higherNibble+0x0b, relative)
			}
		case AutoJumpString:
			pc += 2
			address, err := symbols.resolve(current.Target, current.Location)
			if err != nil {
				return false, err
			}
			relative := address - pc
			higherNibble := int(current.JROpCode) & 0xf0
			if !isValidRelative(relative) {
				commands[index] = LazyContent3{OpCode: higherNibble + 0x0d, Text: current.Target, Location: current.Location}
				pc++
				changed = true
			} else if finalize {
				commands[index] = LazyContent2{OpCode: higherNibble + 0x0b, Text: current.Target, Location: current.Location}
			}
		case Content:
			pc += current.Size()
		case LabelCommand:
			labelChanged, err := symbols.update(current.Name, pc)
			if err != nil {
				return false, err
			}
			changed = changed || labelChanged
		case LazyContent2:
			pc += 2
		case LazyContent3:
			pc += 3
		case Org:
			pc = current.Address
		}
		if pc < 0 || pc > 0x10000 {
			return false, fmt.Errorf("assembled image exceeds the 16-bit address space")
		}
	}
	symbols.finishedInitialization()
	return changed, nil
}

func alignmentOffset(alignment, pc int) (int, error) {
	if alignment < 2 || alignment&(alignment-1) != 0 {
		return 0, fmt.Errorf("invalid alignment %d", alignment)
	}
	maximum := pc + alignment - 1
	remainder := maximum % alignment
	return maximum - remainder - pc, nil
}

func resolveLazyCommands(commands []Command, symbols *symbolTable) ([]Command, []string, error) {
	warnings := make([]string, 0)
	pc := 0
	for index, command := range commands {
		switch current := command.(type) {
		case Align:
			padding, err := alignmentOffset(current.Alignment, pc)
			if err != nil {
				return nil, nil, err
			}
			if padding > 0 {
				bytes := make([]byte, padding)
				for i := range bytes {
					bytes[i] = current.FillByte
				}
				commands[index] = Content{Bytes: bytes}
				pc += padding
			} else {
				commands[index] = nil
			}
		case Content:
			pc += current.Size()
		case LabelCommand:
			symbols.processing(current.Name)
			commands[index] = nil
		case LazyContent2:
			resolved, err := resolveLazy2(current, pc, symbols)
			if err != nil {
				return nil, nil, err
			}
			commands[index] = resolved
			pc += resolved.Size()
		case LazyContent3:
			resolved, advisory, err := resolveLazy3(current, pc, symbols)
			if err != nil {
				return nil, nil, err
			}
			if advisory != "" {
				warnings = append(warnings, advisory)
			}
			commands[index] = resolved
			pc += resolved.Size()
		case Org:
			pc = current.Address
			commands[index] = nil
		case AutoJumpAddress, AutoJumpString:
			return nil, nil, fmt.Errorf("internal error: unresolved automatic jump %T", command)
		default:
			return nil, nil, fmt.Errorf("internal error: unsupported command %T", command)
		}
		if pc < 0 || pc > 0x10000 {
			return nil, nil, fmt.Errorf("assembled image exceeds the 16-bit address space")
		}
	}
	compact := commands[:0]
	for _, command := range commands {
		if command != nil {
			compact = append(compact, command)
		}
	}
	copy(commands, compact)
	commands = commands[:len(compact)]
	return commands[:len(compact)], warnings, nil
}

func resolveLazy2(command LazyContent2, pc int, symbols *symbolTable) (Content, error) {
	opCode := command.OpCode
	lowerNibble := opCode & 0x0f
	if lowerNibble == 0x0a || lowerNibble == 0x0b {
		address, err := symbols.resolve(command.Text, command.Location)
		if err != nil {
			return Content{}, err
		}
		relative := address - pc - 2
		if !isValidRelative(relative) {
			if lowerNibble != 0x0a {
				return Content{}, &SyntaxError{Message: fmt.Sprintf("Target '%s' too far away (%d)", command.Text, relative), Location: command.Location}
			}
			return Content{}, fmt.Errorf("internal error: unresolved long jump")
		}
		return NewContent(opCode, relative), nil
	}
	if lowerNibble == 0x0c {
		defaultHigh := opCode&0x10 == 0
		value, err := resolveLabelHighOrLow(defaultHigh, command.Text, command.Location, symbols)
		if err != nil {
			return Content{}, err
		}
		return NewContent(opCode, value), nil
	}
	if opCode == 0 {
		address, err := symbols.resolve(command.Text, command.Location)
		if err != nil {
			return Content{}, err
		}
		return NewContent(address>>8, address), nil
	}
	return Content{}, fmt.Errorf("internal error: unsupported lazy command %#02x", opCode)
}

func resolveLazy3(command LazyContent3, pc int, symbols *symbolTable) (Content, string, error) {
	opCode := command.OpCode
	lowerNibble := opCode & 0x0f
	if lowerNibble == 0x0d {
		address, err := symbols.resolve(command.Text, command.Location)
		if err != nil {
			return Content{}, "", err
		}
		warning := ""
		if isValidRelative(address - pc - 3) {
			warning = fmt.Sprintf("%s: jp could be jr", command.Location)
		}
		return NewContent(opCode, address>>8, address), warning, nil
	}
	higherNibble := opCode & 0xf0
	if higherNibble <= 0x70 && (lowerNibble == 0x06 || lowerNibble == 0x07) {
		defaultHigh := command.Second&1 == 0
		value, err := resolveLabelHighOrLow(defaultHigh, command.Text, command.Location, symbols)
		if err != nil {
			return Content{}, "", err
		}
		return NewContent(opCode, command.Second, value), "", nil
	}
	if opCode == 0xd6 {
		address, err := symbols.resolve(command.Text, command.Location)
		if err != nil {
			return Content{}, "", err
		}
		return NewContent(opCode, address>>8, address), "", nil
	}
	if opCode == 0xe6 {
		defaultHigh := command.Second&1 == 0
		value, err := resolveLabelHighOrLow(defaultHigh, command.Text, command.Location, symbols)
		if err != nil {
			return Content{}, "", err
		}
		return NewContent(opCode, command.Second, value), "", nil
	}
	return Content{}, "", fmt.Errorf("internal error: unsupported lazy command %#02x", opCode)
}

func resolveLabelHighOrLow(high bool, text string, location Location, symbols *symbolTable) (int, error) {
	lower := strings.ToLower(text)
	if strings.HasSuffix(lower, ")") {
		if strings.HasPrefix(lower, "lo(") {
			text = text[3 : len(text)-1]
			high = false
		} else if strings.HasPrefix(lower, "hi(") {
			text = text[3 : len(text)-1]
			high = true
		}
	}
	address, err := symbols.resolve(text, location)
	if err != nil {
		return 0, err
	}
	if high {
		return address >> 8, nil
	}
	return address, nil
}

func isValidRelative(relative int) bool { return relative >= -128 && relative <= 127 }
