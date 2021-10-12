package tfar.customtnt.net.server;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;
import tfar.customtnt.init.ModItems;

import java.util.UUID;
import java.util.function.Supplier;

public class C2SRotatePacket {

    float pitch;
    float yaw;

    public C2SRotatePacket() {
    }

    public C2SRotatePacket(float forward, float strafe) {
        pitch = forward;
        yaw = strafe;
    }

    public C2SRotatePacket(PacketBuffer buf) {
        pitch = buf.readFloat();
        yaw = buf.readFloat();
    }

    public void encode(PacketBuffer buf) {
        buf.writeFloat(pitch);
        buf.writeFloat(yaw);
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
                //client only!
                //entity.rotateTowards(pitch,0);

                double d0 = 0;
                double d1 = pitch * 0.15D;
                entity.rotationPitch = (float)((double)entity.rotationPitch + d0);
                entity.rotationYaw = (float)((double)entity.rotationYaw + d1);
                entity.rotationPitch = MathHelper.clamp(entity.rotationPitch, -180.0F, 180.0F);
           //     entity.prevRotationPitch = (float)((double)entity.prevRotationPitch + d0);
             //   entity.prevRotationYaw = (float)((double)entity.prevRotationYaw + d1);
              //  entity.prevRotationPitch = MathHelper.clamp(entity.prevRotationPitch, -180.0F, 180.0F);
            }
        });
    }
}
