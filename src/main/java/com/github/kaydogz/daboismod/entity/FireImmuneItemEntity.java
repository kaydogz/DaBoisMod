package com.github.kaydogz.daboismod.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class FireImmuneItemEntity extends ItemEntity {

	public FireImmuneItemEntity(EntityType<? extends FireImmuneItemEntity> typeIn, World worldIn) {
		super(typeIn, worldIn);
	}

	public FireImmuneItemEntity(World worldIn, double x, double y, double z, ItemStack stack) {
		super(worldIn, x, y, z, stack);
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return !source.isFireDamage() && super.attackEntityFrom(source, amount);
	}
}
