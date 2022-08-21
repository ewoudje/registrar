package ga.amogussa.registrar.blackmagic.mixin;

import com.google.common.collect.ImmutableSet;
import net.minecraft.server.packs.repository.PackRepository;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static ga.amogussa.registrar.RegistrarMod.REGISTRAR_SOURCE;

@Mixin(PackRepository.class)
public class MixinPackRepository {
    @Redirect(
            method = "<init>(Lnet/minecraft/server/packs/repository/Pack$PackConstructor;[Lnet/minecraft/server/packs/repository/RepositorySource;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/google/common/collect/ImmutableSet;copyOf([Ljava/lang/Object;)Lcom/google/common/collect/ImmutableSet;"
            )
    )
    <E> ImmutableSet<E> init(E[] elements) {
        return (ImmutableSet<E>) ImmutableSet.builder()
                .add(REGISTRAR_SOURCE)
                .add(elements)
                .build();
    }
}
