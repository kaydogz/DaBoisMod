package com.github.kaydogz.daboismod.network.server;

import com.github.kaydogz.daboismod.client.ClientPacketHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SSasquatchSmashLaunchPacket {

	public final boolean shouldLaunch;
	public final BlockPos position;
	
	public SSasquatchSmashLaunchPacket(boolean shouldLaunch, BlockPos position) {
		this.shouldLaunch = shouldLaunch;
		this.position = position;
	}

	public static void encode(SSasquatchSmashLaunchPacket msg, PacketBuffer buf) {
		buf.writeBoolean(msg.shouldLaunch);
		buf.writeBlockPos(msg.position);
	}

	public static SSasquatchSmashLaunchPacket decode(PacketBuffer buf) {
		return new SSasquatchSmashLaunchPacket(buf.readBoolean(), buf.readBlockPos());
	}

	public static class Handler {
		
		public static void handle(final SSasquatchSmashLaunchPacket msg, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleSasquatchSmashLaunch(msg, ctx));
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
