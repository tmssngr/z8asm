package com.syntevo.z8asm;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import org.jetbrains.annotations.*;
import org.junit.*;

/**
 * @author Thomas Singer
 */
public class Z8AsmParserTest {

	// Accessing ==============================================================

	@Test
	public void testUB8830Rom() throws IOException {
		assembleFileBinaryAscii(Path.of("src/main/examples/ub8830rom.asm"));
	}

	@Test
	public void test2k() throws IOException {
		assembleFileBinaryAscii(Path.of("src/main/examples/2k-1988.asm"));
	}

	@Test
	public void test4k() throws IOException {
		assembleFileBinaryAscii(Path.of("src/main/examples/4k-1988.asm"));
	}

	@Test
	public void testES23() throws IOException {
		assembleFileBinaryAscii(Path.of("src/main/examples/es2.3.asm"));
	}

	@Test
	public void testES40() throws IOException {
		assembleFileBinaryAscii(Path.of("src/main/examples/es4.0.asm"));
	}

	@Test
	public void testES45() throws IOException {
		assembleFileBinaryAscii(Path.of("src/main/examples/es4.5.asm"));
	}

	@Test
	public void testVideo() throws IOException {
		assembleFileBinaryAscii(Path.of("src/main/examples/video.asm"));
	}

	@Test
	public void testForth() throws IOException {
		assembleFileBinaryAscii(Path.of("src/main/examples/forth.asm"));
	}

	@Test
	public void testAsDis() throws IOException {
		assembleFileBinaryAscii(Path.of("src/main/examples/asdis.asm"));
	}

	@Test
	public void testKeys() throws IOException {
		assembleFileBinary(Path.of("src/main/examples/wkey.asm"));
	}

	@Test
	public void testMissingLabel() {
		try {
			assembleAsString("""
					         jp missing""");
			Assert.fail();
		}
		catch (SyntaxException ignored) {
		}

		try {
			assembleAsString("""
					         jp .1""");
			Assert.fail();
		}
		catch (SyntaxException ignored) {
		}
	}

	@Test
	public void testDuplicateLabels() {
		try {
			assembleAsString("""
					         main:
					           ld r0, #10
					         main:
					           call %1000
					           djnz r0, main
					           ret""");
			Assert.fail();
		}
		catch (SyntaxException ignored) {
		}
	}

	@Test
	public void testDotLabels() {
		final String expected = "0000  0c 0a d6 00 08 0a fb af  1c 0a ff 1a fd af\n";
		Assert.assertEquals(expected,
		                    assembleAsString("""
				                             main:
				                               ld r0, #10
				                             main1:
				                               call sub1
				                               djnz r0, main1
				                               ret

				                             sub1:
				                               ld r1, #10
				                             sub2:
				                               nop
				                               djnz r1, sub2
				                               ret
				                             """));
		Assert.assertEquals(expected,
		                    assembleAsString("""
				                             main:
				                               ld r0, #10
				                             .1:
				                               call sub1
				                               djnz r0, .1
				                               ret

				                             sub1:
				                               ld r1, #10
				                             .1:
				                               nop
				                               djnz r1, .1
				                               ret
				                             """));

		try {
			assembleAsString("""
					         .1:
					           ld r0, #10
					           call %1000
					         .2:
					           djnz r0, .1
					           ret""");
			Assert.fail();
		}
		catch (SyntaxException ignored) {
		}

		try {
			assembleAsString("""
					         main:
					           ld r0, #10
					         .1:
					           call %1000
					         .1:
					           djnz r0, .1
					           ret""");
			Assert.fail();
		}
		catch (SyntaxException ignored) {
		}
	}

	@Test
	public void testAlign() {
		Assert.assertEquals("0000  41 42 43 e7 e7 e7 e7 e7\n",
		                    assembleAsString("""
				                                     .data "ABC"
				                                     .align 8, %e7
				                                     """));
	}

