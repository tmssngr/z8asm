// InvalidTokenException.d - translated from Java
module z8asm.invalid_token_exception;

import z8asm.location;

class InvalidTokenException : Exception {
	Location location;

	this(string message, Location location) {
		super(message);
		this.location = location;
	}

	override string toString() {
		return location.toString ~ " " ~ super.toString;
	}
}
