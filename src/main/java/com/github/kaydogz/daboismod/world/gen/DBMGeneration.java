package com.github.kaydogz.daboismod.world.gen;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.block.DBMBlocks;
import com.github.kaydogz.daboismod.config.DBMConfigHandler;
import com.github.kaydogz.daboismod.world.biome.DBMBiomes;
import com.github.kaydogz.daboismod.world.gen.feature.DBMFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.blockplacer.ColumnBlockPlacer;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraft.world.gen.foliageplacer.AcaciaFoliagePlacer;
import net.minecraft.world.gen.placement.*;
import net.minecraftforge.common.BiomeDictionary;

public class DBMGeneration {

	public static TreeFeatureConfig padauk_tree_config;
	public static BlockClusterFeatureConfig botswanian_grass_config;
	public static BlockClusterFeatureConfig botswanian_tall_grass_config;
	
	public static final FillerBlockType END_STONE = FillerBlockType.create("END_STONE", DaBoisMod.modLocation("end_stone").toString(), new BlockMatcher(Blocks.END_STONE));

	public static void createFeatureConfigs() {
		padauk_tree_config = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(DBMBlocks.PADAUK_LOG.get().getDefaultState()), new SimpleBlockStateProvider(DBMBlocks.PADAUK_LEAVES.get().getDefaultState()), new AcaciaFoliagePlacer(2, 0))).baseHeight(5).heightRandA(2).heightRandB(2).trunkHeight(0).ignoreVines().setSapling(DBMBlocks.PADAUK_SAPLING.get()).build();
		botswanian_grass_config = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(DBMBlocks.BOTSWANIAN_GRASS.get().getDefaultState()), new SimpleBlockPlacer())).tries(32).build();
		botswanian_tall_grass_config = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(DBMBlocks.BOTSWANIAN_TALL_GRASS.get().getDefaultState()), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build();
	}

	/**
	 * Sets up generation for DaBoisMod.
	 */
	public static void setupGeneration() {
		
		//Ancient Ore in The End
		if (DBMConfigHandler.COMMON.generateAncientOre.get()) {
			for (Biome biome : BiomeDictionary.getBiomes(BiomeDictionary.Type.END)) {
				biome.addFeature(Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(END_STONE, DBMBlocks.ANCIENT_ORE.get().getDefaultState(), DBMConfigHandler.COMMON.ancientVeinCount.get())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(1, 5, 0, 35))));
			}
		}

		// Cannabis in Jungles
		if (DBMConfigHandler.COMMON.generateCannabis.get()) {
			for (Biome biome : BiomeDictionary.getBiomes(BiomeDictionary.Type.JUNGLE)) {
				biome.addFeature(Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(DBMBlocks.CANNABIS.get().getDefaultState()), new ColumnBlockPlacer(1, 2)).tries(5).xSpread(2).ySpread(0).zSpread(2).func_227317_b_().build()).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(15, 40, 0, 120))));
			}
		}

		for (Biome biome : BiomeDictionary.getBiomes(BiomeDictionary.Type.OVERWORLD)) {
			biome.addStructure(DBMFeatures.TOMB.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));

			biome.addFeature(Decoration.SURFACE_STRUCTURES, DBMFeatures.TOMB.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		}
		
		DBMBiomes.BOTSWANA.get().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DBMFeatures.PADAUK_TREE.get().withConfiguration(padauk_tree_config).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(1, 0.1F, 1))));
		DBMBiomes.BOTSWANA.get().addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, DBMFeatures.CUSTOM_LAKE.get().withConfiguration(new BlockStateFeatureConfig(Blocks.WATER.getDefaultState())).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(4))));
		DBMBiomes.BOTSWANA.get().addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, DBMFeatures.CUSTOM_LAKE.get().withConfiguration(new BlockStateFeatureConfig(Blocks.LAVA.getDefaultState())).withPlacement(Placement.LAVA_LAKE.configure(new ChanceConfig(80))));
		DBMBiomes.BOTSWANA.get().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(botswanian_grass_config).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(2))));
		DBMBiomes.BOTSWANA.get().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(botswanian_tall_grass_config).withPlacement(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(7))));

	}
}
