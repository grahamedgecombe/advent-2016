package com.grahamedgecombe.advent2016;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day16Test {
	@Test
	public void testPart1() {
		assertEquals("100", Day16.generateData("1", 3));
		assertEquals("001", Day16.generateData("0", 3));
		assertEquals("11111000000", Day16.generateData("11111", 11));
		assertEquals("1111000010100101011110000", Day16.generateData("111100001010", 25));

		assertEquals("100", Day16.generateChecksum("110010110100"));

		assertEquals("01100", Day16.fillDisk("10000", 20));
	}
}
