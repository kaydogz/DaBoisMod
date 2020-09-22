package com.github.kaydogz.daboismod.enchantment;

import com.github.kaydogz.daboismod.DaBoisMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DBMEnchantments {

	private static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, DaBoisMod.MODID);
	
	public static void registerEnchantments(IEventBus eventBus) {
		ENCHANTMENTS.register(eventBus);
	}
	
	public static final RegistryObject<MagnetismEnchantment> MAGNETISM = ENCHANTMENTS.register("magnetism", () -> new MagnetismEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND));
}
