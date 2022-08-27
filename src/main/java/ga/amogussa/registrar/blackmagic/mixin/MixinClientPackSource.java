package ga.amogussa.registrar.blackmagic.mixin;

import ga.amogussa.registrar.RegistrarMod;
import net.minecraft.client.resources.ClientPackSource;
import net.minecraft.server.packs.repository.Pack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ClientPackSource.class)
public class MixinClientPackSource {

    @Inject(method = "loadPacks", at = @At("RETURN"))
    private void addBuiltinResourcePacks(Consumer<Pack> consumer, Pack.PackConstructor factory, CallbackInfo ci) {
        RegistrarMod.REGISTRAR_REPOSITORY.getSelectedPacks().forEach(consumer);
    }
}
