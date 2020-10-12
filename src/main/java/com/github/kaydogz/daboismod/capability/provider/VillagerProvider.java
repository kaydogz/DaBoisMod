package com.github.kaydogz.daboismod.capability.provider;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.IVillagerCapability;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class VillagerProvider implements ICapabilitySerializable<INBT> {

	@CapabilityInject(IVillagerCapability.class)
	public static final Capability<IVillagerCapability> VILLAGER_CAPABILITY = null;
	
	private final LazyOptional<IVillagerCapability> HOLDER = LazyOptional.of(VILLAGER_CAPABILITY::getDefaultInstance);
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return VILLAGER_CAPABILITY.orEmpty(cap, this.HOLDER);
	}
	
	@Override
	public INBT serializeNBT() {
		return VILLAGER_CAPABILITY.getStorage().writeNBT(VILLAGER_CAPABILITY, DaBoisMod.get(this.HOLDER), null);
	}

	@Override
	public void deserializeNBT(INBT nbt) {
		VILLAGER_CAPABILITY.getStorage().readNBT(VILLAGER_CAPABILITY, DaBoisMod.get(this.HOLDER), null, nbt);
	}
	
	public static LazyOptional<IVillagerCapability> getCapabilityOf(ICapabilityProvider provider) {
		return provider.getCapability(VILLAGER_CAPABILITY, null);
	}
}