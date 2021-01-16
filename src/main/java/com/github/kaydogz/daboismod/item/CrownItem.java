package com.github.kaydogz.daboismod.item;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.ICrownCapability;
import com.github.kaydogz.daboismod.capability.provider.CrownProvider;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;
import java.util.List;

public abstract class CrownItem extends ArmorItem {

	protected CrownItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
		super(materialIn, slot, builder);
	}

	@Override
	public void addInformation(ItemStack crownStack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(crownStack, worldIn, tooltip, flagIn);

		LazyOptional<ICrownCapability> lazyCrownCap = CrownProvider.getCapabilityOf(crownStack);
		if (worldIn != null && lazyCrownCap.isPresent()) {

			// Activated Tooltip
			if (DaBoisMod.get(lazyCrownCap).isActivated()) {
				tooltip.add(new TranslationTextComponent("item.daboismod.crown.activated", new TranslationTextComponent("item.daboismod.crown.on").mergeStyle(TextFormatting.GREEN)));
			} else {
				tooltip.add(new TranslationTextComponent("item.daboismod.crown.activated", new TranslationTextComponent("item.daboismod.crown.off").mergeStyle(TextFormatting.RED)));
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	@Nullable
	@Override
	public CompoundNBT getShareTag(ItemStack stack) {
		CompoundNBT tag = stack.getOrCreateTag();

		LazyOptional<ICrownCapability> lazyCrownCap = CrownProvider.getCapabilityOf(stack);
		if (lazyCrownCap.isPresent()) {
			ICrownCapability cap = DaBoisMod.get(lazyCrownCap);
			tag.putBoolean("Activated", cap.isActivated());
		}

		return tag;
	}

	@Override
	public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
		super.readShareTag(stack, nbt);

		if (nbt != null) {
			LazyOptional<ICrownCapability> lazyCrownCap = CrownProvider.getCapabilityOf(stack);
			if (lazyCrownCap.isPresent()) {
				ICrownCapability cap = DaBoisMod.get(lazyCrownCap);
				if (nbt.contains("Activated", Constants.NBT.TAG_BYTE)) cap.setActivated(nbt.getBoolean("Activated"));
			}
		}
	}

	public boolean shouldScalePlayer(ItemStack stackIn, PlayerEntity playerIn) {
		return false;
	}

	public float getScale(ItemStack stackIn, PlayerEntity playerIn) {
		return 1.0F;
	}

	/**
	 * Fired when the crown activates.
	 * @param stackIn the crown stack.
	 * @param playerIn the player who activated the crown.
	 */
	public void onActivation(ItemStack stackIn, PlayerEntity playerIn) {
	}

	/**
	 * Fired when the crown deactivates.
	 * @param stackIn the crown stack.
	 * @param playerIn the player who deactivated the crown.
	 */
	public void onDeactivation(ItemStack stackIn, PlayerEntity playerIn) {
	}

	/**
	 * Fired every tick a player is wearing an activated version of the crown.
	 * @param stackIn the crown stack.
	 * @param playerIn the player.
	 */
	public void activatedTick(ItemStack stackIn, PlayerEntity playerIn) {
	}
}
