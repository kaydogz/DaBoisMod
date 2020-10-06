package com.github.kaydogz.daboismod.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public abstract class CrownEvent extends Event {

	private final ItemStack stack;
	private final PlayerEntity player;
	
	protected CrownEvent(ItemStack stackIn, PlayerEntity playerIn) {
		this.stack = stackIn;
		this.player = playerIn;
	}
	
	public ItemStack getCrownStack() {
		return this.stack;
	}
	
	public PlayerEntity getPlayer() {
		return this.player;
	}
	
	public static class Activate extends CrownEvent {

		public Activate(ItemStack stackIn, PlayerEntity playerIn) {
			super(stackIn, playerIn);
		}
	}
	
	public static class Deactivate extends CrownEvent {

		public Deactivate(ItemStack stackIn, PlayerEntity playerIn) {
			super(stackIn, playerIn);
		}
	}
}
