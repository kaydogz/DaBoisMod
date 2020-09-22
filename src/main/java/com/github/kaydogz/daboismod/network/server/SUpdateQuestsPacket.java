package com.github.kaydogz.daboismod.network.server;

import com.github.kaydogz.daboismod.client.ClientPacketHandler;
import com.github.kaydogz.daboismod.quest.Quest;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

public class SUpdateQuestsPacket {
	
	public final ArrayList<Quest> quests;
	public final int playerId;

	public SUpdateQuestsPacket(ArrayList<Quest> quests, int playerId) {
		this.quests = quests;
		this.playerId = playerId;
	}

	public static void encode(SUpdateQuestsPacket msg, PacketBuffer buf) {
		CompoundNBT tag = new CompoundNBT();

		ListNBT listNbt = new ListNBT();
		msg.quests.forEach((quest) -> listNbt.add(quest.write(new CompoundNBT())));
		tag.put("Quests", listNbt);
		buf.writeCompoundTag(tag);

		buf.writeInt(msg.playerId);
	}

	public static SUpdateQuestsPacket decode(PacketBuffer buf) {
		CompoundNBT tag = buf.readCompoundTag();
		ArrayList<Quest> quests = new ArrayList<>();
		if (tag != null && tag.contains("Quests", Constants.NBT.TAG_LIST)) {
			((ListNBT) tag.get("Quests")).forEach((data) -> {
				if (data instanceof CompoundNBT) {
					CompoundNBT questTag = (CompoundNBT) data;
					Quest quest = Quest.read(questTag);
					if (quest != null) quests.add(quest);
				}
			});
		}
		return new SUpdateQuestsPacket(quests, buf.readInt());
	}

	public static class Handler {
		
		public static void handle(final SUpdateQuestsPacket msg, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleUpdateQuests(msg, ctx));
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
