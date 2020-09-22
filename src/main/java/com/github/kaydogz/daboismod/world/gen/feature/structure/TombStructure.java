package com.github.kaydogz.daboismod.world.gen.feature.structure;

import com.github.kaydogz.daboismod.DaBoisMod;
import com.mojang.datafixers.Dynamic;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.ScatteredStructure;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.function.Function;

public class TombStructure extends ScatteredStructure<NoFeatureConfig> {

    public TombStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    protected int getSeedModifier() {
        return 165745296;
    }

    @Override
    public IStartFactory getStartFactory() {
        return TombStructure.Start::new;
    }

    @Override
    public String getStructureName() {
        return DaBoisMod.modLocation("tomb").toString();
    }

    @Override
    public int getSize() {
        return 24;
    }

    public static class Start extends StructureStart {
        public Start(Structure<?> p_i225819_1_, int p_i225819_2_, int p_i225819_3_, MutableBoundingBox boundingBox, int p_i225819_5_, long p_i225819_6_) {
            super(p_i225819_1_, p_i225819_2_, p_i225819_3_, boundingBox, p_i225819_5_, p_i225819_6_);
        }

        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putInt("TPX", chunkX * 16);
            nbt.putInt("TPY", 64);
            nbt.putInt("TPZ", chunkZ * 16);
            TombPiece tombpiece = new TombPiece(templateManagerIn, nbt);
            this.components.add(tombpiece);
            this.recalculateStructureSize();
        }
    }
}
