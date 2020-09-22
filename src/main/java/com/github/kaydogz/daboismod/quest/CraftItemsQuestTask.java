package com.github.kaydogz.daboismod.quest;

import net.minecraft.item.Item;

public class CraftItemsQuestTask extends QuestTask {

	private final Item itemToCraft;
	
	public CraftItemsQuestTask(Item itemToCraft, int minQuota, int maxQuota) {
		super(minQuota, maxQuota);
		this.itemToCraft = itemToCraft;
	}
	
	public Item getItemToCraft() {
		return this.itemToCraft;
	}

}
