package z8asm

import "fmt"

type symbol struct {
	name       string
	address    int
	location   Location
	used       bool
	locals     map[string]*symbol
	localOrder []string
}

type symbolTable struct {
	globals map[string]*symbol
	ordered []*symbol
	current string
}

func newSymbolTable() *symbolTable {
	return &symbolTable{globals: make(map[string]*symbol)}
}

func (s *symbolTable) add(name string, address int, location Location) error {
	if isLocalLabel(name) {
		global := s.globals[s.current]
		if global == nil {
			return &SyntaxError{Message: "A local label only can be defined after a global label", Location: location}
		}
		if previous := global.locals[name]; previous != nil {
			return &SyntaxError{Message: fmt.Sprintf("A local label %s is already defined at %s", name, previous.location), Location: location}
		}
		global.locals[name] = &symbol{name: name, address: address, location: location}
		global.localOrder = append(global.localOrder, name)
		return nil
	}
	if previous := s.globals[name]; previous != nil {
		return &SyntaxError{Message: fmt.Sprintf("A label %s is already defined at %s", name, previous.location), Location: location}
	}
	global := &symbol{name: name, address: address, location: location, locals: make(map[string]*symbol)}
	s.globals[name] = global
	s.ordered = append(s.ordered, global)
	s.current = name
	return nil
}

func (s *symbolTable) update(name string, address int) (bool, error) {
	var target *symbol
	if isLocalLabel(name) {
		global := s.globals[s.current]
		if global == nil {
			return false, fmt.Errorf("internal error: local label %q has no global scope", name)
		}
		target = global.locals[name]
	} else {
		target = s.globals[name]
		s.current = name
	}
	if target == nil {
		return false, fmt.Errorf("internal error: label %q is not defined", name)
	}
	if target.address == address {
		return false, nil
	}
	target.address = address
	return true, nil
}

func (s *symbolTable) finishedInitialization() { 
	s.current = "" 
}

func (s *symbolTable) processing(name string) {
	if !isLocalLabel(name) {
		s.current = name
	}
}

func (s *symbolTable) resolve(name string, location Location) (int, error) {
	var target *symbol
	if isLocalLabel(name) {
		if global := s.globals[s.current]; global != nil {
			target = global.locals[name]
		}
	} else {
		target = s.globals[name]
	}
	if target == nil {
		return 0, &SyntaxError{Message: fmt.Sprintf("No target '%s' found", name), Location: location}
	}
	target.used = true
	return target.address, nil
}

func (s *symbolTable) mapping() map[string]int {
	out := make(map[string]int, len(s.globals))
	for name, label := range s.globals {
		out[name] = label.address
	}
	return out
}

func (s *symbolTable) reportUnused() []string {
	var warnings []string
	for _, global := range s.ordered {
		if !global.used {
			warnings = append(warnings, fmt.Sprintf("%s: Label %s is unused", global.location, global.name))
		}
		for _, localName := range global.localOrder {
			local := global.locals[localName]
			if !local.used {
				warnings = append(warnings, fmt.Sprintf("%s: Label %s/%s is unused", local.location, global.name, localName))
			}
		}
	}
	return warnings
}

func isLocalLabel(name string) bool {
	return len(name) > 0 && name[0] == '.'
}
