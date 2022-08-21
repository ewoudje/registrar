package ga.amogussa.datablocks.data;

import com.google.gson.JsonObject;
import ga.amogussa.datablocks.BlockHandler;
import ga.amogussa.datablocks.block.BasicBlock;
import ga.amogussa.datablocks.block.MyBushBlock;
import ga.amogussa.datablocks.block.MyFallingBlock;
import net.minecraft.resources.ResourceLocation;

public class BlockRegistrar implements Registrar {
    @Override
    public String getType() {
        return "block";
    }

    @Override
    public void handle(ResourceLocation id, JsonObject root) {
        if ("basic".equals(root.get("type").getAsString())) {
            BlockHandler.registerBlock(id, new BasicBlock(id, root), root);
        } else if ("falling".equals(root.get("type").getAsString())) {
            BlockHandler.registerBlock(id, new MyFallingBlock(id, root), root);
        } else if ("bush".equals(root.get("type").getAsString())) {
            BlockHandler.registerBlock(id, new MyBushBlock(id, root), root);
        }
    }
}
