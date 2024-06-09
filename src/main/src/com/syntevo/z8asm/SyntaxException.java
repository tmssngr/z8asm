package com.syntevo.z8asm;

/**
 * @author Thomas Singer
 */
public final class SyntaxException extends RuntimeException {
	public final Location location;

	public SyntaxException(String msg, Location location) {
		super(msg);
		this.location = location;
	}

	@Override
	public String toString() {
		return location + " " + getMessage();
	}
}
