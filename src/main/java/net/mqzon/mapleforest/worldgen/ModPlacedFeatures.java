package net.mqzon.mapleforest.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.mqzon.mapleforest.Mapleforest;
import net.mqzon.mapletree.block.ModBlocks;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> TREES_MAPLE_PLACED_KEY = createKey("trees_maple_placed");
    public static final ResourceKey<PlacedFeature> MAPLE_LEAF_PILE_PLACED_KEY = createKey("maple_leaf_pile_placed");
    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?,?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, TREES_MAPLE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.TREES_MAPLE),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(9, 0.1f, 2),
                        ModBlocks.MAPLE_SAPLING.get()));

        register(context, MAPLE_LEAF_PILE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.MAPLE_LEAF_PILE),
                List.of(NoiseThresholdCountPlacement.of(-0.8D, 5, 10),
                        InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
    }

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Mapleforest.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context,
                                 ResourceKey<PlacedFeature> key,
                                 Holder<ConfiguredFeature<?,?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
