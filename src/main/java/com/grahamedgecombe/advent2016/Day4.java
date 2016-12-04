package com.grahamedgecombe.advent2016;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day4 {
	private static final Pattern ROOM_PATTERN = Pattern.compile("^([a-z-]+)-(\\d{3})\\[([a-z]{5})\\]$");

	public static void main(String[] args) throws IOException {
		List<String> lines = AdventUtils.readLines("day4.txt");
		System.out.println(sumValidSectorIds(lines));
		System.out.println(getSectorId(lines, "northpole object storage"));
	}

	private static final class Room {
		private static Room parse(String line) {
			Matcher matcher = ROOM_PATTERN.matcher(line);
			if (!matcher.matches()) {
				throw new IllegalArgumentException();
			}

			String name = matcher.group(1);
			int sectorId = Integer.parseInt(matcher.group(2));
			String checksum = matcher.group(3);

			return new Room(name, sectorId, checksum);
		}

		private final String name;
		private final int sectorId;
		private final String checksum;

		private Room(String name, int sectorId, String checksum) {
			this.name = name;
			this.sectorId = sectorId;
			this.checksum = checksum;
		}

		private String decryptName() {
			int len = name.length();
			char[] chars = new char[len];

			for (int i = 0; i < len; i++) {
				char ch = name.charAt(i);
				if (ch == '-') {
					chars[i] = ' ';
				} else {
					chars[i] = (char) ((((ch - 'a') + sectorId) % 26) + 'a');
				}
			}

			return new String(chars);
		}

		private boolean isValid() {
			Letter[] letters = new Letter[26];
			for (char ch = 'a'; ch <= 'z'; ch++) {
				letters[ch - 'a'] = new Letter(ch);
			}

			for (char ch : name.toCharArray()) {
				if (ch == '-') {
					continue;
				}

				letters[ch - 'a'].occurrences++;
			}

			Arrays.sort(letters);

			char[] expectedChecksum = new char[5];
			for (int i = 0; i < expectedChecksum.length; i++) {
				expectedChecksum[i] = letters[i].letter;
			}

			return checksum.equals(new String(expectedChecksum));
		}
	}

	private static final class Letter implements Comparable<Letter> {
		private final char letter;
		private int occurrences;

		public Letter(char letter) {
			this.letter = letter;
		}

		@Override
		public int compareTo(Letter o) {
			if (occurrences > o.occurrences) {
				return -1;
			} else if (occurrences < o.occurrences) {
				return 1;
			} else if (letter > o.letter) {
				return 1;
			} else if (letter < o.letter) {
				return -1;
			}

			return 0;
		}
	}

	public static int sumValidSectorIds(List<String> lines) {
		int sum = 0;

		for (String line : lines) {
			Room room = Room.parse(line);

			if (room.isValid()) {
				sum += room.sectorId;
			}
		}

		return sum;
	}

	public static int getSectorId(List<String> lines, String name) {
		for (String line : lines) {
			Room room = Room.parse(line);

			if (room.isValid() && room.decryptName().equals(name)) {
				return room.sectorId;
			}
		}

		throw new IllegalArgumentException();
	}

	private Day4() {
		/* empty */
	}
}
