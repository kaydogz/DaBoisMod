package com.github.kaydogz.daboismod.capability;

import com.github.kaydogz.daboismod.capability.base.IGodsCrownCap;
import com.github.kaydogz.daboismod.item.CryptidGemItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.util.Constants;

public class GodsCrownCap implements IGodsCrownCap {

	private boolean activated = false;
	private ItemStack insertedGem = ItemStack.EMPTY;
	
	@Override
	public boolean isActivated() {
		return this.activated;
	}

	@Override
	public void setActivated(boolean activatedIn) {
		this.activated = activatedIn;
	}

	@Override
	public ItemStack getInsertedGem() {
		return this.insertedGem;
	}

	@Override
	public void setInsertedGem(ItemStack stackIn) {
		this.insertedGem = (stackIn.getItem() instanceof CryptidGemItem) ? stackIn : ItemStack.EMPTY;
	}
	
	public static class Storage implements IStorage<IGodsCrownCap> {
		
		@Override
		public INBT writeNBT(Capability<IGodsCrownCap> capability, IGodsCrownCap instance, Direction side) {
			CompoundNBT tag = new CompoundNBT();
			tag.putBoolean("Activated", instance.isActivated());
			tag.put("InsertedGem", instance.getInsertedGem().serializeNBT());
			return tag;
		}

		@Override
		public void readNBT(Capability<IGodsCrownCap> capability, IGodsCrownCap instance, Direction side, INBT nbt) {
			if (nbt instanceof CompoundNBT) {
				CompoundNBT tag = (CompoundNBT) nbt;
				if (tag.contains("Activated", Constants.NBT.TAG_BYTE)) instance.setActivated(tag.getBoolean("Activated"));
				if (tag.contains("InsertedGem", Constants.NBT.TAG_COMPOUND)) instance.setInsertedGem(ItemStack.read(tag.getCompound("InsertedGem")));
			}
		}
	}
}
