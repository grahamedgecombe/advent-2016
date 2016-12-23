package com.grahamedgecombe.advent2016;

import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class Day23Test {
	@Test
	public void testPart1() {
		VirtualMachine vm = VirtualMachine.create(Arrays.asList(
			"cpy 2 a",
			"tgl a",
			"tgl a",
			"tgl a",
			"cpy 1 a",
			"dec a",
			"dec a"
		));
		vm.run();
		assertEquals(3, vm.get(VirtualMachine.REGISTER_A));
	}
}
