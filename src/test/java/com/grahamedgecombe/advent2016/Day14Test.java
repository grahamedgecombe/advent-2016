package com.grahamedgecombe.advent2016;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day14Test {
	@Test
	public void testPart1() {
		assertEquals(22728, Day14.getIndex("abc", Day14.PART_1_ITERATIONS));
	}

	@Test
	public void testPart2() {
		assertEquals(22551, Day14.getIndex("abc", Day14.PART_2_ITERATIONS));
	}
}
