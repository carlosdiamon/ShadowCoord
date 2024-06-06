package io.github.carlosdiamon.shadowcoord.offsetter.server;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerChunkData;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketSendOffsetter;

public class OffsetterServerChunkData extends PacketSendOffsetter {
    public OffsetterServerChunkData() {
        super(PacketType.Play.Server.CHUNK_DATA);
    }
    @Override
    public void offset(PacketSendEvent event, CoordinateOffset offset) {
        WrapperPlayServerChunkData packet = new WrapperPlayServerChunkData(event);
        packet.setColumn(applyColumn(packet.getColumn(), offset, event.getUser()));
    }
}
