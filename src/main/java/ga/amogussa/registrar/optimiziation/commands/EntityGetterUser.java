package ga.amogussa.registrar.optimiziation.commands;

import net.minecraft.world.level.EntityGetter;

public interface EntityGetterUser {
    void setSpecificEntityGetter(RegistrarEntityGetter getter);

    boolean hasSpecificEntityGetter();
}
