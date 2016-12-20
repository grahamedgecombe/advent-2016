package com.grahamedgecombe.advent2016;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Day20 {
	public static void main(String[] args) throws IOException {
		Blacklist blacklist = Blacklist.parse(AdventUtils.readLines("day20.txt"));
		System.out.println(blacklist.getFirstUnblockedIp());
		System.out.println(blacklist.getAllowedIps(0, 0xFFFFFFFFL));
	}

	public static final class Blacklist {
		public static Blacklist parse(List<String> lines) {
			Blacklist blacklist = new Blacklist();

			for (String line : lines) {
				String[] parts = line.split("-");
				if (parts.length != 2) {
					throw new IllegalArgumentException();
				}

				long start = Long.parseLong(parts[0]);
				long end = Long.parseLong(parts[1]);

				blacklist.add(new Range(start, end));
			}

			return blacklist;
		}

		private Blacklist() {
			/* empty */
		}

		private final List<Range> ranges = new ArrayList<>();

		private void add(Range range) {
			int index = Collections.binarySearch(ranges, range);
			if (index >= 0) {
				/* duplicate range, ignore */
				return;
			}

			/* convert to insertion point (see binarySearch Javadoc) */
			index = -(index + 1);
			ranges.add(index, range);

			boolean merging;
			do {
				merging = false;

				int rightIndex = index + 1;
				if (rightIndex < ranges.size()) {
					Range right = ranges.get(rightIndex);
					if (range.intersects(right)) {
						range = range.merge(right);

						ranges.set(index, range);
						ranges.remove(rightIndex);

						merging = true;
					}
				}

				int leftIndex = index - 1;
				if (leftIndex >= 0) {
					Range left = ranges.get(leftIndex);
					if (range.intersects(left)) {
						range = range.merge(left);

						ranges.set(index, range);
						ranges.remove(leftIndex);

						/* removing leftIndex shifts us to the left too */
						index--;

						merging = true;
					}
				}
			} while (merging);
		}

		public long getFirstUnblockedIp() {
			return ranges.get(0).end + 1;
		}

		public long getAllowedIps(long min, long max) {
			long allowed = 0;

			Range first = ranges.get(0);
			allowed += first.start - min;

			for (int i = 0; i < ranges.size() - 1; i++) {
				Range a = ranges.get(i);
				Range b = ranges.get(i + 1);

				allowed += b.start - (a.end + 1);
			}

			Range last = ranges.get(ranges.size() - 1);
			allowed += max - last.end;

			return allowed;
		}
	}

	private static final class Range implements Comparable<Range> {
		private final long start, end;

		private Range(long start, long end) {
			this.start = start;
			this.end = end;
		}

		private boolean intersects(Range o) {
			return start <= (o.end + 1) && o.start <= (end + 1);
		}

		private Range merge(Range o) {
			return new Range(Math.min(start, o.start), Math.max(end, o.end));
		}

		@Override
		public int compareTo(Range o) {
			if (start > o.start) {
				return 1;
			} else if (start < o.start) {
				return -1;
			} else if (end > o.end) {
				return 1;
			} else if (end < o.end) {
				return -1;
			} else {
				return 0;
			}
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Range)) return false;
			Range range = (Range) o;
			return start == range.start &&
				end == range.end;
		}

		@Override
		public int hashCode() {
			return Objects.hash(start, end);
		}

		@Override
		public String toString() {
			return "[" + start + ", " + end + "]";
		}
	}

	private Day20() {
		/* empty */
	}
}
