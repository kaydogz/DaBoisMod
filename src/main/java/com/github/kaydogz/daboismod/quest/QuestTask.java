package com.github.kaydogz.daboismod.quest;

import net.minecraft.util.Util;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class QuestTask extends ForgeRegistryEntry<QuestTask> {
	
	protected int minQuota;
	protected int maxQuota;

	@Nullable
	private String translationKey;
	
	public QuestTask(int minQuota, int maxQuota) {
		this.minQuota = minQuota;
		this.maxQuota = maxQuota;
	}

	public int getMinQuota() {
		return this.minQuota;
	}

	public int getMaxQuota() {
		return this.maxQuota;
	}

	/**
	 * Both min and max quota are inclusive.
	 * @return a random quota between the bounds.
	 */
	public int getRandomQuota(Random rand) {
		return rand.nextInt(1 + this.maxQuota - this.minQuota) + this.minQuota;
	}
	
	protected String getDefaultTranslationKey() {
		if (this.translationKey == null) {
			this.translationKey = Util.makeTranslationKey("questTask", QuestTasks.QUEST_TASKS_REGISTRY.get().getKey(this));
	    }
	    return this.translationKey;
	}
	
	public String getTranslationKey() {
		return this.getDefaultTranslationKey();
	}
}
