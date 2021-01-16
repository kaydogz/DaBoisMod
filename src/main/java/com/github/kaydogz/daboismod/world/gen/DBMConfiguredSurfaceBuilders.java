package com.github.kaydogz.daboismod.world.gen;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.block.DBMBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class DBMConfiguredSurfaceBuilders {

    public static final RegistryKey<ConfiguredSurfaceBuilder<?>> BOTSWANA = makeKey("botswana");
    public static final RegistryKey<ConfiguredSurfaceBuilder<?>> ANCIENT_ISLANDS = makeKey("ancient_islands");

    public static void registerConfiguredSurfaceBuilders() {
        register(BOTSWANA,
                SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(DBMBlocks.BOTSWANIAN_GRASS_BLOCK.get().getDefaultState(), DBMBlocks.BOTSWANIAN_DIRT.get().getDefaultState(), Blocks.GRAVEL.getDefaultState())));
        register(ANCIENT_ISLANDS,
                SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(DBMBlocks.ANCIENT_BLOCK.get().getDefaultState(), DBMBlocks.ANCIENT_ORE.get().getDefaultState(), DBMBlocks.ANCIENT_BLOCK.get().getDefaultState())));
    }

    private static void register(RegistryKey<ConfiguredSurfaceBuilder<?>> key, ConfiguredSurfaceBuilder<?> surfaceBuilder) {
        Registry.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, key.getLocation(), surfaceBuilder);
    }

    private static RegistryKey<ConfiguredSurfaceBuilder<?>> makeKey(String name) {
        return RegistryKey.getOrCreateKey(Registry.CONFIGURED_SURFACE_BUILDER_KEY, DaBoisMod.modLocation(name));
    }
}
