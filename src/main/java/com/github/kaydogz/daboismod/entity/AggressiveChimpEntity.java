package com.github.kaydogz.daboismod.entity;

import com.github.kaydogz.daboismod.world.randomchimpevent.RandomChimpEvent;
import com.github.kaydogz.daboismod.world.randomchimpevent.RandomChimpEventManager;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.GroundPathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class AggressiveChimpEntity extends AbstractChimpEntity {

    @Nullable
    private RandomChimpEvent randomChimpEvent;
    private int wave;
    private boolean canJoinRandomChimpEvent;
    private int joinDelay;

    private final BreakDoorGoal breakDoor = new BreakDoorGoal(this, difficulty -> true);
    private boolean isBreakDoorsTaskSet;

    public AggressiveChimpEntity(EntityType<? extends AggressiveChimpEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 7.0F));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, CowEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, SheepEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, VillagerEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LlamaEntity.class, true));
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        this.world.getTags().getBlockTags().getTagByID(BlockTags.BEDS.getName()).getAllElements().forEach(block -> {
            this.goalSelector.addGoal(1, new BreakBlockGoal(block, this, 1.5F, 32));
        });
    }

    public void setBreakDoorsAItask(boolean enabled) {
        if (this.canBreakDoors() && GroundPathHelper.isGroundNavigator(this)) {
            if (this.isBreakDoorsTaskSet != enabled) {
                this.isBreakDoorsTaskSet = enabled;
                ((GroundPathNavigator) this.getNavigator()).setBreakDoors(enabled);
                if (enabled) {
                    this.goalSelector.addGoal(0, this.breakDoor);
                } else {
                    this.goalSelector.removeGoal(this.breakDoor);
                }
            }
        } else if (this.isBreakDoorsTaskSet) {
            this.goalSelector.removeGoal(this.breakDoor);
            this.isBreakDoorsTaskSet = false;
        }
    }

    public boolean isBreakDoorsTaskSet() {
        return this.isBreakDoorsTaskSet;
    }

    protected boolean canBreakDoors() {
        return true;
    }

    @Override
    public void onDeath(DamageSource cause) {
        if (this.world instanceof ServerWorld) {
            RandomChimpEvent chimpEvent = this.getRandomChimpEvent();
            if (chimpEvent != null) {
                chimpEvent.leaveRandomChimpEvent(this, false);
            }
        }

        super.onDeath(cause);
    }

    @Override
    public void setAttackTarget(@Nullable LivingEntity entitylivingbaseIn) {
        super.setAttackTarget(entitylivingbaseIn);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("CanBreakDoors", this.isBreakDoorsTaskSet());
        compound.putInt("Wave", this.wave);
        compound.putBoolean("CanJoinRandomChimpEvent", this.canJoinRandomChimpEvent);
        if (this.randomChimpEvent != null) {
            compound.putInt("RandomChimpEventId", this.randomChimpEvent.getId());
        }
    }

    @Override
    public void livingTick() {
        if (this.world instanceof ServerWorld && this.isAlive()) {
            RandomChimpEvent chimpEvent = this.getRandomChimpEvent();
            if (this.canJoinRandomChimpEvent()) {
                if (chimpEvent == null) {
                    if (this.world.getGameTime() % 20L == 0L) {
                        RandomChimpEvent chimpEvent1 = RandomChimpEventManager.getByWorld((ServerWorld) this.world).findRandomChimpEvent(this.getPosition(), 9216);
                        if (chimpEvent1 != null && RandomChimpEventManager.canJoinRandomChimpEvent(this, chimpEvent1)) {
                            chimpEvent1.joinRandomChimpEvent(chimpEvent1.getGroupsSpawned(), this, (BlockPos)null, true);
                        }
                    }
                } else {
                    //LivingEntity livingentity = this.getAttackTarget();
                    //if (livingentity != null && (livingentity.getType() == EntityType.PLAYER)) {
                    //    this.idleTime = 0;
                    //}
                }
            }
        }

        super.livingTick();
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setBreakDoorsAItask(compound.getBoolean("CanBreakDoors"));
        this.wave = compound.getInt("Wave");
        this.canJoinRandomChimpEvent = compound.getBoolean("CanJoinRandomChimpEvent");
        if (compound.contains("RandomChimpEventId", 3)) {
            if (this.world instanceof ServerWorld) {
                this.randomChimpEvent = RandomChimpEventManager.getByWorld((ServerWorld) this.world).get(compound.getInt("RandomChimpEventId"));
            }

            if (this.randomChimpEvent != null) {
                this.randomChimpEvent.joinRandomChimpEvent(this.wave, this, false);
            }
        }
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return this.getRandomChimpEvent() == null && super.canDespawn(distanceToClosestPlayer);
    }

    @Override
    public boolean preventDespawn() {
        return super.preventDespawn() || this.getRandomChimpEvent() != null;
    }

    public int getJoinDelay() {
        return this.joinDelay;
    }

    public void setJoinDelay(int delay) {
        this.joinDelay = delay;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isRandomChimpEventActive()) {
            this.getRandomChimpEvent().updateBarPercentage();
        }

        return super.attackEntityFrom(source, amount);
    }

    @Nullable @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setCanJoinRandomChimpEvent(reason != SpawnReason.NATURAL);
        spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        this.setBreakDoorsAItask(this.canBreakDoors());
        return spawnDataIn;
    }

    @Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return DBMEntities.AGGRESSIVE_CHIMP.get().create(p_241840_1_);
    }

    public void setRandomChimpEvent(RandomChimpEvent randomChimpEvent) {
        this.randomChimpEvent = randomChimpEvent;
    }

    @Nullable
    public RandomChimpEvent getRandomChimpEvent() {
        return this.randomChimpEvent;
    }

    public boolean notInRandomChimpEvent() {
        return !this.isRandomChimpEventActive();
    }

    public boolean isRandomChimpEventActive() {
        return this.getRandomChimpEvent() != null && this.getRandomChimpEvent().isActive();
    }

    public void setWave(int wave) {
        this.wave = wave;
    }

    public int getWave() {
        return this.wave;
    }

    public boolean canJoinRandomChimpEvent() {
        return this.canJoinRandomChimpEvent;
    }

    public void setCanJoinRandomChimpEvent(boolean canJoin) {
        this.canJoinRandomChimpEvent = canJoin;
    }
}
