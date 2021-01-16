package com.github.kaydogz.daboismod.world.gen;

import com.github.kaydogz.daboismod.block.DBMBlocks;
import com.github.kaydogz.daboismod.config.DBMConfigHandler;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.blockplacer.ColumnBlockPlacer;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.foliageplacer.AcaciaFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.ForkyTrunkPlacer;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;

public class DBMGeneration {

	public static BaseTreeFeatureConfig padauk_tree_config;
	public static BlockClusterFeatureConfig botswanian_grass_config;
	public static BlockClusterFeatureConfig botswanian_tall_grass_config;
	public static BlockClusterFeatureConfig cannabis_config;

	public static final RuleTest END_STONE = new BlockMatchRuleTest(Blocks.END_STONE);

	public static void createGenerationConfigs() {
		padauk_tree_config = (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(DBMBlocks.PADAUK_LOG.get().getDefaultState()), new SimpleBlockStateProvider(DBMBlocks.PADAUK_LEAVES.get().getDefaultState()), new AcaciaFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0)), new ForkyTrunkPlacer(5, 2, 2), new TwoLayerFeature(1, 0, 2))).setIgnoreVines().build();
		botswanian_grass_config = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(DBMBlocks.BOTSWANIAN_GRASS.get().getDefaultState()), SimpleBlockPlacer.PLACER)).tries(32).build();
		botswanian_tall_grass_config = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(DBMBlocks.BOTSWANIAN_TALL_GRASS.get().getDefaultState()), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build();
		cannabis_config = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(DBMBlocks.CANNABIS.get().getDefaultState()), new ColumnBlockPlacer(2, 2))).tries(10).xSpread(3).ySpread(0).zSpread(3).func_227317_b_().build();
	}
}
