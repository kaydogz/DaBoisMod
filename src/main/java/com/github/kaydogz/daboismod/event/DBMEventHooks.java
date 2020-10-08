package com.github.kaydogz.daboismod.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class DBMEventHooks {

    /**
     * Returns true if the event was canceled.
     */
    public static boolean onCrownActivation(ItemStack crownStack, PlayerEntity wearer) {
        CrownEvent.Activate event = new CrownEvent.Activate(crownStack, wearer);
        return MinecraftForge.EVENT_BUS.post(event);
    }

    /**
     * Returns true if the event was canceled.
     */
    public static boolean onCrownDeactivation(ItemStack crownStack, PlayerEntity wearer) {
        CrownEvent.Deactivate event = new CrownEvent.Deactivate(crownStack, wearer);
        return MinecraftForge.EVENT_BUS.post(event);
    }
}
