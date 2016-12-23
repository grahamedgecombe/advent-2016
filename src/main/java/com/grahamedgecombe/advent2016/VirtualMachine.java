package com.grahamedgecombe.advent2016;

import java.util.Arrays;
import java.util.List;

public final class VirtualMachine {
	public static final int REGISTER_A = 0, REGISTER_B = 1, REGISTER_C = 2, REGISTER_D = 3;

	private enum Opcode {
		CPY,
		INC,
		DEC,
		JNZ
	}

	private static final class Instruction {
		private final Opcode opcode;
		private final boolean immediate;
		private final int operand1, operand2;

		private Instruction(Opcode opcode, boolean immediate, int operand1, int operand2) {
			this.opcode = opcode;
			this.immediate = immediate;
			this.operand1 = operand1;
			this.operand2 = operand2;
		}
	}

	public static VirtualMachine create(List<String> lines) {
		Instruction[] instructions = new Instruction[lines.size()];

		for (int i = 0; i < instructions.length; i++) {
			String[] parts = lines.get(i).split(" ");

			boolean immediate = false;
			int operand1, operand2 = 0;

			Opcode opcode = Opcode.valueOf(parts[0].toUpperCase());
			switch (opcode) {
				case CPY:
					String x = parts[1];
					String y = parts[2];

					try {
						operand1 = Integer.parseInt(x);
						immediate = true;
					} catch (NumberFormatException ex) {
						operand1 = x.charAt(0) - 'a';
					}

					operand2 = y.charAt(0) - 'a';
					break;
				case INC:
				case DEC:
					x = parts[1];

					operand1 = x.charAt(0) - 'a';
					break;
				case JNZ:
					x = parts[1];
					y = parts[2];

					try {
						operand1 = Integer.parseInt(x);
						immediate = true;
					} catch (NumberFormatException ex) {
						operand1 = x.charAt(0) - 'a';
					}

					operand2 = Integer.parseInt(y);
					break;
				default:
					throw new IllegalArgumentException();
			}

			instructions[i] = new Instruction(opcode, immediate, operand1, operand2);
		}

		return new VirtualMachine(instructions);
	}

	private final Instruction[] instructions;
	private final int[] registers = new int[4];

	private VirtualMachine(Instruction[] instructions) {
		this.instructions = instructions;
	}

	public void reset() {
		Arrays.fill(registers, 0);
	}

	public void set(int register, int value) {
		registers[register] = value;
	}

	public int get(int register) {
		return registers[register];
	}

	public void run() {
		for (int pc = 0; pc < instructions.length; pc++) {
			Instruction instruction = instructions[pc];
			switch (instruction.opcode) {
				case CPY:
					registers[instruction.operand2] = instruction.immediate ? instruction.operand1 : registers[instruction.operand1];
					break;
				case INC:
					registers[instruction.operand1]++;
					break;
				case DEC:
					registers[instruction.operand1]--;
					break;
				case JNZ:
					if ((instruction.immediate ? instruction.operand1 : registers[instruction.operand1]) != 0) {
						pc += instruction.operand2 - 1;
					}
					break;
			}
		}
	}
}
