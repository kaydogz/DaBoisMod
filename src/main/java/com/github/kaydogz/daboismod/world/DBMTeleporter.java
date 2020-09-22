package com.github.kaydogz.daboismod.world;

import net.minecraft.entity.Entity;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class DBMTeleporter implements ITeleporter {
	
	private final boolean fallingFromSky;
	
	public DBMTeleporter(boolean fallingFromSky) {
		this.fallingFromSky = fallingFromSky;
	}
	
	@Override
	public Entity placeEntity(Entity entityIn, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
		Entity entity = repositionEntity.apply(false);
		if (!this.fallingFromSky) return entity;
		entity.setPositionAndUpdate(entity.getPosX(), destWorld.getActualHeight() + 10, entity.getPosZ());
		return entity;
	}

	/**
	 * Teleports an entity to the top of the Overworld from the Realm of the Ancients.
	 * @param entityIn the entity.
	 */
	public static void teleportEntityToOverworldTop(Entity entityIn) {
		entityIn.changeDimension(DimensionType.OVERWORLD, new DBMTeleporter(true));
    }
	
}