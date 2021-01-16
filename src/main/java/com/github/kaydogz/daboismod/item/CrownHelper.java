package com.github.kaydogz.daboismod.item;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.ICrownCapability;
import com.github.kaydogz.daboismod.capability.provider.CrownProvider;
import com.github.kaydogz.daboismod.event.DBMEventHooks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.LazyOptional;

public class CrownHelper {

    /**
     * Checks whether or not a given crown is activated.
     * @param stackIn the crown stack.
     * @return if the crown stack is activated.
     */
    public static boolean isActivated(ItemStack stackIn) {
        return DaBoisMod.get(CrownProvider.getCapabilityOf(stackIn)).isActivated();
    }

    public static void activateCrown(ItemStack stackIn, PlayerEntity playerIn, boolean sendMessages) {
        if (DBMEventHooks.onCrownActivation(stackIn, playerIn)) return;
        DaBoisMod.get(CrownProvider.getCapabilityOf(stackIn)).setActivated(true);
        ((CrownItem) stackIn.getItem()).onActivation(stackIn, playerIn);
        if (!playerIn.world.isRemote && sendMessages) {
            playerIn.sendStatusMessage(new TranslationTextComponent("item.daboismod.crown.toggled", stackIn.getDisplayName(), new TranslationTextComponent("item.daboismod.crown.on").mergeStyle(TextFormatting.GREEN)), true);
        }
    }

    public static void deactivateCrown(ItemStack stackIn, PlayerEntity playerIn, boolean sendMessages) {
        if (DBMEventHooks.onCrownDeactivation(stackIn, playerIn)) return;
        DaBoisMod.get(CrownProvider.getCapabilityOf(stackIn)).setActivated(false);
        ((CrownItem) stackIn.getItem()).onDeactivation(stackIn, playerIn);
        if (!playerIn.world.isRemote && sendMessages) {
            playerIn.sendStatusMessage(new TranslationTextComponent("item.daboismod.crown.toggled", stackIn.getDisplayName(), new TranslationTextComponent("item.daboismod.crown.off").mergeStyle(TextFormatting.RED)), true);
        }
    }

    /**
     * Toggles activation of the crown a player is wearing, assuming the player is wearing the crown.
     * @param playerIn the player who is wearing the crown.
     * @param sendMessages whether or not to send hotbar notification messages to the player.
     */
    public static void toggleActivation(PlayerEntity playerIn, boolean sendMessages) {
        toggleActivation(playerIn.getItemStackFromSlot(EquipmentSlotType.HEAD), playerIn, sendMessages);
    }

    /**
     * Toggles activation of the crown a player is wearing.
     * @param stackIn the crown stack.
     * @param playerIn the player who is wearing the crown.
     * @param sendMessages whether or not to send hotbar notification messages to the player.
     */
    public static void toggleActivation(ItemStack stackIn, PlayerEntity playerIn, boolean sendMessages) {
        if (stackIn.getItem() instanceof CrownItem) {
            LazyOptional<ICrownCapability> lazyCap = CrownProvider.getCapabilityOf(stackIn);
            if (lazyCap.isPresent()) {
                if (DaBoisMod.get(lazyCap).isActivated()) deactivateCrown(stackIn, playerIn, sendMessages);
                else activateCrown(stackIn, playerIn, sendMessages);
            }
        } else if (sendMessages) {

            // Displays 'Not Wearing' Message If the Player is Not Wearing the Crown
            playerIn.sendStatusMessage(new TranslationTextComponent("item.daboismod.crown.notWearing"), true);
        }
    }
}
