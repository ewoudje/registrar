package ga.amogussa.registrar.block;

import com.google.gson.JsonObject;
import ga.amogussa.registrar.data.PropertiesReader;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BasicBlock extends Block {
    private final VoxelShape shape;
    public BasicBlock(ResourceLocation owner, JsonObject root) {
        super(PropertiesReader.block(owner, root));

        if (root.has("collisionShape") && root.getAsJsonArray("collisionShape") != null) {
            var iter = root.getAsJsonArray("collisionShape").iterator();
            shape = Shapes.box(
                    iter.next().getAsDouble() / 16.0,
                    iter.next().getAsDouble() / 16.0,
                    iter.next().getAsDouble() / 16.0,
                    iter.next().getAsDouble() / 16.0,
                    iter.next().getAsDouble() / 16.0,
                    iter.next().getAsDouble() / 16.0
            );
        } else {
            shape = null;
        }
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        if (shape != null)
            return shape;
        else
            return super.getShape(blockState, blockGetter, blockPos, collisionContext);
    }
}
