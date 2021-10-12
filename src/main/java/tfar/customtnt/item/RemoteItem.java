package tfar.customtnt.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import tfar.customtnt.Client;

public class RemoteItem extends Item {
    public RemoteItem(Properties properties) {
        super(properties);
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        if (worldIn.isRemote) {
            Client.handleClient();
        }

        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }
}