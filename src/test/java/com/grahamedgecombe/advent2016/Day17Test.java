package com.grahamedgecombe.advent2016;

import java.util.Optional;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day17Test {
	@Test
	public void testPart1() {
		assertEquals(Optional.empty(), new Day17("hijkl").getPath());
		assertEquals(Optional.of("DDRRRD"), new Day17("ihgpwlah").getPath());
		assertEquals(Optional.of("DDUDRLRRUDRD"), new Day17("kglvqrro").getPath());
		assertEquals(Optional.of("DRURDRUDDLLDLUURRDULRLDUUDDDRR"), new Day17("ulqzkmiv").getPath());
	}
}
