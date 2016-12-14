package com.grahamedgecombe.advent2016;

import java.io.IOException;

public final class Day5 {
	public static void main(String[] args) throws IOException {
		String doorId = AdventUtils.readString("day5.txt");
		System.out.println(getPasswordPart1(doorId));
		System.out.println(getPasswordPart2(doorId));
	}

	public static String getPasswordPart1(String doorId) {
		char[] password = new char[8];

		for (int i = 0, j = 0; i < password.length; j++) {
			String hex = Md5.hash(doorId + j);
			if (hex.startsWith("00000")) {
				password[i++] = hex.charAt(5);
			}
		}

		return new String(password);
	}

	public static String getPasswordPart2(String doorId) {
		char[] password = new char[8];

		for (int i = 0, j = 0; i < password.length; j++) {
			String hex = Md5.hash(doorId + j);
			if (!hex.startsWith("00000")) {
				continue;
			}

			char pos = hex.charAt(5);
			if (pos < '0' || pos > '7') {
				continue;
			}

			pos -= '0';
			if (password[pos] != 0) {
				continue;
			}

			password[pos] = hex.charAt(6);
			i++;
		}

		return new String(password);
	}

	private Day5() {
		/* empty */
	}
}
