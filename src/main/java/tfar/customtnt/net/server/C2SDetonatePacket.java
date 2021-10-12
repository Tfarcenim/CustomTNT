package tfar.customtnt.net.server;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;
import tfar.customtnt.entity.TNTDroneEntity;
import tfar.customtnt.init.ModItems;

import java.util.UUID;
import java.util.function.Supplier;

public class C2SDetonatePacket {

    public C2SDetonatePacket() {
    }


    public C2SDetonatePacket(PacketBuffer buf) {
    }

    public void encode(PacketBuffer buf) {
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        PlayerEntity player = ctx.get().getSender();
        if (player == null) return;
        ctx.get().enqueueWork(() -> run(player));
        ctx.get().setPacketHandled(true);
    }

    public void run(PlayerEntity player) {

        MinecraftServer server = player.getServer();

        server.execute(() -> {
            ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
            if (stack.getItem() == ModItems.REMOTE) {
                UUID uuid = stack.getTag().getUniqueId("target");
                ServerWorld level = server.getWorld(RegistryKey.getOrCreateKey(Registry.WORLD_KEY,new ResourceLocation(stack.getTag().getString("level"))));
                Entity entity = level.getEntityByUuid(uuid);

                if (entity instanceof TNTDroneEntity) {
                    ((TNTDroneEntity)entity).explodeCart(0);
                }
            }
        });
    }
}
