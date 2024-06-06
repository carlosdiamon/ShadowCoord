package io.github.carlosdiamon.shadowcoord.offsetter.server;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketSendOffsetter;
import com.jtprince.coordinateoffset.offsetter.wrapper.WrapperPlayServerSoundEffect_WithIdentifier;

public class OffsetterServerSoundEffect extends PacketSendOffsetter {
    public OffsetterServerSoundEffect() {
        super(PacketType.Play.Server.SOUND_EFFECT);
    }

    @Override
    public void offset(PacketSendEvent event, CoordinateOffset offset) {
        WrapperPlayServerSoundEffect_WithIdentifier packet = new WrapperPlayServerSoundEffect_WithIdentifier(event);
        packet.setEffectPosition(applyTimes8(packet.getEffectPosition(), offset));
    }
}
