package io.github.carlosdiamon.shadowcoord.offsetter.client;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientUpdateCommandBlock;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketReceiveOffsetter;

public class OffsetterClientUpdateCommandBlock extends PacketReceiveOffsetter {
    public OffsetterClientUpdateCommandBlock() {
        super(PacketType.Play.Client.UPDATE_COMMAND_BLOCK);
    }

    @Override
    public void offset(PacketReceiveEvent event, CoordinateOffset offset) {
        WrapperPlayClientUpdateCommandBlock packet = new WrapperPlayClientUpdateCommandBlock(event);
        packet.setPosition(unapply(packet.getPosition(), offset));
    }
}
