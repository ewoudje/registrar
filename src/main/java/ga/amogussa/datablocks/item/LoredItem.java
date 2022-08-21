package ga.amogussa.datablocks.item;

import com.google.gson.JsonObject;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LoredItem extends BasicItem {

    private final List<Component> lore = new ArrayList<>();

    public LoredItem(ResourceLocation owner, JsonObject item) {
        super(owner, item);

        item.getAsJsonArray("lore").forEach(l -> lore.add(Component.Serializer.fromJson(l)));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.addAll(lore);
    }
}
