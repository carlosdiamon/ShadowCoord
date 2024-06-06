package io.github.carlosdiamon.shadowcoord.util;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.netty.channel.ChannelHelper;
import com.github.retrooper.packetevents.protocol.component.ComponentTypes;
import com.github.retrooper.packetevents.protocol.component.builtin.item.LodestoneTracker;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import com.github.retrooper.packetevents.protocol.nbt.NBTInt;
import com.github.retrooper.packetevents.protocol.world.WorldBlockPosition;
import com.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerMultiBlockChange;
import io.github.carlosdiamon.shadowcoord.Core;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class PacketUtil {

	private static final ConcurrentHashMap<BlockData, Integer> BLOCK_DATA_IDS =
		new ConcurrentHashMap<>();

	private PacketUtil() {
		throw new UnsupportedOperationException("This is a utility class and cannot be " +
		                                        "instantiated");
	}

	public static @NotNull ItemStack unapplyItemStack(
		ItemStack item,
		CoordinateOffset offset
	) {
		return applyItemStack(item, offset.negate());
	}

	public static ItemStack applyItemStack(ItemStack item, CoordinateOffset offset) {
		if (item == null) {
			return null;
		}

		if (item.getType() != ItemTypes.COMPASS) {
			return item;
		}

		// Up to 1.20.4 only: NBT tags
		final var nbt = item.getNBT();
		if (nbt != null) {
			final var lodestonePos = nbt.getCompoundTagOrNull("LodestonePos");
			if (lodestonePos != null) {
				lodestonePos.setTag(
					"X",
					new NBTInt(lodestonePos.getNumberTagOrThrow("X")
						           .getAsInt() - offset.coordX()));
				lodestonePos.setTag(
					"Z",
					new NBTInt(lodestonePos.getNumberTagOrThrow("Z")
						           .getAsInt() - offset.coordZ()));
			}
		}

		// 1.20.5+ only: Components
		Optional<?> lodestoneComponent = Optional.empty();
		try {
			lodestoneComponent = item.getComponents()
				                     .getPatches()
				                     .get(ComponentTypes.LODESTONE_TRACKER);
		} catch (NoSuchMethodError e) {
			// Ignore
		}

		if (lodestoneComponent != null
		    && lodestoneComponent.isPresent()
		    && lodestoneComponent.get() instanceof LodestoneTracker lodestone
		    && lodestone.getTarget() != null) {
			lodestone.setTarget(applyWorldBlock(lodestone.getTarget(), offset));
		}

		return item;
	}

	public static @NotNull WorldBlockPosition applyWorldBlock(
		WorldBlockPosition pos,
		CoordinateOffset offset
	) {
		// TODO: When available, this could respect the offset of the specific world instead of the
		//  Player's current one
		return new WorldBlockPosition(
			pos.getWorld(),
			pos.getBlockPosition().x - offset.coordX(),
			pos.getBlockPosition().y,
			pos.getBlockPosition().z - offset.coordZ());
	}

	public static void updateChunks(final @NotNull Player player) {
		final var world = player.getWorld();
		final var user = PacketEvents.getAPI()
			                 .getPlayerManager()
			                 .getUser(player);
		final var location = player.getLocation();
		final var viewDistance = 2; // test

		final var flat = PacketEvents.getAPI()
			                 .getServerManager()
			                 .getVersion()
			                 .isNewerThanOrEquals(ServerVersion.V_1_13);


		// This is based on Tuinity's code, thanks leaf. Now merged into paper.
		// I have no idea how I could possibly get this more efficient...
		final int minSection = world.getMinHeight() >> 4;

		final var minBlockX = location.getBlockX() - viewDistance;
		final var minBlockZ = location.getBlockZ() - viewDistance;
		final var maxBlockX = location.getBlockX() + viewDistance;
		final var maxBlockZ = location.getBlockZ() + viewDistance;
		final var minBlockY = minSection << 4;
		final var maxBlockY = world.getMaxHeight() - 1;


		Bukkit.getScheduler()
			.runTask(Core.instance(), () -> {

				final var minChunkX = minBlockX >> 4;
				final var maxChunkX = maxBlockX >> 4;
				final var minChunkZ = minBlockZ >> 4;
				final var maxChunkZ = maxBlockZ >> 4;
				final var minChunkY = minBlockY >> 4;
				final var maxChunkY = maxBlockY >> 4;

				for (int currChunkZ = minChunkZ; currChunkZ <= maxChunkZ; ++currChunkZ) {
					// coordinate of the chunk
					final var minZ = currChunkZ == minChunkZ ? minBlockZ & 15 : 0;
					final var maxZ = currChunkZ == maxChunkZ ? maxBlockZ & 15 : 15;

					for (int currChunkX = minChunkX; currChunkX <= maxChunkX; ++currChunkX) {
						final var minX = currChunkX == minChunkX ? minBlockX & 15 : 0;
						final var maxX = currChunkX == maxChunkX ? maxBlockX & 15 : 15;

						final var chunk = world.getChunkAt(currChunkX, currChunkZ);

						for (int currChunkY = minChunkY; currChunkY <= maxChunkY; ++currChunkY) {
							final var maxY = currChunkY == maxChunkY ? maxBlockY & 15 : 15;


							final var blocks = encodedBlocks(minX, 0, minZ, maxX, maxY, maxZ, chunk, currChunkY, flat);

							final var packet = new WrapperPlayServerMultiBlockChange(new Vector3i(currChunkX, currChunkY, currChunkZ), true, blocks);
							ChannelHelper.runInEventLoop(user.getChannel(), () -> user.sendPacket(packet));
						}
					}
				}
			});
	}

	private static WrapperPlayServerMultiBlockChange.EncodedBlock[] encodedBlocks(
		final int minX,
		final int minY,
		final int minZ,
		final int maxX,
		final int maxY,
		final int maxZ,
		final Chunk chunk,
		final int chunkY,
		final boolean flat
	) {
		final var totalBlocks = (maxX - minX + 1) * (maxZ - minZ + 1) * (maxY - minY + 1);
		final var encodedBlocks = new WrapperPlayServerMultiBlockChange.EncodedBlock[totalBlocks];

		var index = 0;

		for (int currZ = minZ; currZ <= maxZ; ++currZ) {
			for (int currX = minX; currX <= maxX; ++currX) {
				for (int currY = minY; currY <= maxY; ++currY) {
					final var block = chunk.getBlock(currX, currY | (chunkY << 4), currZ);

					int blockId;

					if (flat) {
						blockId = BLOCK_DATA_IDS.computeIfAbsent(block.getBlockData(), data -> WrappedBlockState.getByString(PacketEvents.getAPI().getServerManager().getVersion().toClientVersion(), data.getAsString(false)).getGlobalId());
					} else {
						blockId = (block.getType().getId() << 4) | block.getData();
					}

					encodedBlocks[index++] = new WrapperPlayServerMultiBlockChange.EncodedBlock(blockId, currX, currY | (chunkY << 4), currZ);
				}

				}
			}

		return encodedBlocks;
	}
}
