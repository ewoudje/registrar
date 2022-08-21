package ga.amogussa.registrar.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PropertiesReader {
    private static final Map<ResourceLocation, Material> materials = new HashMap<>();
    private static final Map<ResourceLocation, SoundType> soundTypes = new HashMap<>();
    private static final Map<ResourceLocation, CreativeModeTab> tabs = new HashMap<>();

    public static BlockBehaviour.Properties block(ResourceLocation owner, JsonObject root) {
        if (root == null) return BlockBehaviour.Properties.of(Material.STONE);

        Material material = computeOrDefault(root.get("material"),
                (m) -> materials.get(new ResourceLocation(m.getAsString())),
                Material.STONE);

        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of(material);

        properties.destroyTime(computeOrDefault(root.get("destroyTime"),
                JsonElement::getAsFloat,
                1f));

        properties.explosionResistance(computeOrDefault(root.get("explosionResistance"),
                JsonElement::getAsFloat,
                1f));

        if (computeOrDefault(root.get("noCollission"),
                JsonElement::getAsBoolean,
                false))
            properties.noCollission();

        if (computeOrDefault(root.get("noOcclusion"),
                JsonElement::getAsBoolean,
                false))
            properties.noOcclusion();

        if (computeOrDefault(root.get("noDrops"),
                JsonElement::getAsBoolean,
                false))
            properties.noDrops();

        if (computeOrDefault(root.get("requiresCorrectToolForDrops"),
                JsonElement::getAsBoolean,
                false))
            properties.requiresCorrectToolForDrops();

        properties.sound(computeOrDefault(root.get("soundType"),
                (m) -> soundTypes.get(new ResourceLocation(m.getAsString())),
                SoundType.STONE));

        return properties;
    }

    public static Item.Properties item(ResourceLocation owner, JsonObject item) {
        Item.Properties properties = new Item.Properties();
        if (item == null) return properties.tab(getDefaultTab());

        properties.stacksTo(computeOrDefault(item.get("stackSize"),
                JsonElement::getAsInt, 64));

        CreativeModeTab tab = computeOrDefault(item.get("creativeTab"),
                (m) -> tabs.get(new ResourceLocation(m.getAsString())),
                getDefaultTab());

        properties.rarity(computeOrDefault(item.get("rarity"),
                (m) -> Rarity.valueOf(m.getAsString()),
                Rarity.COMMON));

        properties.food(food(owner, item.getAsJsonObject("food")));

        if (tab != null)
            properties.tab(tab);

        return properties;
    }

    private static FoodProperties food(ResourceLocation owner, JsonObject food) {
        if (food == null) return null;

        FoodProperties.Builder builder = new FoodProperties.Builder();

        if (computeOrDefault(food.get("meat"),
                JsonElement::getAsBoolean,
                false))
            builder.meat();

        if (computeOrDefault(food.get("fast"),
                JsonElement::getAsBoolean,
                false))
            builder.fast();

        if (computeOrDefault(food.get("canAlwaysEat"),
                JsonElement::getAsBoolean,
                false))
            builder.alwaysEat();

        builder.nutrition(computeOrDefault(food.get("nutrition"),
                JsonElement::getAsInt,
                1));

        builder.saturationMod(computeOrDefault(food.get("saturationModifier"),
                JsonElement::getAsFloat,
                0.3f));

        return builder.build();
    }

    private static CreativeModeTab getDefaultTab() {
        return CreativeModeTab.TAB_MISC;
    }

    private static <T> T computeOrDefault(JsonElement element, Function<JsonElement, T> compute, T def) {
        if (element == null) return def;
        return compute.apply(element);
    }

    private static ResourceLocation mc(String name) {
        return new ResourceLocation("minecraft", name);
    }

    public static void addTab(ResourceLocation location, CreativeModeTab tab) {
        tabs.put(location, tab);
    }

    static {
        fillStaticMap(SoundType.class, soundTypes);
        fillStaticMap(Material.class, materials);
        fillStaticMap(CreativeModeTab.class, tabs);
    }

    private static <T> void fillStaticMap(Class<T> clazz, Map<ResourceLocation, T> map) {
        Arrays.stream(clazz.getDeclaredFields())
                .filter((f) -> Modifier.isStatic(f.getModifiers()))
                .forEach((f) -> {
                    try {
                        map.put(new ResourceLocation(f.getName().toLowerCase()), (T) f.get(null));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
    }
}
