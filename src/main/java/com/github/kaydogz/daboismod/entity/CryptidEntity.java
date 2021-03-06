package com.github.kaydogz.daboismod.entity;

import com.github.kaydogz.daboismod.util.DBMSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public abstract class CryptidEntity extends MonsterEntity {

	protected final ServerBossInfo bossInfo = new ServerBossInfo(this.getDisplayName(), this.getBossBarColor(), BossInfo.Overlay.PROGRESS);
	
	protected CryptidEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
		super(type, worldIn);
		this.setHealth(this.getMaxHealth());
	}
	
	public abstract Item getCryptidDropItem();
	
	protected abstract BossInfo.Color getBossBarColor();
	
	@Override
	public void onDeath(DamageSource cause) {
		if (!this.world.isRemote) {
			final double radius = 1.5D;
			final int bolts = 8;
			for (int i = 0; i < bolts; i++) {
				LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(this.world);
				lightningboltentity.moveForced(Vector3d.copyCenteredHorizontally(this.getPosition().add(radius * Math.cos(i * 2 * Math.PI / bolts), 0, radius * Math.sin(i * 2 * Math.PI / bolts))));
				lightningboltentity.setEffectOnly(true);
				this.world.addEntity(lightningboltentity);
			}
		}
		this.playSound(DBMSoundEvents.HEAVENLY_CHOIR.get(), 10.0F, 1.0F);
		super.onDeath(cause);
	}

	@Override
	public void setCustomName(@Nullable ITextComponent name) {
		super.setCustomName(name);
		this.bossInfo.setName(this.getDisplayName());
	}
	
	@Override
	protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
		super.dropSpecialItems(source, looting, recentlyHitIn);
		ItemEntity itemEntity = this.entityDropItem(this.getCryptidDropItem());
		if (itemEntity != null) itemEntity.setNoDespawn();
	}

	@Override
	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
	}
	
	@Override
	protected void updateAITasks() {
		super.updateAITasks();
		this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
	}
	
	@Override
	public void addTrackingPlayer(ServerPlayerEntity player) {
		super.addTrackingPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	@Override
	public void removeTrackingPlayer(ServerPlayerEntity player) {
		super.removeTrackingPlayer(player);
		this.bossInfo.removePlayer(player);
	}
	
	@Override
	public void checkDespawn() {
		if (this.world.getDifficulty() == Difficulty.PEACEFUL && this.isDespawnPeaceful()) {
			this.remove();
		} else {
			this.idleTime = 0;
		}
	}
	
	@Override
	public boolean isNonBoss() {
		return false;
	}
}
