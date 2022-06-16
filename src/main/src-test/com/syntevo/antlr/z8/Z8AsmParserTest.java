package com.syntevo.antlr.z8;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

/**
 * @author Thomas Singer
 */
public class Z8AsmParserTest {

	// Accessing ==============================================================

	@Test
	public void test() throws IOException {
		assemble("src/main/examples/es4.0.asm");
		assemble("src/main/examples/ub8830rom.asm");
		assemble("src/main/examples/video.asm");
	}

	// Utils ==================================================================

	private static void assemble(String fileName) throws IOException {
		final CharStream charStream = CharStreams.fromFileName(fileName);
		final Z8AsmLexer lexer = new Z8AsmLexer(charStream);

		final CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		final Z8AsmParser parser = new Z8AsmParser(tokenStream);
		parser.addErrorListener(new BaseErrorListener() {
			@Override
			public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
				throw new ParseCancellationException("line " + line + ":" + charPositionInLine + " " + msg);
			}
		});

		final ParseTree root = parser.code();

		final Assembler assembler = new Assembler();
		assembler.setDetails(false);
		assembler.visit(root);

		assembler.setSecondPass(true);
		assembler.visit(root);

		try (Writer writer = Files.newBufferedWriter(Paths.get(fileName + ".expected"))) {
			assembler.print(writer);
		}
	}
}
