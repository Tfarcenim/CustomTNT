package tfar.customtnt.data.data;

import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import tfar.customtnt.init.ModBlocks;

public class ModBlockLootTables extends BlockLootTables {

    @Override
    protected void addTables() {
        registerDropSelfLootTable(ModBlocks.MIRACLE_GROW_TNT);
        registerDropSelfLootTable(ModBlocks.VACUUM_TNT);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.getBlocks();
    }
}
