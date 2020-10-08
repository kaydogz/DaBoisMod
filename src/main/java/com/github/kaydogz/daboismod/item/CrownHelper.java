package com.github.kaydogz.daboismod.item;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.ICrownCapability;
import com.github.kaydogz.daboismod.capability.provider.CrownProvider;
import com.github.kaydogz.daboismod.event.DBMEventHooks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
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

    /**
     * Toggles activation of the crown a player is wearing.
     * @param playerIn the player who is wearing the crown.
     */
    public static void toggleActivation(ServerPlayerEntity playerIn, boolean sendMessages) {
        ItemStack headSlotStack = playerIn.getItemStackFromSlot(EquipmentSlotType.HEAD);
        if (headSlotStack.getItem() instanceof CrownItem) {
            LazyOptional<ICrownCapability> lazyCap = CrownProvider.getCapabilityOf(headSlotStack);
            if (lazyCap.isPresent()) {
                CrownItem crownItem = (CrownItem) headSlotStack.getItem();
                ICrownCapability crownCap = DaBoisMod.get(lazyCap);
                boolean isActivated = !crownCap.isActivated();

                if (!(isActivated ? DBMEventHooks.onCrownActivation(headSlotStack, playerIn) : DBMEventHooks.onCrownDeactivation(headSlotStack, playerIn))) {
                    crownCap.setActivated(isActivated);

                    if (isActivated) {
                        crownItem.onActivation(headSlotStack, playerIn);
                    } else {
                        crownItem.onDeactivation(headSlotStack, playerIn);
                    }

                    // Displays the Activation Status to the Player
                    if (sendMessages) {
                        playerIn.sendStatusMessage(new TranslationTextComponent("item.daboismod.crown.toggled", headSlotStack.getDisplayName(), isActivated ? new TranslationTextComponent("item.daboismod.crown.on").applyTextStyle(TextFormatting.GREEN) : new TranslationTextComponent("item.daboismod.crown.off").applyTextStyle(TextFormatting.RED)), true);
                    }
                }
            }
        } else if (sendMessages) {

            // Displays 'Not Wearing' Message If the Player is Not Wearing the Crown
            playerIn.sendStatusMessage(new TranslationTextComponent("item.daboismod.crown.notWearing"), true);
        }
    }
}
