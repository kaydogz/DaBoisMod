package com.github.kaydogz.daboismod.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AncientFruitItem extends Item {

	public AncientFruitItem(Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		/*ItemStack finishStack = super.onItemUseFinish(stack, worldIn, entityLiving);
		if (worldIn.dimension.getType().equals(DimensionType.OVERWORLD)) {
			DimensionType dimensionType = DimensionType.byName(DBMDimensionTypes.REALM_OF_THE_ANCIENTS_LOCATION);
			if (dimensionType != null) entityLiving.changeDimension(dimensionType, new DBMTeleporter(false));
		} else {
			entityLiving.addPotionEffect(new EffectInstance(Effects.POISON, 225));
		}
		return finishStack;*/
		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}
}
