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
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;

public class RemoveGemRecipe extends SpecialRecipe {

	public RemoveGemRecipe(ResourceLocation idIn) {
		super(idIn);
	}

	@Override
	public boolean matches(CraftingInventory inv, World worldIn) {
		ArrayList<ItemStack> list = Lists.newArrayList();

		for(int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack itemstack = inv.getStackInSlot(i);
			if (!itemstack.isEmpty()) {
				list.add(itemstack);
				if (!(itemstack.getItem() instanceof GodsCrownItem) || list.size() > 1) {
					return false;
				} else {
					if (!(DaBoisMod.get(GodsCrownCapability.getCapabilityOf(itemstack)).getInsertedGem().getItem() instanceof CryptidGemItem)) return false;
				}
			}
		}

		return list.size() == 1;
	}

	@Override
	public ItemStack getCraftingResult(CraftingInventory inv) {
		ArrayList<ItemStack> list = Lists.newArrayList();

		for(int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack itemstack = inv.getStackInSlot(i);
			if (!itemstack.isEmpty()) {
				list.add(itemstack);
				if (!(itemstack.getItem() instanceof GodsCrownItem) || list.size() > 1) {
					return ItemStack.EMPTY;
				} else {
					if (!(DaBoisMod.get(GodsCrownCapability.getCapabilityOf(itemstack)).getInsertedGem().getItem() instanceof CryptidGemItem)) return ItemStack.EMPTY;
				}
			}
		}
		
		if (list.size() == 1) {
			ItemStack itemstack1 = list.get(0);
			if (itemstack1.getItem() instanceof GodsCrownItem) {
				return DaBoisMod.get(GodsCrownCapability.getCapabilityOf(itemstack1)).getInsertedGem();
			}
		}
		
		return ItemStack.EMPTY;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
		int stackIndex = 4;
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack itemstack = inv.getStackInSlot(i);
			if (!itemstack.isEmpty() && itemstack.getItem() instanceof GodsCrownItem) {
				stackIndex = i;
			}
		}
		
		NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
		ItemStack stack = inv.getStackInSlot(stackIndex).copy();
		DaBoisMod.get(GodsCrownCapability.getCapabilityOf(stack)).setInsertedGem(ItemStack.EMPTY);
		nonnulllist.set(stackIndex, stack);
		return nonnulllist;
	}
	
	@Override
	public boolean canFit(int width, int height) {
		return width * height >= 1;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return DBMRecipeSerializers.CRAFTING_RECIPE_REMOVEGEM.get();
	}

}
