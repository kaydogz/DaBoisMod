package com.github.kaydogz.daboismod.capability;

import com.github.kaydogz.daboismod.capability.base.IVillagerCapability;
import com.github.kaydogz.daboismod.util.DBMConstants;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class VillagerCapability implements IVillagerCapability {

    private int skinTone = DBMConstants.SkinTone.DEFAULT;
    private boolean spawned = false;

    @Override
    public int getSkinTone() {
        return this.skinTone;
    }

    @Override
    public void setSkinTone(int skinTone) {
        this.skinTone = skinTone;
    }

    @Override
    public boolean hasSpawned() {
        return this.spawned;
    }

    @Override
    public void setSpawned(boolean spawned) {
        this.spawned = spawned;
    }

    public static class Storage implements Capability.IStorage<IVillagerCapability> {

        @Nullable
        @Override
        public INBT writeNBT(Capability<IVillagerCapability> capability, IVillagerCapability instance, Direction side) {
            CompoundNBT tag = new CompoundNBT();
            tag.putInt("SkinTone", instance.getSkinTone());
            tag.putBoolean("Spawned", instance.hasSpawned());
            return tag;
        }

        @Override
        public void readNBT(Capability<IVillagerCapability> capability, IVillagerCapability instance, Direction side, INBT nbt) {
            if (nbt instanceof CompoundNBT) {
                CompoundNBT tag = (CompoundNBT) nbt;
                if (tag.contains("SkinTone", Constants.NBT.TAG_INT)) instance.setSkinTone(tag.getInt("SkinTone"));
                if (tag.contains("Spawned", Constants.NBT.TAG_BYTE)) instance.setSpawned(tag.getBoolean("Spawned"));
            }
        }
    }
}
