package com.github.kaydogz.daboismod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

	public final ForgeConfigSpec.IntValue skinToneCount;
	public final ForgeConfigSpec.IntValue ancientVeinCount;
	public final ForgeConfigSpec.BooleanValue generateAncientOre;
	public final ForgeConfigSpec.BooleanValue generateCannabis;
	
	public CommonConfig(ForgeConfigSpec.Builder builder) {
		
		builder.comment("Common configuration settings")
				.push("common");

		this.skinToneCount = builder
				.comment("The number of skin tones for villagers. Only increase this value if you have more skin tone textures.")
				.defineInRange("skinToneCount", 5, 1, 64);

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
