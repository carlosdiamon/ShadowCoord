package io.github.carlosdiamon.shadowcoord.offsetter.server;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.nbt.NBTInt;
import com.github.retrooper.packetevents.protocol.nbt.NBTNumber;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerBlockEntityData;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketSendOffsetter;

public class OffsetterServerBlockEntityData extends PacketSendOffsetter {
    public OffsetterServerBlockEntityData() {
        super(PacketType.Play.Server.BLOCK_ENTITY_DATA);
    }

    @Override
    public void offset(PacketSendEvent event, CoordinateOffset offset) {
        WrapperPlayServerBlockEntityData packet = new WrapperPlayServerBlockEntityData(event);
        packet.setPosition(apply(packet.getPosition(), offset));

        // TBD: I'm not sure which tile entity these are used for, but I'm keeping them from upstream just in case.
        if (packet.getNBT() != null) {
            NBTCompound nbt = packet.getNBT();
            NBTNumber x = nbt.getNumberTagOrNull("coordX");
            NBTNumber z = nbt.getNumberTagOrNull("coordZ");
            if (x != null && z != null) {
                nbt.setTag("coordX", new NBTInt(x.getAsInt() - offset.coordX()));
                nbt.setTag("coordZ", new NBTInt(z.getAsInt() - offset.coordZ()));
            }
        }
    }
}
