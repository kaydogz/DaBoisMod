package com.github.kaydogz.daboismod.capability.provider;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.IQuestScrollCap;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class QuestScrollCapability implements ICapabilitySerializable<INBT> {

	@CapabilityInject(IQuestScrollCap.class)
	public static final Capability<IQuestScrollCap> QUEST_SCROLL_CAPABILITY = null;
	
	private final LazyOptional<IQuestScrollCap> HOLDER = LazyOptional.of(QUEST_SCROLL_CAPABILITY::getDefaultInstance);
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return QUEST_SCROLL_CAPABILITY.orEmpty(cap, this.HOLDER);
	}
	
	@Override
	public INBT serializeNBT() {
		return QUEST_SCROLL_CAPABILITY.getStorage().writeNBT(QUEST_SCROLL_CAPABILITY, DaBoisMod.get(this.HOLDER), null);
	}

	@Override
	public void deserializeNBT(INBT nbt) {
		QUEST_SCROLL_CAPABILITY.getStorage().readNBT(QUEST_SCROLL_CAPABILITY, DaBoisMod.get(this.HOLDER), null, nbt);
	}

	public static LazyOptional<IQuestScrollCap> getCapabilityOf(ICapabilityProvider provider) {
		return provider.getCapability(QUEST_SCROLL_CAPABILITY, null);
	}
}