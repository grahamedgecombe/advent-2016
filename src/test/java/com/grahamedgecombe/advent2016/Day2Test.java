package com.grahamedgecombe.advent2016;

import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day2Test {
	@Test
	public void testPart1() {
		assertEquals("1985", Day2.getBathroomCode(Arrays.asList("ULL", "RRDDD", "LURDL", "UUUUD"), Day2.PART_1_KEYPAD, Day2.PART_1_START_X, Day2.PART_1_START_Y));
	}

	@Test
	public void testPart2() {
		assertEquals("5DB3", Day2.getBathroomCode(Arrays.asList("ULL", "RRDDD", "LURDL", "UUUUD"), Day2.PART_2_KEYPAD, Day2.PART_2_START_X, Day2.PART_2_START_Y));
	}
}
