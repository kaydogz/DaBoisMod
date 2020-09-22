package com.github.kaydogz.daboismod.client.model;

import com.github.kaydogz.daboismod.entity.SasquatchEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * Texture and base file was made by Barytyannus in MCreator, but I updated it to a BipedModel and to 1.15.2, which took forever because the model was originally static.
 * @author mrkdo && Barytyannus
 *
 */
public class SasquatchModel extends BipedModel<SasquatchEntity> {
	
	public ModelRenderer lowerBody;
    public ModelRenderer upperBody;
    public ModelRenderer leftFoot;
    public ModelRenderer rightFoot;
    public ModelRenderer leftArm;
    public ModelRenderer rightArm;
    public ModelRenderer leftForearm;
    public ModelRenderer rightForearm;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;
            
    public SasquatchModel(float modelSize) {
		super(modelSize);
		this.createParts();
	}
    
    private void createParts() {
    	this.textureWidth = 256;
        this.textureHeight = 128;
        
        // Left Shoulder
        (this.bipedLeftArm = new ModelRenderer((Model) this, 0, 24)).setRotationPoint(14.0F, 2.0F, 3.5F);
        this.bipedLeftArm.addBox(6.8F, -5.45F, 0.0F, 6.0F, 6.0F, 4.0F, 0.0F);
        
        // Right Shoulder
        (this.bipedRightArm = new ModelRenderer((Model) this, 22, 24)).setRotationPoint(-1.6F, 1.5F, 2.8F);
        this.bipedRightArm.addBox(-1.2F, -5.95F, 0.0F, 6.0F, 6.0F, 4.0F, 0.0F);

        // Left Arm
        (this.leftArm = new ModelRenderer((Model) this, 34, 0)).setRotationPoint(0.0F, 3.3F, 0.8F);
        this.leftArm.addBox(6.8F, -2.55F, -1.5F, 6.0F, 14.0F, 4.0F, 0.0F);
        this.setRotateAngle(this.leftArm, -0.3F, 0.0F, 0.0F);
        
        // Right Arm
        (this.rightArm = new ModelRenderer((Model) this, 56, 0)).setRotationPoint(-0.5F, 3.0F, 0.9F);
        this.rightArm.addBox(-0.7F, -2.75F, -1.6F, 6.0F, 14.0F, 4.0F, 0.0F);
        this.setRotateAngle(this.rightArm, -0.3F, 0.0F, 0.0F);
        
        // Left Forearm
        (this.leftForearm = new ModelRenderer((Model) this, 102, 0)).setRotationPoint(0.4F, 13.3F, 0.3F);
        this.leftForearm.addBox(6.4F, -1.4F, -2.3F, 6.0F, 12.0F, 4.0F, 0.0F);
        this.setRotateAngle(this.leftForearm, -0.3F, 0.0F, 0.0F);

        // Right Forearm
        (this.rightForearm = new ModelRenderer((Model) this, 78, 0)).setRotationPoint(-0.5F, 14.2F, 0.6F);
        this.rightForearm.addBox(-0.2F, -2.3F, -3.0F, 6.0F, 12.0F, 4.0F, 0.0F);
        this.setRotateAngle(this.rightForearm, -0.3F, 0.0F, 0.0F);
        
        // Left Leg Thigh
        (this.bipedLeftLeg = new ModelRenderer((Model) this, 50, 52)).setRotationPoint(9.6F, 6.1F, 3.3F);
        this.bipedLeftLeg.addBox(-2.5F, -8.8F, -0.1F, 6.0F, 8.0F, 6.0F, 0.0F);
        this.setRotateAngle(this.bipedLeftLeg, 0.0F, 0.0F, 0.0F);
        
        // Right Leg Thigh
        (this.bipedRightLeg = new ModelRenderer((Model) this, 78, 52)).setRotationPoint(1.3F, 4.8F, 2.8F);
        this.bipedRightLeg.addBox(-2.9F, -8.8F, -0.1F, 6.0F, 8.0F, 6.0F, 0.0F);
        this.setRotateAngle(this.bipedRightLeg, 0.0F, 0.0F, 0.0F);
        
        // Left Leg
        (this.leftLeg = new ModelRenderer((Model) this, 20, 52)).setRotationPoint(-0.2F, 6.1F, -1.1F);
        this.leftLeg.addBox(-1.8F, -6.9F, 1.0F, 6.0F, 16.0F, 6.0F, 0.0F);
        this.setRotateAngle(this.leftLeg, 0.0F, 0.0F, 0.0F);

        // Right Leg
        (this.rightLeg = new ModelRenderer((Model) this, 106, 52)).setRotationPoint(-0.1F, 7.7F, 0.2F);
        this.rightLeg.addBox(-3.3F, -8.5F, -0.3F, 6.0F, 16.0F, 6.0F, 0.0F);
        this.setRotateAngle(this.rightLeg, 0.0F, 0.0F, 0.0F);
        
        // Left Foot
        (this.leftFoot = new ModelRenderer((Model) this, 20, 80)).setRotationPoint(0.5F, 15.8F, 1.1F);
        this.leftFoot.addBox(-2.3F, -6.7F, -6.1F, 6.0F, 2.0F, 12.0F, 0.0F);
        this.setRotateAngle(this.leftFoot, 0.0F, 0.0F, 0.0F);
        
        // Right Foot
        (this.rightFoot = new ModelRenderer((Model) this, 96, 80)).setRotationPoint(0.9F, 15.7F, -1.0F);
        this.rightFoot.addBox(-4.2F, -8.2F, -5.3F, 6.0F, 2.0F, 12.0F, 0.0F);
        this.setRotateAngle(this.rightFoot, 0.0F, 0.0F, 0.0F);
        
        // Head
        (this.bipedHead = new ModelRenderer((Model) this, 0, 0)).setRotationPoint(5.6F, -2.2F, 3.4F);
        this.bipedHead.addBox(-3.8F, -12.7F, -2.4F, 8.0F, 10.0F, 8.0F, 0.0F);
        
        // Upper Body
        (this.upperBody = new ModelRenderer((Model) this, 42, 22)).setRotationPoint(-0.1F, -14.1F, -2.0F);
        this.upperBody.addBox(-0.2F, -2.7F, 0.0F, 12.0F, 14.0F, 10.0F, 0.0F);
        
        // Lower Body
        (this.lowerBody = new ModelRenderer((Model) this, 2, 34)).setRotationPoint(-5.7F, -5.3F, 0.0F);
        this.lowerBody.addBox(-0.2F, -2.8F, 0.0F, 12.0F, 6.0F, 6.0F, 0.0F);
        
        // Body Attachments
        this.upperBody.addChild(this.bipedHead);
        this.upperBody.addChild(this.bipedLeftArm);
        this.upperBody.addChild(this.bipedRightArm);
        this.lowerBody.addChild(this.upperBody);
        this.lowerBody.addChild(this.bipedLeftLeg);
        this.lowerBody.addChild(this.bipedRightLeg);
        
        // Limb Attachments
        this.leftArm.addChild(this.leftForearm);
        this.bipedLeftArm.addChild(this.leftArm);
        this.leftLeg.addChild(this.leftFoot);
        this.bipedLeftLeg.addChild(this.leftLeg);
        this.rightArm.addChild(this.rightForearm);
        this.bipedRightArm.addChild(this.rightArm);
        this.rightLeg.addChild(this.rightFoot);
        this.bipedRightLeg.addChild(this.rightLeg);
    }
    
	@Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.lowerBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		
    }
    
    public void setRotateAngle(final ModelRenderer modelRenderer, final float x, final float y, final float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

	@Override
	public void setRotationAngles(SasquatchEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount / 3, ageInTicks, netHeadYaw, headPitch);
	}
}
