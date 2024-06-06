package io.github.carlosdiamon.shadowcoord;

import com.github.retrooper.packetevents.PacketEvents;
import io.github.carlosdiamon.shadowcoord.command.OffsetCommand;
import io.github.carlosdiamon.shadowcoord.component.MessageHandler;
import io.github.carlosdiamon.shadowcoord.component.MessageHandlerImpl;
import io.github.carlosdiamon.shadowcoord.component.formatter.DefaultFormatter;
import io.github.carlosdiamon.shadowcoord.component.formatter.Formatter;
import io.github.carlosdiamon.shadowcoord.component.formatter.MiniFormatter;
import io.github.carlosdiamon.shadowcoord.config.Configuration;
import io.github.carlosdiamon.shadowcoord.config.ConfigurationContainer;
import io.github.carlosdiamon.shadowcoord.config.FileConfiguration;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateManager;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateService;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class Core
	extends JavaPlugin {

	private CoordinateManager coordinateManager;
	private ConfigurationContainer<Configuration> configurationContainer;
	private MessageHandler messageHandler;
	private FileConfiguration fileConfiguration;
	private final Logger logger = getSLF4JLogger();

	@Override
	public void onLoad() {
		PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
		PacketEvents.getAPI()
			.load();
	}

	@Override
	public void onEnable() {
		this.coordinateManager = new CoordinateManager(logger);

		this.configurationContainer = ConfigurationContainer.load(
			logger,
			getDataFolder().toPath(),
			Configuration.class,
			"config.conf"
		);

		this.fileConfiguration = FileConfiguration.load(
			logger,
			getDataFolder().toPath(),
			Core.class.getResource("/lang_es.conf"),
			"lang_es.conf"
		);

		Formatter formatter = null;
		if (getServer().getPluginManager()
			    .isPluginEnabled("miniplaceholders")) {
			formatter = new MiniFormatter();
		} else {
			formatter = new DefaultFormatter();
		}

		this.messageHandler = new MessageHandlerImpl(formatter, fileConfiguration);

		final var coordinateService = new CoordinateService(
			coordinateManager,
			configurationContainer,
			messageHandler);

		Bukkit.getPluginManager()
			.registerEvents(new BukkitEventListener(coordinateService, configurationContainer),
			                this);
		getServer().getCommandMap()
			.register("shadowcoord", new OffsetCommand(this, messageHandler, coordinateService));
		new PacketAdapter(coordinateManager, logger).init();
		getComponentLogger().info(deserializeMessage("<#e76005>Plugin enabled."));
	}

	@Override
	public void onDisable() {
		coordinateManager.clearOffsets();
		PacketEvents.getAPI()
			.terminate();
		getComponentLogger().info(deserializeMessage("<red>Plugin disabled."));
	}

	public void reloadConfiguration() {
		// later...
	}

	private @NotNull Component deserializeMessage(final @NotNull String message) {
		return MiniMessage.miniMessage()
			       .deserialize("<dark_red>[<#3d3b3b>ShadowCoord<dark_red>] " + message);
	}

	public CoordinateManager getCoordinateManager() {
		return coordinateManager;
	}

	public ConfigurationContainer<Configuration> getConfigurationContainer() {
		return configurationContainer;
	}

	public MessageHandler getMessageHandler() {
		return messageHandler;
	}

	public FileConfiguration getFileConfiguration() {
		return fileConfiguration;
	}

	public static Plugin instance() { // lazy xdxdxdx
		return getPlugin(Core.class);
	}

}
