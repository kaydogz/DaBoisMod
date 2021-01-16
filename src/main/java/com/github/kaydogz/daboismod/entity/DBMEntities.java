package com.github.kaydogz.daboismod.entity;

import com.github.kaydogz.daboismod.DaBoisMod;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
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
	 * Used to apply entity attributes. Not thread-safe. Enqueue it in common setup.
	 */
	public static void applyEntityAttributes() {
		GlobalEntityTypeAttributes.put(DBMEntities.SASQUATCH.get(), SasquatchEntity.registerAttributes().create());
		GlobalEntityTypeAttributes.put(DBMEntities.WEREWOLF.get(), WerewolfEntity.registerAttributes().create());
	}

	// Cryptids
	public static final RegistryObject<EntityType<SasquatchEntity>> SASQUATCH = ENTITIES.register("sasquatch", () -> EntityType.Builder.create(SasquatchEntity::new, EntityClassification.MONSTER).size(1.15F, 3.15F).setTrackingRange(25).build(DaBoisMod.modLocation("sasquatch").toString()));
	public static final RegistryObject<EntityType<WerewolfEntity>> WEREWOLF = ENTITIES.register("werewolf", () -> EntityType.Builder.create(WerewolfEntity::new, EntityClassification.MONSTER).size(1.05F, 2.15F).setTrackingRange(25).build(DaBoisMod.modLocation("werewolf").toString()));
}
