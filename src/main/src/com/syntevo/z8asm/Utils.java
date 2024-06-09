package com.syntevo.z8asm;

/**
 * @author Thomas Singer
 */
public class Utils {
	public static void assertTrue(boolean value) {
		if (!value) {
			throw new IllegalStateException();
		}
	}
}
