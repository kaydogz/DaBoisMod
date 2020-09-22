package com.github.kaydogz.daboismod.world.gen.feature;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.world.gen.feature.structure.TombStructure;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DBMFeatures {

	private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, DaBoisMod.MODID);
	
	public static void registerFeatures(IEventBus eventBus) {
		FEATURES.register(eventBus);
	}
	
	public static final RegistryObject<Feature<TreeFeatureConfig>> PADAUK_TREE = FEATURES.register("padauk_tree", () -> new PadaukTreeFeature(DBMTreeFeatureConfig::deserializePadauk));
	public static final RegistryObject<Feature<BlockStateFeatureConfig>> CUSTOM_LAKE = FEATURES.register("custom_lake", () -> new DBMLakeFeature(BlockStateFeatureConfig::deserialize));
	public static final RegistryObject<Structure<NoFeatureConfig>> TOMB = FEATURES.register("tomb", () -> new TombStructure(NoFeatureConfig::deserialize));
}
