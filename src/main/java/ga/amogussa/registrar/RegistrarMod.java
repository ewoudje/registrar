package ga.amogussa.registrar;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import ga.amogussa.registrar.data.BlockRegistrar;
import ga.amogussa.registrar.data.CreativeTabRegistrar;
import ga.amogussa.registrar.data.ItemRegistrar;
import ga.amogussa.registrar.data.PropertiesReader;
import ga.amogussa.registrar.data.Registrar;
import ga.amogussa.registrar.data.RegistrarPackSource;
import net.fabricmc.fabric.impl.resource.loader.ModResourcePackCreator;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.levelgen.Heightmap;
import org.slf4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RegistrarMod {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String ID = "registrar";

    public static final PackType REGISTRAR = PackType.valueOf("REGISTRAR");
    public static final List<Registrar> REGISTRARS = new ArrayList<>();
    public static final String registrarPacksFolder = "./registrarpacks";
    public static final RepositorySource REGISTRAR_SOURCE = new FolderRepositorySource(new File(registrarPacksFolder), RegistrarPackSource.INSTANCE);
    public static final PackRepository REGISTRAR_REPOSITORY = new PackRepository(REGISTRAR,
            new ServerPacksSource(),
            new ModResourcePackCreator(REGISTRAR)
    );

    public static boolean isClient = false;

    // Gets called from clienst and server initialization
    public static void init() {
        Gson gson = new Gson();

        // ORDER IS IMPORTANT!!
        REGISTRARS.add(new CreativeTabRegistrar());
        REGISTRARS.add(new BlockRegistrar());
        REGISTRARS.add(new ItemRegistrar());

        REGISTRAR_REPOSITORY.reload();

        REGISTRAR_REPOSITORY.setSelected(REGISTRAR_REPOSITORY.getAvailableIds());
        ResourceManager manager = new MultiPackResourceManager(REGISTRAR, REGISTRAR_REPOSITORY.openAllSelected());

        for (ga.amogussa.registrar.data.Registrar registrar : REGISTRARS) {
            for (ResourceLocation id : manager.listResources(registrar.getType(), path -> path.endsWith(".json"))) {
                try (InputStream stream = manager.getResource(id).getInputStream()) {
                    JsonObject root = JsonParser.parseReader(gson.newJsonReader(new InputStreamReader(stream))).getAsJsonObject();
                    String correctId = id.getPath().substring(registrar.getType().length() + 1, id.getPath().length() - ".json".length());

                    registrar.handle(new ResourceLocation(id.getNamespace(), correctId), root);
                } catch (Exception e) {
                    LOGGER.error("Error occurred while loading resource json " + id.toString(), e);
                }
            }
        }

        REGISTRAR_REPOSITORY.close();

        // Fixes zoglin bug
        try {
            SpawnRestrictionAccessor.callRegister(
                    EntityType.ZOGLIN,
                    SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Zoglin::checkMobSpawnRules);
        } catch (IllegalStateException e) { }
    }

    public static ResourceLocation id(String id) {
        return new ResourceLocation(ID, id);
    }

    public static void addCreativeTab(ResourceLocation id, CreativeModeTab tab) {
        PropertiesReader.addTab(id, tab);
    }
}
