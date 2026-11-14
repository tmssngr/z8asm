# Verification

The D executable is configured in `dub.json` and starts at `src/main.d`.

Run `dub build` and `dub test` from this directory with a D compiler and its
native linker toolchain installed. Compilation alone does not establish
equivalence with the Java assembler; the tests must also check assembled output.

Earlier statements in this file claiming successful builds and complete test
coverage were unverified. During the November 2026 parity review, the review
container had no Java or D toolchain, and toolchain download requests failed.
Build and test execution in that environment could not be verified.
