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
		Block block = state.getBlock();
		return super.isValidGround(state, worldIn, pos) || block == DBMBlocks.BOTSWANIAN_GRASS_BLOCK.get() || block == DBMBlocks.BOTSWANIAN_DIRT.get();
	}
}
