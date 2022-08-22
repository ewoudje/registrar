package ga.amogussa.registrar.blackmagic;

import ga.amogussa.registrar.blackmagic.manninghammills.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class EarlyRiser implements Runnable {
    @Override
    public void run() {
        // Adding a PackType
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();
        String dataTypePattern = remapper.mapClassName("intermediary", "net.minecraft.class_3264"); // PackType

        ClassTinkerers.enumBuilder(dataTypePattern, String.class, com.mojang.bridge.game.PackType.class)
                .addEnum("REGISTRAR", "registrar", com.mojang.bridge.game.PackType.DATA)
                .build();
    }
}
