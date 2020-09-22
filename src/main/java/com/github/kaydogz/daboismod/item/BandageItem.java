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
		if (playerIn.getHealth() < playerIn.getMaxHealth() || playerIn.abilities.isCreativeMode) {
			ItemStack itemstack = playerIn.getHeldItem(handIn);
			playerIn.setActiveHand(handIn);
			playerIn.heal(this.getHealAmount(itemstack));
			playerIn.removePotionEffect(DBMEffects.BLEEDING.get());
			ActionResult<ItemStack> result = ActionResult.resultSuccess(itemstack.copy());
			if (!playerIn.abilities.isCreativeMode) itemstack.shrink(1);
            return result;
		} else {
			return ActionResult.resultPass(playerIn.getHeldItem(handIn));
		}
	}

	public float getHealAmount(ItemStack stackIn) {
		return stackIn.getItem() instanceof BandageItem ? 1.0F : 0.0F;
	}
}
