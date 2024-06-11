package com.syntevo.z8asm;

import java.util.*;

import org.jetbrains.annotations.*;

/**
 * @author Thomas Singer
 */
public class Assembler {

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

	private final List<Command> commands;

	public Assembler(@NotNull List<Command> commands) {
		this.commands = commands;
	}

	@NotNull
	public List<Command> assemble() {
		return assemble(System.out::println);
	}

	@NotNull
	public List<Command> assemble(@NotNull WarningOut out) {
		final Labels labels = determineLabels();
		final List<Command> commands = resolveLazyCommands(labels, out);
		labels.reportUnused(out);
		return commands;
	}

	@NotNull
	private Labels determineLabels() {
		final Labels labels = new Labels();
		boolean allowOrg = true;
		int pc = 0;
		for (Command command : commands) {
			switch (command.type) {
			case LABEL -> {
				labels.add(command.text, pc, command.location);
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
		labels.finishedInitialization();
		return labels;
	}

	@NotNull
	private List<Command> resolveLazyCommands(@NotNull Labels labels, @NotNull WarningOut out) {
		int pc = 0;
		final List<Command> newCommands = new ArrayList<>();
		for (Command command : commands) {
			switch (command.type) {
			case LABEL -> labels.processing(command.text);
			case ORG -> pc = command.first;
			case CONTENT -> {
				pc += command.size;
				newCommands.add(command);
			}
			case LAZY_CONTENT -> {
				pc += command.size;

				final int address = labels.resolve(command.text, command.location);
				newCommands.add(resolve(command, address, pc, out));
			}
			default -> throw new IllegalStateException("Unsupported command " + command);
			}
		}
		return newCommands;
	}

	@NotNull
	private Command resolve(@NotNull Command command, int address, int pc, @NotNull WarningOut out) {
		Utils.assertTrue(command.type == Command.Type.LAZY_CONTENT);

		final int lowerNibble = command.first & 0x0F;
		if (lowerNibble == 0x0A || lowerNibble == 0x0B) {
			Utils.assertTrue(command.size == 2);
			final int relative = address - pc;
			if (!isValidRelative(relative)) {
				throw new SyntaxException("Target '" + command.text + "' too far away", command.location);
			}
			return Command.content2(command.first, relative);
		}
		if (lowerNibble == 0x0D) {
			Utils.assertTrue(command.size == 3);

			if (isValidRelative(address - pc)) {
				out.print(command.location + ": jp could be jr");
			}

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

	private static boolean isValidRelative(int relative) {
		return relative >= -128 && relative <= 127;
	}
}
