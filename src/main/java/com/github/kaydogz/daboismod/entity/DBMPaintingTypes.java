package com.github.kaydogz.daboismod.entity;

import com.github.kaydogz.daboismod.DaBoisMod;
import net.minecraft.entity.item.PaintingType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DBMPaintingTypes {

	private static final DeferredRegister<PaintingType> PAINTING_TYPES = DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, DaBoisMod.MODID);
	
	public static void registerPaintingTypes(IEventBus eventBus) {
		PAINTING_TYPES.register(eventBus);
	}
	
	public static final RegistryObject<PaintingType> FRANK = PAINTING_TYPES.register("frank", () -> new PaintingType(16, 16));
}
