package io.github.carlosdiamon.shadowcoord.offsetter.client;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientCreativeInventoryAction;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketReceiveOffsetter;
import io.github.carlosdiamon.shadowcoord.util.PacketUtil;

public class OffsetterClientCreativeInventoryAction extends PacketReceiveOffsetter {
    public OffsetterClientCreativeInventoryAction() {
        super(PacketType.Play.Client.CREATIVE_INVENTORY_ACTION);
    }

    @Override
    public void offset(PacketReceiveEvent event, CoordinateOffset offset) {
        WrapperPlayClientCreativeInventoryAction packet = new WrapperPlayClientCreativeInventoryAction(event);
        packet.setItemStack(PacketUtil.unapplyItemStack(packet.getItemStack(), offset));
    }
}
