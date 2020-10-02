package com.github.kaydogz.daboismod.data;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.data.loot.QuestLootTables;
import com.github.kaydogz.daboismod.world.storage.loot.DBMLootParameterSets;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootParameterSet;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.ValidationTracker;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DBMLootTables extends LootTableProvider {

    public DBMLootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(Pair.of(QuestLootTables::new, DBMLootParameterSets.QUEST));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
    }

    @Override
    public String getName() {
        return DaBoisMod.FORMATTED_NAME + " " + super.getName();
    }
}
