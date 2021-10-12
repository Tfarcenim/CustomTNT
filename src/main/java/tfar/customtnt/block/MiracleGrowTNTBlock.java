package tfar.customtnt.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.TNTBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tfar.customtnt.entity.MiracleGrowTNTEntity;

import javax.annotation.Nullable;

public class MiracleGrowTNTBlock extends TNTBlock {
    public MiracleGrowTNTBlock(Properties properties) {
        super(properties);
    }


    @Override
    public void catchFire(BlockState state, World world, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        if (!world.isRemote) {
            MiracleGrowTNTEntity tntentity = new MiracleGrowTNTEntity(world, (double)pos.getX() + 0.5D, pos.getY(), (double)pos.getZ() + 0.5D, igniter);
            world.addEntity(tntentity);
            world.playSound(null, tntentity.getPosX(), tntentity.getPosY(), tntentity.getPosZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }
}
