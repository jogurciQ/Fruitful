package com.teamaurora.fruitful.core.registry;

import com.google.common.collect.ImmutableList;
import com.minecraftabnormals.abnormals_core.core.util.DataUtil;
import com.teamaurora.fruitful.core.Fruitful;
import com.teamaurora.fruitful.core.FruitfulConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Fruitful.MODID)
public class FruitfulFeatures {

    private static boolean isOak(BaseTreeFeatureConfig config) {
        if (config.trunkProvider instanceof SimpleBlockStateProvider) {
            if (((SimpleBlockStateProvider)config.trunkProvider).getBlockState(new Random(), new BlockPos(0, 0, 0)).getBlock() == Blocks.OAK_LOG) {
                return config.foliagePlacer instanceof BlobFoliagePlacer;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private static boolean isFancyOak(BaseTreeFeatureConfig config) {
        if (config.trunkProvider instanceof SimpleBlockStateProvider) {
            if (((SimpleBlockStateProvider)config.trunkProvider).getBlockState(new Random(), new BlockPos(0, 0, 0)).getBlock() == Blocks.OAK_LOG) {
                return config.foliagePlacer instanceof FancyFoliagePlacer;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        ResourceLocation biomeName = event.getName();

        if (biomeName == null) return;

        if (FruitfulConfig.COMMON.flowerBiomes.get().contains(biomeName.toString())) {
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Configured.FLOWERING_OAK_INFREQUENT);
        }

        if (DataUtil.matchesKeys(biomeName, Biomes.FLOWER_FOREST)) {
            List<Supplier<ConfiguredFeature<?, ?>>> features = event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);
            if (event.getName() != null) {
                List<Supplier<ConfiguredFeature<?, ?>>> toRemove = new ArrayList<>();
                for (Supplier<ConfiguredFeature<?, ?>> configuredFeatureSupplier : features) {
                    IFeatureConfig config = configuredFeatureSupplier.get().config;
                    if (config instanceof DecoratedFeatureConfig) {
                        ConfiguredFeature<?, ?> configuredFeature1 = ((DecoratedFeatureConfig) config).feature.get();
                        IFeatureConfig config1 = configuredFeature1.config;
                        if (config1 instanceof DecoratedFeatureConfig) {
                            ConfiguredFeature<?, ?> configuredFeature = ((DecoratedFeatureConfig) config1).feature.get();
                            if (configuredFeature.config instanceof MultipleRandomFeatureConfig) {
                                MultipleRandomFeatureConfig mrfconfig = (MultipleRandomFeatureConfig) configuredFeature.config;

                                boolean remove = false;

                                if (isFancyOak((BaseTreeFeatureConfig) mrfconfig.defaultFeature.get().config)) {
                                    remove = true;
                                } else if (isOak((BaseTreeFeatureConfig) mrfconfig.defaultFeature.get().config)) {
                                    remove = true;
                                }

                                for (ConfiguredRandomFeatureList crfl : mrfconfig.features) {
                                    if (isFancyOak((BaseTreeFeatureConfig) crfl.feature.get().config)) {
                                        remove = true;
                                    } else if (isOak((BaseTreeFeatureConfig) crfl.feature.get().config)) {
                                        remove = true;
                                    }
                                }

                                if (remove) {
                                    toRemove.add(configuredFeatureSupplier);
                                }
                            }
                        }
                    }
                }
                toRemove.forEach(features::remove);
                event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Configured.FOREST_FLOWER_TREES);
            }
        }
    }

    public static final class BlockStates {
        public static final BlockState OAK_LOG = Blocks.OAK_LOG.getDefaultState();
        public static final BlockState FLOWERING_OAK_LEAVES = FruitfulBlocks.FLOWERING_OAK_LEAVES.get().getDefaultState();
        public static final BlockState BUDDING_OAK_LEAVES = FruitfulBlocks.BUDDING_OAK_LEAVES.get().getDefaultState();
    }

    public static final class Configs {
        public static final BaseTreeFeatureConfig FLOWERING_OAK = (new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(BlockStates.OAK_LOG),
                new WeightedBlockStateProvider().addWeightedBlockstate(BlockStates.BUDDING_OAK_LEAVES, 2).addWeightedBlockstate(BlockStates.FLOWERING_OAK_LEAVES, 1),
                new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3),
                new StraightTrunkPlacer(4, 2, 0),
                new TwoLayerFeature(1, 0, 1))
        ).setIgnoreVines().build();
        public static final BaseTreeFeatureConfig FLOWERING_FANCY_OAK = (new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(BlockStates.OAK_LOG),
                new WeightedBlockStateProvider().addWeightedBlockstate(BlockStates.BUDDING_OAK_LEAVES, 2).addWeightedBlockstate(BlockStates.FLOWERING_OAK_LEAVES, 1),
                new FancyFoliagePlacer(FeatureSpread.func_242252_a(2),
                FeatureSpread.func_242252_a(4), 4), new FancyTrunkPlacer(3, 11, 0),
                new TwoLayerFeature(0, 0, 0, OptionalInt.of(4)))
        ).setIgnoreVines().func_236702_a_(Heightmap.Type.MOTION_BLOCKING).build();
    }

    public static final class Configured {
        public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FLOWERING_OAK = Feature.TREE.withConfiguration(Configs.FLOWERING_OAK);
        public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FLOWERING_FANCY_OAK = Feature.TREE.withConfiguration(Configs.FLOWERING_FANCY_OAK);
        public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FLOWERING_OAK_BEES_005 = Feature.TREE.withConfiguration(Configs.FLOWERING_OAK.func_236685_a_(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT)));
        public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FLOWERING_FANCY_OAK_BEES_005 = Feature.TREE.withConfiguration(Configs.FLOWERING_FANCY_OAK.func_236685_a_(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT)));
        public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FLOWERING_OAK_BEES_002 = Feature.TREE.withConfiguration(Configs.FLOWERING_OAK.func_236685_a_(ImmutableList.of(Features.Placements.BEES_002_PLACEMENT)));
        public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FLOWERING_FANCY_OAK_BEES_002 = Feature.TREE.withConfiguration(Configs.FLOWERING_FANCY_OAK.func_236685_a_(ImmutableList.of(Features.Placements.BEES_002_PLACEMENT)));

        public static final ConfiguredFeature<?, ?> FLOWERING_OAK_INFREQUENT = FLOWERING_OAK.withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.12F, 3)));
        public static final ConfiguredFeature<?, ?> FOREST_FLOWER_TREES = Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Features.BIRCH_BEES_002.withChance(0.2F), FLOWERING_FANCY_OAK_BEES_002.withChance(0.1F)), FLOWERING_OAK_BEES_002)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(6, 0.1F, 1)));

        private static <FC extends IFeatureConfig> void register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Fruitful.MODID, name), configuredFeature);
        }

        public static void registerConfiguredFeatures() {
            register("flowering_oak", FLOWERING_OAK);
            register("flowering_fancy_oak", FLOWERING_FANCY_OAK);
            register("flowering_oak_bees_005", FLOWERING_OAK_BEES_005);
            register("flowering_fancy_oak_bees_005", FLOWERING_FANCY_OAK_BEES_005);
            register("flowering_oak_bees_002", FLOWERING_OAK_BEES_002);
            register("flowering_fancy_oak_bees_002", FLOWERING_FANCY_OAK_BEES_002);

            register("flowering_oak_infrequent", FLOWERING_OAK_INFREQUENT);
            register("forest_flower_trees", FOREST_FLOWER_TREES);
        }
    }
}
