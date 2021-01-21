package com.github.kaydogz.daboismod.entity;

import com.github.kaydogz.daboismod.DaBoisMod;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DBMEntities {
	
	private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, DaBoisMod.MODID);
	
	public static void registerEntities(IEventBus eventBus) {
		ENTITIES.register(eventBus);
	}

	/**
	 * Used to apply entity attributes. Not thread-safe, enqueue it in common setup.
	 */
	public static void applyEntityAttributes() {
		GlobalEntityTypeAttributes.put(SASQUATCH.get(), SasquatchEntity.registerAttributes().create());
		GlobalEntityTypeAttributes.put(WEREWOLF.get(), WerewolfEntity.registerAttributes().create());
		GlobalEntityTypeAttributes.put(FLESH_CREEPER.get(), FleshCreeperEntity.registerAttributes().create());
	}

	/**
	 * Registers how an entity spawns into the world. Not thread-safe, enqueue it in common setup.
	 */
	public static void registerSpawnPlacements() {
		EntitySpawnPlacementRegistry.register(FLESH_CREEPER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
	}

	// Cryptids
	public static final RegistryObject<EntityType<SasquatchEntity>> SASQUATCH = ENTITIES.register("sasquatch", () -> EntityType.Builder.create(SasquatchEntity::new, EntityClassification.MONSTER).size(1.15F, 3.15F).setTrackingRange(25).build(DaBoisMod.modLocation("sasquatch").toString()));
	public static final RegistryObject<EntityType<WerewolfEntity>> WEREWOLF = ENTITIES.register("werewolf", () -> EntityType.Builder.create(WerewolfEntity::new, EntityClassification.MONSTER).size(1.05F, 2.15F).setTrackingRange(25).build(DaBoisMod.modLocation("werewolf").toString()));

	// Mob Variants
	public static final RegistryObject<EntityType<FleshCreeperEntity>> FLESH_CREEPER = ENTITIES.register("flesh_creeper", () -> EntityType.Builder.create(FleshCreeperEntity::new, EntityClassification.MONSTER).size(0.6F, 1.7F).trackingRange(8).build(DaBoisMod.modLocation("flesh_creeper").toString()));
}
