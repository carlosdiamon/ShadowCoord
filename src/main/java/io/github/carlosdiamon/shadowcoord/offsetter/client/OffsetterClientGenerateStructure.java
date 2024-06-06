package io.github.carlosdiamon.shadowcoord.offsetter.client;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientGenerateStructure;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketReceiveOffsetter;

public class OffsetterClientGenerateStructure extends PacketReceiveOffsetter {
    public OffsetterClientGenerateStructure() {
        super(PacketType.Play.Client.GENERATE_STRUCTURE);
    }
    @Override
    public void offset(PacketReceiveEvent event, CoordinateOffset offset) {
        WrapperPlayClientGenerateStructure packet = new WrapperPlayClientGenerateStructure(event);
        packet.setBlockPosition(unapply(packet.getBlockPosition(), offset));
    }
}
