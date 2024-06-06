package io.github.carlosdiamon.shadowcoord.component;

import io.github.carlosdiamon.shadowcoord.component.decoration.DecorationType;
import io.github.carlosdiamon.shadowcoord.component.formatter.Formatter;
import io.github.carlosdiamon.shadowcoord.config.FileConfiguration;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

public class MessageHandlerImpl implements MessageHandler {

	private final Formatter formatter;
	private final FileConfiguration fileConfiguration;

	public MessageHandlerImpl(
		final Formatter formatter,
		final FileConfiguration fileConfiguration
	) {
		this.formatter = formatter;
		this.fileConfiguration = fileConfiguration;
	}

	@Override
	public void send(
		final @NotNull Audience audience,
		final @NotNull String key,
		final @NotNull TagResolver resolver,
		final @NotNull DecorationType type
	) {
		final var msg = getMessage(key);
		final var message = formatter.apply(audience, msg, resolver);
		audience.sendMessage(message);
		audience.playSound(type.sound());
	}

	@Override
	public void send(
		final @NotNull Audience audience,
		final @NotNull String key,
		final @NotNull DecorationType type
	) {
		send(audience, key, TagResolver.empty(), type);
	}

	@Override
	public @NotNull String getMessage(final @NotNull String key) {
		return fileConfiguration.getMessage(key);
	}
}
