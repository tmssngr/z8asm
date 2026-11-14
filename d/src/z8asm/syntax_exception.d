// SyntaxException.d - translated from Java
module z8asm.syntax_exception;

import z8asm.location : Location;

class SyntaxException : Exception {
	Location location;

	this(string msg, Location location) {
		super(msg);
		this.location = location;
	}

	override string toString() {
		return location.toString ~ " " ~ msg;
	}
}
