package com.github.kaydogz.daboismod.client;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.provider.LivingProvider;
import com.github.kaydogz.daboismod.capability.provider.PlayerProvider;
import com.github.kaydogz.daboismod.client.audio.MagnetismTickableSound;
import com.github.kaydogz.daboismod.client.gui.QuestScreen;
import com.github.kaydogz.daboismod.quest.Quest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
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

    public static void handleDisplayItemActivation(final ItemStack stack, Supplier<NetworkEvent.Context> ctx) {
        Minecraft.getInstance().gameRenderer.displayItemActivation(stack);
    }

    public static void handleSasquatchSmash(double posX, double posY, double posZ, Supplier<NetworkEvent.Context> ctx) {
        double particleSpeedRadius = 0.5D;
        for (int i = 0; i < 16; i++) {
            Minecraft.getInstance().world.addParticle(ParticleTypes.CLOUD, posX, posY, posZ, particleSpeedRadius * Math.cos(i * Math.PI / 8), 0.1D, particleSpeedRadius * Math.sin(i * Math.PI / 8));
        }
    }
}
