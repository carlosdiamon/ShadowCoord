package io.github.carlosdiamon.shadowcoord.offsetter.server;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerVehicleMove;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketSendOffsetter;

public class OffsetterServerVehicleMove extends PacketSendOffsetter {
    public OffsetterServerVehicleMove() {
        super(PacketType.Play.Server.VEHICLE_MOVE);
    }

    @Override
    public void offset(PacketSendEvent event, CoordinateOffset offset) {
        WrapperPlayServerVehicleMove packet = new WrapperPlayServerVehicleMove(event);
        packet.setPosition(apply(packet.getPosition(), offset));
    }
}
