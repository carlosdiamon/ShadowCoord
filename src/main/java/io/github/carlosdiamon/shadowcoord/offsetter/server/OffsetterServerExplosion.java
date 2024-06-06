package io.github.carlosdiamon.shadowcoord.offsetter.server;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerExplosion;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketSendOffsetter;

public class OffsetterServerExplosion extends PacketSendOffsetter {
    public OffsetterServerExplosion() {
        super(PacketType.Play.Server.EXPLOSION);
    }

    @Override
    public void offset(PacketSendEvent event, CoordinateOffset offset) {
        WrapperPlayServerExplosion packet = new WrapperPlayServerExplosion(event);
        packet.setPosition(apply(packet.getPosition(), offset));
        packet.setRecords(packet.getRecords().stream().map(v -> apply(v, offset)).toList());
    }
}
