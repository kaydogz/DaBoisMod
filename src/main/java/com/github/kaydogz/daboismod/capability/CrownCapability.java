package com.github.kaydogz.daboismod.capability;

import com.github.kaydogz.daboismod.capability.base.ICrownCapability;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.util.Constants;

public class CrownCapability implements ICrownCapability {

	private boolean activated = false;

	@Override
	public boolean isActivated() {
		return this.activated;
	}

	@Override
	public void setActivated(boolean activatedIn) {
		this.activated = activatedIn;
	}
	
	public static class Storage implements IStorage<ICrownCapability> {
		
		@Override
		public INBT writeNBT(Capability<ICrownCapability> capability, ICrownCapability instance, Direction side) {
			CompoundNBT tag = new CompoundNBT();
			tag.putBoolean("Activated", instance.isActivated());
			return tag;
		}

		@Override
		public void readNBT(Capability<ICrownCapability> capability, ICrownCapability instance, Direction side, INBT nbt) {
			if (nbt instanceof CompoundNBT) {
				CompoundNBT tag = (CompoundNBT) nbt;
				if (tag.contains("Activated", Constants.NBT.TAG_BYTE)) instance.setActivated(tag.getBoolean("Activated"));
			}
		}
	}
}
