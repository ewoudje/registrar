package ga.amogussa.registrar.blackmagic.mixin.optimization;

import ga.amogussa.registrar.optimiziation.commands.TagOptimization;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.EntitySection;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntitySection.class)
public class MixinEntitySection {

    @Inject(method = "add", at = @At("HEAD"))
    void entityAdded(EntityAccess entityAccess, CallbackInfo ci) {
        TagOptimization.newEntity((Entity) entityAccess);
    }
}
