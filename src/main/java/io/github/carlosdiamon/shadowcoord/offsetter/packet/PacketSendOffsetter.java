package io.github.carlosdiamon.shadowcoord.offsetter.packet;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.protocol.world.chunk.Column;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.util.Vector3i;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import com.jtprince.coordinateoffset.offsetter.OffsettedColumn;
import org.jetbrains.annotations.NotNull;

public abstract class PacketSendOffsetter extends AbstractPacketOffsetter<PacketSendEvent> {

    public PacketSendOffsetter(PacketTypeCommon... packetTypes) {
        super(packetTypes);
    }

    public @NotNull Vector3d apply(
			final @NotNull Vector3d vec,
			final @NotNull CoordinateOffset offset
    ) {
        return new Vector3d(vec.x - offset.coordX(), vec.y, vec.z - offset.coordZ());
    }

    public @NotNull Vector3i apply(
			final @NotNull Vector3i vec,
			final @NotNull CoordinateOffset offset
    ) {
        return new Vector3i(vec.x - offset.coordX(), vec.y, vec.z - offset.coordZ());
    }

    public @NotNull Vector3i applyChunk(
			final @NotNull Vector3i vec,
			final @NotNull CoordinateOffset offset
    ) {
        return new Vector3i(vec.x - offset.chunkX(), vec.y, vec.z - offset.chunkZ());
    }

    public double applyX(
			double x,
			final @NotNull CoordinateOffset offset
    ) {
        return x - offset.coordX();
    }

    public double applyZ(double z, CoordinateOffset offset) {
        return z - offset.coordZ();
    }

    public int applyChunkX(int chunkX, CoordinateOffset offset) {
        return chunkX - offset.chunkX();
    }

    public int applyChunkZ(int chunkZ, CoordinateOffset offset) {
        return chunkZ - offset.chunkZ();
    }

    public OffsettedColumn applyColumn(Column column, CoordinateOffset offset, User user) {
        if (column instanceof OffsettedColumn) return (OffsettedColumn) column;
        return new OffsettedColumn(column, offset, user);
    }

    public Vector3i applyTimes8(Vector3i vec, CoordinateOffset offset) {
        return new Vector3i(vec.x - (offset.coordX() * 8), vec.y, vec.z - (offset.coordZ() * 8));
    }

}