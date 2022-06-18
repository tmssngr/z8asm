import java.io.IOException;
import java.io.InputStream;
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
		buildAst(Paths.get("src/main/examples/video.asm"));
	}

	// Utils ==================================================================

	private static void buildAst(Path path) throws IOException {
		try (final InputStream stream = Files.newInputStream(path)) {
			buildAst(stream);
		}
	}

	private static void buildAst(InputStream stream) throws IOException {
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

		try (Writer writer = Files.newBufferedWriter(Paths.get("data.h"))) {
//		try (PrintWriter writer = new PrintWriter(System.out)) {
			assembler.printC(writer);
		}
	}
}