	@Test
	public void testAutoJump() {
		Assert.assertEquals("""
				                    0000  7b fe 6d 80 00 8b f9 8d  80 00
				                    """,
		                    assembleAsString("""
				                                     jr c, %0
				                                     jr z, %8000
				                                     jr %0
				                                     jr %8000"""));
		Assert.assertEquals("""
				                    0000  6b 7f 7b fc ff ff ff ff  ff ff ff ff ff ff ff ff
				                    0010  ff ff ff ff ff ff ff ff  ff ff ff ff ff ff ff ff
				                    0020  ff ff ff ff ff ff ff ff  ff ff ff ff ff ff ff ff
				                    0030  ff ff ff ff ff ff ff ff  ff ff ff ff ff ff ff ff
				                    0040  ff ff ff ff ff ff ff ff  ff ff ff ff ff ff ff ff
				                    0050  ff ff ff ff ff ff ff ff  ff ff ff ff ff ff ff ff
				                    0060  ff ff ff ff ff ff ff ff  ff ff ff ff ff ff ff ff
				                    0070  ff ff ff ff ff ff ff ff  ff ff ff ff ff ff ff ff
				                    0080  ff af
				                    """,
		                    assembleAsString("""
				                                     start:
				                                       jr z, foo      ; long
				                                       jr c, start    ; short
				                                       .align %80, %ff
				                                       nop
				                                     foo:
				                                       ret"""));
		Assert.assertEquals("""
				                    0000  6d 00 82 7b fb ff ff ff  ff ff ff ff ff ff ff ff
				                    0010  ff ff ff ff ff ff ff ff  ff ff ff ff ff ff ff ff
				                    0020  ff ff ff ff ff ff ff ff  ff ff ff ff ff ff ff ff
				                    0030  ff ff ff ff ff ff ff ff  ff ff ff ff ff ff ff ff
				                    0040  ff ff ff ff ff ff ff ff  ff ff ff ff ff ff ff ff
				                    0050  ff ff ff ff ff ff ff ff  ff ff ff ff ff ff ff ff
				                    0060  ff ff ff ff ff ff ff ff  ff ff ff ff ff ff ff ff
				                    0070  ff ff ff ff ff ff ff ff  ff ff ff ff ff ff ff ff
				                    0080  ff ff af
				                    """,
		                    assembleAsString("""
				                                     start:
				                                       jr z, foo      ; short
				                                       jr c, start    ; short
				                                       .align %80, %ff
				                                       nop
				                                       nop
				                                     foo:
				                                       ret"""));
		Assert.assertEquals("""
				                    0000  6d 00 83 7d 00 86 ff ff  ff ff ff ff ff ff ff ff
				                    0010  ff ff ff ff ff ff ff ff  ff ff ff ff ff ff ff ff
				                    0020  ff ff ff ff ff ff ff ff  ff ff ff ff ff ff ff ff
				                    0030  ff ff ff ff ff ff ff ff  ff ff ff ff ff ff ff ff
				                    0040  ff ff ff ff ff ff ff ff  ff ff ff ff ff ff ff ff
				                    0050  ff ff ff ff ff ff ff ff  ff ff ff ff ff ff ff ff
				                    0060  ff ff ff ff ff ff ff ff  ff ff ff ff ff ff ff ff
				                    0070  ff ff ff ff ff ff ff ff  ff ff ff ff ff ff ff ff
				                    0080  ff ff ff d6 80 00 af
				                    """,
		                    assembleAsString("""
				                                       jr z, foo     ; long because next jump makes the distance too large
				                                       jr c, bar     ; long
				                                       .repeat %7d
				                                          nop
				                                       .end
				                                     foo:
				                                       call %8000
				                                     bar:
				                                       ret"""));
	}

	@Test
	public void testLoHiImmediateLabels() {
		Assert.assertEquals("0000  0c 80 1c 09 2c 80 3c 09  af\n",
		                    assembleAsString("""
				                               .org %8000
				                             main:
				                               ld r0, #hi(data)
				                               ld r1, #lo(data)
				                               ld r2, #data
				                               ld r3, #data
				                               ret
				                             data:
				                             """));
		Assert.assertEquals("0000  e6 40 80 e6 41 0d e6 42  80 e6 43 0d af\n",
		                    assembleAsString("""
				                               .org %8000
				                             main:
				                               ld %40, #hi(data)
				                               ld %41, #lo(data)
				                               ld %42, #data
				                               ld %43, #data
				                               ret
				                             data:
				                             """));
		Assert.assertEquals("0000  06 e1 0d 16 e0 80 06 23  0d 16 22 80 af\n",
		                    assembleAsString("""
				                               .org %8000
				                             main:
				                               add r1, #lo(data)
				                               adc r0, #hi(data)
				                               add %23, #data
				                               adc %22, #data
				                               ret
				                             data:
				                             """));
		Assert.assertEquals("0000  27 e1 0d 37 e0 80 27 13  0d 37 12 80 af\n",
		                    assembleAsString("""
				                               .org %8000
				                             main:
				                               sub @r1, #lo(data)
				                               sbc @r0, #hi(data)
				                               sub @%13, #data
				                               sbc @%12, #data
				                               ret
				                             data:
				                             """));
	}

	private static void assembleFileBinaryAscii(Path file) throws IOException {
		final Output output = assemble(file);

		try (Writer writer = Files.newBufferedWriter(getExpectedFile(file, ".expected"))) {
			output.print(writer);
		}
	}

	private static void assembleFileBinary(Path file) throws IOException {
		final Output output = assemble(file);

		try (OutputStream os = Files.newOutputStream(getExpectedFile(file, ".bin"))) {
			output.write(os);
		}
	}

	@NotNull
	private static Output assemble(Path file) throws IOException {
		List<Command> commands = Parser.parse(file);
		final List<Command> newCommands = Assembler.assemble(commands);
		return Output.create(newCommands);
	}

	@NotNull
	private static Path getExpectedFile(Path file, String suffix) {
		return file.resolveSibling(file.getFileName() + suffix);
	}

	private static String assembleAsString(String input) {
		final Lexer lexer = new Lexer(input);
		final Parser parser = new Parser(lexer);
		final List<Command> commands = parser.parse();

		final List<Command> newCommands = Assembler.assemble(commands);
		final Output output = Output.create(newCommands);
		final StringWriter writer = new StringWriter(1024);
		try {
			output.print(writer, "\n");
		}
		catch (IOException e) {
			throw new AssertionError(e);
		}
		return writer.toString();
	}
}
