import java.io.*;
import java.nio.file.*;
import java.util.*;

import com.syntevo.z8asm.*;

/**
 * @author Thomas Singer
 */
public class Main {

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("asm-file missing");
			return;
		}

		final Path asmFile = Paths.get(args[0]);
		final List<Command> commands = Parser.parse(asmFile);
		final Output output = Assembler.assemble(commands);
		try (OutputStream stream = Files.newOutputStream(Paths.get("output.bin"))) {
			output.write(stream);
		}
	}
}
