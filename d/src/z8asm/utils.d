// Utils.d - translated from Java
module z8asm.utils;

import std.conv : to;
import std.array : Appender;

class Utils {
	static void assertTrue(bool value) {
		if (!value) {
			throw new Exception("Assertion failed");
		}
	}

	static string toHex(int value, int digits) {
		Appender!(char[]) buffer;
		buffer.reserve(digits);
		string result = "";
		while (digits-- > 0) {
			int nibble = value & 0xF;
			value >>= 4;
			char chr = cast(char)(nibble > 9 ? 'a' + (nibble - 10) : '0' + nibble);
			result = chr ~ result;
		}
		return result;
	}

	static string toHex8(int value) {
		return toHex(value, 2);
	}
}
