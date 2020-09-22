package com.github.kaydogz.daboismod.capability;

import com.github.kaydogz.daboismod.capability.base.IPlayerCap;
import com.github.kaydogz.daboismod.quest.Quest;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;

public class PlayerCap implements IPlayerCap {

	private ArrayList<Quest> quests = new ArrayList<>();
	private boolean magnetic;

	@Override
	public boolean isMagnetic() {
		return this.magnetic;
	}

	@Override
	public void setMagnetic(boolean magnetic) {
		this.magnetic = magnetic;
	}
	
	@Override
	public ArrayList<Quest> getQuests() {
		return this.quests;
	}

	@Override
	public void setQuests(ArrayList<Quest> quests) {
		this.quests = quests;
	}

	@Override
	public void addQuest(Quest quest) {
		this.quests.add(quest);
	}

	@Override
	public void removeQuest(Quest quest) {
		this.quests.remove(quest);
	}

	public static class Storage implements IStorage<IPlayerCap>  {
		
		@Override
		public INBT writeNBT(Capability<IPlayerCap> capability, IPlayerCap instance, Direction side) {
			CompoundNBT tag = new CompoundNBT();

			// Quests
			ListNBT listNbt = new ListNBT();
			instance.getQuests().forEach((quest) -> listNbt.add(quest.write(new CompoundNBT())));
			tag.put("Quests", listNbt);

			// Magnetic
			tag.putBoolean("Magnetic", instance.isMagnetic());

			return tag;
		}

		@Override
		public void readNBT(Capability<IPlayerCap> capability, IPlayerCap instance, Direction side, INBT nbt) {
			if (nbt instanceof CompoundNBT) {
				CompoundNBT tag = (CompoundNBT) nbt;

				// Quests
				if (tag.contains("Quests", Constants.NBT.TAG_LIST)) {
					ArrayList<Quest> quests = new ArrayList<>();
					((ListNBT) tag.get("Quests")).forEach((data) -> {
						if (data instanceof CompoundNBT) {
							Quest quest = Quest.read((CompoundNBT) data);
							if (quest != null) quests.add(quest);
						}
					});
					instance.setQuests(quests);
				}

				// Magnetic
				instance.setMagnetic(tag.getBoolean("Magnetic"));
			}
		}
	}
}
