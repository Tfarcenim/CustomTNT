package tfar.customtnt.init;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import tfar.customtnt.blockentity.VacuumTNTBlockEntity;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

public class ModBlockEntityTypes {

    private static List<TileEntityType<?>> MOD_ENTITIES;

    public static final TileEntityType<VacuumTNTBlockEntity> VACUUM_TNT = TileEntityType.Builder.create(VacuumTNTBlockEntity::new, ModBlocks.VACUUM_TNT).build(null);

    public static void register(RegistryEvent.Register<TileEntityType<?>> e) {
        for (Field field : ModBlockEntityTypes.class.getFields()) {
            try {
                Object o = field.get(null);
                if (o instanceof TileEntityType) {
                       e.getRegistry().register(((TileEntityType<?>) o).setRegistryName(field.getName().toLowerCase(Locale.ROOT)));
                }
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        }
    }
}
