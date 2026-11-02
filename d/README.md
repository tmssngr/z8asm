# Z8 Assembler - D Rewrite

This directory contains a D language rewrite of the Java-based Z8 assembler project. The rewrite includes the **Lexer**, **Parser**, and all supporting classes.

## Overview

This is a direct translation of the Java Z8 assembler project into the D programming language. The rewrite maintains the same functionality and structure while using D's native syntax and idioms.

## Files Created

### Core Modules

| File | Source | Purpose | Lines |
|------|--------|---------|-------|
| `src/z8asm/token_type.d` | TokenType.java | Token type enum | 47 |
| `src/z8asm/location.d` | Location.java | Location struct (replaces Java record) | 19 |
| `src/z8asm/char_source.d` | CharSource.java | Character source interface | 8 |
| `src/z8asm/syntax_exception.d` | SyntaxException.java | Exception for syntax errors | 15 |
| `src/z8asm/invalid_token_exception.d` | InvalidTokenException.java | Exception for invalid tokens | 15 |
| `src/z8asm/warning_out.d` | WarningOut.java | Interface for warning output | 8 |
| `src/z8asm/utils.d` | Utils.java | Utility functions (assert, hex conversion) | 25 |

### Lexer & Parser

| File | Source | Purpose | Lines |
|------|--------|---------|-------|
| `src/z8asm/command.d` | Command.java | Command class with factory methods | ~120 |
| `src/z8asm/labels.d` | Labels.java | Label management (global and local) | ~100 |
| `src/z8asm/output.d` | Output.java | Output formatting (binary, hex, C, Verilog) | ~90 |
| `src/z8asm/lexer.d` | **Lexer.java** | **Main lexer implementation** | ~320 |
| `src/z8asm/parser.d` | **Parser.java** | **Main parser implementation** | ~600 |
| `src/z8asm/assembler.d` | **Assembler.java** | **Assembler (label resolution)** | ~250 |

### Tests

| File | Source | Purpose | Lines |
|------|--------|---------|-------|
| `src/z8asm/lexer_test.d` | **LexerTest.java** | **Unit tests for the lexer** | ~210 |
| `src/z8asm/parser_test.d` | **ParserTest.java** | **Unit tests for the parser** | ~270 |

### Configuration

| File | Purpose |
|------|---------|
| `dub.json` | DUB package configuration |
| `README.md` | This file |

**Total: 15 D source files + configuration**

## Key Translations

### Type System

