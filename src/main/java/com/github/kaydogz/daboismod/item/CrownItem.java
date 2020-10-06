package com.github.kaydogz.daboismod.item;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.ICrownCapability;
import com.github.kaydogz.daboismod.capability.provider.CrownProvider;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.Pose;
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

		LazyOptional<ICrownCapability> godsCrownCap = CrownProvider.getCapabilityOf(crownStack);
		if (worldIn != null && godsCrownCap.isPresent()) {
			ICrownCapability crownCap = DaBoisMod.get(godsCrownCap);
			
			// Activated Tooltip
			boolean isActivated = crownCap.isActivated();
			TranslationTextComponent activatedComponent = new TranslationTextComponent(isActivated ? "item.daboismod.crown.on" : "item.daboismod.crown.off");
			activatedComponent.getStyle().setColor(isActivated ? TextFormatting.GREEN : TextFormatting.RED);
			tooltip.add(new TranslationTextComponent("item.daboismod.crown.activated", activatedComponent.getFormattedText()));
		}
	}

	@Nullable
	@Override
	public CompoundNBT getShareTag(ItemStack stack) {
		CompoundNBT tag = stack.getOrCreateTag();
		ICrownCapability cap = DaBoisMod.get(CrownProvider.getCapabilityOf(stack));
		tag.putBoolean("Activated", cap.isActivated());

		return tag;
	}

	@Override
	public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
		super.readShareTag(stack, nbt);

		if (nbt != null) {
			ICrownCapability cap = DaBoisMod.get(CrownProvider.getCapabilityOf(stack));
			if (nbt.contains("Activated", Constants.NBT.TAG_BYTE)) cap.setActivated(nbt.getBoolean("Activated"));
		}
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
