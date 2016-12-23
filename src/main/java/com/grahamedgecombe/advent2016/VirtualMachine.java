package com.grahamedgecombe.advent2016;

import java.util.Arrays;
import java.util.List;

public final class VirtualMachine {
	public static final int REGISTER_A = 0, REGISTER_B = 1, REGISTER_C = 2, REGISTER_D = 3;

	private enum Opcode {
		CPY,
		INC,
		DEC,
		JNZ,
		TGL
	}

	private static final class Instruction {
		private final Opcode opcode;
		private final boolean immediate1, immediate2;
		private final int operand1, operand2;

		private Instruction(Opcode opcode, boolean immediate1, boolean immediate2, int operand1, int operand2) {
			this.opcode = opcode;
			this.immediate1 = immediate1;
			this.immediate2 = immediate2;
			this.operand1 = operand1;
			this.operand2 = operand2;
		}

		private Instruction toggle() {
			switch (opcode) {
				case INC:
					return new Instruction(Opcode.DEC, immediate1, immediate2, operand1, operand2);
				case DEC:
				case TGL:
					return new Instruction(Opcode.INC, immediate1, immediate2, operand1, operand2);
				case JNZ:
					return new Instruction(Opcode.CPY, immediate1, immediate2, operand1, operand2);
				case CPY:
					return new Instruction(Opcode.JNZ, immediate1, immediate2, operand1, operand2);
				default:
					throw new IllegalStateException();
			}
		}

		@Override
		public String toString() {
			StringBuilder buf = new StringBuilder(opcode.toString());
			buf.append(" ");

			if (immediate1) {
				buf.append(operand1);
			} else {
				buf.append((char) ('a' + operand1));
			}

			buf.append(" ");

			if (immediate2) {
				buf.append(operand2);
			} else {
				buf.append((char) ('a' + operand2));
			}

			return buf.toString();
		}
	}

	public static VirtualMachine create(List<String> lines) {
		Instruction[] instructions = new Instruction[lines.size()];

		for (int i = 0; i < instructions.length; i++) {
			String[] parts = lines.get(i).split(" ");

			boolean immediate1 = false, immediate2 = false;
			int operand1, operand2 = 0;

			Opcode opcode = Opcode.valueOf(parts[0].toUpperCase());
			switch (opcode) {
				case CPY:
					String x = parts[1];
					String y = parts[2];

					try {
						operand1 = Integer.parseInt(x);
						immediate1 = true;
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
						immediate1 = true;
					} catch (NumberFormatException ex) {
						operand1 = x.charAt(0) - 'a';
					}

					try {
						operand2 = Integer.parseInt(y);
						immediate2 = true;
					} catch (NumberFormatException ex) {
						operand2 = y.charAt(0) - 'a';
					}
					break;
				case TGL:
					x = parts[1];

					operand1 = x.charAt(0) - 'a';
					break;
				default:
					throw new IllegalArgumentException();
			}

			instructions[i] = new Instruction(opcode, immediate1, immediate2, operand1, operand2);
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
					if (!instruction.immediate2) {
						registers[instruction.operand2] = instruction.immediate1 ? instruction.operand1 : registers[instruction.operand1];
					}
					break;
				case INC:
					if (!instruction.immediate1) {
						registers[instruction.operand1]++;
					}
					break;
				case DEC:
					if (!instruction.immediate1) {
						registers[instruction.operand1]--;
					}
					break;
				case JNZ:
					if ((instruction.immediate1 ? instruction.operand1 : registers[instruction.operand1]) != 0) {
						pc += (instruction.immediate2 ? instruction.operand2 : registers[instruction.operand2]) - 1;
					}
					break;
				case TGL:
					int targetPc = pc + (instruction.immediate1 ? instruction.operand1 : registers[instruction.operand1]);
					if (targetPc >= 0 && targetPc < instructions.length) {
						instructions[targetPc] = instructions[targetPc].toggle();
					}
					break;
			}
		}
	}
}
