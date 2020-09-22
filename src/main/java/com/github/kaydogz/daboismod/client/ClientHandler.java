package com.github.kaydogz.daboismod.client;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.block.DBMBlocks;
import com.github.kaydogz.daboismod.block.DBMWoodType;
import com.github.kaydogz.daboismod.block.trees.PadaukTree;
import com.github.kaydogz.daboismod.client.gui.DBMEditSignScreen;
import com.github.kaydogz.daboismod.client.gui.QuestScreen;
import com.github.kaydogz.daboismod.client.renderer.DBMRenderManager;
import com.github.kaydogz.daboismod.enchantment.MagnetismEnchantment;
import com.github.kaydogz.daboismod.item.CryptidGemItem;
import com.github.kaydogz.daboismod.item.DBMItems;
import com.github.kaydogz.daboismod.item.GodsCrownHelper;
import com.github.kaydogz.daboismod.item.GodsCrownItem;
import com.github.kaydogz.daboismod.network.DBMPacketHandler;
import com.github.kaydogz.daboismod.network.client.CToggleGodsCrownActivationPacket;
import com.github.kaydogz.daboismod.network.client.CUpdateMagneticPacket;
import com.github.kaydogz.daboismod.potion.DBMEffects;
import com.github.kaydogz.daboismod.tileentity.DBMSignTileEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.EditSignScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class ClientHandler {

    public static void onClientSetup(final FMLClientSetupEvent event) {

        DeferredWorkQueue.runLater(() -> {
            DBMWoodType.getCustomValues().forEach((woodType) -> Atlases.SIGN_MATERIALS.put(woodType, getSignMaterial(woodType)));
        });
        DBMKeyBindings.registerKeyBindings();
        DBMRenderManager.registerEntityRenderers();
        DBMRenderManager.bindTileEntityRenderers();
        DBMRenderManager.applyRenderLayers();
    }

    public static Material getSignMaterial(DBMWoodType woodType) {
        return new Material(Atlases.SIGN_ATLAS, DaBoisMod.modLocation("entity/signs/" + woodType.getName()));
    }

    @Mod.EventBusSubscriber(modid = DaBoisMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModEvents {

        @SubscribeEvent
        public static void preTextureStitch(final TextureStitchEvent.Pre event) {
            if (event.getMap().getTextureLocation().equals(Atlases.SIGN_ATLAS)) {
                DBMWoodType.getCustomValues().forEach((woodType) -> event.addSprite(DaBoisMod.modLocation("entity/signs/" + woodType.getName())));
            }
        }

        @SubscribeEvent
        public static void onBlockColorHandler(final ColorHandlerEvent.Block event) {
            event.getBlockColors().register((stateIn, worldIn, blockPosIn, valIn) -> {
                Color defaultColor = new Color((worldIn != null && blockPosIn != null) ? BiomeColors.getGrassColor(worldIn, blockPosIn) : GrassColors.get(1.0D, 1.0D));
                return new Color(MathHelper.clamp(defaultColor.getRed() + 131, 0, 255), MathHelper.clamp(defaultColor.getGreen() + 66, 0, 255), MathHelper.clamp(defaultColor.getBlue() + 21, 0, 255)).getRGB();
            }, DBMBlocks.BOTSWANIAN_GRASS_BLOCK.get(), DBMBlocks.BOTSWANIAN_GRASS.get(), DBMBlocks.BOTSWANIAN_TALL_GRASS.get());
            event.getBlockColors().register((stateIn, worldIn, blockPosIn, valIn) -> PadaukTree.getPadaukLeavesColor(), DBMBlocks.PADAUK_LEAVES.get());
        }

        @SubscribeEvent
        public static void onItemColorHandler(final ColorHandlerEvent.Item event) {
            event.getItemColors().register((stackIn, valIn) -> {
                return event.getBlockColors().getColor(((BlockItem) stackIn.getItem()).getBlock().getDefaultState(), null, null, valIn);
            }, DBMBlocks.BOTSWANIAN_GRASS_BLOCK.get(), DBMBlocks.BOTSWANIAN_GRASS.get(), DBMBlocks.BOTSWANIAN_TALL_GRASS.get());
            event.getItemColors().register((stackIn, valIn) -> {
                return event.getBlockColors().getColor(((BlockItem) stackIn.getItem()).getBlock().getDefaultState(), null, null, valIn);
            }, DBMItems.PADAUK_LEAVES.get());
        }
    }

    @Mod.EventBusSubscriber(modid = DaBoisMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(final InputEvent.KeyInputEvent event) {
            Minecraft minecraft = Minecraft.getInstance();

            if (minecraft.player != null) {
                InputMappings.Input keyInput = InputMappings.getInputByCode(event.getKey(), event.getScanCode());

                // Quest Menu
                if (event.getAction() != GLFW.GLFW_RELEASE && DBMKeyBindings.quest_menu.isActiveAndMatches(keyInput)) {
                    if (minecraft.currentScreen == null) minecraft.execute(() -> minecraft.displayGuiScreen(new QuestScreen()));
                    else if (minecraft.currentScreen instanceof ContainerScreen || minecraft.currentScreen instanceof QuestScreen) minecraft.execute(minecraft.player::closeScreen);
                }

                if (minecraft.currentScreen == null && event.getAction() == GLFW.GLFW_PRESS) {

                    // Magnetism
                    if (DBMKeyBindings.activate_magnetism.isActiveAndMatches(keyInput) && MagnetismEnchantment.isHoldingMagneticItem(minecraft.player)) {
                        DBMPacketHandler.sendToServer(new CUpdateMagneticPacket(true));
                    }

                    // Activate God's Crown
                    if (DBMKeyBindings.activate_gods_crown.isActiveAndMatches(keyInput)) {
                        DBMPacketHandler.sendToServer(new CToggleGodsCrownActivationPacket(true));
                    }
                }
            }
        }

        private static final ResourceLocation BLEEDING_VIGNETTE = DaBoisMod.modLocation("textures/gui/bleeding_vignette.png");

        @SubscribeEvent
        public static void preRenderGameOverlay(final RenderGameOverlayEvent.Pre event) {
            Minecraft minecraft = Minecraft.getInstance();

            // Bleeding Vignette
            if (minecraft.player != null && minecraft.player.isAlive() && minecraft.player.isPotionActive(DBMEffects.BLEEDING.get())) {
                RenderSystem.disableDepthTest();
                RenderSystem.enableBlend();
                RenderSystem.depthMask(false);
                RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                RenderSystem.color4f(255F / 255F, 15F / 255F, 15F / 255F, 255F / 255F);
                RenderSystem.disableAlphaTest();
                minecraft.getTextureManager().bindTexture(BLEEDING_VIGNETTE);
                MainWindow window = minecraft.getMainWindow();
                Tessellator tessellator = Tessellator.getInstance();
                tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
                tessellator.getBuffer().pos(0.0D, (double) window.getScaledHeight(), -90.0D).tex(0.0F, 1.0F).endVertex();
                tessellator.getBuffer().pos((double) window.getScaledWidth(), (double) window.getScaledHeight(), -90.0D).tex(1.0F, 1.0F).endVertex();
                tessellator.getBuffer().pos((double) window.getScaledWidth(), 0.0D, -90.0D).tex(1.0F, 0.0F).endVertex();
                tessellator.getBuffer().pos(0.0D, 0.0D, -90.0D).tex(0.0F, 0.0F).endVertex();
                tessellator.draw();
                RenderSystem.depthMask(true);
                RenderSystem.enableDepthTest();
                RenderSystem.enableAlphaTest();
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            }
        }

        @SubscribeEvent
        public static void onRenderWorldLast(final RenderWorldLastEvent event) {
            Minecraft minecraft = Minecraft.getInstance();

            // Activated Cryptid Gem World Rendering
            if (minecraft.player != null) {
                ItemStack helmetSlotStack = minecraft.player.getItemStackFromSlot(EquipmentSlotType.HEAD);
                if (helmetSlotStack.getItem() instanceof GodsCrownItem && GodsCrownHelper.isActivated(helmetSlotStack)) {
                    Item insertedItem = GodsCrownHelper.getInsertedGem(helmetSlotStack).getItem();
                    if (insertedItem instanceof CryptidGemItem) ((CryptidGemItem) insertedItem).onActivatedRenderWorldLast(
                            event.getContext(),
                            event.getMatrixStack(),
                            event.getPartialTicks(),
                            event.getProjectionMatrix(),
                            event.getFinishTimeNano()
                    );
                }
            }
        }

        @SubscribeEvent
        public static void preRenderPlayer(final RenderPlayerEvent.Pre event) {
            PlayerEntity player = event.getPlayer();

            // Pre Activated Cryptid Gem Player Rendering
            ItemStack helmetSlotStack = player.getItemStackFromSlot(EquipmentSlotType.HEAD);
            if (helmetSlotStack.getItem() instanceof GodsCrownItem && GodsCrownHelper.isActivated(helmetSlotStack)) {
                Item insertedItem = GodsCrownHelper.getInsertedGem(helmetSlotStack).getItem();
                if (insertedItem instanceof CryptidGemItem) event.setCanceled(((CryptidGemItem) insertedItem).preRenderActivatedPlayer(
                        event.getPlayer(),
                        event.getRenderer(),
                        event.getPartialRenderTick(),
                        event.getMatrixStack(),
                        event.getBuffers(),
                        event.getLight()
                ));
            }
        }

        @SubscribeEvent
        public static void postRenderPlayer(final RenderPlayerEvent.Post event) {
            PlayerEntity player = event.getPlayer();

            // Post Activated Cryptid Gem Player Rendering
            ItemStack helmetSlotStack = player.getItemStackFromSlot(EquipmentSlotType.HEAD);
            if (helmetSlotStack.getItem() instanceof GodsCrownItem && GodsCrownHelper.isActivated(helmetSlotStack)) {
                Item insertedItem = GodsCrownHelper.getInsertedGem(helmetSlotStack).getItem();
                if (insertedItem instanceof CryptidGemItem) ((CryptidGemItem) insertedItem).postRenderActivatedPlayer(
                        event.getPlayer(),
                        event.getRenderer(),
                        event.getPartialRenderTick(),
                        event.getMatrixStack(),
                        event.getBuffers(),
                        event.getLight()
                );
            }
        }

        @SubscribeEvent
        public static void onGuiOpen(final GuiOpenEvent event) {

            // Manages Custom Sign Screen
            if (event.getGui() instanceof EditSignScreen) {
                SignTileEntity tileEntity = ((EditSignScreen) event.getGui()).tileSign;
                if (tileEntity instanceof DBMSignTileEntity) event.setGui(new DBMEditSignScreen(tileEntity));
            }
        }
    }
}
