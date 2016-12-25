package com.grahamedgecombe.advent2016;

import java.io.IOException;
import java.util.List;

public final class Day25 {
	public static void main(String[] args) throws IOException {
		System.out.println(getLowestA(AdventUtils.readLines("day25.txt")));
	}

	private static int getLowestA(List<String> lines) {
		/*
		 * Relies on the structure of the program to extract b & c:
		 *
		 * d = (15 * 170) + a;
		 * a = d;
		 * for (;;) {
		 *   a = a / 2;
		 *   b = a % 2;
		 *   print(b);
		 *   if (a == 0) {
		 *     a = d;
		 *   }
		 * }
		 */

		int b = Integer.parseInt(lines.get(2).replaceAll("[^0-9]", ""));
		int c = Integer.parseInt(lines.get(1).replaceAll("[^0-9]", ""));

		int i = b * c, j = 0;
		while (!isClock(i + j)) {
			j++;
		}

		return j;
	}

	private static boolean isClock(int i) {
		boolean one = false;

		for (int j = 1; j <= Integer.highestOneBit(i); j <<= 1, one = !one) {
			if (((i & j) != 0) != one) {
				return false;
			}
		}

		/*
		 * Last bit must be 1 (which is !one because of the flip during the for
		 * increment) as first bit must be 0.
		 */
		return !one;
	}

	private Day25() {
		/* empty */
	}
}
