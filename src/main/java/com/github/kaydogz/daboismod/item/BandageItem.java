package com.github.kaydogz.daboismod.item;

import com.github.kaydogz.daboismod.potion.DBMEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BandageItem extends Item {
	
	public BandageItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (playerIn.shouldHeal()) {
			ItemStack itemstack = playerIn.getHeldItem(handIn);
			playerIn.setActiveHand(handIn);
			playerIn.heal(this.getHealAmount(itemstack));
			playerIn.removePotionEffect(DBMEffects.BLEEDING.get());
			if (!playerIn.abilities.isCreativeMode) {
				itemstack.shrink(1);
			}
            return ActionResult.resultSuccess(itemstack);
		} else {
			return ActionResult.resultPass(playerIn.getHeldItem(handIn));
		}
	}

	public float getHealAmount(ItemStack stackIn) {
		return 1.0F;
	}
}
