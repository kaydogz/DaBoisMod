package com.github.kaydogz.daboismod.quest;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

public class KillEntitiesQuestTask extends QuestTask {

	private final EntityType<? extends LivingEntity> entityTypeToKill;
	
	public KillEntitiesQuestTask(EntityType<? extends LivingEntity> entityTypeToKill, int minQuota, int maxQuota) {
		super(minQuota, maxQuota);
		this.entityTypeToKill = entityTypeToKill;
	}

	public EntityType<? extends LivingEntity> getEntityTypeToKill() {
		return this.entityTypeToKill;
	}
}
