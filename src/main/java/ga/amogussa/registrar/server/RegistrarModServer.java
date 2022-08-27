package ga.amogussa.registrar.server;

import ga.amogussa.registrar.RegistrarMod;
import net.fabricmc.api.DedicatedServerModInitializer;

public class RegistrarModServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        RegistrarMod.init();
    }
}
