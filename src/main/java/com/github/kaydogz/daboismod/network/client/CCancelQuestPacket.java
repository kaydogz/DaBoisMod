package com.github.kaydogz.daboismod.network.client;

import com.github.kaydogz.daboismod.quest.Quest;
import com.github.kaydogz.daboismod.util.ServerPacketHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CCancelQuestPacket {

	private final Quest quest;

	public CCancelQuestPacket(Quest questIn) {
		this.quest = questIn;
	}

	public static void encode(CCancelQuestPacket msg, PacketBuffer buf) {
		buf.writeCompoundTag(msg.quest.write(new CompoundNBT()));
	}

	public static CCancelQuestPacket decode(PacketBuffer buf) {
		return new CCancelQuestPacket(Quest.read(buf.readCompoundTag()));
	}

	public static class Handler {

		public static void handle(final CCancelQuestPacket msg, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> ServerPacketHandler.handleCancelQuest(ctx, msg.quest));
			ctx.get().setPacketHandled(true);
		}
	}
}
