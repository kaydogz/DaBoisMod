package com.github.kaydogz.daboismod.network.client;

import com.github.kaydogz.daboismod.quest.Quest;
import com.github.kaydogz.daboismod.util.ServerPacketHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CClaimQuestPacket {

	private final Quest quest;

	public CClaimQuestPacket(Quest questIn) {
		this.quest = questIn;
	}

	public static void encode(CClaimQuestPacket msg, PacketBuffer buf) {
		buf.writeCompoundTag(msg.quest.write(new CompoundNBT()));
	}

	public static CClaimQuestPacket decode(PacketBuffer buf) {
		return new CClaimQuestPacket(Quest.read(buf.readCompoundTag()));
	}

	public static class Handler {
		
		public static void handle(final CClaimQuestPacket msg, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> ServerPacketHandler.handleClaimQuest(ctx, msg.quest));
			ctx.get().setPacketHandled(true);
		}
	}
}
