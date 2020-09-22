package com.github.kaydogz.daboismod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class DBMDirtBlock extends Block {

    public DBMDirtBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlantGrow(BlockState state, IWorld world, BlockPos pos, BlockPos source) {
        world.setBlockState(pos, this.getDefaultState(), 2);
    }
}
