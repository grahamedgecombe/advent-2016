package com.grahamedgecombe.advent2016;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.common.io.BaseEncoding;

public final class Md5 {
	private static final MessageDigest MD5;
	private static final BaseEncoding LOWERCASE_HEX = BaseEncoding.base16().lowerCase();

	static {
		try {
			MD5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static String hash(String input) {
		byte[] digest = MD5.digest(input.getBytes(StandardCharsets.UTF_8));
		return LOWERCASE_HEX.encode(digest);
	}

	private Md5() {
		/* empty */
	}
}
