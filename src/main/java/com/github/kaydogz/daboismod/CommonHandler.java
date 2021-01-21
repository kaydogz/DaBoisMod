package com.github.kaydogz.daboismod;

import com.github.kaydogz.daboismod.capability.DBMCapabilityHandler;
import com.github.kaydogz.daboismod.capability.base.ILivingCapability;
import com.github.kaydogz.daboismod.capability.base.IPlayerCapability;
import com.github.kaydogz.daboismod.capability.base.IVillagerCapability;
import com.github.kaydogz.daboismod.capability.provider.LivingProvider;
import com.github.kaydogz.daboismod.capability.provider.PlayerProvider;
import com.github.kaydogz.daboismod.capability.provider.VillagerProvider;
import com.github.kaydogz.daboismod.client.DBMKeyBindings;
import com.github.kaydogz.daboismod.command.DBMCommand;
import com.github.kaydogz.daboismod.command.argument.DBMArgumentTypes;
import com.github.kaydogz.daboismod.command.argument.QuestTaskArgument;
import com.github.kaydogz.daboismod.config.DBMConfigHandler;
import com.github.kaydogz.daboismod.data.DBMLootTables;
import com.github.kaydogz.daboismod.enchantment.MagnetismEnchantment;
import com.github.kaydogz.daboismod.entity.DBMEntities;
import com.github.kaydogz.daboismod.item.AmberCrownItem;
import com.github.kaydogz.daboismod.item.CrownHelper;
import com.github.kaydogz.daboismod.item.CrownItem;
import com.github.kaydogz.daboismod.network.DBMNetworkHandler;
import com.github.kaydogz.daboismod.network.client.CUpdateMagneticPacket;
import com.github.kaydogz.daboismod.network.server.SUpdateQuestsPacket;
import com.github.kaydogz.daboismod.network.server.SUpdateRealmFallingPacket;
import com.github.kaydogz.daboismod.network.server.SUpdateSkinTonePacket;
import com.github.kaydogz.daboismod.potion.DBMEffects;
import com.github.kaydogz.daboismod.quest.KillEntitiesQuestTask;
import com.github.kaydogz.daboismod.quest.Quest;
import com.github.kaydogz.daboismod.stats.DBMStats;
import com.github.kaydogz.daboismod.world.biome.DBMBiomeMaker;
import com.github.kaydogz.daboismod.world.biome.DBMBiomes;
import com.github.kaydogz.daboismod.world.gen.feature.DBMConfiguredFeatures;
import com.github.kaydogz.daboismod.world.gen.surfacebuilder.DBMConfiguredSurfaceBuilders;
import net.minecraft.client.Minecraft;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.RegistryKey;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class CommonHandler {

	public static void onCommonSetup(final FMLCommonSetupEvent event) {
		QuestTaskArgument.createExamples();
		DBMNetworkHandler.registerPackets();
		DBMCapabilityHandler.registerCapabilities();
		event.enqueueWork(() -> {
			DBMEntities.applyEntityAttributes();
			DBMEntities.registerSpawnPlacements();
			DBMConfiguredFeatures.registerConfiguredFeatures();
			DBMConfiguredSurfaceBuilders.registerConfiguredSurfaceBuilders();
			DBMBiomes.setupBiomes();
			DBMArgumentTypes.registerSerializers();
		});
		DBMStats.registerStats();
	}

	@Mod.EventBusSubscriber(modid = DaBoisMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ModEvents {

		@SubscribeEvent
		public static void onGatherData(final GatherDataEvent event) {
			final DataGenerator generator = event.getGenerator();

			// Server Data
			if (event.includeServer()) {
				generator.addProvider(new DBMLootTables(generator));
			}
		}
	}

	@Mod.EventBusSubscriber(modid = DaBoisMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class ForgeEvents {

		@SubscribeEvent
		public static void onRegisterCommands(final RegisterCommandsEvent event) {
			DBMCommand.registerCommand(event.getDispatcher());
		}

		@SubscribeEvent(priority = EventPriority.HIGH)
		public static void onBiomeLoading(final BiomeLoadingEvent event) {
			if (event.getName() != null) {
				DBMBiomeMaker.modifyBiomes(RegistryKey.getOrCreateKey(ForgeRegistries.Keys.BIOMES, event.getName()), event.getSpawns(), event.getGeneration());
			}
		}

		@SubscribeEvent
		public static void onPlayerLoggedIn(final PlayerEvent.PlayerLoggedInEvent event) {
			PlayerEntity player = event.getPlayer();
			if (!player.world.isRemote) {
				DBMNetworkHandler.sendToAllTrackingEntityAndSelf(new SUpdateQuestsPacket(DaBoisMod.get(PlayerProvider.getCapabilityOf(player)).getQuests(), player.getEntityId()), player);
			}
		}

		@SubscribeEvent
		public static void onPlayerChangedDimension(final PlayerEvent.PlayerChangedDimensionEvent event) {
			PlayerEntity player = event.getPlayer();
			if (!player.world.isRemote) {
				DBMNetworkHandler.sendToAllTrackingEntityAndSelf(new SUpdateQuestsPacket(DaBoisMod.get(PlayerProvider.getCapabilityOf(player)).getQuests(), player.getEntityId()), player);
			}
		}

		@SubscribeEvent
		public static void onPlayerRespawn(final PlayerEvent.PlayerRespawnEvent event) {
			PlayerEntity player = event.getPlayer();
			if (!player.world.isRemote) {
				DBMNetworkHandler.sendToAllTrackingEntityAndSelf(new SUpdateQuestsPacket(DaBoisMod.get(PlayerProvider.getCapabilityOf(player)).getQuests(), player.getEntityId()), player);
			}
		}

		@SubscribeEvent
		public static void onPlayerClone(final PlayerEvent.Clone event) {
			DaBoisMod.get(PlayerProvider.getCapabilityOf(event.getPlayer())).setQuests(DaBoisMod.get(PlayerProvider.getCapabilityOf(event.getOriginal())).getQuests());
		}

		@SubscribeEvent
		public static void onPlayerStartTracking(final PlayerEvent.StartTracking event) {
			ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
			if (event.getTarget() instanceof LivingEntity) {
				int entityId = event.getTarget().getEntityId();
				DBMNetworkHandler.sendToPlayer(new SUpdateRealmFallingPacket(DaBoisMod.get(LivingProvider.getCapabilityOf(event.getTarget())).isRealmFalling(), entityId), player);
				if (event.getTarget() instanceof VillagerEntity) {
					DBMNetworkHandler.sendToPlayer(new SUpdateSkinTonePacket(DaBoisMod.get(VillagerProvider.getCapabilityOf(event.getTarget())).getSkinTone(), entityId), player);
				}
			}
		}

		@SubscribeEvent
		public static void onEntityJoinWorld(final EntityJoinWorldEvent event) {

			// Add and Sync Villager Skin Tone
			if (event.getEntity() instanceof VillagerEntity) {
				VillagerEntity villager = (VillagerEntity) event.getEntity();
				LazyOptional<IVillagerCapability> lazyCap = VillagerProvider.getCapabilityOf(villager);
				if (lazyCap.isPresent()) {
					IVillagerCapability cap = DaBoisMod.get(lazyCap);
					if (!cap.hasSpawned()) {
						cap.setSpawned(true);
						if (!event.getWorld().isRemote) {
							int skinTone = villager.world.getRandom().nextInt(DBMConfigHandler.COMMON.skinToneCount.get()) + 1;
							DaBoisMod.get(lazyCap).setSkinTone(skinTone);
							DBMNetworkHandler.sendToAllTrackingEntity(new SUpdateSkinTonePacket(skinTone, villager.getEntityId()), villager);
						}
					}
				}
			}
		}

		@SubscribeEvent
		public static void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
			LivingEntity entity = event.getEntityLiving();

			// Manage Botswana Radiation
			if (entity.world.getBiome(entity.getPosition()).getRegistryName().equals(DBMBiomes.BOTSWANA.getId())) {
				if (!entity.isPotionActive(DBMEffects.RADIATION.get())) {
					entity.addPotionEffect(new EffectInstance(DBMEffects.RADIATION.get(), 120000, 0, false, false, true));
				}
			} else if (entity.isInWaterRainOrBubbleColumn()) {
				entity.removePotionEffect(DBMEffects.RADIATION.get());
			}
		}

		@SubscribeEvent
		public static void onLivingEquipmentChange(final LivingEquipmentChangeEvent event) {
			if (event.getEntityLiving() instanceof PlayerEntity && event.getSlot().equals(EquipmentSlotType.HEAD)) {
				PlayerEntity player = (PlayerEntity) event.getEntityLiving();

				// Deactivate Crown When Unequipped
				ItemStack fromStack = event.getFrom();
				if (fromStack.getItem() instanceof CrownItem && CrownHelper.isActivated(fromStack)) {
					CrownHelper.deactivateCrown(fromStack, player, false);
				}
			}
		}

		@SubscribeEvent
		public static void onEntityEyeHeight(final EntityEvent.Size event) {
			if (event.getEntity() instanceof PlayerEntity && event.getEntity().isAddedToWorld()) {
				PlayerEntity player = (PlayerEntity) event.getEntity();

				// Activated Crown Eye Height Changing
				ItemStack helmetSlotStack = (player.inventory != null) ? player.getItemStackFromSlot(EquipmentSlotType.HEAD) : ItemStack.EMPTY;
				if (helmetSlotStack.getItem() instanceof CrownItem && CrownHelper.isActivated(helmetSlotStack)) {
					CrownItem crownItem = (CrownItem) helmetSlotStack.getItem();
					if (crownItem.shouldScalePlayer(helmetSlotStack, player)) {
						float scale = crownItem.getScale(helmetSlotStack, player);
						event.setNewEyeHeight(event.getOldEyeHeight() * scale);
						event.setNewSize(EntitySize.flexible(event.getOldSize().width * scale, event.getOldSize().height * scale));
					}
				}
			}
		}

		@SubscribeEvent
		public static void onPlayerTick(final TickEvent.PlayerTickEvent event) {
			PlayerEntity player = event.player;

			// Activated Crown Player Ticking
			ItemStack helmetSlotStack = player.getItemStackFromSlot(EquipmentSlotType.HEAD);
			if (helmetSlotStack.getItem() instanceof CrownItem && CrownHelper.isActivated(helmetSlotStack)) {
				((CrownItem) helmetSlotStack.getItem()).activatedTick(helmetSlotStack, player);
			}

			// Disable Bleeding Sprinting
			if (player.isPotionActive(DBMEffects.BLEEDING.get())) player.setSprinting(false);

			// Magnetic Attraction
			LazyOptional<IPlayerCapability> lazyPlayerCap = PlayerProvider.getCapabilityOf(player);
			if (lazyPlayerCap.isPresent()) {
				IPlayerCapability playerCap = DaBoisMod.get(lazyPlayerCap);
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
							if (player.world.isRemote && Minecraft.getInstance().player == player && !DBMKeyBindings.activate_magnetism.isKeyDown()) {
								DBMNetworkHandler.sendToServer(new CUpdateMagneticPacket(false));
							}
						});
					} else {
						playerCap.setMagnetic(false);
					}
				}
			}
		}

		@SubscribeEvent
		public static void onWorldTick(final TickEvent.WorldTickEvent event) {
			/*
			// Manages Falling From Sky
			if (!event.world.isRemote && event.phase == TickEvent.Phase.END) {
				ServerWorld world = (ServerWorld) event.world;

				if (world.dimension.getType() == DimensionType.byName(DBMDimensionTypes.REALM_OF_THE_ANCIENTS_LOCATION)) {
					world.getEntities().forEach((entity) -> {
						if (((RealmOfTheAncientsDimension) world.dimension).shouldFallFromSky(entity) && entity.getPosition().getY() < 0) {
							if (entity instanceof LivingEntity) {
								DaBoisMod.get(LivingProvider.getCapabilityOf(entity)).setRealmFalling(true);
								DBMNetworkHandler.sendToAllTrackingEntityAndSelf(new SUpdateRealmFallingPacket(true, entity.getEntityId()), entity);
							}
							DBMTeleporter.teleportEntityToOverworldTop(entity);
						}
					});
				}
			}
			 */
		}

		@SubscribeEvent
		public static void onLivingFall(final LivingFallEvent event) {
			LivingEntity entity = event.getEntityLiving();

			// Manages Canceling Falling From Sky Damage
			LazyOptional<ILivingCapability> lazyCap = LivingProvider.getCapabilityOf(entity);
			if (lazyCap.isPresent()) {
				ILivingCapability livingCap = DaBoisMod.get(lazyCap);
				if (livingCap.isRealmFalling()) {
					event.setCanceled(true);
					livingCap.setRealmFalling(false);
					DBMNetworkHandler.sendToAllTrackingEntityAndSelf(new SUpdateRealmFallingPacket(false, entity.getEntityId()), entity);
				}
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
						IPlayerCapability playerCap = DaBoisMod.get(PlayerProvider.getCapabilityOf(playerAttacker));
						if (!playerCap.getQuests().isEmpty()) {
							for (Quest quest : playerCap.getQuests()) {
								if (quest.getQuestTask() instanceof KillEntitiesQuestTask && event.getEntity().getType() == ((KillEntitiesQuestTask) quest.getQuestTask()).getEntityTypeToKill()) quest.increaseCount();
							}
							DBMNetworkHandler.sendToAllTrackingEntityAndSelf(new SUpdateQuestsPacket(playerCap.getQuests(), playerAttacker.getEntityId()), playerAttacker);
						}
					}
				}
			}
		}

		@SubscribeEvent
		public static void onLivingAttack(final LivingAttackEvent event) {

			if (event.getEntity() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) event.getEntity();

				// Activated Crown Player Projectile Impact
				if (event.getSource().isProjectile()) {
					ItemStack helmetSlotStack = player.getItemStackFromSlot(EquipmentSlotType.HEAD);
					if (helmetSlotStack.getItem() instanceof CrownItem && CrownHelper.isActivated(helmetSlotStack)) {
						CrownItem crownItem = (CrownItem) helmetSlotStack.getItem();
						if (crownItem instanceof AmberCrownItem) {
							event.setCanceled(true);
						}
					}
				}
			}
		}
	}
}
