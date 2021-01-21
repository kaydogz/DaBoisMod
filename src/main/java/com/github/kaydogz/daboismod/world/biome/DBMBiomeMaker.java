package com.github.kaydogz.daboismod.world.biome;

import com.github.kaydogz.daboismod.config.DBMConfigHandler;
import com.github.kaydogz.daboismod.entity.DBMEntities;
import com.github.kaydogz.daboismod.world.gen.feature.DBMConfiguredFeatures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class DBMBiomeMaker {

    public static Biome makeBotswanaBiome(RegistryKey<ConfiguredSurfaceBuilder<?>> surfaceBuilderKey, float depth, float scale) {
        MobSpawnInfo.Builder spawner = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withBats(spawner);

        BiomeGenerationSettings.Builder generator = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(getSurfaceBuilder(surfaceBuilderKey));
        generator.withStructure(StructureFeatures.MINESHAFT);
        DefaultBiomeFeatures.withMonsterRoom(generator);
        DefaultBiomeFeatures.withOverworldOres(generator);
        generator.withCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.field_243767_a);
        generator.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_GRAVEL);
        generator.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_GRANITE);
        generator.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_DIORITE);
        generator.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_ANDESITE);

        return (new Biome.Builder()).category(Biome.Category.SAVANNA).depth(depth).downfall(0.5F).precipitation(Biome.RainType.NONE).scale(scale).setEffects((new BiomeAmbience.Builder()).setWaterColor(0x6bfa6b).setWaterFogColor(0x6bfa6b).setFogColor(0xaff800).withSkyColor(0xb9e529).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).temperature(2.5F).withMobSpawnSettings(spawner.copy()).withGenerationSettings(generator.build()).build();
    }

    public static Biome makeAncientIslands(RegistryKey<ConfiguredSurfaceBuilder<?>> surfaceBuilderKey, float depth, float scale) {
        MobSpawnInfo.Builder spawner = new MobSpawnInfo.Builder();
        BiomeGenerationSettings.Builder generator = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(getSurfaceBuilder(surfaceBuilderKey));

        return (new Biome.Builder()).category(Biome.Category.THEEND).depth(depth).downfall(0.0F).precipitation(Biome.RainType.NONE).scale(scale).setEffects((new BiomeAmbience.Builder()).setWaterColor(0x99ccff).setWaterFogColor(0x99ccff).setFogColor(0x99ccff).withSkyColor(0x6eb2ff).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).temperature(0.9F).withMobSpawnSettings(spawner.copy()).withGenerationSettings(generator.build()).build();
    }

    public static void modifyBiomes(final RegistryKey<Biome> biomeRegistryKey, final MobSpawnInfoBuilder spawner, final BiomeGenerationSettingsBuilder generator) {

        // Flesh Creepers in the Nether and Botswana
        if (BiomeDictionary.hasType(biomeRegistryKey, BiomeDictionary.Type.NETHER) || biomeRegistryKey.getLocation() == DBMBiomes.BOTSWANA.get().getRegistryName()) {
            spawner.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(DBMEntities.FLESH_CREEPER.get(), 20, 2, 4));
        }

        //Ancient Ore in The End
        if (DBMConfigHandler.COMMON.generateAncientOre.get()) {
            if (BiomeDictionary.hasType(biomeRegistryKey, BiomeDictionary.Type.END)) {
                generator.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, getFeature(DBMConfiguredFeatures.ORE_ANCIENT));
            }
        }

        // Cannabis in Jungles
        if (DBMConfigHandler.COMMON.generateCannabis.get()) {
            if (BiomeDictionary.hasType(biomeRegistryKey, BiomeDictionary.Type.JUNGLE)) {
                generator.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, getFeature(DBMConfiguredFeatures.PATCH_CANNABIS));
            }
        }

        // Add Custom Features and Spawns to Custom Biomes
        if (biomeRegistryKey.getLocation() == DBMBiomes.BOTSWANA.get().getRegistryName()) {
            spawner.disablePlayerSpawn();
            generator.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, getFeature(DBMConfiguredFeatures.DISK_SAND));
            generator.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, getFeature(DBMConfiguredFeatures.DISK_CLAY));
            generator.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, getFeature(DBMConfiguredFeatures.DISK_GRAVEL));
            generator.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, getFeature(DBMConfiguredFeatures.ORE_BOTSWANIAN_DIRT));
            generator.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, getFeature(DBMConfiguredFeatures.TREES_BOTSWANA));
            generator.withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, getFeature(DBMConfiguredFeatures.LAKE_WATER));
            generator.withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, getFeature(DBMConfiguredFeatures.LAKE_LAVA));
            generator.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, getFeature(DBMConfiguredFeatures.PATCH_GRASS_BOTSWANIAN));
            generator.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, getFeature(DBMConfiguredFeatures.PATCH_TALL_GRASS_BOTSWANIAN));
        }
    }

    public static Supplier<ConfiguredSurfaceBuilder<?>> getSurfaceBuilder(final RegistryKey<ConfiguredSurfaceBuilder<?>> key) {
        return () -> WorldGenRegistries.CONFIGURED_SURFACE_BUILDER.getOrThrow(key);
    }

    public static ConfiguredFeature<?, ?> getFeature(final RegistryKey<ConfiguredFeature<?, ?>> key) {
        return WorldGenRegistries.CONFIGURED_FEATURE.getOrThrow(key);
    }
}
