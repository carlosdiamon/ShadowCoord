package io.github.carlosdiamon.shadowcoord.offsetter.client;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerBlockPlacement;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketReceiveOffsetter;

public class OffsetterClientPlayerBlockPlacement extends PacketReceiveOffsetter {
    public OffsetterClientPlayerBlockPlacement() {
        super(PacketType.Play.Client.PLAYER_BLOCK_PLACEMENT);
    }

    @Override
    public void offset(PacketReceiveEvent event, CoordinateOffset offset) {
        WrapperPlayClientPlayerBlockPlacement packet = new WrapperPlayClientPlayerBlockPlacement(event);
        packet.setBlockPosition(unapply(packet.getBlockPosition(), offset));
    }
}
