package io.github.carlosdiamon.shadowcoord.offsetter;

import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketReceiveOffsetter;
import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketSendOffsetter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class OffsetterManager {

	private final Map<PacketTypeCommon, PacketSendOffsetter> packetSendOffsetters;
	private final Map<PacketTypeCommon, PacketReceiveOffsetter> packetReceiveOffsetters;
	private final Logger logger;

	public OffsetterManager(final Logger logger) {
		this.packetSendOffsetters = new HashMap<>();
		this.packetReceiveOffsetters = new HashMap<>();
		this.logger = logger;
	}

	public void registerSendOffsetter(final @NotNull PacketSendOffsetter packet) {
		for (final var packetType : packet.getPacketTypes()) {
			if (packetSendOffsetters.containsKey(packetType)) {
				logger.warn("[OffsetterManager - Send] Packet already registered for packet type: {}", packetType);
				continue;
			}
			packetSendOffsetters.put(packetType, packet);
			logger.info("[OffsetterManager - Send] Registered packet for packet type: {}", packetType);
		}
	}

	public void registerReceiveOffsetter(final @NotNull PacketReceiveOffsetter packet) {
		for (final var packetType : packet.getPacketTypes()) {
			if (packetReceiveOffsetters.containsKey(packetType)) {
				logger.warn("[OffsetterManager - Receive] Packet already registered for packet type: {}", packetType);
				continue;
			}
			packetReceiveOffsetters.put(packetType, packet);
			logger.info("[OffsetterManager - Receive] Registered packet for packet type: {}", packetType);
		}
	}

	public @Nullable PacketSendOffsetter getSendOffsetter(final @NotNull PacketTypeCommon packetType) {
		return packetSendOffsetters.get(packetType);
	}

	public @Nullable PacketReceiveOffsetter getReceiveOffsetter(final @NotNull PacketTypeCommon packetType) {
		return packetReceiveOffsetters.get(packetType);
	}

	public void unregisterSendOffsetter(final @NotNull PacketSendOffsetter packet) {
		for (final var packetType : packet.getPacketTypes()) {
			if (!packetSendOffsetters.containsKey(packetType)) {
				logger.warn("[OffsetterManager - Send] Packet not registered for packet type: {}", packetType);
				continue;
			}
			packetSendOffsetters.remove(packetType);
			logger.info("[OffsetterManager - Send] Unregistered packet for packet type: {}", packetType);
		}
	}

	public void unregisterReceiveOffsetter(final @NotNull PacketReceiveOffsetter packet) {
		for (final var packetType : packet.getPacketTypes()) {
			if (!packetReceiveOffsetters.containsKey(packetType)) {
				logger.warn("[OffsetterManager - Receive] Packet not registered for packet type: {}", packetType);
				continue;
			}
			packetReceiveOffsetters.remove(packetType);
			logger.info("[OffsetterManager - Receive] Unregistered packet for packet type: {}", packetType);
		}
	}
}
