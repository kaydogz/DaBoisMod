package com.github.kaydogz.daboismod.network.client;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.IPlayerCapability;
import com.github.kaydogz.daboismod.capability.provider.PlayerProvider;
import com.github.kaydogz.daboismod.network.DBMPacketHandler;
import com.github.kaydogz.daboismod.network.server.SUpdateQuestsPacket;
import com.github.kaydogz.daboismod.quest.Quest;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
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
			ctx.get().enqueueWork(() -> {
				ServerPlayerEntity player = ctx.get().getSender();
				if (player != null) {
					LazyOptional<IPlayerCapability> lazyCap = PlayerProvider.getCapabilityOf(player);
					if (lazyCap.isPresent()) {
						IPlayerCapability cap = DaBoisMod.get(lazyCap);
						if (!msg.quest.isComplete() && cap.getQuests().remove(msg.quest)) {
							DBMPacketHandler.sendToAllTrackingEntityAndSelf(new SUpdateQuestsPacket(cap.getQuests(), player.getEntityId()), player);
						}
					}
				}
			});
			ctx.get().setPacketHandled(true);
		}
	}
}
