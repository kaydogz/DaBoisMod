package com.github.kaydogz.daboismod.event;

import com.github.kaydogz.daboismod.quest.Quest;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.lang3.tuple.Pair;

public class DBMEventHooks {

    /**
     * Returns the new reward, which is the original unless set by an event subscriber.
     */
    public static Pair<ItemStack, Integer> getQuestReward(Quest quest, ItemStack originalReward, int originalExperience) {
        QuestRewardEvent event = new QuestRewardEvent(quest, originalReward, originalExperience);
        MinecraftForge.EVENT_BUS.post(event);
        return Pair.of(event.getNewReward(), event.getNewExperience());
    }

    /**
     * Returns true if the event was canceled.
     */
    public static boolean onGodsCrownActivation(ItemStack crownStack, PlayerEntity wearer) {
        GodsCrownEvent.Activate event = new GodsCrownEvent.Activate(crownStack, wearer);
        return MinecraftForge.EVENT_BUS.post(event);
    }

    /**
     * Returns true if the event was canceled.
     */
    public static boolean onGodsCrownDeactivation(ItemStack crownStack, PlayerEntity wearer) {
        GodsCrownEvent.Deactivate event = new GodsCrownEvent.Deactivate(crownStack, wearer);
        return MinecraftForge.EVENT_BUS.post(event);
    }
}
