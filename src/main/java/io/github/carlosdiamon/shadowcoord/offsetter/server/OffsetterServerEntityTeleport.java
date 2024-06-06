package io.github.carlosdiamon.shadowcoord.offsetter.server;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityTeleport;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketSendOffsetter;

public class OffsetterServerEntityTeleport extends PacketSendOffsetter {
    public OffsetterServerEntityTeleport() {
        super(PacketType.Play.Server.ENTITY_TELEPORT);
    }

    @Override
    public void offset(PacketSendEvent event, CoordinateOffset offset) {
        WrapperPlayServerEntityTeleport packet = new WrapperPlayServerEntityTeleport(event);
        packet.setPosition(apply(packet.getPosition(), offset));
    }
}
