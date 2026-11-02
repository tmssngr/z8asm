// Assembler.d - translated from Java
module z8asm.assembler;

import std.string : format, toLower, startsWith, endsWith;
import std.conv : to;
import std.array : Appender;
import std.stdio : writeln;

import z8asm.command : Command;
import z8asm.location : Location;
import z8asm.labels : Labels;
import z8asm.warning_out : WarningOut;
import z8asm.syntax_exception : SyntaxException;
import z8asm.utils : Utils;

class Assembler {
	public static Command[] assemble(Command[] commands) {
		Assembler assembler = new Assembler();
		return assembler.assemble(commands, new PrintWarningOut());
	}

	public this() {
	}

	public Command[] assemble(Command[] commands, WarningOut warningOut) {
		commands = commands.dup;
		Labels labels = determineLabels(commands);
		resolveAutoJumps(commands, labels);
		resolveLazyCommands(commands, labels, warningOut);
		labels.reportUnused(warningOut);
		Appender!(Command[]) filtered;
		foreach (cmd2; commands) {
			if (cmd2 !is null) {
				filtered.put(cmd2);
			}
		}
		return filtered.data;
	}

	private Labels determineLabels(Command[] commands) {
		Labels labels = new Labels();
		bool allowOrg = true;
		int pc = 0;
		foreach (cmd; commands) {
			switch (cmd.type) {
			case Command.Type.LABEL:
				labels.add(cmd.text, pc, cmd.location);
				allowOrg = false;
				break;
			case Command.Type.ORG:
				if (!allowOrg) {
					throw new SyntaxException(".org is only allowed before any content or label", cmd.location);
				}
				pc = cmd.get16bitValue();
				break;
			case Command.Type.CONTENT:
			case Command.Type.LAZY_CONTENT:
				allowOrg = false;
				pc += cmd.size;
				break;
			case Command.Type.AUTO_JUMP:
				Utils.assertTrue(cmd.size == 1 || cmd.size == 3);
				pc += 2;
				break;
			case Command.Type.ALIGN:
				int offset = getAlignmentOffset(cmd.get16bitValue(), pc);
				pc += offset;
				break;
			default:
				throw new Exception("Unsupported command " ~ cmd.toString());
			}
		}
		labels.finishedInitialization();
		return labels;
	}

	private void resolveAutoJumps(ref Command[] commands, Labels labels) {
		while (true) {
			if (!resolveAutoJumps(commands, labels, false)) {
				break;
			}
		}

		resolveAutoJumps(commands, labels, true);
	}

	private bool resolveAutoJumps(ref Command[] commands, Labels labels, bool finalize) {
		int pc = 0;
		bool changed = false;
		for (int i = 0; i < commands.length; i++) {
			Command command = commands[i];
			if (command is null) continue;

			switch (command.type) {
			case Command.Type.LABEL:
				if (labels.update(command.text, pc)) {
					changed = true;
				}
				break;
			case Command.Type.ORG:
				pc = command.get16bitValue();
				break;
			case Command.Type.CONTENT:
			case Command.Type.LAZY_CONTENT:
				pc += command.size;
				break;
			case Command.Type.AUTO_JUMP:
				int opCode = command.get(0);
				int higherNibble = opCode & 0xF0;
				pc += 2;
				if (command.size == 1) {
					// jump to text target
					int address = labels.resolve(command.text, command.location);
					int relative = address - pc;
					if (!isValidRelative(relative)) {
						commands[i] = Command.lazyContent3(higherNibble + 0x0D, command.text, command.location);
						pc++;
						changed = true;
					} else if (finalize) {
						commands[i] = Command.lazyContent2(higherNibble + 0x0B, command.text, command.location);
					}
				} else {
					// jump to absolute target
					Utils.assertTrue(command.size == 3);
					int addrHi = command.get(1);
					int addrLo = command.get(2);
					int address = (addrHi << 8) + addrLo;
					int relative = address - pc;
					if (!isValidRelative(relative)) {
						commands[i] = Command.content3(higherNibble + 0x0D, addrHi, addrLo);
						pc++;
						changed = true;
					} else if (finalize) {
						commands[i] = Command.content2(higherNibble + 0x0B, relative);
					}
				}
				break;
			case Command.Type.ALIGN:
				int offset = getAlignmentOffset(command.get16bitValue(), pc);
				pc += offset;
				break;
			default:
				throw new Exception("Unsupported command " ~ command.toString());
			}
		}
		labels.finishedInitialization();
		return changed;
	}

	private int getAlignmentOffset(int alignment, int pc) {
		Utils.assertTrue(alignment > 0);
		int maxPc = pc + alignment - 1;
		int remainder = maxPc % alignment;
		return maxPc - remainder - pc;
	}

