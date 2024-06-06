package io.github.carlosdiamon.shadowcoord.offsetter.server;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerOpenSignEditor;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketSendOffsetter;

public class OffsetterServerOpenSignEditor extends PacketSendOffsetter {
    public OffsetterServerOpenSignEditor() {
        super(PacketType.Play.Server.OPEN_SIGN_EDITOR);
    }
    @Override
    public void offset(PacketSendEvent event, CoordinateOffset offset) {
        WrapperPlayServerOpenSignEditor packet = new WrapperPlayServerOpenSignEditor(event);
        packet.setPosition(apply(packet.getPosition(), offset));
    }
}
