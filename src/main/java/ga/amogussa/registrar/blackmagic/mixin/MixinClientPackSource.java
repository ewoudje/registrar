package ga.amogussa.registrar.blackmagic.mixin;

import ga.amogussa.registrar.RegistrarMod;
import net.minecraft.client.resources.ClientPackSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.server.packs.repository.BuiltInPackSource;
import net.minecraft.server.packs.repository.Pack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ClientPackSource.class)
public abstract class MixinClientPackSource extends BuiltInPackSource {

    public MixinClientPackSource(PackType packType, VanillaPackResources vanillaPackResources, ResourceLocation resourceLocation) {
        super(packType, vanillaPackResources, resourceLocation);
        throw new RuntimeException();
    }

    @Override
    public void loadPacks(Consumer<Pack> consumer) {
        super.loadPacks(consumer);
        RegistrarMod.REGISTRAR_SOURCE.loadPacks(consumer);
    }
}
