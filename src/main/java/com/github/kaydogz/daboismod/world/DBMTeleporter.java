package com.github.kaydogz.daboismod.world;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class DBMTeleporter implements ITeleporter {
	
	private final boolean realmFalling;
	
	public DBMTeleporter(boolean realmFalling) {
		this.realmFalling = realmFalling;
	}
	
	@Override
	public Entity placeEntity(Entity entityIn, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
		Entity entity = repositionEntity.apply(false);
		if (!this.realmFalling) return entity;
		entity.setPositionAndUpdate(entity.getPosX(), destWorld.getHeight() + 10, entity.getPosZ());
		return entity;
	}

	/**
	 * Teleports an entity to the top of the Overworld from the Realm of the Ancients.
	 * @param entityIn the entity.
	 */
	public static void teleportEntityToOverworldTop(Entity entityIn) {
		entityIn.changeDimension(entityIn.getServer().getWorld(World.OVERWORLD), new DBMTeleporter(true));
    }
	
}