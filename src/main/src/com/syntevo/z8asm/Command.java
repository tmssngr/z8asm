package com.syntevo.z8asm;

import java.util.*;

import org.jetbrains.annotations.*;

/**
 * @author Thomas Singer
 */
public sealed interface Command permits Command.Align, Command.Content, Command.AutoJumpAddress, Command.AutoJumpString, Command.Label, Command.LazyContent2, Command.LazyContent3, Command.Org {

	record Align(int alignment, byte fillByte) implements Command {
	}

	record AutoJumpString(byte jrOpCode, @NotNull String target, @NotNull Location location) implements Command {
	}

	record AutoJumpAddress(byte jrOpCode, int address) implements Command {
	}

	record Content(byte[] bytes) implements Command {
		public Content {
			Utils.assertTrue(bytes.length > 0);
		}

		public Content(int first) {
			this(new byte[] {(byte)first});
		}

		public Content(int first, int second) {
			this(new byte[] {(byte)first, (byte)second});
		}

		public Content(int first, int second, int third) {
			this(new byte[] {(byte)first, (byte)second, (byte)third});
		}

		@Override
		public boolean equals(Object o) {
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			final Content content = (Content)o;
			return Objects.deepEquals(bytes, content.bytes);
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(bytes);
		}

		public int size() {
			return bytes.length;
		}
	}

	record Label(@NotNull String name, @NotNull Location location) implements Command {
	}

	record LazyContent2(int opCode, @NotNull String text, @NotNull Location location) implements Command {
	}

	record LazyContent3(int opCode, int second, @NotNull String text, @NotNull Location location) implements Command {
		public LazyContent3(int opCode, @NotNull String text, @NotNull Location location) {
			this(opCode, 0, text, location);
		}
	}

	record Org(int org, @NotNull Location location) implements Command {
	}
}
