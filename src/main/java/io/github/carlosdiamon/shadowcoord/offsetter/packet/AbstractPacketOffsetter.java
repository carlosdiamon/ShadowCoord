package io.github.carlosdiamon.shadowcoord.offsetter.packet;

import com.github.retrooper.packetevents.event.ProtocolPacketEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

public abstract class AbstractPacketOffsetter<T extends ProtocolPacketEvent<Object>> {
    private final PacketTypeCommon[] packetTypes;

    public AbstractPacketOffsetter(PacketTypeCommon... packetTypes) {
        this.packetTypes = packetTypes;
    }

    public abstract void offset(T event, CoordinateOffset offset);

    public PacketTypeCommon[] getPacketTypes() {
        return packetTypes;
    }
}