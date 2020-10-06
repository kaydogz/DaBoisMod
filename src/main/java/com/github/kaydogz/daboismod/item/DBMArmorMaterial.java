package com.github.kaydogz.daboismod.item;

import com.github.kaydogz.daboismod.DaBoisMod;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import java.util.function.Supplier;

public enum DBMArmorMaterial implements IArmorMaterial {
	GOD(DaBoisMod.modLocation("god").toString(), 35, new int[]{2, 5, 7, 2}, 8, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.1F, () -> {
		return Ingredient.fromItems(Items.ENCHANTED_GOLDEN_APPLE);
	}),
	AMBER_CROWN(DaBoisMod.modLocation("amber_crown").toString(), 35, new int[]{2, 5, 7, 2}, 8, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.1F, () -> {
		return Ingredient.fromItems(DBMItems.AMBER.get());
	}),
	TOPAZ_CROWN(DaBoisMod.modLocation("topaz_crown").toString(), 35, new int[]{2, 5, 7, 2}, 8, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.1F, () -> {
		return Ingredient.fromItems(DBMItems.TOPAZ.get());
	}),
	RUBY_CROWN(DaBoisMod.modLocation("ruby_crown").toString(), 35, new int[]{2, 5, 7, 2}, 8, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.1F, () -> {
		return Ingredient.fromItems(DBMItems.RUBY.get());
	}),
	AMETHYST_CROWN(DaBoisMod.modLocation("amethyst_crown").toString(), 35, new int[]{2, 5, 7, 2}, 8, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2.1F, () -> {
		return Ingredient.fromItems(DBMItems.AMETHYST.get());
	}),
	ANCIENT(DaBoisMod.modLocation("ancient").toString(), 40, new int[]{5, 8, 11, 5}, 9, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.2F, () -> {
		return Ingredient.fromItems(DBMItems.ANCIENT_INGOT.get());
	});

	private static final int[] MAX_DAMAGE_ARRAY = new int[] {13, 15, 16, 11};
	private final String name;
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final SoundEvent soundEvent;
	private final float toughness;
	private final LazyValue<Ingredient> repairMaterial;

	DBMArmorMaterial(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountsIn, int enchantabilityIn, SoundEvent equipSoundIn, float toughness, Supplier<Ingredient> repairMaterialSupplier) {
		this.name = nameIn;
	    this.maxDamageFactor = maxDamageFactorIn;
	    this.damageReductionAmountArray = damageReductionAmountsIn;
	    this.enchantability = enchantabilityIn;
	    this.soundEvent = equipSoundIn;
	    this.toughness = toughness;
	    this.repairMaterial = new LazyValue<>(repairMaterialSupplier);
	}
	
	@Override
	public int getDurability(EquipmentSlotType slotIn) {
	    return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
	}

	@Override
	public int getDamageReductionAmount(EquipmentSlotType slotIn) {
	    return this.damageReductionAmountArray[slotIn.getIndex()];
	}

	@Override
	public int getEnchantability() {
		return this.enchantability;
	}

	@Override
	public SoundEvent getSoundEvent() {
		return this.soundEvent;
	}

	@Override
	public Ingredient getRepairMaterial() {
		return this.repairMaterial.getValue();
	}

	@Override
	public String getName() {
	    return this.name;
	}
	
	@Override
	public float getToughness() {
	    return this.toughness;
	}
}
