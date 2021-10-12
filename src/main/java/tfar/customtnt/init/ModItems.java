package tfar.customtnt.init;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import tfar.customtnt.item.RemoteItem;
import tfar.customtnt.item.TNTDroneItem;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

public class ModItems {

    private static List<Item> MOD_ITEMS;

    public static final Item TNT_DRONE = new TNTDroneItem(new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final Item REMOTE = new RemoteItem(new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final Item MIRACLE_GROW_TNT = new BlockItem(ModBlocks.MIRACLE_GROW_TNT,new Item.Properties().group(ItemGroup.DECORATIONS));
    public static final Item VACUUM_TNT = new BlockItem(ModBlocks.VACUUM_TNT,new Item.Properties().group(ItemGroup.DECORATIONS));

    public static void register(RegistryEvent.Register<Item> e) {
        for (Field field : ModItems.class.getFields()) {
            try {
                Object o = field.get(null);
                if (o instanceof Item) {
                    e.getRegistry().register(((Item) o).setRegistryName(field.getName().toLowerCase(Locale.ROOT)));
                }
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        }
    }
}
