package com.github.kaydogz.daboismod.world.gen.feature.structure;

import com.github.kaydogz.daboismod.DaBoisMod;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;

public class TombPiece extends TemplateStructurePiece {

    public TombPiece(int componentTypeIn) {
        super(DBMStructurePieceTypes.tomb, componentTypeIn);
    }

    public TombPiece(TemplateManager managerIn, CompoundNBT compoundNBT) {
        super(DBMStructurePieceTypes.tomb, compoundNBT);
        Template template = managerIn.getTemplateDefaulted(DaBoisMod.modLocation("tomb"));
        PlacementSettings placementSettings = new PlacementSettings().setIgnoreEntities(true).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
        this.setup(template, templatePosition, placementSettings);
    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, IWorld worldIn, Random rand, MutableBoundingBox sbb) {
    }
}
