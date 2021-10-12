package tfar.customtnt.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.RegistryEvent;
import tfar.customtnt.block.MiracleGrowTNTBlock;
import tfar.customtnt.block.VacuumTNTBlock;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ModBlocks {

    private static List<Block> MOD_BLOCKS = new ArrayList<>();

    public static final Block MIRACLE_GROW_TNT = new MiracleGrowTNTBlock(AbstractBlock.Properties.create(Material.TNT).zeroHardnessAndResistance().sound(SoundType.PLANT));
    public static final Block VACUUM_TNT = new VacuumTNTBlock(AbstractBlock.Properties.create(Material.TNT).zeroHardnessAndResistance().sound(SoundType.PLANT));

    public static void register(RegistryEvent.Register<Block> e) {
        for (Field field : ModBlocks.class.getFields()) {
            try {
                Object o = field.get(null);
                if (o instanceof Block) {
                       e.getRegistry().register(((Block) o).setRegistryName(field.getName().toLowerCase(Locale.ROOT)));
                }
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        }
    }

    public static List<Block> getBlocks() {
        if (MOD_BLOCKS.isEmpty()) {
            for (Field field : ModBlocks.class.getFields()) {
                try {
                    Object o = field.get(null);
                    if (o instanceof Block) {
                        MOD_BLOCKS.add((Block)o);
                    }
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }
            }
        }
        return MOD_BLOCKS;
    }
}
