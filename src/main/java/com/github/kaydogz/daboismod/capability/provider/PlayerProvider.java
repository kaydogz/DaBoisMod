package com.github.kaydogz.daboismod.capability.provider;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.IPlayerCapability;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerProvider implements ICapabilitySerializable<INBT> {

	@CapabilityInject(IPlayerCapability.class)
	public static final Capability<IPlayerCapability> PLAYER_CAPABILITY = null;
	
	private final LazyOptional<IPlayerCapability> HOLDER = LazyOptional.of(PLAYER_CAPABILITY::getDefaultInstance);
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return PLAYER_CAPABILITY.orEmpty(cap, this.HOLDER);
	}
	
	@Override
	public INBT serializeNBT() {
		return PLAYER_CAPABILITY.getStorage().writeNBT(PLAYER_CAPABILITY, DaBoisMod.get(this.HOLDER), null);
	}

	@Override
	public void deserializeNBT(INBT nbt) {
		PLAYER_CAPABILITY.getStorage().readNBT(PLAYER_CAPABILITY, DaBoisMod.get(this.HOLDER), null, nbt);
	}
	
	public static LazyOptional<IPlayerCapability> getCapabilityOf(ICapabilityProvider provider) {
		return provider.getCapability(PLAYER_CAPABILITY, null);
	}
}