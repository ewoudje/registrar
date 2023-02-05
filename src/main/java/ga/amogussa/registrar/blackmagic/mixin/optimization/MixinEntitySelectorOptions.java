package ga.amogussa.registrar.blackmagic.mixin.optimization;

import ga.amogussa.registrar.optimiziation.commands.TagOptimization;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.commands.arguments.selector.options.EntitySelectorOptions;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Predicate;

@Mixin(EntitySelectorOptions.class)
public abstract class MixinEntitySelectorOptions {


    @Shadow
    private static void register(String string, EntitySelectorOptions.Modifier modifier, Predicate<EntitySelectorParser> predicate, Component component) {}

    @Redirect(method = "bootStrap", at = @At(value = "INVOKE", target = "Lnet/minecraft/commands/arguments/selector/options/EntitySelectorOptions;register(Ljava/lang/String;Lnet/minecraft/commands/arguments/selector/options/EntitySelectorOptions$Modifier;Ljava/util/function/Predicate;Lnet/minecraft/network/chat/Component;)V"))
    private static void redirectRegister(String string, EntitySelectorOptions.Modifier modifier, Predicate<EntitySelectorParser> predicate, Component component) {
        if ("tag".equals(string)) {
            register(string, TagOptimization.getModifier(), predicate, component);
        } else {
            register(string, modifier, predicate, component);
        }
    }
}
