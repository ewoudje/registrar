package ga.amogussa.datablocks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import ga.amogussa.datablocks.data.BlockRegistrar;
import ga.amogussa.datablocks.data.CreativeTabRegistrar;
import ga.amogussa.datablocks.data.ItemRegistrar;
import ga.amogussa.datablocks.data.PropertiesReader;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.impl.resource.loader.ModResourcePackCreator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.CreativeModeTab;
import org.slf4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RegistrarMod implements ModInitializer {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final String ID = "registrar";
    public static final PackType REGISTRAR = PackType.valueOf("REGISTRAR");
    public static final List<ga.amogussa.datablocks.data.Registrar> REGISTRARS = new ArrayList<>();
    public static boolean isClient = false;

    @Override
    public void onInitialize() {
        Gson gson = new Gson();

        // ORDER IS IMPORTANT!!
        REGISTRARS.add(new CreativeTabRegistrar());
        REGISTRARS.add(new BlockRegistrar());
        REGISTRARS.add(new ItemRegistrar());

        PackRepository repository = new PackRepository(REGISTRAR,
                new ServerPacksSource(),
                new ModResourcePackCreator(REGISTRAR)
        );
        repository.reload();

        repository.setSelected(repository.getAvailableIds());
        ResourceManager manager = new MultiPackResourceManager(REGISTRAR, repository.openAllSelected());

        for (ga.amogussa.datablocks.data.Registrar registrar : REGISTRARS) {
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

        repository.close();
    }

    public static ResourceLocation id(String id) {
        return new ResourceLocation(ID, id);
    }
    public static void addCreativeTab(ResourceLocation id, CreativeModeTab tab) {
        PropertiesReader.addTab(id, tab);
    }
}
