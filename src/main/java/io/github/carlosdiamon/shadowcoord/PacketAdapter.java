package io.github.carlosdiamon.shadowcoord;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateManager;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;
import io.github.carlosdiamon.shadowcoord.offsetter.OffsetterManager;
import io.github.carlosdiamon.shadowcoord.offsetter.client.OffsetterClientClickWindow;
import io.github.carlosdiamon.shadowcoord.offsetter.client.OffsetterClientCreativeInventoryAction;
import io.github.carlosdiamon.shadowcoord.offsetter.client.OffsetterClientGenerateStructure;
import io.github.carlosdiamon.shadowcoord.offsetter.server.*;
import io.github.carlosdiamon.shadowcoord.offsetter.client.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;

public final class PacketAdapter {

	private final CoordinateManager coordinateManager;
	private final OffsetterManager offsetterManager;

	private final Logger logger;

	public PacketAdapter(
		final @NotNull CoordinateManager coordinateManager,
		final @NotNull Logger logger
	) {
		this.logger = logger;
		this.coordinateManager = coordinateManager;
		this.offsetterManager = new OffsetterManager(logger);
	}

	public void init() {
		final var sendOffsetters = List.of(
			new OffsetterServerAcknowledgePlayerDigging(),
			new OffsetterServerBlockAction(),
			new OffsetterServerBlockBreakAnimation(),
			new OffsetterServerBlockChange(),
			new OffsetterServerBlockEntityData(),
			new OffsetterServerChunkData(),
			new OffsetterServerEffect(),
			new OffsetterServerEntityEquipment(),
			new OffsetterServerEntityMetadata(),
			new OffsetterServerEntityTeleport(),
			new OffsetterServerExplosion(),
			new OffsetterServerFacePlayer(),
			new OffsetterServerJoinGame(),
			new OffsetterServerUpdateLight(),
			new OffsetterServerMultiBlockChange(),
			new OffsetterServerNamedSoundEffect(),
			new OffsetterServerOpenSignEditor(),
			new OffsetterServerParticle(),
			new OffsetterServerPlayerPositionAndLook(),
			new OffsetterServerRespawn(),
			new OffsetterServerSculkVibrationSignal(),
			new OffsetterServerSetSlot(),
			new OffsetterServerSoundEffect(),
			new OffsetterServerSpawnEntity(),
			new OffsetterServerSpawnExperienceOrb(),
			new OffsetterServerSpawnLivingEntity(),
			new OffsetterServerSpawnPainting(),
			new OffsetterServerSpawnPlayer(),
			new OffsetterServerSpawnPosition(),
			new OffsetterServerUnloadChunk(),
			new OffsetterServerUpdateViewPosition(),
			new OffsetterServerVehicleMove(),
			new OffsetterServerWindowItems()
		);

		final var receiveOffsetters = List.of(
			new OffsetterClientClickWindow(),
			new OffsetterClientCreativeInventoryAction(),
			new OffsetterClientGenerateStructure(),
			new OffsetterClientPlayerBlockPlacement(),
			new OffsetterClientPlayerDigging(),
			new OffsetterClientPlayerPosition(),
			new OffsetterClientUpdateCommandBlock(),
			new OffsetterClientUpdateJigsawBlock(),
			new OffsetterClientUpdateSign(),
			new OffsetterClientVehicleMove()
		);

		sendOffsetters.forEach(offsetterManager::registerSendOffsetter);
		receiveOffsetters.forEach(offsetterManager::registerReceiveOffsetter);

		PacketEvents.getAPI()
			.getEventManager()
			.registerListener(new Listener());
		PacketEvents.getAPI()
			.init();
	}

	private final class Listener
		extends PacketListenerAbstract {

		public Listener() {
			super(PacketListenerPriority.HIGH);
		}

		@Override
		public void onPacketSend(final PacketSendEvent event) {
			final var serverPacket = offsetterManager.getSendOffsetter(event.getPacketType());

			if (serverPacket == null) {
				return;
			}

			final var offset = getCoordinateOffset((Player) event.getPlayer());

			if (offset == null) {
				logger.debug("[Send] Failed to get coordinate offset for player: {}",
				             event.getUser()
					             .getName());
				return;
			}

			serverPacket.offset(event, offset);
		}

		@Override
		public void onPacketReceive(final PacketReceiveEvent event) {
			final var clientPacket = offsetterManager.getReceiveOffsetter(event.getPacketType());

			if (clientPacket == null) {
				return;
			}

			final var offset = getCoordinateOffset((Player) event.getPlayer());

			if (offset == null) {
				logger.debug("[Receive] Failed to get coordinate offset for player: {}",
				             event.getUser()
					             .getName());
				return;
			}

			clientPacket.offset(event, offset);
		}
	}

	private @Nullable CoordinateOffset getCoordinateOffset(final @Nullable Player player) {
		if (player == null) {
			return null;
		}

		return coordinateManager.getOffset(player.getUniqueId());
	}
}
