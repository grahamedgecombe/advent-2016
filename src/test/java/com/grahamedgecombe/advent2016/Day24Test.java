package com.grahamedgecombe.advent2016;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day24Test {
	private static final ImmutableList<String> MAP = ImmutableList.of(
		"###########",
		"#0.1.....2#",
		"#.#######.#",
		"#4.......3#",
		"###########"
	);

	@Test
	public void testPart1() {
		Day24.AirDuctMap map = Day24.AirDuctMap.parse(MAP);
		assertEquals(14, map.getMinimumSteps());
	}

	@Test
	public void testPart2() {
		Day24.AirDuctMap map = Day24.AirDuctMap.parse(MAP);
		assertEquals(20, map.getMinimumStepsReturning());
	}
}
