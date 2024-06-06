package io.github.carlosdiamon.shadowcoord.offsetter.client;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientVehicleMove;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketReceiveOffsetter;

public class OffsetterClientVehicleMove extends PacketReceiveOffsetter {
    public OffsetterClientVehicleMove() {
        super(PacketType.Play.Client.VEHICLE_MOVE);
    }

    @Override
    public void offset(PacketReceiveEvent event, CoordinateOffset offset) {
        WrapperPlayClientVehicleMove packet = new WrapperPlayClientVehicleMove(event);
        packet.setPosition(unapply(packet.getPosition(), offset));
    }
}
