// Output.d - translated from Java
module z8asm.output;

import std.conv : to;
import std.array : Appender;
import std.range;
import std.file : writeFile = write;

import z8asm.command : Command;
import z8asm.utils : Utils;

class Output {
	public static const(string) NL = "\n";

	private {
		byte[] buffer;
		int offset;
		int pc;
	}

	public this() {
		buffer = new byte[2 << 16];
	}

	public static Output create(Command[] commands) {
		Output output = new Output();

		foreach (command; commands) {
			if (command.type != Command.Type.CONTENT) {
				throw new Exception(command.toString);
			}

			for (int i = 0; i < command.size; i++) {
				output.write(command.get(i));
			}
		}
		return output;
	}

	public int getPc() {
		return pc + offset;
	}

	public void setPc(int pc) {
		if (offset != 0 || this.pc != 0) {
			throw new Exception("Cannot set PC after writing");
		}

		offset = pc;
	}

	public void write(int value) {
		buffer[pc] = cast(byte)value;
		pc++;
	}

	public void writeOutputStream(OutputStream stream) {
		stream.write(buffer[0 .. pc]);
	}

	public void writeToFile(string file) {
		writeFile(file, buffer[0 .. pc]);
	}

	public void print(Writer writer) {
		print(writer, NL);
	}

	public void print(Writer writer, string newLine) {
		bool newline = false;
		for (int i = 0; i < pc; i++) {
			if (i % 16 == 0) {
				if (newline) {
					writer.write(newLine);
				}
				writer.write(Utils.toHex(i, 4));
			}
			if (i % 8 == 0) {
				writer.write(" ");
			}
			writer.write(" ");
			writer.write(Utils.toHex8(buffer[i]));
			newline = true;
		}

		if (newline) {
			writer.write(newLine);
		}
	}

	public int printC(Writer writer) {
		for (int i = 0; i < pc; i++) {
			if (i % 16 == 0) {
				writer.write("\t");
			}

			writer.write("0x");
			writer.write(Utils.toHex8(buffer[i]));

			bool isLast = i + 1 == pc;
			if (isLast) {
				writer.write("  ");
			} else {
				writer.write(", ");
			}
			if (isLast || i % 16 == 15) {
				writer.write("// ");
				writer.write(Utils.toHex((i - 1) & ~15, 4));
				writer.write(NL);
			}
		}
		return pc;
	}

	public void printVerilog(Writer writer) {
		for (int i = 0; i < pc; i++) {
			writer.write("memory[16'h");
			writer.write(Utils.toHex(offset + i, 4));
			writer.write("] = 8'h");
			writer.write(Utils.toHex8(buffer[i]));
			writer.write(";");
			writer.write(NL);
		}
	}

	public void printFrom(int pc) {
		pc -= offset;
		int count = this.pc - pc;
		if (count < 0 || count > 1000) {
			throw new Exception(to!string(count));
		}

		import std.stdio : writeln, write, writef;
		write("  ");
		write(Utils.toHex(pc, 4));
		write(" ");
		while (count-- > 0) {
			write(" ");
			int i = pc++;
			write(Utils.toHex8(buffer[i]));
		}
	}
}

// Interfaces for I/O
interface Writer {
	void write(string s);
}

interface OutputStream {
	void write(byte[] data);
}
