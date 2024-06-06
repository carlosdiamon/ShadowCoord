package io.github.carlosdiamon.shadowcoord.offsetter.client;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;
import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketReceiveOffsetter;
import io.github.carlosdiamon.shadowcoord.util.PacketUtil;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class OffsetterClientClickWindow extends PacketReceiveOffsetter {
    public OffsetterClientClickWindow() {
        super(PacketType.Play.Client.CLICK_WINDOW);
    }

    @Override
    public void offset(PacketReceiveEvent event, CoordinateOffset offset) {
        WrapperPlayClientClickWindow packet = new WrapperPlayClientClickWindow(event);

        if (packet.getSlots().isPresent()) {
            Map<Integer, ItemStack> clientItems = packet.getSlots().get();
            Map<Integer, ItemStack> serverItems = clientItems.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, v -> PacketUtil.unapplyItemStack(v.getValue(), offset)));
            packet.setSlots(Optional.of(serverItems));
        }
        packet.setCarriedItemStack(PacketUtil.unapplyItemStack(packet.getCarriedItemStack(), offset));
    }
}
