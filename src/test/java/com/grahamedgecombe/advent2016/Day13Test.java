package com.grahamedgecombe.advent2016;

import java.util.Optional;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day13Test {
	@Test
	public void testPart1() {
		assertEquals(Optional.of(11), new Day13(10, 7, 4).getSteps());
	}
}
