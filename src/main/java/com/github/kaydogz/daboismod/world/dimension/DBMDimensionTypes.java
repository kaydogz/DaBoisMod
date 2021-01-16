package com.github.kaydogz.daboismod.world.dimension;

import com.github.kaydogz.daboismod.DaBoisMod;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;

public class DBMDimensionTypes {

	public static final RegistryKey<DimensionType> REALM_OF_THE_ANCIENTS = makeKey("realm_of_the_ancients");

	protected static RegistryKey<DimensionType> makeKey(String location) {
		return RegistryKey.getOrCreateKey(Registry.DIMENSION_TYPE_KEY, DaBoisMod.modLocation(location));
	}
}
