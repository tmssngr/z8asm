package com.syntevo.z8asm;

/**
 * @author Thomas Singer
 */
public record Location(int line, int column) {

	public static final Location DUMMY = new Location(-1, -1);

	@Override
	public String toString() {
		return (line + 1) + ":" + (column + 1);
	}
}
