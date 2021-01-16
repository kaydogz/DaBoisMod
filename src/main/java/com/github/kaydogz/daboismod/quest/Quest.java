package com.github.kaydogz.daboismod.quest;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.world.storage.loot.DBMLootParameterSets;
import com.github.kaydogz.daboismod.world.storage.loot.DBMLootParameters;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
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

	public Quest(ServerWorld world) {
		this(world, QuestHelper.getRandomTask(world.getRandom()));
	}
	
	public Quest(ServerWorld world, QuestTask task) {
		this(world, task, task.getRandomQuota(world.getRandom()));
	}

	public Quest(ServerWorld world, QuestTask task, int quota) {
		this.task = task;
		this.quota = quota;
		this.count = 0;
		this.difficulty = Difficulty.getAppropriateDifficulty(quota, task);
		this.completed = this.count >= this.quota;
		this.reward = this.getLootTableReward(world);
		this.experience = 16 * quota / task.getMaxQuota();
		this.uniqueId = UUID.randomUUID();
	}

	public Quest(QuestTask task, int quota, int count, ItemStack reward, int experience, UUID uniqueId) {
		this(task, quota, count, Difficulty.getAppropriateDifficulty(quota, task), reward, experience, uniqueId);
	}

	private Quest(QuestTask task, int quota, int count, Difficulty difficulty, ItemStack reward, int experience, UUID uniqueId) {
		this.task = task;
		this.quota = quota;
		this.count = count;
		this.reward = reward;
		this.difficulty = difficulty;
		this.completed = this.count >= this.quota;
		this.experience = experience;
		this.uniqueId = uniqueId;
	}

	/**
	 * Use {@code Quest.read(compound)} instead.
	 */
	@Deprecated
	private Quest(CompoundNBT compound) {
		this(QuestTasks.QUEST_TASKS_REGISTRY.get().getValue(new ResourceLocation(compound.getString("QuestTask"))), compound.getInt("Quota"), compound.getInt("Count"), Difficulty.fromString(compound.getString("Difficulty")), ItemStack.read(compound.getCompound("Reward")), compound.getInt("Experience"), compound.getUniqueId("UUID"));
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

	public CompoundNBT write(CompoundNBT tag) {
		tag.putString("QuestTask", this.getQuestTask().getRegistryName().toString());
		tag.putInt("Quota", this.getQuota());
		tag.putInt("Count", this.getCount());
		tag.putString("Difficulty", this.getDifficulty().toString().toLowerCase());
		tag.put("Reward", this.getReward().serializeNBT());
		tag.putInt("Experience", this.getExperience());
		tag.putUniqueId("UUID", this.getUniqueId());
		return tag;
	}

	public void increaseCount() {
		this.count++;
		this.completed = this.count >= this.quota;
	}

	protected ItemStack getLootTableReward(ServerWorld worldIn) {
		ResourceLocation location = this.difficulty.getLootTableResourceLocation();
		if (location == LootTables.EMPTY) {
			return ItemStack.EMPTY;
		} else {
			LootContext.Builder builder = (new LootContext.Builder(worldIn)).withRandom(worldIn.getRandom()).withParameter(DBMLootParameters.QUEST, this);
			LootContext lootcontext = builder.build(DBMLootParameterSets.QUEST);
			LootTable loottable = lootcontext.getWorld().getServer().getLootTableManager().getLootTableFromLocation(location);
			return loottable.generate(lootcontext).get(0);
		}
	}

	public ItemStack getReward() {
		return this.reward;
	}

	public int getCount() {
		return this.count;
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
		return ((Quest) o).uniqueId.equals(this.uniqueId);
	}
}
