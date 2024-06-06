package io.github.carlosdiamon.shadowcoord;

import io.github.carlosdiamon.shadowcoord.config.Configuration;
import io.github.carlosdiamon.shadowcoord.config.ConfigurationContainer;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateService;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BukkitEventListener
	implements Listener {

	private final CoordinateService service;
	private final ConfigurationContainer<Configuration> configurationContainer;

	public BukkitEventListener(
		final CoordinateService service,
		final ConfigurationContainer<Configuration> configurationContainer
	) {
		this.service = service;
		this.configurationContainer = configurationContainer;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onSpawnLocation(final PlayerJoinEvent event) {
		final var player = event.getPlayer();
		if (!isCandidate(player.getGameMode())) {
			return;
		}

		service.putOffset(player, false);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onRespawnLocation(final PlayerRespawnEvent event) {
		final var player = event.getPlayer();
		if (!service.isOffset(player.getUniqueId())) {
			return;
		}

		service.putOffset(player, false);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerTeleport(final PlayerTeleportEvent event) {
		final var player = event.getPlayer();
		if (!service.isOffset(player.getUniqueId())) {
			return;
		}

		final var from = event.getFrom();
		final var to = event.getTo();

		if (Objects.equals(from.getWorld(), to.getWorld()) ||
		    from.distanceSquared(to) > getMinTeleportDistance(to.getWorld())) {
			service.putOffset(player, false);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(final PlayerQuitEvent event) {
		service.removeOffset(event.getPlayer(), false);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void changeGameMode(final PlayerGameModeChangeEvent event) {
		final var player = event.getPlayer();

		if (!service.isOffset(player.getUniqueId())) {
			return;
		}

		if (isCandidate(event.getNewGameMode())) {
			service.putOffset(player, true);
		} else {
			service.removeOffset(player, true);
		}
	}

	public boolean isCandidate(final @NotNull GameMode mode) {
		final var configuration = configurationContainer.get();
		final var modes = configuration.getGameModes();

		return modes.contains(mode);
	}

	private int getMinTeleportDistance(final @NotNull World world) {
		final var viewDistance = world.getViewDistance();
		final var minBlocks = ((viewDistance + 1) * 2) * 16;
		return minBlocks * minBlocks;
	}

}
