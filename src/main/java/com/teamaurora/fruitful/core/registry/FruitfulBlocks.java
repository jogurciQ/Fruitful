package com.teamaurora.fruitful.core.registry;

import com.minecraftabnormals.abnormals_core.common.blocks.HedgeBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.LeafCarpetBlock;
import com.minecraftabnormals.abnormals_core.common.blocks.wood.AbnormalsSaplingBlock;
import com.minecraftabnormals.abnormals_core.core.util.registry.BlockSubRegistryHelper;
import com.teamaurora.fruitful.common.block.FruitLeavesBlock;
import com.teamaurora.fruitful.common.block.OakBlossomBlock;
import com.teamaurora.fruitful.common.block.OakFlowerLeavesBlock;
import com.teamaurora.fruitful.common.block.trees.FloweringOakTree;
import com.teamaurora.fruitful.core.Fruitful;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Fruitful.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
public class FruitfulBlocks {
    public static final BlockSubRegistryHelper HELPER = Fruitful.REGISTRY_HELPER.getBlockSubHelper();

    public static final RegistryObject<Block> FLOWERING_OAK_LEAVES = HELPER.createBlock("flowering_oak_leaves", ()->new OakFlowerLeavesBlock(AbstractBlock.Properties.from(Blocks.OAK_LEAVES).notSolid().harvestTool(ToolType.HOE).tickRandomly()), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> BUDDING_OAK_LEAVES = HELPER.createBlock("budding_oak_leaves", ()->new OakFlowerLeavesBlock(AbstractBlock.Properties.from(Blocks.OAK_LEAVES).notSolid().harvestTool(ToolType.HOE).tickRandomly()), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> BLOSSOMING_OAK_LEAVES = HELPER.createBlock("blossoming_oak_leaves", ()->new OakBlossomBlock(AbstractBlock.Properties.from(Blocks.OAK_LEAVES).notSolid().harvestTool(ToolType.HOE).tickRandomly()), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> APPLE_OAK_LEAVES = HELPER.createBlock("apple_oak_leaves", ()->new FruitLeavesBlock(AbstractBlock.Properties.from(Blocks.OAK_LEAVES).notSolid().harvestTool(ToolType.HOE).tickRandomly(), (LeavesBlock)BUDDING_OAK_LEAVES.get(), ()->Items.APPLE), ItemGroup.DECORATIONS);

    public static final RegistryObject<Block> FLOWERING_OAK_LEAF_CARPET = HELPER.createCompatBlock("quark", "flowering_oak_leaf_carpet", ()->new LeafCarpetBlock(AbstractBlock.Properties.from(Blocks.OAK_LEAVES).notSolid().harvestTool(ToolType.HOE)), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> BUDDING_OAK_LEAF_CARPET = HELPER.createCompatBlock("quark", "budding_oak_leaf_carpet", ()->new LeafCarpetBlock(AbstractBlock.Properties.from(Blocks.OAK_LEAVES).notSolid().harvestTool(ToolType.HOE)), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> BLOSSOMING_OAK_LEAF_CARPET = HELPER.createCompatBlock("quark", "blossoming_oak_leaf_carpet", ()->new LeafCarpetBlock(AbstractBlock.Properties.from(Blocks.OAK_LEAVES).notSolid().harvestTool(ToolType.HOE)), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> APPLE_OAK_LEAF_CARPET = HELPER.createCompatBlock("quark", "apple_oak_leaf_carpet", ()->new LeafCarpetBlock(AbstractBlock.Properties.from(Blocks.OAK_LEAVES).notSolid().harvestTool(ToolType.HOE)), ItemGroup.DECORATIONS);

    public static final RegistryObject<Block> FLOWERING_OAK_HEDGE = HELPER.createCompatBlock("quark", "flowering_oak_hedge", ()->new HedgeBlock(AbstractBlock.Properties.from(Blocks.OAK_FENCE)), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> BUDDING_OAK_HEDGE = HELPER.createCompatBlock("quark", "budding_oak_hedge", ()->new HedgeBlock(AbstractBlock.Properties.from(Blocks.OAK_FENCE)), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> BLOSSOMING_OAK_HEDGE = HELPER.createCompatBlock("quark", "blossoming_oak_hedge", ()->new HedgeBlock(AbstractBlock.Properties.from(Blocks.OAK_FENCE)), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> APPLE_OAK_HEDGE = HELPER.createCompatBlock("quark", "apple_oak_hedge", ()->new HedgeBlock(AbstractBlock.Properties.from(Blocks.OAK_FENCE)), ItemGroup.DECORATIONS);


    public static final RegistryObject<Block> FLOWERING_OAK_SAPLING = HELPER.createBlock("flowering_oak_sapling", ()->new AbnormalsSaplingBlock(new FloweringOakTree(), Block.Properties.create(Material.PLANTS, MaterialColor.GREEN).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT)), ItemGroup.DECORATIONS);
    public static final RegistryObject<Block> POTTED_FLOWERING_OAK_SAPLING = HELPER.createBlockNoItem("potted_flowering_oak_sapling", () -> new FlowerPotBlock(FLOWERING_OAK_SAPLING.get(), AbstractBlock.Properties.from(Blocks.POTTED_ALLIUM)));
}
