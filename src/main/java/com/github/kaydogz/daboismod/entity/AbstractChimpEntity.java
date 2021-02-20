package com.github.kaydogz.daboismod.entity;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public abstract class AbstractChimpEntity extends AnimalEntity {

    protected AbstractChimpEntity(EntityType<? extends AbstractChimpEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 10.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 1.0D);
    }

    @Nullable
    @Override
    public abstract AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_);
}
