package com.syntevo.z8asm;

import java.util.*;

import org.jetbrains.annotations.*;
import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 * @author Thomas Singer
 */
public class ParserTest {

	@Test
	public void testUnary() {
		assertUnary(0x00, "dec");
		assertUnary(0x10, "rlc");

		assertCommand(Command.content2(0x20, 0x60), "inc %60");
		assertCommand(Command.content1(0x2E), "inc r2");
		assertCommand(Command.content2(0x21, 0xE1), "inc @r1");
		assertCommand(Command.content2(0x20, 0x62), """
				.const FOO = %62
				inc FOO""");

		assertCommand(Command.content2(0x31, 0x40), "srp #%40");

		assertUnary(0x40, "da");
		assertUnary(0x50, "pop");
		assertUnary(0x60, "com");
		assertUnary(0x70, "push");
		assertUnary(0x80, "decw");
		assertUnary(0x90, "rl");
		assertUnary(0xA0, "incw");
		assertUnary(0xB0, "clr");
		assertUnary(0xC0, "rrc");
		assertUnary(0xD0, "sra");
		assertUnary(0xE0, "rr");
		assertUnary(0xF0, "swap");
	}

	@Test
	public void testBinary() {
		assertBinary(0x00, "add");
		assertBinary(0x10, "adc");
		assertBinary(0x20, "sub");
		assertBinary(0x30, "sbc");
		assertBinary(0x40, "or");
		assertBinary(0x50, "and");
		assertBinary(0x70, "tm");
		assertBinary(0xB0, "xor");
	}

	@Test
	public void testLd() {
		assertCommand(Command.content2(0x68, 0x5a), "LD R6, %5a");
		assertCommand(Command.content2(0x18, 0xE2), "LD R1, R2");
		assertCommand(Command.content2(0xFC, 0x16), "LD R15, #%16");
		assertCommand(Command.content2(0xE3, 0x26), "LD R2, @R6");
		assertCommand(Command.content2(0xF3, 0x74), "LD @R7, R4");
		assertCommand(Command.content3(0xE4, 0x15, 0x5A), "LD %5A, %15");
		assertCommand(Command.content3(0xE5, 0x45, 0x34), "LD %34, @%45");
		assertCommand(Command.content3(0xF5, 0x15, 0x5A), "LD @%5A, %15");
		assertCommand(Command.content3(0xE6, 0x5A, 0x3d), "LD %5A, #%3D");
		assertCommand(Command.content3(0xE7, 0x5A, 0x3d), "LD @%5A, #%3D");
		assertCommand(Command.content3(0xC7, 0x26, 0x9E), "LD R2, %9E(R6)");
		assertCommand(Command.content3(0xD7, 0xA0, 0xF0), "LD %F0(R0), R10");
	}

	@Test
	public void testLdX() {
		assertCommand(Command.content2(0x82, 0x60), "LDE R6, @RR0");
		assertCommand(Command.content2(0x92, 0x60), "LDE @RR0, R6");
		assertCommand(Command.content2(0xC2, 0x60), "LDC R6, @RR0");
		assertCommand(Command.content2(0xd2, 0x60), "LDC @RR0, R6");

		assertCommand(Command.content2(0x83, 0x60), "LDEI @R6, @RR0");
		assertCommand(Command.content2(0x93, 0x60), "LDEI @RR0, @R6");
		assertCommand(Command.content2(0xc3, 0x9e), "LDCI @R9, @RR14");
		assertCommand(Command.content2(0xd3, 0x60), "LDCI @RR0, @R6");
	}

	@Test
	public void testJpJrCallDjnz() {
		assertCommand(Command.content2(0x30, 0x6E), "jp @%6e");
		assertCommand(Command.content2(0x30, 0xEE), "jp @rr14");
		assertCommand(Command.content3(0x8D, 0x08, 0x12), "jp %0812");
		assertCommand(Command.lazyContent3(0x8D, "M_0812", new Location(0, 3)), "jp M_0812");
		assertCommand(Command.content3(0xED, 0x08, 0x12), "jp nz, %0812");
		assertCommand(Command.lazyContent3(0x7D, "M_0812", new Location(0, 6)), "jp c, M_0812");

		assertCommand(Command.lazyContent2(0x8B, "M_0812", new Location(0, 3)), "jr M_0812");
		assertCommand(Command.lazyContent2(0x7B, "M_0812", new Location(0, 6)), "jr c, M_0812");

		assertCommand(Command.lazyContent2(0x1A, "FOO", new Location(0, 9)), "djnz r1, FOO");

		assertCommand(Command.content2(0xD4, 0x6E), "call @%6e");
		assertCommand(Command.content2(0xD4, 0xEE), "call @rr14");
		assertCommand(Command.content3(0xD6, 0x08, 0x12), "call %0812");
		assertCommand(Command.lazyContent3(0xD6, "M_0812", new Location(0, 5)), "call M_0812");
	}

