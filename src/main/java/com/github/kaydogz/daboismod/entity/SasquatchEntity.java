package com.github.kaydogz.daboismod.entity;

import com.github.kaydogz.daboismod.item.DBMItems;
import com.github.kaydogz.daboismod.network.DBMNetworkHandler;
import com.github.kaydogz.daboismod.network.server.SSasquatchSmashPacket;
import com.github.kaydogz.daboismod.tags.DBMBlockTags;
import com.github.kaydogz.daboismod.util.DBMSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.World;

import java.util.Random;

public class SasquatchEntity extends CryptidEntity {

	private static final DataParameter<Boolean> IS_GIANT = EntityDataManager.createKey(SasquatchEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> BECOMING_GIANT = EntityDataManager.createKey(SasquatchEntity.class, DataSerializers.BOOLEAN);

	protected double maxLaunch = 2.5D;
	protected double minLaunch = 0.5D;

	public SasquatchEntity(EntityType<? extends CryptidEntity> type, World worldIn) {
		super(type, worldIn);
		this.getNavigator().setCanSwim(true);
		this.experienceValue = 2400;
	}
	
	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(IS_GIANT, false);
		this.dataManager.register(BECOMING_GIANT, false);
	}
	
	@Override
	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(275.0D);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(25.0D);
		this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).setBaseValue(14.0D);
		this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.15D);
		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50.0D);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(2, new RestrictSunGoal(this));
	    this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
	    this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, CreeperEntity.class, 8.0F, 1.0D, 1.3D));
	    this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0F));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 15.0F));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp(SasquatchEntity.class));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PigEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, CowEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, RabbitEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AbstractIllagerEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AbstractHorseEntity.class, true));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, BeeEntity.class, true));
	}
	
	@Override
	protected void updateAITasks() {
		super.updateAITasks();
		if (this.getHealth() <= 3 * this.getMaxHealth() / 4 && !this.world.getWorldInfo().isThundering()) {
			if (!this.world.getWorldInfo().isRaining()) this.world.getWorldInfo().setRaining(true);
			this.world.getWorldInfo().setThundering(true);
		}
		if (this.getHealth() <= this.getMaxHealth() / 2) this.becomeGiant();
	}
	
	@Override
	public float getRenderScale() {
		return this.isGiant() ? 5.0F : 1.0F;
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		boolean flag = super.attackEntityAsMob(entityIn);
		Random rand = new Random();
		if (flag && rand.nextBoolean()) {
			boolean isGiant = this.isGiant();
			if (!isGiant || rand.nextBoolean()) {
				double factor = ((isGiant) ? 2.0D : 1.0D) * ((entityIn instanceof PlayerEntity) ? 1.0D : 0.3D);
				entityIn.setMotion(entityIn.getMotion().add(0.0D, factor * (rand.nextDouble() * (this.maxLaunch - this.minLaunch) + this.minLaunch), 0.0D));
			}
		}
		return flag;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return (this.getHealth() >= this.getMaxHealth() / 2 || !source.isProjectile()) && super.attackEntityFrom(source, amount);
	}
	
	@Override
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
		this.world.getWorldInfo().setThundering(false);
		this.world.getWorldInfo().setRaining(false);
	}
	
	@Override
	public boolean onLivingFall(float distance, float damageMultiplier) {
		if (this.isGiant() && distance > 10.0F) {
			for (Entity entity : this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox().grow(7.0D, 2.0D, 7.0D), (entity) -> entity instanceof LivingEntity && entity.onGround)) {
				entity.addVelocity(0.0D, 1.5D, 0.0D);
				entity.velocityChanged = true;
			}

			DBMNetworkHandler.sendToAllTrackingEntity(new SSasquatchSmashPacket(this.getPosX(), this.getPosY(), this.getPosZ()), this);

			this.playSound(this.getSmashSound(), 3.0F, 1.0F);
			return false;
		}
		return super.onLivingFall(distance, damageMultiplier);
	}
	
	public void becomeGiant() {
		if (!this.isNormal()) return;
		this.setBecomingGiant(true);
		this.setMotion(this.getMotion().add(0.0D, 2.0D, 0.0D));
	}
	
	@Override
	public void livingTick() {
		super.livingTick();

		if (this.isBecomingGiant()) {
			if (!this.isGiant()) {
				this.setMotion(this.getMotion().add(0.0D, 1.2D, 0.0D));
				this.noClip = this.collidedVertically && !this.noClip;
				this.destroyBlocksInAABB(this.getBoundingBox());
				if (this.getPosY() > 125.0D) {
					this.setInvulnerable(false);
					this.setGiant(true);
					this.setMotion(this.getMotion().subtract(0.0D, 5.0D, 0.0D));
					this.playSound(this.getTransitionSound(), 15.0F, 1.0F);
				}
			} else {
				this.recalculateSize();
				this.noClip = false;
				this.setBecomingGiant(false);
			}
		}
	}
	
	private boolean destroyBlocksInAABB(AxisAlignedBB box) {
		int minX = MathHelper.floor(box.minX);
		int minY = MathHelper.floor(box.minY);
		int minZ = MathHelper.floor(box.minZ);
		int maxX = MathHelper.floor(box.maxX);
		int maxY = MathHelper.floor(box.maxY);
		int maxZ = MathHelper.floor(box.maxZ);
		boolean flag = false;
		boolean flag1 = false;
		
		for (int x = minX; x <= maxX; ++x) {
			for (int y = (minY + maxY) / 2; y <= maxY; ++y) {
				for (int z = minZ; z <= maxZ; ++z) {
					
					BlockPos blockpos = new BlockPos(x, y, z);
					BlockState blockstate = this.world.getBlockState(blockpos);
					Block block = blockstate.getBlock();
					if (!blockstate.isAir(this.world, blockpos) && blockstate.getMaterial() != Material.FIRE) {
						if (net.minecraftforge.common.ForgeHooks.canEntityDestroy(this.world, blockpos, this) && !DBMBlockTags.SASQUATCH_IMMUNE.contains(block)) {
							flag1 = this.world.removeBlock(blockpos, false) || flag1;
						} else {
							flag = true;
						}
					}
				}
			}
		}

		if (flag1) {
			BlockPos blockpos1 = new BlockPos(minX + this.rand.nextInt(maxX - minX + 1), minY + this.rand.nextInt(maxY - minY + 1), minZ + this.rand.nextInt(maxZ - minZ + 1));
			this.world.playEvent(2008, blockpos1, 0);
		}

		return flag;
	}
	
	protected SoundEvent getTransitionSound() {
		return DBMSoundEvents.ENTITY_SASQUATCH_TRANSITION.get();
	}
	
	@Override
	public void playAmbientSound() {
		SoundEvent soundevent = this.getAmbientSound();
		if (soundevent != null) {
			this.world.playMovingSound(null, this, soundevent, this.getSoundCategory(), this.getSoundVolume(), this.getSoundPitch());
		}
	}
	
	protected SoundEvent getSmashSound() {
		return DBMSoundEvents.ENTITY_SASQUATCH_SMASH.get();
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return DBMSoundEvents.ENTITY_SASQUATCH_AMBIENT.get();
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return DBMSoundEvents.ENTITY_SASQUATCH_DEATH.get();
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return DBMSoundEvents.ENTITY_SASQUATCH_HURT.get();
	}

	public final boolean isGiant() {
		return this.dataManager.get(IS_GIANT);
	}

	protected final void setGiant(boolean giant) {
		this.dataManager.set(IS_GIANT, giant);
	}

	public boolean isNormal() {
		return !isGiant() && !isBecomingGiant();
	}
	
	public final boolean isBecomingGiant() {
		return this.dataManager.get(BECOMING_GIANT);
	}

	protected final void setBecomingGiant(boolean becomingGiant) {
		this.dataManager.set(BECOMING_GIANT, becomingGiant);
	}

	@Override
	public Item getCryptidDropItem() {
		return DBMItems.AMBER.get();
	}

	@Override
	protected Color getBossBarColor() {
		return BossInfo.Color.YELLOW;
	}
}
