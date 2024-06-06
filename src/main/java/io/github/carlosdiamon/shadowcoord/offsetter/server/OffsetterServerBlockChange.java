package io.github.carlosdiamon.shadowcoord.offsetter.server;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerBlockChange;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketSendOffsetter;

public class OffsetterServerBlockChange extends PacketSendOffsetter {
    public OffsetterServerBlockChange() {
        super(PacketType.Play.Server.BLOCK_CHANGE);
    }

    @Override
    public void offset(PacketSendEvent event, CoordinateOffset offset) {
        WrapperPlayServerBlockChange packet = new WrapperPlayServerBlockChange(event);
        packet.setBlockPosition(apply(packet.getBlockPosition(), offset));
    }
}
