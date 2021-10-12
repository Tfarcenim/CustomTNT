package tfar.customtnt;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tfar.customtnt.data.ModDatagen;
import tfar.customtnt.init.*;
import tfar.customtnt.net.PacketHandler;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleType;

import java.util.ArrayList;
import java.util.List;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CustomTNT.MODID)
public class CustomTNT {
    // Directly reference a log4j logger.

    public static final String MODID = "customtnt";

    private static final Logger LOGGER = LogManager.getLogger();

    public CustomTNT() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(ModDatagen::start);
        bus.addListener(this::setup);

        bus.addGenericListener(Block.class, ModBlocks::register);
        bus.addGenericListener(Item.class, ModItems::register);
        bus.addGenericListener(TileEntityType.class, ModBlockEntityTypes::register);
        bus.addGenericListener(EntityType.class, ModEntityTypes::register);
        bus.addGenericListener(ContainerType.class, ModMenuTypes::register);

        MinecraftForge.EVENT_BUS.addListener(this::drops);

        if (FMLEnvironment.dist.isClient()) {
            bus.addListener(Client::setup);
            Client.addEvents();
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.registerPackets();
    }

    private void drops(LivingDropsEvent e) {
        ScaleData scaleData = ScaleType.BASE.getScaleData(e.getEntityLiving());
        List<ItemEntity> additionalDrops = new ArrayList<>();
        if (scaleData.getTargetScale() > 1) {
            for (int i = 0; i < 32;i++) {
                additionalDrops.addAll(e.getDrops());
            }
        }
        e.getDrops().addAll(additionalDrops);
    }
}
