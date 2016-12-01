package com.grahamedgecombe.advent2016;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class Day1 {
	public static void main(String[] args) throws IOException {
		Result result = Day1.run(AdventUtils.readString("day1.txt"));
		System.out.println(result.getHqDistance());
		System.out.println(result.getVisitedTwiceDistance());
	}

	public static final class Result {
		private final int hqDistance;
		private final Optional<Integer> visitedTwiceDistance;

		public Result(int hqDistance, Optional<Integer> visitedTwiceDistance) {
			this.hqDistance = hqDistance;
			this.visitedTwiceDistance = visitedTwiceDistance;
		}

		public int getHqDistance() {
			return hqDistance;
		}

		public Optional<Integer> getVisitedTwiceDistance() {
			return visitedTwiceDistance;
		}
	}

	private static final class Block {
		private final int x, y;

		public Block(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Block)) return false;
			Block block = (Block) o;
			return x == block.x &&
				y == block.y;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}

		@Override
		public String toString() {
			return "(" + x + ", " + y + ")";
		}
	}

	public static Result run(String instructions) {
		int direction = 0, x = 0, y = 0;
		Set<Block> visited = new HashSet<>();
		Optional<Block> visitedTwice = Optional.empty();

		visited.add(new Block(0, 0));

		for (String instruction : instructions.split(",\\s*")) {
			char turn = instruction.charAt(0);
			int blocks = Integer.parseInt(instruction.substring(1));

			if (turn == 'L') {
				direction--;
			} else { /* turn == 'R' */
				direction++;
			}

			direction &= 3;

			for (int i = 0; i < blocks; i++) {
				if (direction == 0) {
					y--;
				} else if (direction == 1) {
					x++;
				} else if (direction == 2) {
					y++;
				} else { /* direction == 3 */
					x--;
				}

				Block block = new Block(x, y);

				if (!visitedTwice.isPresent() && visited.contains(block)) {
					visitedTwice = Optional.of(block);
				}

				visited.add(block);
			}
		}

		int hqDistance = Math.abs(x) + Math.abs(y);
		Optional<Integer> visitedTwiceDistance = visitedTwice.map(block -> Math.abs(block.x) + Math.abs(block.y));

		return new Result(hqDistance, visitedTwiceDistance);
	}

	private Day1() {
		/* empty */
	}
}
