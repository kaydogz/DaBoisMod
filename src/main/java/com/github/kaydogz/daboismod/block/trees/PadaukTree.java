package com.github.kaydogz.daboismod.block.trees;

import com.github.kaydogz.daboismod.world.gen.DBMGeneration;
import com.github.kaydogz.daboismod.world.gen.feature.DBMFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;

public class PadaukTree extends Tree {

	@Override
	public ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
		return DBMFeatures.PADAUK_TREE.get().withConfiguration(DBMGeneration.padauk_tree_config);
	}
	
	public static int getPadaukLeavesColor() {
		return 16772955;
	}
}
