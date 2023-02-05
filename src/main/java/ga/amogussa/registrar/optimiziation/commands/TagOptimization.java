package ga.amogussa.registrar.optimiziation.commands;

import com.mojang.datafixers.util.Pair;
import net.minecraft.commands.arguments.selector.options.EntitySelectorOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public class TagOptimization {
    private static final HashMap<String, Pair<TagEntityGetter, TagEntityGetter>> getters = new HashMap<>();

    public static EntitySelectorOptions.Modifier getModifier() {
        return (parser) -> {
            boolean bl = parser.shouldInvertValue();
            String string = parser.getReader().readUnquotedString();
            if ("".equals(string)) {
                parser.addPredicate((entity) -> entity.getTags().isEmpty() == bl);
            } else {
                EntityGetterUser parser2 = (EntityGetterUser) parser;
                if (parser2.hasSpecificEntityGetter()) {
                    parser.addPredicate((entity) -> entity.getTags().contains(string) == bl);
                } else {
                    // TODO bl
                    parser2.setSpecificEntityGetter(inverse(getEntityGetters(string), bl));
                }
            }
        };
    }

    private static Pair<TagEntityGetter, TagEntityGetter> getEntityGetters(String string) {
        return getters.computeIfAbsent(string, (s) -> new Pair<>(
                new TagEntityGetter(),
                new TagEntityGetter()
        ));
    }

    public static void tagAdded(Entity entity, String tag) {
        Pair<TagEntityGetter, TagEntityGetter> getter = getEntityGetters(tag);
        getter.getFirst().taggedEntities.add(entity);
        getter.getSecond().taggedEntities.remove(entity);
    }

    public static void tagRemoved(Entity entity, String tag) {
        Pair<TagEntityGetter, TagEntityGetter> getter = getEntityGetters(tag);
        getter.getFirst().taggedEntities.remove(entity);
        getter.getSecond().taggedEntities.add(entity);
    }

    public static void newEntity(Entity entity) {
        for (Pair<TagEntityGetter, TagEntityGetter> getter : getters.values()) {
            if (!getter.getFirst().taggedEntities.contains(entity))
                getter.getSecond().taggedEntities.add(entity);
        }
    }

    private static TagEntityGetter inverse(Pair<TagEntityGetter, TagEntityGetter> pair, boolean inverse) {
        if (inverse) {
            return pair.getSecond();
        } else {
            return pair.getFirst();
        }
    }

    private static class TagEntityGetter implements RegistrarEntityGetter {
        private ServerLevel level = null;
        public Set<Entity> taggedEntities = new HashSet<>();

        @Override
        public void fromLevel(ServerLevel level) {
            this.level = level;
        }

        @Override
        public <T extends Entity> List<T> getEntities(EntityTypeTest<Entity, T> entityTypeTest, Predicate<? super T> predicate) {
            return taggedEntities.stream()
                    .map(entityTypeTest::tryCast)
                    .filter(i -> i != null && i.level == level && predicate.test(i))
                    .toList();
        }

        @Override
        public List<Entity> getEntities(@Nullable Entity entity, AABB aabb, Predicate<? super Entity> predicate) {
            return taggedEntities.stream()
                    .filter(i -> i != null && entity != i && i.level == level && predicate.test(i))
                    .filter(i -> aabb.intersects(i.getBoundingBox()))
                    .toList();
        }

        @Override
        public <T extends Entity> List<T> getEntities(EntityTypeTest<Entity, T> entityTypeTest, AABB aabb, Predicate<? super T> predicate) {
            return taggedEntities.stream()
                    .map(entityTypeTest::tryCast)
                    .filter(i -> i != null && i.level == level && predicate.test(i))
                    .filter(i -> aabb.intersects(i.getBoundingBox()))
                    .toList();
        }

        @Override
        public List<? extends Player> players() {
            return level.players();
        }
    }
}
