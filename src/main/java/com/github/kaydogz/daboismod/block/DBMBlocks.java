package com.github.kaydogz.daboismod.block;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.block.trees.PadaukTree;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DBMBlocks {
	
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DaBoisMod.MODID);
	
	public static void registerBlocks(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}

	private static RotatedPillarBlock createLogBlock(MaterialColor topColor, MaterialColor barkColor) {
		return new DBMLogBlock(AbstractBlock.Properties.create(Material.WOOD, (state) -> {
			return state.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor;
		}).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
	}
	
	// General Blocks
	public static final RegistryObject<Block> ANCIENT_BLOCK = BLOCKS.register("ancient_block", () -> new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(15.0F, 1200.0F).sound(SoundType.METAL)));
	public static final RegistryObject<GrassBlock> BOTSWANIAN_GRASS_BLOCK = BLOCKS.register("botswanian_grass_block", () -> new BotswanianGrassBlock(Block.Properties.create(Material.ORGANIC).harvestTool(ToolType.SHOVEL).tickRandomly().hardnessAndResistance(0.7F).sound(SoundType.PLANT)));
	public static final RegistryObject<DBMDirtBlock> BOTSWANIAN_DIRT = BLOCKS.register("botswanian_dirt", () -> new DBMDirtBlock(Block.Properties.create(Material.EARTH, MaterialColor.RED).harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.6F).sound(SoundType.GROUND)));

	// Cryptid Gem Blocks
	public static final RegistryObject<Block> TOPAZ_BLOCK = BLOCKS.register("topaz_block", () -> new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(10.0F, 8.5F).sound(SoundType.METAL)));
	public static final RegistryObject<Block> AMBER_BLOCK = BLOCKS.register("amber_block", () -> new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(10.0F, 8.5F).sound(SoundType.METAL)));
	public static final RegistryObject<Block> AMETHYST_BLOCK = BLOCKS.register("amethyst_block", () -> new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(10.0F, 8.5F).sound(SoundType.METAL)));
	public static final RegistryObject<Block> RUBY_BLOCK = BLOCKS.register("ruby_block", () -> new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(10.0F, 8.5F).sound(SoundType.METAL)));
	
	// Ores
	public static final RegistryObject<Block> ANCIENT_ORE = BLOCKS.register("ancient_ore", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F).sound(SoundType.STONE)));
	
	// Plants
	public static final RegistryObject<CannabisBlock> CANNABIS = BLOCKS.register("cannabis", () -> new CannabisBlock(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT)));	
	public static final RegistryObject<DBMTallGrassBlock> BOTSWANIAN_GRASS = BLOCKS.register("botswanian_grass", () -> new DBMTallGrassBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT)));
	public static final RegistryObject<DBMDoublePlantBlock> BOTSWANIAN_TALL_GRASS = BLOCKS.register("botswanian_tall_grass", () -> new DBMDoublePlantBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT)));
	
	// Padauk Wood
	public static final RegistryObject<Block> PADAUK_PLANKS = BLOCKS.register("padauk_planks", () -> new Block(Block.Properties.create(Material.WOOD, MaterialColor.RED).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<DBMSaplingBlock> PADAUK_SAPLING = BLOCKS.register("padauk_sapling", () -> new DBMSaplingBlock(new PadaukTree(), Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT)));
	public static final RegistryObject<FlowerPotBlock> POTTED_PADAUK_SAPLING = BLOCKS.register("potted_padauk_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, PADAUK_SAPLING, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F).notSolid()));
	public static final RegistryObject<RotatedPillarBlock> PADAUK_LOG = BLOCKS.register("padauk_log", () -> createLogBlock(MaterialColor.RED, MaterialColor.QUARTZ));
	public static final RegistryObject<LeavesBlock> PADAUK_LEAVES = BLOCKS.register("padauk_leaves", () -> new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid()));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_PADAUK_LOG = BLOCKS.register("stripped_padauk_log", () -> createLogBlock(MaterialColor.RED, MaterialColor.RED));
	public static final RegistryObject<RotatedPillarBlock> PADAUK_WOOD = BLOCKS.register("padauk_wood", () -> new DBMLogBlock(Block.Properties.create(Material.WOOD, MaterialColor.QUARTZ).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_PADAUK_WOOD = BLOCKS.register("stripped_padauk_wood", () -> new DBMLogBlock(Block.Properties.create(Material.WOOD, MaterialColor.RED).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<DBMWoodButtonBlock> PADAUK_BUTTON = BLOCKS.register("padauk_button", () -> new DBMWoodButtonBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
	public static final RegistryObject<DBMDoorBlock> PADAUK_DOOR = BLOCKS.register("padauk_door", () -> new DBMDoorBlock(Block.Properties.create(Material.WOOD, MaterialColor.RED).hardnessAndResistance(3.0F).sound(SoundType.WOOD).notSolid()));
	public static final RegistryObject<FenceBlock> PADAUK_FENCE = BLOCKS.register("padauk_fence", () -> new FenceBlock(Block.Properties.create(Material.WOOD, MaterialColor.RED).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<FenceGateBlock> PADAUK_FENCE_GATE = BLOCKS.register("padauk_fence_gate", () -> new FenceGateBlock(Block.Properties.create(Material.WOOD, MaterialColor.RED).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<DBMPressurePlateBlock> PADAUK_PRESSURE_PLATE = BLOCKS.register("padauk_pressure_plate", () -> new DBMPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOD, MaterialColor.RED).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
	public static final RegistryObject<DBMStandingSignBlock> PADAUK_SIGN = BLOCKS.register("padauk_sign", () -> new DBMStandingSignBlock(Block.Properties.create(Material.WOOD, MaterialColor.RED).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), DBMWoodType.padauk));
	public static final RegistryObject<DBMWallSignBlock> PADAUK_WALL_SIGN = BLOCKS.register("padauk_wall_sign", () -> new DBMWallSignBlock(Block.Properties.create(Material.WOOD, MaterialColor.RED).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD).lootFrom(PADAUK_SIGN), DBMWoodType.padauk));
	public static final RegistryObject<SlabBlock> PADAUK_SLAB = BLOCKS.register("padauk_slab", () -> new SlabBlock(Block.Properties.create(Material.WOOD, MaterialColor.RED).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<StairsBlock> PADAUK_STAIRS = BLOCKS.register("padauk_stairs", () -> new StairsBlock(() -> PADAUK_PLANKS.get().getDefaultState(), Block.Properties.from(PADAUK_PLANKS.get())));
	public static final RegistryObject<DBMTrapDoorBlock> PADAUK_TRAPDOOR = BLOCKS.register("padauk_trapdoor", () -> new DBMTrapDoorBlock(Block.Properties.create(Material.WOOD, MaterialColor.RED).hardnessAndResistance(3.0F).sound(SoundType.WOOD).notSolid()));
}