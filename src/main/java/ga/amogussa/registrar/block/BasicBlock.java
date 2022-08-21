package ga.amogussa.registrar.block;

import com.google.gson.JsonObject;
import ga.amogussa.registrar.data.PropertiesReader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class BasicBlock extends Block {
    public BasicBlock(ResourceLocation owner, JsonObject root) {
        super(PropertiesReader.block(owner, root));
    }


}
