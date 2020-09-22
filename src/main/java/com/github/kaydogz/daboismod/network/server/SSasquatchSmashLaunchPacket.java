package com.github.kaydogz.daboismod.network.server;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SSasquatchSmashLaunchPacket {

	private final boolean shouldLaunch;
	private final BlockPos position;
	
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
				ClientPlayerEntity player = Minecraft.getInstance().player;

				if (player != null) {
					if (msg.shouldLaunch) player.setMotion(player.getMotion().add(0.0D, 1.5D, 0.0D));
					double particleSpeedRadius = 0.5D;
					for (int i = 0; i < 16; i++)
						player.world.addParticle(
								ParticleTypes.CLOUD,
								msg.position.getX(),
								msg.position.getY(),
								msg.position.getZ(),
								particleSpeedRadius * Math.cos(i * Math.PI / 8),
								0.1D, particleSpeedRadius * Math.sin(i * Math.PI / 8)
						);
				}

			});
			ctx.get().setPacketHandled(true);
		}
	}
}
