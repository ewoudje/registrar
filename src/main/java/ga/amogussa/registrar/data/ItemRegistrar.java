package ga.amogussa.registrar.data;

import com.google.gson.JsonObject;
import ga.amogussa.registrar.ItemHandler;
import ga.amogussa.registrar.item.BasicItem;
import ga.amogussa.registrar.item.LoredItem;
import net.minecraft.resources.ResourceLocation;

public class ItemRegistrar implements Registrar {
    @Override
    public String getType() {
        return "item";
    }

    @Override
    public void handle(ResourceLocation id, JsonObject root) {
        if ("basic".equals(root.get("type").getAsString())) {
            ItemHandler.registerItem(id, new BasicItem(id, root), root);
        } else if ("lored".equals(root.get("type").getAsString())) {
            ItemHandler.registerItem(id, new LoredItem(id, root), root);
        }
    }
}
