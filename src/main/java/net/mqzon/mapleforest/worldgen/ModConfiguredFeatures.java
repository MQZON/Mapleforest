package net.mqzon.mapleforest.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.mqzon.mapleforest.Mapleforest;
import net.mqzon.mapletree.block.ModBlocks;
import net.mqzon.mapletree.worldgen.foliage.custom.MapleFoliagePlacer;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?,?>> TREES_MAPLE = registerKey("trees_maple");
    public static void bootstrap(BootstapContext<ConfiguredFeature<?,?>> context) {
        register(context, TREES_MAPLE, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(
           HolderSet.direct(PlacementUtils.inlinePlaced(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.MAPLE_LOG.get()),
                new StraightTrunkPlacer(7,2,0),
                BlockStateProvider.simple(ModBlocks.MAPLE_LEAVES.get()),
                new MapleFoliagePlacer(
                        ConstantInt.of(4), //radius
                        ConstantInt.of(2), //offset
                        ConstantInt.of(7), //height
                        0.75F, 0.75F),
                new TwoLayersFeatureSize(1, 0, 2))
                .decorators(List.of(new BeehiveDecorator(0.05F))).build()

           ),
                   PlacementUtils.inlinePlaced(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.MAPLE_LOG.get()),
                new StraightTrunkPlacer(7,2,0),
                BlockStateProvider.simple(ModBlocks.RED_MAPLE_LEAVES.get()),
                new MapleFoliagePlacer(
                        ConstantInt.of(4), //radius
                        ConstantInt.of(2), //offset
                        ConstantInt.of(7), //height
                        0.75F, 0.75F),
                new TwoLayersFeatureSize(1, 0, 2))
                .decorators(List.of(new BeehiveDecorator(0.05F))).build())
                   ))
        );
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Mapleforest.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
            BootstapContext<ConfiguredFeature<?,?>> context,
            ResourceKey<ConfiguredFeature<?,?>> key,
            F feature,
            FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
