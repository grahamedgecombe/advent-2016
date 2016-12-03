package com.grahamedgecombe.advent2016;

import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day3Test {
	@Test
	public void testPart1() {
		assertEquals(0, Day3.getPossibleTrianglesHorizontally(Arrays.asList("5 10 25")));
	}
}
