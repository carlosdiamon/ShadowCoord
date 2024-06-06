package io.github.carlosdiamon.shadowcoord.offsetter.server;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerRespawn;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketSendOffsetter;
import io.github.carlosdiamon.shadowcoord.util.PacketUtil;

public class OffsetterServerRespawn extends PacketSendOffsetter {
    public OffsetterServerRespawn() {
        super(PacketType.Play.Server.RESPAWN);
    }

    @Override
    public void offset(PacketSendEvent event, CoordinateOffset offset) {
        WrapperPlayServerRespawn packet = new WrapperPlayServerRespawn(event);
        if (packet.getLastDeathPosition() != null) {
            packet.setLastDeathPosition(PacketUtil.applyWorldBlock(packet.getLastDeathPosition(), offset));
        }
    }
}
