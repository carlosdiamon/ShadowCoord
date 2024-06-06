package io.github.carlosdiamon.shadowcoord.offsetter.server;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerWindowItems;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketSendOffsetter;
import io.github.carlosdiamon.shadowcoord.util.PacketUtil;

public class OffsetterServerWindowItems extends PacketSendOffsetter {
    public OffsetterServerWindowItems() {
        super(PacketType.Play.Server.WINDOW_ITEMS);
    }

    @Override
    public void offset(PacketSendEvent event, CoordinateOffset offset) {
        WrapperPlayServerWindowItems packet = new WrapperPlayServerWindowItems(event);
        packet.setItems(packet.getItems().stream().map(it -> PacketUtil.applyItemStack(it, offset)).toList());
        if (packet.getCarriedItem().isPresent()) {
            packet.setCarriedItem(PacketUtil.applyItemStack(packet.getCarriedItem().get(), offset));
        }
    }
}
