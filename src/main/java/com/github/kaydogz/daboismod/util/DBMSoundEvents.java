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
	
	// Entity Sounds
	public static final RegistryObject<SoundEvent> ENTITY_SASQUATCH_AMBIENT = SOUND_EVENTS.register("entity.sasquatch.ambient", () -> new SoundEvent(DaBoisMod.modLocation("entity.sasquatch.ambient")));
	public static final RegistryObject<SoundEvent> ENTITY_SASQUATCH_HURT = SOUND_EVENTS.register("entity.sasquatch.hurt", () -> new SoundEvent(DaBoisMod.modLocation("entity.sasquatch.hurt")));
	public static final RegistryObject<SoundEvent> ENTITY_SASQUATCH_DEATH = SOUND_EVENTS.register("entity.sasquatch.death", () -> new SoundEvent(DaBoisMod.modLocation("entity.sasquatch.death")));
	public static final RegistryObject<SoundEvent> ENTITY_SASQUATCH_TRANSITION = SOUND_EVENTS.register("entity.sasquatch.transition", () -> new SoundEvent(DaBoisMod.modLocation("entity.sasquatch.transition")));
	public static final RegistryObject<SoundEvent> ENTITY_SASQUATCH_SMASH = SOUND_EVENTS.register("entity.sasquatch.smash", () -> new SoundEvent(DaBoisMod.modLocation("entity.sasquatch.smash")));
	public static final RegistryObject<SoundEvent> ENTITY_FLESH_CREEPER_PRIMED = SOUND_EVENTS.register("entity.flesh_creeper.primed", () -> new SoundEvent(DaBoisMod.modLocation("entity.flesh_creeper.primed")));

	// Record Sounds
	public static final RegistryObject<SoundEvent> CARNIVORES = SOUND_EVENTS.register("music_disc.carnivores", () -> new SoundEvent(DaBoisMod.modLocation("music_disc.carnivores")));
	public static final RegistryObject<SoundEvent> MR_BLUE_SKY = SOUND_EVENTS.register("music_disc.mr_blue_sky", () -> new SoundEvent(DaBoisMod.modLocation("music_disc.mr_blue_sky")));
	public static final RegistryObject<SoundEvent> SPHERE = SOUND_EVENTS.register("music_disc.sphere", () -> new SoundEvent(DaBoisMod.modLocation("music_disc.sphere")));
	public static final RegistryObject<SoundEvent> MEGALOVANIA = SOUND_EVENTS.register("music_disc.megalovania", () -> new SoundEvent(DaBoisMod.modLocation("music_disc.megalovania")));
	public static final RegistryObject<SoundEvent> A_DAY_IN_THE_LIFE = SOUND_EVENTS.register("music_disc.a_day_in_the_life", () -> new SoundEvent(DaBoisMod.modLocation("music_disc.a_day_in_the_life")));
	public static final RegistryObject<SoundEvent> HERE_COMES_THE_SUN = SOUND_EVENTS.register("music_disc.here_comes_the_sun", () -> new SoundEvent(DaBoisMod.modLocation("music_disc.here_comes_the_sun")));

	// Player Sounds
	public static final RegistryObject<SoundEvent> MAGNETIC_HUM = SOUND_EVENTS.register("player.magnetic_hum", () -> new SoundEvent(DaBoisMod.modLocation("player.magnetic_hum")));
	
	// Master Sounds
	public static final RegistryObject<SoundEvent> HEAVENLY_CHOIR = SOUND_EVENTS.register("master.heavenly_choir", () -> new SoundEvent(DaBoisMod.modLocation("master.heavenly_choir")));
}
