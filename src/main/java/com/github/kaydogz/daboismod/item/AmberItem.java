package com.github.kaydogz.daboismod.item;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BeaconBlock;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.HealthBoostEffect;

public class AmberItem extends CryptidGemItem {

	public AmberItem(Properties properties) {
		super(properties);
	}

	@Override
	public void onActivation(ItemStack stackIn, PlayerEntity playerIn) {
		playerIn.recalculateSize();
		playerIn.abilities.setWalkSpeed(playerIn.abilities.getWalkSpeed() * 5.0F);
		playerIn.abilities.setFlySpeed(playerIn.abilities.getFlySpeed() * 5.0F);
		// TODO: Maybe rework gods crowns and make each gem have a seperate crown item, fix launching for squatches, and fix speed/jump height for amber activators
	}
	
	@Override
	public void onDeactivation(ItemStack stackIn, PlayerEntity playerIn) {
		playerIn.recalculateSize();
		playerIn.abilities.setWalkSpeed(playerIn.abilities.getWalkSpeed() / 5.0F);
		playerIn.abilities.setFlySpeed(playerIn.abilities.getFlySpeed() / 5.0F);
	}

	@Override
	public boolean preRenderActivatedPlayer(ItemStack stackIn, PlayerEntity playerIn, PlayerRenderer rendererIn, float partialRenderTickIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int lightIn) {
		matrixStackIn.push();
		float scale = this.getGiantScale(stackIn, playerIn);
		matrixStackIn.scale(scale, scale, scale);
		return false;
	}

	@Override
	public void postRenderActivatedPlayer(ItemStack stackIn, PlayerEntity playerIn, PlayerRenderer rendererIn, float partialRenderTickIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int lightIn) {
		matrixStackIn.pop();
	}

	@Override
	public float onActivatedPlayerEyeHeight(ItemStack stackIn, PlayerEntity playerIn, Pose poseIn, EntitySize sizeIn, float oldHeight) {
		this.scaleSize(stackIn, playerIn);
		return oldHeight * this.getGiantScale(stackIn, playerIn);
	}

	public float getGiantScale(ItemStack stackIn, PlayerEntity playerIn) {
		return 5.0F;
	}

	protected void scaleSize(ItemStack stackIn, PlayerEntity playerIn) {
		EntitySize originalSize = playerIn.getSize(playerIn.getPose());
		float scale = this.getGiantScale(stackIn, playerIn);
		EntitySize newSize = EntitySize.flexible(originalSize.width * scale, originalSize.height * scale);
		playerIn.size = newSize;

		playerIn.setBoundingBox(playerIn.getBoundingBox().expand(
				-newSize.width / 2,
				0,
				-newSize.width / 2
		).expand(
				newSize.width / 2,
				newSize.height,
				newSize.width / 2
		));
	}
}