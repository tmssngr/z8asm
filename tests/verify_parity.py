#!/usr/bin/env python3
"""Build and test both assemblers, then compare their command-line output."""

import os
from pathlib import Path
import shutil
import subprocess
import sys
import tempfile


ROOT = Path(__file__).resolve().parents[1]
EXAMPLES = ROOT / "src/main/examples"


def run(command, directory, *, succeeds=True):
    result = subprocess.run(
        [str(arg) for arg in command],
        cwd=directory,
        capture_output=True,
        text=True,
        encoding="utf-8",
        errors="replace",
        timeout=120,
    )
    if (result.returncode == 0) != succeeds:
        raise RuntimeError(
            f"Unexpected exit status {result.returncode}: {command}\n"
            f"{result.stdout}{result.stderr}"
        )
    return result


def listing_bytes(path):
    data = bytearray()
    for line in path.read_text(encoding="utf-8").splitlines():
        address, *values = line.split()
        if int(address, 16) != len(data):
            raise ValueError(f"Unexpected address in {path}: {address}")
        data.extend(int(value, 16) for value in values)
    return bytes(data)


def main():
    missing = [tool for tool in ("java", "javac", "dub") if not shutil.which(tool)]
    if missing:
        sys.exit("Missing required tools: " + ", ".join(missing))

    with tempfile.TemporaryDirectory(prefix="z8asm-parity-") as temporary:
        temporary = Path(temporary)
        classes = temporary / "classes"
        classes.mkdir()
        libraries = ROOT / "src/main/lib"
        classpath = os.pathsep.join(str(path) for path in (
            classes, libraries / "annotations.jar", libraries / "junit.jar",
            libraries / "hamcrest-core.jar",
        ))
        sources = sorted((ROOT / "src/main/src").rglob("*.java"))
        sources += sorted((ROOT / "src/main/src-test").rglob("*.java"))
        run(["javac", "-encoding", "UTF-8", "-cp", classpath, "-d", classes, *sources], ROOT)
        result = run([
            "java", "-cp", classpath, "org.junit.runner.JUnitCore",
            "com.syntevo.z8asm.LexerTest", "com.syntevo.z8asm.ParserTest",
            "com.syntevo.z8asm.AssemblerTest",
        ], ROOT)
        print(result.stdout)
        run(["dub", "build"], ROOT / "d")
        result = run(["dub", "test"], ROOT / "d")
        print(result.stdout + result.stderr)

        commands = {
            "Java": ["java", "-cp", classpath, "Main"],
            "D": [ROOT / "d" / ("z8asm.exe" if os.name == "nt" else "z8asm")],
        }
        directories = {}
        for name in commands:
            directories[name] = temporary / name
            directories[name].mkdir()

        def check_case(name, source, expected):
            diagnostics = []
            for language, command in commands.items():
                directory = directories[language]
                output = directory / "output.bin"
                output.unlink(missing_ok=True)
                result = run([*command, source], directory)
                actual = output.read_bytes()
                if actual != expected:
                    raise AssertionError(f"{language} output differs for {name}")
                diagnostics.append(result.stdout)
            if diagnostics[0] != diagnostics[1]:
                raise AssertionError(f"Java and D warnings differ for {name}")
            print(f"PASS {name}: {len(expected)} bytes")

        for source in sorted(EXAMPLES.glob("*.asm")):
            check_case(source.name, source, listing_bytes(source.with_suffix(".asm.expected")))

        cases = {
            "local-labels": (
                "first:\n jr .loop\n nop\n.loop:\n jr .loop\n"
                "second:\n jr .loop\n nop\n.loop:\n jr .loop\n",
                "8b 01 ff 8b fe 8b 01 ff 8b fe",
            ),
            "constants": (
                ".const BASE = %62\n.const ALIAS = BASE\ninc ALIAS\n"
                ".repeat 2\n.data l\"\" \"b\"\n.end\n", "20 62 00 62 00 62",
            ),
            "unicode-and-escapes": (
                '.data L"" l"ok" "\\x1234"\ncall \'\\x1234\'\n.data "é€😀"',
                "00 02 6f 6b 34 d6 12 34 e9 ac 3d 00",
            ),
        }
        for name, (text, expected) in cases.items():
            source = temporary / (name + ".asm")
            source.write_text(text, encoding="utf-8")
            check_case(name, source, bytes.fromhex(expected))

        for language, command in commands.items():
            directory = directories[language]
            output = directory / "output.bin"
            for args in ([], ["one.asm", "two.asm"]):
                output.unlink(missing_ok=True)
                result = run([*command, *args], directory)
                if result.stdout != "asm-file missing\n" or output.exists():
                    raise AssertionError(f"{language} argument handling differs from Main")

            for text in ("jp missing", "first:\n.loop:\n nop\nsecond:\n jr .loop"):
                source = temporary / "invalid.asm"
                source.write_text(text, encoding="utf-8")
                run([*command, source], directory, succeeds=False)
                if output.exists():
                    raise AssertionError(f"{language} wrote output for invalid input")

        print("Java and D command-line parity checks passed.")


if __name__ == "__main__":
    main()
