package com.syntevo.z8asm;

import java.util.*;

import org.jetbrains.annotations.*;

/**
 * @author Thomas Singer
 */
public final class Command {

	public static Command label(@NotNull String name, @NotNull Location location) {
		return new Command(Type.LABEL, 0, 0, 0, 0, name, location);
	}

	public static Command org(int value, @NotNull Location location) {
		return new Command(Type.ORG, value, 0, 0, 0, "", location);
	}

	public static Command content1(int first) {
		return new Command(Type.CONTENT, first, 0, 0, 1, "", Location.DUMMY);
	}

	public static Command content2(int first, int second) {
		return new Command(Type.CONTENT, first, second, 0, 2, "", Location.DUMMY);
	}

	public static Command content3(int first, int second, int third) {
		return new Command(Type.CONTENT, first, second, third & 0xFF, 3, "", Location.DUMMY);
	}

	public static Command lazyContent2(int first, @NotNull String text, @NotNull Location location) {
		return new Command(Type.LAZY_CONTENT, first, 0, 0, 2, text, location);
	}

	public static Command lazyContent3(int first, @NotNull String text, @NotNull Location location) {
		return new Command(Type.LAZY_CONTENT, first, 0, 0, 3, text, location);
	}

	public final Type type;
	public final int first;
	public final int second;
	public final int third;
	public final int size;
	public final String text;
	public final Location location;

	private Command(@NotNull Type type, int first, int second, int third, int size, @NotNull String text, Location location) {
		this.type = type;
		this.first = first;
		this.second = second;
		this.third = third;
		this.size = size;
		this.text = text;
		this.location = location;
	}

	@Override
	public String toString() {
		return "Command{" +
		       "type=" + type +
		       ", first=" + first +
		       ", second=" + second +
		       ", third=" + third +
		       ", size=" + size +
		       ", text='" + text + '\'' +
		       ", location=" + location +
		       "}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Command command = (Command)o;
		return first == command.first && second == command.second && third == command.third && size == command.size && type == command.type && Objects.equals(text, command.text) && Objects.equals(location, command.location);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, first, second, third, size, text, location);
	}

	public enum Type {
		LABEL, ORG, CONTENT, LAZY_CONTENT
	}
}
