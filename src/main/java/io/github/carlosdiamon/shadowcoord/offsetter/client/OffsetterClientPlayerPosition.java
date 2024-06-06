package io.github.carlosdiamon.shadowcoord.offsetter.client;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketReceiveOffsetter;

public class OffsetterClientPlayerPosition extends PacketReceiveOffsetter {
    public OffsetterClientPlayerPosition() {
        super(PacketType.Play.Client.PLAYER_POSITION, PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION);
    }

    @Override
    public void offset(PacketReceiveEvent event, CoordinateOffset offset) {
        WrapperPlayClientPlayerFlying packet = new WrapperPlayClientPlayerFlying(event);
        packet.setLocation(unapply(packet.getLocation(), offset));
    }
}
