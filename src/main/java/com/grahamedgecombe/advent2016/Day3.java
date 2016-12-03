package com.grahamedgecombe.advent2016;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day3 {
	private static final Pattern TRIANGLE_PATTERN = Pattern.compile("^\\s*(\\d+)\\s*(\\d+)\\s*(\\d+)\\s*$");

	public static void main(String[] args) throws IOException {
		List<String> lines = AdventUtils.readLines("day3.txt");
		System.out.println(getPossibleTrianglesHorizontally(lines));
		System.out.println(getPossibleTrianglesVertically(lines));
	}

	private static final class Row {
		private static Row parse(String line) {
			Matcher matcher = TRIANGLE_PATTERN.matcher(line);
			if (!matcher.matches()) {
				throw new IllegalArgumentException();
			}

			int a = Integer.parseInt(matcher.group(1));
			int b = Integer.parseInt(matcher.group(2));
			int c = Integer.parseInt(matcher.group(3));

			return new Row(a, b, c);
		}

		private final int a;
		private final int b;
		private final int c;

		private Row(int a, int b, int c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}
	}

	private static boolean isPossible(int a, int b, int c) {
		return (a + b) > c && (a + c) > b && (b + c) > a;
	}

	public static int getPossibleTrianglesHorizontally(List<String> lines) {
		int possible = 0;

		for (String line : lines) {
			Row row = Row.parse(line);

			if (isPossible(row.a, row.b, row.c)) {
				possible++;
			}
		}

		return possible;
	}

	public static int getPossibleTrianglesVertically(List<String> lines) {
		int possible = 0;

		for (int i = 0; i < lines.size(); i += 3) {
			Row row1 = Row.parse(lines.get(i));
			Row row2 = Row.parse(lines.get(i + 1));
			Row row3 = Row.parse(lines.get(i + 2));

			if (isPossible(row1.a, row2.a, row3.a)) {
				possible++;
			}

			if (isPossible(row1.b, row2.b, row3.b)) {
				possible++;
			}

			if (isPossible(row1.c, row2.c, row3.c)) {
				possible++;
			}
		}

		return possible;
	}

	private Day3() {
		/* empty */
	}
}
