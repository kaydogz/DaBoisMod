package com.github.kaydogz.daboismod.world.dimension;

import com.github.kaydogz.daboismod.block.DBMBlocks;
import com.github.kaydogz.daboismod.world.biome.DBMBiomes;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.entity.item.LeashKnotEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.biome.provider.SingleBiomeProviderSettings;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.EndGenerationSettings;

import javax.annotation.Nullable;
import java.util.Random;

public class RealmOfTheAncientsDimension extends Dimension {
	public static final BlockPos SPAWN = new BlockPos(0, 60, 0);
	
	public RealmOfTheAncientsDimension(final World worldIn, final DimensionType dimension) {
		super(worldIn, dimension, 0);
	}
	
	@Override
	public ChunkGenerator<?> createChunkGenerator() {
		EndGenerationSettings realmoftheancientsgensettings = (EndGenerationSettings) ChunkGeneratorType.FLOATING_ISLANDS.createSettings();
		realmoftheancientsgensettings.setDefaultBlock(DBMBlocks.ANCIENT_BLOCK.get().getDefaultState());
		realmoftheancientsgensettings.setDefaultFluid(Blocks.WATER.getDefaultState());
		realmoftheancientsgensettings.setSpawnPos(this.getSpawnCoordinate());
		return ChunkGeneratorType.FLOATING_ISLANDS
				.create(this.world, BiomeProviderType.FIXED
						.create(((SingleBiomeProviderSettings) BiomeProviderType.FIXED
								.createSettings(this.world.getWorldInfo()))
								.setBiome(DBMBiomes.ANCIENT_ISLANDS.get())), realmoftheancientsgensettings);
	}

	@Override
	public float calculateCelestialAngle(final long p_76563_1_, final float p_76563_3_) {
		return 0.0f;
	}
	
	@Override @Nullable
	public float[] calcSunriseSunsetColors(final float p_76560_1_, final float p_76560_2_) {
		return null;
	}

	@Override
	public Vec3d getFogColor(final float p_76562_1_, final float p_76562_2_) {
		float f = MathHelper.cos(p_76562_1_ * 6.2831855f) * 2.0f + 0.5f;
		f = MathHelper.clamp(f, 0.0f, 1.0f);
		float f2 = 0.7529412f;
		float f3 = 0.84705883f;
		float f4 = 1.0f;
		f2 *= f * 0.94f + 0.06f;
		f3 *= f * 0.94f + 0.06f;
		f4 *= f * 0.91f + 0.09f;
		return new Vec3d((double) f2, (double) f3, (double) f4);
	}
	
	@Override
	public double getVoidFogYFactor() {
		return 0.5d;
	}

	@Override
	public boolean hasSkyLight() {
		return true;
	}
	
	@Override
	public boolean isSkyColored() {
		return true;
	}

	@Override
	public boolean canRespawnHere() {
		return false;
	}

	@Override
	public boolean isSurfaceWorld() {
		return false;
	}

	@Override
	public float getCloudHeight() {
		return 8.0f;
	}

	@Override @Nullable
	public BlockPos findSpawn(final ChunkPos p_206920_1_, final boolean p_206920_2_) {
		final Random random = new Random(this.world.getSeed());
		final BlockPos blockpos = new BlockPos(p_206920_1_.getXStart() + random.nextInt(15), 0, p_206920_1_.getZEnd() + random.nextInt(15));
		return this.world.getGroundAboveSeaLevel(blockpos).getMaterial().blocksMovement() ? blockpos : null;
	}

	@Override
	public BlockPos getSpawnCoordinate() {
		return RealmOfTheAncientsDimension.SPAWN;
	}

	@Override @Nullable
	public BlockPos findSpawn(final int p_206921_1_, final int p_206921_2_, final boolean p_206921_3_) {
		return this.findSpawn(new ChunkPos(p_206921_1_ >> 4, p_206921_2_ >> 4), p_206921_3_);
	}

	@Override
	public boolean doesXZShowFog(final int p_76568_1_, final int p_76568_2_) {
		return false;
	}

	@Override
	public void onWorldSave() {
		final CompoundNBT compoundnbt = new CompoundNBT();
		this.world.getWorldInfo().setDimensionData(this.world.getDimension().getType(), compoundnbt);
	}

	/**
	 * When overriding, ALWAYS CALL SUPER. This makes sure the game doesn't crash by preventing entities such as fishing rod bobbers from changing dimensions.
	 */
	public boolean shouldFallFromSky(Entity entity) {
		return !(entity instanceof FishingBobberEntity || entity instanceof LightningBoltEntity || entity instanceof AreaEffectCloudEntity || entity instanceof ShulkerBulletEntity || entity instanceof EnderCrystalEntity || entity instanceof HangingEntity || entity instanceof LeashKnotEntity);
	}
}
