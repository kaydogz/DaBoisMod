package com.github.kaydogz.daboismod;

import com.github.kaydogz.daboismod.block.DBMBlocks;
import com.github.kaydogz.daboismod.client.ClientHandler;
import com.github.kaydogz.daboismod.config.DBMConfigHandler;
import com.github.kaydogz.daboismod.enchantment.DBMEnchantments;
import com.github.kaydogz.daboismod.entity.DBMEntities;
import com.github.kaydogz.daboismod.entity.DBMPaintingTypes;
import com.github.kaydogz.daboismod.item.DBMItems;
import com.github.kaydogz.daboismod.item.crafting.DBMRecipeSerializers;
import com.github.kaydogz.daboismod.potion.DBMEffects;
import com.github.kaydogz.daboismod.potion.DBMPotions;
import com.github.kaydogz.daboismod.quest.QuestTasks;
import com.github.kaydogz.daboismod.tileentity.DBMTileEntities;
import com.github.kaydogz.daboismod.util.DBMSoundEvents;
import com.github.kaydogz.daboismod.world.biome.DBMBiomes;
import com.github.kaydogz.daboismod.world.dimension.DBMModDimensions;
import com.github.kaydogz.daboismod.world.gen.feature.DBMFeatures;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(DaBoisMod.MODID)
public class DaBoisMod {
	
	public static final String MODID = "daboismod";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	public DaBoisMod() {
		
		final ModLoadingContext modLoadingContext = ModLoadingContext.get();
		DBMConfigHandler.registerConfigs(modLoadingContext);

		final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		DBMBlocks.registerBlocks(eventBus);
		DBMItems.registerItems(eventBus);
		DBMBiomes.registerBiomes(eventBus);
		DBMEnchantments.registerEnchantments(eventBus);
		DBMEntities.registerEntities(eventBus);
		DBMEffects.registerEffects(eventBus);
		DBMFeatures.registerFeatures(eventBus);
		DBMModDimensions.registerModDimensions(eventBus);
		DBMPaintingTypes.registerPaintingTypes(eventBus);
		DBMPotions.registerPotions(eventBus);
		DBMRecipeSerializers.registerRecipeSerializers(eventBus);
		DBMSoundEvents.registerSoundEvents(eventBus);
		DBMTileEntities.registerTileEntities(eventBus);
		QuestTasks.registerQuestTasks(eventBus);

		eventBus.addListener(CommonHandler::onCommonSetup);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> eventBus.addListener(ClientHandler::onClientSetup));
	}

	/**
	 * Returns a new DaBoisMod resource location.
	 * @param name the name of the registry.
	 * @return the new resource location.
	 */
	public static ResourceLocation modLocation(String name) {
		return new ResourceLocation(MODID, name);
	}

	/**
	 * Gets the interface stored in a {@link LazyOptional}, and if not present, throws an {@link IllegalArgumentException}.
	 * @param lazyOptional the lazy optional which may or may not store the interface.
	 * @return the interface instance.
	 * @throws IllegalArgumentException if the interface is not present.
	 */
	public static <T> T get(LazyOptional<T> lazyOptional) {
		return lazyOptional.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
	}

	/**
	 * Sends a debug message to the console.
	 * @param obj the message to send.
	 */
	public static void debug(Object obj) {
		DaBoisMod.LOGGER.debug(obj);
	}
}