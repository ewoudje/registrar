package ga.amogussa.datablocks.blackmagic.mixin;

import com.google.common.collect.ImmutableSet;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.Set;

@Mixin(PackRepository.class)
public class MixinPackRepository {

    @Shadow @Final private Set<RepositorySource> sources;

    @Redirect(
            method = "<init>(Lnet/minecraft/server/packs/repository/Pack$PackConstructor;[Lnet/minecraft/server/packs/repository/RepositorySource;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/google/common/collect/ImmutableSet;copyOf([Ljava/lang/Object;)Lcom/google/common/collect/ImmutableSet;"
            )
    )
    <E> ImmutableSet<E> init(E[] elements) {
        return (ImmutableSet<E>) ImmutableSet.builder()
                .add(new FolderRepositorySource(new File("./registrarpacks"), PackSource.DEFAULT))
                .add(elements)
                .build();
    }
}
