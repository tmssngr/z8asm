# Verification

The D executable is configured in `dub.json` and starts at `src/main.d`.

Run `dub build` and `dub test` from this directory with a D compiler and its
native linker toolchain installed. Compilation alone does not establish
equivalence with the Java assembler; the tests must also check assembled output.
From the repository root, `python3 tests/verify_parity.py` builds and tests both
implementations and compares their binaries and warnings with each other and
the committed example listings. It requires Java 17+, a D toolchain, DUB, and
Python 3.8+.

Earlier statements in this file claiming successful builds and complete test
coverage were unverified. During the November 2026 parity review, the review
container had no Java or D toolchain, and toolchain download requests failed.
Build and test execution in that environment could not be verified.
