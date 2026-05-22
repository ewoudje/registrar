package ga.amogussa.registrar;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import ga.amogussa.registrar.data.PropertiesReader;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemHandler {
    private static final List<Pair<Item, JsonObject>> addedItems = new ArrayList<>();

    public static void registerBlock(ResourceLocation location, Block block, JsonObject item) {
        registerItem(location, new BlockItem(block, PropertiesReader.item(location, item)), item);
    }

    public static void registerItem(ResourceLocation location, Item item, JsonObject root) {
        Registry.register(BuiltInRegistries.ITEM, location, item);
        addedItems.add(Pair.of(item, root));
    }

    public static void register() {
        Map<ResourceKey<CreativeModeTab>, List<Item>> items = new HashMap<>();
        for (var addedItem : addedItems) {
            var json = addedItem.getSecond();
            var tab = json == null ? CreativeModeTabs.getDefaultTab()
                    : PropertiesReader.computeOrDefault(json, "creativeTab",
                    (m) -> BuiltInRegistries.CREATIVE_MODE_TAB.get(new ResourceLocation(m.getAsString())),
                    CreativeModeTabs.getDefaultTab()
            );

            var getKey = BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(tab).orElseThrow();
            items.computeIfAbsent(getKey, t -> new ArrayList<>()).add(addedItem.getFirst());
        }

        for (var entry : items.entrySet()) {
            ItemGroupEvents.modifyEntriesEvent(entry.getKey()).register(e -> {
                for (var item : entry.getValue()) {
                    e.accept(new ItemStack(item));
                }
            });
        }

    }
}
