package io.github.carlosdiamon.shadowcoord.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.net.URL;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

public class FileConfiguration {

	private static final String FILE_EXTENSION = ".conf";
	private final Path path;
	private final String fileName;
	private final CommentedConfigurationNode node;
	private final Logger logger;

	private FileConfiguration(
		final Path path,
		final String fileName,
		final CommentedConfigurationNode node,
		final Logger logger
	) {
		this.path = path;
		this.fileName = fileName;
		this.node = node;
		this.logger = logger;
	}

	public @NotNull String getMessage(final @NotNull String key) {
		final Object[] split = key.split("\\.");
		return node.node(split)
			       .getString(key);
	}

	public @NotNull List<String> getMessages(final @NotNull String key) {
		final Object[] split = key.split("\\.");
		try {
			final var list = node.node(split)
				                 .getList(String.class);

			if (list != null) {
				return list;
			}

		} catch (final SerializationException e) {
			logger.error(
				"[FileConfiguration] An error occurred when getting the message using {}: ",
				key,
				e
			);
		}
		return Collections.emptyList();
	}

	public static FileConfiguration load(
		final @NotNull Logger logger,
		final @NotNull Path path,
		final @Nullable URL url,
		final @NotNull String file,
		final @NotNull UnaryOperator<ConfigurationOptions> options
	) {
		final var name = file.endsWith(FILE_EXTENSION) ?
		                 file :
		                 file + FILE_EXTENSION;
		try {
			final var loader = HoconConfigurationLoader.builder()
				                   .path(path.resolve(name))
				                   .defaultOptions(options)
				                   .url(url)
				                   .build();

			final var node = loader.load();
			loader.save(node);
			return new FileConfiguration(path, name, node, logger);
		} catch (final ConfigurateException e) {
			logger.error("[FileConfiguration] Error loading config file {}", name, e);
			return null;
		}
	}

	public static FileConfiguration load(
		final @NotNull Logger logger,
		final @NotNull Path path,
		final @Nullable URL url,
		final @NotNull String file
	) {
		return load(
			logger,
			path,
			url,
			file,
			opts -> opts.shouldCopyDefaults(true)
		);
	}
}
