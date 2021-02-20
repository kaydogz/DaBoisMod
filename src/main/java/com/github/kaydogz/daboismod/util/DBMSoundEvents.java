package com.github.kaydogz.daboismod.util;

import com.github.kaydogz.daboismod.DaBoisMod;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DBMSoundEvents {

	private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DaBoisMod.MODID);
	
	public static void registerSoundEvents(IEventBus eventBus) {
		SOUND_EVENTS.register(eventBus);
	}

	private static RegistryObject<SoundEvent> registerSoundEvent(String location) {
		return SOUND_EVENTS.register(location, () -> new SoundEvent(DaBoisMod.modLocation(location)));
	}
	
	// Entity Sounds
	public static final RegistryObject<SoundEvent> ENTITY_SASQUATCH_AMBIENT = registerSoundEvent("entity.sasquatch.ambient");
	public static final RegistryObject<SoundEvent> ENTITY_SASQUATCH_HURT = registerSoundEvent("entity.sasquatch.hurt");
	public static final RegistryObject<SoundEvent> ENTITY_SASQUATCH_DEATH = registerSoundEvent("entity.sasquatch.death");
	public static final RegistryObject<SoundEvent> ENTITY_SASQUATCH_TRANSITION = registerSoundEvent("entity.sasquatch.transition");
	public static final RegistryObject<SoundEvent> ENTITY_SASQUATCH_SMASH = registerSoundEvent("entity.sasquatch.smash");
	public static final RegistryObject<SoundEvent> ENTITY_FLESH_CREEPER_PRIMED = registerSoundEvent("entity.flesh_creeper.primed");

	// Event Sounds
	public static final RegistryObject<SoundEvent> EVENT_RANDOM_CHIMP_LOOP = registerSoundEvent("event.random_chimp.loop");

	// Record Sounds
	public static final RegistryObject<SoundEvent> CARNIVORES = registerSoundEvent("music_disc.carnivores");
	public static final RegistryObject<SoundEvent> MR_BLUE_SKY = registerSoundEvent("music_disc.mr_blue_sky");
	public static final RegistryObject<SoundEvent> SPHERE = registerSoundEvent("music_disc.sphere");
	public static final RegistryObject<SoundEvent> MEGALOVANIA = registerSoundEvent("music_disc.megalovania");
	public static final RegistryObject<SoundEvent> A_DAY_IN_THE_LIFE = registerSoundEvent("music_disc.a_day_in_the_life");
	public static final RegistryObject<SoundEvent> HERE_COMES_THE_SUN = registerSoundEvent("music_disc.here_comes_the_sun");

	// Player Sounds
	public static final RegistryObject<SoundEvent> MAGNETIC_HUM = registerSoundEvent("player.magnetic_hum");
	
	// Master Sounds
	public static final RegistryObject<SoundEvent> HEAVENLY_CHOIR = registerSoundEvent("master.heavenly_choir");
}