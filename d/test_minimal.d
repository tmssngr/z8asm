// Minimal test to check basic compilation
import z8asm.lexer;
import z8asm.token_type;
import z8asm.location;

void main() {
	Lexer lexer = new Lexer("LD R0, #10");
	TokenType token;
	do {
		token = lexer.next();
		import std.stdio;
		writeln("Token: ", token);
	} while (token != TokenType.EOF);
}
