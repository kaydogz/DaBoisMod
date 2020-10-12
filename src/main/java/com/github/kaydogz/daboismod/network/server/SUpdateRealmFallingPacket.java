package com.github.kaydogz.daboismod.network.server;

import com.github.kaydogz.daboismod.client.DBMClientPacketHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SUpdateRealmFallingPacket {

	private final boolean realmFalling;
	private final int entityId;

	public SUpdateRealmFallingPacket(boolean realmFalling, int entityId) {
		this.realmFalling = realmFalling;
		this.entityId = entityId;
	}

	public static void encode(SUpdateRealmFallingPacket msg, PacketBuffer buf) {
		buf.writeBoolean(msg.realmFalling);
		buf.writeInt(msg.entityId);
	}

	public static SUpdateRealmFallingPacket decode(PacketBuffer buf) {
		return new SUpdateRealmFallingPacket(buf.readBoolean(), buf.readInt());
	}

	public static class Handler {
		
		public static void handle(final SUpdateRealmFallingPacket msg, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> DBMClientPacketHandler.handleUpdateRealmFalling(msg.realmFalling, msg.entityId, ctx)));
			ctx.get().setPacketHandled(true);
		}
	}
}
