package io.github.carlosdiamon.shadowcoord.component.formatter;

import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

public class MiniFormatter
	implements Formatter {
	@Override
	public @NotNull Component apply(
		final @NotNull Audience audience,
		final @NotNull String message,
		final @NotNull TagResolver... resolvers
	) {
		return MINI_MESSAGE.deserialize(
			message,
			TagResolver.resolver(resolvers),
			MiniPlaceholders.getAudienceGlobalPlaceholders(audience)
		);
	}

	@Override
	public @NotNull Component apply(
		final @NotNull Audience audience,
		final @NotNull String message
	) {
		return MINI_MESSAGE.deserialize(
			message,
			MiniPlaceholders.getAudienceGlobalPlaceholders(audience)
		);
	}

	@Override
	public @NotNull Component apply(final @NotNull String message) {
		return MINI_MESSAGE.deserialize(message, MiniPlaceholders.getGlobalPlaceholders());
	}
}
