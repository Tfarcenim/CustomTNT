package tfar.customtnt;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;
import tfar.customtnt.client.MiracleGrowTNTRenderer;
import tfar.customtnt.client.TntDroneRenderer;
import tfar.customtnt.client.VacuumTNTScreen;
import tfar.customtnt.entity.TNTDroneEntity;
import tfar.customtnt.init.ModEntityTypes;
import tfar.customtnt.init.ModMenuTypes;
import tfar.customtnt.net.PacketHandler;
import tfar.customtnt.net.server.C2SDetonatePacket;
import tfar.customtnt.net.server.C2SRotatePacket;
import tfar.customtnt.net.server.C2SMovementPacket;

public class Client {

    public static KeyBinding DETONATE = new KeyBinding("detonate_drone", GLFW.GLFW_KEY_I,CustomTNT.MODID);

    public static void addEvents() {
        MinecraftForge.EVENT_BUS.addListener(Client::movement);
        MinecraftForge.EVENT_BUS.addListener(Client::tick);
        MinecraftForge.EVENT_BUS.addListener(Client::key);
    }

    public static void setup(FMLClientSetupEvent e) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.TNT_DRONE, TntDroneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.MIRACLE_GROW_TNT, MiracleGrowTNTRenderer::new);
        ScreenManager.registerFactory(ModMenuTypes.VACUUM_TNT, VacuumTNTScreen::new);
        ClientRegistry.registerKeyBinding(DETONATE);
    }

    public static void key(InputEvent.KeyInputEvent e) {
        while (DETONATE.isPressed()) {
            PacketHandler.INSTANCE.sendToServer(new C2SDetonatePacket());
        }
    }

    private static void movement(InputUpdateEvent e) {
        Entity renderView = Minecraft.getInstance().renderViewEntity;
        if (renderView instanceof TNTDroneEntity) {
            MovementInput movementInput = e.getMovementInput();
            if (Math.abs(movementInput.moveForward) > 0) {
                PacketHandler.INSTANCE.sendToServer(new C2SMovementPacket(movementInput.moveForward,movementInput.moveStrafe));
            }
        }
    }

    public static float x;
    public static float y;

    public static void tick(TickEvent.ClientTickEvent e) {
        Entity renderView = Minecraft.getInstance().renderViewEntity;
        if (e.phase == TickEvent.Phase.START && renderView instanceof TNTDroneEntity) {
            if (!renderView.isAlive()) {
                Minecraft.getInstance().setRenderViewEntity(Minecraft.getInstance().player);
            } else {
                PacketHandler.INSTANCE.sendToServer(new C2SRotatePacket(x, y));
                x = y=0;
            }
        }
    }

    public static void handleClient() {
        if (Minecraft.getInstance().getRenderViewEntity() instanceof TNTDroneEntity) {
            Minecraft.getInstance().setRenderViewEntity(Minecraft.getInstance().player);
        }
    }
}
