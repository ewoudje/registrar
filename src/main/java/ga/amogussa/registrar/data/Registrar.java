package ga.amogussa.registrar.data;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

public interface Registrar {

    String getType();

    void handle(ResourceLocation id, JsonObject root);

    default void setupTags() {
    }

}
