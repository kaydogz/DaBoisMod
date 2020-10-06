package com.github.kaydogz.daboismod.capability.base;

import com.github.kaydogz.daboismod.quest.Quest;

import java.util.ArrayList;

public interface IPlayerCapability {

	ArrayList<Quest> getQuests();
	void setQuests(ArrayList<Quest> quests);
	
	void addQuest(Quest quest);
	void removeQuest(Quest quest);
	
	boolean isMagnetic();
	void setMagnetic(boolean magnetic);
}
