package com.grahamedgecombe.advent2016;

import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day20Test {
	@Test
	public void testPart1() {
		Day20.Blacklist blacklist = Day20.Blacklist.parse(Arrays.asList("5-8", "0-2", "4-7"));
		assertEquals(3, blacklist.getFirstUnblockedIp());
	}

	@Test
	public void testPart2() {
		Day20.Blacklist blacklist = Day20.Blacklist.parse(Arrays.asList("5-8", "0-2", "4-7"));
		assertEquals(2, blacklist.getAllowedIps(0, 9));
	}
}
