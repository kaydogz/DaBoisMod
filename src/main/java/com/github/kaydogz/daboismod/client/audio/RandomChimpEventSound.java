package com.github.kaydogz.daboismod.client.audio;

import com.github.kaydogz.daboismod.util.DBMSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.util.LazyOptional;

public class RandomChimpEventSound extends TickableSound {

    public RandomChimpEventSound() {
        super(DBMSoundEvents.EVENT_RANDOM_CHIMP_LOOP.get(), SoundCategory.AMBIENT);
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 1.0F;
        this.global = true;
    }

    @Override
    public boolean canBeSilent() {
        return true;
    }

    @Override
    public void tick() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.world == null || minecraft.player == null || !minecraft.player.isAlive() || !minecraft.world.chunkExists(minecraft.player.chunkCoordX, minecraft.player.chunkCoordZ)) {
            this.finishPlaying();
        }
    }
}
