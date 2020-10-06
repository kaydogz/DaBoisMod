package com.github.kaydogz.daboismod.network.client;

import com.github.kaydogz.daboismod.item.CrownHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CToggleGodsCrownActivationPacket {

	private final boolean sendMessages;

	public CToggleGodsCrownActivationPacket(boolean sendMessages) {
		this.sendMessages = sendMessages;
	}

	public static void encode(CToggleGodsCrownActivationPacket msg, PacketBuffer buf) {
		buf.writeBoolean(msg.sendMessages);
	}

	public static CToggleGodsCrownActivationPacket decode(PacketBuffer buf) {
		return new CToggleGodsCrownActivationPacket(buf.readBoolean());
	}

	public static class Handler {
		
		public static void handle(final CToggleGodsCrownActivationPacket msg, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				ServerPlayerEntity player = ctx.get().getSender();
				if (player != null) CrownHelper.toggleActivation(player, msg.sendMessages);
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
