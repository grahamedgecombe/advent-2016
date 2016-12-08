package com.grahamedgecombe.advent2016;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day8 {
	private static final int SCREEN_WIDTH = 50;
	private static final int SCREEN_HEIGHT = 6;

	private static final Pattern RECT_PATTERN = Pattern.compile("^rect (\\d+)x(\\d+)$");
	private static final Pattern ROTATE_PATTERN = Pattern.compile("^rotate (column|row) (?:x|y)=(\\d+) by (\\d+)$");

	public static void main(String[] args) throws IOException {
		Screen screen = Screen.parse(SCREEN_WIDTH, SCREEN_HEIGHT, AdventUtils.readLines("day8.txt"));
		System.out.println(screen.getLitPixels());
		System.out.println(screen.toString());
	}

	public static final class Screen {
		public static Screen parse(int width, int height, List<String> instructions) {
			Screen screen = new Screen(width, height);

			for (String instruction : instructions) {
				Matcher matcher;

				if ((matcher = RECT_PATTERN.matcher(instruction)).matches()) {
					int rectWidth = Integer.parseInt(matcher.group(1));
					int rectHeight = Integer.parseInt(matcher.group(2));

					for (int x = 0; x < rectWidth; x++) {
						for (int y = 0; y < rectHeight; y++) {
							screen.pixels[x][y] = true;
						}
					}
				} else if ((matcher = ROTATE_PATTERN.matcher(instruction)).matches()) {
					int n = Integer.parseInt(matcher.group(3));

					if (matcher.group(1).equals("column")) {
						int x = Integer.parseInt(matcher.group(2));

						boolean[] col = screen.pixels[x].clone();

						for (int y = 0; y < col.length; y++) {
							screen.pixels[x][(y + n) % screen.height] = col[y];
						}
					} else { /* "row" */
						int y = Integer.parseInt(matcher.group(2));

						boolean[] row = new boolean[screen.width];
						for (int x = 0; x < row.length; x++) {
							row[x] = screen.pixels[x][y];
						}

						for (int x = 0; x < row.length; x++) {
							screen.pixels[(x + n) % screen.width][y] = row[x];
						}
					}
				} else {
					throw new IllegalArgumentException();
				}
			}

			return screen;
		}

		private final int width, height;
		private final boolean[][] pixels;

		public Screen(int width, int height) {
			this.width = width;
			this.height = height;
			this.pixels = new boolean[width][height];
		}

		public int getLitPixels() {
			int count = 0;

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					if (pixels[x][y]) {
						count++;
					}
				}
			}

			return count;
		}

		@Override
		public String toString() {
			StringBuilder buf = new StringBuilder();

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					buf.append(pixels[x][y] ? '#' : '.');
				}

				if (y != (height - 1)) {
					buf.append('\n');
				}
			}

			return buf.toString();
		}
	}

	private Day8() {
		/* empty */
	}
}
