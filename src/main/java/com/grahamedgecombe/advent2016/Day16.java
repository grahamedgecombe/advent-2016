package com.grahamedgecombe.advent2016;

import java.io.IOException;

public final class Day16 {
	private static final int PART_1_LENGTH = 272;
	private static final int PART_2_LENGTH = 35651584;

	public static void main(String[] args) throws IOException {
		String input = AdventUtils.readString("day16.txt");
		System.out.println(fillDisk(input, PART_1_LENGTH));
		System.out.println(fillDisk(input, PART_2_LENGTH));
	}

	public static String fillDisk(String input, int len) {
		return generateChecksum(generateData(input, len));
	}

	public static String generateData(String input, int len) {
		while (input.length() < len) {
			input += "0" + reverseAndSwapDigits(input);
		}

		return input.substring(0, len);
	}

	private static String reverseAndSwapDigits(String input) {
		char[] output = new char[input.length()];

		for (int i = 0; i < output.length; i++) {
			char ch = input.charAt(output.length - i - 1);
			if (ch == '0') {
				ch = '1';
			} else {
				ch = '0';
			}
			output[i] = ch;
		}

		return new String(output);
	}

	public static String generateChecksum(String input) {
		do {
			input = comparePairs(input);
		} while ((input.length() & 1) == 0);

		return input;
	}

	private static String comparePairs(String input) {
		char[] checksum = new char[input.length() / 2];

		for (int i = 0; i < checksum.length; i++) {
			char c1 = input.charAt(i * 2);
			char c2 = input.charAt(i * 2 + 1);

			char ch;
			if (c1 == c2) {
				ch = '1';
			} else {
				ch = '0';
			}

			checksum[i] = ch;
		}

		return new String(checksum);
	}

	private Day16() {
		/* empty */
	}
}
