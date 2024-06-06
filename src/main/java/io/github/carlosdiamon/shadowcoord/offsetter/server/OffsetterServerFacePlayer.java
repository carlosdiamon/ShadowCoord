package io.github.carlosdiamon.shadowcoord.offsetter.server;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerFacePlayer;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketSendOffsetter;

public class OffsetterServerFacePlayer extends PacketSendOffsetter {
    public OffsetterServerFacePlayer() {
        super(PacketType.Play.Server.FACE_PLAYER);
    }

    @Override
    public void offset(PacketSendEvent event, CoordinateOffset offset) {
        WrapperPlayServerFacePlayer packet = new WrapperPlayServerFacePlayer(event);
        packet.setTargetPosition(apply(packet.getTargetPosition(), offset));
    }
}
