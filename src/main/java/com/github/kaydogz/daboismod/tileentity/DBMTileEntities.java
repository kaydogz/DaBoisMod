package com.github.kaydogz.daboismod.tileentity;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.block.DBMBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DBMTileEntities {

    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, DaBoisMod.MODID);

    public static void registerTileEntities(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }

    public static final RegistryObject<TileEntityType<DBMSignTileEntity>> CUSTOM_SIGN = TILE_ENTITIES.register("custom_sign", () -> TileEntityType.Builder.create(DBMSignTileEntity::new, DBMBlocks.PADAUK_SIGN.get(), DBMBlocks.PADAUK_WALL_SIGN.get()).build(null));
}
