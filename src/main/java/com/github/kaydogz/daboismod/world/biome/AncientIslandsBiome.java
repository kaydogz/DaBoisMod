package com.github.kaydogz.daboismod.world.biome;

import com.github.kaydogz.daboismod.block.DBMBlocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class AncientIslandsBiome extends Biome {

	public AncientIslandsBiome() {
		super((new Biome.Builder()
				.surfaceBuilder(new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(
						DBMBlocks.ANCIENT_BLOCK.get().getDefaultState(), //Top Block
						DBMBlocks.ANCIENT_ORE.get().getDefaultState(), //Underground Block
						DBMBlocks.ANCIENT_BLOCK.get().getDefaultState()))) //Water Floor Block
				.precipitation(RainType.NONE)
				.category(Category.THEEND)
				.downfall(0f)
				.depth(0.8f)
				.temperature(0.9f)
				.scale(0.5f)
				.waterColor(0x99ccff)
				.waterFogColor(0x99ccff)
				.parent(null)));
	}
}
