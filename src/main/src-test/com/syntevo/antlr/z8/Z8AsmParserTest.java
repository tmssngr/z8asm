package com.syntevo.antlr.z8;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Thomas Singer
 */
public class Z8AsmParserTest {

	// Accessing ==============================================================

	@Test
	public void test() throws IOException {
		assemble("src/main/examples/ub8830rom.asm");
	}

	// Utils ==================================================================

	private static void assemble(String fileName) throws IOException {
		final CharStream charStream = CharStreams.fromFileName(fileName);
		final Z8AsmLexer lexer = new Z8AsmLexer(charStream);

		final CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		final Z8AsmParser parser = new Z8AsmParser(tokenStream);

		//		parser.addParseListener(parser.new TraceListener());
		final Z8AsmParser.CodeContext code = parser.code();

		final Assembler assembler = new Assembler();
		ParseTreeWalker.DEFAULT.walk(assembler, code);

		assembler.setAbortForUnknownLabel(true);
		ParseTreeWalker.DEFAULT.walk(assembler, code);

		try (Writer writer = Files.newBufferedWriter(Paths.get(fileName + ".expected"))) {
			assembler.print(writer);
		}
	}
}
