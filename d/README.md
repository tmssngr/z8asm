# Z8 Assembler in D

This is a D port of the Java Zilog Z8 assembler. The Java entry point is
[`Main.java`](../src/main/src/Main.java); the D equivalent is
[`src/main.d`](src/main.d).

Both programs accept one assembly file and write its assembled bytes to
`output.bin` in the current directory. `.org` sets the address used to resolve
labels; it does not add leading padding to the binary. See the
[project README](../README.md) for assembly syntax.

Build and run with a D compiler (DMD or LDC), DUB, and the compiler's native
linker toolchain installed:

```sh
cd d
dub build
dub run -- ../src/main/examples/es4.5.asm
```

Run the D unit tests:

```sh
cd d
dub test
```

The tests cover lexing and instruction parsing. Additional Java utilities
`ConvertBinToHexListing`, `WriteMainEpromHeaderFile`, and `WriteVerilogFile` do
not have D command-line equivalents; their output formatting is available in
`z8asm.output`.
