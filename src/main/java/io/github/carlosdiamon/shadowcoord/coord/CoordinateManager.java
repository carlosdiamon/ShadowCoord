package io.github.carlosdiamon.shadowcoord.coord;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CoordinateManager {
	private static final int MAX_OFFSET = 100_000;
	private final Map<UUID, CoordinateOffset> playerOffsets;
	private final Logger logger;

	public CoordinateManager(final Logger logger) {
		this.playerOffsets = Collections.synchronizedMap(new HashMap<>());
		this.logger = logger;
	}

	public void putOffset(final @NotNull UUID uniqueId) {
		final var offset = CoordinateOffset.random(MAX_OFFSET);
		logger.info("[CoordianteManager] Adding user = {}, offset = {}.", uniqueId, offset);
		playerOffsets.put(uniqueId, offset);
	}

	public @Nullable CoordinateOffset getOffset(final @NotNull UUID uniqueId) {
		return playerOffsets.get(uniqueId);
	}

	public void removeOffset(final @NotNull UUID uniqueId) {
		logger.info("[CoordianteManager] Removing user = {}.", uniqueId);
		playerOffsets.remove(uniqueId);
	}

	public void clearOffsets() {
		playerOffsets.clear();
	}
}
