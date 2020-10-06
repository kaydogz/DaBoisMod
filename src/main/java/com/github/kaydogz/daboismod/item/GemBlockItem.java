package com.github.kaydogz.daboismod.item;

import com.github.kaydogz.daboismod.entity.FireImmuneItemEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GemBlockItem extends BlockItem {

	public GemBlockItem(Block blockIn, Properties builder) {
		super(blockIn, builder);
	}

	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		return true;
	}
	
	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
		if (world.isRemote) return super.createEntity(world, location, itemstack);
		FireImmuneItemEntity fireImmuneItem = new FireImmuneItemEntity(world, location.getPosX(), location.getPosY(), location.getPosZ(), itemstack);
		fireImmuneItem.setMotion(location.getMotion().x, location.getMotion().y, location.getMotion().z);
		fireImmuneItem.setDefaultPickupDelay();
		return fireImmuneItem;
	}
}
