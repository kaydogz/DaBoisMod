package com.github.kaydogz.daboismod.network.server;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.provider.PlayerCapability;
import com.github.kaydogz.daboismod.client.audio.MagnetismTickableSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SUpdateMagneticPacket {

    private final boolean magnetic;
    private final int playerId;

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
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                    Minecraft minecraft = Minecraft.getInstance();
                    Entity entity = minecraft.world.getEntityByID(msg.playerId);
                    if (entity instanceof AbstractClientPlayerEntity) {
                        DaBoisMod.get(PlayerCapability.getCapabilityOf(entity)).setMagnetic(msg.magnetic);
                        minecraft.getSoundHandler().playOnNextTick(new MagnetismTickableSound((AbstractClientPlayerEntity) entity));
                    }
                });
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
