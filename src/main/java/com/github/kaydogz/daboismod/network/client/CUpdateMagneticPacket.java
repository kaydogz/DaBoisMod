package com.github.kaydogz.daboismod.network.client;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.IPlayerCapability;
import com.github.kaydogz.daboismod.capability.provider.PlayerProvider;
import com.github.kaydogz.daboismod.enchantment.MagnetismEnchantment;
import com.github.kaydogz.daboismod.network.DBMPacketHandler;
import com.github.kaydogz.daboismod.network.server.SUpdateMagneticPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
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
			ctx.get().enqueueWork(() -> {
				ServerPlayerEntity player = ctx.get().getSender();

				// Make sure request is valid before updating
				if (!msg.magnetic || MagnetismEnchantment.isHoldingMagneticItem(player)) {
					IPlayerCapability cap = DaBoisMod.get(PlayerProvider.getCapabilityOf(player));
					cap.setMagnetic(msg.magnetic);
					DBMPacketHandler.sendToAllTrackingEntityAndSelf(new SUpdateMagneticPacket(msg.magnetic, player.getEntityId()), player);
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
