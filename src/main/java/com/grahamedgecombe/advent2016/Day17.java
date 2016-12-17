package com.grahamedgecombe.advent2016;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

public final class Day17 {
	public static void main(String[] args) throws IOException {
		Day17 day17 = new Day17(AdventUtils.readString("day17.txt"));
		System.out.println(day17.getShortestPath());
		System.out.println(day17.getLongestPathLength());
	}

	private final String passcode;
	private final Node root = new Node(0, 0, "");

	public Day17(String passcode) {
		this.passcode = passcode;
	}

	public Optional<String> getShortestPath() {
		Optional<List<Node>> path = Bfs.search(root);
		if (!path.isPresent()) {
			return Optional.empty();
		}

		List<Node> nodes = path.get();
		if (nodes.isEmpty()) {
			return Optional.empty();
		}

		Node lastNode = nodes.get(nodes.size() - 1);
		return Optional.of(lastNode.path);
	}

	public OptionalInt getLongestPathLength() {
		return Bfs.searchAll(root).stream().mapToInt(List::size).map(i -> i - 1).max();
	}

	private final class Node extends Bfs.Node<Node> {
		private final int x, y;
		private final String path;

		private Node(int x, int y, String path) {
			this.x = x;
			this.y = y;
			this.path = path;
		}

		@Override
		public boolean isGoal() {
			return x == 3 && y == 3;
		}

		@Override
		public Iterable<Node> getNeighbours() {
			List<Node> neighbours = new ArrayList<>();
			addNeighbour(neighbours, 'U', 0, 0, -1);
			addNeighbour(neighbours, 'D', 1, 0, 1);
			addNeighbour(neighbours, 'L', 2, -1, 0);
			addNeighbour(neighbours, 'R', 3, 1, 0);
			return neighbours;
		}

		private void addNeighbour(List<Node> neighbours, char direction, int hashIndex, int dx, int dy) {
			int x = this.x + dx;
			if (x < 0 || x > 3) {
				return;
			}

			int y = this.y + dy;
			if (y < 0 || y > 3) {
				return;
			}

			String hash = Md5.hash(passcode + path);
			char ch = hash.charAt(hashIndex);
			if (ch >= 'b' && ch <= 'f') {
				neighbours.add(new Node(x, y, path + direction));
			}
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Node)) return false;
			Node node = (Node) o;
			return x == node.x &&
				y == node.y &&
				Objects.equals(path, node.path);
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y, path);
		}
	}
}
