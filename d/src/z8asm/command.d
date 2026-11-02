// Command.d - translated from Java
module z8asm.command;

import std.conv : to;
import std.array : Appender;
import core.stdc.stdlib : qsort;

import z8asm.location : Location;
import z8asm.token_type : TokenType;
import z8asm.utils : Utils;

class Command {
	public enum Type {
		LABEL,
		ORG,
		CONTENT,
		ALIGN,
		LAZY_CONTENT,
		AUTO_JUMP
	}

	Type type;
	byte[] values;
	int size;
	string text;
	Location location;

	static Command label(string name, Location location) {
		return new Command(Type.LABEL, null, 0, name, location);
	}

	static Command org(int value, Location location) {
		return new Command(Type.ORG, [cast(byte)(value / 0x100), cast(byte)value], 0, "", location);
	}

	static Command content1(int first) {
		return new Command(Type.CONTENT, [cast(byte)first], 1, "", Location.DUMMY);
	}

	static Command content2(int first, int second) {
		return new Command(Type.CONTENT, [cast(byte)first, cast(byte)second], 2, "", Location.DUMMY);
	}

	static Command content3(int first, int second, int third) {
		return new Command(Type.CONTENT, [cast(byte)first, cast(byte)second, cast(byte)third], 3, "", Location.DUMMY);
	}

	static Command content(byte[] bytes) {
		Utils.assertTrue(bytes.length > 0);
		return new Command(Type.CONTENT, bytes.dup, cast(int)bytes.length, "", Location.DUMMY);
	}

	static Command lazyContent2(int first, string text, Location location) {
		return new Command(Type.LAZY_CONTENT, [cast(byte)first, 0], 2, text, location);
	}

	static Command lazyContent3(int first, string text, Location location) {
		return lazyContent3(first, 0, text, location);
	}

	static Command lazyContent3(int first, int second, string text, Location location) {
		return new Command(Type.LAZY_CONTENT, [cast(byte)first, cast(byte)second, 0], 3, text, location);
	}

	static Command autoJump(int jrOpCode, string target, Location location) {
		return new Command(Type.AUTO_JUMP, [cast(byte)jrOpCode], 1, target, location);
	}

	static Command autoJump(int jrOpCode, int address) {
		return new Command(Type.AUTO_JUMP, [cast(byte)jrOpCode, cast(byte)(address >> 8), cast(byte)address], 3, "", Location.DUMMY);
	}

	static Command createAlign(int alignment, int fillByte) {
		return new Command(Type.ALIGN, [cast(byte)(alignment >> 8), cast(byte)alignment, cast(byte)fillByte], 0, "", Location.DUMMY);
	}

	this(Type type, byte[] values, int size, string text, Location location) {
		this.type = type;
		this.values = values;
		this.size = size;
		this.text = text;
		this.location = location;
	}

	override bool opEquals(Object o) {
		if (o is null || typeid(this) != typeid(o)) {
			return false;
		}
		Command command = cast(Command)o;
		if (size != command.size) return false;
		if (type != command.type) return false;
		if (text != command.text) return false;
		if (location != command.location) return false;
		if (values is null) {
			return command.values is null;
		}
		if (command.values is null) {
			return false;
		}
		if (values.length != command.values.length) {
			return false;
		}
		foreach (i, ref val; values) {
			if (val != command.values[i]) {
				return false;
			}
		}
		return true;
	}

	override size_t toHash() {
		// Simple hash implementation
		size_t h = cast(size_t)type;
		if (values !is null) {
			foreach (v; values) {
				h = h * 31 + cast(size_t)v;
			}
		}
		h = h * 31 + cast(size_t)size;
		if (text !is null) {
			foreach (c; text) {
				h = h * 31 + cast(size_t)c;
			}
		}
		h = h * 31 + cast(size_t)location.line;
		h = h * 31 + cast(size_t)location.column;
		return h;
	}

	override string toString() {
		string result = "Command{type=" ~ to!string(type);
		result = result ~ ", values=";
		if (values is null) {
			result = result ~ "null";
		} else {
			result = result ~ "[";
			for (int i = 0; i < values.length; i++) {
				if (i > 0) result = result ~ ", ";
				result = result ~ Utils.toHex8(values[i]);
			}
			result = result ~ "]";
		}
		result = result ~ ", size=" ~ to!string(size);
		result = result ~ ", text='" ~ text ~ "'";
		result = result ~ ", location=" ~ location.toString();
		result = result ~ "}";
		return result;
	}

	int get(int index) {
		if (values is null) {
			throw new Exception("values is null");
		}
		return cast(int)values[index] & 0xFF;
	}

	int get16bitValue() {
		return (get(0) << 8) + get(1);
	}
}
