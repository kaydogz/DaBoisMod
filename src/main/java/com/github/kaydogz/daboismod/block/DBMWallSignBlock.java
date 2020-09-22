package com.github.kaydogz.daboismod.block;

import com.github.kaydogz.daboismod.tileentity.DBMSignTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class DBMWallSignBlock extends WallSignBlock {

    public DBMWallSignBlock(Properties properties, DBMWoodType type) {
        super(properties, type);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new DBMSignTileEntity();
    }
}
