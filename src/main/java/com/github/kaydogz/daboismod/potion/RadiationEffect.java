package com.github.kaydogz.daboismod.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class RadiationEffect extends Effect {

    public RadiationEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int j = 50 >> amplifier;
        if (j > 0) {
            return duration % j == 0;
        } else {
            return true;
        }
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        super.performEffect(entityLivingBaseIn, amplifier);
        if (entityLivingBaseIn instanceof MobEntity) {
            entityLivingBaseIn.heal(1.0F);
        } else if (entityLivingBaseIn instanceof PlayerEntity) {
            entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC, 1.0F);
        }
    }
}
