import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.syntevo.z8asm.*;

import static com.syntevo.z8asm.Output.NL;
import static com.syntevo.z8asm.Output.toHex;

/**
 * @author Thomas Singer
 */
public class WriteMainEpromHeaderFile {

	public static void main(String[] args) throws IOException {
		try (Writer writer = Files.newBufferedWriter(Paths.get("data.h"))) {
			writer.write("#ifndef DATA_H");
			writer.write(NL);
			writer.write("#define DATA_H");
			writer.write(NL);
			writer.write("const static uint8_t DATA[] PROGMEM = {");
			writer.write(NL);

			final Output ub8830rom = buildAst(Paths.get("src/main/examples/ub8830rom.asm"));
			int count = ub8830rom.printC(writer);

			writer.write(",");
			writer.write(NL);

			final Output es40 = buildAst(Paths.get("src/main/examples/es4.0.asm"));
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

	private static Output buildAst(Path asmFile) throws IOException {
		final List<Command> commands = Parser.parse(asmFile);
		return Assembler.assemble(commands);
	}
}
