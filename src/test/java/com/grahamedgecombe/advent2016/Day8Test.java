package com.grahamedgecombe.advent2016;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day8Test {
	private static final int SCREEN_WIDTH = 7;
	private static final int SCREEN_HEIGHT = 3;
	private static final ImmutableList<String> INSTRUCTIONS = ImmutableList.of(
		"rect 3x2",
		"rotate column x=1 by 1",
		"rotate row y=0 by 4",
		"rotate column x=1 by 1"
	);

	@Test
	public void testPart1() {
		assertEquals(6, Day8.Screen.parse(SCREEN_WIDTH, SCREEN_HEIGHT, INSTRUCTIONS).getLitPixels());
	}

	@Test
	public void testPart2() {
		assertEquals(".#..#.#\n#.#....\n.#.....", Day8.Screen.parse(SCREEN_WIDTH, SCREEN_HEIGHT, INSTRUCTIONS).toString());
	}
}
