package com.github.kaydogz.daboismod.command.argument;

import net.minecraft.command.arguments.ArgumentSerializer;
import net.minecraft.command.arguments.ArgumentTypes;

public class DBMArgumentTypes {

    public static void registerSerializers() {
        ArgumentTypes.register("quest_task", QuestTaskArgument.class, new ArgumentSerializer<>(QuestTaskArgument::questTask));
    }
}
