package com.grahamedgecombe.advent2016;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.google.common.primitives.Ints;

public final class Day7 {
	public static void main(String[] args) throws IOException {
		List<String> ips = AdventUtils.readLines("day7.txt");
		System.out.println(getTlsCount(ips));
		System.out.println(getSslCount(ips));
	}

	private static boolean isBracket(char ch) {
		return ch == '[' || ch == ']';
	}

	private static boolean isTls(String ip) {
		boolean inBrackets = false;
		boolean abba = false, abbaHypernet = false;

		for (int i = 0; i < ip.length() - 3; i++) {
			char c1 = ip.charAt(i);
			char c2 = ip.charAt(i + 1);
			char c3 = ip.charAt(i + 2);
			char c4 = ip.charAt(i + 3);

			if (c1 == '[') {
				inBrackets = true;
			} else if (c1 == ']') {
				inBrackets = false;
			} else if (c1 != c2 && c1 == c4 && c2 == c3 && !isBracket(c2) && !isBracket(c3) && !isBracket(c4)) {
				if (inBrackets) {
					abbaHypernet = true;
				} else {
					abba = true;
				}
			}
		}

		return abba && !abbaHypernet;
	}

	private static final class Pair {
		private final char a, b;

		public Pair(char a, char b) {
			this.a = a;
			this.b = b;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Pair)) return false;
			Pair pair = (Pair) o;
			return a == pair.a &&
				b == pair.b;
		}

		@Override
		public int hashCode() {
			return Objects.hash(a, b);
		}
	}

	private static boolean isSsl(String ip) {
		Set<Pair> abas = new HashSet<>();
		Set<Pair> babs = new HashSet<>();
		boolean inBrackets = false;

		for (int i = 0; i < ip.length() - 2; i++) {
			char c1 = ip.charAt(i);
			char c2 = ip.charAt(i + 1);
			char c3 = ip.charAt(i + 2);

			if (c1 == '[') {
				inBrackets = true;
			} else if (c1 == ']') {
				inBrackets = false;
			} else if (c1 != c2 && c1 == c3 && !isBracket(c2) && !isBracket(c3)) {
				if (inBrackets) {
					babs.add(new Pair(c1, c2));
				} else {
					abas.add(new Pair(c2, c1));
				}
			}
		}

		return !Collections.disjoint(abas, babs);
	}

	public static int getTlsCount(List<String> ips) {
		return Ints.checkedCast(ips.stream().filter(Day7::isTls).count());
	}

	public static int getSslCount(List<String> ips) {
		return Ints.checkedCast(ips.stream().filter(Day7::isSsl).count());
	}

	private Day7() {
		/* empty */
	}
}
