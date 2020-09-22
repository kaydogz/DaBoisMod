package com.github.kaydogz.daboismod.quest;

import net.minecraft.block.Block;

public class PlaceBlocksQuestTask extends QuestTask {

	private final Block blockToPlace;
	
	public PlaceBlocksQuestTask(Block blockToPlace, int minQuota, int maxQuota) {
		super(minQuota, maxQuota);
		this.blockToPlace = blockToPlace;
	}

	public Block getBlockToPlace() {
		return this.blockToPlace;
	}
}
