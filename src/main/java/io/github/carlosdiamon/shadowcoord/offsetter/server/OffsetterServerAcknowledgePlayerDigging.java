package io.github.carlosdiamon.shadowcoord.offsetter.server;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerAcknowledgePlayerDigging;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketSendOffsetter;
public class OffsetterServerAcknowledgePlayerDigging extends PacketSendOffsetter {
    public OffsetterServerAcknowledgePlayerDigging() {
        // Removed in 1.19 and replaced with ACKNOWLEDGE_BLOCK_CHANGES (which has no position)
        super(PacketType.Play.Server.ACKNOWLEDGE_PLAYER_DIGGING);
    }

    @Override
    public void offset(PacketSendEvent event, CoordinateOffset offset) {
        WrapperPlayServerAcknowledgePlayerDigging packet = new WrapperPlayServerAcknowledgePlayerDigging(event);
        packet.setBlockPosition(apply(packet.getBlockPosition(), offset));
    }
}
