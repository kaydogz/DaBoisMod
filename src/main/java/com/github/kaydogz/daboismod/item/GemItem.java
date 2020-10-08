package com.github.kaydogz.daboismod.item;

import com.github.kaydogz.daboismod.entity.FireImmuneItemEntity;
import net.minecraft.command.impl.GiveCommand;
import net.minecraft.entity.Entity;
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
		FireImmuneItemEntity fireImmuneItem = new FireImmuneItemEntity(world, location.getPosX(), location.getPosY(), location.getPosZ(), itemstack);
		fireImmuneItem.setMotion(location.getMotion());
		fireImmuneItem.setDefaultPickupDelay(); // TODO FIX THIS AND IN GEMBLOCKITEM
		return fireImmuneItem;
	}
}
