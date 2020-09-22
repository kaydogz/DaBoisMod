package com.github.kaydogz.daboismod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

	// OreGen
	public final ForgeConfigSpec.IntValue ancientVeinCount;
	public final ForgeConfigSpec.BooleanValue generateAncientOre;
	
	// PlantGen
	public final ForgeConfigSpec.BooleanValue generateCannabis;
	
	public CommonConfig(ForgeConfigSpec.Builder builder) {
		
		builder.comment("Common configuration settings")
				.push("common");

		this.ancientVeinCount = builder
				.comment("Maximum number of Ancient Ores to spawn in a vein.")
				.defineInRange("ancientVeinCount", 8, 1, 16);

		this.generateAncientOre = builder
				.comment("Decide if you want Ancient Ore to spawn in The End.")
				.define("generateAncientOre", true);
		
		this.generateCannabis = builder
				.comment("Decide if you want Cannabis to generate in jungles.")
				.define("generateCannabis", true);
		
		builder.pop();
	}
}
