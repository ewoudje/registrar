package ga.amogussa.registrar.blackmagic.mixin.optimization;

import ga.amogussa.registrar.optimiziation.commands.EntityGetterUser;
import ga.amogussa.registrar.optimiziation.commands.RegistrarEntityGetter;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.world.level.EntityGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntitySelectorParser.class)
public class MixinEntitySelectorParser implements EntityGetterUser {
    @Unique
    private RegistrarEntityGetter specificEntityGetter = null;

    @Override
    public void setSpecificEntityGetter(RegistrarEntityGetter getter) {
        specificEntityGetter = getter;
    }

    @Override
    public int specificEntityGetterSize() {
        return specificEntityGetter != null ? specificEntityGetter.getSize() : Integer.MAX_VALUE;
    }

    @Inject(method = "getSelector", at = @At("RETURN"))
    void getSelector(CallbackInfoReturnable<EntitySelector> cir) {
        ((EntityGetterUser) cir.getReturnValue()).setSpecificEntityGetter(specificEntityGetter);
    }
}
