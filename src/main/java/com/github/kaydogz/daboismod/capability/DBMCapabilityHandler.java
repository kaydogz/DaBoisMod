package com.github.kaydogz.daboismod.capability;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.ICrownCapability;
import com.github.kaydogz.daboismod.capability.base.ILivingCapability;
import com.github.kaydogz.daboismod.capability.base.IPlayerCapability;
import com.github.kaydogz.daboismod.capability.provider.CrownProvider;
import com.github.kaydogz.daboismod.capability.provider.LivingProvider;
import com.github.kaydogz.daboismod.capability.provider.PlayerProvider;
import com.github.kaydogz.daboismod.item.CrownItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DaBoisMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DBMCapabilityHandler {

	// Entity
	public static final ResourceLocation LIVING_CAP_LOCATION = DaBoisMod.modLocation("living_cap");
	public static final ResourceLocation PLAYER_CAP_LOCATION = DaBoisMod.modLocation("player_cap");
	
	// Item Stack
	public static final ResourceLocation CROWN_CAP_LOCATION = DaBoisMod.modLocation("crown_cap");

	@SubscribeEvent
	public static void onAttachEntityCapability(final AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof LivingEntity) {
			event.addCapability(LIVING_CAP_LOCATION, new LivingProvider());
			if (event.getObject() instanceof PlayerEntity) {
				event.addCapability(PLAYER_CAP_LOCATION, new PlayerProvider());
			}
		}
	}
	
	@SubscribeEvent
	public static void onAttachItemStackCapability(final AttachCapabilitiesEvent<ItemStack> event) {
		if (event.getObject().getItem() instanceof CrownItem) {
			event.addCapability(CROWN_CAP_LOCATION, new CrownProvider());
		}
	}
	
	/**
	 * Registers capabilities for DaBoisMod.
	 */
	public static void registerCapabilities() {
		CapabilityManager.INSTANCE.register(ICrownCapability.class, new CrownCapability.Storage(), CrownCapability::new);
		CapabilityManager.INSTANCE.register(ILivingCapability.class, new LivingCapability.Storage(), LivingCapability::new);
		CapabilityManager.INSTANCE.register(IPlayerCapability.class, new PlayerCapability.Storage(), PlayerCapability::new);
	}
}
