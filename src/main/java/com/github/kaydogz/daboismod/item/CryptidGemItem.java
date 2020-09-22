package com.github.kaydogz.daboismod.item;

import com.github.kaydogz.daboismod.entity.FireImmuneItemEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class CryptidGemItem extends Item {

	public CryptidGemItem(Item.Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		return true;
	}
	
	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
		FireImmuneItemEntity fireImmuneItem = new FireImmuneItemEntity(world, location.getPosX(), location.getPosY(), location.getPosZ(), itemstack);
		fireImmuneItem.setMotion(location.getMotion());
		fireImmuneItem.setDefaultPickupDelay();
		return fireImmuneItem;
	}

	/**
	 * Fired when a God's Crown with this gem activates.
	 * @param stackIn the God's Crown {@link ItemStack}.
	 * @param playerIn the player who activated the crown.
	 */
	public void onActivation(ItemStack stackIn, PlayerEntity playerIn) {}

	/**
	 * Fired when a God's Crown with this gem deactivates.
	 * @param stackIn the God's Crown {@link ItemStack}.
	 * @param playerIn the player who deactivated the crown.
	 */
	public void onDeactivation(ItemStack stackIn, PlayerEntity playerIn) {}

	/**
	 * Fired every tick a player has an activated God's Crown with this gem equipped.
	 * @param stackIn the God's Crown {@link ItemStack}.
	 * @param playerIn the player.
	 */
	public void activatedTick(ItemStack stackIn, PlayerEntity playerIn) {}

	/**
	 * Fired before a player with an activated God's Crown with this gem renders.
	 * @param playerIn the player.
	 * @param rendererIn the player's renderer.
	 * @param partialRenderTickIn the partial render tick.
	 * @param matrixStackIn the {@link MatrixStack} used for rendering.
	 * @param bufferIn the {@link IRenderTypeBuffer} used for rendering.
	 * @param lightIn the amount of light.
	 * @return if the rendering should be canceled.
	 */
	public boolean preRenderActivatedPlayer(PlayerEntity playerIn, PlayerRenderer rendererIn, float partialRenderTickIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int lightIn) {
		return false;
	}

	/**
	 * Fired after a player with an activated God's Crown with this gem renders.
	 * @param playerIn the player.
	 * @param rendererIn the player's renderer.
	 * @param partialRenderTickIn the partial render tick.
	 * @param matrixStackIn the {@link MatrixStack} used for rendering.
	 * @param bufferIn the {@link IRenderTypeBuffer} used for rendering.
	 * @param lightIn the amount of light.
	 */
	public void postRenderActivatedPlayer(PlayerEntity playerIn, PlayerRenderer rendererIn, float partialRenderTickIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int lightIn) {}

	/**
	 * Fired whenever a player's eye height changes or adjusts.
	 * @param playerIn the player.
	 * @param poseIn the player's pose.
	 * @param sizeIn the size of the player.
	 * @param oldHeight the previous eye height of the player.
	 * @return what the new height should be.
	 */
	public float onActivatedPlayerEyeHeight(PlayerEntity playerIn, Pose poseIn, EntitySize sizeIn, float oldHeight) {
		return oldHeight;
	}

	/**
	 * Fired when the world renders.
	 * @param contextIn the world renderer.
	 * @param matrixStackIn the {@link MatrixStack} used for rendering.
	 * @param partialTicksIn the partial ticks used for rendering.
	 * @param projectionMatrixIn the projection {@link Matrix4f}.
	 * @param finishTimeNanoIn the finish time nano.
	 */
	public void onActivatedRenderWorldLast(WorldRenderer contextIn, MatrixStack matrixStackIn, float partialTicksIn, Matrix4f projectionMatrixIn, long finishTimeNanoIn) {}
}
