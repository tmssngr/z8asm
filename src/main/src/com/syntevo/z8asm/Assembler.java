package com.syntevo.z8asm;

import java.util.*;

import org.jetbrains.annotations.*;

/**
 * @author Thomas Singer
 */
public class Assembler {

	@NotNull
	public static List<Command> assemble(List<Command> commands) {
		final Assembler assembler = new Assembler();
		return assembler.assemble(commands, System.out::println);
	}

	public Assembler() {
	}

	@NotNull
	public List<Command> assemble(List<Command> commands, @NotNull WarningOut out) {
		commands = new ArrayList<>(commands);
		final Labels labels = determineLabels(commands);
		resolveAutoJumps(commands, labels);
		resolveLazyCommands(commands, labels, out);
		labels.reportUnused(out);
		commands.removeIf(Objects::isNull);
		return commands;
	}

	@NotNull
	private Labels determineLabels(List<Command> commands) {
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
				pc = command.get16bitValue();
			}
			case CONTENT,
			     LAZY_CONTENT -> {
				allowOrg = false;
				pc += command.size;
			}
			case AUTO_JUMP -> {
				Utils.assertTrue(command.size == 1 || command.size == 3);
				pc += 2;
			}
			case ALIGN -> {
				final int offset = getAlignmentOffset(command.get16bitValue(), pc);
				pc += offset;
			}
			default -> throw new IllegalStateException("Unsupported command " + command);
			}
		}
		labels.finishedInitialization();
		return labels;
	}

	private void resolveAutoJumps(List<Command> commands, Labels labels) {
		while (true) {
			if (!resolveAutoJumps(commands, labels, false)) {
				break;
			}
		}

		resolveAutoJumps(commands, labels, true);
	}

	private boolean resolveAutoJumps(List<Command> commands, Labels labels, boolean finalize) {
		int pc = 0;
		boolean changed = false;
		for (int i = 0; i < commands.size(); i++) {
			final Command command = commands.get(i);
			switch (command.type) {
			case LABEL -> {
				if (labels.update(command.text, pc)) {
					changed = true;
				}
			}
			case ORG -> pc = command.get16bitValue();
			case CONTENT,
			     LAZY_CONTENT -> pc += command.size;
			case AUTO_JUMP -> {
				final int opCode = command.get(0);
				final int higherNibble = opCode & 0xF0;
				pc += 2;
				if (command.size == 1) {
					// jump to text target
					final int address = labels.resolve(command.text, command.location);
					final int relative = address - pc;
					if (!isValidRelative(relative)) {
						commands.set(i, Command.lazyContent3(higherNibble + 0x0D, command.text, command.location));
						pc++;
						changed = true;
					}
					else if (finalize) {
						commands.set(i, Command.lazyContent2(higherNibble + 0x0B, command.text, command.location));
					}
				}
				else {
					// jump to absolute target
					Utils.assertTrue(command.size == 3);
					final int addrHi = command.get(1);
					final int addrLo = command.get(2);
					final int address = (addrHi << 8) + addrLo;
					final int relative = address - pc;
					if (!isValidRelative(relative)) {
						commands.set(i, Command.content3(higherNibble + 0x0D, addrHi, addrLo));
						pc++;
						changed = true;
					}
					else if (finalize) {
						commands.set(i, Command.content2(higherNibble + 0x0B, relative));
					}
				}
			}
			case ALIGN -> {
				final int offset = getAlignmentOffset(command.get16bitValue(), pc);
				pc += offset;
			}
			default -> throw new IllegalStateException("Unsupported command " + command);
			}
		}
		labels.finishedInitialization();
		return changed;
	}

	private int getAlignmentOffset(int alignment, int pc) {
		Utils.assertTrue(alignment > 0);
		final int maxPc = pc + alignment - 1;
		final int remainder = maxPc % alignment;
		return maxPc - remainder - pc;
	}

	private void resolveLazyCommands(@NotNull List<Command> commands, @NotNull Labels labels, @NotNull WarningOut out) {
		int pc = 0;
		for (int i = 0; i < commands.size(); i++) {
			final Command command = commands.get(i);
			switch (command.type) {
			case LABEL -> {
				labels.processing(command.text);
				commands.set(i, null);
			}
			case ORG -> {
				pc = command.get16bitValue();
				commands.set(i, null);
			}
			case CONTENT -> pc += command.size;
			case ALIGN -> {
				final int offset = getAlignmentOffset(command.get16bitValue(), pc);
				if (offset > 0) {
					final byte[] bytes = new byte[offset];
					Arrays.fill(bytes, (byte)command.get(2));
					commands.set(i, Command.content(bytes));
					pc += offset;
				}
				else {
					commands.set(i, null);
				}
			}
			case LAZY_CONTENT -> {
				final Command resolvedCommand = resolveLazyCommand(command, pc + command.size, labels, out);
				pc += resolvedCommand.size;
				commands.set(i, resolvedCommand);
			}
			default -> throw new IllegalStateException("Unsupported command " + command);
			}
		}
	}

	@NotNull
	private Command resolveLazyCommand(@NotNull Command command, int pc, @NotNull Labels labels, @NotNull WarningOut out) {
		Utils.assertTrue(command.type == Command.Type.LAZY_CONTENT);

		final int opCode = command.get(0);
		final int lowerNibble = opCode & 0x0F;
		if (lowerNibble == 0x0A || lowerNibble == 0x0B) {
			Utils.assertTrue(command.size == 2);
			final int address = labels.resolve(command.text, command.location);
			final int relative = address - pc;
			if (!isValidRelative(relative)) {
				Utils.assertTrue(lowerNibble == 0x0A); // can't happen for JR, because it already should have been turned into a JP
				throw new SyntaxException("Target '" + command.text + "' too far away (" + relative + ")", command.location);
			}
			return Command.content2(opCode, relative);
		}

		if (lowerNibble == 0x0C) {
			Utils.assertTrue(command.size == 2);
			final boolean defaultHigh = (opCode & 0x10) == 0;
			final int value = resolveLabelHighOrLow(defaultHigh, command.text, command.location, labels);
			return Command.content2(opCode, value);
		}

		if (lowerNibble == 0x0D) {
			Utils.assertTrue(command.size == 3);

			final int address = labels.resolve(command.text, command.location);
			if (isValidRelative(address - pc)) {
				out.print(command.location + ": jp could be jr");
			}

			return Command.content3(opCode, address >> 8, address);
		}

		final int higherNibble = opCode & 0xF0;
		if (higherNibble <= 0x70) {
			if (lowerNibble == 0x06 || lowerNibble == 0x07) {
				final int register = command.get(1);
				final boolean defaultHigh = (register & 1) == 0;
				final int value = resolveLabelHighOrLow(defaultHigh, command.text, command.location, labels);
				return Command.content3(opCode, register, value);
			}
		}

		if (opCode == 0xD6) {
			Utils.assertTrue(command.size == 3);
			final int address = labels.resolve(command.text, command.location);
			return Command.content3(opCode, address >> 8, address);
		}

		if (opCode == 0xE6) {
			Utils.assertTrue(command.size == 3);
			final int register = command.get(1);
			final boolean defaultHigh = (register & 1) == 0;
			final int value = resolveLabelHighOrLow(defaultHigh, command.text, command.location, labels);
			return Command.content3(opCode, register, value);
		}

		if (opCode == 0) {
			Utils.assertTrue(command.size == 2);
			final int address = labels.resolve(command.text, command.location);
			return Command.content2(address >> 8, address);
		}

		throw new IllegalStateException("Unsupported command " + command);
	}

	private int resolveLabelHighOrLow(boolean high, @NotNull String text, @NotNull Location location, @NotNull Labels labels) {
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
