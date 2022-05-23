import com.syntevo.antlr.z8.Assembler;
import com.syntevo.antlr.z8.Z8AsmLexer;
import com.syntevo.antlr.z8.Z8AsmParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Thomas Singer
 */
public class Main {

	public static void main(String[] args) throws IOException {
		buildAst(Paths.get("src/main/examples/z8.asm"));
	}

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
//		parser.addParseListener(parser.new TraceListener());
		final Z8AsmParser.CodeContext code = parser.code();
		final Assembler assembler = new Assembler();
		try {
			ParseTreeWalker.DEFAULT.walk(assembler, code);
			assembler.setAbortForUnknownLabel(true);
			ParseTreeWalker.DEFAULT.walk(assembler, code);
		}
		catch (Assembler.SyntaxException ex) {
			System.err.println("Error: " + ex.getPosition() + ": " + ex.getMessage());
			return;
		}

//		assembler.print();
	}
}
