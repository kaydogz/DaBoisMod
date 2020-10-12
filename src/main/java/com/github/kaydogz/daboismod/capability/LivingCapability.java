package com.github.kaydogz.daboismod.capability;

import com.github.kaydogz.daboismod.capability.base.ILivingCapability;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.util.Constants;

public class LivingCapability implements ILivingCapability {

	private boolean realmFalling = false;
	private boolean radiated = false;

	@Override
	public boolean isRealmFalling() {
		return this.realmFalling;
	}

	@Override
	public void setRealmFalling(boolean realmFalling) {
		this.realmFalling = realmFalling;
	}
	
	@Override
	public boolean isRadiated() {
		return this.radiated;
	}

	@Override
	public void setRadiated(boolean radiated) {
		this.radiated = radiated;
	}
	
	public static class Storage implements IStorage<ILivingCapability> {

		@Override
		public INBT writeNBT(Capability<ILivingCapability> capability, ILivingCapability instance, Direction side) {
			CompoundNBT tag = new CompoundNBT();
			tag.putBoolean("RealmFalling", instance.isRealmFalling());
			tag.putBoolean("Radiated", instance.isRadiated());
			return tag;
		}

		@Override
		public void readNBT(Capability<ILivingCapability> capability, ILivingCapability instance, Direction side, INBT nbt) {
			if (nbt instanceof CompoundNBT) {
				CompoundNBT tag = (CompoundNBT) nbt;
				if (tag.contains("RealmFalling", Constants.NBT.TAG_BYTE)) instance.setRealmFalling(tag.getBoolean("RealmFalling"));
				if (tag.contains("Radiated", Constants.NBT.TAG_BYTE)) instance.setRadiated(tag.getBoolean("Radiated"));
			}
		}
	}
}
