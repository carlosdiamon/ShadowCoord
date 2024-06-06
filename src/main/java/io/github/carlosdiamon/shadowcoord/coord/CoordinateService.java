package io.github.carlosdiamon.shadowcoord.coord;

import io.github.carlosdiamon.shadowcoord.component.MessageHandler;
import io.github.carlosdiamon.shadowcoord.component.decoration.DecorationType;
import io.github.carlosdiamon.shadowcoord.config.Configuration;
import io.github.carlosdiamon.shadowcoord.config.ConfigurationContainer;
import io.github.carlosdiamon.shadowcoord.util.PacketUtil;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CoordinateService {

	private final CoordinateManager manager;
	private final ConfigurationContainer<Configuration> configurationContainer;
	private final MessageHandler messageHandler;

	public CoordinateService(
		final CoordinateManager manager,
		final ConfigurationContainer<Configuration> configurationContainer,
		final MessageHandler messageHandler
	) {
		this.manager = manager;
		this.configurationContainer = configurationContainer;
		this.messageHandler = messageHandler;
	}

	/**
	 * Adds offset to the player's coordinates, Ignores if it was already there before
	 *
	 * @param player
	 * 	Player to add offset to
	 *
	 * @return true if offset was added, false if it could not be added because of your permissions.
	 */
	public boolean putOffset(
		final @NotNull Player player,
		final boolean reload
	) {
		final var uniqueId = player.getUniqueId();
		final var config = configurationContainer.get();
		if (player.hasPermission("shadowcoord.bypass") && !config.isIgnorePermission()) {
			return false;
		}

		manager.putOffset(uniqueId);
		if (reload) {
			PacketUtil.updateChunks(player);
		}
		return true;
	}

	/**
	 * Removes offset from the player's coordinates.
	 *
	 * @param sender
	 * 	responsible
	 * @param target
	 * 	Player to add offset to
	 */
	public void putOffset(
		final @NotNull CommandSender sender,
		final @NotNull Player target
	) {
		if (manager.getOffset(target.getUniqueId()) != null) {
			messageHandler.send(
				sender,
				"command.error.already-offset",
				Placeholder.component("target", target.displayName()),
				DecorationType.ERROR
			);
		}

		if (putOffset(target, true)) {
			messageHandler.send(
				sender,
				"offset.apply-message",
				Placeholder.component("target", target.displayName()),
				DecorationType.SUCCESS
			);
		} else {
			messageHandler.send(sender, "command.error.invalid-player", DecorationType.ERROR);
		}
	}

	/**
	 * Removes offset from the player's coordinates.
	 * @param player Player to remove offset from
	 * @return true if offset was removed, false if it could not be removed because it was not there before.
	 */
	public boolean removeOffset(
		final @NotNull Player player,
		final boolean reload
	) {
		final var uniqueId = player.getUniqueId();
		if (manager.getOffset(uniqueId) == null) {
			return false;
		}
		manager.removeOffset(player.getUniqueId());
		if (reload) {
			PacketUtil.updateChunks(player);
		}
		return true;
	}

	/**
	 * Removes offset from the player's coordinates.
	 * @param sender responsible
	 * @param target Player to remove offset from
	 */
	public void removeOffset(
		final @NotNull CommandSender sender,
		final @NotNull Player target
	) {
		if (removeOffset(target, true)) {
			messageHandler.send(
				sender,
				"offset.remove-offset",
				Placeholder.component("target", target.displayName()),
				DecorationType.SUCCESS
			);
		} else {
			messageHandler.send(
				sender,
				"command.error.not-offset",
				Placeholder.component("target", target.displayName()),
				DecorationType.ERROR
			);
		}
	}

	public boolean isOffset(final @NotNull UUID uniqueId) {
		return manager.getOffset(uniqueId) != null;
	}

}
