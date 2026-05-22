package ga.amogussa.registrar.data;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class CreativeTabRegistrar implements ga.amogussa.registrar.data.Registrar {
    @Override
    public String getType() {
        return "creative_tab";
    }

    @Override
    public void handle(ResourceLocation id, JsonObject root) {
        ResourceLocation item = new ResourceLocation(root.get("item").getAsString());
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id, FabricItemGroup.builder()
                .icon(() -> new ItemStack(BuiltInRegistries.ITEM.get(item)))
                .title(Component.translatable("creative_tab." + id.getNamespace() + "." + id.getPath()))
                .build());
    }
}
