package com.github.kaydogz.daboismod.network.server;

import com.github.kaydogz.daboismod.client.DBMClientPacketHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SUpdateSkinTonePacket {

    private final int skinTone;
    private final int entityId;

    public SUpdateSkinTonePacket(int skinTone, int entityId) {
        this.skinTone = skinTone;
        this.entityId = entityId;
    }

    public static void encode(SUpdateSkinTonePacket msg, PacketBuffer buf) {
        buf.writeInt(msg.skinTone);
        buf.writeInt(msg.entityId);
    }

    public static SUpdateSkinTonePacket decode(PacketBuffer buf) {
        return new SUpdateSkinTonePacket(buf.readInt(), buf.readInt());
    }

    public static class Handler {

        public static void handle(final SUpdateSkinTonePacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> DBMClientPacketHandler.handleUpdateSkinTone(msg.skinTone, msg.entityId, ctx)));
            ctx.get().setPacketHandled(true);
        }
    }
}
