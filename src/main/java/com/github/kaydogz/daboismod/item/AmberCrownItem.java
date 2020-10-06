package com.github.kaydogz.daboismod.item;

import net.minecraft.entity.EntitySize;
import net.minecraft.entity.Pose;
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
        float scale = this.getGiantScale(stackIn, playerIn);
        playerIn.abilities.setWalkSpeed(playerIn.abilities.getWalkSpeed() * scale);
        playerIn.abilities.setFlySpeed(playerIn.abilities.getFlySpeed() * scale);
    }

    @Override
    public void onDeactivation(ItemStack stackIn, PlayerEntity playerIn) {
        playerIn.recalculateSize();
        float scale = this.getGiantScale(stackIn, playerIn);
        playerIn.abilities.setWalkSpeed(playerIn.abilities.getWalkSpeed() / scale);
        playerIn.abilities.setFlySpeed(playerIn.abilities.getFlySpeed() / scale);
    }

    public float onActivatedPlayerEyeHeight(ItemStack stackIn, PlayerEntity playerIn, Pose poseIn, EntitySize sizeIn, float oldHeight) {
        scaleSize(this.getGiantScale(stackIn, playerIn), playerIn);
        return oldHeight * this.getGiantScale(stackIn, playerIn);
    }

    public float getGiantScale(ItemStack stackIn, PlayerEntity playerIn) {
        return 5.0F;
    }

    protected static void scaleSize(float scaleIn, PlayerEntity playerIn) {
        EntitySize originalSize = playerIn.getSize(playerIn.getPose());
        playerIn.size = EntitySize.flexible(originalSize.width * scaleIn, originalSize.height * scaleIn);

        playerIn.setBoundingBox(playerIn.getBoundingBox().expand(
                -playerIn.size.width / 2,
                0,
                -playerIn.size.width / 2
        ).expand(
                playerIn.size.width / 2,
                playerIn.size.height,
                playerIn.size.width / 2
        ));
    }
}
