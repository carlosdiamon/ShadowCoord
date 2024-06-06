package io.github.carlosdiamon.shadowcoord.offsetter.client;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerDigging;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketReceiveOffsetter;

public class OffsetterClientPlayerDigging extends PacketReceiveOffsetter {
    public OffsetterClientPlayerDigging() {
        super(PacketType.Play.Client.PLAYER_DIGGING);
    }

    @Override
    public void offset(PacketReceiveEvent event, CoordinateOffset offset) {
        WrapperPlayClientPlayerDigging packet = new WrapperPlayClientPlayerDigging(event);

        packet.setBlockPosition(unapply(packet.getBlockPosition(), offset));
    }
}
