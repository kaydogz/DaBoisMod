package com.github.kaydogz.daboismod.world.gen.feature;

import com.github.kaydogz.daboismod.block.DBMBlocks;
import com.mojang.datafixers.Dynamic;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.treedecorator.TreeDecorator;

import java.util.List;

public class DBMTreeFeatureConfig extends TreeFeatureConfig {
	
	public DBMTreeFeatureConfig(BlockStateProvider trunkProviderIn, BlockStateProvider leavesProviderIn, FoliagePlacer foliagePlacerIn, List<TreeDecorator> decoratorsIn, int baseHeightIn, int heightRandAIn, int heightRandBIn, int trunkHeightIn, int trunkHeightRandomIn, int trunkTopOffsetIn, int trunkTopOffsetRandomIn, int foliageHeightIn, int foliageHeightRandomIn, int maxWaterDepthIn, boolean ignoreVinesIn) {
		super(trunkProviderIn, leavesProviderIn, foliagePlacerIn, decoratorsIn, baseHeightIn, heightRandAIn, heightRandBIn, trunkHeightIn, trunkHeightRandomIn, trunkTopOffsetIn, trunkTopOffsetRandomIn, foliageHeightIn, foliageHeightRandomIn, maxWaterDepthIn, ignoreVinesIn);
	}

	public static <T> DBMTreeFeatureConfig deserializeFoliage(Dynamic<T> dynamic) {
		BaseTreeFeatureConfig basetreefeatureconfig = BaseTreeFeatureConfig.deserialize(dynamic);
		FoliagePlacerType<?> foliageplacertype = Registry.FOLIAGE_PLACER_TYPE.getOrDefault(new ResourceLocation(dynamic.get("foliage_placer").get("type").asString().orElseThrow(RuntimeException::new)));
		return new DBMTreeFeatureConfig(basetreefeatureconfig.trunkProvider, basetreefeatureconfig.leavesProvider, foliageplacertype.func_227391_a_(dynamic.get("foliage_placer").orElseEmptyMap()), basetreefeatureconfig.decorators, basetreefeatureconfig.baseHeight, dynamic.get("height_rand_a").asInt(0), dynamic.get("height_rand_b").asInt(0), dynamic.get("trunk_height").asInt(-1), dynamic.get("trunk_height_random").asInt(0), dynamic.get("trunk_top_offset").asInt(0), dynamic.get("trunk_top_offset_random").asInt(0), dynamic.get("foliage_height").asInt(-1), dynamic.get("foliage_height_random").asInt(0), dynamic.get("max_water_depth").asInt(0), dynamic.get("ignore_vines").asBoolean(false));
	}
	
	public static <T> TreeFeatureConfig deserializePadauk(Dynamic<T> data) {
		return DBMTreeFeatureConfig.deserializeFoliage(data).setSapling(DBMBlocks.PADAUK_SAPLING.get());
	}
}
