package com.grahamedgecombe.advent2016;

import java.io.IOException;
import java.util.List;

public final class Day2 {
	public static final char[][] PART_1_KEYPAD = {
		{ '1', '2', '3' },
		{ '4', '5', '6' },
		{ '7', '8', '9' }
	};

	public static final int PART_1_START_X = 1;
	public static final int PART_1_START_Y = 1;

	public static final char[][] PART_2_KEYPAD = {
		{ ' ', ' ', '1', ' ', ' ' },
		{ ' ', '2', '3', '4', ' ' },
		{ '5', '6', '7', '8', '9' },
		{ ' ', 'A', 'B', 'C', ' ' },
		{ ' ', ' ', 'D', ' ', ' ' }
	};

	public static final int PART_2_START_X = 0;
	public static final int PART_2_START_Y = 2;

	public static void main(String[] args) throws IOException {
		List<String> lines = AdventUtils.readLines("day2.txt");
		System.out.println(Day2.getBathroomCode(lines, PART_1_KEYPAD, PART_1_START_X, PART_1_START_Y));
		System.out.println(Day2.getBathroomCode(lines, PART_2_KEYPAD, PART_2_START_X, PART_2_START_Y));
	}

	public static String getBathroomCode(List<String> lines, char[][] keypad, int x, int y) {
		StringBuilder code = new StringBuilder();

		int width = keypad[0].length, height = keypad.length;

		for (String line : lines) {
			for (char direction : line.toCharArray()) {
				int x0 = x, y0 = y;

				if (direction == 'U') {
					y0 = Math.max(y - 1, 0);
				} else if (direction == 'D') {
					y0 = Math.min(y + 1, height - 1);
				} else if (direction == 'L') {
					x0 = Math.max(x - 1, 0);
				} else { /* direction == 'R' */
					x0 = Math.min(x + 1, width - 1);
				}

				if (keypad[y0][x0] != ' ') {
					x = x0;
					y = y0;
				}
			}

			code.append(keypad[y][x]);
		}

		return code.toString();
	}

	private Day2() {
		/* empty */
	}
}
