package com.github.kaydogz.daboismod.capability;

import com.github.kaydogz.daboismod.capability.base.ILivingCap;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.util.Constants;

public class LivingCap implements ILivingCap {

	private boolean fallingFromSky = false;
	private boolean radiated = false;

	@Override
	public boolean isFallingFromSky() {
		return this.fallingFromSky;
	}

	@Override
	public void setFallingFromSky(boolean fallingFromSky) {
		this.fallingFromSky = fallingFromSky;
	}
	
	@Override
	public boolean isRadiated() {
		return this.radiated;
	}

	@Override
	public void setRadiated(boolean radiated) {
		this.radiated = radiated;
	}
	
	public static class Storage implements IStorage<ILivingCap> {

		@Override
		public INBT writeNBT(Capability<ILivingCap> capability, ILivingCap instance, Direction side) {
			CompoundNBT tag = new CompoundNBT();
			tag.putBoolean("FallingFromSky", instance.isFallingFromSky());
			tag.putBoolean("Radiated", instance.isRadiated());
			return tag;
		}

		@Override
		public void readNBT(Capability<ILivingCap> capability, ILivingCap instance, Direction side, INBT nbt) {
			if (nbt instanceof CompoundNBT) {
				CompoundNBT tag = (CompoundNBT) nbt;
				if (tag.contains("FallingFromSky", Constants.NBT.TAG_BYTE)) instance.setFallingFromSky(tag.getBoolean("FallingFromSky"));
				if (tag.contains("Radiated", Constants.NBT.TAG_BYTE)) instance.setRadiated(tag.getBoolean("Radiated"));
			}
		}
	}
}
