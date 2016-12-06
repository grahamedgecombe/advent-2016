package com.grahamedgecombe.advent2016;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day6Test {
	private static final ImmutableList<String> MESSAGES = ImmutableList.of(
		"eedadn",
		"drvtee",
		"eandsr",
		"raavrd",
		"atevrs",
		"tsrnev",
		"sdttsa",
		"rasrtv",
		"nssdts",
		"ntnada",
		"svetve",
		"tesnvt",
		"vntsnd",
		"vrdear",
		"dvrsen",
		"enarar"
	);

	@Test
	public void testPart1() {
		assertEquals("easter", Day6.getMessage(MESSAGES, Day6.GREATER_THAN));
	}

	@Test
	public void testPart2() {
		assertEquals("advent", Day6.getMessage(MESSAGES, Day6.LESS_THAN));
	}
}
