package com.grahamedgecombe.advent2016;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day10Test {
	private static final ImmutableList<String> INSTRUCTIONS = ImmutableList.of(
		"value 5 goes to bot 2",
		"bot 2 gives low to bot 1 and high to bot 0",
		"value 3 goes to bot 1",
		"bot 1 gives low to output 1 and high to bot 0",
		"bot 0 gives low to output 2 and high to output 0",
		"value 2 goes to bot 2"
	);

	@Test
	public void testPart1() {
		Day10.Factory factory = Day10.Factory.create(INSTRUCTIONS);
		assertEquals(2, factory.getBotComparing(5, 2));
		assertEquals(1, factory.getBotComparing(2, 3));
		assertEquals(0, factory.getBotComparing(5, 3));
	}

	@Test
	public void testPart2() {
		Day10.Factory factory = Day10.Factory.create(INSTRUCTIONS);
		assertEquals(30, factory.multiplyOutputs(0, 1, 2));
	}
}
