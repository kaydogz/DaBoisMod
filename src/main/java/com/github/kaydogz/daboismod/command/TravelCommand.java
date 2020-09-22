package com.github.kaydogz.daboismod.command;

import com.github.kaydogz.daboismod.world.DBMTeleporter;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.DimensionArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.dimension.DimensionType;

import java.util.Collection;

public class TravelCommand {

	/**
	 * Registers the command.
	 * @param dispatcher the command dispatcher.
	 */
	public static void registerCommand(final CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("travel")
		        .requires(source -> source.hasPermissionLevel(2))
		        .then(Commands.argument("targets", EntityArgument.entities())
		          .then((Commands.argument("dimension", DimensionArgument.getDimension())
		            .executes(context -> TravelCommand.travelEntity((CommandSource) context.getSource(), EntityArgument.getEntities(context, "targets"), DimensionArgument.getDimensionArgument(context, "dimension")))))));
	}
	
	/**
	 * Travels an entity to a dimension via command. 
	 * @param source the command source.
	 * @param targets the targets of the command.
	 * @param dimension the dimension to travel to.
	 * @return the amount of targets traveled.
	 */
	private static int travelEntity(CommandSource source, Collection<? extends Entity> targets, DimensionType dimension) {
		if (dimension != null) {
			for (Entity entityIn : targets) {
				entityIn.changeDimension(dimension, new DBMTeleporter(false));
			}
			if (targets.size() == 1) {
		         source.sendFeedback(new TranslationTextComponent("commands.daboismod.travel.success.single", targets.iterator().next().getDisplayName(), new StringTextComponent(dimension.getRegistryName().toString())), true);
		    } else {
		         source.sendFeedback(new TranslationTextComponent("commands.daboismod.travel.success.multiple", targets.size(), new StringTextComponent(dimension.getRegistryName().toString())), true);
		    }
		}
		
		return targets.size();
	}
}
