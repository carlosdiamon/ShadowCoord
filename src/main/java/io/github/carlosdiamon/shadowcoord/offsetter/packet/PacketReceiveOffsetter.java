package io.github.carlosdiamon.shadowcoord.offsetter.packet;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import com.github.retrooper.packetevents.protocol.world.Location;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.util.Vector3i;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

public abstract class PacketReceiveOffsetter extends AbstractPacketOffsetter<PacketReceiveEvent> {

    public PacketReceiveOffsetter(PacketTypeCommon... packetTypes) {
        super(packetTypes);
    }

    public Vector3d unapply(Vector3d vec, CoordinateOffset offset) {
        return new Vector3d(vec.x + offset.coordX(), vec.y, vec.z + offset.coordZ());
    }

    public Vector3i unapply(Vector3i vec, CoordinateOffset offset) {
        return new Vector3i(vec.x + offset.coordX(), vec.y, vec.z + offset.coordZ());
    }

    public Location unapply(Location loc, CoordinateOffset offset) {
        loc.setPosition(new Vector3d(loc.getX() + offset.coordX(), loc.getY(), loc.getZ() + offset.coordZ()));
        return loc;
    }

}
