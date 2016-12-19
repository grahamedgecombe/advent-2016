package com.grahamedgecombe.advent2016;

import java.io.IOException;

public final class Day19 {
	public static void main(String[] args) throws IOException {
		int elves = Integer.parseInt(AdventUtils.readString("day19.txt"));
		System.out.println(getElfWithPresentsPart1(elves));
		System.out.println(getElfWithPresentsPart2(elves));
	}

	private static final class Elf {
		private final int number;
		private Elf prev, next;

		private Elf(int number) {
			this.number = number;
			this.prev = this;
			this.next = this;
		}

		private boolean hasNext() {
			return next != this;
		}

		private void add(Elf elf) {
			elf.prev = this;
			elf.next = next;
			next.prev = elf;
			next = elf;
		}

		private void remove() {
			prev.next = next;
			next.prev = prev;
		}
	}

	private static Elf createElves(int elves) {
		Elf head = new Elf(1);

		Elf elf = head;
		for (int i = 2; i <= elves; i++) {
			Elf next = new Elf(i);
			elf.add(next);
			elf = next;
		}

		return head;
	}

	public static int getElfWithPresentsPart1(int elves) {
		Elf elf = createElves(elves);

		while (elf.hasNext()) {
			elf.next.remove();
			elf = elf.next;
		}

		return elf.number;
	}

	public static int getElfWithPresentsPart2(int elves) {
		Elf head = createElves(elves);

		Elf opposite = head;
		for (int i = 0; i < elves / 2; i++) {
			opposite = opposite.next;
		}

		boolean odd = (elves & 1) != 0;

		Elf elf = head;
		while (elf.hasNext()) {
			opposite.remove();

			opposite = opposite.next;
			if (odd) {
				opposite = opposite.next;
			}
			odd = !odd;

			elf = elf.next;
		}

		return elf.number;
	}

	private Day19() {
		/* empty */
	}
}
