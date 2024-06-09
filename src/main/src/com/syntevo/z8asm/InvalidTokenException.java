package com.syntevo.z8asm;

/**
 * @author Thomas Singer
 */
public final class InvalidTokenException extends RuntimeException {

	public final Location location;

	public InvalidTokenException(String message, Location location) {
		super(message);
		this.location = location;
	}
}
