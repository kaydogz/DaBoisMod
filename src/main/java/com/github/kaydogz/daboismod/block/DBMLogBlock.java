package com.github.kaydogz.daboismod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class DBMLogBlock extends RotatedPillarBlock {

    public DBMLogBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
        Block block = state.getBlock();
        if (block == DBMBlocks.PADAUK_LOG.get()) {
            return DBMBlocks.STRIPPED_PADAUK_LOG.get().getDefaultState().with(RotatedPillarBlock.AXIS, state.get(RotatedPillarBlock.AXIS));
        } else if (block == DBMBlocks.PADAUK_WOOD.get()) {
            return DBMBlocks.STRIPPED_PADAUK_WOOD.get().getDefaultState().with(RotatedPillarBlock.AXIS, state.get(RotatedPillarBlock.AXIS));
        } else {
            return super.getToolModifiedState(state, world, pos, player, stack, toolType);
        }
    }
}
