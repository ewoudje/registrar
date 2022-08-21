package ga.amogussa.datablocks.block;

import com.google.gson.JsonObject;
import ga.amogussa.datablocks.data.PropertiesReader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;

public class MyBushBlock extends BushBlock {
    public MyBushBlock(ResourceLocation owner, JsonObject root) {
        super(PropertiesReader.block(owner, root));
    }
}
