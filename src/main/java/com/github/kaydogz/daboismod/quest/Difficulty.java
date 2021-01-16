package com.github.kaydogz.daboismod.quest;

import com.github.kaydogz.daboismod.DaBoisMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public enum Difficulty {
    VERY_EASY(DaBoisMod.modLocation("very_easy"), TextFormatting.GREEN),
    EASY(DaBoisMod.modLocation("easy"), TextFormatting.DARK_GREEN),
    MEDIUM(DaBoisMod.modLocation("medium"), TextFormatting.YELLOW),
    HARD(DaBoisMod.modLocation("hard"), TextFormatting.RED),
    VERY_HARD(DaBoisMod.modLocation("very_hard"), TextFormatting.DARK_RED);

    private final ITextComponent textComponent;
    private final ResourceLocation location;
    private final ResourceLocation lootTableLocation;

    Difficulty(ResourceLocation locationIn, TextFormatting colorIn) {
        this.location = locationIn;
        this.lootTableLocation = new ResourceLocation(this.location.getNamespace(), "quests/" + this.location.getPath());
        this.textComponent = new TranslationTextComponent("gui." + locationIn.getNamespace() + ".quest.difficulty." + locationIn.getPath()).mergeStyle(colorIn).mergeStyle(TextFormatting.BOLD);
    }

    public ITextComponent getTextComponent() {
        return this.textComponent;
    }

    public ResourceLocation getResourceLocation() {
        return this.location;
    }

    public ResourceLocation getLootTableResourceLocation() {
        return this.lootTableLocation;
    }

    public static Difficulty fromString(String str) {
        return valueOf(Difficulty.class, str.toUpperCase());
    }

    /**
     * Gets the difficulty of a quest by dividing the maximum quota into a part for every difficulty. The difficulty is the difficulty of the divided part which the count falls into.
     * @param quota the quest's quota.
     * @param task the quest's {@link QuestTask}.
     * @return the difficulty.
     */
    public static Difficulty getAppropriateDifficulty(int quota, QuestTask task) {
        int maxQuota = task.getMaxQuota();
        int minQuota = task.getMinQuota();
        if (quota >= maxQuota) {
            return VERY_HARD;
        } else if (quota <= minQuota) {
            return VERY_EASY;
        }
        return values()[Math.round((float) (values().length - 1) * (quota - minQuota) / (maxQuota - minQuota))];
    }
}
