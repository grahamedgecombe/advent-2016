package com.grahamedgecombe.advent2016;

import java.io.IOException;
import java.util.List;
import java.util.function.BiPredicate;

public final class Day6 {
	public static final BiPredicate<Integer, Integer> GREATER_THAN = (a, b) -> a > b;
	public static final BiPredicate<Integer, Integer> LESS_THAN = (a, b) -> a < b;

	public static void main(String[] args) throws IOException {
		List<String> messages = AdventUtils.readLines("day6.txt");
		System.out.println(getMessage(messages, GREATER_THAN));
		System.out.println(getMessage(messages, LESS_THAN));
	}

	public static String getMessage(List<String> messages, BiPredicate<Integer, Integer> greaterThan) {
		int len = messages.get(0).length();
		int[][] letterFrequencies = new int[len][26];

		for (String message : messages) {
			for (int i = 0; i < message.length(); i++) {
				char ch = message.charAt(i);
				letterFrequencies[i][ch - 'a']++;
			}
		}

		char[] message = new char[len];
		for (int i = 0; i < len; i++) {
			message[i] = mostFrequentLetter(letterFrequencies[i], greaterThan);
		}

		return new String(message);
	}

	private static char mostFrequentLetter(int[] frequencies, BiPredicate<Integer, Integer> greaterThan) {
		char letter = 'a';
		int frequency = frequencies[0];

		for (int i = 1; i < frequencies.length; i++) {
			if (frequencies[i] != 0 && greaterThan.test(frequencies[i], frequency)) {
				letter = (char) ('a' + i);
				frequency = frequencies[i];
			}
		}

		return letter;
	}

	private Day6() {
		/* empty */
	}
}
