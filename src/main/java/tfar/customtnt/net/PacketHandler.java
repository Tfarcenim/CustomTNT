package tfar.customtnt.net;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import tfar.customtnt.CustomTNT;
import tfar.customtnt.net.server.C2SDetonatePacket;
import tfar.customtnt.net.server.C2SRotatePacket;
import tfar.customtnt.net.server.C2SMovementPacket;

public class PacketHandler {

    public static SimpleChannel INSTANCE;
    static int i = 0;

    public static void registerPackets() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(CustomTNT.MODID, CustomTNT.MODID), () -> "1.0", s -> true, s -> true);

        INSTANCE.registerMessage(i++, C2SMovementPacket.class,
                C2SMovementPacket::encode,
                C2SMovementPacket::new,
                C2SMovementPacket::handle);

        INSTANCE.registerMessage(i++, C2SRotatePacket.class,
                C2SRotatePacket::encode,
                C2SRotatePacket::new,
                C2SRotatePacket::handle);

        INSTANCE.registerMessage(i++, C2SDetonatePacket.class,
                C2SDetonatePacket::encode,
                C2SDetonatePacket::new,
                C2SDetonatePacket::handle);
    }
}
