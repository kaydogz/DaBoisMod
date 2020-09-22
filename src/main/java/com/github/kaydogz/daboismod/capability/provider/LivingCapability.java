package com.github.kaydogz.daboismod.capability.provider;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.ILivingCap;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class LivingCapability implements ICapabilitySerializable<INBT> {

	@CapabilityInject(ILivingCap.class)
	public static final Capability<ILivingCap> LIVING_CAPABILITY = null;
	
	private final LazyOptional<ILivingCap> HOLDER = LazyOptional.of(LIVING_CAPABILITY::getDefaultInstance);
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return LIVING_CAPABILITY.orEmpty(cap, this.HOLDER);
	}
	
	@Override
	public INBT serializeNBT() {
		return LIVING_CAPABILITY.getStorage().writeNBT(LIVING_CAPABILITY, DaBoisMod.get(this.HOLDER), null);
	}

	@Override
	public void deserializeNBT(INBT nbt) {
		LIVING_CAPABILITY.getStorage().readNBT(LIVING_CAPABILITY, DaBoisMod.get(this.HOLDER), null, nbt);
	}
	
	public static LazyOptional<ILivingCap> getCapabilityOf(ICapabilityProvider provider) {
		return provider.getCapability(LIVING_CAPABILITY, null);
	}
}