package com.github.kaydogz.daboismod.network.server;

import com.github.kaydogz.daboismod.client.ClientPacketHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SSasquatchSmashPacket {

    private final double posX;
    private final double posY;
    private final double posZ;

    public SSasquatchSmashPacket(double posX, double posY, double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static void encode(SSasquatchSmashPacket msg, PacketBuffer buf) {
        buf.writeDouble(msg.posX);
        buf.writeDouble(msg.posY);
        buf.writeDouble(msg.posZ);
    }

    public static SSasquatchSmashPacket decode(PacketBuffer buf) {
        return new SSasquatchSmashPacket(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public static class Handler {

        public static void handle(final SSasquatchSmashPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleSasquatchSmash(ctx, msg.posX, msg.posY, msg.posZ)));
            ctx.get().setPacketHandled(true);
        }
    }
}
