package com.syntevo.antlr.z8;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * @author Thomas Singer
 */
public final class Output {

	// Constants ==============================================================

	public static final String NL = System.getProperty("line.separator", "\n");

	// Static =================================================================

	public static String toHex(int value, int digits) {
		final StringBuilder builder = new StringBuilder(digits);
		while (digits-- > 0) {
			final int nibble = value & 0xF;
			value >>= 4;
			final int chr = nibble > 9
					? 'a' + nibble - 10
					: '0' + nibble;
			builder.insert(0, (char) chr);
		}
		return builder.toString();
	}

	// Fields =================================================================

	private final byte[] buffer;

	private int offset;
	private int pc;

	// Setup ==================================================================

	public Output() {
		buffer = new byte[2 << 16];
	}

	// Accessing ==============================================================

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
		boolean newline = false;
		for (int i = 0; i < pc; i++) {
			if (i % 16 == 0) {
				if (newline) {
					writer.write(NL);
				}
				writer.write(toHex(i, 4));
			}
			if (i % 8 == 0) {
				writer.write(" ");
			}
			writer.write(" ");
			writer.write(byteToHex(i));
			newline = true;
		}

		if (newline) {
			writer.write(NL);
		}
	}

	public int printC(Writer writer) throws IOException {
		for (int i = 0; i < pc; i++) {
			if (i % 16 == 0) {
				writer.write("\t");
			}

			writer.write("0x");
			writer.write(byteToHex(i));

			final boolean isLast = i + 1 == pc;
			if (isLast) {
				writer.write("  ");
			}
			else {
				writer.write(", ");
			}
			if (isLast || i % 16 == 15) {
				writer.write("// ");
				writer.write(toHex((i - 1) & ~15, 4));
				writer.write(NL);
			}
		}
		return pc;
	}

	public void printFrom(int pc) {
		pc -= offset;
		int count = this.pc - pc;
		if (count < 0 || count > 1000) {
			throw new IllegalStateException(String.valueOf(count));
		}

		System.out.print("  ");
		System.out.print(toHex(pc, 4));
		System.out.print(" ");
		while (count-- > 0) {
			System.out.print(" ");
			System.out.print(byteToHex(pc++));
		}
	}

	// Utils ==================================================================

	private String byteToHex(int i) {
		return toHex(buffer[i], 2);
	}
}
