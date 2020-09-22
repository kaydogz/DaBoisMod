package com.github.kaydogz.daboismod.item;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.IGodsCrownCap;
import com.github.kaydogz.daboismod.capability.provider.GodsCrownCapability;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;

public class GodsCrownItem extends ArmorItem {
	
	public GodsCrownItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
		super(materialIn, slot, builder);
	}
	
	@Override
	public void addInformation(ItemStack crownStack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(crownStack, worldIn, tooltip, flagIn);

		LazyOptional<IGodsCrownCap> godsCrownCap = GodsCrownCapability.getCapabilityOf(crownStack);
		if (worldIn != null && godsCrownCap.isPresent()) {
			IGodsCrownCap crownCap = DaBoisMod.get(godsCrownCap);

			// Inserted Gem Tooltip
			ItemStack insertedGem = crownCap.getInsertedGem();
			TranslationTextComponent insertedGemComponent = insertedGem.getItem() instanceof CryptidGemItem ? new TranslationTextComponent(insertedGem.getItem().getTranslationKey()) : new TranslationTextComponent("item.daboismod.gods_crown.empty");
			insertedGemComponent.getStyle().setColor(insertedGem.getItem() instanceof CryptidGemItem ? insertedGem.getRarity().color : TextFormatting.GRAY);
			tooltip.add(new TranslationTextComponent("item.daboismod.gods_crown.insertedGem", insertedGemComponent.getFormattedText()));
			
			// Activated Tooltip
			boolean isActivated = crownCap.isActivated();
			TranslationTextComponent activatedComponent = new TranslationTextComponent(isActivated ? "item.daboismod.gods_crown.on" : "item.daboismod.gods_crown.off");
			activatedComponent.getStyle().setColor(isActivated ? TextFormatting.GREEN : TextFormatting.RED);
			tooltip.add(new TranslationTextComponent("item.daboismod.gods_crown.activated", activatedComponent.getFormattedText()));
		}
	}
}
