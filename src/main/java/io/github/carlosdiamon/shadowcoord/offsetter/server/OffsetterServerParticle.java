package io.github.carlosdiamon.shadowcoord.offsetter.server;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.particle.data.ParticleItemStackData;
import com.github.retrooper.packetevents.protocol.particle.data.ParticleVibrationData;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerParticle;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketSendOffsetter;
import io.github.carlosdiamon.shadowcoord.util.PacketUtil;

public class OffsetterServerParticle extends PacketSendOffsetter {
    public OffsetterServerParticle() {
        super(PacketType.Play.Server.PARTICLE);
    }

    @Override
    public void offset(PacketSendEvent event, CoordinateOffset offset) {
        WrapperPlayServerParticle packet = new WrapperPlayServerParticle(event);
        packet.setPosition(apply(packet.getPosition(), offset));

        if (packet.getParticle().getData() instanceof ParticleVibrationData vibrationData) {
            // startingPosition was only part of packet data up to 1.19.4. PE reports >1.19.4 with a zero vector.
            //  Make sure not to offset this zero vector, or it will leak offsets.
            if (!vibrationData.getStartingPosition().equals(Vector3i.zero())) {
                vibrationData.setStartingPosition(apply(vibrationData.getStartingPosition(), offset));
            }

            if (vibrationData.getBlockPosition().isPresent()) {
                vibrationData.setBlockPosition(apply(vibrationData.getBlockPosition().get(), offset));
            }
        }

        if (packet.getParticle().getData() instanceof ParticleItemStackData itemStackData) {
            itemStackData.setItemStack(PacketUtil.applyItemStack(itemStackData.getItemStack(), offset));
        }
    }
}
