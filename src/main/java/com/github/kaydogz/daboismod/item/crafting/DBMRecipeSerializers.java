package com.github.kaydogz.daboismod.item.crafting;

import com.github.kaydogz.daboismod.DaBoisMod;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DBMRecipeSerializers {

	private static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, DaBoisMod.MODID);
	
	public static void registerRecipeSerializers(IEventBus eventBus) {
		RECIPE_SERIALIZERS.register(eventBus);
	}
	
	public static final RegistryObject<SpecialRecipeSerializer<InsertGemRecipe>> CRAFTING_RECIPE_INSERTGEM = RECIPE_SERIALIZERS.register("crafting_recipe_insertgem", () -> new SpecialRecipeSerializer<>(InsertGemRecipe::new));
	public static final RegistryObject<SpecialRecipeSerializer<RemoveGemRecipe>> CRAFTING_RECIPE_REMOVEGEM = RECIPE_SERIALIZERS.register("crafting_recipe_removegem", () -> new SpecialRecipeSerializer<>(RemoveGemRecipe::new));
}
