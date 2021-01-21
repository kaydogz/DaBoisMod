package com.github.kaydogz.daboismod.client;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.provider.LivingProvider;
import com.github.kaydogz.daboismod.capability.provider.PlayerProvider;
import com.github.kaydogz.daboismod.capability.provider.VillagerProvider;
import com.github.kaydogz.daboismod.client.audio.MagnetismTickableSound;
import com.github.kaydogz.daboismod.client.gui.QuestScreen;
import com.github.kaydogz.daboismod.quest.Quest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ClientPacketHandler {

    public static void handleUpdateMagnetic(Supplier<NetworkEvent.Context> ctx, boolean magnetic, int playerId) {
        Minecraft minecraft = Minecraft.getInstance();
        Entity entity = minecraft.world.getEntityByID(playerId);
        if (entity instanceof AbstractClientPlayerEntity) {
            DaBoisMod.get(PlayerProvider.getCapabilityOf(entity)).setMagnetic(magnetic);
            if (magnetic) minecraft.getSoundHandler().playOnNextTick(new MagnetismTickableSound((AbstractClientPlayerEntity) entity));
        }
    }

    public static void handleUpdateQuests(Supplier<NetworkEvent.Context> ctx, ArrayList<Quest> quests, int playerId) {
        Minecraft minecraft = Minecraft.getInstance();
        Entity entity = minecraft.world.getEntityByID(playerId);
        if (entity instanceof AbstractClientPlayerEntity) {
            DaBoisMod.get(PlayerProvider.getCapabilityOf(entity)).setQuests(quests);
            if (entity instanceof ClientPlayerEntity && minecraft.currentScreen instanceof QuestScreen) {
                ((QuestScreen) minecraft.currentScreen).updateQuests();
            }
        }
    }

    public static void handleUpdateRealmFalling(Supplier<NetworkEvent.Context> ctx, boolean realmFalling, int entityId) {
        Entity entity = Minecraft.getInstance().world.getEntityByID(entityId);
        if (entity instanceof LivingEntity) DaBoisMod.get(LivingProvider.getCapabilityOf(entity)).setRealmFalling(realmFalling);
    }

    public static void handleDisplayItemActivation(Supplier<NetworkEvent.Context> ctx, ItemStack stack) {
        Minecraft.getInstance().gameRenderer.displayItemActivation(stack);
    }

    public static void handleSasquatchSmash(Supplier<NetworkEvent.Context> ctx, double posX, double posY, double posZ) {
        double particleSpeedRadius = 0.5D;
        for (int i = 0; i < 16; i++) {
            Minecraft.getInstance().world.addParticle(ParticleTypes.CLOUD, posX, posY, posZ, particleSpeedRadius * Math.cos(i * Math.PI / 8), 0.1D, particleSpeedRadius * Math.sin(i * Math.PI / 8));
        }
    }

    public static void handleUpdateSkinTone(Supplier<NetworkEvent.Context> ctx, int skinTone, int entityId) {
        Entity entity = Minecraft.getInstance().world.getEntityByID(entityId);
        if (entity instanceof VillagerEntity) DaBoisMod.get(VillagerProvider.getCapabilityOf(entity)).setSkinTone(skinTone);
    }
}
