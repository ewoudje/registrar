package ga.amogussa.datablocks;

import com.google.gson.JsonObject;
import ga.amogussa.datablocks.data.PropertiesReader;
import ga.amogussa.datablocks.item.BasicItem;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ItemHandler {
    public static void registerBlock(ResourceLocation location, Block block, JsonObject item) {
        registerItem(location, new BlockItem(block, PropertiesReader.item(location, item)), item);
    }

    public static void registerItem(ResourceLocation location, Item item, JsonObject root) {
        Registry.register(Registry.ITEM, location, item);
    }
}