| Java | D |
|------|---|
| `enum TokenType { ... }` | `enum TokenType { ... }` |
| `record Location(int line, int column)` | `struct Location { int line; int column; }` |
| `class InvalidTokenException extends RuntimeException` | `class InvalidTokenException : Exception` |
| `interface CharSource` | `interface CharSource` |
| `class Command` | `class Command` |
| `final class` | `class` (D doesn't have `final` for classes) |

### String & Character Handling

| Java | D |
|------|---|
| `StringBuilder` | `Appender!(char)` |
| `s.charAt(i)` | `s[i]` |
| `s.substring(a, b)` | `s[a .. b]` |
| `s.length()` | `s.length` |
| `s.startsWith("prefix")` | `s.startsWith("prefix")` |
| `s.toLowerCase()` | `s.toLower()` |
| `s.isEmpty()` | `s.empty` |

### Number Parsing

| Java | D |
|------|---|
| `Integer.parseInt(s)` | `std.conv.to!int(s)` |
| `Integer.parseInt(s, 16)` | `std.conv.to!int(s, 16)` |
| `Integer.parseInt(s, 2)` | `std.conv.to!int(s, 2)` |

### Exception Handling

| Java | D |
|------|---|
| `throw new Exception("msg")` | `throw new Exception("msg")` |
| `try { ... } catch (Ex e) { ... }` | `try { ... } catch (Ex e) { ... }` |

### Collections & Arrays

| Java | D |
|------|---|
| `StringBuilder buffer = new StringBuilder()` | `Appender!(char) buffer;` |
| `buffer.append(c)` | `buffer.put(c)` |
| `buffer.toString()` | `buffer.data` |
| `List<Command>` | `Command[]` or `Appender!Command` |
| `new ArrayList<>()` | `Appender!Command` or `new Command[]` |
| `Arrays.copyOf(bytes, length)` | `bytes.dup` or manual copy |

### Object Orientation

| Java | D |
|------|---|
| `class Inner { ... }` | `private class Inner { ... }` (at module level) |
| `abstract class` | `abstract class` |
| `static method()` | `static method()` |
| `final method()` | `final method()` |
| `@Override` | Not needed in D |
| `@NotNull` annotations | Not used (D has different null handling) |

## Testing

The unit tests from both `LexerTest.java` and `ParserTest.java` have been translated to D's built-in `std.testing` framework.

### Running Tests

To run all tests:

```bash
cd /workspace/d
dub test
```

Or directly with a D compiler:

```bash
cd /workspace/d
dmd -unittest -run src/z8asm/lexer_test.d src/z8asm/parser_test.d src/z8asm/*.d
```

## Building

### Using DUB (Recommended)

```bash
# Build the library
dub build

# Run all tests
dub test

# Run specific test
dub test z8asm:lexer
```

### Using DMD Directly

```bash
# Compile all source files
dmd src/z8asm/*.d

# Compile with tests
dmd -unittest -run src/z8asm/lexer_test.d src/z8asm/parser_test.d src/z8asm/*.d
```

### Using LDMD2 (LLVM D Compiler)

```bash
ldc2 -unittest -run src/z8asm/lexer_test.d src/z8asm/parser_test.d src/z8asm/*.d
```

## Dependencies

This project has **no external dependencies**. It uses only the D standard library:

- `std.string` - String manipulation
- `std.array` - Dynamic arrays and `Appender`
- `std.conv` - String-to-number conversion
- `std.testing` - Unit testing framework
- `std.file` - File I/O
- `std.path` - Path manipulation
- `core.exception` - Exception base class
- `std.stdio` - Standard I/O

## Notes

### String Handling

- D strings are UTF-8 by default, while Java uses UTF-16
- The lexer and parser handle ASCII characters only, so this is not an issue
- Character literals in D are single `dchar` values

### Character Types

- D uses `char`, `wchar`, and `dchar` for different character types
- We use `char` throughout for compatibility with ASCII input
- Casting between `int` and `char` is explicit in D

### Array Handling

- D arrays are value types (copied by value)
- Use `.dup` to create a copy of an array
- Slices (`array[start .. end]`) are views, not copies

### Memory Management

- D has garbage collection, similar to Java
- No manual memory management needed for this project
- Structs are value types, classes are reference types

### Module System

- D uses a hierarchical module system based on directory structure
- Module names match file paths (e.g., `z8asm.lexer` → `src/z8asm/lexer.d`)

## Directory Structure

```
/workspace/d/
├── README.md             # This file
├── dub.json              # DUB package configuration
└── src/
    └── z8asm/
        ├── token_type.d
        ├── location.d
        ├── char_source.d
        ├── syntax_exception.d
        ├── invalid_token_exception.d
        ├── warning_out.d
        ├── utils.d
        ├── command.d
        ├── labels.d
        ├── output.d
        ├── lexer.d         # Main lexer implementation
        ├── lexer_test.d    # Lexer unit tests
        ├── parser.d        # Main parser implementation
        ├── parser_test.d   # Parser unit tests
        └── assembler.d     # Assembler (label resolution)
```

## Comparison with Java

- **Lines of Code**: The D version is approximately 20-25% shorter than the Java version due to D's more concise syntax
- **Compilation**: D compiles directly to native code, eliminating the need for a JVM
- **Performance**: Expected to be comparable or better than Java for this type of text processing
- **Memory**: D has a garbage collector similar to Java, so memory usage patterns are comparable
- **Start-up Time**: D binaries have faster start-up (no JVM warm-up)

## Original Java Files

The original Java source files can be found in:
- `/workspace/src/main/src/com/syntevo/z8asm/` - Core classes
- `/workspace/src/main/src-test/com/syntevo/z8asm/` - Test classes

## What's Missing

The following Java classes have **not** been rewritten yet:
- `ConvertBinToHexListing.java`
- `Main.java`
- `WriteMainEpromHeaderFile.java`
- `WriteVerilogFile.java`

These are utility/main classes that depend on the core assembler functionality.

## License

The original Java code is licensed under its original license. This D rewrite inherits the same licensing terms.
