package com.github.kaydogz.daboismod.entity;

import com.github.kaydogz.daboismod.item.DBMItems;
import com.github.kaydogz.daboismod.potion.DBMEffects;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;

public class WerewolfEntity extends CryptidEntity {

	private static final DataParameter<Boolean> IS_MOLTEN = EntityDataManager.createKey(WerewolfEntity.class, DataSerializers.BOOLEAN);
	
	protected WerewolfEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
		this.getNavigator().setCanSwim(true);
		this.experienceValue = 2400;
	}
	
	@Override
	protected void registerData() {
		super.registerData();
		this.dataManager.register(WerewolfEntity.IS_MOLTEN, false);
	}
	
	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.registerAttributes()
				.createMutableAttribute(Attributes.ARMOR, 10.0D)
				.createMutableAttribute(Attributes.MAX_HEALTH, 275.0D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.65D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 35.0D)
				.createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 2.0D)
				.createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.2D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 50.0D);
	}
	
	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(2, new RestrictSunGoal(this));
	    this.goalSelector.addGoal(2, new FleeSunGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, true));
	    this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0F));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 15.0F));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp(WerewolfEntity.class));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, SheepEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PigEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, CowEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, RabbitEntity.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, ChickenEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AbstractIllagerEntity.class, true));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, AbstractHorseEntity.class, true));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, LlamaEntity.class, true));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, WolfEntity.class, true));
	}
	
	@Override
	protected void updateAITasks() {
		super.updateAITasks();
		if (this.getHealth() <= 3 * this.getMaxHealth() / 5) this.becomeMolten();
	}
	
	public void becomeMolten() {
		if (this.dataManager.get(WerewolfEntity.IS_MOLTEN)) return;
		this.setNoAI(true);
		this.dataManager.set(WerewolfEntity.IS_MOLTEN, true);
		this.setNoAI(false);
	}
	
	@Override
	public void livingTick() {
		super.livingTick();
		
		if (this.ticksExisted % 15 == 0 && this.onGround && this.world.getBlockState(this.getPosition()).getBlock() == Blocks.AIR && this.dataManager.get(WerewolfEntity.IS_MOLTEN)) {
			this.world.setBlockState(this.getPosition(), Blocks.FIRE.getDefaultState());
		}
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return (!this.dataManager.get(WerewolfEntity.IS_MOLTEN) || (!source.isFireDamage() && (this.isInWaterRainOrBubbleColumn() || source.isUnblockable()))) && super.attackEntityFrom(source, amount);
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		boolean flag = super.attackEntityAsMob(entityIn);
		if (flag && entityIn instanceof LivingEntity) ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(DBMEffects.BLEEDING.get(), 300));
		return flag;
	}
	
	@Override
	public boolean onLivingFall(float distance, float damageMultiplier) {
		return false;
	}
	
	public boolean isMolten() {
		return this.dataManager.get(WerewolfEntity.IS_MOLTEN);
	}
	
	@Override
	public Item getCryptidDropItem() {
		return DBMItems.RUBY.get();
	}

	@Override
	protected BossInfo.Color getBossBarColor() {
		return BossInfo.Color.RED;
	}

}
