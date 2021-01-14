package com.github.kaydogz.daboismod.network.client;

import com.github.kaydogz.daboismod.util.ServerPacketHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CUpdateMagneticPacket {

	private final boolean magnetic;

	public CUpdateMagneticPacket(boolean magnetic) {
		this.magnetic = magnetic;
	}

	public static void encode(CUpdateMagneticPacket msg, PacketBuffer buf) {
		buf.writeBoolean(msg.magnetic);
	}

	public static CUpdateMagneticPacket decode(PacketBuffer buf) {
		return new CUpdateMagneticPacket(buf.readBoolean());
	}

	public static class Handler {
		
		public static void handle(final CUpdateMagneticPacket msg, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> ServerPacketHandler.handleUpdateMagnetic(ctx, msg.magnetic));
			ctx.get().setPacketHandled(true);
		}
	}
}
