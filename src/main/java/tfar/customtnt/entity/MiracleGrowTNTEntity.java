package tfar.customtnt.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import tfar.customtnt.init.ModEntityTypes;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleType;

import javax.annotation.Nullable;
import java.util.List;

public class MiracleGrowTNTEntity extends TNTEntity {

    public MiracleGrowTNTEntity(EntityType<? extends MiracleGrowTNTEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public MiracleGrowTNTEntity(World worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {
        this(ModEntityTypes.MIRACLE_GROW_TNT, worldIn);
        this.setPosition(x, y, z);
        double d0 = worldIn.rand.nextDouble() * (double)((float)Math.PI * 2F);
        this.setMotion(-Math.sin(d0) * 0.02D, 0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(80);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        //is this needed?
        //this.igniter = igniter;
    }


    @Override
    protected void explode() {
        float f2 = 8;//this.size * 2.0F;
        int k1 = MathHelper.floor(this.getPosX() - (double)f2 - 1.0D);
        int l1 = MathHelper.floor(this.getPosX() + (double)f2 + 1.0D);
        int i2 = MathHelper.floor(this.getPosY() - (double)f2 - 1.0D);
        int i1 = MathHelper.floor(this.getPosY() + (double)f2 + 1.0D);
        int j2 = MathHelper.floor(this.getPosZ() - (double)f2 - 1.0D);
        int j1 = MathHelper.floor(this.getPosZ() + (double)f2 + 1.0D);
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(k1, i2, j2, l1, i1, j1));

        for (Entity entity : list) {
            final ScaleData scaleData = ScaleType.BASE.getScaleData(entity);

            scaleData.setTargetScale(scaleData.getTargetScale() * 10);
        }

    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
