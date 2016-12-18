package com.grahamedgecombe.advent2016;

import java.io.IOException;

public final class Day18 {
	private static final int PART_1_ROWS = 40;
	private static final int PART_2_ROWS = 400000;

	private static final char SAFE = '.';
	private static final char TRAP = '^';

	public static void main(String[] args) throws IOException {
		String row = AdventUtils.readString("day18.txt");
		System.out.println(countSafeTiles(row, PART_1_ROWS));
		System.out.println(countSafeTiles(row, PART_2_ROWS));
	}

	public static int countSafeTiles(String row, int rows) {
		int safeTiles = countSafeTilesRow(row);

		for (int i = 1; i < rows; i++) {
			row = evolve(row);
			safeTiles += countSafeTilesRow(row);
		}

		return safeTiles;
	}

	public static String evolve(String current) {
		char[] next = new char[current.length()];

		for (int i = 0; i < current.length(); i++) {
			boolean left = isTrap(current, i - 1);
			boolean center = isTrap(current, i);
			boolean right = isTrap(current, i + 1);

			char ch;
			if (left && center && !right) {
				ch = TRAP;
			} else if (!left && center && right) {
				ch = TRAP;
			} else if (left && !center && !right) {
				ch = TRAP;
			} else if (!left && !center && right) {
				ch = TRAP;
			} else {
				ch = SAFE;
			}

			next[i] = ch;
		}

		return new String(next);
	}

	private static boolean isTrap(String row, int i) {
		if (i < 0 || i >= row.length()) {
			return false;
		} else {
			return row.charAt(i) == TRAP;
		}
	}

	private static int countSafeTilesRow(String row) {
		int safeTiles = 0;

		for (char ch : row.toCharArray()) {
			if (ch == SAFE) {
				safeTiles++;
			}
		}

		return safeTiles;
	}

	private Day18() {
		/* empty */
	}
}
