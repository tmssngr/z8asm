import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;

import com.syntevo.antlr.z8.Assembler;
import com.syntevo.antlr.z8.Z8AsmLexer;
import com.syntevo.antlr.z8.Z8AsmParser;

/**
 * @author Thomas Singer
 */
public class Main {

	// Static =================================================================

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("asm-file missing");
			return;
		}

		final Assembler assembler = buildAst(Paths.get(args[0]));
		try (OutputStream stream = Files.newOutputStream(Paths.get("output.bin"))) {
			assembler.write(stream);
		}
/*
		try (Writer writer = Files.newBufferedWriter(Paths.get("data.h"))) {
			assembler.printC(writer);
		}
*/
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
		assembler.visit(root);

		assembler.setSecondPass(true);
		assembler.visit(root);

		return
		assembler;
	}
}
