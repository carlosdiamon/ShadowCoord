package io.github.carlosdiamon.shadowcoord.offsetter.server;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketSendOffsetter;
import com.jtprince.coordinateoffset.offsetter.wrapper.WrapperPlayServerUpdateLight;

public class OffsetterServerUpdateLight extends PacketSendOffsetter {
    public OffsetterServerUpdateLight() {
        super(PacketType.Play.Server.UPDATE_LIGHT);
    }

    @Override
    public void offset(PacketSendEvent event, CoordinateOffset offset) {
        WrapperPlayServerUpdateLight packet = new WrapperPlayServerUpdateLight(event);
        packet.setChunkX(applyChunkX(packet.getChunkX(), offset));
        packet.setChunkZ(applyChunkZ(packet.getChunkZ(), offset));
    }
}
