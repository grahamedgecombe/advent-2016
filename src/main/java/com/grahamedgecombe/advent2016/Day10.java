package com.grahamedgecombe.advent2016;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;

public final class Day10 {
	private static final Pattern VALUE_PATTERN = Pattern.compile("^value (\\d+) goes to bot (\\d+)$");
	private static final Pattern LOGIC_PATTERN = Pattern.compile("^bot (\\d+) gives low to (bot|output) (\\d+) and high to (bot|output) (\\d+)$");

	public static void main(String[] args) throws IOException {
		Factory factory = Factory.create(AdventUtils.readLines("day10.txt"));
		System.out.println(factory.getBotComparing(61, 17));
		System.out.println(factory.multiplyOutputs(0, 1, 2));
	}

	public static final class Factory {
		public static Factory create(List<String> instructions) {
			Factory factory = new Factory();

			for (String instruction : instructions) {
				Matcher matcher = LOGIC_PATTERN.matcher(instruction);
				if (!matcher.matches()) {
					continue;
				}

				int sourceId = Integer.parseInt(matcher.group(1));
				Bot source = factory.bots.computeIfAbsent(sourceId, Bot::new);

				String lowDestType = matcher.group(2);
				int lowDestId = Integer.parseInt(matcher.group(3));

				ValueSink lowDest;
				if (lowDestType.equals("bot")) {
					lowDest = factory.bots.computeIfAbsent(lowDestId, Bot::new);
				} else { /* "output" */
					lowDest = factory.outputs.computeIfAbsent(lowDestId, Output::new);
				}

				String highDestType = matcher.group(4);
				int highDestId = Integer.parseInt(matcher.group(5));

				ValueSink highDest;
				if (highDestType.equals("bot")) {
					highDest = factory.bots.computeIfAbsent(highDestId, Bot::new);
				} else { /* "output" */
					highDest = factory.outputs.computeIfAbsent(highDestId, Output::new);
				}

				source.lowSink = lowDest;
				source.highSink = highDest;
			}

			for (String instruction : instructions) {
				Matcher matcher = VALUE_PATTERN.matcher(instruction);
				if (!matcher.matches()) {
					continue;
				}

				int value = Integer.parseInt(matcher.group(1));
				int botId = Integer.parseInt(matcher.group(2));

				Bot bot = factory.bots.get(botId);
				bot.receive(value);
			}

			return factory;
		}

		private final Map<Integer, Bot> bots = new HashMap<>();
		private final Map<Integer, Output> outputs = new HashMap<>();

		private Factory() {
			/* empty */
		}

		public int getBotComparing(int v0, int v1) {
			for (Bot bot : bots.values()) {
				if (bot.values.contains(v0) && bot.values.contains(v1)) {
					return bot.id;
				}
			}

			throw new IllegalArgumentException();
		}

		public int multiplyOutputs(int... outputIds) {
			return Arrays.stream(outputIds)
				.mapToObj(outputs::get)
				.mapToInt(Output::getValue)
				.reduce(1, (x, y) -> x * y);
		}
	}

	private interface ValueSink {
		void receive(int value);
	}

	private static final class Bot implements ValueSink {
		private final int id;
		private List<Integer> values = new ArrayList<>();
		private ValueSink lowSink, highSink;

		public Bot(int id) {
			this.id = id;
		}

		@Override
		public void receive(int value) {
			if (values.size() == 0) {
				values.add(value);
			} else if (values.size() == 1) {
				values.add(value);

				int v0 = values.get(0);

				if (v0 > value) {
					highSink.receive(v0);
					lowSink.receive(value);
				} else {
					lowSink.receive(v0);
					highSink.receive(value);
				}
			} else {
				throw new IllegalStateException();
			}
		}
	}

	private static final class Output implements ValueSink {
		private final int id;
		private boolean filled;
		private int value;

		public Output(int id) {
			this.id = id;
		}

		@Override
		public void receive(int value) {
			Preconditions.checkState(!filled);
			this.filled = true;
			this.value = value;
		}

		public int getValue() {
			Preconditions.checkState(filled);
			return value;
		}
	}

	private Day10() {
		/* empty */
	}
}
