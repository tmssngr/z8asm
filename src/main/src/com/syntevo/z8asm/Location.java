package com.syntevo.z8asm;

import org.jetbrains.annotations.*;

/**
 * @author Thomas Singer
 */
public record Location(int line, int column) {

	public static final Location DUMMY = new Location(-1, -1);

	@NotNull
	@Override
	public String toString() {
		return (line + 1) + ":" + (column + 1);
	}
}
