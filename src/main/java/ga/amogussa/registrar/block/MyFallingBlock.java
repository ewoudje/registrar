package ga.amogussa.registrar.block;

import com.google.gson.JsonObject;
import ga.amogussa.registrar.data.PropertiesReader;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MyFallingBlock extends FallingBlock {
    private final int dustColor;

    public MyFallingBlock(ResourceLocation owner, JsonObject root) {
        super(PropertiesReader.block(owner, root));

        if (root.get("dustColor") != null)
            this.dustColor = Integer.parseInt(root.get("dustColor").getAsString(), 16);
        else
            this.dustColor = 0xFFFFFF;
    }

    @Override
    public int getDustColor(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return dustColor;
    }
}
