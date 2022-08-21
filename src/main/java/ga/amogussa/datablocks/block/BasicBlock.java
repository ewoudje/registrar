package ga.amogussa.datablocks.block;

import com.google.gson.JsonObject;
import ga.amogussa.datablocks.data.PropertiesReader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class BasicBlock extends Block {
    public BasicBlock(ResourceLocation owner, JsonObject root) {
        super(PropertiesReader.block(owner, root));
    }


}
