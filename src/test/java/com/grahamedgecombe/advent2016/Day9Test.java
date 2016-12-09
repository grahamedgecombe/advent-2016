package com.grahamedgecombe.advent2016;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day9Test {
	@Test
	public void testPart1() {
		assertEquals("ADVENT".length(), Day9.decompress("ADVENT"));
		assertEquals("ABBBBBC".length(), Day9.decompress("A(1x5)BC"));
		assertEquals("XYZXYZXYZ".length(), Day9.decompress("(3x3)XYZ"));
		assertEquals("ABCBCDEFEFG".length(), Day9.decompress("A(2x2)BCD(2x2)EFG"));
		assertEquals("(1x3)A".length(), Day9.decompress("(6x1)(1x3)A"));
		assertEquals("X(3x3)ABC(3x3)ABCY".length(), Day9.decompress("X(8x2)(3x3)ABCY"));
	}

	@Test
	public void testPart2() {
		assertEquals("XYZXYZXYZ".length(), Day9.decompressRecursively("(3x3)XYZ"));
		assertEquals("XABCABCABCABCABCABCY".length(), Day9.decompressRecursively("X(8x2)(3x3)ABCY"));
		assertEquals(241920, Day9.decompressRecursively("(27x12)(20x12)(13x14)(7x10)(1x12)A"));
		assertEquals(445, Day9.decompressRecursively("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN"));
	}
}
