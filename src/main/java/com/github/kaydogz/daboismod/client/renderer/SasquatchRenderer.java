package com.github.kaydogz.daboismod.client.renderer;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.client.model.SasquatchModel;
import com.github.kaydogz.daboismod.entity.SasquatchEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class SasquatchRenderer extends MobRenderer<SasquatchEntity, SasquatchModel> {

	private static final ResourceLocation SASQUATCH = DaBoisMod.modLocation("textures/entity/sasquatch.png");

	public SasquatchRenderer(EntityRendererManager renderManagerIn) {
		this(renderManagerIn, 1.0F);
	}
	
	public SasquatchRenderer(EntityRendererManager renderManagerIn, float modelSize) {
		super(renderManagerIn, new SasquatchModel(modelSize), modelSize);
	}
	
	@Override
	public void render(SasquatchEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		matrixStackIn.push();
    	matrixStackIn.scale(entityIn.getRenderScale(), entityIn.getRenderScale(), entityIn.getRenderScale());
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		matrixStackIn.pop();
	}

	@Override
	public ResourceLocation getEntityTexture(SasquatchEntity entity) {
		return SASQUATCH;
	}
}
