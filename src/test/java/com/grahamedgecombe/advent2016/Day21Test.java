package com.grahamedgecombe.advent2016;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day21Test {
	private final ImmutableList<String> INSTRUCTIONS = ImmutableList.of(
		"swap position 4 with position 0",
		"swap letter d with letter b",
		"reverse positions 0 through 4",
		"rotate left 1 step",
		"move position 1 to position 4",
		"move position 3 to position 0",
		"rotate based on position of letter b",
		"rotate based on position of letter d"
	);

	@Test
	public void testPart1() {
		assertEquals("decab", Day21.scramble(INSTRUCTIONS, "abcde"));
	}

	@Test
	public void testPart2() {
		assertEquals("abcde", Day21.unscramble(INSTRUCTIONS, "decab"));
	}
}
