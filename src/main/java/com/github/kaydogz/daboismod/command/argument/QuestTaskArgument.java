package com.github.kaydogz.daboismod.command.argument;

import com.github.kaydogz.daboismod.quest.QuestTask;
import com.github.kaydogz.daboismod.quest.QuestTasks;
import com.google.common.collect.Streams;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuestTaskArgument implements ArgumentType<QuestTask> {

	private static Collection<String> examples;
	
	public static final DynamicCommandExceptionType INVALID_QUEST_TASK = new DynamicCommandExceptionType((msg) -> new TranslationTextComponent("argument.daboismod.questTask.invalid", msg));

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> ctx, SuggestionsBuilder builder) {
		return ISuggestionProvider.func_212476_a(Streams.stream(QuestTasks.QUEST_TASKS_REGISTRY.get()).map(QuestTasks.QUEST_TASKS_REGISTRY.get()::getKey), builder);
	}

	@Override
	public Collection<String> getExamples() {
		return examples;
	}

	public static QuestTaskArgument questTask() {
		return new QuestTaskArgument();
	}

	@Override
	public QuestTask parse(StringReader reader) throws CommandSyntaxException {
		ResourceLocation resourcelocation = ResourceLocation.read(reader);
		Optional<QuestTask> questTask = Optional.ofNullable(QuestTasks.QUEST_TASKS_REGISTRY.get().getValue(resourcelocation));
	    return questTask.orElseThrow(() -> INVALID_QUEST_TASK.create(resourcelocation));
	}

	public static QuestTask getQuestTask(CommandContext<CommandSource> source, String arg) {
		return source.getArgument(arg, QuestTask.class);
	}

	public static void createExamples() {
		examples = Stream.of(QuestTasks.KILL_ZOMBIES.get(), QuestTasks.KILL_ZOMBIES.get()).map((key) -> QuestTasks.QUEST_TASKS_REGISTRY.get().getKey(key).toString()).collect(Collectors.toList());
	}
}
