package com.github.kaydogz.daboismod.world.storage.loot;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.quest.Quest;
import net.minecraft.world.storage.loot.LootParameter;

public class DBMLootParameters {
    public static final LootParameter<Quest> QUEST = new LootParameter<>(DaBoisMod.modLocation("quest"));
}
