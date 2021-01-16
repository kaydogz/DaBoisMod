package com.github.kaydogz.daboismod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.TallGrassBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class DBMTallGrassBlock extends TallGrassBlock {

	public DBMTallGrassBlock(Block.Properties properties) {
		super(properties);
	}

	@Override
	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return super.isValidGround(state, worldIn, pos) || state.isIn(DBMBlocks.BOTSWANIAN_GRASS_BLOCK.get()) || state.isIn(DBMBlocks.BOTSWANIAN_DIRT.get());
	}
	
	@Override
	public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		DoublePlantBlock doubleplantblock = this == DBMBlocks.BOTSWANIAN_GRASS.get() ? DBMBlocks.BOTSWANIAN_TALL_GRASS.get() : DBMBlocks.BOTSWANIAN_TALL_GRASS.get();
		if (doubleplantblock.getDefaultState().isValidPosition(worldIn, pos) && worldIn.isAirBlock(pos.up())) {
			doubleplantblock.placeAt(worldIn, pos, 2);
		}
	}
}
