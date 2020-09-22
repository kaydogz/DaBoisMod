package com.github.kaydogz.daboismod.block;

import net.minecraft.block.*;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Random;

public class BotswanianGrassBlock extends GrassBlock {
	
	public BotswanianGrassBlock(Block.Properties properties) {
		super(properties);
	}

	@Override
	public void onPlantGrow(BlockState state, IWorld world, BlockPos pos, BlockPos source) {
		world.setBlockState(pos, DBMBlocks.BOTSWANIAN_DIRT.get().getDefaultState(), 2);
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		if (!func_220257_b(state, worldIn, pos)) {
			if (!worldIn.isAreaLoaded(pos, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
			worldIn.setBlockState(pos, DBMBlocks.BOTSWANIAN_DIRT.get().getDefaultState());
		} else {
			if (worldIn.getLight(pos.up()) >= 9) {
				BlockState blockstate = this.getDefaultState();

				for(int i = 0; i < 4; ++i) {
					BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
					if (worldIn.getBlockState(blockpos).getBlock() == DBMBlocks.BOTSWANIAN_DIRT.get() && func_220256_c(blockstate, worldIn, blockpos)) {
						worldIn.setBlockState(blockpos, blockstate.with(SNOWY, worldIn.getBlockState(blockpos.up()).getBlock() == Blocks.SNOW));
					}
				}
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	@Override
	public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		BlockPos blockpos = pos.up();
		BlockState blockstate = DBMBlocks.BOTSWANIAN_GRASS.get().getDefaultState();

		for(int i = 0; i < 128; ++i) {
			BlockPos blockpos1 = blockpos;
			int j = 0;

			while(true) {
				if (j >= i / 16) {
					BlockState blockstate2 = worldIn.getBlockState(blockpos1);
					if (blockstate2.getBlock() == blockstate.getBlock() && rand.nextInt(10) == 0) {
						((IGrowable)blockstate.getBlock()).grow(worldIn, rand, blockpos1, blockstate2);
					}

					if (!blockstate2.isAir()) {
						break;
					}

					BlockState blockstate1;
					if (rand.nextInt(8) == 0) {
						List<ConfiguredFeature<?, ?>> list = worldIn.getBiome(blockpos1).getFlowers();
						if (list.isEmpty()) {
							break;
						}

						ConfiguredFeature<?, ?> configuredfeature = ((DecoratedFeatureConfig) (list.get(0)).config).feature;
						blockstate1 = ((FlowersFeature) configuredfeature.feature).getFlowerToPlace(rand, blockpos1, configuredfeature.config);
					} else {
						blockstate1 = blockstate;
					}

					if (blockstate1.isValidPosition(worldIn, blockpos1)) {
						worldIn.setBlockState(blockpos1, blockstate1, 3);
					}
					break;
				}

				blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
				if (worldIn.getBlockState(blockpos1.down()).getBlock() != this || worldIn.getBlockState(blockpos1).isCollisionShapeOpaque(worldIn, blockpos1)) {
					break;
				}

				++j;
			}
		}
	}
	
	private static boolean func_220257_b(BlockState stateIn, IWorldReader worldIn, BlockPos blockPosIn) {
		BlockPos blockpos = blockPosIn.up();
		BlockState blockstate = worldIn.getBlockState(blockpos);
		if (blockstate.getBlock() == Blocks.SNOW && blockstate.get(SnowBlock.LAYERS) == 1) {
			return true;
		} else {
			int i = LightEngine.func_215613_a(worldIn, stateIn, blockPosIn, blockstate, blockpos, Direction.UP, blockstate.getOpacity(worldIn, blockpos));
			return i < worldIn.getMaxLightLevel();
		}
	}

	private static boolean func_220256_c(BlockState stateIn, IWorldReader worldIn, BlockPos blockPosIn) {
		BlockPos blockpos = blockPosIn.up();
		return func_220257_b(stateIn, worldIn, blockPosIn) && !worldIn.getFluidState(blockpos).isTagged(FluidTags.WATER);
	}
}
