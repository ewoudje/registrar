package ga.amogussa.registrar.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class PropertiesReader {
    private static final Map<ResourceLocation, SoundType> soundTypes = new HashMap<>();

    static {
        soundTypes.put(mc("wood"), SoundType.WOOD);
        soundTypes.put(mc("gravel"), SoundType.GRAVEL);
        soundTypes.put(mc("grass"), SoundType.GRASS);
        soundTypes.put(mc("lily_pad"), SoundType.LILY_PAD);
        soundTypes.put(mc("stone"), SoundType.STONE);
        soundTypes.put(mc("metal"), SoundType.METAL);
        soundTypes.put(mc("glass"), SoundType.GLASS);
        soundTypes.put(mc("wool"), SoundType.WOOL);
        soundTypes.put(mc("sand"), SoundType.SAND);
        soundTypes.put(mc("snow"), SoundType.SNOW);
        soundTypes.put(mc("powder_snow"), SoundType.POWDER_SNOW);
        soundTypes.put(mc("ladder"), SoundType.LADDER);
        soundTypes.put(mc("anvil"), SoundType.ANVIL);
        soundTypes.put(mc("slime_block"), SoundType.SLIME_BLOCK);
        soundTypes.put(mc("honey_block"), SoundType.HONEY_BLOCK);
        soundTypes.put(mc("wet_grass"), SoundType.WET_GRASS);
        soundTypes.put(mc("coral_block"), SoundType.CORAL_BLOCK);
        soundTypes.put(mc("bamboo"), SoundType.BAMBOO);
        soundTypes.put(mc("bamboo_sapling"), SoundType.BAMBOO_SAPLING);
        soundTypes.put(mc("scaffolding"), SoundType.SCAFFOLDING);
        soundTypes.put(mc("sweet_berry_bush"), SoundType.SWEET_BERRY_BUSH);
        soundTypes.put(mc("crop"), SoundType.CROP);
        soundTypes.put(mc("hard_crop"), SoundType.HARD_CROP);
        soundTypes.put(mc("vine"), SoundType.VINE);
        soundTypes.put(mc("nether_wart"), SoundType.NETHER_WART);
        soundTypes.put(mc("lantern"), SoundType.LANTERN);
        soundTypes.put(mc("stem"), SoundType.STEM);
        soundTypes.put(mc("nylium"), SoundType.NYLIUM);
        soundTypes.put(mc("fungus"), SoundType.FUNGUS);
        soundTypes.put(mc("roots"), SoundType.ROOTS);
        soundTypes.put(mc("shroomlight"), SoundType.SHROOMLIGHT);
        soundTypes.put(mc("weeping_vines"), SoundType.WEEPING_VINES);
        soundTypes.put(mc("twisting_vines"), SoundType.TWISTING_VINES);
        soundTypes.put(mc("soul_sand"), SoundType.SOUL_SAND);
        soundTypes.put(mc("soul_soil"), SoundType.SOUL_SOIL);
        soundTypes.put(mc("basalt"), SoundType.BASALT);
        soundTypes.put(mc("wart_block"), SoundType.WART_BLOCK);
        soundTypes.put(mc("netherrack"), SoundType.NETHERRACK);
        soundTypes.put(mc("nether_bricks"), SoundType.NETHER_BRICKS);
        soundTypes.put(mc("nether_sprouts"), SoundType.NETHER_SPROUTS);
        soundTypes.put(mc("nether_ore"), SoundType.NETHER_ORE);
        soundTypes.put(mc("bone_block"), SoundType.BONE_BLOCK);
        soundTypes.put(mc("netherite_block"), SoundType.NETHERITE_BLOCK);
        soundTypes.put(mc("ancient_debris"), SoundType.ANCIENT_DEBRIS);
        soundTypes.put(mc("lodestone"), SoundType.LODESTONE);
        soundTypes.put(mc("chain"), SoundType.CHAIN);
        soundTypes.put(mc("nether_gold_ore"), SoundType.NETHER_GOLD_ORE);
        soundTypes.put(mc("gilded_blackstone"), SoundType.GILDED_BLACKSTONE);
        soundTypes.put(mc("candle"), SoundType.CANDLE);
        soundTypes.put(mc("amethyst"), SoundType.AMETHYST);
        soundTypes.put(mc("amethyst_cluster"), SoundType.AMETHYST_CLUSTER);
        soundTypes.put(mc("small_amethyst_bud"), SoundType.SMALL_AMETHYST_BUD);
        soundTypes.put(mc("medium_amethyst_bud"), SoundType.MEDIUM_AMETHYST_BUD);
        soundTypes.put(mc("large_amethyst_bud"), SoundType.LARGE_AMETHYST_BUD);
        soundTypes.put(mc("tuff"), SoundType.TUFF);
        soundTypes.put(mc("calcite"), SoundType.CALCITE);
        soundTypes.put(mc("dripstone_block"), SoundType.DRIPSTONE_BLOCK);
        soundTypes.put(mc("pointed_dripstone"), SoundType.POINTED_DRIPSTONE);
        soundTypes.put(mc("copper"), SoundType.COPPER);
        soundTypes.put(mc("cave_vines"), SoundType.CAVE_VINES);
        soundTypes.put(mc("spore_blossom"), SoundType.SPORE_BLOSSOM);
        soundTypes.put(mc("azalea"), SoundType.AZALEA);
        soundTypes.put(mc("flowering_azalea"), SoundType.FLOWERING_AZALEA);
        soundTypes.put(mc("moss_carpet"), SoundType.MOSS_CARPET);
        soundTypes.put(mc("moss"), SoundType.MOSS);
        soundTypes.put(mc("big_dripleaf"), SoundType.BIG_DRIPLEAF);
        soundTypes.put(mc("small_dripleaf"), SoundType.SMALL_DRIPLEAF);
        soundTypes.put(mc("rooted_dirt"), SoundType.ROOTED_DIRT);
        soundTypes.put(mc("hanging_roots"), SoundType.HANGING_ROOTS);
        soundTypes.put(mc("azalea_leaves"), SoundType.AZALEA_LEAVES);
        soundTypes.put(mc("sculk_sensor"), SoundType.SCULK_SENSOR);
        soundTypes.put(mc("glow_lichen"), SoundType.GLOW_LICHEN);
        soundTypes.put(mc("deepslate"), SoundType.DEEPSLATE);
        soundTypes.put(mc("deepslate_bricks"), SoundType.DEEPSLATE_BRICKS);
        soundTypes.put(mc("deepslate_tiles"), SoundType.DEEPSLATE_TILES);
        soundTypes.put(mc("polished_deepslate"), SoundType.POLISHED_DEEPSLATE);
    }

    public static BlockBehaviour.Properties block(ResourceLocation owner, JsonObject root) {
        if (root == null) return BlockBehaviour.Properties.of();
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of();

        properties.destroyTime(computeOrDefault(root, "destroyTime",
                JsonElement::getAsFloat,
                1f));

        properties.explosionResistance(computeOrDefault(root, "explosionResistance",
                JsonElement::getAsFloat,
                1f));

        if (computeOrDefault(root, "noCollission",
                JsonElement::getAsBoolean,
                false))
            properties.noCollission();

        if (computeOrDefault(root, "noOcclusion",
                JsonElement::getAsBoolean,
                false))
            properties.noOcclusion();

        if (computeOrDefault(root, "noDrops",
                JsonElement::getAsBoolean,
                false))
            properties.noLootTable();
        else if (computeOrDefault(root, "noLootTable",
                JsonElement::getAsBoolean,
                false))
            properties.noLootTable();

        if (computeOrDefault(root, "requiresCorrectToolForDrops",
                JsonElement::getAsBoolean,
                false))
            properties.requiresCorrectToolForDrops();

        properties.sound(computeOrDefault(root, "soundType",
                (m) -> soundTypes.get(new ResourceLocation(m.getAsString())),
                SoundType.STONE));

        int lightLevel = computeOrDefault(root, "lightLevel",
                JsonElement::getAsInt,
                0);

        if (lightLevel != 0)
            properties.lightLevel((state) -> lightLevel);

        return properties;
    }

    public static Item.Properties item(ResourceLocation owner, JsonObject item) {
        Item.Properties properties = new Item.Properties();
        if (item == null) return properties;

        properties.stacksTo(computeOrDefault(item, "stackSize",
                JsonElement::getAsInt, 64));

        properties.rarity(computeOrDefault(item, "rarity",
                (m) -> Rarity.valueOf(m.getAsString()),
                Rarity.COMMON));

        properties.food(food(owner, item.getAsJsonObject("food")));
        return properties;
    }

    private static FoodProperties food(ResourceLocation owner, JsonObject food) {
        if (food == null) return null;

        FoodProperties.Builder builder = new FoodProperties.Builder();

        if (computeOrDefault(food, "meat",
                JsonElement::getAsBoolean,
                false))
            builder.meat();

        if (computeOrDefault(food, "fast",
                JsonElement::getAsBoolean,
                false))
            builder.fast();

        if (computeOrDefault(food, "canAlwaysEat",
                JsonElement::getAsBoolean,
                false))
            builder.alwaysEat();

        builder.nutrition(computeOrDefault(food, "nutrition",
                JsonElement::getAsInt,
                1));

        builder.saturationMod(computeOrDefault(food, "saturationModifier",
                JsonElement::getAsFloat,
                0.3f));

        return builder.build();
    }

    public static <T> T computeOrDefault(JsonObject root, String element, Function<JsonElement, T> compute, T def) {
        if (root.get(element) == null) return def;
        try {
            T result = compute.apply(root.get(element));

            if (result == null) throw new NullPointerException("Invalid input for " + element);

            return result;
        } catch (Exception e) {
            throw new RuntimeException("Invalid " + element + " for " + root.get(element), e);
        }
    }

    private static ResourceLocation mc(String name) {
        return new ResourceLocation("minecraft", name);
    }

    /* Used for generating the static below
    static {
        fillStaticMap(SoundType.class, "soundTypes");
        fillStaticMap(Material.class, "materials");
        fillStaticMap(CreativeModeTab.class, "tabs", (s) -> s.substring(4), (s) ->! s.equals("tabs"));
    }*/

    private static <T> void fillStaticMap(Class<T> clazz, String mapName) {
        fillStaticMap(clazz, mapName, (s) -> s, (s) -> true);
    }

    private static <T> void fillStaticMap(Class<T> clazz, String mapName, Function<String, String> name, Predicate<String> filter) {
        Arrays.stream(clazz.getDeclaredFields())
                .filter((f) -> Modifier.isStatic(f.getModifiers()))
                .filter((f) -> filter.test(f.getName()))
                .forEach((f) -> {
                    System.out.println(mapName + ".put(mc(\"" + name.apply(f.getName().toLowerCase()) + "\"), " + clazz.getSimpleName() + "." + f.getName() + ");");
                });
    }
}
