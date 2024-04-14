package com.syntevo.antlr.z8;

import java.io.*;
import java.nio.file.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
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
		catch (Assembler.SyntaxException ignored) {
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
		catch (Assembler.SyntaxException ignored) {
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
		catch (Assembler.SyntaxException ignored) {
		}
	}

	private static void assembleFile(String fileName) throws IOException {
		final CharStream charStream = CharStreams.fromFileName(fileName);
		final Assembler assembler = assemble(charStream);

		try (Writer writer = Files.newBufferedWriter(Paths.get(fileName + ".expected"))) {
			assembler.print(writer);
		}
	}

	private static String assemble(String input) {
		if (false) {
			printTokens(CharStreams.fromString(input));
		}

		final Assembler assembler = assemble(CharStreams.fromString(input));
		final StringWriter writer = new StringWriter(1024);
		try {
			assembler.print(writer, "\n");
		}
		catch (IOException e) {
			throw new AssertionError(e);
		}
		return writer.toString();
	}

	private static void printTokens(CharStream charStream) {
		final Z8AsmLexer lexer = new Z8AsmLexer(charStream);

		while (true) {
			final Token token = lexer.nextToken();
			if (token.getType() < 0) {
				break;
			}

			System.out.print(token.getLine());
			System.out.print(':');
			System.out.print(token.getCharPositionInLine());
			System.out.print(" \"");
			String txt = token.getText();
			if (txt != null) {
				txt = txt.replace("\n", "\\n");
				txt = txt.replace("\r", "\\r");
				txt = txt.replace("\t", "\\t");
			}
			else {
				txt = "";
			}
			System.out.print(txt);
			System.out.print("\" ");
			System.out.print(Z8AsmLexer.VOCABULARY.getDisplayName(token.getType()));
			System.out.println();
		}
	}

	private static Assembler assemble(CharStream charStream) {
		final Z8AsmLexer lexer = new Z8AsmLexer(charStream);

		final CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		final Z8AsmParser parser = new Z8AsmParser(tokenStream);
		parser.addErrorListener(new BaseErrorListener() {
			@Override
			public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
				throw new ParseCancellationException("line " + line + ":" + charPositionInLine + " " + msg);
			}
		});

		final ParseTree root = parser.root();

		final Assembler assembler = new Assembler();
		assembler.setDetails(false);

		assembler.process(root);
		return assembler;
	}
}
