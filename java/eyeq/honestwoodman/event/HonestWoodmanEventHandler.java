package eyeq.honestwoodman.event;

import eyeq.honestwoodman.HonestWoodman;
import eyeq.util.oredict.UOreDictionary;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HonestWoodmanEventHandler {
    @SubscribeEvent
    public void onEntityItemExpire(ItemExpireEvent event) {
        EntityItem entity = event.getEntityItem();
        World world = entity.getEntityWorld();
        if(world.isRemote) {
            return;
        }
        ItemStack itemStack = entity.getEntityItem();
        if(itemStack.isEmpty()) {
            return;
        }
        BlockPos pos = entity.getPosition();
        Block block = world.getBlockState(pos).getBlock();
        if(block == Blocks.WATER) {
            if(UOreDictionary.contains(itemStack, UOreDictionary.OREDICT_GOLD_INGOT)) {
                itemStack.shrink(1);
                world.setBlockState(pos, HonestWoodman.waterGold.getDefaultState());
            }
        }
    }
}
