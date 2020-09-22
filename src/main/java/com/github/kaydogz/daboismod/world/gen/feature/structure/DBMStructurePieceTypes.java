package com.github.kaydogz.daboismod.world.gen.feature.structure;

import com.github.kaydogz.daboismod.DaBoisMod;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public class DBMStructurePieceTypes {

    public static IStructurePieceType tomb;

    public static void registerStructurePieceTypes() {
        tomb = IStructurePieceType.register(TombPiece::new, DaBoisMod.modLocation("tomb").toString());
    }
}
