module main;

import std.stdio : writeln;

import z8asm.assembler : Assembler;
import z8asm.output : Output;
import z8asm.parser : Parser;

void main(string[] args) {
	if (args.length != 2) {
		writeln("asm-file missing");
		return;
	}

	auto commands = Parser.parseFromFile(args[1]);
	auto assembled = Assembler.assemble(commands);
	Output.create(assembled).writeToFile("output.bin");
}
