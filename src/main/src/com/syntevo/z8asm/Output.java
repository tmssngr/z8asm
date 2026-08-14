package com.syntevo.z8asm;

import java.io.*;
import java.util.*;

import org.jetbrains.annotations.*;

/**
 * @author Thomas Singer
 */
public final class Output {

	public static final String NL = System.getProperty("line.separator", "\n");

	@NotNull
	public static Output create(@NotNull List<Command> commands) {
		final Output output = new Output();

		for (Command command : commands) {
			if (command.type != Command.Type.CONTENT) {
				throw new IllegalStateException(command.toString());
			}

			for (int i = 0; i < command.size; i++) {
				output.write(command.get(i));
			}
		}
		return output;
	}

	private final byte[] buffer;

	private int offset;
	private int pc;

	public Output() {
		buffer = new byte[2 << 16];
	}

	public int getPc() {
		return pc + offset;
	}

	public void setPc(int pc) {
		if (offset != 0 || this.pc != 0) {
			throw new IllegalStateException();
		}

		offset = pc;
	}

	public void write(int value) {
		buffer[pc] = (byte) value;
		pc++;
	}

	public void write(OutputStream stream) throws IOException {
		stream.write(buffer, 0, pc);
	}

	public void print(Writer writer) throws IOException {
		print(writer, NL);
	}

	public void print(Writer writer, String newLine) throws IOException {
		boolean newline = false;
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

	public int printC(Writer writer) throws IOException {
		for (int i = 0; i < pc; i++) {
			if (i % 16 == 0) {
				writer.write("\t");
			}

			writer.write("0x");
			writer.write(Utils.toHex8(buffer[i]));

			final boolean isLast = i + 1 == pc;
			if (isLast) {
				writer.write("  ");
			}
			else {
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

	public void printVerilog(Writer writer) throws IOException {
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
			throw new IllegalStateException(String.valueOf(count));
		}

		System.out.print("  ");
		System.out.print(Utils.toHex(pc, 4));
		System.out.print(" ");
		while (count-- > 0) {
			System.out.print(" ");
			final int i = pc++;
			System.out.print(Utils.toHex8(buffer[i]));
		}
	}
}
