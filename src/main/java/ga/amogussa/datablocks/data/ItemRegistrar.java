package ga.amogussa.datablocks.data;

import com.google.gson.JsonObject;
import ga.amogussa.datablocks.ItemHandler;
import ga.amogussa.datablocks.item.BasicItem;
import ga.amogussa.datablocks.item.LoredItem;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

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
