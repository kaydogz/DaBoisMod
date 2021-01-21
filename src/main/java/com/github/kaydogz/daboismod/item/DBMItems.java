package com.github.kaydogz.daboismod.item;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.block.DBMBlocks;
import com.github.kaydogz.daboismod.entity.DBMEntities;
import com.github.kaydogz.daboismod.util.DBMSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DBMItems {
	
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DaBoisMod.MODID);
	
	public static void registerItems(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}

	private static <T extends Block> RegistryObject<BlockItem> registerBlockItem(RegistryObject<T> block) {
		return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP)));
	}

	// General Items
	public static final RegistryObject<Item> ANCIENT_INGOT = ITEMS.register("ancient_ingot", () -> new Item(new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP)));
	public static final RegistryObject<Item> ANCIENT_ROD = ITEMS.register("ancient_rod", () -> new Item(new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP)));
	public static final RegistryObject<Item> MARIJUANA = ITEMS.register("marijuana", () -> new Item(new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP)));
	public static final RegistryObject<QuestScrollItem> QUEST_SCROLL = ITEMS.register("quest_scroll", () -> new QuestScrollItem(new Item.Properties().maxStackSize(1).group(DBMItemGroup.DA_BOIS_GROUP)));
	public static final RegistryObject<BandageItem> BANDAGE = ITEMS.register("bandage", () -> new BandageItem(new Item.Properties().maxStackSize(16).group(DBMItemGroup.DA_BOIS_GROUP)));
	public static final RegistryObject<BoatItem> PADAUK_BOAT = ITEMS.register("padauk_boat", () -> new BoatItem(BoatEntity.Type.ACACIA, new Item.Properties().maxStackSize(1).group(DBMItemGroup.DA_BOIS_GROUP)));

	// Gems
	public static final RegistryObject<GemItem> TOPAZ = ITEMS.register("topaz", () -> new GemItem(new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP).isImmuneToFire().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<GemItem> AMETHYST = ITEMS.register("amethyst", () -> new GemItem(new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP).isImmuneToFire().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<GemItem> RUBY = ITEMS.register("ruby", () -> new GemItem(new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP).isImmuneToFire().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<GemItem> AMBER = ITEMS.register("amber", () -> new GemItem(new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP).isImmuneToFire().rarity(Rarity.UNCOMMON)));

	// Crowns
	public static final RegistryObject<AmberCrownItem> AMBER_CROWN = ITEMS.register("amber_crown", () -> new AmberCrownItem(DBMArmorMaterial.AMBER_CROWN, EquipmentSlotType.HEAD, new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP).isImmuneToFire().defaultMaxDamage(780).rarity(Rarity.EPIC)));
	public static final RegistryObject<TopazCrownItem> TOPAZ_CROWN = ITEMS.register("topaz_crown", () -> new TopazCrownItem(DBMArmorMaterial.TOPAZ_CROWN, EquipmentSlotType.HEAD, new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP).isImmuneToFire().defaultMaxDamage(780).rarity(Rarity.EPIC)));
	public static final RegistryObject<AmethystCrownItem> AMETHYST_CROWN = ITEMS.register("amethyst_crown", () -> new AmethystCrownItem(DBMArmorMaterial.AMETHYST_CROWN, EquipmentSlotType.HEAD, new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP).isImmuneToFire().defaultMaxDamage(780).rarity(Rarity.EPIC)));
	public static final RegistryObject<RubyCrownItem> RUBY_CROWN = ITEMS.register("ruby_crown", () -> new RubyCrownItem(DBMArmorMaterial.RUBY_CROWN, EquipmentSlotType.HEAD, new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP).isImmuneToFire().defaultMaxDamage(780).rarity(Rarity.EPIC)));

	// Food
	public static final RegistryObject<AncientFruitItem> ANCIENT_FRUIT = ITEMS.register("ancient_fruit", () -> new AncientFruitItem(new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP).food(DBMFoods.ANCIENT_FRUIT)));
	
	// Spawn Eggs
	public static final RegistryObject<DBMSpawnEggItem> SASQUATCH_SPAWN_EGG = ITEMS.register("sasquatch_spawn_egg", () -> new DBMSpawnEggItem(DBMEntities.SASQUATCH, 0x654321, 0xa9a9a9, new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP)));
	public static final RegistryObject<DBMSpawnEggItem> WEREWOLF_SPAWN_EGG = ITEMS.register("werewolf_spawn_egg", () -> new DBMSpawnEggItem(DBMEntities.WEREWOLF, 0x2b1d0e, 0xcc0000, new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP)));
	public static final RegistryObject<DBMSpawnEggItem> FLESH_CREEPER_SPAWN_EGG = ITEMS.register("flesh_creeper_spawn_egg", () -> new DBMSpawnEggItem(DBMEntities.FLESH_CREEPER, 0xebbca4, 0x59051c, new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP)));

	// Armor
	public static final RegistryObject<ArmorItem> ANCIENT_HELMET = ITEMS.register("ancient_helmet", () -> new ArmorItem(DBMArmorMaterial.ANCIENT, EquipmentSlotType.HEAD, new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP)));
	public static final RegistryObject<ArmorItem> ANCIENT_CHESTPLATE = ITEMS.register("ancient_chestplate", () -> new ArmorItem(DBMArmorMaterial.ANCIENT, EquipmentSlotType.CHEST, new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP)));
	public static final RegistryObject<ArmorItem> ANCIENT_LEGGINGS = ITEMS.register("ancient_leggings", () -> new ArmorItem(DBMArmorMaterial.ANCIENT, EquipmentSlotType.LEGS, new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP)));
	public static final RegistryObject<ArmorItem> ANCIENT_BOOTS = ITEMS.register("ancient_boots", () -> new ArmorItem(DBMArmorMaterial.ANCIENT, EquipmentSlotType.FEET, new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP)));
	
	// Weapons
	public static final RegistryObject<SwordItem> ANCIENT_SWORD = ITEMS.register("ancient_sword", () -> new SwordItem(DBMItemTier.ANCIENT, 3, -2.4F, new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP)));
	public static final RegistryObject<SwordItem> CHANDLERS_WRATH = ITEMS.register("chandlers_wrath", () -> new SwordItem(DBMItemTier.CHANDLER, 4, -2.6F, new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP).rarity(Rarity.RARE)));

	// Tools
	public static final RegistryObject<PickaxeItem> ANCIENT_PICKAXE = ITEMS.register("ancient_pickaxe", () -> new PickaxeItem(DBMItemTier.ANCIENT, 1, -2.8F, new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP)));
	public static final RegistryObject<AxeItem> ANCIENT_AXE = ITEMS.register("ancient_axe", () -> new AxeItem(DBMItemTier.ANCIENT, 6.0F, -2.9F, new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP)));
	public static final RegistryObject<ShovelItem> ANCIENT_SHOVEL = ITEMS.register("ancient_shovel", () -> new ShovelItem(DBMItemTier.ANCIENT, 1.5F, -3.0F, new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP)));
	public static final RegistryObject<HoeItem> ANCIENT_HOE = ITEMS.register("ancient_hoe", () -> new HoeItem(DBMItemTier.ANCIENT, -5, 0.0F, new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP)));
	
	// Music Discs
	public static final RegistryObject<MusicDiscItem> MUSIC_DISC_MR_BLUE_SKY = ITEMS.register("music_disc_mr_blue_sky", () -> new MusicDiscItem(13, DBMSoundEvents.MR_BLUE_SKY, (new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP).maxStackSize(1).rarity(Rarity.RARE))));
	public static final RegistryObject<MusicDiscItem> MUSIC_DISC_SPHERE = ITEMS.register("music_disc_sphere", () -> new MusicDiscItem(13, DBMSoundEvents.SPHERE, (new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP).maxStackSize(1).rarity(Rarity.RARE))));
	public static final RegistryObject<MusicDiscItem> MUSIC_DISC_CARNIVORES = ITEMS.register("music_disc_carnivores", () -> new MusicDiscItem(13, DBMSoundEvents.CARNIVORES, (new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP).maxStackSize(1).rarity(Rarity.RARE))));
	public static final RegistryObject<MusicDiscItem> MUSIC_DISC_MEGALOVANIA = ITEMS.register("music_disc_megalovania", () -> new MusicDiscItem(14, DBMSoundEvents.MEGALOVANIA, (new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP).maxStackSize(1).rarity(Rarity.RARE))));
	public static final RegistryObject<MusicDiscItem> MUSIC_DISC_A_DAY_IN_THE_LIFE = ITEMS.register("music_disc_a_day_in_the_life", () -> new MusicDiscItem(13, DBMSoundEvents.A_DAY_IN_THE_LIFE, (new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP).maxStackSize(1).rarity(Rarity.RARE))));
	public static final RegistryObject<MusicDiscItem> MUSIC_DISC_HERE_COMES_THE_SUN = ITEMS.register("music_disc_here_comes_the_sun", () -> new MusicDiscItem(13, DBMSoundEvents.HERE_COMES_THE_SUN, (new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP).maxStackSize(1).rarity(Rarity.RARE))));

	// Gem Block Items
	public static final RegistryObject<GemBlockItem> TOPAZ_BLOCK = ITEMS.register(DBMBlocks.TOPAZ_BLOCK.getId().getPath(), () -> new GemBlockItem(DBMBlocks.TOPAZ_BLOCK.get(), new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP).isImmuneToFire()));
	public static final RegistryObject<GemBlockItem> AMBER_BLOCK = ITEMS.register(DBMBlocks.AMBER_BLOCK.getId().getPath(), () -> new GemBlockItem(DBMBlocks.AMBER_BLOCK.get(), new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP).isImmuneToFire()));
	public static final RegistryObject<GemBlockItem> RUBY_BLOCK = ITEMS.register(DBMBlocks.RUBY_BLOCK.getId().getPath(), () -> new GemBlockItem(DBMBlocks.RUBY_BLOCK.get(), new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP).isImmuneToFire()));
	public static final RegistryObject<GemBlockItem> AMETHYST_BLOCK = ITEMS.register(DBMBlocks.AMETHYST_BLOCK.getId().getPath(), () -> new GemBlockItem(DBMBlocks.AMETHYST_BLOCK.get(), new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP).isImmuneToFire()));

	// Block Items
	public static final RegistryObject<BlockItem> ANCIENT_BLOCK = registerBlockItem(DBMBlocks.ANCIENT_BLOCK);
	public static final RegistryObject<BlockItem> BOTSWANIAN_GRASS_BLOCK = registerBlockItem(DBMBlocks.BOTSWANIAN_GRASS_BLOCK);
	public static final RegistryObject<BlockItem> BOTSWANIAN_GRASS = registerBlockItem(DBMBlocks.BOTSWANIAN_GRASS);
	public static final RegistryObject<BlockItem> BOTSWANIAN_TALL_GRASS = registerBlockItem(DBMBlocks.BOTSWANIAN_TALL_GRASS);
	public static final RegistryObject<BlockItem> BOTSWANIAN_DIRT = registerBlockItem(DBMBlocks.BOTSWANIAN_DIRT);
	public static final RegistryObject<BlockItem> ANCIENT_ORE = registerBlockItem(DBMBlocks.ANCIENT_ORE);
	public static final RegistryObject<BlockItem> CANNABIS = registerBlockItem(DBMBlocks.CANNABIS);
	public static final RegistryObject<BlockItem> PADAUK_PLANKS = registerBlockItem(DBMBlocks.PADAUK_PLANKS);
	public static final RegistryObject<BlockItem> PADAUK_LOG = registerBlockItem(DBMBlocks.PADAUK_LOG);
	public static final RegistryObject<BlockItem> PADAUK_SAPLING = registerBlockItem(DBMBlocks.PADAUK_SAPLING);
	public static final RegistryObject<BlockItem> PADAUK_LEAVES = registerBlockItem(DBMBlocks.PADAUK_LEAVES);
	public static final RegistryObject<BlockItem> STRIPPED_PADAUK_LOG = registerBlockItem(DBMBlocks.STRIPPED_PADAUK_LOG);
	public static final RegistryObject<BlockItem> PADAUK_WOOD = registerBlockItem(DBMBlocks.PADAUK_WOOD);
	public static final RegistryObject<BlockItem> STRIPPED_PADAUK_WOOD = registerBlockItem(DBMBlocks.STRIPPED_PADAUK_WOOD);
	public static final RegistryObject<BlockItem> PADAUK_BUTTON = registerBlockItem(DBMBlocks.PADAUK_BUTTON);
	public static final RegistryObject<BlockItem> PADAUK_DOOR = ITEMS.register(DBMBlocks.PADAUK_DOOR.getId().getPath(), () -> new TallBlockItem(DBMBlocks.PADAUK_DOOR.get(), new Item.Properties().group(DBMItemGroup.DA_BOIS_GROUP)));
	public static final RegistryObject<BlockItem> PADAUK_FENCE = registerBlockItem(DBMBlocks.PADAUK_FENCE);
	public static final RegistryObject<BlockItem> PADAUK_FENCE_GATE = registerBlockItem(DBMBlocks.PADAUK_FENCE_GATE);
	public static final RegistryObject<BlockItem> PADAUK_PRESSURE_PLATE = registerBlockItem(DBMBlocks.PADAUK_PRESSURE_PLATE);
	public static final RegistryObject<BlockItem> PADAUK_SIGN = ITEMS.register(DBMBlocks.PADAUK_SIGN.getId().getPath(), () -> new SignItem(new Item.Properties().maxStackSize(16).group(DBMItemGroup.DA_BOIS_GROUP), DBMBlocks.PADAUK_SIGN.get(), DBMBlocks.PADAUK_WALL_SIGN.get()));
	public static final RegistryObject<BlockItem> PADAUK_STAIRS = registerBlockItem(DBMBlocks.PADAUK_STAIRS);
	public static final RegistryObject<BlockItem> PADAUK_SLAB = registerBlockItem(DBMBlocks.PADAUK_SLAB);
	public static final RegistryObject<BlockItem> PADAUK_TRAPDOOR = registerBlockItem(DBMBlocks.PADAUK_TRAPDOOR);
}
