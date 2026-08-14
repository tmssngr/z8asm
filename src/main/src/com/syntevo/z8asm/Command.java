package com.syntevo.z8asm;

import java.util.*;

import org.jetbrains.annotations.*;

/**
 * @author Thomas Singer
 */
public final class Command {

	public static Command label(@NotNull String name, @NotNull Location location) {
		return new Command(Type.LABEL, null, 0, name, location);
	}

	public static Command org(int value, @NotNull Location location) {
		return new Command(Type.ORG, new byte[] {(byte)(value / 0x100), (byte)value}, 0, "", location);
	}

	public static Command content1(int first) {
		return new Command(Type.CONTENT, new byte[] {(byte)first}, 1, "", Location.DUMMY);
	}

	public static Command content2(int first, int second) {
		return new Command(Type.CONTENT, new byte[] {(byte)first, (byte)second}, 2, "", Location.DUMMY);
	}

	public static Command content3(int first, int second, int third) {
		return new Command(Type.CONTENT, new byte[] {(byte)first, (byte)second, (byte)third}, 3, "", Location.DUMMY);
	}

	public static Command content(byte[] bytes) {
		Utils.assertTrue(bytes.length > 0);
		return new Command(Type.CONTENT, Arrays.copyOf(bytes, bytes.length), bytes.length, "", Location.DUMMY);
	}

	public static Command lazyContent2(int first, @NotNull String text, @NotNull Location location) {
		return new Command(Type.LAZY_CONTENT, new byte[]{(byte)first, 0}, 2, text, location);
	}

	public static Command lazyContent3(int first, @NotNull String text, @NotNull Location location) {
		return lazyContent3(first, 0, text, location);
	}

	public static Command lazyContent3(int first, int second, @NotNull String text, @NotNull Location location) {
		return new Command(Type.LAZY_CONTENT, new byte[] {(byte)first, (byte)second, 0}, 3, text, location);
	}

	public static Command align(int alignment, int fillByte) {
		return new Command(Type.ALIGN, new byte[] {(byte)(alignment >> 8), (byte)alignment, (byte)fillByte}, 0, "", Location.DUMMY);
	}

	public final Type type;
	@Nullable private final byte[] values;
	public final int size;
	public final String text;
	public final Location location;

	private Command(@NotNull Type type, @Nullable byte[] values, int size, @NotNull String text, Location location) {
		this.type = type;
		this.values = values;
		this.size = size;
		this.text = text;
		this.location = location;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Command command = (Command)o;
		return size == command.size
		       && type == command.type
		       && Objects.equals(text, command.text)
		       && Objects.deepEquals(values, command.values)
		       && Objects.equals(location, command.location);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, Arrays.hashCode(values), size, text, location);
	}

	@NotNull
	@Override
	public String toString() {
		final StringBuilder buffer = new StringBuilder();
		buffer.append("Command{");
		buffer.append("type=");
		buffer.append(type);
		buffer.append(", values=");
		if (values != null) {
			buffer.append("[");
			for (int i = 0; i < values.length; i++) {
				byte value = values[i];
				if (i > 0) {
					buffer.append(", ");
				}
				buffer.append(Utils.toHex8(value));
			}
			buffer.append("]");
		}
		else {
			buffer.append("null");
		}
		buffer.append(", size=");
		buffer.append(size);
		buffer.append(", text='");
		buffer.append(text);
		buffer.append('\'');
		buffer.append(", location=");
		buffer.append(location);
		buffer.append('}');
		return buffer.toString();
	}

	public int get(int index) {
		if (values == null) {
			throw new IllegalArgumentException();
		}
		return (int)values[index] & 0xFF;
	}

	public int get16bitValue() {
		return (get(0) << 8) + get(1);
	}

	public enum Type {
		LABEL, ORG, CONTENT, ALIGN, LAZY_CONTENT
	}
}
