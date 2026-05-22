package ga.amogussa.registrar.data;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.repository.PackSource;

public class RegistrarPackSource implements PackSource {

    public static final RegistrarPackSource INSTANCE = new RegistrarPackSource();

    @Override
    public Component decorate(Component packName) {
        return Component.translatable("pack.registrarName").withStyle(ChatFormatting.GRAY).append(packName);
    }

    @Override
    public boolean shouldAddAutomatically() {
        return true;
    }
}
