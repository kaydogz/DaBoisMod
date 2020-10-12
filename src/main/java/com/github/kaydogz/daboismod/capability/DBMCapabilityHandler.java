package com.github.kaydogz.daboismod.capability;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.ICrownCapability;
import com.github.kaydogz.daboismod.capability.base.ILivingCapability;
import com.github.kaydogz.daboismod.capability.base.IPlayerCapability;
import com.github.kaydogz.daboismod.capability.base.IVillagerCapability;
import com.github.kaydogz.daboismod.capability.provider.CrownProvider;
import com.github.kaydogz.daboismod.capability.provider.LivingProvider;
import com.github.kaydogz.daboismod.capability.provider.PlayerProvider;
import com.github.kaydogz.daboismod.capability.provider.VillagerProvider;
import com.github.kaydogz.daboismod.config.DBMConfigHandler;
import com.github.kaydogz.daboismod.item.CrownItem;
import com.github.kaydogz.daboismod.network.DBMPacketHandler;
import com.github.kaydogz.daboismod.network.server.SUpdateSkinTonePacket;
import com.github.kaydogz.daboismod.util.DBMConstants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DaBoisMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DBMCapabilityHandler {

	// Entity
	public static final ResourceLocation LIVING_LOCATION = DaBoisMod.modLocation("living");
	public static final ResourceLocation PLAYER_LOCATION = DaBoisMod.modLocation("player");
	public static final ResourceLocation VILLAGER_LOCATION = DaBoisMod.modLocation("villager");
	
	// Item Stack
	public static final ResourceLocation CROWN_LOCATION = DaBoisMod.modLocation("crown");

	@SubscribeEvent
	public static void onAttachEntityCapability(final AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof LivingEntity) {
			event.addCapability(LIVING_LOCATION, new LivingProvider());
			if (event.getObject() instanceof PlayerEntity) {
				event.addCapability(PLAYER_LOCATION, new PlayerProvider());
			}
			if (event.getObject() instanceof VillagerEntity) {
				event.addCapability(VILLAGER_LOCATION, new VillagerProvider());
			}
		}
	}
	
	@SubscribeEvent
	public static void onAttachItemStackCapability(final AttachCapabilitiesEvent<ItemStack> event) {
		if (event.getObject().getItem() instanceof CrownItem) {
			event.addCapability(CROWN_LOCATION, new CrownProvider());
		}
	}
	
	/**
	 * Registers capabilities for DaBoisMod.
	 */
	public static void registerCapabilities() {
		CapabilityManager.INSTANCE.register(ICrownCapability.class, new CrownCapability.Storage(), CrownCapability::new);
		CapabilityManager.INSTANCE.register(ILivingCapability.class, new LivingCapability.Storage(), LivingCapability::new);
		CapabilityManager.INSTANCE.register(IPlayerCapability.class, new PlayerCapability.Storage(), PlayerCapability::new);
		CapabilityManager.INSTANCE.register(IVillagerCapability.class, new VillagerCapability.Storage(), VillagerCapability::new);
	}
}
