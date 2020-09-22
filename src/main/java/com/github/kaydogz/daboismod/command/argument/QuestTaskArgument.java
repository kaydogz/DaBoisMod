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

	private static final Collection<String> EXAMPLES = Stream.of(QuestTasks.KILL_ZOMBIES.get(), QuestTasks.PLACE_DIRT.get()).map((key) -> QuestTasks.QUEST_TASKS_REGISTRY.get().getKey(key).toString()).collect(Collectors.toList());
	
	public static final DynamicCommandExceptionType INVALID_QUEST_TASK = new DynamicCommandExceptionType((msg) -> new TranslationTextComponent("argument.daboismod.questTask.invalid", msg));
	
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> ctx, SuggestionsBuilder builder) {
		return ISuggestionProvider.func_212476_a(Streams.stream(QuestTasks.QUEST_TASKS_REGISTRY.get()).map(QuestTasks.QUEST_TASKS_REGISTRY.get()::getKey), builder);
	}
	
	public Collection<String> getExamples() {
		return QuestTaskArgument.EXAMPLES;
	}
	
	public static QuestTaskArgument questTask() {
		return new QuestTaskArgument();
	}

	@Override
	public QuestTask parse(StringReader reader) throws CommandSyntaxException {
		ResourceLocation resourcelocation = ResourceLocation.read(reader);
		Optional<QuestTask> questTask = Optional.ofNullable(QuestTasks.QUEST_TASKS_REGISTRY.get().getValue(resourcelocation));
	    return questTask.orElseThrow(() -> {
	    	return QuestTaskArgument.INVALID_QUEST_TASK.create(resourcelocation);
	    });
	}

	public static QuestTask getQuestTask(CommandContext<CommandSource> source, String arg) {
		return source.getArgument(arg, QuestTask.class);
	}
}