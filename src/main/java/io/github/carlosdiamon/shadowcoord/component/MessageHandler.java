package io.github.carlosdiamon.shadowcoord.component;

import io.github.carlosdiamon.shadowcoord.component.decoration.DecorationType;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

public interface MessageHandler {

	void send(
		final @NotNull Audience audience,
		final @NotNull String key,
		final @NotNull TagResolver resolver,
		final @NotNull DecorationType type
	);

	void send(
		final @NotNull Audience audience,
		final @NotNull String key,
		final @NotNull DecorationType type
	);

	@NotNull String getMessage(final @NotNull String key);
}
