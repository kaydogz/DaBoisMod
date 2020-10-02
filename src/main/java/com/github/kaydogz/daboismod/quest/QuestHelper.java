package com.github.kaydogz.daboismod.quest;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.provider.PlayerCapability;
import com.github.kaydogz.daboismod.network.DBMPacketHandler;
import com.github.kaydogz.daboismod.network.server.SUpdateQuestsPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class QuestHelper {

    /**
     * Assigns a quest to a player and syncs to tracking clients.
     * @param questIn the quest to assign.
     * @param playerIn the player to assign it to.
     */
    public static void assignQuest(Quest questIn, ServerPlayerEntity playerIn) {
        playerIn.sendStatusMessage(new StringTextComponent(TextFormatting.GOLD + new TranslationTextComponent("gui.daboismod.quest.new").getFormattedText()), true);
        ArrayList<Quest> quests = DaBoisMod.get(PlayerCapability.getCapabilityOf(playerIn)).getQuests();
        quests.add(questIn);
        DBMPacketHandler.sendToAllTrackingEntityAndSelf(new SUpdateQuestsPacket(quests, playerIn.getEntityId()), playerIn);
    }

    /**
     * Clears the quests of a player and syncs to tracking clients.
     * @param playerIn the player to clear the quests of.
     * @return the number of quests cleared.
     */
    public static int clearQuests(ServerPlayerEntity playerIn) {
        ArrayList<Quest> quests = DaBoisMod.get(PlayerCapability.getCapabilityOf(playerIn)).getQuests();
        int cleared = quests.size();
        quests.clear();
        DBMPacketHandler.sendToAllTrackingEntityAndSelf(new SUpdateQuestsPacket(quests, playerIn.getEntityId()), playerIn);
        return cleared;
    }

    protected static QuestTask getRandomTask(Random rand) {
        Collection<QuestTask> registeredTasks = QuestTasks.QUEST_TASKS_REGISTRY.get().getValues();
        return (QuestTask) registeredTasks.toArray()[rand.nextInt(registeredTasks.size())];
    }
}
