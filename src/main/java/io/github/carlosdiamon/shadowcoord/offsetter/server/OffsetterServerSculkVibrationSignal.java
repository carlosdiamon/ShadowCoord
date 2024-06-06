package io.github.carlosdiamon.shadowcoord.offsetter.server;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import io.github.carlosdiamon.shadowcoord.coord.CoordinateOffset;

import io.github.carlosdiamon.shadowcoord.offsetter.packet.PacketSendOffsetter;
import com.jtprince.coordinateoffset.offsetter.wrapper.WrapperPlayServerSculkVibrationSignal;

public class OffsetterServerSculkVibrationSignal extends PacketSendOffsetter {
    public OffsetterServerSculkVibrationSignal() {
        // Removed in 1.19
        super(PacketType.Play.Server.SCULK_VIBRATION_SIGNAL);
    }

    @Override
    public void offset(PacketSendEvent event, CoordinateOffset offset) {
        WrapperPlayServerSculkVibrationSignal packet = new WrapperPlayServerSculkVibrationSignal(event);
        packet.setSourcePosition(apply(packet.getSourcePosition(), offset));
        if (packet.getDestinationPosition() != null) {
            packet.setDestinationPosition(apply(packet.getDestinationPosition(), offset));
        }
    }
}
