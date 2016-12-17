package com.grahamedgecombe.advent2016;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

public final class Bfs {
	public static abstract class Node<T extends Node<T>> {
		public abstract boolean isGoal();
		public abstract Iterable<T> getNeighbours();
	}

	public static <T extends Node<T>> Optional<List<T>> search(T root) {
		Queue<T> queue = new ArrayDeque<>();
		Map<T, T> parents = new HashMap<>();

		queue.add(root);

		T current;
		while ((current = queue.poll()) != null) {
			if (current.isGoal()) {
				List<T> path = new ArrayList<>();

				do {
					path.add(current);
				} while ((current = parents.get(current)) != null);

				Collections.reverse(path);
				return Optional.of(path);
			}

			for (T neighbour : current.getNeighbours()) {
				if (parents.containsKey(neighbour)) {
					continue;
				}

				queue.add(neighbour);
				parents.put(neighbour, current);
			}
		}

		return Optional.empty();
	}

	public static <T extends Node<T>> List<List<T>> searchAll(T root) {
		List<List<T>> paths = new ArrayList<>();

		Queue<T> queue = new ArrayDeque<>();
		Map<T, T> parents = new HashMap<>();

		queue.add(root);

		T current;
		while ((current = queue.poll()) != null) {
			if (current.isGoal()) {
				List<T> path = new ArrayList<>();

				do {
					path.add(current);
				} while ((current = parents.get(current)) != null);

				Collections.reverse(path);
				paths.add(path);
				continue;
			}

			for (T neighbour : current.getNeighbours()) {
				if (parents.containsKey(neighbour)) {
					continue;
				}

				queue.add(neighbour);
				parents.put(neighbour, current);
			}
		}

		return paths;
	}

	private Bfs() {
		/* empty */
	}
}
