package com.github.kaydogz.daboismod.command;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.provider.PlayerProvider;
import com.github.kaydogz.daboismod.command.argument.QuestTaskArgument;
import com.github.kaydogz.daboismod.quest.Quest;
import com.github.kaydogz.daboismod.quest.QuestHelper;
import com.github.kaydogz.daboismod.quest.QuestTask;
import com.github.kaydogz.daboismod.world.randomchimpevent.RandomChimpEvent;
import com.github.kaydogz.daboismod.world.randomchimpevent.RandomChimpEventManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.raid.RaidManager;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class DBMCommand {

	private static final DynamicCommandExceptionType ASSIGN_FAILED_EXCEPTION = new DynamicCommandExceptionType((playerName) -> {
		return new TranslationTextComponent("commands.daboismod.quest.assign.failed");
	});
	private static final DynamicCommandExceptionType CLEAR_SINGLE_FAILED_EXCEPTION = new DynamicCommandExceptionType((playerName) -> {
		return new TranslationTextComponent("commands.daboismod.quest.clear.failed.single", playerName);
	});
	private static final DynamicCommandExceptionType CLEAR_MULTIPLE_FAILED_EXCEPTION = new DynamicCommandExceptionType((playerCount) -> {
		return new TranslationTextComponent("commands.daboismod.quest.clear.failed.multiple", playerCount);
	});

	public static void registerCommand(final CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal(DaBoisMod.MODID)
				.then(Commands.literal("quest")
						.requires(source -> source.hasPermissionLevel(2))
						.then(Commands.literal("assign")
								.then(Commands.argument("targets", EntityArgument.players())
										.then(Commands.argument("task", QuestTaskArgument.questTask())
												.then(Commands.argument("quota", IntegerArgumentType.integer(1))
														.executes(context -> assignQuest(context.getSource(), EntityArgument.getPlayers(context, "targets"), QuestTaskArgument.getQuestTask(context, "task"), IntegerArgumentType.getInteger(context, "quota"), ItemStack.EMPTY))
														.then(Commands.argument("reward", ItemArgument.item())
																.executes(context -> assignQuest(context.getSource(), EntityArgument.getPlayers(context, "targets"), QuestTaskArgument.getQuestTask(context, "task"), IntegerArgumentType.getInteger(context, "quota"), ItemArgument.getItem(context, "reward").createStack(1, false)))
																.then(Commands.argument("count", IntegerArgumentType.integer(0))
																		.executes(context -> assignQuest(context.getSource(), EntityArgument.getPlayers(context, "targets"), QuestTaskArgument.getQuestTask(context, "task"), IntegerArgumentType.getInteger(context, "quota"), ItemArgument.getItem(context, "reward").createStack(IntegerArgumentType.getInteger(context, "count"), true)))
																		.then(Commands.argument("experience", IntegerArgumentType.integer(0))
																				.executes(context -> assignQuest(context.getSource(), EntityArgument.getPlayers(context, "targets"), QuestTaskArgument.getQuestTask(context, "task"), IntegerArgumentType.getInteger(context, "quota"), ItemArgument.getItem(context, "reward").createStack(IntegerArgumentType.getInteger(context, "count"), true), IntegerArgumentType.getInteger(context, "experience")))
																		)
																)
														)
												)
										)
								)
						)
						.then(Commands.literal("clear")
								.executes(context -> clearQuests(context.getSource(), Collections.singleton(context.getSource().asPlayer())))
								.then(Commands.argument("targets", EntityArgument.players())
										.executes(context -> clearQuests(context.getSource(), EntityArgument.getPlayers(context, "targets")))
								)
						)
				)
				.then(Commands.literal("randomchimpevent")
						.requires(source -> source.hasPermissionLevel(2))
						.then(Commands.literal("start")
								.executes(context -> startRandomChimpEvent(context.getSource(), new BlockPos(context.getSource().getPos()), 270))
								.then(Commands.argument("position", BlockPosArgument.blockPos())
										.executes(context -> startRandomChimpEvent(context.getSource(), BlockPosArgument.getBlockPos(context, "position"), 270))
										.then(Commands.argument("chimpCount", IntegerArgumentType.integer(9))
												.executes(context -> startRandomChimpEvent(context.getSource(), BlockPosArgument.getBlockPos(context, "position"), IntegerArgumentType.getInteger(context, "chimpCount")))
										)
								)
						)
						.then(Commands.literal("stop")
								.executes(context -> stopRandomChimpEvent(context.getSource(), new BlockPos(context.getSource().getPos())))
								.then(Commands.argument("position", BlockPosArgument.blockPos())
										.executes(context -> stopRandomChimpEvent(context.getSource(), BlockPosArgument.getBlockPos(context, "position")))
								)
						)
				)
		);
	}

	private static int assignQuest(CommandSource source, Collection<? extends ServerPlayerEntity> targets, QuestTask task, int quota, ItemStack reward) throws CommandSyntaxException {
		return assignQuest(source, targets, task, quota, reward, 16 * quota / task.getMaxQuota());
	}

	private static int assignQuest(CommandSource source, Collection<? extends ServerPlayerEntity> targets, QuestTask task, int quota, ItemStack reward, int experience) throws CommandSyntaxException {

		for (ServerPlayerEntity target : targets) {
			if (PlayerProvider.getCapabilityOf(target).isPresent()) {
				QuestHelper.assignQuest(new Quest(task, quota, 0, reward, experience, UUID.randomUUID()), target);
			} else {
				throw ASSIGN_FAILED_EXCEPTION.create(target.getDisplayName());
			}
		}

		if (targets.size() == 1) {
			source.sendFeedback(new TranslationTextComponent("commands.daboismod.quest.assign.success.single", targets.iterator().next().getDisplayName()), true);
		} else {
			source.sendFeedback(new TranslationTextComponent("commands.daboismod.quest.assign.success.multiple", targets.size()), true);
		}

		return targets.size();
	}
	
	private static int clearQuests(CommandSource source, Collection<? extends ServerPlayerEntity> targets) throws CommandSyntaxException {
		int questsCleared = 0;

		for (ServerPlayerEntity target : targets) {
			if (PlayerProvider.getCapabilityOf(target).isPresent()) questsCleared += QuestHelper.clearQuests(target);
		}

		if (questsCleared == 0) {
			if (targets.size() == 1) {
				throw CLEAR_SINGLE_FAILED_EXCEPTION.create(targets.iterator().next().getName().getUnformattedComponentText());
			} else {
				throw CLEAR_MULTIPLE_FAILED_EXCEPTION.create(targets.size());
			}
		} else {
			if (targets.size() == 1) {
				source.sendFeedback(new TranslationTextComponent("commands.daboismod.quest.clear.success.single", questsCleared, targets.iterator().next().getDisplayName()), true);
			} else {
				source.sendFeedback(new TranslationTextComponent("commands.daboismod.quest.clear.success.multiple", questsCleared, targets.size()), true);
			}

			return 1;
		}
	}

	private static int startRandomChimpEvent(CommandSource source, BlockPos position, int chimpCount) {
		RandomChimpEventManager.getByWorld(source.getWorld()).findOrCreateRandomChimpEvent(position);
		source.sendFeedback(new TranslationTextComponent("commands.daboismod.randomchimpevent.start.success", chimpCount), true);

		return 1;
	}

	private static int stopRandomChimpEvent(CommandSource source, BlockPos position) {
		RandomChimpEventManager.getByWorld(source.getWorld()).findRandomChimpEvent(position, 100).stop();
		source.sendFeedback(new TranslationTextComponent("commands.daboismod.randomchimpevent.stop.success"), true);

		return 1;
	}
}
