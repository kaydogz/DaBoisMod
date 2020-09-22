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

	// Break Blocks
	public static final RegistryObject<BreakBlocksQuestTask> BREAK_DIRT = QUEST_TASKS.register("break_dirt", () -> new BreakBlocksQuestTask(Blocks.DIRT, 30, 200));
	
	// Place Blocks
	public static final RegistryObject<PlaceBlocksQuestTask> PLACE_DIRT = QUEST_TASKS.register("place_dirt", () -> new PlaceBlocksQuestTask(Blocks.DIRT, 15, 100));
		
	// Craft Items
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_IRON_HELMETS = QUEST_TASKS.register("craft_iron_helmets", () -> new CraftItemsQuestTask(Items.IRON_HELMET, 1, 10));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_GOLDEN_HELMETS = QUEST_TASKS.register("craft_golden_helmets", () -> new CraftItemsQuestTask(Items.GOLDEN_HELMET, 1, 10));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_IRON_CHESTPLATES = QUEST_TASKS.register("craft_iron_chestplates", () -> new CraftItemsQuestTask(Items.IRON_CHESTPLATE, 1, 10));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_GOLDEN_CHESTPLATES = QUEST_TASKS.register("craft_golden_chestplates", () -> new CraftItemsQuestTask(Items.GOLDEN_CHESTPLATE, 1, 10));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_IRON_LEGGINGS = QUEST_TASKS.register("craft_iron_leggings", () -> new CraftItemsQuestTask(Items.IRON_LEGGINGS, 1, 10));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_GOLDEN_LEGGINGS = QUEST_TASKS.register("craft_golden_leggings", () -> new CraftItemsQuestTask(Items.GOLDEN_LEGGINGS, 1, 10));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_IRON_BOOTS = QUEST_TASKS.register("craft_iron_boots", () -> new CraftItemsQuestTask(Items.IRON_BOOTS, 1, 10));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_GOLDEN_BOOTS = QUEST_TASKS.register("craft_golden_boots", () -> new CraftItemsQuestTask(Items.GOLDEN_BOOTS, 1, 10));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_PISTONS = QUEST_TASKS.register("craft_pistons", () -> new CraftItemsQuestTask(Items.PISTON, 2, 15));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_SLIME_BLOCKS = QUEST_TASKS.register("craft_slime_blocks", () -> new CraftItemsQuestTask(Items.SLIME_BLOCK, 2, 15));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_FURNACES = QUEST_TASKS.register("craft_furnaces", () -> new CraftItemsQuestTask(Items.FURNACE, 10, 40));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_ENDER_CHESTS = QUEST_TASKS.register("craft_ender_chests", () -> new CraftItemsQuestTask(Items.ENDER_CHEST, 1, 6));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_LADDERS = QUEST_TASKS.register("craft_ladders", () -> new CraftItemsQuestTask(Items.LADDER, 5, 30));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_TNT = QUEST_TASKS.register("craft_tnt", () -> new CraftItemsQuestTask(Items.TNT, 2, 15));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_BOOKSHELVES = QUEST_TASKS.register("craft_bookshelves", () -> new CraftItemsQuestTask(Items.BOOKSHELF, 3, 20));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_NOTE_BLOCKS = QUEST_TASKS.register("craft_note_blocks", () -> new CraftItemsQuestTask(Items.NOTE_BLOCK, 5, 30));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_HAY_BLOCKS = QUEST_TASKS.register("craft_hay_blocks", () -> new CraftItemsQuestTask(Items.HAY_BLOCK, 10, 40));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_DRIED_KELP_BLOCKS = QUEST_TASKS.register("craft_dried_kelp_blocks", () -> new CraftItemsQuestTask(Items.DRIED_KELP_BLOCK, 2, 15));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_HONEYCOMB_BLOCKS = QUEST_TASKS.register("craft_honeycomb_blocks", () -> new CraftItemsQuestTask(Items.HONEYCOMB_BLOCK, 2, 15));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_SHIELDS = QUEST_TASKS.register("craft_shields", () -> new CraftItemsQuestTask(Items.SHIELD, 3, 20));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_MINECARTS = QUEST_TASKS.register("craft_minecarts", () -> new CraftItemsQuestTask(Items.MINECART, 3, 20));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_HOPPERS = QUEST_TASKS.register("craft_hoppers", () -> new CraftItemsQuestTask(Items.HOPPER, 3, 20));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_GOLDEN_APPLES = QUEST_TASKS.register("craft_golden_apples", () -> new CraftItemsQuestTask(Items.GOLDEN_APPLE, 1, 10));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_CAKES = QUEST_TASKS.register("craft_cakes", () -> new CraftItemsQuestTask(Items.CAKE, 1, 10));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_PUMPKIN_PIES = QUEST_TASKS.register("craft_pumpkin_pies", () -> new CraftItemsQuestTask(Items.PUMPKIN_PIE, 2, 15));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_PAINTINGS = QUEST_TASKS.register("craft_paintings", () -> new CraftItemsQuestTask(Items.PAINTING, 3, 20));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_ENDER_EYES = QUEST_TASKS.register("craft_ender_eyes", () -> new CraftItemsQuestTask(Items.ENDER_EYE, 2, 15));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_LECTERNS = QUEST_TASKS.register("craft_lecterns", () -> new CraftItemsQuestTask(Items.LECTERN, 3, 20));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_GLISTERING_MELON_SLICES = QUEST_TASKS.register("craft_glistering_melon_slices", () -> new CraftItemsQuestTask(Items.GLISTERING_MELON_SLICE, 3, 20));
	public static final RegistryObject<CraftItemsQuestTask> CRAFT_CAULDRONS = QUEST_TASKS.register("craft_cauldrons", () -> new CraftItemsQuestTask(Items.CAULDRON, 2, 15));
		
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
