package com.github.kaydogz.daboismod.world.randomchimpevent;

import com.github.kaydogz.daboismod.entity.AggressiveChimpEntity;
import com.github.kaydogz.daboismod.entity.DBMEntities;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class RandomChimpEvent {

    private static final ITextComponent RANDOM_CHIMP_EVENT = new TranslationTextComponent("event.daboismod.random_chimp");
    private final Map<Integer, Set<AggressiveChimpEntity>> chimps = Maps.newHashMap();
    private long ticksActive;
    private BlockPos center;
    private final ServerWorld world;
    private boolean started;
    private final int id;
    private float totalHealth;
    private boolean active;
    private int groupsSpawned;
    private final ServerBossInfo bossInfo = new ServerBossInfo(RANDOM_CHIMP_EVENT, BossInfo.Color.RED, BossInfo.Overlay.NOTCHED_10);
    private int postRandomChimpEventTicks;
    private int preRandomChimpEventTicks;
    private final Random random = new Random();
    private RandomChimpEvent.Status status;
    private final int numGroups;
    private Optional<BlockPos> waveSpawnPos = Optional.empty();

    public RandomChimpEvent(int id, ServerWorld world, BlockPos center) {
        this.id = id;
        this.world = world;
        this.active = true;
        this.preRandomChimpEventTicks = 300;
        this.bossInfo.setPercent(0.0F);
        this.center = center;
        this.numGroups = this.getWaves(world.getDifficulty());
        this.status = RandomChimpEvent.Status.ONGOING;
    }

    public RandomChimpEvent(ServerWorld world, CompoundNBT nbt) {
        this.world = world;
        this.id = nbt.getInt("Id");
        this.started = nbt.getBoolean("Started");
        this.active = nbt.getBoolean("Active");
        this.ticksActive = nbt.getLong("TicksActive");
        this.groupsSpawned = nbt.getInt("GroupsSpawned");
        this.preRandomChimpEventTicks = nbt.getInt("PreRandomChimpTicks");
        this.postRandomChimpEventTicks = nbt.getInt("PostRandomChimpTicks");
        this.totalHealth = nbt.getFloat("TotalHealth");
        this.center = new BlockPos(nbt.getInt("CX"), nbt.getInt("CY"), nbt.getInt("CZ"));
        this.numGroups = nbt.getInt("NumGroups");
        this.status = RandomChimpEvent.Status.getByName(nbt.getString("Status"));
    }

    public boolean isBetweenWaves() {
        return this.func_221297_c() && this.getChimpCount() == 0 && this.preRandomChimpEventTicks > 0;
    }

    public boolean func_221297_c() {
        return this.groupsSpawned > 0;
    }

    public boolean isStopped() {
        return this.status == RandomChimpEvent.Status.STOPPED;
    }

    public World getWorld() {
        return this.world;
    }

    public boolean isStarted() {
        return this.started;
    }

    public int getGroupsSpawned() {
        return this.groupsSpawned;
    }

    public int getWaves(Difficulty difficultyIn) {
        switch(difficultyIn) {
            case EASY:
                return 3;
            case NORMAL:
                return 5;
            case HARD:
                return 7;
            default:
                return 0;
        }
    }

    private Predicate<ServerPlayerEntity> getParticipantsPredicate() {
        return (player) -> {
            BlockPos blockpos = player.getPosition();
            return player.isAlive() && RandomChimpEventManager.getByWorld(this.world).findRandomChimpEvent(blockpos, 9216) == this;
        };
    }

    private void updateBossInfoVisibility() {
        Set<ServerPlayerEntity> set = Sets.newHashSet(this.bossInfo.getPlayers());
        List<ServerPlayerEntity> list = this.world.getPlayers(this.getParticipantsPredicate());

        for(ServerPlayerEntity serverplayerentity : list) {
            if (!set.contains(serverplayerentity)) {
                this.bossInfo.addPlayer(serverplayerentity);
            }
        }

        for(ServerPlayerEntity serverplayerentity1 : set) {
            if (!list.contains(serverplayerentity1)) {
                this.bossInfo.removePlayer(serverplayerentity1);
            }
        }

    }

    public BlockPos getCenter() {
        return this.center;
    }

    private void setCenter(BlockPos center) {
        this.center = center;
    }

    public void stop() {
        this.active = false;
        this.bossInfo.removeAllPlayers();
        this.status = RandomChimpEvent.Status.STOPPED;
    }

    public void tick() {
        if (!this.isStopped()) {
            if (this.status == RandomChimpEvent.Status.ONGOING) {
                boolean flag = this.active;
                this.active = this.world.isBlockLoaded(this.center);
                if (this.world.getDifficulty() == Difficulty.PEACEFUL) {
                    this.stop();
                    return;
                }

                if (flag != this.active) {
                    this.bossInfo.setVisible(this.active);
                }

                if (!this.active) {
                    return;
                }

                ++this.ticksActive;
                if (this.ticksActive >= 48000L) {
                    this.stop();
                    return;
                }

                int i = this.getChimpCount();
                if (i == 0 && this.hasMoreWaves()) {
                    if (this.preRandomChimpEventTicks <= 0) {
                        if (this.preRandomChimpEventTicks == 0 && this.groupsSpawned > 0) {
                            this.preRandomChimpEventTicks = 300;
                            this.bossInfo.setName(RANDOM_CHIMP_EVENT);
                            return;
                        }
                    } else {
                        boolean flag1 = this.waveSpawnPos.isPresent();
                        boolean flag2 = !flag1 && this.preRandomChimpEventTicks % 5 == 0;
                        if (flag1 && !this.world.getChunkProvider().isChunkLoaded(new ChunkPos(this.waveSpawnPos.get()))) {
                            flag2 = true;
                        }

                        if (flag2) {
                            int j = 0;
                            if (this.preRandomChimpEventTicks < 100) {
                                j = 1;
                            } else if (this.preRandomChimpEventTicks < 40) {
                                j = 2;
                            }

                            this.waveSpawnPos = this.getValidSpawnPos(j);
                        }

                        if (this.preRandomChimpEventTicks == 300 || this.preRandomChimpEventTicks % 20 == 0) {
                            this.updateBossInfoVisibility();
                        }

                        --this.preRandomChimpEventTicks;
                        this.bossInfo.setPercent(MathHelper.clamp((float)(300 - this.preRandomChimpEventTicks) / 300.0F, 0.0F, 1.0F));
                    }
                }

                if (this.ticksActive % 20L == 0L) {
                    this.updateBossInfoVisibility();
                    this.updateChimps();
                    if (i > 0) {
                        if (i <= 2) {
                            this.bossInfo.setName(RANDOM_CHIMP_EVENT.deepCopy().appendString(" - ").append(new TranslationTextComponent("event.daboismod.random_chimp.chimps_remaining", i)));
                        } else {
                            this.bossInfo.setName(RANDOM_CHIMP_EVENT);
                        }
                    } else {
                        this.bossInfo.setName(RANDOM_CHIMP_EVENT);
                    }
                }

                boolean flag3 = false;
                int k = 0;

                while(this.shouldSpawnGroup()) {
                    BlockPos blockpos = this.waveSpawnPos.isPresent() ? this.waveSpawnPos.get() : this.findRandomSpawnPos(k, 20);
                    if (blockpos != null) {
                        this.started = true;
                        this.spawnNextWave(blockpos);
                        if (!flag3) {
                            flag3 = true;
                        }
                    } else {
                        ++k;
                    }

                    if (k > 3) {
                        this.stop();
                        break;
                    }
                }

                if (this.isStarted() && !this.hasMoreWaves() && i == 0) {
                    if (this.postRandomChimpEventTicks < 40) {
                        ++this.postRandomChimpEventTicks;
                    } else {
                        this.stop();

                        // send packets to stop sound
                    }
                }

                this.markDirty();
            }
        }
    }

    @Nullable
    private BlockPos findRandomSpawnPos(int p_221298_1_, int p_221298_2_) {
        int i = p_221298_1_ == 0 ? 2 : 2 - p_221298_1_;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for(int i1 = 0; i1 < p_221298_2_; ++i1) {
            float f = this.world.rand.nextFloat() * ((float)Math.PI * 2F);
            int j = this.center.getX() + MathHelper.floor(MathHelper.cos(f) * 32.0F * (float)i) + this.world.rand.nextInt(5);
            int l = this.center.getZ() + MathHelper.floor(MathHelper.sin(f) * 32.0F * (float)i) + this.world.rand.nextInt(5);
            int k = this.world.getHeight(Heightmap.Type.WORLD_SURFACE, j, l);
            blockpos$mutable.setPos(j, k, l);
            if (this.world.isAreaLoaded(blockpos$mutable.getX() - 10, blockpos$mutable.getY() - 10, blockpos$mutable.getZ() - 10, blockpos$mutable.getX() + 10, blockpos$mutable.getY() + 10, blockpos$mutable.getZ() + 10) && this.world.getChunkProvider().isChunkLoaded(new ChunkPos(blockpos$mutable)) && (WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, this.world, blockpos$mutable, DBMEntities.AGGRESSIVE_CHIMP.get()) || this.world.getBlockState(blockpos$mutable.down()).isIn(Blocks.SNOW) && this.world.getBlockState(blockpos$mutable).isAir())) {
                return blockpos$mutable;
            }
        }

        return null;
    }

    private Optional<BlockPos> getValidSpawnPos(int p_221313_1_) {
        for(int i = 0; i < 3; ++i) {
            BlockPos blockpos = this.findRandomSpawnPos(p_221313_1_, 1);
            if (blockpos != null) {
                return Optional.of(blockpos);
            }
        }

        return Optional.empty();
    }

    private boolean hasMoreWaves() {
        return !this.isFinalWave();
    }

    private boolean isFinalWave() {
        return this.getGroupsSpawned() == this.numGroups;
    }

    private void updateChimps() {
        Iterator<Set<AggressiveChimpEntity>> iterator = this.chimps.values().iterator();
        Set<AggressiveChimpEntity> set = Sets.newHashSet();

        while(iterator.hasNext()) {
            Set<AggressiveChimpEntity> set1 = iterator.next();

            for(AggressiveChimpEntity chimp : set1) {
                BlockPos blockpos = chimp.getPosition();
                if (!chimp.removed && chimp.world.getDimensionKey() == this.world.getDimensionKey() && !(this.center.distanceSq(blockpos) >= 12544.0D)) {
                    if (chimp.ticksExisted > 600) {
                        if (this.world.getEntityByUuid(chimp.getUniqueID()) == null) {
                            set.add(chimp);
                        }

                        if (chimp.getIdleTime() > 2400) {
                            chimp.setJoinDelay(chimp.getJoinDelay() + 1);
                        }

                        if (chimp.getJoinDelay() >= 30) {
                            set.add(chimp);
                        }
                    }
                } else {
                    set.add(chimp);
                }
            }
        }

        for(AggressiveChimpEntity chimp1 : set) {
            this.leaveRandomChimpEvent(chimp1, true);
        }

    }
    private void spawnNextWave(BlockPos p_221294_1_) {
        int i = this.groupsSpawned + 1;
        this.totalHealth = 0.0F;

        for(RandomChimpEvent.WaveMember member : RandomChimpEvent.WaveMember.VALUES) {
            int j = this.getDefaultNumSpawns(member, i, false);

            for(int l = 0; l < j; ++l) {

                this.joinRandomChimpEvent(i, member.type.get().create(this.world), p_221294_1_, false);
            }
        }

        this.waveSpawnPos = Optional.empty();
        ++this.groupsSpawned;
        this.updateBarPercentage();
        this.markDirty();
    }

    private boolean joinRandomChimpEvent(int wave, AggressiveChimpEntity chimp) {
        return this.joinRandomChimpEvent(wave, chimp, true);
    }

    public boolean joinRandomChimpEvent(int wave, AggressiveChimpEntity chimp, boolean p_221300_3_) {
        this.chimps.computeIfAbsent(wave, (p_221323_0_) -> Sets.newHashSet());
        Set<AggressiveChimpEntity> set = this.chimps.get(wave);
        AggressiveChimpEntity chimp1 = null;

        for(AggressiveChimpEntity chimp2 : set) {
            if (chimp2.getUniqueID().equals(chimp.getUniqueID())) {
                chimp1 = chimp2;
                break;
            }
        }

        if (chimp1 != null) {
            set.remove(chimp1);
            set.add(chimp);
        }

        set.add(chimp);
        if (p_221300_3_) {
            this.totalHealth += chimp.getHealth();
        }

        this.updateBarPercentage();
        this.markDirty();
        return true;
    }

    public void joinRandomChimpEvent(int wave, AggressiveChimpEntity chimp, @Nullable BlockPos p_221317_3_, boolean p_221317_4_) {
        boolean flag = this.joinRandomChimpEvent(wave, chimp);
        if (flag) {
            chimp.setRandomChimpEvent(this);
            chimp.setWave(wave);
            chimp.setCanJoinRandomChimpEvent(true);
            chimp.setJoinDelay(0);
            if (!p_221317_4_ && p_221317_3_ != null) {
                chimp.setPosition((double)p_221317_3_.getX() + 0.5D, (double)p_221317_3_.getY() + 1.0D, (double)p_221317_3_.getZ() + 0.5D);
                chimp.onInitialSpawn(this.world, this.world.getDifficultyForLocation(p_221317_3_), SpawnReason.EVENT, (ILivingEntityData)null, (CompoundNBT)null);
                chimp.setOnGround(true);
                this.world.func_242417_l(chimp);
            }
        }

    }

    public void updateBarPercentage() {
        this.bossInfo.setPercent(MathHelper.clamp(this.getCurrentHealth() / this.totalHealth, 0.0F, 1.0F));
    }

    public float getCurrentHealth() {
        float f = 0.0F;

        for(Set<AggressiveChimpEntity> set : this.chimps.values()) {
            for(AggressiveChimpEntity chimp : set) {
                f += chimp.getHealth();
            }
        }

        return f;
    }

    private boolean shouldSpawnGroup() {
        return this.preRandomChimpEventTicks == 0 && (this.groupsSpawned < this.numGroups) && this.getChimpCount() == 0;
    }

    public int getChimpCount() {
        return this.chimps.values().stream().mapToInt(Set::size).sum();
    }

    public void leaveRandomChimpEvent(AggressiveChimpEntity chimp, boolean p_221322_2_) {
        Set<AggressiveChimpEntity> set = this.chimps.get(chimp.getWave());
        if (set != null) {
            boolean flag = set.remove(chimp);
            if (flag) {
                if (p_221322_2_) {
                    this.totalHealth -= chimp.getHealth();
                }

                chimp.setRandomChimpEvent(null);
                this.updateBarPercentage();
                this.markDirty();
            }
        }

    }

    private void markDirty() {
        RandomChimpEventManager.getByWorld(this.world).markDirty();
    }

    public int getId() {
        return this.id;
    }

    private int getDefaultNumSpawns(RandomChimpEvent.WaveMember member, int wave, boolean p_221330_3_) {
        return p_221330_3_ ? member.waveCounts[this.numGroups] : member.waveCounts[wave];
    }

    public boolean isActive() {
        return this.active;
    }

    public CompoundNBT write(CompoundNBT nbt) {
        nbt.putInt("Id", this.id);
        nbt.putBoolean("Started", this.started);
        nbt.putBoolean("Active", this.active);
        nbt.putLong("TicksActive", this.ticksActive);
        nbt.putInt("GroupsSpawned", this.groupsSpawned);
        nbt.putInt("PreRandomChimpEventTicks", this.preRandomChimpEventTicks);
        nbt.putInt("PostRandomChimpEventTicks", this.postRandomChimpEventTicks);
        nbt.putFloat("TotalHealth", this.totalHealth);
        nbt.putInt("NumGroups", this.numGroups);
        nbt.putString("Status", this.status.getName());
        nbt.putInt("CX", this.center.getX());
        nbt.putInt("CY", this.center.getY());
        nbt.putInt("CZ", this.center.getZ());
        return nbt;
    }

    enum Status {
        ONGOING,
        STOPPED;

        private static final RandomChimpEvent.Status[] VALUES = values();

        private static RandomChimpEvent.Status getByName(String name) {
            for(RandomChimpEvent.Status status : VALUES) {
                if (name.equalsIgnoreCase(status.name())) {
                    return status;
                }
            }

            return ONGOING;
        }

        public String getName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    public enum WaveMember {
        AGGRESSIVE_CHIMP(DBMEntities.AGGRESSIVE_CHIMP, new int[]{10, 15, 20, 25, 30, 35, 40});

        private static RandomChimpEvent.WaveMember[] VALUES = values();
        private final Supplier<EntityType<AggressiveChimpEntity>> type;
        private final int[] waveCounts;

        WaveMember(Supplier<EntityType<AggressiveChimpEntity>> typeIn, int[] waveCountsIn) {
            this.type = typeIn;
            this.waveCounts = waveCountsIn;
        }
    }
}