	@Test
	public void testXF() {
		assertCommand(Command.content1(0x8F), "di");
		assertCommand(Command.content1(0x9F), "ei");
		assertCommand(Command.content1(0xAF), "ret");
		assertCommand(Command.content1(0xBF), "iret");
		assertCommand(Command.content1(0xCF), "rcf");
		assertCommand(Command.content1(0xDF), "scf");
		assertCommand(Command.content1(0xEF), "ccf");
		assertCommand(Command.content1(0xFF), "nop");
	}

	@Test
	public void testData() {
		assertCommands(List.of(
				Command.content1(0x46),
				Command.content1(0x16),
				Command.content1(0x40),
				Command.content1(0x50),
				Command.content1(0x60),
				Command.content1(0x20),
				Command.content1(0x00),
				Command.lazyContent2(0, "FOO", new Location(1, 16))
		), """
				               .data %46 %16 %40 %50
				               .data %60 " \\0" FOO
				               """);
	}

	@Test
	public void testRepeat() {
		assertCommands(List.of(
				Command.content1(0xFF),
				Command.content1(0xFF),
				Command.content1(0xFF)
		), """
				               .repeat 3
				               	nop
				               .end""");

		assertCommands(List.of(
				Command.content1(0x22),
				Command.content1(0x22),
				Command.content1(0x22),
				Command.content1(0x22),
				Command.content1(0x22),
				Command.content1(0x22)
		), """
				               .repeat 3
				                 .repeat 2
				               	   .data %22
				               	 .end
				               .end""");
	}

	@Test
	public void testParser() {
		final List<Command> commands = assertCommands(List.of(
				Command.org(0x0800, new Location(0, 8)),
				Command.content3(0x8d, 0xe0, 0),
				Command.content3(0x8d, 0xe0, 3),
				Command.label("M_0812", new Location(4, 0)),
				Command.lazyContent3(0x8d, "M_0812", new Location(4, 11))
		), """
				                                                 .org %0800
				                                                 jp %e000
				                                                 jp %e003
				                                                 ; boot
				                                              M_0812: jp M_0812
				                                              """);

		final Assembler assembler = new Assembler(commands);
		final List<Command> newCommands = assembler.assemble();
		assertEquals(List.of(
				Command.content3(0x8d, 0xe0, 0),
				Command.content3(0x8d, 0xe0, 3),
				Command.content3(0x8d, 0x8, 6)
		), newCommands);
	}

	private void assertUnary(int opCode, String mnemonic) {
		assertCommand(Command.content2(opCode, 0x60), mnemonic + " %60");
		assertCommand(Command.content2(opCode, 0xE2), mnemonic + " r2");
		assertCommand(Command.content2(opCode + 1, 0x60), mnemonic + " @%60");
		assertCommand(Command.content2(opCode + 1, 0xE2), mnemonic + " @r2");
		assertCommands(List.of(
				Command.content2(opCode, 0xFD)
		), ".const RP = %FD\n"
		   + mnemonic + " RP");
	}

	private void assertBinary(int opCode, String mnemonic) {
		assertCommand(Command.content2(opCode + 2, 0x23), mnemonic + " r2, r3");
		assertCommand(Command.content2(opCode + 3, 0x4f), mnemonic + " r4, @r15");
		assertCommand(Command.content3(opCode + 4, 0x62, 0x60), mnemonic + " %60, %62");
		assertCommand(Command.content3(opCode + 4, 0x66, 0x64), ".const FOO = %64\n"
		                                                        + ".const BAR = %66\n"
		                                                        + mnemonic + " FOO, BAR\n");
		assertCommand(Command.content3(opCode + 5, 0x12, 0x43), mnemonic + " @%43, %12");
		assertCommand(Command.content3(opCode + 6, 0x61, 0x0d), mnemonic + " %61, #%0d");
		assertCommand(Command.content3(opCode + 6, 0xEA, 0x40), mnemonic + " r10, #%40");
		assertCommand(Command.content3(opCode + 6, 0x64, 0x0d), ".const FOO = %64\n"
		                                                        + mnemonic + " FOO, #%0d\n");
		assertCommand(Command.content3(opCode + 6, 0x65, 0x0d), ".const NEWLINE = %0d\n"
		                                                        + mnemonic + " %65, #NEWLINE");
		assertCommand(Command.content3(opCode + 7, 0xEB, 0x0F), mnemonic + " @R11, #%0F");
	}

	private void assertCommand(Command expected, String input) {
		assertCommands(List.of(expected), input);
	}

	@NotNull
	private List<Command> assertCommands(List<Command> expected, String input) {
		final Parser parser = new Parser(new Lexer(input));
		final List<Command> commands = parser.process();
		assertEquals(expected, commands);
		return commands;
	}
}