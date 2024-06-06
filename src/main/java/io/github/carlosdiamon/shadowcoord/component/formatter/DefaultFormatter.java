package io.github.carlosdiamon.shadowcoord.component.formatter;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

public class DefaultFormatter
	implements Formatter {

	@Override
	public @NotNull Component apply(
		final @NotNull Audience audience,
		final @NotNull String message,
		final @NotNull TagResolver... resolvers
	) {
		return MINI_MESSAGE.deserialize(message, resolvers);
	}

	@Override
	public @NotNull Component apply(
		final @NotNull Audience audience,
		final @NotNull String message
	) {
		return apply(message);
	}

	@Override
	public @NotNull Component apply(final @NotNull String message) {
		return MINI_MESSAGE.deserialize(message);
	}
}
