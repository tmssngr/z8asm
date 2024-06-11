package com.syntevo.z8asm;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import org.junit.*;

/**
 * @author Thomas Singer
 */
public class Z8AsmParserTest {

	// Accessing ==============================================================

	@Test
	public void testUB8830Rom() throws IOException {
		assembleFile("src/main/examples/ub8830rom.asm");
	}

	@Test
	public void test2k() throws IOException {
		assembleFile("src/main/examples/2k-1988.asm");
	}

	@Test
	public void test4k() throws IOException {
		assembleFile("src/main/examples/4k-1988.asm");
	}

	@Test
	public void testES23() throws IOException {
		assembleFile("src/main/examples/es2.3.asm");
	}

	@Test
	public void testES40() throws IOException {
		assembleFile("src/main/examples/es4.0.asm");
	}

	@Test
	public void testVideo() throws IOException {
		assembleFile("src/main/examples/video.asm");
	}

	@Test
	public void testForth() throws IOException {
		assembleFile("src/main/examples/forth.asm");
	}

	@Test
	public void testMissingLabel() {
		try {
			assemble("""
					         jp missing""");
			Assert.fail();
		}
		catch (SyntaxException ignored) {
		}

		try {
			assemble("""
					         jp .1""");
			Assert.fail();
		}
		catch (SyntaxException ignored) {
		}
	}

	@Test
	public void testDuplicateLabels() {
		try {
			assemble("""
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
		                    assemble("""
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
		                    assemble("""
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
			assemble("""
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
			assemble("""
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

	private static void assembleFile(String fileName) throws IOException {
		List<Command> commands = Parser.parse(Path.of(fileName));
		final Output output = Assembler.assemble(commands);

		try (Writer writer = Files.newBufferedWriter(Paths.get(fileName + ".expected"))) {
			output.print(writer);
		}
	}

	private static String assemble(String input) {
		final Lexer lexer = new Lexer(input);
		final Parser parser = new Parser(lexer);
		final List<Command> commands = parser.parse();

		final Output output = Assembler.assemble(commands);
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
