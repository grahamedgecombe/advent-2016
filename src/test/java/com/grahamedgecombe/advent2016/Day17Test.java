package com.grahamedgecombe.advent2016;

import java.util.Optional;
import java.util.OptionalInt;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day17Test {
	@Test
	public void testPart1() {
		assertEquals(Optional.empty(), new Day17("hijkl").getShortestPath());
		assertEquals(Optional.of("DDRRRD"), new Day17("ihgpwlah").getShortestPath());
		assertEquals(Optional.of("DDUDRLRRUDRD"), new Day17("kglvqrro").getShortestPath());
		assertEquals(Optional.of("DRURDRUDDLLDLUURRDULRLDUUDDDRR"), new Day17("ulqzkmiv").getShortestPath());
	}

	@Test
	public void testPart2() {
		assertEquals(OptionalInt.of(370), new Day17("ihgpwlah").getLongestPathLength());
		assertEquals(OptionalInt.of(492), new Day17("kglvqrro").getLongestPathLength());
		assertEquals(OptionalInt.of(830), new Day17("ulqzkmiv").getLongestPathLength());
	}
}
