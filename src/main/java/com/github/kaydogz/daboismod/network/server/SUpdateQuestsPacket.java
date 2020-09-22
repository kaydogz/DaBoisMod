package com.github.kaydogz.daboismod.network.server;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.provider.PlayerCapability;
import com.github.kaydogz.daboismod.client.gui.QuestScreen;
import com.github.kaydogz.daboismod.quest.Quest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
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
	
	private final ArrayList<Quest> quests;
	private final int playerId;

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
				DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
					Minecraft minecraft = Minecraft.getInstance();
					Entity entity = minecraft.world.getEntityByID(msg.playerId);
					if (entity instanceof AbstractClientPlayerEntity) {
						DaBoisMod.get(PlayerCapability.getCapabilityOf(entity)).setQuests(msg.quests);
						if (entity instanceof ClientPlayerEntity && minecraft.currentScreen instanceof QuestScreen) {
							QuestScreen screen = (QuestScreen) minecraft.currentScreen;
							screen.updateQuests();
						}
					}
				});
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
