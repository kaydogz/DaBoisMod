package com.github.kaydogz.daboismod.stats;

import com.github.kaydogz.daboismod.DaBoisMod;
import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class DBMStats {

	public static ResourceLocation complete_quest;
	
	public static ResourceLocation registerStat(String key, IStatFormatter formatter) {
		ResourceLocation location = DaBoisMod.modLocation(key);
		Registry.register(Registry.CUSTOM_STAT, key, location);
		Stats.CUSTOM.get(location, formatter);
		return location;
	}
	
	public static void registerStats() {
		DBMStats.complete_quest = registerStat("complete_quest", IStatFormatter.DEFAULT);
	}
}
