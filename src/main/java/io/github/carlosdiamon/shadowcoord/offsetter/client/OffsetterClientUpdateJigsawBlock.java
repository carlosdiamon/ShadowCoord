package io.github.carlosdiamon.shadowcoord.offsetter.client;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientUpdateJigsawBlock;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketReceiveOffsetter;

public class OffsetterClientUpdateJigsawBlock extends PacketReceiveOffsetter {
    public OffsetterClientUpdateJigsawBlock() {
        super(PacketType.Play.Client.UPDATE_JIGSAW_BLOCK);
    }

    @Override
    public void offset(PacketReceiveEvent event, CoordinateOffset offset) {
        WrapperPlayClientUpdateJigsawBlock packet = new WrapperPlayClientUpdateJigsawBlock(event);
        packet.setPosition(unapply(packet.getPosition(), offset));
    }
}
