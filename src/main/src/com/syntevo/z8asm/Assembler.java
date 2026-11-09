package com.syntevo.z8asm;

import java.io.*;
import java.util.*;

import org.jetbrains.annotations.*;

/**
 * @author Thomas Singer
 */
public record Assembler(@NotNull List<Command> commands, @NotNull Map<String, Integer> labels, int org) {

	@NotNull
	public static List<Command> assemble(@NotNull List<Command> commands) {
		final Assembler assembler = assemble(commands, System.out::println);
		return assembler.commands;
	}

	@NotNull
	public static Assembler assemble(@NotNull List<Command> commands, @NotNull WarningOut out) {
		commands = new ArrayList<>(commands);
		final Pair<Labels, Integer> labelsAndOrg = determineLabels(commands);
		final Labels labels = labelsAndOrg.first();
		resolveAutoJumps(commands, labels);
		resolveLazyCommands(commands, labels, out);
		labels.reportUnused(out);
		commands.removeIf(Objects::isNull);
		final Map<String, Integer> mapping = labels.getMapping();
		return new Assembler(commands, mapping, labelsAndOrg.second());
	}

	public void writeLabels(Writer writer) throws IOException {
		final Map<String, Integer> labelMapping = labels();
		final List<String> labelsSortedByAddress = new ArrayList<>(labelMapping.keySet());
		labelsSortedByAddress.sort((o1, o2) -> {
			final int a1 = labelMapping.get(o1);
			final int a2 = labelMapping.get(o2);
			if (a1 != a2) {
				return a1 - a2;
			}
			return o1.compareTo(o2);
		});
		for (String label : labelsSortedByAddress) {
			final int address = labelMapping.get(label);
			writer.write(Utils.toHex(address, 4));
			writer.write(" ");
			writer.write(label);
			writer.write("\n");
		}
	}

	@NotNull
	private static Pair<Labels, Integer> determineLabels(@NotNull List<Command> commands) {
		final Labels labels = new Labels();
		boolean isFirst = true;
		int org = 0;
		int pc = 0;
		for (Command cmd : commands) {
			if (isFirst) {
				isFirst = false;
				if (cmd instanceof Command.Org c) {
					org = c.org();
					pc = org;
					continue;
				}
			}

			switch (cmd) {
			case Command.Align align -> {
				final int offset = getAlignmentOffset(align.alignment(), pc);
				pc += offset;
			}
			case Command.AutoJumpAddress ignored -> pc += 2;
			case Command.AutoJumpString ignored -> pc += 2;
			case Command.Content content -> pc += content.size();
			case Command.Label command -> labels.add(command.name(), pc, command.location());
			case Command.LazyContent2 ignored -> pc += 2;
			case Command.LazyContent3 ignored -> pc += 3;
			case Command.Org o -> throw new SyntaxException(".org is only allowed before any content or label", o.location());
			}
		}
		labels.finishedInitialization();
		return new Pair<>(labels, org);
	}

	private static void resolveAutoJumps(List<Command> commands, Labels labels) {
		while (true) {
			if (!resolveAutoJumps(commands, labels, false)) {
				break;
			}
		}

		resolveAutoJumps(commands, labels, true);
	}

	private static boolean resolveAutoJumps(List<Command> commands, Labels labels, boolean finalize) {
		int pc = 0;
		boolean changed = false;
		for (int i = 0; i < commands.size(); i++) {
			final Command cmd = commands.get(i);
			switch (cmd) {
			case Command.Align align -> {
				final int offset = getAlignmentOffset(align.alignment(), pc);
				pc += offset;
			}
			case Command.AutoJumpAddress command -> {
				final int opCode = command.jrOpCode();
				final int higherNibble = opCode & 0xF0;
				pc += 2;
				final int address = command.address();
				final int relative = address - pc;
				if (!isValidRelative(relative)) {
					commands.set(i, new Command.Content(higherNibble + 0x0D, address >> 8, address & 0xFF));
					pc++;
					changed = true;
				}
				else if (finalize) {
					commands.set(i, new Command.Content(higherNibble + 0x0B, relative));
				}
			}
			case Command.AutoJumpString command -> {
				final int opCode = command.jrOpCode();
				final int higherNibble = opCode & 0xF0;
				pc += 2;
				final int address = labels.resolve(command.target(), command.location());
				final int relative = address - pc;
				if (!isValidRelative(relative)) {
					commands.set(i, new Command.LazyContent3(higherNibble + 0x0D, command.target(), command.location()));
					pc++;
					changed = true;
				}
				else if (finalize) {
					commands.set(i, new Command.LazyContent2(higherNibble + 0x0B, command.target(), command.location()));
				}
			}
			case Command.Content command -> pc += command.size();
			case Command.Label command -> {
				if (labels.update(command.name(), pc)) {
					changed = true;
				}
			}
			case Command.LazyContent2 ignored -> pc += 2;
			case Command.LazyContent3 ignored -> pc += 3;
			case Command.Org o -> pc = o.org();
			}
		}
		labels.finishedInitialization();
		return changed;
	}

