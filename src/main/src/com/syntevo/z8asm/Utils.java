package com.syntevo.z8asm;

import java.nio.file.*;

import org.jetbrains.annotations.*;

/**
 * @author Thomas Singer
 */
public class Utils {
	public static void assertTrue(boolean value) {
		if (!value) {
			throw new IllegalStateException();
		}
	}

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

	public static String toHex8(int value) {
		return toHex(value, 2);
	}

	@NotNull
	public static String getFileNameWithoutSuffix(Path file) {
		String fileName = file.getFileName().toString();
		final int lastDot = fileName.lastIndexOf('.');
		if (lastDot > 0) {
			fileName = fileName.substring(0, lastDot);
		}
		return fileName;
	}
}
