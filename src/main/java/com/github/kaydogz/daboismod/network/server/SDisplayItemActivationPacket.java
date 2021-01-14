package com.github.kaydogz.daboismod.network.server;

import com.github.kaydogz.daboismod.client.ClientPacketHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SDisplayItemActivationPacket {

	private final ItemStack stack;

	public SDisplayItemActivationPacket(ItemStack stack) {
		this.stack = stack;
	}

	public static void encode(SDisplayItemActivationPacket msg, PacketBuffer buf) {
		buf.writeItemStack(msg.stack);
	}

	public static SDisplayItemActivationPacket decode(PacketBuffer buf) {
		return new SDisplayItemActivationPacket(buf.readItemStack());
	}

	public static class Handler {
		
		public static void handle(final SDisplayItemActivationPacket msg, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleDisplayItemActivation(ctx, msg.stack)));
			ctx.get().setPacketHandled(true);
		}
	}
}
