package com.github.kaydogz.daboismod.client.renderer;

import com.github.kaydogz.daboismod.block.DBMBlocks;
import com.github.kaydogz.daboismod.entity.DBMEntities;
import com.github.kaydogz.daboismod.tileentity.DBMTileEntities;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class DBMRenderManager {

	public static void registerEntityRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityType.VILLAGER, DBMVillagerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(DBMEntities.SASQUATCH.get(), SasquatchRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(DBMEntities.WEREWOLF.get(), WerewolfRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(DBMEntities.FLESH_CREEPER.get(), FleshCreeperRenderer::new);
	}

	public static void bindTileEntityRenderers() {
		ClientRegistry.bindTileEntityRenderer(DBMTileEntities.CUSTOM_SIGN.get(), DBMSignTileEntityRenderer::new);
	}
	
	public static void applyRenderLayers() {
		final RenderType cutoutType = RenderType.getCutout();
		RenderTypeLookup.setRenderLayer(DBMBlocks.CANNABIS.get(), cutoutType);
		RenderTypeLookup.setRenderLayer(DBMBlocks.BOTSWANIAN_GRASS.get(), cutoutType);
		RenderTypeLookup.setRenderLayer(DBMBlocks.BOTSWANIAN_TALL_GRASS.get(), cutoutType);
		RenderTypeLookup.setRenderLayer(DBMBlocks.PADAUK_SAPLING.get(), cutoutType);
		RenderTypeLookup.setRenderLayer(DBMBlocks.PADAUK_TRAPDOOR.get(), cutoutType);
		RenderTypeLookup.setRenderLayer(DBMBlocks.PADAUK_DOOR.get(), cutoutType);
		RenderTypeLookup.setRenderLayer(DBMBlocks.POTTED_PADAUK_SAPLING.get(), cutoutType);
		final RenderType cutoutMippedType = RenderType.getCutoutMipped();
		RenderTypeLookup.setRenderLayer(DBMBlocks.PADAUK_LEAVES.get(), cutoutMippedType);
		RenderTypeLookup.setRenderLayer(DBMBlocks.BOTSWANIAN_GRASS_BLOCK.get(), cutoutMippedType);
	}
}
