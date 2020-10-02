package com.github.kaydogz.daboismod.capability;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.IGodsCrownCap;
import com.github.kaydogz.daboismod.capability.base.ILivingCap;
import com.github.kaydogz.daboismod.capability.base.IPlayerCap;
import com.github.kaydogz.daboismod.capability.provider.GodsCrownCapability;
import com.github.kaydogz.daboismod.capability.provider.LivingCapability;
import com.github.kaydogz.daboismod.capability.provider.PlayerCapability;
import com.github.kaydogz.daboismod.item.GodsCrownItem;
import com.github.kaydogz.daboismod.item.QuestScrollItem;
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
	public static final ResourceLocation GODS_CROWN_CAP_LOCATION = DaBoisMod.modLocation("gods_crown_cap");

	@SubscribeEvent
	public static void onAttachEntityCapability(final AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof LivingEntity) {
			event.addCapability(LIVING_CAP_LOCATION, new LivingCapability());
			if (event.getObject() instanceof PlayerEntity) {
				event.addCapability(PLAYER_CAP_LOCATION, new PlayerCapability());
			}
		}
	}
	
	@SubscribeEvent
	public static void onAttachItemStackCapability(final AttachCapabilitiesEvent<ItemStack> event) {
		if (event.getObject().getItem() instanceof GodsCrownItem) {
			event.addCapability(GODS_CROWN_CAP_LOCATION, new GodsCrownCapability());
		}
	}
	
	/**
	 * Registers capabilities for DaBoisMod.
	 */
	public static void registerCapabilities() {
		CapabilityManager.INSTANCE.register(IGodsCrownCap.class, new GodsCrownCap.Storage(), GodsCrownCap::new);
		CapabilityManager.INSTANCE.register(ILivingCap.class, new LivingCap.Storage(), LivingCap::new);
		CapabilityManager.INSTANCE.register(IPlayerCap.class, new PlayerCap.Storage(), PlayerCap::new);
	}
}
