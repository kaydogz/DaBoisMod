package com.github.kaydogz.daboismod.capability.base;

import net.minecraft.item.ItemStack;

public interface IGodsCrownCap {

	boolean isActivated();
	void setActivated(boolean activatedIn);
	
	ItemStack getInsertedGem();
	void setInsertedGem(ItemStack stackIn);
}
