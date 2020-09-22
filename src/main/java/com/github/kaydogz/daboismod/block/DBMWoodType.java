package com.github.kaydogz.daboismod.block;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.block.WoodType;

import java.util.Set;
import java.util.stream.Stream;

public class DBMWoodType extends WoodType {

    private static final Set<DBMWoodType> CUSTOM_VALUES = new ObjectArraySet<>();
    public static DBMWoodType padauk = registerWoodType(new DBMWoodType("padauk"));

    public static Stream<DBMWoodType> getCustomValues() {
        return CUSTOM_VALUES.stream();
    }

    private static DBMWoodType registerWoodType(DBMWoodType woodTypeIn) {
        CUSTOM_VALUES.add(woodTypeIn);
        return woodTypeIn;
    }

    protected DBMWoodType(String nameIn) {
        super(nameIn);
    }
}
