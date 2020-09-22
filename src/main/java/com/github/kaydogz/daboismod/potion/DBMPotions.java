package com.github.kaydogz.daboismod.potion;

import com.github.kaydogz.daboismod.DaBoisMod;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DBMPotions {

	private static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, DaBoisMod.MODID);
	
	public static void registerPotions(IEventBus eventBus) {
		POTIONS.register(eventBus);
	}
	
	public static final RegistryObject<Potion> INDICA_ELIXIR = POTIONS.register("indica_elixir", () -> new Potion(new EffectInstance(DBMEffects.INDICA_STONAGE.get(), 3600)));
	public static final RegistryObject<Potion> INDICA_ELIXIR_LONG = POTIONS.register("indica_elixir_long", () -> new Potion("indica_elixir", new EffectInstance(DBMEffects.INDICA_STONAGE.get(), 9600)));
}