	private void resolveLazyCommands(ref Command[] commands, Labels labels, WarningOut warningOut) {
		int pc = 0;
		for (int i = 0; i < commands.length; i++) {
			Command command = commands[i];
			if (command is null) continue;

			switch (command.type) {
			case Command.Type.LABEL:
				labels.processing(command.text);
				commands[i] = null;
				break;
			case Command.Type.ORG:
				pc = command.get16bitValue();
				commands[i] = null;
				break;
			case Command.Type.CONTENT:
				pc += command.size;
				break;
			case Command.Type.ALIGN:
				int offset = getAlignmentOffset(command.get16bitValue(), pc);
				if (offset > 0) {
					byte[] bytes = new byte[offset];
					foreach (ref b; bytes) {
						b = cast(byte)command.get(2);
					}
					commands[i] = Command.content(bytes);
					pc += offset;
				} else {
					commands[i] = null;
				}
				break;
			case Command.Type.LAZY_CONTENT:
				Command resolvedCommand = resolveLazyCommand(command, pc + command.size, labels, warningOut);
				pc += resolvedCommand.size;
				commands[i] = resolvedCommand;
				break;
			default:
				throw new Exception("Unsupported command " ~ command.toString());
			}
		}
	}

	private Command resolveLazyCommand(Command command, int pc, Labels labels, WarningOut warningOut) {
		Utils.assertTrue(command.type == Command.Type.LAZY_CONTENT);

		int opCode = command.get(0);
		int lowerNibble = opCode & 0x0F;
		if (lowerNibble == 0x0A || lowerNibble == 0x0B) {
			Utils.assertTrue(command.size == 2);
			int address = labels.resolve(command.text, command.location);
			int relative = address - pc;
			if (!isValidRelative(relative)) {
				Utils.assertTrue(lowerNibble == 0x0A); // can't happen for JR, because it already should have been turned into a JP
				throw new SyntaxException("Target '" ~ command.text ~ "' too far away (" ~ to!string(relative) ~ ")", command.location);
			}
			return Command.content2(opCode, relative);
		}

		if (lowerNibble == 0x0C) {
			Utils.assertTrue(command.size == 2);
			bool defaultHigh = (opCode & 0x10) == 0;
			int value = resolveLabelHighOrLow(defaultHigh, command.text, command.location, labels);
			return Command.content2(opCode, value);
		}

		if (lowerNibble == 0x0D) {
			Utils.assertTrue(command.size == 3);

			int address = labels.resolve(command.text, command.location);
			if (isValidRelative(address - pc)) {
				warningOut.print(command.location.toString ~ ": jp could be jr");
			}

			return Command.content3(opCode, address >> 8, address);
		}

		int higherNibble = opCode & 0xF0;
		if (higherNibble <= 0x70) {
			if (lowerNibble == 0x06 || lowerNibble == 0x07) {
				int register = command.get(1);
				bool defaultHigh = (register & 1) == 0;
				int value = resolveLabelHighOrLow(defaultHigh, command.text, command.location, labels);
				return Command.content3(opCode, register, value);
			}
		}

		if (opCode == 0xD6) {
			Utils.assertTrue(command.size == 3);
			int address = labels.resolve(command.text, command.location);
			return Command.content3(opCode, address >> 8, address);
		}

		if (opCode == 0xE6) {
			Utils.assertTrue(command.size == 3);
			int register = command.get(1);
			bool defaultHigh = (register & 1) == 0;
			int value = resolveLabelHighOrLow(defaultHigh, command.text, command.location, labels);
			return Command.content3(opCode, register, value);
		}

		if (opCode == 0) {
			Utils.assertTrue(command.size == 2);
			int address = labels.resolve(command.text, command.location);
			return Command.content2(address >> 8, address);
		}

		throw new Exception("Unsupported command " ~ command.toString());
	}

	private int resolveLabelHighOrLow(bool high, string text, Location location, Labels labels) {
		string textLower = text.toLower();
		if (textLower.length == text.length && textLower.endsWith(")")) {
			if (textLower.startsWith("lo(")) {
				text = text[3 .. text.length - 1];
				high = false;
			} else if (textLower.startsWith("hi(")) {
				text = text[3 .. text.length - 1];
				high = true;
			}
		}
		int address = labels.resolve(text, location);
		return high ? address >> 8 : address;
	}

	private static bool isValidRelative(int relative) {
		return relative >= -128 && relative <= 127;
	}
}

// Helper class for warning output
private class PrintWarningOut : WarningOut {
	override void print(string text) {
		import std.stdio : writeln;
		writeln(text);
	}
}
