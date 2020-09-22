package com.github.kaydogz.daboismod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import java.util.Random;

public class CannabisBlock extends Block implements IPlantable {
	public static final IntegerProperty AGE = BlockStateProperties.AGE_0_15;
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	
	public CannabisBlock(final Block.Properties properties) {
		super(properties);
	    this.setDefaultState(this.stateContainer.getBaseState().with(AGE, Integer.valueOf(0)));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return CannabisBlock.SHAPE;
	}
	
	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		if (!state.isValidPosition(worldIn, pos)) {
			worldIn.destroyBlock(pos, true);
	    } else if (worldIn.isAirBlock(pos.up())) {
	    	int i;
	        for(i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this; ++i) {
			}
	        
	        if (i < 3) {
	        	int j = state.get(AGE);
	            if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
	            	if (j == 15) {
	            		worldIn.setBlockState(pos.up(), this.getDefaultState());
	            		worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(0)), 4);
	            	} else {
	            		worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(j + 1)), 4);
	            	}
	            	net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
	            }
	        }
	    }
	}
	
	@Override @SuppressWarnings("deprecation")
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (!stateIn.isValidPosition(worldIn, currentPos)) {
			worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
	    }

	    return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		BlockState soil = worldIn.getBlockState(pos.down());
	    if (soil.canSustainPlant(worldIn, pos.down(), Direction.UP, this)) return true;
	    Block block = worldIn.getBlockState(pos.down()).getBlock();
	    if (block == this) {
	    	return true;
	    } else {
	    	if (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == DBMBlocks.BOTSWANIAN_GRASS_BLOCK.get() || block == DBMBlocks.BOTSWANIAN_DIRT.get()) return true;
	        return false;
	    }
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(CannabisBlock.AGE);
	}

	@Override
	public PlantType getPlantType(IBlockReader world, BlockPos pos) {
		return PlantType.Crop;
	}

	@Override
	public BlockState getPlant(IBlockReader world, BlockPos pos) {
		return this.getDefaultState();
	}

}
