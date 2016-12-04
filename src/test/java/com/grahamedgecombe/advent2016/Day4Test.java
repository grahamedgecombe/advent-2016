package com.grahamedgecombe.advent2016;

import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day4Test {
	@Test
	public void testPart1() {
		assertEquals(123, Day4.sumValidSectorIds(Arrays.asList("aaaaa-bbb-z-y-x-123[abxyz]")));
		assertEquals(987, Day4.sumValidSectorIds(Arrays.asList("a-b-c-d-e-f-g-h-987[abcde]")));
		assertEquals(404, Day4.sumValidSectorIds(Arrays.asList("not-a-real-room-404[oarel]")));
		assertEquals(0, Day4.sumValidSectorIds(Arrays.asList("totally-real-room-200[decoy]")));
	}

	@Test
	public void testPart2() {
		assertEquals(343, Day4.getSectorId(Arrays.asList("qzmt-zixmtkozy-ivhz-343[zimth]"), "very encrypted name"));
	}
}
