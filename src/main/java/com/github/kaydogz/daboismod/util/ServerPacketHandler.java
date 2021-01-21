package com.github.kaydogz.daboismod.util;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.IPlayerCapability;
import com.github.kaydogz.daboismod.capability.provider.PlayerProvider;
import com.github.kaydogz.daboismod.enchantment.MagnetismEnchantment;
import com.github.kaydogz.daboismod.item.CrownHelper;
import com.github.kaydogz.daboismod.network.DBMNetworkHandler;
import com.github.kaydogz.daboismod.network.server.SUpdateMagneticPacket;
import com.github.kaydogz.daboismod.network.server.SUpdateQuestsPacket;
import com.github.kaydogz.daboismod.quest.Quest;
import com.github.kaydogz.daboismod.stats.DBMStats;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerPacketHandler {

    public static void handleCancelQuest(Supplier<NetworkEvent.Context> ctx, Quest quest) {
        ServerPlayerEntity player = ctx.get().getSender();
        if (player != null) {
            LazyOptional<IPlayerCapability> lazyCap = PlayerProvider.getCapabilityOf(player);
            if (lazyCap.isPresent()) {
                IPlayerCapability cap = DaBoisMod.get(lazyCap);
                if (!quest.isComplete() && cap.getQuests().remove(quest)) {
                    DBMNetworkHandler.sendToAllTrackingEntityAndSelf(new SUpdateQuestsPacket(cap.getQuests(), player.getEntityId()), player);
                }
            }
        }
    }

    public static void handleClaimQuest(Supplier<NetworkEvent.Context> ctx, Quest quest) {
        ServerPlayerEntity player = ctx.get().getSender();
        if (player != null) {
            LazyOptional<IPlayerCapability> lazyCap = PlayerProvider.getCapabilityOf(player);
            if (lazyCap.isPresent()) {
                IPlayerCapability cap = DaBoisMod.get(lazyCap);
                if (cap.getQuests().contains(quest) && quest.isComplete()) {
                    ItemStack reward = quest.getReward();
                    if (!player.addItemStackToInventory(reward)) player.dropItem(reward, false);
                    player.giveExperiencePoints(quest.getExperience());
                    cap.getQuests().remove(quest);
                    player.addStat(DBMStats.complete_quest);
                    DBMNetworkHandler.sendToAllTrackingEntityAndSelf(new SUpdateQuestsPacket(cap.getQuests(), player.getEntityId()), player);
                }
            }
        }
    }

    public static void handleToggleCrownActivation(Supplier<NetworkEvent.Context> ctx, boolean sendMessages) {
        ServerPlayerEntity player = ctx.get().getSender();
        if (player != null) CrownHelper.toggleActivation(player, sendMessages);
    }

    public static void handleUpdateMagnetic(Supplier<NetworkEvent.Context> ctx, boolean magnetic) {
        ServerPlayerEntity player = ctx.get().getSender();

        // Make sure request is valid before updating
        if (!magnetic || MagnetismEnchantment.isHoldingMagneticItem(player)) {
            DaBoisMod.get(PlayerProvider.getCapabilityOf(player)).setMagnetic(magnetic);
            DBMNetworkHandler.sendToAllTrackingEntityAndSelf(new SUpdateMagneticPacket(magnetic, player.getEntityId()), player);
        }
    }
}
