package com.grahamedgecombe.advent2016;

import java.io.IOException;

public final class Day9 {
	public static void main(String[] args) throws IOException {
		String compressed = AdventUtils.readString("day9.txt");
		System.out.println(decompress(compressed));
		System.out.println(decompressRecursively(compressed));
	}

	public static long decompress(String compressed) {
		return decompress(compressed, false);
	}

	public static long decompressRecursively(String compressed) {
		return decompress(compressed, true);
	}

	private static long decompress(String compressed, boolean recursive) {
		long uncompressedLen = 0;

		for (int i = 0, markerIndex = -1; i < compressed.length(); i++) {
			char ch = compressed.charAt(i);

			if (markerIndex != -1) {
				if (ch == ')') {
					String[] markerParts = compressed.substring(markerIndex + 1, i).split("x");
					int runLen = Integer.parseInt(markerParts[0]);
					int runTimes = Integer.parseInt(markerParts[1]);

					if (recursive) {
						int runIndex = i + 1;
						String run = compressed.substring(runIndex, runIndex + runLen);
						uncompressedLen += decompress(run, true) * runTimes;
					} else {
						uncompressedLen += runLen * runTimes;
					}

					i += runLen;
					markerIndex = -1;
				}
			} else if (ch == '(') {
				markerIndex = i;
			} else {
				uncompressedLen++;
			}
		}

		return uncompressedLen;
	}

	private Day9() {
		/* empty */
	}
}
