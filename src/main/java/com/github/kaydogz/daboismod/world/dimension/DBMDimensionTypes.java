package com.github.kaydogz.daboismod.world.dimension;

import com.github.kaydogz.daboismod.DaBoisMod;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DBMDimensionTypes {

	public static final ResourceLocation REALM_OF_THE_ANCIENTS_LOCATION = DaBoisMod.modLocation("the_realm_of_the_ancients");

	/**
	 * Registers dimensions to the {@link DimensionManager} for DaBoisMod.
	 */
	public static void registerDimensions() {
		if (DimensionType.byName(DBMDimensionTypes.REALM_OF_THE_ANCIENTS_LOCATION) == null) {
			DimensionManager.registerDimension(DBMDimensionTypes.REALM_OF_THE_ANCIENTS_LOCATION, DBMModDimensions.THE_REALM_OF_THE_ANCIENTS.get(), new PacketBuffer(Unpooled.buffer(16)), false);
		}
	}
}
