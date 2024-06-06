package io.github.carlosdiamon.shadowcoord.offsetter.server;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketSendOffsetter;
import io.github.carlosdiamon.shadowcoord.util.PacketUtil;

import java.util.Optional;

public class OffsetterServerEntityMetadata extends PacketSendOffsetter {
    public OffsetterServerEntityMetadata() {
        super(PacketType.Play.Server.ENTITY_METADATA);
    }

    @Override
    public void offset(PacketSendEvent event, CoordinateOffset offset) {
        WrapperPlayServerEntityMetadata packet = new WrapperPlayServerEntityMetadata(event);
        for (EntityData data : packet.getEntityMetadata()) {
            Object value = data.getValue();
            if (value == null) {
                continue;
            }
            if (value instanceof Optional<?> optional) {
                optional.ifPresent(o -> data.setValue(Optional.of(applyOffsetToEntityMeta(o, offset))));
            } else {
                data.setValue(applyOffsetToEntityMeta(value, offset));
            }
        }
    }

    private Object applyOffsetToEntityMeta(Object object, CoordinateOffset offset) {
        if (object instanceof Vector3i blockPosition) {
            // TBD: subtract instead of adding negative https://github.com/retrooper/packetevents/issues/646
            return blockPosition.add(-offset.coordX(), 0, -offset.coordZ());
        }
        if (object instanceof ItemStack) {
            return PacketUtil.applyItemStack((ItemStack) object, offset);
        }
        return object;
    }
}
