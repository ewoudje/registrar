package ga.amogussa.registrar.blackmagic.mixin.optimization;

import ga.amogussa.registrar.optimiziation.commands.TagOptimization;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(Entity.class)
public class MixinEntity {

    @Inject(method = "addTag", at = @At("HEAD"))
    void tagAdded(String string, CallbackInfoReturnable<Boolean> cir) {
        TagOptimization.tagAdded((Entity) (Object) this, string);
    }

    @Inject(method = "removeTag", at = @At("HEAD"))
    void tagRemoved(String string, CallbackInfoReturnable<Boolean> cir) {
        TagOptimization.tagRemoved((Entity) (Object) this, string);
    }

    @Redirect(method = "load", at = @At(value = "INVOKE", target = "Ljava/util/Set;add(Ljava/lang/Object;)Z"))
    boolean tagAddedAtLoad(Set instance, Object e) {
        String tag = (String) e;
        TagOptimization.tagAdded((Entity) (Object) this, tag);
        return instance.add(e);
    }
}
