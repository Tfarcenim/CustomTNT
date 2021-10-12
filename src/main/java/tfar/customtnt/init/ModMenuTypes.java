package tfar.customtnt.init;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent;
import tfar.customtnt.menu.VacuumTNTMenu;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

public class ModMenuTypes {

    private static List<ContainerType<?>> MOD_ENTITIES;

    public static final ContainerType<VacuumTNTMenu> VACUUM_TNT = new ContainerType<>(VacuumTNTMenu::new);

    public static void register(RegistryEvent.Register<ContainerType<?>> e) {
        for (Field field : ModMenuTypes.class.getFields()) {
            try {
                Object o = field.get(null);
                if (o instanceof ContainerType) {
                       e.getRegistry().register(((ContainerType<?>) o).setRegistryName(field.getName().toLowerCase(Locale.ROOT)));
                }
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        }
    }
}
