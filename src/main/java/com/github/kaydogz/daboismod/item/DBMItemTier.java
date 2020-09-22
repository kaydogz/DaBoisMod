package com.github.kaydogz.daboismod.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum DBMItemTier implements IItemTier {
	ANCIENT(3, 2305, 11.0F, 4.5F, 9, () -> {
		return Ingredient.fromItems(DBMItems.ANCIENT_INGOT.get());
	}),
	CHANDLER(3, 3208, 13.0F, 4.7F, 9, () -> {
		return Ingredient.fromItems(Items.BEEF);
	});

	private final int harvestLevel;
	private final int maxUses;
	private final float efficiency;
	private final float attackDamage;
	private final int enchantability;
	private final LazyValue<Ingredient> repairMaterial;

	private DBMItemTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
		this.harvestLevel = harvestLevelIn;
	    this.maxUses = maxUsesIn;
	    this.efficiency = efficiencyIn;
	    this.attackDamage = attackDamageIn;
	    this.enchantability = enchantabilityIn;
	    this.repairMaterial = new LazyValue<>(repairMaterialIn);
	}

	public int getMaxUses() {
		return this.maxUses;
	}

	public float getEfficiency() {
	    return this.efficiency;
	}

	public float getAttackDamage() {
	    return this.attackDamage;
	}

	public int getHarvestLevel() {
	    return this.harvestLevel;
	}

	public int getEnchantability() {
	    return this.enchantability;
	 }

	 public Ingredient getRepairMaterial() {
	    return this.repairMaterial.getValue();
	 }

}
