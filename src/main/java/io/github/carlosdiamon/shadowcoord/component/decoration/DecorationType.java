package io.github.carlosdiamon.shadowcoord.component.decoration;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.jetbrains.annotations.NotNull;

public enum DecorationType {
	SUCCESS(Key.key("minecraft", "block.note_block.chime")),
	INFO(Key.key("minecraft", "block.note_block.bit")),
	WARNING(Key.key("minecraft", "block.note_block.pling")),
	ERROR(Key.key("minecraft", "block.note_block.bass"));

	private final Key key;
	DecorationType(final Key key) {
		this.key = key;
	}

	public @NotNull Key key() {
		return this.key;
	}

	public @NotNull Sound sound() {
		return Sound.sound(key, Sound.Source.AMBIENT, 1f, 1f);
	}

}