package com.grahamedgecombe.advent2016;

import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day15Test {
	@Test
	public void testPart1() {
		assertEquals(5, Day15.getFirstCapsuleTime(Arrays.asList(
			"Disc #1 has 5 positions; at time=0, it is at position 4.",
			"Disc #2 has 2 positions; at time=0, it is at position 1."
		)));
	}
}
