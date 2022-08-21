package ga.amogussa.datablocks;


import ga.amogussa.datablocks.blackmagic.EnumAdder;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class EarlyRiser implements Runnable {
    @Override
    public void run() {
        // Adding a PackType
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();
        String dataTypePattern = remapper.mapClassName("intermediary", "net.minecraft.class_3264"); // PackType

        // The enumbuilder seems to be broken in the jar;
        // i just copy pasted the files from the library and modified them (when needed)
        new EnumAdder(dataTypePattern, String.class, com.mojang.bridge.game.PackType.class)
                .addEnum("REGISTRAR", "registrar", com.mojang.bridge.game.PackType.DATA)
                .build();
    }
}
