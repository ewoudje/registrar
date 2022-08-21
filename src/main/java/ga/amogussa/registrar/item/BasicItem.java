package ga.amogussa.registrar.item;

import com.google.gson.JsonObject;
import ga.amogussa.registrar.data.PropertiesReader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class BasicItem extends Item {
    public BasicItem(ResourceLocation owner, JsonObject item) {
        super(PropertiesReader.item(owner, item));
    }
}
