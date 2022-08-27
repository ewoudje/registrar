package ga.amogussa.registrar.client;

import ga.amogussa.registrar.BlockHandler;
import ga.amogussa.registrar.RegistrarMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class RegistrarModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RegistrarMod.isClient = true;
        BlockHandler.initRenderTypes();
        RegistrarMod.init();
    }
}
