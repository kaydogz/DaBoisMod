package com.github.kaydogz.daboismod.block;

import com.github.kaydogz.daboismod.DaBoisMod;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.model.Material;

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

    public static void registerToSignMaterials() {
        CUSTOM_VALUES.forEach((woodType) -> Atlases.SIGN_MATERIALS.put(woodType, woodType.getSignMaterial()));
    }

    private final Material renderMaterial;

    protected DBMWoodType(String nameIn) {
        super(nameIn);
        this.renderMaterial = new Material(Atlases.SIGN_ATLAS, DaBoisMod.modLocation("entity/signs/" + nameIn));
    }

    public final Material getSignMaterial() {
        return this.renderMaterial;
    }
}
