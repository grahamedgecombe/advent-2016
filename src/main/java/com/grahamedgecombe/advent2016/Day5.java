package com.grahamedgecombe.advent2016;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.common.io.BaseEncoding;

public final class Day5 {
	private static final MessageDigest MD5;

	static {
		try {
			MD5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static void main(String[] args) throws IOException {
		String doorId = AdventUtils.readString("day5.txt");
		System.out.println(getPasswordPart1(doorId));
		System.out.println(getPasswordPart2(doorId));
	}

	private static String md5(String input) {
		byte[] digest = MD5.digest(input.getBytes(StandardCharsets.UTF_8));
		return BaseEncoding.base16().lowerCase().encode(digest);
	}

	public static String getPasswordPart1(String doorId) {
		char[] password = new char[8];

		for (int i = 0, j = 0; i < password.length; j++) {
			String hex = md5(doorId + j);
			if (hex.startsWith("00000")) {
				password[i++] = hex.charAt(5);
			}
		}

		return new String(password);
	}

	public static String getPasswordPart2(String doorId) {
		char[] password = new char[8];

		for (int i = 0, j = 0; i < password.length; j++) {
			String hex = md5(doorId + j);
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
