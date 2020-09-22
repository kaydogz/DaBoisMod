package com.github.kaydogz.daboismod.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public abstract class DBMItemGroup extends ItemGroup {
	
	public static final ItemGroup DA_BOIS_GROUP = new DBMItemGroup("daBois") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(DBMItems.ANCIENT_INGOT.get());
		}
	};
	
	public DBMItemGroup(String name) {
		super(name);
	}
}
