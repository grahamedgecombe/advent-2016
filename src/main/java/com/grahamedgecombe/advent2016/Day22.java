package com.grahamedgecombe.advent2016;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day22 {
	private static final Pattern NODE_PATTERN = Pattern.compile("^/dev/grid/node-x(\\d+?)-y(\\d+?)\\s+(\\d+?)T\\s+(\\d+?)T\\s+(\\d+?)T\\s+(\\d+?)%$");

	public static void main(String[] args) throws IOException {
		Cluster cluster = Cluster.parse(AdventUtils.readLines("day22.txt"), 500);
		System.out.println(cluster.getViablePairs());
		System.out.println(cluster.getSteps());
	}

	public static final class Cluster {
		public static Cluster parse(List<String> lines, int bigThreshold) {
			Disk[] disks = new Disk[lines.size() - 2];

			int width = 0, height = 0;
			for (int i = 0; i < disks.length; i++) {
				Matcher matcher = NODE_PATTERN.matcher(lines.get(i + 2));
				if (!matcher.matches()) {
					throw new IllegalArgumentException();
				}

				int x = Integer.parseInt(matcher.group(1));
				int y = Integer.parseInt(matcher.group(2));
				int size = Integer.parseInt(matcher.group(3));
				int used = Integer.parseInt(matcher.group(4));

				disks[i] = new Disk(x, y, size, used, size >= bigThreshold);

				width = Math.max(x + 1, width);
				height = Math.max(y + 1, height);
			}

			Disk[][] map = new Disk[width][height];
			for (Disk disk : disks) {
				map[disk.x][disk.y] = disk;
			}

			return new Cluster(disks, map, width, height);
		}

		private final Disk[] disks;
		private final Disk[][] map;
		private final int width, height;

		private Cluster(Disk[] disks, Disk[][] map, int width, int height) {
			this.disks = disks;
			this.map = map;
			this.width = width;
			this.height = height;
		}

		public int getViablePairs() {
			int viable = 0;

			for (int i = 0; i < disks.length; i++) {
				for (int j = 0; j < disks.length; j++) {
					if (i == j) {
						continue;
					}

					if (disks[i].isViable(disks[j])) {
						viable++;
					}
				}
			}

			return viable;
		}

		/* makes lots of assumptions about this particular case! */
		public Optional<Integer> getSteps() {
			/* find position of empty disk */
			int emptyX = -1, emptyY = -1;

			outer:
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					if (map[x][y].isEmpty()) {
						emptyX = x;
						emptyY = y;
						break outer;
					}
				}
			}

			if (emptyX == -1 || emptyY == -1) {
				throw new IllegalArgumentException();
			}

			/* data is at the top right */
			int dataX = width - 1;
			int dataY = 0;

			return AStar.search(new Node(emptyX, emptyY, dataX, dataY)).map(l -> l.size() - 1);
		}

		@Override
		public String toString() {
			StringBuilder buf = new StringBuilder();

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					buf.append(map[x][y].toString());
				}

				if (y != (height - 1)) {
					buf.append("\n");
				}
			}

			return buf.toString();
		}

		private final class Node extends AStar.Node<Node> {
			private final int emptyX, emptyY, dataX, dataY;

			private Node(int emptyX, int emptyY, int dataX, int dataY) {
				this.emptyX = emptyX;
				this.emptyY = emptyY;
				this.dataX = dataX;
				this.dataY = dataY;
			}

			@Override
			public boolean isGoal() {
				return dataX == 0 && dataY == 0;
			}

			@Override
			public Iterable<Node> getNeighbours() {
				List<Node> neighbours = new ArrayList<>();

				for (int dx = -1; dx <= 1; dx++) {
					for (int dy = -1; dy <= 1; dy++) {
						if (dx == dy || dx == -dy) {
							continue;
						}

						int newEmptyX = emptyX + dx;
						int newEmptyY = emptyY + dy;
						if (newEmptyX < 0 || newEmptyY < 0 || newEmptyX >= width || newEmptyY >= height) {
							continue;
						}

						if (newEmptyX == dataX && newEmptyY == dataY) {
							neighbours.add(new Node(dataX, dataY, emptyX, emptyY));
						} else if (!map[newEmptyX][newEmptyY].isBig()) {
							neighbours.add(new Node(newEmptyX, newEmptyY, dataX, dataY));
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
				int dataDistance = dataX + dataY;
				int emptyDistance = Math.abs(emptyX - dataX) + Math.abs(emptyY - dataY);
				return dataDistance + emptyDistance;
			}

			@Override
			public boolean equals(Object o) {
				if (this == o) return true;
				if (!(o instanceof Node)) return false;
				Node node = (Node) o;
				return emptyX == node.emptyX &&
					emptyY == node.emptyY &&
					dataX == node.dataX &&
					dataY == node.dataY;
			}

			@Override
			public int hashCode() {
				return Objects.hash(emptyX, emptyY, dataX, dataY);
			}

			@Override
			public String toString() {
				return "E: (" + emptyX + ", " + emptyY + ") D: (" + dataX + ", " + dataY + ")";
			}
		}
	}

	private static final class Disk {
		private final int x, y, size, used;
		private final boolean big;

		private Disk(int x, int y, int size, int used, boolean big) {
			this.x = x;
			this.y = y;
			this.size = size;
			this.used = used;
			this.big = big;
		}

		private boolean isViable(Disk o) {
			int avail = o.size - o.used;
			return used != 0 && used <= avail;
		}

		private boolean isEmpty() {
			return used == 0;
		}

		private boolean isBig() {
			return big;
		}

		@Override
		public String toString() {
			if (isEmpty()) {
				return "_";
			} else if (isBig()) {
				return "#";
			} else {
				return ".";
			}
		}
	}

	private Day22() {
		/* empty */
	}
}
