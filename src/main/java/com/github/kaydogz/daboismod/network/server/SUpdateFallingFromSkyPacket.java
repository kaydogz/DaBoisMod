package com.github.kaydogz.daboismod.network.server;

import com.github.kaydogz.daboismod.client.ClientPacketHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SUpdateFallingFromSkyPacket {

	public final boolean fallingFromSky;
	public final int entityId;

	public SUpdateFallingFromSkyPacket(boolean fallingFromSky, int entityId) {
		this.fallingFromSky = fallingFromSky;
		this.entityId = entityId;
	}

	public static void encode(SUpdateFallingFromSkyPacket msg, PacketBuffer buf) {
		buf.writeInt(msg.entityId);
		buf.writeBoolean(msg.fallingFromSky);
	}

	public static SUpdateFallingFromSkyPacket decode(PacketBuffer buf) {
		return new SUpdateFallingFromSkyPacket(buf.readBoolean(), buf.readInt());
	}

	public static class Handler {
		
		public static void handle(final SUpdateFallingFromSkyPacket msg, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleUpdateFallingFromSky(msg, ctx));
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
