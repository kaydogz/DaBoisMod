package com.github.kaydogz.daboismod.item;

import net.minecraft.entity.EntitySize;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

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

    public float scalePlayer(ItemStack stackIn, PlayerEntity playerIn, Pose poseIn, EntitySize sizeIn, float oldEyeHeight) {
        float scale = this.getGiantScale(stackIn, playerIn);
        EntitySize originalSize = playerIn.getSize(playerIn.getPose());
        playerIn.size = EntitySize.flexible(originalSize.width * scale, originalSize.height * scale);

        float newWidthMagnitude = playerIn.size.width / 2;
        playerIn.setBoundingBox(playerIn.getBoundingBox().expand(-newWidthMagnitude, 0, -newWidthMagnitude).expand(newWidthMagnitude, playerIn.size.height, newWidthMagnitude));

        return oldEyeHeight * scale;
    }

    public float getGiantScale(ItemStack stackIn, PlayerEntity playerIn) {
        return 5.0F;
    }
}
