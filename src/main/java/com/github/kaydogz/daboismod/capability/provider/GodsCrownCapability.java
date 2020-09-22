package com.github.kaydogz.daboismod.capability.provider;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.IGodsCrownCap;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class GodsCrownCapability implements ICapabilitySerializable<INBT> {

	@CapabilityInject(IGodsCrownCap.class)
	public static final Capability<IGodsCrownCap> GODS_CROWN_CAPABILITY = null;
	
	private final LazyOptional<IGodsCrownCap> HOLDER = LazyOptional.of(GODS_CROWN_CAPABILITY::getDefaultInstance);
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return GODS_CROWN_CAPABILITY.orEmpty(cap, this.HOLDER);
	}
	
	@Override
	public INBT serializeNBT() {
		return GODS_CROWN_CAPABILITY.getStorage().writeNBT(GODS_CROWN_CAPABILITY, DaBoisMod.get(this.HOLDER), null);
	}

	@Override
	public void deserializeNBT(INBT nbt) {
		GODS_CROWN_CAPABILITY.getStorage().readNBT(GODS_CROWN_CAPABILITY, DaBoisMod.get(this.HOLDER), null, nbt);
	}
	
	public static LazyOptional<IGodsCrownCap> getCapabilityOf(ICapabilityProvider provider) {
		return provider.getCapability(GODS_CROWN_CAPABILITY, null);
	}
}