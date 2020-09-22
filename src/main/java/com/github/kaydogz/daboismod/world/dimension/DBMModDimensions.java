package com.github.kaydogz.daboismod.world.dimension;

import com.github.kaydogz.daboismod.DaBoisMod;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DBMModDimensions {
	
	private static final DeferredRegister<ModDimension> MOD_DIMENSIONS = DeferredRegister.create(ForgeRegistries.MOD_DIMENSIONS, DaBoisMod.MODID);
	
	public static void registerModDimensions(IEventBus eventBus) {
		MOD_DIMENSIONS.register(eventBus);
	}
	
	public static final RegistryObject<RealmOfTheAncientsModDimension> THE_REALM_OF_THE_ANCIENTS = MOD_DIMENSIONS.register("the_realm_of_the_ancients", RealmOfTheAncientsModDimension::new);
}
