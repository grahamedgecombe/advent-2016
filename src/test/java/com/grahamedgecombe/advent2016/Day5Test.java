package com.grahamedgecombe.advent2016;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day5Test {
	@Test
	public void testPart1() {
		assertEquals("18f47a30", Day5.getPasswordPart1("abc"));
	}

	@Test
	public void testPart2() {
		assertEquals("05ace8e3", Day5.getPasswordPart2("abc"));
	}
}
