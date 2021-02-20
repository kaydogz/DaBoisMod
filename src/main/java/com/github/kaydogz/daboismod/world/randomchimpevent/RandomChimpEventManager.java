package com.github.kaydogz.daboismod.world.randomchimpevent;

import com.github.kaydogz.daboismod.entity.AggressiveChimpEntity;
import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Map;

public class RandomChimpEventManager extends WorldSavedData {
    private final Map<Integer, RandomChimpEvent> byId = Maps.newHashMap();
    private final ServerWorld world;
    private int nextAvailableId;
    private int tick;

    public RandomChimpEventManager(ServerWorld p_i50142_1_) {
        super(func_234620_a_(p_i50142_1_.getDimensionType()));
        this.world = p_i50142_1_;
        this.nextAvailableId = 1;
        this.markDirty();
    }

    public static RandomChimpEventManager getByWorld(ServerWorld world) {
        return world.getSavedData().getOrCreate(() -> new RandomChimpEventManager(world), RandomChimpEventManager.func_234620_a_(world.getDimensionType()));
    }

    public RandomChimpEvent get(int id) {
        return this.byId.get(id);
    }

    public void tick() {
        ++this.tick;
        Iterator<RandomChimpEvent> iterator = this.byId.values().iterator();

        while(iterator.hasNext()) {
            RandomChimpEvent chimpEvent = iterator.next();

            if (chimpEvent.isStopped()) {
                iterator.remove();
                this.markDirty();
            } else {
                chimpEvent.tick();
            }
        }

        if (this.tick % 200 == 0) {
            this.markDirty();
        }
    }

    public static boolean canJoinRandomChimpEvent(AggressiveChimpEntity chimp, RandomChimpEvent chimpEvent) {
        if (chimp != null && chimpEvent != null && chimpEvent.getWorld() != null) {
            return chimp.isAlive() && chimp.canJoinRandomChimpEvent() && chimp.getIdleTime() <= 2400 && chimp.world.getDimensionType() == chimpEvent.getWorld().getDimensionType();
        } else {
            return false;
        }
    }

    private RandomChimpEvent findOrCreateRandomChimpEvent(ServerWorld worldIn, BlockPos posIn, int distance) {
        RandomChimpEvent chimpEvent = getByWorld(worldIn).findRandomChimpEvent(posIn, distance);
        return chimpEvent != null ? chimpEvent : new RandomChimpEvent(this.incrementNextId(), worldIn, posIn);
    }

    @Nullable
    public RandomChimpEvent findOrCreateRandomChimpEvent(BlockPos posIn) {
        RandomChimpEvent chimpEvent = this.findOrCreateRandomChimpEvent(this.world, posIn, 9216);
        if (!chimpEvent.isStarted()) {
            if (!this.byId.containsKey(chimpEvent.getId())) {
                this.byId.put(chimpEvent.getId(), chimpEvent);
            }
        }

        this.markDirty();
        return chimpEvent;
    }

    /**
     * reads in data from the NBTTagCompound into this MapDataBase
     */
    public void read(CompoundNBT nbt) {
        this.nextAvailableId = nbt.getInt("NextAvailableID");
        this.tick = nbt.getInt("Tick");
        ListNBT listnbt = nbt.getList("RandomChimpEvents", 10);

        for(int i = 0; i < listnbt.size(); ++i) {
            CompoundNBT compoundnbt = listnbt.getCompound(i);
            RandomChimpEvent chimpEvent = new RandomChimpEvent(this.world, compoundnbt);
            this.byId.put(chimpEvent.getId(), chimpEvent);
        }

    }

    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("NextAvailableID", this.nextAvailableId);
        compound.putInt("Tick", this.tick);
        ListNBT listnbt = new ListNBT();

        for(RandomChimpEvent chimpEvent : this.byId.values()) {
            CompoundNBT compoundnbt = new CompoundNBT();
            chimpEvent.write(compoundnbt);
            listnbt.add(compoundnbt);
        }

        compound.put("RandomChimpEvents", listnbt);
        return compound;
    }

    public static String func_234620_a_(DimensionType p_234620_0_) {
        return "random_chimp_events" + p_234620_0_.getSuffix();
    }

    private int incrementNextId() {
        return ++this.nextAvailableId;
    }

    @Nullable
    public RandomChimpEvent findRandomChimpEvent(BlockPos p_215174_1_, int distance) {
        RandomChimpEvent chimpEvent = null;
        double d0 = (double)distance;

        for(RandomChimpEvent chimpEvent1 : this.byId.values()) {
            double d1 = chimpEvent1.getCenter().distanceSq(p_215174_1_);
            if (chimpEvent1.isActive() && d1 < d0) {
                chimpEvent = chimpEvent1;
                d0 = d1;
            }
        }

        return chimpEvent;
    }
}
