package com.github.kaydogz.daboismod.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;

public class AmberItem extends CryptidGemItem {

	public final float giantScale = 5.0F;
	
	public AmberItem(Properties properties) {
		super(properties);
	}

	@Override
	public void onActivation(ItemStack stackIn, PlayerEntity playerIn) {
		playerIn.recalculateSize();
		this.scaleSize(playerIn);
	}
	
	@Override
	public void onDeactivation(ItemStack stackIn, PlayerEntity playerIn) {
		playerIn.recalculateSize();
	}

	@Override
	public boolean preRenderActivatedPlayer(PlayerEntity playerIn, PlayerRenderer rendererIn, float partialRenderTickIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int lightIn) {
		matrixStackIn.push();
		matrixStackIn.scale(this.giantScale, this.giantScale, this.giantScale);
		return false;
	}

	@Override
	public void postRenderActivatedPlayer(PlayerEntity playerIn, PlayerRenderer rendererIn, float partialRenderTickIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int lightIn) {
		matrixStackIn.pop();
	}

	@Override
	public float onActivatedPlayerEyeHeight(PlayerEntity playerIn, Pose poseIn, EntitySize sizeIn, float oldHeight) {
		this.scaleSize(playerIn);
		return oldHeight * this.giantScale;
	}

	protected void scaleSize(PlayerEntity playerIn) {
		playerIn.size = EntitySize.flexible(playerIn.getSize(playerIn.getPose()).width * this.giantScale, playerIn.getSize(playerIn.getPose()).height * this.giantScale);
		playerIn.setBoundingBox(new AxisAlignedBB(
				playerIn.getPosX() - playerIn.getSize(playerIn.getPose()).width / 2 * this.giantScale, 	// Min X
				playerIn.getPosY(),																			// Min Y
				playerIn.getPosZ() - playerIn.getSize(playerIn.getPose()).width / 2 * this.giantScale,	// Min Z
				playerIn.getPosX() + playerIn.getSize(playerIn.getPose()).width / 2 * this.giantScale,	// Max X
				playerIn.getPosY() + playerIn.getSize(playerIn.getPose()).height * this.giantScale,		// Max Y
				playerIn.getPosZ() + playerIn.getSize(playerIn.getPose()).width / 2 * this.giantScale	// Max Z
		));
	}
}