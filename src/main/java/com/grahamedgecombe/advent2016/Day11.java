package com.grahamedgecombe.advent2016;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Day11 {
	private static final Pattern ITEM_PATTERN = Pattern.compile("([a-z]*)( generator|-compatible microchip)");

	public static void main(String[] args) throws IOException {
		List<String> lines = AdventUtils.readLines("day11.txt");
		System.out.println(getSteps(lines));

		lines.set(0, lines.get(0) + "elerium generator elerium-compatible microchip dilithium generator dilithium-compatible microchip");
		System.out.println(getSteps(lines));
	}

	public static Optional<Integer> getSteps(List<String> lines) {
		return AStar.search(Node.parse(lines)).map(List::size).map(i -> i - 1);
	}

	private enum Type {
		GENERATOR,
		MICROCHIP
	}

	private static final class Item implements Comparable<Item> {
		private final String material;
		private final Type type;

		private Item(String material, Type type) {
			this.material = material;
			this.type = type;
		}

		@Override
		public String toString() {
			return material + " " + type.toString().toLowerCase();
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Item)) return false;
			Item item = (Item) o;
			return material.equals(item.material) &&
				type == item.type;
		}

		@Override
		public int hashCode() {
			return Objects.hash(material, type);
		}

		@Override
		public int compareTo(Item o) {
			int cmp = material.compareTo(o.material);
			if (cmp != 0) {
				return cmp;
			}

			return type.compareTo(o.type);
		}
	}

	private static final class Pair<T extends Comparable<T>> implements Comparable<Pair<T>> {
		private final T x, y;

		private Pair(T x, T y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Pair<T> o) {
			int cmp = x.compareTo(o.x);
			if (cmp != 0) {
				return cmp;
			}

			return y.compareTo(o.y);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Pair)) return false;
			Pair<?> pair = (Pair<?>) o;
			return Objects.equals(x, pair.x) &&
				Objects.equals(y, pair.y);
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

	private static final class Floor {
		private final List<Item> items;

		private Floor(List<Item> items) {
			Collections.sort(items);
			this.items = items;
		}

		private List<List<Item>> getItemCombinations() {
			List<List<Item>> combinations = new ArrayList<>();

			for (int i = 0; i < items.size(); i++) {
				for (int j = 0; j < i; j++) {
					Item item1 = items.get(i);
					Item item2 = items.get(j);

					combinations.add(Arrays.asList(item1, item2));
				}
			}

			for (Item item : items) {
				combinations.add(Collections.singletonList(item));
			}

			return combinations;
		}

		private boolean isEmpty() {
			return items.isEmpty();
		}

		private boolean isIsolated() {
			List<String> generators = items.stream()
				.filter(item -> item.type == Type.GENERATOR)
				.map(item -> item.material)
				.collect(Collectors.toList());

			List<String> microchips = items.stream()
				.filter(item -> item.type == Type.MICROCHIP)
				.map(item -> item.material)
				.collect(Collectors.toList());

			return generators.isEmpty() || generators.containsAll(microchips);
		}

		@Override
		public String toString() {
			return items.toString();
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Floor)) return false;
			Floor floor = (Floor) o;
			return Objects.equals(items, floor.items);
		}

		@Override
		public int hashCode() {
			return Objects.hash(items);
		}
	}

	private static final class Node extends AStar.Node<Node> {
		private static Node parse(List<String> lines) {
			Floor[] floors = new Floor[lines.size()];

			for (int i = 0; i < floors.length; i++) {
				List<Item> items = new ArrayList<>();

				Matcher matcher = ITEM_PATTERN.matcher(lines.get(i));
				while (matcher.find()) {
					String material = matcher.group(1);

					Type type;
					if (matcher.group(2).equals(" generator")) {
						type = Type.GENERATOR;
					} else { /* microchip */
						type = Type.MICROCHIP;
					}

					items.add(new Item(material, type));
				}

				floors[i] = new Floor(items);
			}

			return new Node(0, floors);
		}

		private final int level;
		private final Floor[] floors;
		private final List<Pair<Integer>> pairs;

		private Node(int level, Floor[] floors) {
			this.level = level;
			this.floors = floors;
			this.pairs = getPairs();
		}

		private List<Pair<Integer>> getPairs() {
			List<Pair<Integer>> pairs = new ArrayList<>();

			SortedMap<Item, Integer> itemToFloor = new TreeMap<>();
			for (int i = 0; i < floors.length; i++) {
				for (Item item : floors[i].items) {
					itemToFloor.put(item, i);
				}
			}

			Iterator<Item> it = itemToFloor.keySet().iterator();
			while (it.hasNext()) {
				int x = itemToFloor.get(it.next());
				int y = itemToFloor.get(it.next());
				pairs.add(new Pair<>(x, y));
			}

			Collections.sort(pairs);
			return pairs;
		}

		@Override
		public int getCost() {
			int cost = 0;
			for (int i = 0; i < floors.length - 1; i++) {
				cost += floors[i].items.size() * (floors.length - i);
			}
			return cost;
		}

		@Override
		public int getDistance(Node neighbour) {
			return 1;
		}

		@Override
		public boolean isGoal() {
			for (int i = 0; i < floors.length - 1; i++) {
				if (!floors[i].isEmpty()) {
					return false;
				}
			}

			return true;
		}

		@Override
		public List<Node> getNeighbours() {
			List<Node> neighbours = new ArrayList<>();

			if (level > 0) {
				addNeighbours(neighbours, level - 1);
			}

			if (level < (floors.length - 1)) {
				addNeighbours(neighbours, level + 1);
			}

			return neighbours;
		}

		private void addNeighbours(List<Node> neighbours, int destLevel) {
			Floor floor = floors[level];
			Floor destFloor = floors[destLevel];

			for (List<Item> combination : floor.getItemCombinations()) {
				List<Item> items = new ArrayList<>(floor.items);
				List<Item> destItems = new ArrayList<>(destFloor.items);

				items.removeAll(combination);
				destItems.addAll(combination);

				Floor newFloor = new Floor(items);
				Floor newDestFloor = new Floor(destItems);

				if (!newFloor.isIsolated() || !newDestFloor.isIsolated()) {
					continue;
				}

				Floor[] newFloors = floors.clone();
				newFloors[level] = newFloor;
				newFloors[destLevel] = newDestFloor;

				neighbours.add(new Node(destLevel, newFloors));
			}
		}

		@Override
		public String toString() {
			return level + ": " + Arrays.toString(floors);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Node)) return false;
			Node node = (Node) o;
			return level == node.level &&
				Objects.equals(pairs, node.pairs);
		}

		@Override
		public int hashCode() {
			return Objects.hash(level, pairs);
		}
	}
}
