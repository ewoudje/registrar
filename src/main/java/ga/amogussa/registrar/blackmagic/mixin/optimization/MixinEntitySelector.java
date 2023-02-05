package ga.amogussa.registrar.blackmagic.mixin.optimization;

import ga.amogussa.registrar.optimiziation.commands.EntityGetterUser;
import ga.amogussa.registrar.optimiziation.commands.RegistrarEntityGetter;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.function.Predicate;

@Mixin(EntitySelector.class)
public class MixinEntitySelector implements EntityGetterUser {
    @Unique
    private RegistrarEntityGetter specificEntityGetter = null;

    @Override
    public void setSpecificEntityGetter(RegistrarEntityGetter getter) {
        specificEntityGetter = getter;
    }

    @Override
    public boolean hasSpecificEntityGetter() {
        return specificEntityGetter != null;
    }

    @Redirect(method = "addEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;getEntities(Lnet/minecraft/world/level/entity/EntityTypeTest;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;"))
    <T extends Entity> List<T> getEntities1(ServerLevel instance, EntityTypeTest<Entity, T> entityTypeTest, AABB aabb, Predicate<T> predicate) {
        if (hasSpecificEntityGetter()) {
            specificEntityGetter.fromLevel(instance);
            return specificEntityGetter.getEntities(entityTypeTest, aabb, predicate);
        } else {
            return instance.getEntities(entityTypeTest, aabb, predicate);
        }
    }

    @Redirect(method = "addEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;getEntities(Lnet/minecraft/world/level/entity/EntityTypeTest;Ljava/util/function/Predicate;)Ljava/util/List;"))
    <T extends Entity> List<T> getEntities2(ServerLevel instance, EntityTypeTest<Entity, T> entityTypeTest, Predicate<T> predicate) {
        if (hasSpecificEntityGetter()) {
            specificEntityGetter.fromLevel(instance);
            return specificEntityGetter.getEntities(entityTypeTest, predicate);
        } else {
            return (List<T>) instance.getEntities(entityTypeTest, predicate);
        }
    }
}
