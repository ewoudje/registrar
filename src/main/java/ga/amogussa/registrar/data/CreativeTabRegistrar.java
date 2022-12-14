package ga.amogussa.registrar.data;

import com.google.gson.JsonObject;
import ga.amogussa.registrar.RegistrarMod;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.core.Registry;
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
        RegistrarMod.addCreativeTab(id, FabricItemGroupBuilder.build(id, () -> new ItemStack(Registry.ITEM.get(item))));
    }
}