	private static int getAlignmentOffset(int alignment, int pc) {
		Utils.assertTrue(alignment > 0);
		final int maxPc = pc + alignment - 1;
		final int remainder = maxPc % alignment;
		return maxPc - remainder - pc;
	}

	private static void resolveLazyCommands(@NotNull List<Command> commands, @NotNull Labels labels, @NotNull WarningOut out) {
		int pc = 0;
		for (int i = 0; i < commands.size(); i++) {
			final Command cmd = commands.get(i);
			switch (cmd) {
			case Command.Align align -> {
				final int offset = getAlignmentOffset(align.alignment(), pc);
				if (offset > 0) {
					final byte[] bytes = new byte[offset];
					Arrays.fill(bytes, align.fillByte());
					commands.set(i, new Command.Content(bytes));
					pc += offset;
				}
				else {
					commands.set(i, null);
				}
			}
			case Command.Content command -> pc += command.size();
			case Command.Label label -> {
				labels.processing(label.name());
				commands.set(i, null);
			}
			case Command.LazyContent2 command -> {
				final Command.Content resolvedCommand = resolveLazyCommand(command, pc, labels);
				pc += resolvedCommand.size();
				commands.set(i, resolvedCommand);
			}
			case Command.LazyContent3 command -> {
				final Command.Content resolvedCommand = resolveLazyCommand(command, pc, labels, out);
				pc += resolvedCommand.size();
				commands.set(i, resolvedCommand);
			}
			case Command.Org org -> {
				pc = org.org();
				commands.set(i, null);
			}
			default -> {
				throw new IllegalStateException("Unsupported command " + cmd);
			}
			}
		}
	}

	@NotNull
	private static Command.Content resolveLazyCommand(@NotNull Command.LazyContent2 command, int pc, @NotNull Labels labels) {
		final int opCode = command.opCode();
		final int lowerNibble = opCode & 0x0F;
		if (lowerNibble == 0x0A || lowerNibble == 0x0B) {
			final int address = labels.resolve(command.text(), command.location());
			final int relative = address - pc - 2;
			if (!isValidRelative(relative)) {
				Utils.assertTrue(lowerNibble == 0x0A); // can't happen for JR, because it already should have been turned into a JP
				throw new SyntaxException("Target '" + command.text() + "' too far away (" + relative + ")", command.location());
			}
			return new Command.Content(opCode, relative);
		}

		if (lowerNibble == 0x0C) {
			final boolean defaultHigh = (opCode & 0x10) == 0;
			final int value = resolveLabelHighOrLow(defaultHigh, command.text(), command.location(), labels);
			return new Command.Content(opCode, value);
		}

		if (opCode == 0) {
			final int address = labels.resolve(command.text(), command.location());
			return new Command.Content(address >> 8, address);
		}

		throw new IllegalStateException("Unsupported command " + command);
	}

	@NotNull
	private static Command.Content resolveLazyCommand(@NotNull Command.LazyContent3 command, int pc, @NotNull Labels labels, @NotNull WarningOut out) {
		final int opCode = command.opCode();
		final int lowerNibble = opCode & 0x0F;
		if (lowerNibble == 0x0D) {
			final int address = labels.resolve(command.text(), command.location());
			if (isValidRelative(address - pc - 3)) {
				out.print(command.location() + ": jp could be jr");
			}

			return new Command.Content(opCode, address >> 8, address);
		}

		final int higherNibble = opCode & 0xF0;
		if (higherNibble <= 0x70) {
			if (lowerNibble == 0x06 || lowerNibble == 0x07) {
				final int register = command.second();
				final boolean defaultHigh = (register & 1) == 0;
				final int value = resolveLabelHighOrLow(defaultHigh, command.text(), command.location(), labels);
				return new Command.Content(opCode, register, value);
			}
		}

		if (opCode == 0xD6) {
			final int address = labels.resolve(command.text(), command.location());
			return new Command.Content(opCode, address >> 8, address);
		}

		if (opCode == 0xE6) {
			final int register = command.second();
			final boolean defaultHigh = (register & 1) == 0;
			final int value = resolveLabelHighOrLow(defaultHigh, command.text(), command.location(), labels);
			return new Command.Content(opCode, register, value);
		}

		throw new IllegalStateException("Unsupported command " + command);
	}

	private static int resolveLabelHighOrLow(boolean high, @NotNull String text, @NotNull Location location, @NotNull Labels labels) {
		final String textLower = text.toLowerCase(Locale.ROOT);
		if (textLower.length() == text.length() && textLower.endsWith(")")) {
			if (textLower.startsWith("lo(")) {
				text = text.substring(3, text.length() - 1);
				high = false;
			}
			else if (textLower.startsWith("hi(")) {
				text = text.substring(3, text.length() - 1);
				high = true;
			}
		}
		final int address = labels.resolve(text, location);
		return high ? address >> 8 : address;
	}

	private static boolean isValidRelative(int relative) {
		return relative >= -128 && relative <= 127;
	}
}
