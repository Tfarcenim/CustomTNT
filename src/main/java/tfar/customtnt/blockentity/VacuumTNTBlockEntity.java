package tfar.customtnt.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.items.ItemStackHandler;
import tfar.customtnt.CustomTNT;
import tfar.customtnt.block.VacuumTNTBlock;
import tfar.customtnt.init.ModBlockEntityTypes;
import tfar.customtnt.init.ModBlocks;
import tfar.customtnt.menu.VacuumTNTMenu;

import javax.annotation.Nullable;
import java.util.List;

public class VacuumTNTBlockEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    public static final int SLOTS = 54;

    public final ItemStackHandler handler = new ItemStackHandler(SLOTS);

    public VacuumTNTBlockEntity() {
        super(ModBlockEntityTypes.VACUUM_TNT);
    }

    public VacuumTNTBlockEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }


    @Override
    public void tick() {
        if (!world.isRemote && getBlockState().get(VacuumTNTBlock.ACTIVE)) {
            List<ItemEntity> nearby = world.getEntitiesWithinAABB(ItemEntity.class,new AxisAlignedBB(pos).grow(50));

            for (ItemEntity itemEntity : nearby) {
                Vector3d direction = Vector3d.copy(pos.subtract(itemEntity.getPosition())).normalize();
                itemEntity.setMotion(itemEntity.getMotion().add(direction.scale(.5)));
                itemEntity.velocityChanged = true;
            }
        }
    }

    public void onEntityCollision(Entity entity) {
        if (entity instanceof ItemEntity) {
            ItemStack stack = ((ItemEntity)entity).getItem();
            ItemStack returned = stack.copy();
            for (int i = 0; i < handler.getSlots();i++) {
                returned = handler.insertItem(i,returned,false);
                if (returned.isEmpty()) {
                    break;
                }
            }
            stack.setCount(returned.getCount());
        }
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        handler.deserializeNBT(nbt.getCompound("inv"));
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("inv",handler.serializeNBT());
        return super.write(compound);
    }

    @Override
    public ITextComponent getDisplayName() {
        return ModBlocks.VACUUM_TNT.getTranslatedName();
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new VacuumTNTMenu(p_createMenu_1_, p_createMenu_2_,handler);
    }
}
