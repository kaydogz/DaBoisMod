package com.github.kaydogz.daboismod.block.trees;

import com.github.kaydogz.daboismod.world.gen.DBMConfiguredFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;

import java.util.Random;

public class PadaukTree extends Tree {

	@Override @SuppressWarnings("unchecked")
	public ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
		ConfiguredFeature<?, ?> feature = WorldGenRegistries.CONFIGURED_FEATURE.getOrThrow(DBMConfiguredFeatures.PADAUK);
		if (feature.config instanceof BaseTreeFeatureConfig) {
			return (ConfiguredFeature<BaseTreeFeatureConfig, ?>) feature;
		}
		return Features.OAK;
	}
	
	public static int getPadaukLeavesColor() {
		return 16772955;
	}
}
