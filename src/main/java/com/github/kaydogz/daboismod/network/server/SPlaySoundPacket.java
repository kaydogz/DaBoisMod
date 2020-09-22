package com.github.kaydogz.daboismod.network.server;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class SPlaySoundPacket {

	private final SoundEvent sound;
	private final boolean global;
	
	public SPlaySoundPacket(SoundEvent sound, boolean global) {
		this.sound = sound;
		this.global = global;
	}

	public static void encode(SPlaySoundPacket msg, PacketBuffer buf) {
		buf.writeResourceLocation(msg.sound.getRegistryName());
		buf.writeBoolean(msg.global);
	}

	public static SPlaySoundPacket decode(PacketBuffer buf) {
		return new SPlaySoundPacket(ForgeRegistries.SOUND_EVENTS.getValue(buf.readResourceLocation()), buf.readBoolean());
	}

	public static class Handler {
		
		public static void handle(final SPlaySoundPacket msg, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
					Minecraft minecraft = Minecraft.getInstance();
					ClientPlayerEntity player = minecraft.player;
					if (player != null) {
						if (msg.global) {
							ActiveRenderInfo renderInfo = minecraft.gameRenderer.getActiveRenderInfo();
							if (minecraft.gameRenderer.getActiveRenderInfo().isValid()) {
								double d0 = (double) player.getPosition().getX() - renderInfo.getProjectedView().x;
								double d1 = (double) player.getPosition().getY() - renderInfo.getProjectedView().y;
								double d2 = (double) player.getPosition().getZ() - renderInfo.getProjectedView().z;
								double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
								double d4 = renderInfo.getProjectedView().x;
								double d5 = renderInfo.getProjectedView().y;
								double d6 = renderInfo.getProjectedView().z;
								if (d3 > 0.0D) {
									d4 += d0 / d3 * 2.0D;
									d5 += d1 / d3 * 2.0D;
									d6 += d2 / d3 * 2.0D;
								}

								player.worldClient.playSound(d4, d5, d6, msg.sound, SoundCategory.MASTER, 1.0F, 1.0F, false);
							}
						}
						else player.playSound(msg.sound, 1.0F, 1.0F);
					}
				});
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
