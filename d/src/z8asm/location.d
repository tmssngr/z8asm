// Location.d - translated from Java
module z8asm.location;

import std.conv : to;

struct Location {
	int line;
	int column;

	this(int line, int column) {
		this.line = line;
		this.column = column;
	}

	static const DUMMY = Location(-1, -1);

	string toString() const {
		return to!string(line + 1) ~ ":" ~ to!string(column + 1);
	}
}
