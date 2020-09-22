package com.github.kaydogz.daboismod.network.server;

import com.github.kaydogz.daboismod.client.ClientPacketHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SUpdateMagneticPacket {

    public final boolean magnetic;
    public final int playerId;

    public SUpdateMagneticPacket(boolean magnetic, int playerId) {
        this.magnetic = magnetic;
        this.playerId = playerId;
    }

    public static void encode(SUpdateMagneticPacket msg, PacketBuffer buf) {
        buf.writeBoolean(msg.magnetic);
        buf.writeInt(msg.playerId);
    }

    public static SUpdateMagneticPacket decode(PacketBuffer buf) {
        return new SUpdateMagneticPacket(buf.readBoolean(), buf.readInt());
    }

    public static class Handler {

        public static void handle(final SUpdateMagneticPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleUpdateMagnetic(msg, ctx));
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
