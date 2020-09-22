package com.github.kaydogz.daboismod.event;

import com.github.kaydogz.daboismod.quest.Quest;
import com.github.kaydogz.daboismod.quest.QuestTask;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

public class QuestRewardEvent extends Event {

    private final QuestTask questTask;
    private final Quest.Difficulty difficulty;
    private final int count;
    private final int quota;
    private final ItemStack originalReward;
    private final int originalExperience;
    private ItemStack newReward;
    private int newExperience;

    public QuestRewardEvent(Quest questIn, ItemStack originalReward, int originalExperience) {
        this.questTask = questIn.getQuestTask();
        this.difficulty = questIn.getDifficulty();
        this.count = questIn.getCount();
        this.quota = questIn.getQuota();
        this.originalReward = originalReward.copy();
        this.originalExperience = originalExperience;
        this.newReward = this.originalReward;
        this.newExperience = this.originalExperience;
    }

    public QuestTask getQuestTask() {
        return this.questTask;
    }

    public Quest.Difficulty getDifficulty() {
        return this.difficulty;
    }

    public int getCount() {
        return this.count;
    }

    public int getQuota() {
        return this.quota;
    }

    public void setReward(ItemStack stack) {
        this.newReward = stack;
    }

    public ItemStack getOriginalReward() {
        return this.originalReward;
    }

    public ItemStack getNewReward() {
        return this.newReward;
    }

    public void setExperience(int experience) {
        this.newExperience = experience;
    }

    public int getOriginalExperience() {
        return this.originalExperience;
    }

    public int getNewExperience() {
        return this.newExperience;
    }
}
