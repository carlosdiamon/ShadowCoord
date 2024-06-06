package io.github.carlosdiamon.shadowcoord.offsetter.server;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerJoinGame;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketSendOffsetter;
import io.github.carlosdiamon.shadowcoord.util.PacketUtil;

public class OffsetterServerJoinGame extends PacketSendOffsetter {
    public OffsetterServerJoinGame() {
        super(PacketType.Play.Server.JOIN_GAME);
    }

    @Override
    public void offset(PacketSendEvent event, CoordinateOffset offset) {
        WrapperPlayServerJoinGame packet = new WrapperPlayServerJoinGame(event);
        if (packet.getLastDeathPosition() != null) {
            packet.setLastDeathPosition(PacketUtil.applyWorldBlock(packet.getLastDeathPosition(), offset));
        }
    }
}
