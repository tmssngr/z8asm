import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.syntevo.z8asm.Output;

/**
 * @author Thomas Singer
 */
public class ConvertBinToHexListing {

	// Static =================================================================

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Need input file");
			return;
		}

		final Output output = new Output();
		try (final InputStream stream = Files.newInputStream(Paths.get(args[0]))) {
			while (true) {
				final int data = stream.read();
				if (data < 0) {
					break;
				}

				output.write(data);
			}
		}

		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"), StandardOpenOption.CREATE_NEW)) {
			output.print(writer);
		}
	}
}
