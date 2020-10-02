package com.github.kaydogz.daboismod.quest;

import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class QuestTask extends ForgeRegistryEntry<QuestTask> {
	
	protected int minQuota;
	protected int maxQuota;

	@Nullable
	private String translationKey;
	
	public QuestTask(int minQuota, int maxQuota) {
		this.maxQuota = Math.max(1, maxQuota);
		this.minQuota = MathHelper.clamp(minQuota, 0, this.maxQuota - 1);
	}

	public int getMinQuota() {
		return this.minQuota;
	}

	public int getMaxQuota() {
		return this.maxQuota;
	}

	/**
	 * Gets a random quota between the min and max quotas. Both min and max quotas are inclusive.
	 * @return a random quota between the bounds.
	 */
	public int getRandomQuota(Random rand) {
		return rand.nextInt(1 + this.getMaxQuota() - this.getMinQuota()) + this.getMinQuota();
	}
	
	protected String getDefaultTranslationKey() {
	    return this.translationKey == null ? this.translationKey = Util.makeTranslationKey("questTask", QuestTasks.QUEST_TASKS_REGISTRY.get().getKey(this)) : this.translationKey;
	}
	
	public String getTranslationKey() {
		return this.getDefaultTranslationKey();
	}
}
