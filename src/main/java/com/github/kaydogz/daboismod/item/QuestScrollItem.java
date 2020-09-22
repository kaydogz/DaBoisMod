package com.github.kaydogz.daboismod.item;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.IQuestScrollCap;
import com.github.kaydogz.daboismod.capability.provider.QuestScrollCapability;
import com.github.kaydogz.daboismod.quest.Quest;
import com.github.kaydogz.daboismod.quest.QuestHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;
import java.util.List;

public class QuestScrollItem extends Item {

	public QuestScrollItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);

		LazyOptional<IQuestScrollCap> lazyCap = QuestScrollCapability.getCapabilityOf(stack);
		if (worldIn != null && lazyCap.isPresent()) {
			Quest quest = DaBoisMod.get(lazyCap).getQuest();

			// Objective
			TranslationTextComponent taskComponent = new TranslationTextComponent(quest.getQuestTask().getTranslationKey(), quest.getQuota());
			taskComponent.getStyle().setColor(TextFormatting.GRAY);
			tooltip.add(new TranslationTextComponent("gui.daboismod.quest.objective", taskComponent.getFormattedText()));
			
			// Difficulty
			tooltip.add(new TranslationTextComponent("gui.daboismod.quest.difficulty", quest.getDifficulty().getTextComponent()));
			
			// Reward
			TranslationTextComponent rewardComponent = new TranslationTextComponent(quest.getReward().getTranslationKey());
			rewardComponent.getStyle().setColor(quest.getReward().getRarity().color);
			tooltip.add(new TranslationTextComponent("gui.daboismod.quest.reward", new TranslationTextComponent("gui.daboismod.quest.reward.count", rewardComponent.getFormattedText(), quest.getReward().getCount())));
		}
	}

	@Nullable
	@Override
	public CompoundNBT getShareTag(ItemStack stack) {
		CompoundNBT tag = stack.getOrCreateTag();
		IQuestScrollCap cap = DaBoisMod.get(QuestScrollCapability.getCapabilityOf(stack));
		tag.put("Quest", cap.getQuest().write(new CompoundNBT()));

		return tag;
	}

	@Override
	public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
		super.readShareTag(stack, nbt);

		if (nbt != null && nbt.contains("Quest", Constants.NBT.TAG_COMPOUND)) {
			IQuestScrollCap cap = DaBoisMod.get(QuestScrollCapability.getCapabilityOf(stack));
			Quest quest = Quest.read(nbt.getCompound("Quest"));
			if (quest != null) cap.setQuest(quest);
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		playerIn.setActiveHand(handIn);
		if (!worldIn.isRemote) QuestHelper.assignQuest(DaBoisMod.get(QuestScrollCapability.getCapabilityOf(itemstack)).getQuest(), (ServerPlayerEntity) playerIn);
		if (!playerIn.abilities.isCreativeMode) itemstack.shrink(1);
		return ActionResult.resultSuccess(itemstack);
	}
}