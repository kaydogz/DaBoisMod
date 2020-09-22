package com.github.kaydogz.daboismod.network.client;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.IPlayerCap;
import com.github.kaydogz.daboismod.capability.provider.PlayerCapability;
import com.github.kaydogz.daboismod.network.DBMPacketHandler;
import com.github.kaydogz.daboismod.network.server.SUpdateQuestsPacket;
import com.github.kaydogz.daboismod.quest.Quest;
import com.github.kaydogz.daboismod.stats.DBMStats;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
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
			ctx.get().enqueueWork(() -> {
				ServerPlayerEntity player = ctx.get().getSender();
				if (player != null) {
					LazyOptional<IPlayerCap> lazyCap = PlayerCapability.getCapabilityOf(player);
					if (lazyCap.isPresent()) {
						IPlayerCap cap = DaBoisMod.get(lazyCap);
						if (cap.getQuests().contains(msg.quest) && msg.quest.isComplete()) {
							ItemStack reward = msg.quest.getReward();
							if (!player.addItemStackToInventory(reward)) player.dropItem(reward, false);
							player.giveExperiencePoints(msg.quest.getExperience());
							cap.getQuests().remove(msg.quest);
							player.addStat(DBMStats.complete_quest);
							DBMPacketHandler.sendToAllTrackingEntityAndSelf(new SUpdateQuestsPacket(cap.getQuests(), player.getEntityId()), player);
						}
					}
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
