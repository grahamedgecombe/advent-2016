package com.grahamedgecombe.advent2016;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day18Test {
	@Test
	public void testPart1() {
		assertEquals(".^^^^", Day18.evolve("..^^."));
		assertEquals("^^..^", Day18.evolve(".^^^^"));

		assertEquals(38, Day18.countSafeTiles(".^^.^.^^^^", 10));
	}
}
