package com.grahamedgecombe.advent2016;

import java.util.Optional;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day1Test {
	@Test
	public void testPart1() {
		assertEquals(5, Day1.run("R2, L3").getHqDistance());
		assertEquals(2, Day1.run("R2, R2, R2").getHqDistance());
		assertEquals(12, Day1.run("R5, L5, R5, R3").getHqDistance());
	}

	@Test
	public void testPart2() {
		assertEquals(Optional.of(4), Day1.run("R8, R4, R4, R8").getVisitedTwiceDistance());
	}
}
