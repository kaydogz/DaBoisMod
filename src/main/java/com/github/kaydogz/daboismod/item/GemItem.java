package com.github.kaydogz.daboismod.item;

import com.github.kaydogz.daboismod.entity.FireImmuneItemEntity;
import com.github.kaydogz.daboismod.event.DBMEventHooks;
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

public class GemItem extends Item {

	public GemItem(Item.Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		return true;
	}
	
	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
		if (world.isRemote) return super.createEntity(world, location, itemstack);
		FireImmuneItemEntity fireImmuneItem = new FireImmuneItemEntity(world, location.getPosX(), location.getPosY(), location.getPosZ(), itemstack);
		fireImmuneItem.setMotion(location.getMotion());
		fireImmuneItem.setDefaultPickupDelay();
		return fireImmuneItem;
	}
}
