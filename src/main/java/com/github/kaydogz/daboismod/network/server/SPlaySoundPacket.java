package com.github.kaydogz.daboismod.network.server;

import com.github.kaydogz.daboismod.client.DBMClientPacketHandler;
import net.minecraft.network.PacketBuffer;
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
			ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> DBMClientPacketHandler.handlePlaySound(msg.sound, msg.global, ctx)));
			ctx.get().setPacketHandled(true);
		}
	}
}
