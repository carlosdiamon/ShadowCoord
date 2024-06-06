package io.github.carlosdiamon.shadowcoord.offsetter.server;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerMultiBlockChange;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketSendOffsetter;

public class OffsetterServerMultiBlockChange extends PacketSendOffsetter {
    public OffsetterServerMultiBlockChange() {
        super(PacketType.Play.Server.MULTI_BLOCK_CHANGE);
    }

    @Override
    public void offset(PacketSendEvent event, CoordinateOffset offset) {
			WrapperPlayServerMultiBlockChange packet = new WrapperPlayServerMultiBlockChange(event);
			packet.setChunkPosition(applyChunk(packet.getChunkPosition(), offset));
    }
}
