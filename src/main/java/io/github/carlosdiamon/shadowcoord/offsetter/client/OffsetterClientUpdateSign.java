package io.github.carlosdiamon.shadowcoord.offsetter.client;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientUpdateSign;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketReceiveOffsetter;

public class OffsetterClientUpdateSign extends PacketReceiveOffsetter {
    public OffsetterClientUpdateSign() {
        super(PacketType.Play.Client.UPDATE_SIGN);
    }

    @Override
    public void offset(PacketReceiveEvent event, CoordinateOffset offset) {
        WrapperPlayClientUpdateSign packet = new WrapperPlayClientUpdateSign(event);
        packet.setBlockPosition(unapply(packet.getBlockPosition(), offset));
    }
}
