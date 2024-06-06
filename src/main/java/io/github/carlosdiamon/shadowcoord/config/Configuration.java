package io.github.carlosdiamon.shadowcoord.config;

import org.bukkit.GameMode;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.List;

@ConfigSerializable
@SuppressWarnings("FieldMayBeFinal")
public class Configuration {
	private List<GameMode> gameModes = List.of(GameMode.SURVIVAL, GameMode.ADVENTURE, GameMode.SPECTATOR);
	private boolean ignorePermission = false;
	private boolean debugMode = false;

	public @NotNull List<GameMode> getGameModes() {
		return gameModes;
	}

	public boolean isIgnorePermission() {
		return ignorePermission;
	}
	public boolean isDebugMode() {
		return debugMode;
	}
}
