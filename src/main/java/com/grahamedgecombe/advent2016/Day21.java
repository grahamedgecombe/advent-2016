package com.grahamedgecombe.advent2016;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day21 {
	private static final Pattern SWAP_POS_PATTERN = Pattern.compile("^swap position (\\d+) with position (\\d+)$");
	private static final Pattern SWAP_LETTER_PATTERN = Pattern.compile("^swap letter ([a-z]) with letter ([a-z])$");
	private static final Pattern ROTATE_PATTERN = Pattern.compile("^rotate (left|right) (\\d+) steps?$");
	private static final Pattern ROTATE_BASED_ON_LETTER_POS_PATTERN = Pattern.compile("^rotate based on position of letter ([a-z])$");
	private static final Pattern REVERSE_PATTERN = Pattern.compile("^reverse positions (\\d+) through (\\d+)$");
	private static final Pattern MOVE_PATTERN = Pattern.compile("^move position (\\d+) to position (\\d+)$");

	public static void main(String[] args) throws IOException {
		List<String> ops = AdventUtils.readLines("day21.txt");
		System.out.println(scramble(ops, "abcdefgh"));
		System.out.println(unscramble(ops, "fbgdceah"));
	}

	private static void swap(char[] chars, int x, int y) {
		char tmp = chars[x];
		chars[x] = chars[y];
		chars[y] = tmp;
	}

	private static int indexOf(char[] chars, char ch) {
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == ch) {
				return i;
			}
		}

		return -1;
	}

	private static void rotate(char[] chars, boolean left, int steps) {
		steps %= chars.length;

		char[] temp = new char[steps];

		if (left) {
			System.arraycopy(chars, 0, temp, 0, steps);
			System.arraycopy(chars, steps, chars, 0, chars.length - steps);
			System.arraycopy(temp, 0, chars, chars.length - steps, steps);
		} else {
			System.arraycopy(chars, chars.length - steps, temp, 0, steps);
			System.arraycopy(chars, 0, chars, steps, chars.length - steps);
			System.arraycopy(temp, 0, chars, 0, steps);
		}
	}

	private static void reverse(char[] chars, int x, int y) {
		for (int i = x, j = y; i < j; i++, j--) {
			swap(chars, i, j);
		}
	}

	private static void move(char[] chars, int x, int y) {
		char tmp = chars[x];

		System.arraycopy(chars, x + 1, chars, x, chars.length - x - 1);
		System.arraycopy(chars, y, chars, y + 1, chars.length - y - 1);

		chars[y] = tmp;
	}

	private static int indexToSteps(int index) {
		return index + (index >= 4 ? 2 : 1);
	}

	private static void apply(char[] chars, String op, boolean unscramble) {
		int[] rotatedIndexToSteps = new int[chars.length];
		for (int index = 0; index < chars.length; index++) {
			int steps = indexToSteps(index);
			int rotatedIndex = (index + steps) % chars.length;
			rotatedIndexToSteps[rotatedIndex] = steps;
		}

		Matcher matcher;
		if ((matcher = SWAP_POS_PATTERN.matcher(op)).matches()) {
			int x = Integer.parseInt(matcher.group(1));
			int y = Integer.parseInt(matcher.group(2));
			swap(chars, x, y);
		} else if ((matcher = SWAP_LETTER_PATTERN.matcher(op)).matches()) {
			char x = matcher.group(1).charAt(0);
			char y = matcher.group(2).charAt(0);
			swap(chars, indexOf(chars, x), indexOf(chars, y));
		} else if ((matcher = ROTATE_PATTERN.matcher(op)).matches()) {
			boolean left = matcher.group(1).equals("left");
			int steps = Integer.parseInt(matcher.group(2));
			if (unscramble) {
				left = !left;
			}
			rotate(chars, left, steps);
		} else if ((matcher = ROTATE_BASED_ON_LETTER_POS_PATTERN.matcher(op)).matches()) {
			char x = matcher.group(1).charAt(0);
			int index = indexOf(chars, x);
			int steps;
			if (unscramble) {
				steps = rotatedIndexToSteps[index];
			} else {
				steps = indexToSteps(index);
			}
			rotate(chars, unscramble, steps);
		} else if ((matcher = REVERSE_PATTERN.matcher(op)).matches()) {
			int x = Integer.parseInt(matcher.group(1));
			int y = Integer.parseInt(matcher.group(2));
			reverse(chars, x, y);
		} else if ((matcher = MOVE_PATTERN.matcher(op)).matches()) {
			int x = Integer.parseInt(matcher.group(1));
			int y = Integer.parseInt(matcher.group(2));
			if (unscramble) {
				int tmp = x;
				x = y;
				y = tmp;
			}
			move(chars, x, y);
		} else {
			throw new IllegalArgumentException(op);
		}
	}

	public static String scramble(List<String> ops, String str) {
		char[] chars = str.toCharArray();

		for (String op : ops) {
			apply(chars, op, false);
		}

		return new String(chars);
	}

	public static String unscramble(List<String> ops, String str) {
		char[] chars = str.toCharArray();

		for (int i = ops.size() - 1; i >= 0; i--) {
			apply(chars, ops.get(i), true);
		}

		return new String(chars);
	}

	private Day21() {
		/* empty */
	}
}
