package com.github.kaydogz.daboismod.world.storage.loot;

import com.github.kaydogz.daboismod.DaBoisMod;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public class DBMLootParameterSets {

    public static final LootParameterSet QUEST = register(DaBoisMod.modLocation("quest"), builder -> builder.required(DBMLootParameters.QUEST));

    public static LootParameterSet register(ResourceLocation location, Consumer<LootParameterSet.Builder> builder) {
        LootParameterSet.Builder lootparameterset$builder = new LootParameterSet.Builder();
        builder.accept(lootparameterset$builder);
        LootParameterSet lootparameterset = lootparameterset$builder.build();
        LootParameterSet lootparameterset1 = LootParameterSets.REGISTRY.put(location, lootparameterset);
        if (lootparameterset1 != null) {
            throw new IllegalStateException("Loot table parameter set " + location + " is already registered");
        } else {
            return lootparameterset;
        }
    }
}
