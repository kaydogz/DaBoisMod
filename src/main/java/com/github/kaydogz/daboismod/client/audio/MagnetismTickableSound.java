package com.github.kaydogz.daboismod.client.audio;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.IPlayerCapability;
import com.github.kaydogz.daboismod.capability.provider.PlayerProvider;
import com.github.kaydogz.daboismod.util.DBMSoundEvents;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.util.LazyOptional;

public class MagnetismTickableSound extends TickableSound {

	private final AbstractClientPlayerEntity player;
	
	public MagnetismTickableSound(AbstractClientPlayerEntity playerIn) {
		super(DBMSoundEvents.MAGNETIC_HUM.get(), SoundCategory.PLAYERS);
		this.player = playerIn;
		this.repeat = true;
		this.repeatDelay = 0;
		this.volume = 1.0F;
		this.x = (float) this.player.getPosX();
		this.y = (float) this.player.getPosY();
		this.z = (float) this.player.getPosZ();
	}

	@Override
	public boolean canBeSilent() {
		return true;
	}
	
	@Override
	public void tick() {
		if (this.player == null || !this.player.isAlive()) {
			this.donePlaying = true;
		} else {
			LazyOptional<IPlayerCapability> playerCap = PlayerProvider.getCapabilityOf(this.player);
			if (!playerCap.isPresent() || !DaBoisMod.get(playerCap).isMagnetic()) this.donePlaying = true;
			this.x = (float) this.player.getPosX();
			this.y = (float) this.player.getPosY();
			this.z = (float) this.player.getPosZ();
		}
	}
}
