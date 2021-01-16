package com.github.kaydogz.daboismod.world.gen.feature;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.block.DBMBlocks;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.AcaciaFoliagePlacer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DBMFeatures {

	private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, DaBoisMod.MODID);
	
	public static void registerFeatures(IEventBus eventBus) {
		FEATURES.register(eventBus);
	}

	public static final RegistryObject<Feature<BlockStateFeatureConfig>> CUSTOM_LAKE = FEATURES.register("custom_lake", () -> new DBMLakeFeature(BlockStateFeatureConfig.field_236455_a_));
}
