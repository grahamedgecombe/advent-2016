package com.grahamedgecombe.advent2016;

import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day12Test {
	@Test
	public void testPart1() {
		VirtualMachine vm = VirtualMachine.create(Arrays.asList(
			"cpy 41 a",
			"inc a",
			"inc a",
			"dec a",
			"jnz a 2",
			"dec a"
		));
		vm.run();
		assertEquals(42, vm.get(VirtualMachine.REGISTER_A));
	}
}
