package com.github.kaydogz.daboismod.entity;

import com.github.kaydogz.daboismod.util.DBMSoundEvents;
import net.minecraft.client.renderer.entity.BeeRenderer;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class FleshCreeperEntity extends CreeperEntity {

    public FleshCreeperEntity(EntityType<? extends CreeperEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return CreeperEntity.registerAttributes()
                .createMutableAttribute(Attributes.MAX_HEALTH, 30.0D);
    }

    @Override
    public void playSound(SoundEvent soundIn, float volume, float pitch) {
        super.playSound(soundIn == SoundEvents.ENTITY_CREEPER_PRIMED ? DBMSoundEvents.ENTITY_FLESH_CREEPER_PRIMED.get() : soundIn, volume, pitch);
    }
}
