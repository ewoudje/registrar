package ga.amogussa.registrar.block;

import com.google.gson.JsonObject;
import ga.amogussa.registrar.data.PropertiesReader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.BushBlock;

public class MyBushBlock extends BushBlock {
    public MyBushBlock(ResourceLocation owner, JsonObject root) {
        super(PropertiesReader.block(owner, root));
    }
}
