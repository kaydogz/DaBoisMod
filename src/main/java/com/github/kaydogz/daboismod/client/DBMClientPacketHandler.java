package com.github.kaydogz.daboismod.client;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.ICrownCapability;
import com.github.kaydogz.daboismod.capability.provider.CrownProvider;
import com.github.kaydogz.daboismod.capability.provider.LivingProvider;
import com.github.kaydogz.daboismod.capability.provider.PlayerProvider;
import com.github.kaydogz.daboismod.client.audio.MagnetismTickableSound;
import com.github.kaydogz.daboismod.client.gui.QuestScreen;
import com.github.kaydogz.daboismod.item.GemItem;
import com.github.kaydogz.daboismod.item.CrownItem;
import com.github.kaydogz.daboismod.quest.Quest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

public class DBMClientPacketHandler {

    public static void handleUpdateMagnetic(final boolean magnetic, final int playerId, Supplier<NetworkEvent.Context> ctx) {
        Minecraft minecraft = Minecraft.getInstance();
        Entity entity = minecraft.world.getEntityByID(playerId);
        if (entity instanceof AbstractClientPlayerEntity) {
            DaBoisMod.get(PlayerProvider.getCapabilityOf(entity)).setMagnetic(magnetic);
            minecraft.getSoundHandler().playOnNextTick(new MagnetismTickableSound((AbstractClientPlayerEntity) entity));
        }
    }

    public static void handleUpdateQuests(final ArrayList<Quest> quests, final int playerId, Supplier<NetworkEvent.Context> ctx) {
        Minecraft minecraft = Minecraft.getInstance();
        Entity entity = minecraft.world.getEntityByID(playerId);
        if (entity instanceof AbstractClientPlayerEntity) {
            DaBoisMod.get(PlayerProvider.getCapabilityOf(entity)).setQuests(quests);
            if (entity instanceof ClientPlayerEntity && minecraft.currentScreen instanceof QuestScreen) {
                ((QuestScreen) minecraft.currentScreen).updateQuests();
            }
        }
    }

    public static void handleUpdateFallingFromSky(final boolean fallingFromSky, final int entityId, Supplier<NetworkEvent.Context> ctx) {
        Entity entity = Minecraft.getInstance().world.getEntityByID(entityId);
        if (entity != null) DaBoisMod.get(LivingProvider.getCapabilityOf(entity)).setFallingFromSky(fallingFromSky);
    }

    public static void handlePlaySound(final SoundEvent sound, final boolean global, Supplier<NetworkEvent.Context> ctx) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientPlayerEntity player = minecraft.player;
        if (player != null) {
            if (global) {
                ActiveRenderInfo renderInfo = minecraft.gameRenderer.getActiveRenderInfo();
                if (minecraft.gameRenderer.getActiveRenderInfo().isValid()) {
                    double d0 = (double) player.getPosition().getX() - renderInfo.getProjectedView().x;
                    double d1 = (double) player.getPosition().getY() - renderInfo.getProjectedView().y;
                    double d2 = (double) player.getPosition().getZ() - renderInfo.getProjectedView().z;
                    double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                    double d4 = renderInfo.getProjectedView().x;
                    double d5 = renderInfo.getProjectedView().y;
                    double d6 = renderInfo.getProjectedView().z;
                    if (d3 > 0.0D) {
                        d4 += d0 / d3 * 2.0D;
                        d5 += d1 / d3 * 2.0D;
                        d6 += d2 / d3 * 2.0D;
                    }

                    player.worldClient.playSound(d4, d5, d6, sound, SoundCategory.MASTER, 1.0F, 1.0F, false);
                }
            }
            else player.playSound(sound, 1.0F, 1.0F);
        }
    }

    public static void handleDisplayItemActivation(final ItemStack stack, Supplier<NetworkEvent.Context> ctx) {
        Minecraft.getInstance().gameRenderer.displayItemActivation(stack);
    }
}
