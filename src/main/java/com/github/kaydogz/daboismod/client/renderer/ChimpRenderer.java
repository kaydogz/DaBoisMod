package com.github.kaydogz.daboismod.client.renderer;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.client.model.ChimpModel;
import com.github.kaydogz.daboismod.entity.AbstractChimpEntity;
import com.github.kaydogz.daboismod.entity.AggressiveChimpEntity;
import com.github.kaydogz.daboismod.entity.ChimpEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class ChimpRenderer extends MobRenderer<AbstractChimpEntity, ChimpModel> {

	private static final ResourceLocation CHIMP = DaBoisMod.modLocation("textures/entity/chimp.png");
	private static final ResourceLocation AGGRESSIVE_CHIMP = DaBoisMod.modLocation("textures/entity/aggressive_chimp.png");

	public ChimpRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new ChimpModel(), 0.4F);
	}

	@Override
	public ResourceLocation getEntityTexture(AbstractChimpEntity entity) {
		return entity instanceof AggressiveChimpEntity ? AGGRESSIVE_CHIMP : CHIMP;
	}

	@Override
	public void render(AbstractChimpEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		matrixStackIn.push();
		matrixStackIn.translate(0.0D, 0.35D * entityIn.getRenderScale(), 0.0D);
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		matrixStackIn.pop();
	}
}
