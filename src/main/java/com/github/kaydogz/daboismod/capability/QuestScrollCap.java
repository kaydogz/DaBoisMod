package com.github.kaydogz.daboismod.capability;

import com.github.kaydogz.daboismod.capability.base.IQuestScrollCap;
import com.github.kaydogz.daboismod.quest.Quest;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.util.Constants;

public class QuestScrollCap implements IQuestScrollCap {

	private Quest quest = new Quest();
	
	@Override
	public Quest getQuest() {
		return this.quest;
	}

	@Override
	public void setQuest(Quest quest) {
		this.quest = quest;
	}

	public static class Storage implements IStorage<IQuestScrollCap> {

		@Override
		public INBT writeNBT(Capability<IQuestScrollCap> capability, IQuestScrollCap instance, Direction side) {
			CompoundNBT tag = new CompoundNBT();
			tag.put("Quest", instance.getQuest().write(new CompoundNBT()));
			return tag;
		}

		@Override
		public void readNBT(Capability<IQuestScrollCap> capability, IQuestScrollCap instance, Direction side, INBT nbt) {
			if (nbt instanceof CompoundNBT) {
				CompoundNBT tag = (CompoundNBT) nbt;
				if (tag.contains("Quest", Constants.NBT.TAG_COMPOUND)) {
					Quest quest = Quest.read(tag.getCompound("Quest"));
					if (quest != null) instance.setQuest(quest);
				}
			}
		}
	}
}
