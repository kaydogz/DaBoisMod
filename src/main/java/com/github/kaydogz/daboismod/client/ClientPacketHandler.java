package com.github.kaydogz.daboismod.client;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.provider.LivingCapability;
import com.github.kaydogz.daboismod.capability.provider.PlayerCapability;
import com.github.kaydogz.daboismod.client.audio.MagnetismTickableSound;
import com.github.kaydogz.daboismod.client.gui.QuestScreen;
import com.github.kaydogz.daboismod.network.server.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientPacketHandler {

    public static void handleUpdateMagnetic(final SUpdateMagneticPacket msg, Supplier<NetworkEvent.Context> ctx) {
        Minecraft minecraft = Minecraft.getInstance();
        Entity entity = minecraft.world.getEntityByID(msg.playerId);
        if (entity instanceof AbstractClientPlayerEntity) {
            DaBoisMod.get(PlayerCapability.getCapabilityOf(entity)).setMagnetic(msg.magnetic);
            minecraft.getSoundHandler().playOnNextTick(new MagnetismTickableSound((AbstractClientPlayerEntity) entity));
        }
    }

    public static void handleUpdateQuests(final SUpdateQuestsPacket msg, Supplier<NetworkEvent.Context> ctx) {
        Minecraft minecraft = Minecraft.getInstance();
        Entity entity = minecraft.world.getEntityByID(msg.playerId);
        if (entity instanceof AbstractClientPlayerEntity) {
            DaBoisMod.get(PlayerCapability.getCapabilityOf(entity)).setQuests(msg.quests);
            if (entity instanceof ClientPlayerEntity && minecraft.currentScreen instanceof QuestScreen) {
                QuestScreen screen = (QuestScreen) minecraft.currentScreen;
                screen.updateQuests();
            }
        }
    }

    public static void handleUpdateFallingFromSky(final SUpdateFallingFromSkyPacket msg, Supplier<NetworkEvent.Context> ctx) {
        Entity entity = Minecraft.getInstance().world.getEntityByID(msg.entityId);
        if (entity != null) DaBoisMod.get(LivingCapability.getCapabilityOf(entity)).setFallingFromSky(msg.fallingFromSky);
    }

    public static void handleSasquatchSmashLaunch(final SSasquatchSmashLaunchPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ClientPlayerEntity player = Minecraft.getInstance().player;

        if (player != null) {
            if (msg.shouldLaunch) player.setMotion(player.getMotion().add(0.0D, 1.5D, 0.0D));
            double particleSpeedRadius = 0.5D;
            for (int i = 0; i < 16; i++)
                player.world.addParticle(
                        ParticleTypes.CLOUD,
                        msg.position.getX(),
                        msg.position.getY(),
                        msg.position.getZ(),
                        particleSpeedRadius * Math.cos(i * Math.PI / 8),
                        0.1D, particleSpeedRadius * Math.sin(i * Math.PI / 8)
                );
        }
    }

    public static void handlePlaySound(final SPlaySoundPacket msg, Supplier<NetworkEvent.Context> ctx) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientPlayerEntity player = minecraft.player;
        if (player != null) {
            if (msg.global) {
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

                    player.worldClient.playSound(d4, d5, d6, msg.sound, SoundCategory.MASTER, 1.0F, 1.0F, false);
                }
            }
            else player.playSound(msg.sound, 1.0F, 1.0F);
        }
    }

    public static void handleDisplayItemActivation(final SDisplayItemActivationPacket msg, Supplier<NetworkEvent.Context> ctx) {
        Minecraft.getInstance().gameRenderer.displayItemActivation(msg.stack);
    }
}
