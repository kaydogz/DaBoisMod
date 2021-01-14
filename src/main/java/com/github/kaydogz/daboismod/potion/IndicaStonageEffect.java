package com.github.kaydogz.daboismod.potion;

import com.github.kaydogz.daboismod.item.DBMItems;
import com.github.kaydogz.daboismod.network.DBMNetworkHandler;
import com.github.kaydogz.daboismod.network.server.SDisplayItemActivationPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class IndicaStonageEffect extends Effect {

	public IndicaStonageEffect(EffectType typeIn, int liquidColorIn) {
		super(typeIn, liquidColorIn);
	}
	
	@Override
	public void applyAttributesModifiersToEntity(LivingEntity entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
		if (entityLivingBaseIn instanceof ServerPlayerEntity) DBMNetworkHandler.sendToPlayer(new SDisplayItemActivationPacket(new ItemStack(DBMItems.MARIJUANA.get())), (ServerPlayerEntity) entityLivingBaseIn);
		super.applyAttributesModifiersToEntity(entityLivingBaseIn, attributeMapIn, amplifier);
		entityLivingBaseIn.setGlowing(true);
	}
	
	@Override
	public void removeAttributesModifiersFromEntity(LivingEntity entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
		super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);
		entityLivingBaseIn.setGlowing(false);
	}
}
