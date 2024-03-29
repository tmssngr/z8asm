# Z8ASM

Z8ASM is, as the name suggests, a (simple) assembler for the Zilog Z8 processor.

The main class is [Main](src/main/src/Main.java).
It accepts the `.asm` file as input and writes the compiled binary file to `output.bin`.

## Syntax

`.data <hex2|hex4|char|string>+` writes the specified bytes or characters directly to the memory.

`.org <hex4>` set the output location (only possible once).

`.repeat <number>` start a block which is repeated <number> times, `.end` it.

where
- `hex2`: a 2-digit hex value, e.g. `%0d`
- `hex4`: a 4-digit hex value, e.g. `%ABCD` (= `%AB %CD`)
- `char`: a char converted to US-ASCII, e.g. `'?'` (= `%2F`)
- `string`: a series of characters (US-ASCII), e.g. `"12"` (= `%31 %32`)

### Comments

Line comments start with a semicolon `;` or with C-like double slashes `//`.
Block comments are also C-like: they start with `/*` and end with the next `*/` (they can't be nested).

### Labels

Z8ASM supports global labels and local labels.
Global labels must not start with a dot (`.`) and are available globally.

Local labels start with a dot `.` and are valid only after a global label until the next global label occurs:
```
main:             // global label
     ld r0, #10
.1:               // local label valid after 'main'
     call sub1
     djnz r0, .1  // jump to local label above
     ret

sub1:             // another global label, the above '.1' is forgotten
     ld r1, #10
.1:               // hence we can define it again for `sub1`
     nop
     djnz r1, .1  // jump to the '.1' label inside 'sub1'
     ret

```

## Examples

Please see directory [src/main/examples](src/main/examples) for example files used to build internal ROM, ROM and video ROM for the JU+TE computer (East-German DIY computer from 1988).
