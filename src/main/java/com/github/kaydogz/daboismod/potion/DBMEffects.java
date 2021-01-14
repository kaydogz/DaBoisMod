package com.github.kaydogz.daboismod.potion;

import com.github.kaydogz.daboismod.DaBoisMod;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DBMEffects {

	private static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, DaBoisMod.MODID);
	
	public static void registerEffects(IEventBus eventBus) {
		EFFECTS.register(eventBus);
	}
	
	public static final RegistryObject<IndicaStonageEffect> INDICA_STONAGE = EFFECTS.register("indica_stonage", () -> (IndicaStonageEffect) new IndicaStonageEffect(EffectType.BENEFICIAL, 2523174).addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "7398DB42-6B24-4117-945D-14BA1BFF8008", 0.2D, AttributeModifier.Operation.MULTIPLY_TOTAL));
	public static final RegistryObject<BleedingEffect> BLEEDING = EFFECTS.register("bleeding", () -> new BleedingEffect(EffectType.HARMFUL, 16720683));
	public static final RegistryObject<RadiationEffect> RADIATION = EFFECTS.register("radiation", () -> new RadiationEffect(EffectType.HARMFUL, 11464748));
}
