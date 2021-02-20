package com.github.kaydogz.daboismod.network.server;

import com.github.kaydogz.daboismod.client.ClientPacketHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SUpdateDimensionPacket {

    private final RegistryKey<World> worldKey;
    private final boolean add;

    public SUpdateDimensionPacket(RegistryKey<World> worldKey, boolean add) {
        this.worldKey = worldKey;
        this.add = add;
    }

    public static void encode(SUpdateDimensionPacket msg, PacketBuffer buf) {
        buf.writeResourceLocation(msg.worldKey.getLocation());
        buf.writeBoolean(msg.add);
    }

    public static SUpdateDimensionPacket decode(PacketBuffer buf) {
        return new SUpdateDimensionPacket(RegistryKey.getOrCreateKey(Registry.WORLD_KEY, buf.readResourceLocation()), buf.readBoolean());
    }

    public static class Handler {

        public static void handle(final SUpdateDimensionPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleUpdateDimension(ctx, msg.worldKey, msg.add)));
            ctx.get().setPacketHandled(true);
        }
    }
}
