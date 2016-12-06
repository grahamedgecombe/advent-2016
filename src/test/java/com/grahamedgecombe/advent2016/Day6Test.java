package com.grahamedgecombe.advent2016;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day6Test {
	private static final List<String> messages = Arrays.asList(
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
		assertEquals("easter", Day6.getMessage(messages, Day6.GREATER_THAN));
	}

	@Test
	public void testPart2() {
		assertEquals("advent", Day6.getMessage(messages, Day6.LESS_THAN));
	}
}
