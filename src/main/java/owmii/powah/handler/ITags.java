package owmii.powah.handler;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class ITags {
    public static class Blocks {
        // Remove non-dry ice if Forge handles them in the future
        public static final Tags.IOptionalNamedTag<Block> ICES = tag("ices");
        public static final Tags.IOptionalNamedTag<Block> ICES_ICE = tag("ices/ice");
        public static final Tags.IOptionalNamedTag<Block> ICES_PACKED = tag("ices/packed");
        public static final Tags.IOptionalNamedTag<Block> ICES_BLUE = tag("ices/blue");
        public static final Tags.IOptionalNamedTag<Block> ICES_DRY = tag("ices/dry");
        public static final Tags.IOptionalNamedTag<Block> URANINITE_ORE = tag("ores/uraninite");
        public static final Tags.IOptionalNamedTag<Block> URANINITE_ORE_POOR = tag("ores/uraninite_poor");
        public static final Tags.IOptionalNamedTag<Block> URANINITE_ORE_DENSE = tag("ores/uraninite_dense");
        public static final Tags.IOptionalNamedTag<Block> URANINITE_BLOCK = tag("storage_blocks/uraninite");

        private static Tags.IOptionalNamedTag<Block> tag(String name) {
            return BlockTags.createOptional(new ResourceLocation("forge", name));
        }
    }

    public static class Items {
        // Remove non-dry ice if Forge handles them in the future
        public static final Tags.IOptionalNamedTag<Item> ICES = tag("ices");
        public static final Tags.IOptionalNamedTag<Item> ICES_ICE = tag("ices/ice");
        public static final Tags.IOptionalNamedTag<Item> ICES_PACKED = tag("ices/packed");
        public static final Tags.IOptionalNamedTag<Item> ICES_BLUE = tag("ices/blue");
        public static final Tags.IOptionalNamedTag<Item> ICES_DRY = tag("ices/dry");
        public static final Tags.IOptionalNamedTag<Item> URANINITE_ORE = tag("ores/uraninite");
        public static final Tags.IOptionalNamedTag<Item> URANINITE_ORE_POOR = tag("ores/uraninite_poor");
        public static final Tags.IOptionalNamedTag<Item> URANINITE_ORE_DENSE = tag("ores/uraninite_dense");
        public static final Tags.IOptionalNamedTag<Item> URANINITE_BLOCK = tag("storage_blocks/uraninite");
        public static final Tags.IOptionalNamedTag<Item> ENERGIZED_STEEL_BLOCK = tag("storage_blocks/energized_steel");
        public static final Tags.IOptionalNamedTag<Item> BLAZING_CRYSTAL_BLOCK = tag("storage_blocks/blazing_crystal");
        public static final Tags.IOptionalNamedTag<Item> NIOTIC_CRYSTAL_BLOCK = tag("storage_blocks/niotic_crystal");
        public static final Tags.IOptionalNamedTag<Item> SPIRITED_CRYSTAL_BLOCK = tag("storage_blocks/spirited_crystal");
        public static final Tags.IOptionalNamedTag<Item> NITRO_CRYSTAL_BLOCK = tag("storage_blocks/nitro_crystal");

        private static Tags.IOptionalNamedTag<Item> tag(String name) {
            return ItemTags.createOptional(new ResourceLocation("forge", name));
        }
    }
}
