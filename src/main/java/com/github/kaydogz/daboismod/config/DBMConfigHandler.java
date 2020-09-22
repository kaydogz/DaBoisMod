package com.github.kaydogz.daboismod.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class DBMConfigHandler {

	public static final ClientConfig CLIENT;
	protected static final ForgeConfigSpec CLIENT_SPEC;
	
	public static final CommonConfig COMMON;
	protected static final ForgeConfigSpec COMMON_SPEC;
	
	static {
		{
			final org.apache.commons.lang3.tuple.Pair<ClientConfig, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
			CLIENT = clientSpecPair.getLeft();
			CLIENT_SPEC = clientSpecPair.getRight();
		}
		{
			final org.apache.commons.lang3.tuple.Pair<CommonConfig, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
			COMMON = commonSpecPair.getLeft();
			COMMON_SPEC = commonSpecPair.getRight();
		}
	}
	
	public static void registerConfigs(final ModLoadingContext modLoadingContext) {
		modLoadingContext.registerConfig(ModConfig.Type.CLIENT, DBMConfigHandler.CLIENT_SPEC);
		modLoadingContext.registerConfig(ModConfig.Type.COMMON, DBMConfigHandler.COMMON_SPEC);
	}
}
