package com.grahamedgecombe.advent2016;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day11Test {
	@Test
	public void testPart1() {
		assertEquals(Optional.of(11), Day11.getSteps(Arrays.asList(
			"The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.",
			"The second floor contains a hydrogen generator.",
			"The third floor contains a lithium generator.",
			"The fourth floor contains nothing relevant."
		)));
	}
}
