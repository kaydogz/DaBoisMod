package com.github.kaydogz.daboismod.client.renderer;

import com.github.kaydogz.daboismod.DaBoisMod;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.ResourceLocation;

public class FleshCreeperRenderer extends CreeperRenderer {

    private static final ResourceLocation FLESH_CREEPER = DaBoisMod.modLocation("textures/entity/flesh_creeper.png");

    public FleshCreeperRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(CreeperEntity entity) {
        return FLESH_CREEPER;
    }
}
