package tfar.customtnt.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tfar.customtnt.entity.TNTDroneEntity;
import tfar.customtnt.init.ModEntityTypes;

public class TNTDroneItem extends Item {

    public TNTDroneItem(Properties properties) {
        super(properties);
    }

    /**
     * Called when this item is used when targetting a Block
     */
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        ItemStack itemstack = context.getItem();
        if (!world.isRemote) {
            double d0 = 1;

            TNTDroneEntity abstractminecartentity = new TNTDroneEntity(ModEntityTypes.TNT_DRONE,world, (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.0625D + d0, (double)blockpos.getZ() + 0.5D);
            if (itemstack.hasDisplayName()) {
                abstractminecartentity.setCustomName(itemstack.getDisplayName());
            }

            world.addEntity(abstractminecartentity);
        }

        itemstack.shrink(1);
        return ActionResultType.func_233537_a_(world.isRemote);
    }
}
