package com.github.kaydogz.daboismod.client.model;

import com.github.kaydogz.daboismod.entity.AbstractChimpEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class ChimpModel extends AgeableModel<AbstractChimpEntity> {

    public ModelRenderer torso;
    public ModelRenderer rightarm;
    public ModelRenderer leftarm;
    public ModelRenderer neck;
    public ModelRenderer rightupperleg;
    public ModelRenderer leftupperleg;
    public ModelRenderer taillower;
    public ModelRenderer rightarmlower;
    public ModelRenderer mainhand;
    public ModelRenderer leftarmlower;
    public ModelRenderer head;
    public ModelRenderer rightlowerleg;
    public ModelRenderer rightfootback;
    public ModelRenderer rightfootfront;
    public ModelRenderer leftlowerleg;
    public ModelRenderer leftfootback;
    public ModelRenderer leftfootfront;
    public ModelRenderer tailupper;

    public ChimpModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        (this.head = new ModelRenderer(this, 0, 0)).setRotationPoint(0.4f, -0.4f, -2.5f);
        this.head.addBox(-2.5f, -2.3f, -2.9f, 5, 5, 5, 0.0f);
        this.setRotateAngle(this.head, -1.4570009f, 0.091106184f, 1.5025539f);
        (this.leftupperleg = new ModelRenderer(this, 23, 9)).setRotationPoint(3.6f, 3.2f, 7.1f);
        this.leftupperleg.addBox(-0.6f, -0.2f, -5.1f, 2, 2, 6, 0.0f);
        this.setRotateAngle(this.leftupperleg, 1.5025539f, 0.0f, 0.0f);
        (this.rightfootback = new ModelRenderer(this, 40, 14)).setRotationPoint(-1.1f, 2.6f, 0.3f);
        this.rightfootback.addBox(0.0f, 0.0f, -1.5f, 2, 1, 2, 0.0f);
        this.setRotateAngle(this.rightfootback, -0.68294734f, 0.0f, 0.0f);
        (this.leftfootback = new ModelRenderer(this, 39, 9)).setRotationPoint(0.9f, 3.0f, -0.2f);
        this.leftfootback.addBox(-0.9f, -0.3f, -1.7f, 2, 1, 2, 0.0f);
        this.setRotateAngle(this.leftfootback, -0.7285004f, -0.010646508f, 0.0038397245f);
        (this.rightarmlower = new ModelRenderer(this, 0, 12)).setRotationPoint(0.0f, 5.2f, 0.0f);
        this.rightarmlower.addBox(0.0f, 0.0f, 0.0f, 1, 7, 1, 0.0f);
        (this.leftarmlower = new ModelRenderer(this, 4, 12)).setRotationPoint(0.5f, 5.5f, 0.0f);
        this.leftarmlower.addBox(-0.5f, -0.2f, -0.1f, 1, 7, 1, 0.0f);
        this.setRotateAngle(this.leftarmlower, 0.045553092f, -0.045553092f, 0.0f);
        (this.torso = new ModelRenderer(this, 0, 12)).setRotationPoint(-2.0f, 15.4f, 3.3f);
        this.torso.addBox(0.0f, 0.0f, 0.0f, 5, 3, 9, 0.0f);
        this.setRotateAngle(this.torso, -0.7740535f, 0.0f, 0.0f);
        (this.mainhand = new ModelRenderer(this, 4, 30)).setRotationPoint(0.7f, 6.6f, 0.3f);
        this.mainhand.addBox(-0.7f, -0.6f, -0.4f, 1, 1, 1, 0.0f);
        (this.tailupper = new ModelRenderer(this, 22, 0)).setRotationPoint(0.6f, 0.2f, 3.4f);
        this.tailupper.addBox(-0.6f, -3.5f, 0.0f, 1, 4, 1, 0.0f);
        this.setRotateAngle(this.tailupper, -0.4553564f, 0.0f, 0.0f);
        (this.rightarm = new ModelRenderer(this, 44, 0)).setRotationPoint(-1.1f, 1.6f, 0.5f);
        this.rightarm.addBox(0.0f, 0.0f, 0.0f, 1, 6, 1, 0.0f);
        this.setRotateAngle(this.rightarm, 0.4553564f, 0.0f, 0.0f);
        (this.leftlowerleg = new ModelRenderer(this, 33, 19)).setRotationPoint(-0.5f, 1.6f, -4.4f);
        this.leftlowerleg.addBox(0.0f, 0.0f, -0.7f, 2, 3, 1, 0.0f);
        this.setRotateAngle(this.leftlowerleg, -0.045553092f, 0.0f, -0.007853982f);
        (this.taillower = new ModelRenderer(this, 54, 0)).setRotationPoint(1.7f, 0.2f, 7.9f);
        this.taillower.addBox(0.0f, 0.0f, 0.0f, 1, 1, 4, 0.0f);
        this.setRotateAngle(this.taillower, 1.6390387f, 0.0f, 0.0f);
        (this.rightfootfront = new ModelRenderer(this, 0, 30)).setRotationPoint(-1.1f, 1.0f, -1.7f);
        this.rightfootfront.addBox(0.0f, 0.0f, 0.0f, 2, 1, 1, 0.0f);
        this.setRotateAngle(this.rightfootfront, -0.68294734f, 0.0f, 0.0f);
        (this.leftfootfront = new ModelRenderer(this, 6, 30)).setRotationPoint(0.7f, 2.2f, -1.6f);
        this.leftfootfront.addBox(-0.7f, -0.6f, -0.9f, 2, 1, 1, 0.0f);
        this.setRotateAngle(this.leftfootfront, -0.7031931f, 0.0f, -0.0155334305f);
        (this.rightupperleg = new ModelRenderer(this, 22, 0)).setRotationPoint(1.3f, 3.4f, 7.6f);
        this.rightupperleg.addBox(-1.3f, -0.7f, -5.2f, 2, 2, 6, 0.0f);
        this.setRotateAngle(this.rightupperleg, 1.548107f, 0.0f, 0.0f);
        (this.leftarm = new ModelRenderer(this, 39, 0)).setRotationPoint(4.8f, 1.7f, 0.3f);
        this.leftarm.addBox(0.0f, 0.0f, 0.0f, 1, 6, 1, 0.0f);
        this.setRotateAngle(this.leftarm, 0.4553564f, 0.045553092f, 0.0f);
        (this.neck = new ModelRenderer(this, 0, 0)).setRotationPoint(2.4f, 1.8f, -0.2f);
        this.neck.addBox(-0.6f, -0.6f, -0.4f, 1, 1, 1, 0.0f);
        this.setRotateAngle(this.neck, 0.091106184f, 0.63739425f, -1.5025539f);
        (this.rightlowerleg = new ModelRenderer(this, 22, 10)).setRotationPoint(-0.2f, 1.0f, -4.6f);
        this.rightlowerleg.addBox(-1.2f, -0.2f, -0.6f, 2, 3, 1, 0.0f);
        this.setRotateAngle(this.rightlowerleg, -0.091106184f, -0.021118484f, -0.0f);
        this.neck.addChild(this.head);
        this.torso.addChild(this.leftupperleg);
        this.rightlowerleg.addChild(this.rightfootback);
        this.leftlowerleg.addChild(this.leftfootback);
        this.rightarm.addChild(this.rightarmlower);
        this.leftarm.addChild(this.leftarmlower);
        this.rightarmlower.addChild(this.mainhand);
        this.taillower.addChild(this.tailupper);
        this.torso.addChild(this.rightarm);
        this.leftupperleg.addChild(this.leftlowerleg);
        this.torso.addChild(this.taillower);
        this.rightlowerleg.addChild(this.rightfootfront);
        this.leftlowerleg.addChild(this.leftfootfront);
        this.torso.addChild(this.rightupperleg);
        this.torso.addChild(this.leftarm);
        this.torso.addChild(this.neck);
        this.rightupperleg.addChild(this.rightlowerleg);
    }

    public void setRotateAngle(final ModelRenderer modelRenderer, final float x, final float y, final float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(AbstractChimpEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.rightlowerleg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + 3.1415927f) * 1.4f * limbSwingAmount;
        this.leftlowerleg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + 3.1415927f) * 1.4f * limbSwingAmount;
        this.rightfootback.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + 3.1415927f) * 1.4f * limbSwingAmount;
        this.leftfootback.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + 3.1415927f) * 1.4f * limbSwingAmount;
        this.rightarmlower.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 1.9f * limbSwingAmount;
        this.leftarmlower.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
        this.neck.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 0.8f * limbSwingAmount;
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(torso);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, 0.0D, -0.2D);
        super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
    }
}
