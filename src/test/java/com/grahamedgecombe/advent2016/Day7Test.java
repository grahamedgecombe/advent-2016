package com.grahamedgecombe.advent2016;

import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day7Test {
	@Test
	public void testPart1() {
		assertEquals(1, Day7.getTlsCount(Arrays.asList("abba[mnop]qrst")));
		assertEquals(0, Day7.getTlsCount(Arrays.asList("abcd[bddb]xyyx")));
		assertEquals(0, Day7.getTlsCount(Arrays.asList("aaaa[qwer]tyui")));
		assertEquals(1, Day7.getTlsCount(Arrays.asList("ioxxoj[asdfgh]zxcvbn")));
	}

	@Test
	public void testPart2() {
		assertEquals(1, Day7.getSslCount(Arrays.asList("aba[bab]xyz")));
		assertEquals(0, Day7.getSslCount(Arrays.asList("xyx[xyx]xyx")));
		assertEquals(1, Day7.getSslCount(Arrays.asList("aaa[kek]eke")));
		assertEquals(1, Day7.getSslCount(Arrays.asList("zazbz[bzb]cdb")));
	}
}
