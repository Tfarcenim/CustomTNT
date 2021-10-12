package tfar.customtnt.menu;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import tfar.customtnt.blockentity.VacuumTNTBlockEntity;
import tfar.customtnt.init.ModMenuTypes;

public class VacuumTNTMenu extends Container {

    public VacuumTNTMenu(int inventory, PlayerInventory id) {
        this(inventory, id,new ItemStackHandler(VacuumTNTBlockEntity.SLOTS));
    }

    public VacuumTNTMenu(int id, PlayerInventory inventory, ItemStackHandler handler) {
        super(ModMenuTypes.VACUUM_TNT, id);

        for(int k = 0; k < 6; ++k) {
            for(int l = 0; l < 9; ++l) {
                this.addSlot(new SlotItemHandler(handler, l + k * 9, 8 + l * 18, 18 + k * 18));
            }
        }

        int i = (6 - 4) * 18;

        for(int j = 0; j < 6; ++j) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new SlotItemHandler(handler, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inventory, i1, 8 + i1 * 18, 161 + i));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
}
