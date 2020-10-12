package com.github.kaydogz.daboismod.client.renderer;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.github.kaydogz.daboismod.capability.base.IVillagerCapability;
import com.github.kaydogz.daboismod.capability.provider.VillagerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;

public class DBMVillagerRenderer extends VillagerRenderer {

    public DBMVillagerRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, (IReloadableResourceManager) Minecraft.getInstance().getResourceManager());
    }

    @Override
    public ResourceLocation getEntityTexture(VillagerEntity entity) {
        LazyOptional<IVillagerCapability> lazyCap = VillagerProvider.getCapabilityOf(entity);
        if (lazyCap.isPresent()) {
            return DaBoisMod.modLocation("textures/entity/villager/villager_skin_tone_" + DaBoisMod.get(lazyCap).getSkinTone() + ".png");
        }
        return super.getEntityTexture(entity);
    }
}
