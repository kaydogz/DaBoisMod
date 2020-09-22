package com.github.kaydogz.daboismod.quest;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.event.DBMEventHooks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

public class Quest {

	protected int count;
	protected boolean completed;

	protected final int experience;
	protected final int quota;
	protected final ItemStack reward;
	protected final QuestTask task;
	protected final Difficulty difficulty;
	protected final UUID uniqueId;
	protected final Random rand = new Random();

	public Quest() {
		this.task = QuestHelper.getRandomTask(this.rand);
		this.quota = task.getRandomQuota(this.rand);
		this.count = 0;
		this.difficulty = QuestHelper.getAppropriateDifficulty(this);
		this.completed = this.count >= this.quota;
		final Pair<ItemStack, Integer> rewardPair = DBMEventHooks.getQuestReward(this, QuestHelper.getRandomReward(this), 15 * quota / task.getMaxQuota());
		this.reward = rewardPair.getLeft();
		this.experience = rewardPair.getRight();
		this.uniqueId = UUID.randomUUID();
	}
	
	public Quest(QuestTask task) {
		this.task = task;
		this.quota = task.getRandomQuota(this.rand);
		this.count = 0;
		this.difficulty = QuestHelper.getAppropriateDifficulty(this);
		this.completed = this.count >= this.quota;
		final Pair<ItemStack, Integer> rewardPair = DBMEventHooks.getQuestReward(this, QuestHelper.getRandomReward(this), 15 * quota / task.getMaxQuota());
		this.reward = rewardPair.getLeft();
		this.experience = rewardPair.getRight();
		this.uniqueId = UUID.randomUUID();
	}

	public Quest(QuestTask task, int quota) {
		this.task = task;
		this.quota = quota;
		this.count = 0;
		this.difficulty = QuestHelper.getAppropriateDifficulty(this);
		this.completed = this.count >= this.quota;
		final Pair<ItemStack, Integer> rewardPair = DBMEventHooks.getQuestReward(this, QuestHelper.getRandomReward(this), 16 * quota / task.getMaxQuota());
		this.reward = rewardPair.getLeft();
		this.experience = rewardPair.getRight();
		this.uniqueId = UUID.randomUUID();
	}

	/**
	 * Not meant for in game use, this is just used for the command and data syncing.
	 */
	public Quest(QuestTask task, int quota, int count, ItemStack reward, int experience, UUID uniqueId) {
		this.task = task;
		this.quota = quota;
		this.count = count;
		this.reward = reward;
		this.difficulty = QuestHelper.getAppropriateDifficulty(this);
		this.completed = this.count >= this.quota;
		this.experience = experience;
		this.uniqueId = uniqueId;
	}

	public CompoundNBT write(CompoundNBT tag) {
		tag.putString("QuestTask", this.getQuestTask().getRegistryName().toString());
		tag.putInt("Quota", this.getQuota());
		tag.putInt("Count", this.getCount());
		tag.put("Reward", this.getReward().serializeNBT());
		tag.putInt("Experience", this.getExperience());
		tag.putUniqueId("UUID", this.uniqueId);
		return tag;
	}

	private Quest(CompoundNBT compound) {
		this(QuestTasks.QUEST_TASKS_REGISTRY.get().getValue(new ResourceLocation(compound.getString("QuestTask"))), compound.getInt("Quota"), compound.getInt("Count"), ItemStack.read(compound.getCompound("Reward")), compound.getInt("Experience"), compound.getUniqueId("UUID"));
	}

	@Nullable
	public static Quest read(CompoundNBT compound) {
		try {
			return new Quest(compound);
		} catch (RuntimeException runtimeexception) {
			DaBoisMod.LOGGER.debug("Tried to load invalid quest: {}", compound, runtimeexception);
			return null;
		}
	}

	public ItemStack getReward() {
		return this.reward;
	}

	public int getCount() {
		return this.count;
	}
	
	public void increaseCount() {
		this.count = Math.min(this.task.getMaxQuota(), this.count + 1);
		this.completed = this.count >= this.quota;
	}
	
	public Difficulty getDifficulty() {
		return this.difficulty;
	}
	
	public int getQuota() {
		return this.quota;
	}
	
	public QuestTask getQuestTask() {
		return this.task;
	}
	
	public boolean isComplete() {
		return this.completed;
	}

	public int getExperience() {
		return this.experience;
	}

	public UUID getUniqueId() {
		return this.uniqueId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Quest)) return false;
		Quest quest = (Quest) o;
		return quest.uniqueId.equals(this.uniqueId);
	}

	public enum Difficulty {
		VERY_EASY("gui.daboismod.quest.difficulty.veryEasy", TextFormatting.GREEN),
		EASY("gui.daboismod.quest.difficulty.easy", TextFormatting.DARK_GREEN),
		MEDIUM("gui.daboismod.quest.difficulty.medium", TextFormatting.YELLOW),
		HARD("gui.daboismod.quest.difficulty.hard", TextFormatting.RED),
		VERY_HARD("gui.daboismod.quest.difficulty.veryHard", TextFormatting.DARK_RED);
		
		private final ITextComponent textComponent;

		Difficulty(String translationKeyIn, TextFormatting colorIn) {
			this.textComponent = new TranslationTextComponent(translationKeyIn);
			this.textComponent.getStyle().setColor(colorIn).setBold(true);
		}
		
		public ITextComponent getTextComponent() {
			return this.textComponent;
		}
	}
}
