package com.grahamedgecombe.advent2016;

import java.io.IOException;

public final class Day12 {
	public static void main(String[] args) throws IOException {
		VirtualMachine vm = VirtualMachine.create(AdventUtils.readLines("day12.txt"));
		vm.run();
		System.out.println(vm.get(VirtualMachine.REGISTER_A));

		vm.reset();
		vm.set(VirtualMachine.REGISTER_C, 1);
		vm.run();
		System.out.println(vm.get(VirtualMachine.REGISTER_A));
	}

	private Day12() {
		/* empty */
	}
}
