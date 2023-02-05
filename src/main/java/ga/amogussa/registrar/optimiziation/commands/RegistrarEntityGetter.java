package ga.amogussa.registrar.optimiziation.commands;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.EntityGetter;
import net.minecraft.world.level.entity.EntityTypeTest;

import java.util.List;
import java.util.function.Predicate;

public interface RegistrarEntityGetter extends EntityGetter {

    void fromLevel(ServerLevel level);

    <T extends Entity> List<T> getEntities(EntityTypeTest<Entity, T> entityTypeTest, Predicate<? super T> predicate);
}
