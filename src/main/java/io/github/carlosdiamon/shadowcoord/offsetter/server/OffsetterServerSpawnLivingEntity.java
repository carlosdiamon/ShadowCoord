package io.github.carlosdiamon.shadowcoord.offsetter.server;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnLivingEntity;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketSendOffsetter;

public class OffsetterServerSpawnLivingEntity extends PacketSendOffsetter {
    public OffsetterServerSpawnLivingEntity() {
        // Removed in 1.19
        super(PacketType.Play.Server.SPAWN_LIVING_ENTITY);
    }

    @Override
    public void offset(PacketSendEvent event, CoordinateOffset offset) {
        WrapperPlayServerSpawnLivingEntity packet = new WrapperPlayServerSpawnLivingEntity(event);
        packet.setPosition(apply(packet.getPosition(), offset));
    }
}
