package io.github.carlosdiamon.shadowcoord.config;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;

/**
 * Class in charge of representing a configuration file. Thanks to Adrian for the inspiration. <a
 * href="https://github.com/4drian3d/KickRedirect/blob/main/src/main/java/io/github/_4drian3d
 * /kickredirect/configuration/ConfigurationContainer.java">github</a>
 */
public final class ConfigurationContainer<C> {

	private static final String FILE_EXTENSION = ".conf";
	private final AtomicReference<C> configuration;
	private final HoconConfigurationLoader loader;
	private final Class<C> clazz;
	private final Logger logger;

	private ConfigurationContainer(
		final C configuration,
		final HoconConfigurationLoader loader,
		final Class<C> clazz,
		final Logger logger
	) {
		this.configuration = new AtomicReference<>(configuration);
		this.loader = loader;
		this.clazz = clazz;
		this.logger = logger;
	}

	public @NotNull CompletableFuture<Boolean> reload() {
		return CompletableFuture.supplyAsync(() -> {
			try {
				final var node = loader.load();
				final var newConfig = node.get(clazz);
				node.set(clazz, newConfig);
				loader.save(node);
				configuration.set(newConfig);
				return true;
			} catch (final ConfigurateException exception) {
				logger.error("[ConfigurationContainer] Could not reload {} configuration file.", clazz.getSimpleName(),
				             exception);
				return false;
			}
		});
	}

	public @NotNull C get() {
		return configuration.get();
	}

	public static <C> ConfigurationContainer<C> load(
		final @NotNull Logger logger,
		final @NotNull Path path,
		final @NotNull Class<C> clazz,
		final @NotNull String file,
		final @NotNull UnaryOperator<ConfigurationOptions> options
	) {
		final var name = file.endsWith(FILE_EXTENSION) ?
		                 file :
		                 file + FILE_EXTENSION;

		final HoconConfigurationLoader loader = HoconConfigurationLoader.builder()
			                                        .defaultOptions(options)
			                                        .path(path.resolve(name))
			                                        .build();

		try {
			final var node = loader.load();
			final var config = node.get(clazz);
			node.set(clazz, config);
			loader.save(node);
			return new ConfigurationContainer<>(config, loader, clazz, logger);
		} catch (final ConfigurateException exception) {
			logger.error("[ConfigurationContainer] Could not load {} configuration file.", clazz.getSimpleName(), exception);
			return null;
		}
	}

	public static <C> ConfigurationContainer<C> load(
		final @NotNull Logger logger,
		final @NotNull Path path,
		final @NotNull Class<C> clazz,
		final @NotNull String file
	) {
		return load(
			logger,
			path,
			clazz,
			file,
			opts -> opts.shouldCopyDefaults(true)
				        .header("ShadowCoord | CarlosDiamon")
		);
	}
}
