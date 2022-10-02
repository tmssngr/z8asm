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

import static com.syntevo.antlr.z8.Output.NL;
import static com.syntevo.antlr.z8.Output.toHex;

/**
 * @author Thomas Singer
 */
public class WriteMainEpromHeaderFile {

	// Static =================================================================

	public static void main(String[] args) throws IOException {
		try (Writer writer = Files.newBufferedWriter(Paths.get("data.h"))) {
			writer.write("#ifndef DATA_H");
			writer.write(NL);
			writer.write("#define DATA_H");
			writer.write(NL);
			writer.write("const static uint8_t DATA[] PROGMEM = {");
			writer.write(NL);

			final Assembler ub8830rom = buildAst(Paths.get("src/main/examples/ub8830rom.asm"));
			int count = ub8830rom.printC(writer);

			writer.write(",");
			writer.write(NL);

			final Assembler es40 = buildAst(Paths.get("src/main/examples/es4.0.asm"));
			count += es40.printC(writer);

			writer.write("};");
			writer.write(NL);
			writer.write("const int DATA_LENGTH = 0x");
			writer.write(toHex(count, 4));
			writer.write(";");
			writer.write(NL);
			writer.write("#endif");
			writer.write(NL);
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
