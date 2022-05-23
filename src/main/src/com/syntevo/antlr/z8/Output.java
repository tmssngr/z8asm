package com.syntevo.antlr.z8;

import java.io.IOException;
import java.io.Writer;

/**
 * @author Thomas Singer
 */
public final class Output {

	// Fields =================================================================

	private final byte[] buffer;

	private int pc;

	// Setup ==================================================================

	public Output() {
		buffer = new byte[2 << 16];
	}

	// Accessing ==============================================================

	public int getPc() {
		return pc;
	}

	public void write(int value) {
		buffer[pc] = (byte) value;
		pc++;
	}

	public void print(Writer writer) throws IOException {
		boolean newline = false;
		for (int i = 0; i < pc; i++) {
			if (i % 16 == 0) {
				if (newline) {
					writer.write("\n");
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
			writer.write("\n");
		}
	}

	public void printFrom(int pc) {
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

	private String toHex(int value, int digits) {
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
}
