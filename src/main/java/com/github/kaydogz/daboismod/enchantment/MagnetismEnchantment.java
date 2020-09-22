package com.github.kaydogz.daboismod.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;

public class MagnetismEnchantment extends Enchantment {

	public MagnetismEnchantment(Rarity rarity, EquipmentSlotType...slots) {
		super(rarity, EnchantmentType.DIGGER, slots);
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 20;
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return 50;
	}

	@Override
	public int getMaxLevel() {
		return 1; 
	}

	public static boolean isHoldingMagneticItem(PlayerEntity player) {
		ItemStack heldItem = player.getHeldItemMainhand();
		return heldItem.getItem() instanceof ToolItem && EnchantmentHelper.getEnchantments(heldItem).containsKey(DBMEnchantments.MAGNETISM.get());
	}
}
