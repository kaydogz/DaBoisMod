package com.github.kaydogz.daboismod.quest;

import com.github.kaydogz.daboismod.DaBoisMod;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class QuestTasks {

	private static final DeferredRegister<QuestTask> QUEST_TASKS = DeferredRegister.create(QuestTask.class, DaBoisMod.MODID);
	public static final Supplier<IForgeRegistry<QuestTask>> QUEST_TASKS_REGISTRY = QUEST_TASKS.makeRegistry("quest_task", RegistryBuilder::new);

	public static void registerQuestTasks(IEventBus eventBus) {
		QUEST_TASKS.register(eventBus);
	}

	// Kill Entities
	public static final RegistryObject<KillEntitiesQuestTask> KILL_ZOMBIES = QUEST_TASKS.register("kill_zombies", () -> new KillEntitiesQuestTask(EntityType.ZOMBIE, 5, 45));
	public static final RegistryObject<KillEntitiesQuestTask> KILL_CREEPERS = QUEST_TASKS.register("kill_creepers", () -> new KillEntitiesQuestTask(EntityType.CREEPER, 5, 40));
	public static final RegistryObject<KillEntitiesQuestTask> KILL_SKELETONS = QUEST_TASKS.register("kill_skeletons", () -> new KillEntitiesQuestTask(EntityType.SKELETON, 5, 35));
	public static final RegistryObject<KillEntitiesQuestTask> KILL_SPIDERS = QUEST_TASKS.register("kill_spiders", () -> new KillEntitiesQuestTask(EntityType.SPIDER, 5, 35));
	public static final RegistryObject<KillEntitiesQuestTask> KILL_ENDERMEN = QUEST_TASKS.register("kill_endermen", () -> new KillEntitiesQuestTask(EntityType.ENDERMAN, 1, 25));
	public static final RegistryObject<KillEntitiesQuestTask> KILL_WITCHES = QUEST_TASKS.register("kill_witches", () -> new KillEntitiesQuestTask(EntityType.WITCH, 1, 10));
	public static final RegistryObject<KillEntitiesQuestTask> KILL_PIGS = QUEST_TASKS.register("kill_pigs", () -> new KillEntitiesQuestTask(EntityType.PIG, 5, 30));
	public static final RegistryObject<KillEntitiesQuestTask> KILL_COWS = QUEST_TASKS.register("kill_cows", () -> new KillEntitiesQuestTask(EntityType.COW, 5, 30));
	public static final RegistryObject<KillEntitiesQuestTask> KILL_CHICKENS = QUEST_TASKS.register("kill_chickens", () -> new KillEntitiesQuestTask(EntityType.CHICKEN, 5, 30));
	public static final RegistryObject<KillEntitiesQuestTask> KILL_SHEEP = QUEST_TASKS.register("kill_sheep", () -> new KillEntitiesQuestTask(EntityType.SHEEP, 5, 30));
	public static final RegistryObject<KillEntitiesQuestTask> KILL_SQUIDS = QUEST_TASKS.register("kill_squids", () -> new KillEntitiesQuestTask(EntityType.SQUID, 5, 30));
}
