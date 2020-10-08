package com.github.kaydogz.daboismod.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;

public class AmberCrownItem extends CrownItem {

    public AmberCrownItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
        super(materialIn, slot, builder);
    }

    @Override
    public void onActivation(ItemStack stackIn, PlayerEntity playerIn) {
        playerIn.recalculateSize();
        playerIn.setPositionAndUpdate(playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ());
    }

    @Override
    public void onDeactivation(ItemStack stackIn, PlayerEntity playerIn) {
        playerIn.recalculateSize();
    }

    @Override
    public boolean shouldScalePlayer(ItemStack stackIn, PlayerEntity playerIn) {
        return true;
    }

    @Override
    public float getScale(ItemStack stackIn, PlayerEntity playerIn) {
        return 5.0F;
    }
}
