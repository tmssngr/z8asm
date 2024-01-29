import java.io.*;
import java.nio.file.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

import com.syntevo.antlr.z8.*;

/**
 * @author Thomas Singer
 */
public class WriteVerilogFile {

	// Static =================================================================

	public static void main(String[] args) throws IOException {
		try (Writer writer = Files.newBufferedWriter(Paths.get("memory.txt"))) {
			final Assembler ub8830rom = buildAst(Paths.get("src/main/examples/ub8830rom.asm"));
			ub8830rom.printVerilog(writer);

			final Assembler os2k = buildAst(Paths.get("src/main/examples/2k-1988.asm"));
			os2k.printVerilog(writer);
		}
	}

	// Utils ==================================================================

	private static Assembler buildAst(Path path) throws IOException {
		try (final InputStream stream = Files.newInputStream(path)) {
			return buildAst(stream);
		}
	}

	private static Assembler buildAst(InputStream stream) throws IOException {
		final CharStream charStream = CharStreams.fromStream(stream);
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
