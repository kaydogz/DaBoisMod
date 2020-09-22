package com.github.kaydogz.daboismod.util;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.item.DBMSpawnEggItem;
import com.github.kaydogz.daboismod.world.biome.DBMBiomes;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DaBoisMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DBMRegistryHandler {

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void postRegisterBiomes(final RegistryEvent.Register<Biome> event) {
		BiomeDictionary.addTypes(DBMBiomes.ANCIENT_ISLANDS.get());
		BiomeDictionary.addTypes(DBMBiomes.BOTSWANA.get(), BiomeDictionary.Type.HOT, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.DEAD);
		BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(DBMBiomes.BOTSWANA.get(), 3));
	}
	
	/**
	 * Exists to work around a limitation with Spawn Eggs:
	 * Spawn Eggs require an EntityType, but EntityTypes are created AFTER Items.
	 * Therefore it is "impossible" for modded spawn eggs to exist.
	 * To get around this we have our own custom SpawnEggItem, but it needs
	 * some extra setup after Item and EntityType registration completes.
	 */
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void postRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
		DBMSpawnEggItem.initUnaddedEggs();
	}
}
