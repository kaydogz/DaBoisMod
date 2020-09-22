package com.github.kaydogz.daboismod;

import com.github.kaydogz.daboismod.capability.DBMCapabilityHandler;
import com.github.kaydogz.daboismod.capability.base.ILivingCap;
import com.github.kaydogz.daboismod.capability.base.IPlayerCap;
import com.github.kaydogz.daboismod.capability.provider.LivingCapability;
import com.github.kaydogz.daboismod.capability.provider.PlayerCapability;
import com.github.kaydogz.daboismod.client.DBMKeyBindings;
import com.github.kaydogz.daboismod.command.QuestCommand;
import com.github.kaydogz.daboismod.command.TravelCommand;
import com.github.kaydogz.daboismod.enchantment.MagnetismEnchantment;
import com.github.kaydogz.daboismod.item.CryptidGemItem;
import com.github.kaydogz.daboismod.item.GodsCrownHelper;
import com.github.kaydogz.daboismod.item.GodsCrownItem;
import com.github.kaydogz.daboismod.network.DBMPacketHandler;
import com.github.kaydogz.daboismod.network.client.CUpdateMagneticPacket;
import com.github.kaydogz.daboismod.network.server.SUpdateFallingFromSkyPacket;
import com.github.kaydogz.daboismod.network.server.SUpdateQuestsPacket;
import com.github.kaydogz.daboismod.potion.DBMEffects;
import com.github.kaydogz.daboismod.quest.*;
import com.github.kaydogz.daboismod.stats.DBMStats;
import com.github.kaydogz.daboismod.world.DBMTeleporter;
import com.github.kaydogz.daboismod.world.dimension.DBMDimensionTypes;
import com.github.kaydogz.daboismod.world.dimension.RealmOfTheAncientsDimension;
import com.github.kaydogz.daboismod.world.gen.DBMGeneration;
import com.github.kaydogz.daboismod.world.gen.feature.structure.DBMStructurePieceTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public class CommonHandler {

	public static void onCommonSetup(final FMLCommonSetupEvent event) {

		DBMStructurePieceTypes.registerStructurePieceTypes();
		DBMPacketHandler.registerPackets();
		DBMCapabilityHandler.registerCapabilities();
		DBMGeneration.createFeatureConfigs();
		DeferredWorkQueue.runLater(DBMGeneration::setupGeneration);
		DBMStats.registerStats();
	}

	@Mod.EventBusSubscriber(modid = DaBoisMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class ForgeEvents {

		@SubscribeEvent
		public static void onRegisterDimensions(final RegisterDimensionsEvent event) {
			DBMDimensionTypes.registerDimensions();
		}

		@SubscribeEvent
		public static void onServerStarting(final FMLServerStartingEvent event) {
			TravelCommand.registerCommand(event.getCommandDispatcher());
			QuestCommand.registerCommand(event.getCommandDispatcher());
		}

		@SubscribeEvent
		public static void onPlayerLoggedIn(final PlayerEvent.PlayerLoggedInEvent event) {
			PlayerEntity player = event.getPlayer();
			if (!player.world.isRemote) {
				DBMPacketHandler.sendToAllTrackingEntityAndSelf(new SUpdateQuestsPacket(DaBoisMod.get(PlayerCapability.getCapabilityOf(player)).getQuests(), player.getEntityId()), player);
			}
		}

		@SubscribeEvent
		public static void onPlayerChangedDimension(final PlayerEvent.PlayerChangedDimensionEvent event) {
			PlayerEntity player = event.getPlayer();
			if (!player.world.isRemote) {
				DBMPacketHandler.sendToAllTrackingEntityAndSelf(new SUpdateQuestsPacket(DaBoisMod.get(PlayerCapability.getCapabilityOf(player)).getQuests(), player.getEntityId()), player);
			}
		}

		@SubscribeEvent
		public static void onPlayerRespawn(final PlayerEvent.PlayerRespawnEvent event) {
			PlayerEntity player = event.getPlayer();
			if (!player.world.isRemote) {
				DBMPacketHandler.sendToAllTrackingEntityAndSelf(new SUpdateQuestsPacket(DaBoisMod.get(PlayerCapability.getCapabilityOf(player)).getQuests(), player.getEntityId()), player);
			}
		}

		@SubscribeEvent
		public static void onPlayerClone(final PlayerEvent.Clone event) {
			DaBoisMod.get(PlayerCapability.getCapabilityOf(event.getPlayer())).setQuests(DaBoisMod.get(PlayerCapability.getCapabilityOf(event.getOriginal())).getQuests());
		}

		@SubscribeEvent
		public static void onPlayerStartTracking(final PlayerEvent.StartTracking event) {
			ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
			if (event.getTarget() instanceof LivingEntity) {
				DBMPacketHandler.sendToPlayer(new SUpdateFallingFromSkyPacket(DaBoisMod.get(LivingCapability.getCapabilityOf(event.getTarget())).isFallingFromSky(), event.getTarget().getEntityId()), player);
			}
		}

		@SubscribeEvent
		public static void onEntityEyeHeight(final EntityEvent.EyeHeight event) {
			if (event.getEntity() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) event.getEntity();

				// Activated Cryptid Gem Eye Height Changing
				ItemStack helmetSlotStack = (player.inventory != null) ? player.getItemStackFromSlot(EquipmentSlotType.HEAD) : ItemStack.EMPTY;
				if (helmetSlotStack.getItem() instanceof GodsCrownItem && GodsCrownHelper.isActivated(helmetSlotStack)) {
					Item insertedItem = GodsCrownHelper.getInsertedGem(helmetSlotStack).getItem();
					if (insertedItem instanceof CryptidGemItem) event.setNewHeight(((CryptidGemItem) insertedItem).onActivatedPlayerEyeHeight(
							player,
							event.getPose(),
							event.getSize(),
							event.getOldHeight()
					));
				}
			}
		}

		@SubscribeEvent
		public static void onPlayerTick(final TickEvent.PlayerTickEvent event) {
			PlayerEntity player = event.player;

			// Activated Cryptid Gem Player Ticking
			ItemStack helmetSlotStack = player.getItemStackFromSlot(EquipmentSlotType.HEAD);
			if (helmetSlotStack.getItem() instanceof GodsCrownItem && GodsCrownHelper.isActivated(helmetSlotStack)) {
				Item insertedItem = GodsCrownHelper.getInsertedGem(helmetSlotStack).getItem();
				if (insertedItem instanceof CryptidGemItem) ((CryptidGemItem) insertedItem).activatedTick(helmetSlotStack, player);
			}

			// Disable Bleeding Sprinting
			if (player.isPotionActive(DBMEffects.BLEEDING.get())) player.setSprinting(false);

			// Magnetic Attraction
			LazyOptional<IPlayerCap> lazyPlayerCap = PlayerCapability.getCapabilityOf(player);
			if (lazyPlayerCap.isPresent()) {
				IPlayerCap playerCap = DaBoisMod.get(lazyPlayerCap);
				if (playerCap.isMagnetic()) {
					if (MagnetismEnchantment.isHoldingMagneticItem(player)) {
						for (ItemEntity item : player.world.getEntitiesWithinAABB(ItemEntity.class, player.getBoundingBox().grow(8.0D))) {
							double factor = 0.1D;
							item.setMotion(
									(player.getPosX() - item.getPosX()) * factor,
									(item.getPosY() + 0.4D - player.getPosY()) < 0.0D ? (player.getPosY() + 3.0D - item.getPosY()) * factor : item.getMotion().y,
									(player.getPosZ() - item.getPosZ()) * factor
							);
						}
						DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
							if (!DBMKeyBindings.activate_magnetism.isKeyDown()) DBMPacketHandler.sendToServer(new CUpdateMagneticPacket(false));
						});
					} else {
						playerCap.setMagnetic(false);
					}
				}
			}
		}

		@SubscribeEvent
		public static void onWorldTick(final TickEvent.WorldTickEvent event) {

			// Manages Falling From Sky
			if (event.side.isServer() && event.phase == TickEvent.Phase.END) {
				ServerWorld world = (ServerWorld) event.world;

				if (world.dimension.getType() == DimensionType.byName(DBMDimensionTypes.REALM_OF_THE_ANCIENTS_LOCATION)) {
					world.getEntities().forEach((entity) -> {
						if (((RealmOfTheAncientsDimension) world.dimension).shouldFallFromSky(entity) && entity.getPosition().getY() < 0) {
							if (entity instanceof LivingEntity) {
								DaBoisMod.get(LivingCapability.getCapabilityOf(entity)).setFallingFromSky(true);
								DBMPacketHandler.sendToAllTrackingEntityAndSelf(new SUpdateFallingFromSkyPacket(true, entity.getEntityId()), entity);
							}
							DBMTeleporter.teleportEntityToOverworldTop(entity);
						}
					});
				}
			}
		}


		@SubscribeEvent
		public static void onBlockBreak(final BlockEvent.BreakEvent event) {
			if (!event.getPlayer().world.isRemote) {
				ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();

				// Break Blocks Quests
				IPlayerCap playerCap = DaBoisMod.get(PlayerCapability.getCapabilityOf(player));
				if (!playerCap.getQuests().isEmpty()) {
					for (Quest quest : playerCap.getQuests()) {
						if (quest.getQuestTask() instanceof BreakBlocksQuestTask && event.getState().getBlock() == ((BreakBlocksQuestTask) quest.getQuestTask()).getBlockToBreak()) quest.increaseCount();
					}
					DBMPacketHandler.sendToAllTrackingEntityAndSelf(new SUpdateQuestsPacket(playerCap.getQuests(), player.getEntityId()), player);
				}
			}
		}

		@SubscribeEvent
		public static void onEntityPlaceBlock(final BlockEvent.EntityPlaceEvent event) {
			if (event.getEntity() instanceof PlayerEntity && !event.getEntity().world.isRemote) {
				ServerPlayerEntity player = (ServerPlayerEntity) event.getEntity();

				// Place Blocks Quests
				IPlayerCap playerCap = DaBoisMod.get(PlayerCapability.getCapabilityOf(player));
				if (!playerCap.getQuests().isEmpty()) {
					for (Quest quest : playerCap.getQuests()) {
						if (quest.getQuestTask() instanceof PlaceBlocksQuestTask && event.getState().getBlock() == ((PlaceBlocksQuestTask) quest.getQuestTask()).getBlockToPlace()) quest.increaseCount();
					}
					DBMPacketHandler.sendToAllTrackingEntityAndSelf(new SUpdateQuestsPacket(playerCap.getQuests(), player.getEntityId()), player);
				}
			}
		}

		@SubscribeEvent
		public static void onPlayerItemCrafted(final PlayerEvent.ItemCraftedEvent event) {
			if (!event.getPlayer().world.isRemote) {
				ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();

				// Craft Items Quests
				IPlayerCap playerCap = DaBoisMod.get(PlayerCapability.getCapabilityOf(player));
				if (!playerCap.getQuests().isEmpty()) {
					for (Quest quest : playerCap.getQuests()) {
						if (quest.getQuestTask() instanceof CraftItemsQuestTask && event.getCrafting().getItem() == ((CraftItemsQuestTask) quest.getQuestTask()).getItemToCraft()) quest.increaseCount();
					}
					DBMPacketHandler.sendToAllTrackingEntityAndSelf(new SUpdateQuestsPacket(playerCap.getQuests(), player.getEntityId()), player);
				}
			}
		}

		@SubscribeEvent
		public static void onLivingFall(final LivingFallEvent event) {
			LivingEntity entity = event.getEntityLiving();

			// Manages Canceling Falling From Sky Damage
			ILivingCap livingCap = DaBoisMod.get(LivingCapability.getCapabilityOf(entity));
			if (livingCap.isFallingFromSky()) {
				event.setCanceled(true);
				livingCap.setFallingFromSky(false);
				DBMPacketHandler.sendToAllTrackingEntityAndSelf(new SUpdateFallingFromSkyPacket(false, entity.getEntityId()), entity);
			}

			// Nullify Fall Damage For Indica Stonage
			if (entity.isPotionActive(DBMEffects.INDICA_STONAGE.get())) event.setCanceled(true);
		}

		@SubscribeEvent
		public static void onLivingDeath(final LivingDeathEvent event) {
			if (!event.getEntity().world.isRemote) {
				if (event.getSource() instanceof EntityDamageSource) {
					EntityDamageSource entityDamageSource = (EntityDamageSource) event.getSource();
					if (entityDamageSource.getTrueSource() instanceof ServerPlayerEntity) {
						ServerPlayerEntity playerAttacker = (ServerPlayerEntity) entityDamageSource.getTrueSource();

						// Kill Entities Quests
						IPlayerCap playerCap = DaBoisMod.get(PlayerCapability.getCapabilityOf(playerAttacker));
						if (!playerCap.getQuests().isEmpty()) {
							for (Quest quest : playerCap.getQuests()) {
								if (quest.getQuestTask() instanceof KillEntitiesQuestTask && event.getEntity().getType() == ((KillEntitiesQuestTask) quest.getQuestTask()).getEntityTypeToKill()) quest.increaseCount();
							}
							DBMPacketHandler.sendToAllTrackingEntityAndSelf(new SUpdateQuestsPacket(playerCap.getQuests(), playerAttacker.getEntityId()), playerAttacker);
						}
					}
				}
			}
		}
	}
}
