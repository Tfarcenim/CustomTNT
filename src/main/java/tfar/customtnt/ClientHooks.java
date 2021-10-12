package tfar.customtnt;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import tfar.customtnt.entity.TNTDroneEntity;

public class ClientHooks {

    public static boolean updateTNTLook(double d2, double v) {
        PlayerEntity player = Minecraft.getInstance().player;

        Minecraft mc = Minecraft.getInstance();
        if (player != null && mc.getRenderViewEntity() instanceof TNTDroneEntity) {
            Client.x += (float) d2;
            Client.y += (float) v;
            return true;
        }
        return false;
    }
}
