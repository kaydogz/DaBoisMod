package com.github.kaydogz.daboismod.client.renderer;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.client.model.WerewolfModel;
import com.github.kaydogz.daboismod.entity.WerewolfEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class WerewolfRenderer extends MobRenderer<WerewolfEntity, WerewolfModel> {

	private static final ResourceLocation WEREWOLF = DaBoisMod.modLocation("textures/entity/werewolf.png");
	private static final ResourceLocation WEREWOLF_MOLTEN = DaBoisMod.modLocation("textures/entity/werewolf_molten.png");
	
	public WerewolfRenderer(EntityRendererManager rendererManager) {
		super(rendererManager, new WerewolfModel(), 1.0F);
	}
	
	@Override
	public ResourceLocation getEntityTexture(WerewolfEntity entity) {
		return entity.isMolten() ? WerewolfRenderer.WEREWOLF_MOLTEN : WerewolfRenderer.WEREWOLF;
	}
}
