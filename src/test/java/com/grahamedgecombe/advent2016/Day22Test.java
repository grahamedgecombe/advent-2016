package com.grahamedgecombe.advent2016;

import java.util.Optional;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day22Test {
	private static final ImmutableList<String> DF_LINES = ImmutableList.of(
		"root@ebhq-gridcenter# df -h",
		"Filesystem            Size  Used  Avail  Use%",
		"/dev/grid/node-x0-y0   10T    8T     2T   80%",
		"/dev/grid/node-x0-y1   11T    6T     5T   54%",
		"/dev/grid/node-x0-y2   32T   28T     4T   87%",
		"/dev/grid/node-x1-y0    9T    7T     2T   77%",
		"/dev/grid/node-x1-y1    8T    0T     8T    0%",
		"/dev/grid/node-x1-y2   11T    7T     4T   63%",
		"/dev/grid/node-x2-y0   10T    6T     4T   60%",
		"/dev/grid/node-x2-y1    9T    8T     1T   88%",
		"/dev/grid/node-x2-y2    9T    6T     3T   66%"
	);

	@Test
	public void testPart1() {
		Day22.Cluster cluster = Day22.Cluster.parse(DF_LINES, 32);
		assertEquals(7, cluster.getViablePairs());
	}

	@Test
	public void testPart2() {
		Day22.Cluster cluster = Day22.Cluster.parse(DF_LINES, 32);
		assertEquals("...\n._.\n#..", cluster.toString());
		assertEquals(Optional.of(7), cluster.getSteps());
	}
}
