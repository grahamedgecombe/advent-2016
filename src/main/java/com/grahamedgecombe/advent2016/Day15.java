package com.grahamedgecombe.advent2016;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Day15 {
	private static final Pattern PATTERN = Pattern.compile("^Disc #\\d+ has (\\d+) positions; at time=0, it is at position (\\d+).$");

	public static void main(String[] args) throws IOException {
		List<String> lines = AdventUtils.readLines("day15.txt");
		System.out.println(getFirstCapsuleTime(lines));

		lines.add("Disc #7 has 11 positions; at time=0, it is at position 0.");
		System.out.println(getFirstCapsuleTime(lines));
	}

	private static final class Disc {
		private static Disc parse(String line) {
			Matcher matcher = PATTERN.matcher(line);
			if (!matcher.matches()) {
				throw new IllegalArgumentException();
			}

			int size = Integer.parseInt(matcher.group(1));
			int position = Integer.parseInt(matcher.group(2));
			return new Disc(size, position);
		}

		private final int size;
		private int position;

		private Disc(int size, int position) {
			this.size = size;
			this.position = position;
		}

		private boolean isZeroAt(int time) {
			return ((position + time) % size) == 0;
		}
	}

	public static int getFirstCapsuleTime(List<String> lines) {
		List<Disc> discs = lines.stream().map(Disc::parse).collect(Collectors.toList());

		for (int time = 0;; time++) {
			boolean blocked = false;

			for (int i = 0; i < discs.size(); i++) {
				if (!discs.get(i).isZeroAt(time + i + 1)) {
					blocked = true;
				}
			}

			if (!blocked) {
				return time;
			}
		}
	}

	private Day15() {
		/* empty */
	}
}
