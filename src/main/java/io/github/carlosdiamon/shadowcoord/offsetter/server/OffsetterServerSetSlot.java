package io.github.carlosdiamon.shadowcoord.offsetter.server;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetSlot;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketSendOffsetter;
import io.github.carlosdiamon.shadowcoord.util.PacketUtil;

public class OffsetterServerSetSlot extends PacketSendOffsetter {
    public OffsetterServerSetSlot() {
        super(PacketType.Play.Server.SET_SLOT);
    }

    @Override
    public void offset(PacketSendEvent event, CoordinateOffset offset) {
        WrapperPlayServerSetSlot packet = new WrapperPlayServerSetSlot(event);
        packet.setItem(PacketUtil.applyItemStack(packet.getItem(), offset));
    }
}
