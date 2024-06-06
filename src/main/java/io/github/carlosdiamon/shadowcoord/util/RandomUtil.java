package io.github.carlosdiamon.shadowcoord.util;

import java.util.Random;

public final class RandomUtil {

	private static final Random RANDOM;

	private RandomUtil() {
		throw new UnsupportedOperationException("This class cannot be instantiated.");
	}

	static {
		RANDOM = new Random();
	}

	public static int nextInt(final int bound) {
		return RANDOM.nextInt(bound);
	}

	public static int nextInt(final int min, final int max) {
		return RANDOM.nextInt(min, max);
	}
}
