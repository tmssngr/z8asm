import java.io.*;
import java.nio.file.*;
import java.util.*;

import com.syntevo.z8asm.*;

/**
 * @author Thomas Singer
 */
public class WriteVerilogFile {

	// Static =================================================================

	public static void main(String[] args) throws IOException {
		try (Writer writer = Files.newBufferedWriter(Paths.get("memory.txt"))) {
			final Output ub8830rom = buildAst(Paths.get("src/main/examples/ub8830rom.asm"));
			ub8830rom.printVerilog(writer);

			final Output os = buildAst(Paths.get(true
					                                        ? "src/main/examples/4k-1988.asm"
					                                        : "src/main/examples/2k-1988.asm"));
			os.printVerilog(writer);
		}
	}

	// Utils ==================================================================

	private static Output buildAst(Path asmFile) throws IOException {
		final List<Command> commands = Parser.parse(asmFile);
		return Assembler.assemble(commands);
	}
}
