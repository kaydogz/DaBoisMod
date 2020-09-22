package com.github.kaydogz.daboismod.quest;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.provider.PlayerCapability;
import com.github.kaydogz.daboismod.enchantment.DBMEnchantments;
import com.github.kaydogz.daboismod.item.DBMItems;
import com.github.kaydogz.daboismod.network.DBMPacketHandler;
import com.github.kaydogz.daboismod.network.server.SUpdateQuestsPacket;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class QuestHelper {

    /**
     * Assigns a quest to a player and syncs to tracking clients.
     * @param questIn the quest to assign.
     * @param playerIn the player to assign it to.
     */
    public static void assignQuest(Quest questIn, ServerPlayerEntity playerIn) {
        playerIn.sendStatusMessage(new StringTextComponent(TextFormatting.GOLD + new TranslationTextComponent("gui.daboismod.quest.new").getFormattedText()), true);
        ArrayList<Quest> quests = DaBoisMod.get(PlayerCapability.getCapabilityOf(playerIn)).getQuests();
        quests.add(questIn);
        DBMPacketHandler.sendToAllTrackingEntityAndSelf(new SUpdateQuestsPacket(quests, playerIn.getEntityId()), playerIn);
    }

    /**
     * Clears the quests of a player and syncs to tracking clients.
     * @param playerIn the player to clear the quests of.
     * @return the number of quests cleared.
     */
    public static int clearQuests(ServerPlayerEntity playerIn) {
        ArrayList<Quest> quests = DaBoisMod.get(PlayerCapability.getCapabilityOf(playerIn)).getQuests();
        int cleared = quests.size();
        quests.clear();
        DBMPacketHandler.sendToAllTrackingEntityAndSelf(new SUpdateQuestsPacket(quests, playerIn.getEntityId()), playerIn);
        return cleared;
    }

    /**
     * Gets the difficulty of a quest by dividing the maximum quota into a part for every difficulty. The difficulty is the difficulty of the divided part which the count falls into.
     * @param questIn the quest to get the difficulty of.
     * @return the appropriate difficulty.
     */
    public static Quest.Difficulty getAppropriateDifficulty(Quest questIn) {
        int quota = questIn.getQuota();
        int minQuota = questIn.getQuestTask().getMinQuota();
        int maxQuota = questIn.getQuestTask().getMaxQuota();
        if (quota >= maxQuota) {
            return Quest.Difficulty.VERY_HARD;
        } else if (quota <= minQuota) {
            return Quest.Difficulty.VERY_EASY;
        }
        return Quest.Difficulty.values()[Math.round((float) (Quest.Difficulty.values().length - 1) * (quota - minQuota) / (maxQuota - minQuota))];
    }

    protected static QuestTask getRandomTask(Random rand) {
        Collection<QuestTask> registeredTasks = QuestTasks.QUEST_TASKS_REGISTRY.get().getValues();
        return (QuestTask) registeredTasks.toArray()[rand.nextInt(registeredTasks.size())];
    }

    protected static ItemStack getRandomReward(Quest quest) {
        switch (quest.difficulty) {
            default: return ItemStack.EMPTY;

            case VERY_EASY: {
                int picker = quest.rand.nextInt(100);
                if (picker >= 20) return getCommonReward(quest); 			// 80% Common
                else if (picker >= 5) return getUncommonReward(quest); 		// 15% Uncommon
                else return getRareReward(quest);							// 5% Rare
            }

            case EASY: {
                int picker = quest.rand.nextInt(100);
                if (picker >= 70) return getCommonReward(quest);			// 30% Common
                else if (picker >= 10) return getUncommonReward(quest);		// 60% Uncommon
                else return getRareReward(quest);							// 10% Rare
            }

            case MEDIUM: {
                int picker = quest.rand.nextInt(100);
                if (picker >= 95) return getCommonReward(quest);			// 5% Common
                else if (picker >= 50) return getUncommonReward(quest);		// 45% Uncommon
                else if (picker >= 5) return getRareReward(quest);			// 45% Rare
                else return getEpicReward(quest);							// 5% Epic
            }

            case HARD: {
                int picker = quest.rand.nextInt(100);
                if (picker >= 70) return getEpicReward(quest);				// 30% Epic
                else if (picker >= 10) return getRareReward(quest);			// 60% Rare
                else return getUncommonReward(quest);						// 10% Uncommon

            }

            case VERY_HARD: {
                int picker = quest.rand.nextInt(100);
                if (picker >= 20) return getEpicReward(quest); 				// 80% Epic
                else if (picker >= 5) return getRareReward(quest); 			// 15% Rare
                else return getUncommonReward(quest);						// 5% Uncommon
            }
        }
    }

    protected static ItemStack getCommonReward(Quest quest) {
        switch (quest.rand.nextInt(50)) {
            default: return ItemStack.EMPTY;

            case 0: return new ItemStack(Items.DIRT, quest.rand.nextInt(31) + 25);
            case 1: return new ItemStack(Items.WHEAT_SEEDS, quest.rand.nextInt(16) + 15);
            case 2: return new ItemStack(Items.COBBLESTONE, quest.rand.nextInt(31) + 20);
            case 3: return new ItemStack(Items.OAK_PLANKS, quest.rand.nextInt(26) + 10);
            case 4: return new ItemStack(Items.BREAD, quest.rand.nextInt(11) + 5);
            case 5: return new ItemStack(Items.QUARTZ, quest.rand.nextInt(21) + 10);
            case 6: return new ItemStack(Items.COOKIE, quest.rand.nextInt(11) + 10);
            case 7: return new ItemStack(Items.SAND, quest.rand.nextInt(21) + 15);
            case 8: return new ItemStack(Items.SNOWBALL, quest.rand.nextInt(11) + 5);
            case 9: return new ItemStack(Items.MAGMA_BLOCK, quest.rand.nextInt(11) + 10);
            case 10: return new ItemStack(Items.CARROT, quest.rand.nextInt(11) + 10);
            case 11: return new ItemStack(Items.PRISMARINE, quest.rand.nextInt(21) + 15);
            case 12: return new ItemStack(Items.END_STONE, quest.rand.nextInt(21) + 15);
            case 13: return new ItemStack(Items.BAKED_POTATO, quest.rand.nextInt(21) + 10);
            case 14: return new ItemStack(Items.CLAY_BALL, quest.rand.nextInt(21) + 10);
            case 15: return new ItemStack(Items.RAIL, quest.rand.nextInt(11) + 5);
            case 16: return new ItemStack(Items.NETHERRACK, quest.rand.nextInt(21) + 10);
            case 17: return new ItemStack(Items.ICE, quest.rand.nextInt(11) + 10);
            case 18: return new ItemStack(Items.MELON_SLICE, quest.rand.nextInt(11) + 10);
            case 19: return new ItemStack(Items.BOOK, quest.rand.nextInt(11) + 5);
            case 20: return new ItemStack(Items.WHEAT, quest.rand.nextInt(21) + 10);
            case 21: return new ItemStack(Items.SPIDER_EYE, quest.rand.nextInt(21) + 10);
            case 22: return new ItemStack(Items.ROTTEN_FLESH, quest.rand.nextInt(31) + 10);
            case 23: return new ItemStack(Items.BONE, quest.rand.nextInt(16) + 10);
            case 24: return new ItemStack(Items.BONE_MEAL, quest.rand.nextInt(11) + 5);
            case 25: return new ItemStack(Items.PAPER, quest.rand.nextInt(21) + 10);
            case 26: return new ItemStack(Items.APPLE, quest.rand.nextInt(16) + 10);
            case 27: return new ItemStack(Items.GOLD_NUGGET, quest.rand.nextInt(31) + 10);
            case 28: return new ItemStack(Items.MELON_SEEDS, quest.rand.nextInt(5) + 1);
            case 29: return new ItemStack(Items.COBWEB, quest.rand.nextInt(16) + 10);
            case 30: return new ItemStack(Items.BOWL, quest.rand.nextInt(6) + 5);
            case 31: return new ItemStack(Items.FEATHER, quest.rand.nextInt(16) + 5);
            case 32: return new ItemStack(Items.ACACIA_PLANKS, quest.rand.nextInt(21) + 10);
            case 33: return new ItemStack(Items.BIRCH_PLANKS, quest.rand.nextInt(21) + 10);
            case 34: return new ItemStack(Items.DARK_OAK_PLANKS, quest.rand.nextInt(21) + 10);
            case 35: return new ItemStack(Items.JUNGLE_PLANKS, quest.rand.nextInt(21) + 10);
            case 36: return new ItemStack(Items.SPRUCE_PLANKS, quest.rand.nextInt(21) + 10);
            case 37: return new ItemStack(Items.IRON_NUGGET, quest.rand.nextInt(31) + 10);
            case 38: return new ItemStack(Items.PODZOL, quest.rand.nextInt(31) + 20);
            case 39: return new ItemStack(Items.PUMPKIN_SEEDS, quest.rand.nextInt(5) + 1);
            case 40: return new ItemStack(Items.SOUL_SAND, quest.rand.nextInt(16) + 10);
            case 41: return new ItemStack(Items.GLASS, quest.rand.nextInt(21) + 10);
            case 42: return new ItemStack(Items.GUNPOWDER, quest.rand.nextInt(11) + 5);
            case 43: return new ItemStack(Items.STICK, quest.rand.nextInt(26) + 15);
            case 44: return new ItemStack(Items.MYCELIUM, quest.rand.nextInt(16) + 10);
            case 45: return new ItemStack(Items.BAMBOO, quest.rand.nextInt(11) + 5);
            case 46: return new ItemStack(Items.FLINT, quest.rand.nextInt(16) + 10);
            case 47: return new ItemStack(Items.RABBIT_HIDE, quest.rand.nextInt(16) + 10);
            case 48: return new ItemStack(Items.TORCH, quest.rand.nextInt(21) + 10);
            case 49: return new ItemStack(Items.STRING, quest.rand.nextInt(21) + 10);
        }
    }

    protected static ItemStack getUncommonReward(Quest quest) {
        switch (quest.rand.nextInt(25)) {
            default: return ItemStack.EMPTY;

            case 0: return new ItemStack(Items.COAL, quest.rand.nextInt(26) + 5);
            case 1: return new ItemStack(Items.IRON_INGOT, quest.rand.nextInt(16) + 5);
            case 2: return new ItemStack(Items.GOLD_INGOT, quest.rand.nextInt(16) + 5);
            case 3: return new ItemStack(Items.ACACIA_LOG, quest.rand.nextInt(11) + 5);
            case 4: return new ItemStack(Items.BIRCH_LOG, quest.rand.nextInt(11) + 5);
            case 5: return new ItemStack(Items.DARK_OAK_LOG, quest.rand.nextInt(11) + 5);
            case 6: return new ItemStack(Items.JUNGLE_LOG, quest.rand.nextInt(11) + 5);
            case 7: return new ItemStack(Items.OAK_LOG, quest.rand.nextInt(11) + 5);
            case 8: return new ItemStack(Items.SPRUCE_LOG, quest.rand.nextInt(11) + 5);
            case 9: return new ItemStack(Items.BLAZE_POWDER, quest.rand.nextInt(6) + 5);
            case 10: return addRandomToolEnchantmentTo(new ItemStack(Items.IRON_PICKAXE, 1), quest);
            case 11: return addRandomToolEnchantmentTo(new ItemStack(Items.IRON_AXE, 1), quest);
            case 12: return addRandomToolEnchantmentTo(new ItemStack(Items.IRON_HOE, 1), quest);
            case 13: return addRandomToolEnchantmentTo(new ItemStack(Items.IRON_SHOVEL, 1), quest);
            case 14: return addRandomSwordEnchantmentTo(new ItemStack(Items.IRON_SWORD, 1), quest);
            case 15: return new ItemStack(Items.COOKED_PORKCHOP, quest.rand.nextInt(16) + 5);
            case 16: return new ItemStack(Items.COOKED_BEEF, quest.rand.nextInt(16) + 5);
            case 17: return new ItemStack(Items.COOKED_CHICKEN, quest.rand.nextInt(16) + 5);
            case 18: return new ItemStack(Items.GLOWSTONE, quest.rand.nextInt(11) + 10);
            case 19: return new ItemStack(Items.PACKED_ICE, quest.rand.nextInt(11) + 5);
            case 20: return new ItemStack(Items.OBSIDIAN, quest.rand.nextInt(11) + 5);
            case 21: return new ItemStack(Items.SUGAR_CANE, quest.rand.nextInt(21) + 15);
            case 22: return new ItemStack(Items.TNT, quest.rand.nextInt(11) + 5);
            case 23: return new ItemStack(Items.SLIME_BALL, quest.rand.nextInt(11) + 5);
            case 24: return new ItemStack(Items.HOPPER, quest.rand.nextInt(6) + 5);
        }
    }

    protected static ItemStack getRareReward(Quest quest) {
        switch (quest.rand.nextInt(20)) {
            default: return ItemStack.EMPTY;

            case 0: return new ItemStack(Items.IRON_BLOCK, quest.rand.nextInt(5) + 1);
            case 1: return new ItemStack(Items.SPONGE, quest.rand.nextInt(5) + 1);
            case 2: return new ItemStack(Items.GOLD_BLOCK, quest.rand.nextInt(5) + 1);
            case 3: return new ItemStack(Items.DIAMOND, quest.rand.nextInt(11) + 5);
            case 4: return new ItemStack(DBMItems.MARIJUANA.get(), quest.rand.nextInt(6) + 5);
            case 5: return new ItemStack(Items.EMERALD, quest.rand.nextInt(6) + 5);
            case 6: return new ItemStack(Items.WHITE_BANNER, quest.rand.nextInt(6) + 5);
            case 7: return new ItemStack(Items.GOLDEN_APPLE, quest.rand.nextInt(11) + 5);
            case 8: return new ItemStack(Items.ENDER_CHEST, quest.rand.nextInt(5) + 1);
            case 9: return new ItemStack(Items.SLIME_BLOCK, quest.rand.nextInt(5) + 1);
            case 10: return new ItemStack(Items.REDSTONE_BLOCK, quest.rand.nextInt(6) + 5);
            case 11: return addRandomToolEnchantmentTo(new ItemStack(Items.GOLDEN_PICKAXE, 1), quest);
            case 12: return addRandomToolEnchantmentTo(new ItemStack(Items.GOLDEN_AXE, 1), quest);
            case 13: return addRandomToolEnchantmentTo(new ItemStack(Items.GOLDEN_SHOVEL, 1), quest);
            case 14: return addRandomToolEnchantmentTo(new ItemStack(Items.GOLDEN_HOE, 1), quest);
            case 15: return addRandomSwordEnchantmentTo(new ItemStack(Items.GOLDEN_SWORD, 1), quest);
            case 16: return new ItemStack(Items.ENDER_PEARL, quest.rand.nextInt(6) + 5);
            case 17: return new ItemStack(Items.LAPIS_LAZULI, quest.rand.nextInt(21) + 10);
            case 18: return new ItemStack(Items.FIREWORK_ROCKET, quest.rand.nextInt(26) + 5);
            case 19: return new ItemStack(Items.ARROW, quest.rand.nextInt(26) + 10);
        }
    }

    protected static ItemStack getEpicReward(Quest quest) {
        switch (quest.rand.nextInt(10)) {
            default: return ItemStack.EMPTY;

            case 0: return addRandomToolEnchantmentTo(new ItemStack(Items.DIAMOND_PICKAXE, 1), quest);
            case 1: return addRandomToolEnchantmentTo(new ItemStack(Items.DIAMOND_AXE, 1), quest);
            case 2: return addRandomToolEnchantmentTo(new ItemStack(Items.DIAMOND_SHOVEL, 1), quest);
            case 3: return addRandomToolEnchantmentTo(new ItemStack(Items.DIAMOND_HOE, 1), quest);
            case 4: return addRandomSwordEnchantmentTo(new ItemStack(Items.DIAMOND_SWORD, 1), quest);
            case 5: return new ItemStack(DBMItems.ANCIENT_FRUIT.get(), quest.rand.nextInt(6) + 5);
            case 6: return new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, quest.rand.nextInt(5) + 1);
            case 7: return new ItemStack(Items.DRAGON_BREATH, quest.rand.nextInt(6) + 5);
            case 8: return createMagnetismBook();
            case 9: return new ItemStack(Items.SHULKER_SHELL, quest.rand.nextInt(6) + 5);
        }
    }

    private static ItemStack addRandomToolEnchantmentTo(ItemStack stack, Quest quest) {
        switch (quest.rand.nextInt(5)) {
            default: break;

            case 1: {
                stack.addEnchantment(Enchantments.UNBREAKING, quest.rand.nextInt(3) + 1);
                break;
            }
            case 2: {
                stack.addEnchantment(Enchantments.EFFICIENCY, quest.rand.nextInt(5) + 1);
                break;
            }
            case 3: {
                stack.addEnchantment(Enchantments.MENDING, 1);
                break;
            }
            case 4: {
                stack.addEnchantment(Enchantments.FORTUNE, 1);
                break;
            }
        }
        return stack;
    }

    private static ItemStack addRandomSwordEnchantmentTo(ItemStack stack, Quest quest) {
        switch (quest.rand.nextInt(8)) {
            default: break;

            case 1: {
                stack.addEnchantment(Enchantments.UNBREAKING, quest.rand.nextInt(3) + 1);
                break;
            }
            case 2: {
                stack.addEnchantment(Enchantments.SHARPNESS, quest.rand.nextInt(5) + 1);
                break;
            }
            case 3: {
                stack.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, quest.rand.nextInt(5) + 1);
                break;
            }
            case 4: {
                stack.addEnchantment(Enchantments.SMITE, quest.rand.nextInt(5) + 1);
                break;
            }
            case 5: {
                stack.addEnchantment(Enchantments.FIRE_ASPECT, quest.rand.nextInt(2) + 1);
                break;
            }
            case 6: {
                stack.addEnchantment(Enchantments.MENDING, 1);
                break;
            }
            case 7: {
                stack.addEnchantment(Enchantments.LOOTING, quest.rand.nextInt(3) + 1);
                break;
            }
        }
        return stack;
    }

    private static ItemStack createMagnetismBook() {
        ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
        stack.addEnchantment(DBMEnchantments.MAGNETISM.get(), 1);
        return stack;
    }
}
