package io.github.carlosdiamon.shadowcoord.component.formatter;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

public interface Formatter {

	@NotNull MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

	@NotNull Component apply(
		final @NotNull Audience audience,
		final @NotNull String message,
		final @NotNull TagResolver... resolvers
	);

	@NotNull Component apply(
		final @NotNull Audience audience,
		final @NotNull String message
	);

	@NotNull Component apply(final @NotNull String message);

}
