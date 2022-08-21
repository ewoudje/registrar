package ga.amogussa.datablocks.client;

import ga.amogussa.datablocks.BlockHandler;
import ga.amogussa.datablocks.DataBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class DataBlocksClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        DataBlocks.isClient = true;
        BlockHandler.initRenderTypes();
    }
}
