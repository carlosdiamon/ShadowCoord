package io.github.carlosdiamon.shadowcoord.command;

import io.github.carlosdiamon.shadowcoord.Core;
import io.github.carlosdiamon.shadowcoord.component.MessageHandler;
import io.github.carlosdiamon.shadowcoord.component.decoration.DecorationType;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class OffsetCommand extends Command implements PluginIdentifiableCommand {
	private static final List<String> SUGGESTIONS = List.of("reload", "apply", "remove");
	private final CoordinateService service;
	private final MessageHandler messageHandler;
	private final Core plugin;

	public OffsetCommand(
		final @NotNull Core plugin,
		final @NotNull MessageHandler messageHandler,
		final @NotNull CoordinateService service
	) {
		super("offset", "", "", List.of("shc", "ofc"));
		setPermission("shadowcoord.offset");
		this.plugin = plugin;
		this.messageHandler = messageHandler;
		this.service = service;

		setUsage(messageHandler.getMessage("command.usage"));
		setDescription(messageHandler.getMessage("command.description"));
	}

	@Override
	public @NotNull List<String> tabComplete(
		@NotNull final CommandSender sender,
		@NotNull final String alias,
		final @NotNull String[] args
	) throws IllegalArgumentException {
		if (args.length == 1) {
			return SUGGESTIONS;
		}

		return Collections.emptyList();
	}

	@Override
	public boolean execute(
		final @NotNull CommandSender sender,
		final @NotNull String commandLabel,
		final @NotNull String[] args
	) {
		if (args.length == 0) {
			messageHandler.send(sender, "command.usage", DecorationType.INFO);
			return true;
		}

		Player target = null;

		if (args.length == 2) {
			target = plugin.getServer().getPlayerExact(args[1]);
		} else {
			if (sender instanceof Player) {
				target = (Player) sender;
			}
		}

		if (target == null) {
			messageHandler.send(sender, "command.error.invalid-args", DecorationType.ERROR);
			return false;
		}

		switch (args[0].toLowerCase(Locale.ROOT)) {
			case "reload" -> {
				plugin.reloadConfiguration();
				return true;
			}
			case "apply" -> {
				service.putOffset(sender, target);
				return true;
			}
			case "remove" -> {
				service.removeOffset(sender, target);
				return true;
			}
			default -> {
				messageHandler.send(sender, "command.error.invalid-args", DecorationType.ERROR);
				return false;
			}
		}
	}


	@Override
	public @NotNull Plugin getPlugin() {
		return plugin;
	}
}
