package com.github.kaydogz.daboismod.world.gen;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.block.DBMBlocks;
import com.github.kaydogz.daboismod.config.DBMConfigHandler;
import com.github.kaydogz.daboismod.world.biome.DBMBiomeMaker;
import com.github.kaydogz.daboismod.world.gen.feature.DBMFeatures;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;

public class DBMConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> PADAUK = makeKey("padauk");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_ANCIENT = makeKey("ore_ancient");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_BOTSWANIAN_DIRT = makeKey("ore_botswanian_dirt");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_CANNABIS = makeKey("patch_cannabis");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DISK_SAND = makeKey("disk_sand");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DISK_CLAY = makeKey("disk_clay");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DISK_GRAVEL = makeKey("disk_gravel");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PADAUK_TREE = makeKey("padauk_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LAKE_WATER = makeKey("lake_water");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LAKE_LAVA = makeKey("lake_lava");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_GRASS_BOTSWANIAN = makeKey("patch_grass_botswanian");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_TALL_GRASS_BOTSWANIAN = makeKey("patch_tall_grass_botswanian");

    public static void registerConfiguredFeatures() {
        register(PADAUK,
                Feature.TREE.withConfiguration(DBMGeneration.padauk_tree_config));
        register(ORE_ANCIENT,
                Feature.ORE.withConfiguration(new OreFeatureConfig(DBMGeneration.END_STONE, DBMBlocks.ANCIENT_ORE.get().getDefaultState(), DBMConfigHandler.COMMON.ancientVeinCount.get())).range(16).square());
        register(ORE_BOTSWANIAN_DIRT,
                Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, DBMBlocks.BOTSWANIAN_DIRT.get().getDefaultState(), 33)).range(256).square().func_242731_b(10));
        register(PATCH_CANNABIS,
                Feature.RANDOM_PATCH.withConfiguration(DBMGeneration.cannabis_config).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(10));
        register(DISK_SAND,
                Feature.DISK.withConfiguration(new SphereReplaceConfig(Blocks.SAND.getDefaultState(), FeatureSpread.func_242253_a(2, 4), 2, ImmutableList.of(DBMBlocks.BOTSWANIAN_DIRT.get().getDefaultState(), DBMBlocks.BOTSWANIAN_GRASS_BLOCK.get().getDefaultState()))).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT).func_242731_b(3));
        register(DISK_CLAY,
                Feature.DISK.withConfiguration(new SphereReplaceConfig(Blocks.CLAY.getDefaultState(), FeatureSpread.func_242253_a(2, 1), 1, ImmutableList.of(DBMBlocks.BOTSWANIAN_DIRT.get().getDefaultState(), Blocks.CLAY.getDefaultState()))).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT));
        register(DISK_GRAVEL,
                Feature.DISK.withConfiguration(new SphereReplaceConfig(Blocks.GRAVEL.getDefaultState(), FeatureSpread.func_242253_a(2, 3), 2, ImmutableList.of(DBMBlocks.BOTSWANIAN_DIRT.get().getDefaultState(), DBMBlocks.BOTSWANIAN_GRASS_BLOCK.get().getDefaultState()))).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT));
        register(PADAUK_TREE,
                Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(DBMBiomeMaker.getFeature(PADAUK).withChance(0.99F)), Features.DARK_OAK)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.1F, 1))));
        register(LAKE_WATER,
                DBMFeatures.CUSTOM_LAKE.get().withConfiguration(new BlockStateFeatureConfig(Blocks.WATER.getDefaultState())).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(4))));
        register(LAKE_LAVA,
                DBMFeatures.CUSTOM_LAKE.get().withConfiguration(new BlockStateFeatureConfig(Blocks.LAVA.getDefaultState())).withPlacement(Placement.LAVA_LAKE.configure(new ChanceConfig(80))));
        register(PATCH_GRASS_BOTSWANIAN,
                Feature.RANDOM_PATCH.withConfiguration(DBMGeneration.botswanian_grass_config).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(20));
        register(PATCH_TALL_GRASS_BOTSWANIAN,
                Feature.RANDOM_PATCH.withConfiguration(DBMGeneration.botswanian_tall_grass_config).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(7));
    }

    private static void register(RegistryKey<ConfiguredFeature<?, ?>> key, ConfiguredFeature<?, ?> feature) {
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key.getLocation(), feature);
    }

    private static RegistryKey<ConfiguredFeature<?, ?>> makeKey(String name) {
        return RegistryKey.getOrCreateKey(Registry.CONFIGURED_FEATURE_KEY, DaBoisMod.modLocation(name));
    }
}
