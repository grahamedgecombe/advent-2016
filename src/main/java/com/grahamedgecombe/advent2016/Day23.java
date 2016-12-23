package com.grahamedgecombe.advent2016;

import java.io.IOException;

public final class Day23 {
	public static void main(String[] args) throws IOException {
		VirtualMachine vm = VirtualMachine.create(AdventUtils.readLines("day23.txt"));
		vm.set(VirtualMachine.REGISTER_A, 7);
		vm.run();
		System.out.println(vm.get(VirtualMachine.REGISTER_A));

		/* reset() doesn't work as TGL modifies the instructions */
		vm = VirtualMachine.create(AdventUtils.readLines("day23.txt"));
		vm.set(VirtualMachine.REGISTER_A, 12);
		vm.run();
		System.out.println(vm.get(VirtualMachine.REGISTER_A));
	}

	private Day23() {
		/* empty */
	}
}
