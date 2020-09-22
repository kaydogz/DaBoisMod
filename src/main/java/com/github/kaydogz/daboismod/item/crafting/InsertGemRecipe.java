package com.github.kaydogz.daboismod.item.crafting;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.provider.GodsCrownCapability;
import com.github.kaydogz.daboismod.item.CryptidGemItem;
import com.github.kaydogz.daboismod.item.GodsCrownItem;
import com.google.common.collect.Lists;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;

public class InsertGemRecipe extends SpecialRecipe {

	public InsertGemRecipe(ResourceLocation idIn) {
		super(idIn);
	}

	@Override
	public boolean matches(CraftingInventory inv, World worldIn) {
		ArrayList<ItemStack> list = Lists.newArrayList();

		for(int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack itemstack = inv.getStackInSlot(i);
			if (!itemstack.isEmpty()) {
				list.add(itemstack);
				if (list.size() > 2) return false;
				if (list.size() > 1) {
					ItemStack itemstack1 = list.get(0);
					if (!(itemstack.getItem() instanceof CryptidGemItem) && !(itemstack1.getItem() instanceof CryptidGemItem)) {
						return false;
					} else {
						if (!(itemstack.getItem() instanceof GodsCrownItem) && !(itemstack1.getItem() instanceof GodsCrownItem)) {
							return false;
						} else {
							ItemStack crownStack = itemstack.getItem() instanceof GodsCrownItem ? itemstack : itemstack1;
							if (DaBoisMod.get(GodsCrownCapability.getCapabilityOf(crownStack)).getInsertedGem().getItem() instanceof CryptidGemItem) return false;
						}
					}
				}
			}
		}

		return list.size() == 2;
	}

	@Override
	public ItemStack getCraftingResult(CraftingInventory inv) {
		ArrayList<ItemStack> list = Lists.newArrayList();

		for(int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack itemstack = inv.getStackInSlot(i);
			if (!itemstack.isEmpty()) {
				list.add(itemstack);
				if (list.size() > 2) return ItemStack.EMPTY;
				if (list.size() > 1) {
					ItemStack itemstack1 = list.get(0);
					if (!(itemstack.getItem() instanceof CryptidGemItem) && !(itemstack1.getItem() instanceof CryptidGemItem)) {
						return ItemStack.EMPTY;
					} else {
						if (!(itemstack.getItem() instanceof GodsCrownItem) && !(itemstack1.getItem() instanceof GodsCrownItem)) {
							return ItemStack.EMPTY;
						} else {
							ItemStack crownStack = itemstack.getItem() instanceof GodsCrownItem ? itemstack : itemstack1;
							if (DaBoisMod.get(GodsCrownCapability.getCapabilityOf(crownStack)).getInsertedGem().getItem() instanceof CryptidGemItem) return ItemStack.EMPTY;
						}
					}
				}
			}
		}
		
		if (list.size() == 2) {
			ItemStack itemstack3 = list.get(0);
			ItemStack itemstack4 = list.get(1);
			if ((itemstack3.getItem() instanceof GodsCrownItem || itemstack4.getItem() instanceof GodsCrownItem) && (itemstack3.getItem() instanceof CryptidGemItem || itemstack4.getItem() instanceof CryptidGemItem)) {
				boolean flag = itemstack3.getItem() instanceof GodsCrownItem;
				ItemStack crownStack = flag ? itemstack3.copy() : itemstack4.copy();
				ItemStack gemStack = flag ? itemstack4.copy() : itemstack3.copy();
				gemStack.setCount(1);

				DaBoisMod.get(GodsCrownCapability.getCapabilityOf(crownStack)).setInsertedGem(gemStack);
				return crownStack;
			}
		}
		
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canFit(int width, int height) {
		return width * height >= 2;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return DBMRecipeSerializers.CRAFTING_RECIPE_INSERTGEM.get();
	}

}
