package com.github.kaydogz.daboismod.item;

import com.github.kaydogz.daboismod.quest.Quest;
import com.github.kaydogz.daboismod.quest.QuestHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class QuestScrollItem extends Item {

	public QuestScrollItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		playerIn.setActiveHand(handIn);
		if (!worldIn.isRemote) QuestHelper.assignQuest(new Quest(((ServerPlayerEntity) playerIn).getServerWorld()), (ServerPlayerEntity) playerIn);
		if (!playerIn.abilities.isCreativeMode) {
			stack.shrink(1);
		}
		return ActionResult.resultSuccess(stack);
	}
}