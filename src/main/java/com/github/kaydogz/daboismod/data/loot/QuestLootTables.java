package com.github.kaydogz.daboismod.data.loot;

import com.github.kaydogz.daboismod.item.DBMItems;
import com.github.kaydogz.daboismod.quest.Difficulty;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.functions.EnchantWithLevels;
import net.minecraft.world.storage.loot.functions.SetCount;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class QuestLootTables  implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(Difficulty.VERY_EASY.getLootTableResourceLocation(), LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(Items.DIRT).acceptFunction(SetCount.builder(RandomValueRange.of(20, 50))))
                .addEntry(ItemLootEntry.builder(Items.WHEAT_SEEDS).acceptFunction(SetCount.builder(RandomValueRange.of(15, 30))))
                .addEntry(ItemLootEntry.builder(Items.COBBLESTONE).acceptFunction(SetCount.builder(RandomValueRange.of(30, 50))))
                .addEntry(ItemLootEntry.builder(Items.OAK_PLANKS).acceptFunction(SetCount.builder(RandomValueRange.of(20, 30))))
                .addEntry(ItemLootEntry.builder(Items.BREAD).acceptFunction(SetCount.builder(RandomValueRange.of(15, 20))))
                .addEntry(ItemLootEntry.builder(Items.QUARTZ).acceptFunction(SetCount.builder(RandomValueRange.of(20, 30))))
                .addEntry(ItemLootEntry.builder(Items.COOKIE).acceptFunction(SetCount.builder(RandomValueRange.of(10, 20))))
                .addEntry(ItemLootEntry.builder(Items.SAND).acceptFunction(SetCount.builder(RandomValueRange.of(20, 35))))
                .addEntry(ItemLootEntry.builder(Items.SNOWBALL).acceptFunction(SetCount.builder(RandomValueRange.of(10, 15))))
                .addEntry(ItemLootEntry.builder(Items.MAGMA_BLOCK).acceptFunction(SetCount.builder(RandomValueRange.of(10, 20))))
                .addEntry(ItemLootEntry.builder(Items.CARROT).acceptFunction(SetCount.builder(RandomValueRange.of(10, 20))))
                .addEntry(ItemLootEntry.builder(Items.ROTTEN_FLESH).acceptFunction(SetCount.builder(RandomValueRange.of(30, 40))))
                .addEntry(ItemLootEntry.builder(Items.BONE).acceptFunction(SetCount.builder(RandomValueRange.of(10, 15))))
                .addEntry(ItemLootEntry.builder(Items.PAPER).acceptFunction(SetCount.builder(RandomValueRange.of(20, 30))))
                .addEntry(ItemLootEntry.builder(Items.APPLE).acceptFunction(SetCount.builder(RandomValueRange.of(15, 25))))
                .addEntry(ItemLootEntry.builder(Items.GOLD_NUGGET).acceptFunction(SetCount.builder(RandomValueRange.of(20, 30))))
                .addEntry(ItemLootEntry.builder(Items.MELON_SEEDS).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
                .addEntry(ItemLootEntry.builder(Items.COBWEB).acceptFunction(SetCount.builder(RandomValueRange.of(15, 25))))
                .addEntry(ItemLootEntry.builder(Items.BOWL).acceptFunction(SetCount.builder(RandomValueRange.of(5, 10))))
                .addEntry(ItemLootEntry.builder(Items.FEATHER).acceptFunction(SetCount.builder(RandomValueRange.of(10, 20))))
                .addEntry(ItemLootEntry.builder(Items.ACACIA_PLANKS).acceptFunction(SetCount.builder(RandomValueRange.of(20, 30))))
                .addEntry(ItemLootEntry.builder(Items.BIRCH_PLANKS).acceptFunction(SetCount.builder(RandomValueRange.of(20, 30))))
                .addEntry(ItemLootEntry.builder(Items.DARK_OAK_PLANKS).acceptFunction(SetCount.builder(RandomValueRange.of(20, 30))))
                .addEntry(ItemLootEntry.builder(Items.JUNGLE_PLANKS).acceptFunction(SetCount.builder(RandomValueRange.of(20, 30))))
                .addEntry(ItemLootEntry.builder(Items.OAK_PLANKS).acceptFunction(SetCount.builder(RandomValueRange.of(20, 30))))
                .addEntry(ItemLootEntry.builder(Items.SPRUCE_PLANKS).acceptFunction(SetCount.builder(RandomValueRange.of(20, 30))))
                .addEntry(ItemLootEntry.builder(DBMItems.PADAUK_PLANKS.get()).acceptFunction(SetCount.builder(RandomValueRange.of(20, 30))))
                .addEntry(ItemLootEntry.builder(Items.STICK).acceptFunction(SetCount.builder(RandomValueRange.of(15, 30))))
                .addEntry(ItemLootEntry.builder(Items.MYCELIUM).acceptFunction(SetCount.builder(RandomValueRange.of(10, 25))))
                .addEntry(ItemLootEntry.builder(Items.BAMBOO).acceptFunction(SetCount.builder(RandomValueRange.of(5, 15))))
                .addEntry(ItemLootEntry.builder(Items.FLINT).acceptFunction(SetCount.builder(RandomValueRange.of(10, 20))))
                .addEntry(ItemLootEntry.builder(Items.RABBIT_HIDE).acceptFunction(SetCount.builder(RandomValueRange.of(10, 25))))
                .addEntry(ItemLootEntry.builder(Items.TORCH).acceptFunction(SetCount.builder(RandomValueRange.of(10, 30))))
                .addEntry(ItemLootEntry.builder(Items.STRING).acceptFunction(SetCount.builder(RandomValueRange.of(10, 30))))
        ));
        consumer.accept(Difficulty.EASY.getLootTableResourceLocation(), LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(Items.LEATHER_HELMET).weight(4).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(20))))
                .addEntry(ItemLootEntry.builder(Items.LEATHER_CHESTPLATE).weight(4).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(20))))
                .addEntry(ItemLootEntry.builder(Items.LEATHER_LEGGINGS).weight(4).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(20))))
                .addEntry(ItemLootEntry.builder(Items.LEATHER_BOOTS).weight(4).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(20))))
                .addEntry(ItemLootEntry.builder(Items.COAL).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 25))))
                .addEntry(ItemLootEntry.builder(Items.COAL).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 25))))
                .addEntry(ItemLootEntry.builder(Items.COAL).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 25))))
                .addEntry(ItemLootEntry.builder(Items.PRISMARINE).acceptFunction(SetCount.builder(RandomValueRange.of(20, 35))))
                .addEntry(ItemLootEntry.builder(Items.END_STONE).acceptFunction(SetCount.builder(RandomValueRange.of(20, 35))))
                .addEntry(ItemLootEntry.builder(Items.BAKED_POTATO).acceptFunction(SetCount.builder(RandomValueRange.of(10, 20))))
                .addEntry(ItemLootEntry.builder(Items.CLAY_BALL).acceptFunction(SetCount.builder(RandomValueRange.of(20, 30))))
                .addEntry(ItemLootEntry.builder(Items.RAIL).acceptFunction(SetCount.builder(RandomValueRange.of(10, 15))))
                .addEntry(ItemLootEntry.builder(Items.NETHERRACK).acceptFunction(SetCount.builder(RandomValueRange.of(20, 30))))
                .addEntry(ItemLootEntry.builder(Items.ICE).acceptFunction(SetCount.builder(RandomValueRange.of(10, 20))))
                .addEntry(ItemLootEntry.builder(Items.MELON_SLICE).acceptFunction(SetCount.builder(RandomValueRange.of(10, 20))))
                .addEntry(ItemLootEntry.builder(Items.BOOK).acceptFunction(SetCount.builder(RandomValueRange.of(5, 15))))
                .addEntry(ItemLootEntry.builder(Items.WHEAT).acceptFunction(SetCount.builder(RandomValueRange.of(20, 30))))
                .addEntry(ItemLootEntry.builder(Items.SPIDER_EYE).acceptFunction(SetCount.builder(RandomValueRange.of(20, 30))))
                .addEntry(ItemLootEntry.builder(Items.IRON_NUGGET).acceptFunction(SetCount.builder(RandomValueRange.of(30, 40))))
                .addEntry(ItemLootEntry.builder(Items.PODZOL).acceptFunction(SetCount.builder(RandomValueRange.of(20, 50))))
                .addEntry(ItemLootEntry.builder(Items.PUMPKIN_SEEDS).acceptFunction(SetCount.builder(RandomValueRange.of(5, 10))))
                .addEntry(ItemLootEntry.builder(Items.SOUL_SAND).acceptFunction(SetCount.builder(RandomValueRange.of(10, 25))))
                .addEntry(ItemLootEntry.builder(Items.GLASS).acceptFunction(SetCount.builder(RandomValueRange.of(10, 30))))
                .addEntry(ItemLootEntry.builder(Items.GUNPOWDER).acceptFunction(SetCount.builder(RandomValueRange.of(5, 15))))
        ));
        consumer.accept(Difficulty.MEDIUM.getLootTableResourceLocation(), LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(Items.COAL).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 25))))
                .addEntry(ItemLootEntry.builder(Items.IRON_INGOT).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 20))))
                .addEntry(ItemLootEntry.builder(Items.GOLD_INGOT).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 20))))
                .addEntry(ItemLootEntry.builder(Items.ACACIA_LOG).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 15))))
                .addEntry(ItemLootEntry.builder(Items.BIRCH_LOG).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 15))))
                .addEntry(ItemLootEntry.builder(Items.DARK_OAK_LOG).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 15))))
                .addEntry(ItemLootEntry.builder(Items.JUNGLE_LOG).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 15))))
                .addEntry(ItemLootEntry.builder(Items.OAK_LOG).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 15))))
                .addEntry(ItemLootEntry.builder(Items.SPRUCE_LOG).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 15))))
                .addEntry(ItemLootEntry.builder(DBMItems.PADAUK_LOG.get()).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 15))))
                .addEntry(ItemLootEntry.builder(Items.SPRUCE_LOG).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 15))))
                .addEntry(ItemLootEntry.builder(Items.MAGMA_CREAM).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 10))))
                .addEntry(ItemLootEntry.builder(Items.GOLDEN_SWORD).weight(4).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(20))))
                .addEntry(ItemLootEntry.builder(Items.GOLDEN_AXE).weight(4).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(20))))
                .addEntry(ItemLootEntry.builder(Items.GOLDEN_HOE).weight(4).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(20))))
                .addEntry(ItemLootEntry.builder(Items.GOLDEN_SHOVEL).weight(4).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(20))))
                .addEntry(ItemLootEntry.builder(Items.GOLDEN_PICKAXE).weight(4).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(20))))
                .addEntry(ItemLootEntry.builder(Items.COOKED_PORKCHOP).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 20))))
                .addEntry(ItemLootEntry.builder(Items.COOKED_BEEF).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 20))))
                .addEntry(ItemLootEntry.builder(Items.COOKED_CHICKEN).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 20))))
                .addEntry(ItemLootEntry.builder(Items.GLOWSTONE).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(10, 20))))
                .addEntry(ItemLootEntry.builder(Items.PACKED_ICE).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 15))))
                .addEntry(ItemLootEntry.builder(Items.OBSIDIAN).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(1, 10))))
                .addEntry(ItemLootEntry.builder(Items.SUGAR_CANE).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(15, 30))))
                .addEntry(ItemLootEntry.builder(Items.TNT).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 15))))
                .addEntry(ItemLootEntry.builder(Items.SLIME_BALL).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 20))))
                .addEntry(ItemLootEntry.builder(Items.HOPPER).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 10))))
        ));
        consumer.accept(Difficulty.HARD.getLootTableResourceLocation(), LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(Items.IRON_BLOCK).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
                .addEntry(ItemLootEntry.builder(Items.GOLD_BLOCK).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
                .addEntry(ItemLootEntry.builder(Items.SPONGE).weight(2).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
                .addEntry(ItemLootEntry.builder(Items.DIAMOND).weight(8).acceptFunction(SetCount.builder(RandomValueRange.of(10, 20))))
                .addEntry(ItemLootEntry.builder(DBMItems.MARIJUANA.get()).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(10, 20))))
                .addEntry(ItemLootEntry.builder(Items.EMERALD).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(15, 25))))
                .addEntry(ItemLootEntry.builder(Items.WHITE_BANNER).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 10))))
                .addEntry(ItemLootEntry.builder(Items.GOLDEN_APPLE).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
                .addEntry(ItemLootEntry.builder(Items.ENDER_CHEST).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
                .addEntry(ItemLootEntry.builder(Items.SLIME_BLOCK).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))))
                .addEntry(ItemLootEntry.builder(Items.REDSTONE_BLOCK).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 10))))
                .addEntry(ItemLootEntry.builder(Items.IRON_SWORD).weight(4).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(30))))
                .addEntry(ItemLootEntry.builder(Items.IRON_AXE).weight(4).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(30))))
                .addEntry(ItemLootEntry.builder(Items.IRON_HOE).weight(4).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(30))))
                .addEntry(ItemLootEntry.builder(Items.IRON_SHOVEL).weight(4).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(30))))
                .addEntry(ItemLootEntry.builder(Items.IRON_PICKAXE).weight(4).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(30))))
                .addEntry(ItemLootEntry.builder(Items.ENDER_PEARL).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(10, 15))))
                .addEntry(ItemLootEntry.builder(Items.LAPIS_LAZULI).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(15, 35))))
                .addEntry(ItemLootEntry.builder(Items.FIREWORK_ROCKET).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(30, 55))))
                .addEntry(ItemLootEntry.builder(Items.ARROW).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(15, 40))))
        ));
        consumer.accept(Difficulty.VERY_HARD.getLootTableResourceLocation(), LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(Items.DIAMOND_SWORD).weight(2).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(30))))
                .addEntry(ItemLootEntry.builder(Items.DIAMOND_AXE).weight(2).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(30))))
                .addEntry(ItemLootEntry.builder(Items.DIAMOND_HOE).weight(2).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(30))))
                .addEntry(ItemLootEntry.builder(Items.DIAMOND_SHOVEL).weight(2).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(30))))
                .addEntry(ItemLootEntry.builder(Items.DIAMOND_PICKAXE).weight(2).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(30))))
                .addEntry(ItemLootEntry.builder(DBMItems.ANCIENT_FRUIT.get()).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 10))))
                .addEntry(ItemLootEntry.builder(Items.ENCHANTED_GOLDEN_APPLE).weight(5).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))))
                .addEntry(ItemLootEntry.builder(Items.DRAGON_BREATH).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 10))))
                .addEntry(ItemLootEntry.builder(Items.SHULKER_SHELL).weight(10).acceptFunction(SetCount.builder(RandomValueRange.of(5, 10))))
                .addEntry(ItemLootEntry.builder(Items.BOOK).weight(10).acceptFunction(EnchantWithLevels.func_215895_a(ConstantRange.of(40)).func_216059_e()))
                .addEntry(ItemLootEntry.builder(Items.NETHER_STAR).weight(5).acceptFunction(SetCount.builder(ConstantRange.of(1))))
        ));
    }
}
