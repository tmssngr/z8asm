import java.io.*;
import java.nio.file.*;
import java.util.*;

import org.jetbrains.annotations.*;

import com.syntevo.z8asm.*;

/**
 * @author Thomas Singer
 */
public class Main {

	public static void main(String[] args) throws IOException {
		final Options options = new Options();
		options.parse(args);
		if (options.targetCount() == 0) {
			options.bin = true;
		}

		if (options.files.isEmpty()) {
			System.out.println("asm-file missing");
			return;
		}

		if (options.files.size() > 1 && options.output != null) {
			final Path outputPath = Paths.get(options.output);
			if (!Files.isDirectory(outputPath)) {
				System.out.println("for multiple specified files the output needs to be a directory");
				return;
			}
		}

		if (options.targetCount() == 2 && options.output != null) {
			final Path outputPath = Paths.get(options.output);
			if (!Files.isDirectory(outputPath)) {
				System.out.println("for multiple targets the output needs to be a directory");
				return;
			}
		}

		for (String file : options.files) {
			final Path asmFile = Paths.get(file);
			final List<Command> commands = Parser.parse(asmFile);
			final Assembler assembler = Assembler.assemble(commands, System.out::println);
			final List<Command> newCommands = assembler.commands();
			final Output output = Output.create(newCommands);

			if (options.ascii) {
				try (BufferedWriter writer = Files.newBufferedWriter(getFile(asmFile, ".ascii", options.output))) {
					output.print(writer);
				}
			}
			if (options.bin) {
				try (OutputStream stream = Files.newOutputStream(getFile(asmFile, ".bin", options.output))) {
					output.write(stream);
				}
			}
			if (options.labels) {
				try (BufferedWriter writer = Files.newBufferedWriter(getFile(asmFile, ".labels", options.output))) {
					assembler.writeLabels(writer);
				}
			}
		}
	}

	@NotNull
	private static Path getFile(Path asmFile, String suffix, @Nullable String output) {
		final String fileNameWithoutSuffix = Utils.getFileNameWithoutSuffix(asmFile);
		final String fileName = fileNameWithoutSuffix + suffix;
		if (output == null) {
			return asmFile.resolveSibling(fileName);
		}

		final Path outputPath = Paths.get(output);
		if (Files.isDirectory(outputPath)) {
			return outputPath.resolve(fileName);
		}
		return outputPath;
	}

	private static class Options {

		private final List<String> files = new ArrayList<>();

		private boolean ascii;
		private boolean bin;
		private boolean labels;
		private String output;

		public void parse(String[] args) {
			boolean files = false;
			for (String arg : args) {
				if (!files) {
					switch (arg) {
					case "--ascii" -> {
						ascii = true;
						continue;
					}
					case "--bin" -> {
						bin = true;
						continue;
					}
					case "--labels" -> {
						labels = true;
						continue;
					}
					case "--" -> {
						files = true;
						continue;
					}
					}
					final String outputPrefix = "--output=";
					if (arg.startsWith(outputPrefix)) {
						this.output = arg.substring(outputPrefix.length());
					}
				}
				this.files.add(arg);
			}
		}

		public int targetCount() {
			int count = 0;
			if (ascii) {
				count++;
			}
			if (bin) {
				count++;
			}
			return count;
		}
	}
}
