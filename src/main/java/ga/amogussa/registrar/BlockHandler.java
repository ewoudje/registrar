package ga.amogussa.registrar;

import com.google.gson.JsonObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class BlockHandler {
    @Environment(EnvType.CLIENT)
    private static final Map<ResourceLocation, RenderType> layers = new HashMap<>();

    public static void registerBlock(ResourceLocation location, Block block, JsonObject root) {
        if (!root.has("noItem") || !root.get("noItem").getAsBoolean()) {
            ItemHandler.registerBlock(location, block, root.getAsJsonObject("item"));
        }

        if (root.has("layer") && RegistrarMod.isClient) {
            ResourceLocation layer = new ResourceLocation(root.get("layer").getAsString());
            BlockRenderLayerMap.INSTANCE.putBlock(block, layers.get(layer));
        }

        Registry.register(Registry.BLOCK, location, block);
    }

    @Environment(EnvType.CLIENT)
    public static void initRenderTypes() {
        layers.put(new ResourceLocation("minecraft:solid"), RenderType.solid());
        layers.put(new ResourceLocation("minecraft:cutout"), RenderType.cutout());
        layers.put(new ResourceLocation("minecraft:cutout_mipped"), RenderType.cutoutMipped());
        layers.put(new ResourceLocation("minecraft:translucent"), RenderType.translucent());
        layers.put(new ResourceLocation("minecraft:tripwire"), RenderType.tripwire());
    }
}
