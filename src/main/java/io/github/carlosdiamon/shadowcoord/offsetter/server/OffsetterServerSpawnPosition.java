package io.github.carlosdiamon.shadowcoord.offsetter.server;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnPosition;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketSendOffsetter;

public class OffsetterServerSpawnPosition extends PacketSendOffsetter {
    public OffsetterServerSpawnPosition() {
        super(PacketType.Play.Server.SPAWN_POSITION);
    }

    @Override
    public void offset(PacketSendEvent event, CoordinateOffset offset) {
        WrapperPlayServerSpawnPosition packet = new WrapperPlayServerSpawnPosition(event);
        packet.setPosition(apply(packet.getPosition(), offset));
    }
}
