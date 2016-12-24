package com.grahamedgecombe.advent2016;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.google.common.collect.Collections2;

public final class Day24 {
	public static void main(String[] args) throws IOException {
		AirDuctMap map = AirDuctMap.parse(AdventUtils.readLines("day24.txt"));
		System.out.println(map.getMinimumSteps());
		System.out.println(map.getMinimumStepsReturning());
	}

	public static final class AirDuctMap {
		public static AirDuctMap parse(List<String> lines) {
			int width = lines.get(0).length();
			int height = lines.size();
			List<Point> points = new ArrayList<>();

			char[][] tiles = new char[width][height];
			for (int y = 0; y < height; y++) {
				String line = lines.get(y);
				for (int x = 0; x < width; x++) {
					char tile = tiles[x][y] = line.charAt(x);
					if (tile >= '0' && tile <= '9') {
						points.add(new Point(x, y, tile - '0'));
					}
				}
			}

			return new AirDuctMap(tiles, width, height, points);
		}

		private final char[][] tiles;
		private final int width, height;
		private final List<Point> points;

		private AirDuctMap(char[][] tiles, int width, int height, List<Point> points) {
			this.tiles = tiles;
			this.width = width;
			this.height = height;
			this.points = points;
		}

		private class Node extends AStar.Node<Node> {
			private final int x, y, destX, destY;

			public Node(int x, int y, int destX, int destY) {
				this.x = x;
				this.y = y;
				this.destX = destX;
				this.destY = destY;
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
						if (x < 0 || y < 0 || x >= width || y >= height) {
							continue;
						}

						if (tiles[x][y] == '#') {
							continue;
						}

						neighbours.add(new Node(x, y, destX, destY));
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
				return Math.abs(x - destX) + Math.abs(y - destY);
			}

			@Override
			public boolean equals(Object o) {
				if (this == o) return true;
				if (o == null || getClass() != o.getClass()) return false;
				Node node = (Node) o;
				return x == node.x &&
					y == node.y &&
					destX == node.destX &&
					destY == node.destY;
			}

			@Override
			public int hashCode() {
				return Objects.hash(x, y, destX, destY);
			}
		}

		private int getMinimumSteps(boolean returning) {
			Map<Pair, Integer> distances = new HashMap<>();

			/* find the shortest path between each pair of points */
			for (int i = 0; i < points.size(); i++) {
				for (int j = 0; j < i; j++) {
					Point a = points.get(i);
					Point b = points.get(j);

					Optional<Integer> len = AStar.search(new Node(a.x, a.y, b.x, b.y)).map(l -> l.size() - 1);
					if (!len.isPresent()) {
						throw new IllegalArgumentException();
					}

					distances.put(new Pair(a, b), len.get());
					distances.put(new Pair(b, a), len.get());
				}
			}

			/* find total distance for all permutations of the points */
			int minSteps = Integer.MAX_VALUE;
			for (List<Point> path : Collections2.permutations(points)) {
				Point start = path.get(0);
				if (start.number != 0) {
					continue;
				}

				if (returning) {
					path = new ArrayList<>(path);
					path.add(start);
				}

				int distance = 0;
				for (int i = 0; i < path.size() - 1; i++) {
					Point a = path.get(i);
					Point b = path.get(i + 1);
					distance += distances.get(new Pair(a, b));
				}

				minSteps = Math.min(minSteps, distance);
			}

			return minSteps;
		}

		public int getMinimumSteps() {
			return getMinimumSteps(false);
		}

		public int getMinimumStepsReturning() {
			return getMinimumSteps(true);
		}
	}

	private static final class Point {
		private final int x, y, number;

		private Point(int x, int y, int number) {
			this.x = x;
			this.y = y;
			this.number = number;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Point point = (Point) o;
			return x == point.x &&
				y == point.y &&
				number == point.number;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y, number);
		}
	}

	private static final class Pair {
		private final Point a, b;

		private Pair(Point a, Point b) {
			this.a = a;
			this.b = b;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Pair pair = (Pair) o;
			return Objects.equals(a, pair.a) &&
				Objects.equals(b, pair.b);
		}

		@Override
		public int hashCode() {
			return Objects.hash(a, b);
		}
	}

	private Day24() {
		/* empty */
	}
}
