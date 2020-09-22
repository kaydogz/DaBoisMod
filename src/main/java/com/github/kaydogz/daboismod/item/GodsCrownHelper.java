package com.github.kaydogz.daboismod.item;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.IGodsCrownCap;
import com.github.kaydogz.daboismod.capability.provider.GodsCrownCapability;
import com.github.kaydogz.daboismod.event.DBMEventHooks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class GodsCrownHelper {

    /**
     * Gets a serialization of the inserted gem of a given God's Crown. Note that changes to the returned {@link ItemStack} will not change the capability's stored data.
     * @param stackIn the God's Crown stack.
     * @return a serialization of the stored gem.
     */
    public static ItemStack getInsertedGem(ItemStack stackIn) {
        return DaBoisMod.get(GodsCrownCapability.getCapabilityOf(stackIn)).getInsertedGem();
    }

    /**
     * Checks whether or not a given God's Crown is activated.
     * @param stackIn the God's Crown stack.
     * @return if the God's Crown stack is activated.
     */
    public static boolean isActivated(ItemStack stackIn) {
        return DaBoisMod.get(GodsCrownCapability.getCapabilityOf(stackIn)).isActivated();
    }

    /**
     * Toggles activation of the God's Crown a player is wearing.
     * @param playerIn the player who is wearing the crown.
     */
    public static void toggleActivation(ServerPlayerEntity playerIn, boolean sendMessages) {
        ItemStack headSlotStack = playerIn.getItemStackFromSlot(EquipmentSlotType.HEAD);
        if (headSlotStack.getItem() instanceof GodsCrownItem) {
            IGodsCrownCap crownCap = DaBoisMod.get(GodsCrownCapability.getCapabilityOf(headSlotStack));
            boolean isActivated = !crownCap.isActivated();

            Item insertedItem = crownCap.getInsertedGem().getItem();
            boolean canceled = isActivated ? DBMEventHooks.onGodsCrownActivation(headSlotStack, playerIn) : DBMEventHooks.onGodsCrownDeactivation(headSlotStack, playerIn);

            if (!canceled) {
                if (insertedItem instanceof CryptidGemItem) {
                    if (isActivated) ((CryptidGemItem) insertedItem).onActivation(headSlotStack, playerIn);
                    else ((CryptidGemItem) insertedItem).onDeactivation(headSlotStack, playerIn);
                }

                crownCap.setActivated(isActivated);

                // Displays the Activation Status to the Player
                if (sendMessages) {
                    TranslationTextComponent statusComponent = new TranslationTextComponent(isActivated ? "item.daboismod.gods_crown.on" : "item.daboismod.gods_crown.off");
                    statusComponent.getStyle().setColor(isActivated ? TextFormatting.GREEN : TextFormatting.RED);
                    TranslationTextComponent component = new TranslationTextComponent("item.daboismod.gods_crown.toggled", headSlotStack.getDisplayName().getFormattedText(), statusComponent.getFormattedText());
                    playerIn.sendStatusMessage(component, true);
                }
            }
        } else if (sendMessages) {

            // Displays 'Not Wearing' Message If the Player is Not Wearing the Crown
            TranslationTextComponent crownComponent = new TranslationTextComponent(DBMItems.GODS_CROWN.get().getTranslationKey());
            crownComponent.getStyle().setColor(DBMItems.GODS_CROWN.get().getRarity(ItemStack.EMPTY).color);
            playerIn.sendStatusMessage(new TranslationTextComponent("item.daboismod.gods_crown.notWearing", crownComponent.getFormattedText()), true);
        }
    }
}
