package com.grahamedgecombe.advent2016;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class Day14 {
	public static final int PART_1_ITERATIONS = 1;
	public static final int PART_2_ITERATIONS = 2017;

	public static void main(String[] args) throws IOException {
		String salt = AdventUtils.readString("day14.txt");
		System.out.println(getIndex(salt, PART_1_ITERATIONS));
		System.out.println(getIndex(salt, PART_2_ITERATIONS));
	}

	public static int getIndex(String salt, int iterations) {
		Map<Integer, String> hashes = new HashMap<>();

		for (int i = 0, keys = 0;; i++) {
			String hash = hashes.computeIfAbsent(i, i0 -> hash(salt + i0, iterations));
			char triple = containsTriple(hash);
			if (triple == 0) {
				continue;
			}

			boolean key = false;

			for (int j = 0, k = i + 1; j < 1000; j++, k++) {
				hash = hashes.computeIfAbsent(k, k0 -> hash(salt + k0, iterations));
				if (containsFiveTimes(hash, triple)) {
					key = true;
					break;
				}
			}

			if (key && ++keys == 64) {
				return i;
			}

			hashes.remove(i);
		}
	}

	private static String hash(String str, int iterations) {
		for (int i = 0; i < iterations; i++) {
			str = Md5.hash(str);
		}
		return str;
	}

	private static char containsTriple(String str) {
		char prev = str.charAt(0);

		for (int i = 1, j = 1; i < str.length(); i++) {
			char ch = str.charAt(i);

			if (ch == prev) {
				if (++j == 3) {
					return ch;
				}
			} else {
				prev = ch;
				j = 1;
			}
		}

		return 0;
	}

	private static boolean containsFiveTimes(String str, char expected) {
		char prev = str.charAt(0);

		for (int i = 1, j = 1; i < str.length(); i++) {
			char ch = str.charAt(i);

			if (ch == prev && ch == expected) {
				if (++j == 5) {
					return true;
				}
			} else {
				prev = ch;
				j = 1;
			}
		}

		return false;
	}

	private Day14() {
		/* empty */
	}
}
