package com.grahamedgecombe.advent2016;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class Day13 {
	public static void main(String[] args) throws IOException {
		int favouriteNumber = Integer.parseInt(AdventUtils.readString("day13.txt"));
		Day13 day13 = new Day13(favouriteNumber, 31, 39);
		System.out.println(day13.getSteps());
	}

	private final int favouriteNumber, destX, destY;

	public Day13(int favouriteNumber, int destX, int destY) {
		this.favouriteNumber = favouriteNumber;
		this.destX = destX;
		this.destY = destY;
	}

	public Optional<Integer> getSteps() {
		return AStar.search(new Node(1, 1)).map(List::size).map(i -> i - 1);
	}

	private final class Node extends AStar.Node<Node> {
		private final int x, y;

		private Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean isGoal() {
			return x == destX && y == destY;
		}

		@Override
		public Iterable<Node> getNeighbours() {
			List<Node> neighbours = new ArrayList<>();

			for (int dx = -1; dx <= 1; dx++) {
				for (int dy = -1; dy <= 1; dy++) {
					if (dx == dy || dx == -dy) {
						continue;
					}

					int x = this.x + dx;
					int y = this.y + dy;

					if (x < 0|| y < 0) {
						continue;
					}

					int f = x*x + 3*x + 2*x*y + y + y*y + favouriteNumber;
					if ((Integer.bitCount(f) & 1) == 0) {
						neighbours.add(new Node(x, y));
					}
				}
			}

			return neighbours;
		}

		@Override
		public int getCost() {
			return 1;
		}

		@Override
		public int getDistance(Node neighbour) {
			return Math.abs(x - neighbour.x) + Math.abs(y - neighbour.y);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Node)) return false;
			Node node = (Node) o;
			return x == node.x &&
				y == node.y;
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
}
