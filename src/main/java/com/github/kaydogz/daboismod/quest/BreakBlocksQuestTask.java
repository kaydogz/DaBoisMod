package com.github.kaydogz.daboismod.quest;

import net.minecraft.block.Block;

public class BreakBlocksQuestTask extends QuestTask {

	private final Block blockToBreak;
	
	public BreakBlocksQuestTask(Block blockToBreak, int minQuota, int maxQuota) {
		super(minQuota, maxQuota);
		this.blockToBreak = blockToBreak;
	}

	public Block getBlockToBreak() {
		return this.blockToBreak;
	}
}
