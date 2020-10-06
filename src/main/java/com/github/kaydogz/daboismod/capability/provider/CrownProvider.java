package com.github.kaydogz.daboismod.capability.provider;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.ICrownCapability;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CrownProvider implements ICapabilitySerializable<INBT> {

	@CapabilityInject(ICrownCapability.class)
	public static final Capability<ICrownCapability> CROWN_CAPABILITY = null;
	
	private final LazyOptional<ICrownCapability> HOLDER = LazyOptional.of(CROWN_CAPABILITY::getDefaultInstance);
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return CROWN_CAPABILITY.orEmpty(cap, this.HOLDER);
	}
	
	@Override
	public INBT serializeNBT() {
		return CROWN_CAPABILITY.getStorage().writeNBT(CROWN_CAPABILITY, DaBoisMod.get(this.HOLDER), null);
	}

	@Override
	public void deserializeNBT(INBT nbt) {
		CROWN_CAPABILITY.getStorage().readNBT(CROWN_CAPABILITY, DaBoisMod.get(this.HOLDER), null, nbt);
	}
	
	public static LazyOptional<ICrownCapability> getCapabilityOf(ICapabilityProvider provider) {
		return provider.getCapability(CROWN_CAPABILITY, null);
	}
}