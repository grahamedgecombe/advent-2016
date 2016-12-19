package com.grahamedgecombe.advent2016;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day19Test {
	@Test
	public void testPart1() {
		assertEquals(3, Day19.getElfWithPresentsPart1(5));
	}

	@Test
	public void testPart2() {
		assertEquals(2, Day19.getElfWithPresentsPart2(5));
	}
}
