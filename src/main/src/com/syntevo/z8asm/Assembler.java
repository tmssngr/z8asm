package com.syntevo.z8asm;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import org.jetbrains.annotations.*;

import com.syntevo.antlr.z8.*;

/**
 * @author Thomas Singer
 */
public class Assembler {

	public static void main(String[] args) throws IOException {
		final Path file = Paths.get(args[0]);
		final List<Command> commands = Parser.parse(file);

		final Output output = assemble(commands);

		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(args[0] + ".expected"))) {
			output.print(writer);
		}
	}

	@NotNull
	public static Output assemble(List<Command> commands) {
		final Assembler assembler = new Assembler(commands);
		final List<Command> newCommands = assembler.assemble();

		final Output output = new Output();
		for (Command command : newCommands) {
			if (command.type != Command.Type.CONTENT) {
				throw new IllegalStateException(command.toString());
			}

			output.write(command.first);
			if (command.size > 1) {
				output.write(command.second);
				if (command.size > 2) {
					output.write(command.third);
				}
			}
		}
		return output;
	}

	private final Map<String, Integer> globalLabels = new HashMap<>();
	private final Map<String, Map<String, Integer>> localLabels = new HashMap<>();
	private final List<Command> commands;

	public Assembler(@NotNull List<Command> commands) {
		this.commands = commands;
	}

	public List<Command> assemble() {
		determineLabels();
		return convertLazyToNormalCommands();
	}

	private void determineLabels() {
		boolean allowOrg = true;
		int pc = 0;
		String currentGlobalLabel = null;
		for (Command command : commands) {
			switch (command.type) {
			case LABEL -> {
				if (isLocalLabel(command.text)) {
					if (currentGlobalLabel == null) {
						throw new SyntaxException("A local label needs to be after a global label", command.location);
					}
					final Map<String, Integer> map = localLabels.computeIfAbsent(currentGlobalLabel, s -> new HashMap<>());
					if (map.put(command.text, pc) != null) {
						throw new SyntaxException("Duplicate local label " + command.text, command.location);
					}
				}
				else {
					if (globalLabels.put(command.text, pc) != null) {
						throw new SyntaxException("Duplicate label " + command.text, command.location);
					}
					currentGlobalLabel = command.text;
				}
				allowOrg = false;
			}
			case ORG -> {
				if (!allowOrg) {
					throw new SyntaxException(".org is only allowed before any content or label", command.location);
				}
				pc = command.first;
			}
			case CONTENT,
			     LAZY_CONTENT -> {
				allowOrg = false;
				pc += command.size;
			}
			default -> throw new IllegalStateException("Unsupported command " + command);
			}
		}
	}

	@NotNull
	private List<Command> convertLazyToNormalCommands() {
		int pc = 0;
		String currentGlobalLabel = null;
		final List<Command> newCommands = new ArrayList<>();
		for (Command command : commands) {
			switch (command.type) {
			case LABEL -> {
				if (!isLocalLabel(command.text)) {
					currentGlobalLabel = command.text;
				}
			}
			case ORG -> pc = command.first;
			case CONTENT -> {
				pc += command.size;
				newCommands.add(command);
			}
			case LAZY_CONTENT -> {
				pc += command.size;

				final Map<String, Integer> labels = isLocalLabel(command.text)
						? localLabels.get(currentGlobalLabel)
						: globalLabels;
				if (labels == null || !labels.containsKey(command.text)) {
					throw new SyntaxException("No target '" + command.text + "' found", command.location);
				}

				final int address = labels.get(command.text);
				newCommands.add(processLazyCommand(command, address, pc));
			}
			default -> throw new IllegalStateException("Unsupported command " + command);
			}
		}
		return newCommands;
	}

	private boolean isLocalLabel(String text) {
		return text.startsWith(".");
	}

	private Command processLazyCommand(Command command, int address, int pc) {
		Utils.assertTrue(command.type == Command.Type.LAZY_CONTENT);

		final int lowerNibble = command.first & 0x0F;
		if (lowerNibble == 0x0A || lowerNibble == 0x0B) {
			Utils.assertTrue(command.size == 2);
			final int relative = address - pc;
			if (relative < -128 || relative > 127) {
				throw new SyntaxException("Target '" + command.text + "' too far away", command.location);
			}
			return Command.content2(command.first, relative);
		}
		if (lowerNibble == 0x0D) {
			Utils.assertTrue(command.size == 3);
			return Command.content3(command.first, address >> 8, address);
		}
		if (command.first == 0xD6) {
			Utils.assertTrue(command.size == 3);
			return Command.content3(command.first, address >> 8, address);
		}
		if (command.first == 0) {
			Utils.assertTrue(command.size == 2);
			return Command.content2(address >> 8, address);
		}
		throw new IllegalStateException("Unsupported command " + command);
	}
}
