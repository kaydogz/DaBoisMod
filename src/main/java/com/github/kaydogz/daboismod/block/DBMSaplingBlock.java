package com.github.kaydogz.daboismod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class DBMSaplingBlock extends SaplingBlock {

	public DBMSaplingBlock(Tree treeIn, Block.Properties properties) {
		super(treeIn, properties);
	}
	
	@Override
	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return super.isValidGround(state, worldIn, pos) || state.isIn(DBMBlocks.BOTSWANIAN_GRASS_BLOCK.get()) || state.isIn(DBMBlocks.BOTSWANIAN_DIRT.get());
	}
}
