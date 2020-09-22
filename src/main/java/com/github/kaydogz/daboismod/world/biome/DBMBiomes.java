package com.github.kaydogz.daboismod.world.biome;

import com.github.kaydogz.daboismod.DaBoisMod;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DBMBiomes {

	private static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, DaBoisMod.MODID);
	
	public static void registerBiomes(IEventBus eventBus) {
		BIOMES.register(eventBus);
	}
	
	public static final RegistryObject<AncientIslandsBiome> ANCIENT_ISLANDS = BIOMES.register("ancient_islands", AncientIslandsBiome::new);
	public static final RegistryObject<BotswanaBiome> BOTSWANA = BIOMES.register("botswana", BotswanaBiome::new);
}
