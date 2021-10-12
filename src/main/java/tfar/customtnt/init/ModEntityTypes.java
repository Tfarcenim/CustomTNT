package tfar.customtnt.init;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.TNTEntity;
import net.minecraftforge.event.RegistryEvent;
import tfar.customtnt.entity.MiracleGrowTNTEntity;
import tfar.customtnt.entity.TNTDroneEntity;
import tfar.customtnt.entity.VacuumTNTEntity;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

public class ModEntityTypes {

    private static List<EntityType<?>> MOD_ENTITIES;

    public static final EntityType<TNTDroneEntity> TNT_DRONE = EntityType.Builder.<TNTDroneEntity>create(TNTDroneEntity::new,EntityClassification.MISC).size(0.98F, 0.7F).trackingRange(8).build("tnt_drone");
    public static final EntityType<MiracleGrowTNTEntity> MIRACLE_GROW_TNT = EntityType.Builder.<MiracleGrowTNTEntity>create(MiracleGrowTNTEntity::new,EntityClassification.MISC).size(.98f,.98f).trackingRange(8).build("miracle_grow_tnt");
  //  public static final EntityType<VacuumTNTEntity> VACUUM_TNT = EntityType.Builder.<VacuumTNTEntity>create(VacuumTNTEntity::new, EntityClassification.MISC).size(.98f,.98f).trackingRange(8).build("miracle_grow_tnt");

    public static void register(RegistryEvent.Register<EntityType<?>> e) {
        for (Field field : ModEntityTypes.class.getFields()) {
            try {
                Object o = field.get(null);
                if (o instanceof EntityType) {
                       e.getRegistry().register(((EntityType<?>) o).setRegistryName(field.getName().toLowerCase(Locale.ROOT)));
                }
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        }
    }
}
