package eyeq.honestwoodman.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStaticLiquidGold extends BlockStaticLiquid {
    public BlockStaticLiquidGold(Material material) {
        super(material);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {}

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        super.onEntityCollidedWithBlock(world, pos, state, entity);
        if(world.isRemote) {
            return;
        }
        if(!(entity instanceof EntityItem)) {
            return;
        }
        ItemStack itemStack = ((EntityItem) entity).getEntityItem();
        Item item = itemStack.getItem();
        Item newItem;
        if(item instanceof ItemAxe) {
            newItem = Items.GOLDEN_AXE;
        } else if(item instanceof ItemPickaxe) {
            newItem = Items.GOLDEN_PICKAXE;
        } else if(item instanceof ItemSpade) {
            newItem = Items.GOLDEN_SHOVEL;
        } else if(item instanceof ItemHoe) {
            newItem = Items.GOLDEN_HOE;
        } else if(item instanceof ItemSword) {
            newItem = Items.GOLDEN_SWORD;
        } else {
            return;
        }
        if(newItem != item) {
            ((EntityItem) entity).setEntityItemStack(new ItemStack(newItem, itemStack.getCount()));
        }
    }
}
