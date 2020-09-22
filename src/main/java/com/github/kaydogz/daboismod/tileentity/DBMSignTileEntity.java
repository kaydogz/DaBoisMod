package com.github.kaydogz.daboismod.tileentity;

import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class DBMSignTileEntity extends SignTileEntity {

    @Override
    public TileEntityType<?> getType() {
        return DBMTileEntities.CUSTOM_SIGN.get();
    }
}
