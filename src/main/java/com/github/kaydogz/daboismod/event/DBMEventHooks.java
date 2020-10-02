package com.github.kaydogz.daboismod.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class DBMEventHooks {

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
