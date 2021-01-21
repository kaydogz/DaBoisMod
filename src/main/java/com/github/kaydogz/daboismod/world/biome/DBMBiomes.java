package com.github.kaydogz.daboismod.world.biome;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.world.gen.surfacebuilder.DBMConfiguredSurfaceBuilders;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DBMBiomes {

	private static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, DaBoisMod.MODID);

	public static void registerBiomes(IEventBus eventBus) {
		BIOMES.register(eventBus);
	}

	public static final RegistryObject<Biome> BOTSWANA = BIOMES.register("botswana", () -> DBMBiomeMaker.makeBotswanaBiome(DBMConfiguredSurfaceBuilders.BOTSWANA, 0.1F, 0.5F));
	public static final RegistryObject<Biome> ANCIENT_ISLANDS = BIOMES.register("ancient_islands", () -> DBMBiomeMaker.makeAncientIslands(DBMConfiguredSurfaceBuilders.ANCIENT_ISLANDS, 0.8F, 0.5F));

	public static void setupBiomes() {
		setupBiome(BOTSWANA.get(), BiomeManager.BiomeType.DESERT, 3, BiomeDictionary.Type.HOT, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.DEAD);
		setupBiome(ANCIENT_ISLANDS.get(), BiomeManager.BiomeType.COOL, 1, BiomeDictionary.Type.COLD, BiomeDictionary.Type.MAGICAL);
	}

	private static void setupBiome(Biome biome, BiomeManager.BiomeType biomeType, int weight, BiomeDictionary.Type... types) {
		RegistryKey<Biome> key = RegistryKey.getOrCreateKey(ForgeRegistries.Keys.BIOMES, ForgeRegistries.BIOMES.getKey(biome));
		BiomeDictionary.addTypes(key, types);
		BiomeManager.addBiome(biomeType, new BiomeManager.BiomeEntry(key, weight));
	}
}
