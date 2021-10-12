package tfar.customtnt.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import tfar.customtnt.init.ModItems;

public class TNTDroneEntity extends Entity {
    private static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(TNTDroneEntity.class, DataSerializers.FLOAT);

    public TNTDroneEntity(EntityType<?> type, World worldIn) {
        super(type, worldIn);
        this.preventEntitySpawning = true;
    }

    public TNTDroneEntity(EntityType<?> type, World worldIn, double posX, double posY, double posZ) {
        this(type, worldIn);
        this.setPosition(posX, posY, posZ);
        this.setMotion(Vector3d.ZERO);
        this.prevPosX = posX;
        this.prevPosY = posY;
        this.prevPosZ = posZ;
    }


    protected boolean canTriggerWalking() {
        return false;
    }

    protected void registerData() {
        this.dataManager.register(DAMAGE, 0.0F);
    }

    public boolean canCollide(Entity entity) {
        return BoatEntity.func_242378_a(this, entity);
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean canBePushed() {
        return true;
    }

    protected Vector3d func_241839_a(Direction.Axis axis, TeleportationRepositioner.Result result) {
        return LivingEntity.func_242288_h(super.func_241839_a(axis, result));
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    /**
     * Makes the minecart explode.
     */
    public void explodeCart(double radiusModifier) {
        if (!this.world.isRemote) {
            double d0 = Math.sqrt(radiusModifier);
            if (d0 > 5.0D) {
                d0 = 5.0D;
            }

            this.world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), (float)(4.0D + this.rand.nextDouble() * 1.5D * d0), Explosion.Mode.BREAK);
            this.remove();
        }
    }

    public void killMinecart(DamageSource source) {
        this.remove();
        if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            ItemStack itemstack = new ItemStack(Items.MINECART);
            if (this.hasCustomName()) {
                itemstack.setDisplayName(this.getCustomName());
            }

            this.entityDropItem(itemstack);
        }

    }


    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith() {
        return !this.removed;
    }

    /**
     * Gets the horizontal facing direction of this Entity, adjusted to take specially-treated entity types into account.
     */
    public Direction getAdjustedHorizontalFacing() {
        return this.getHorizontalFacing().rotateY();
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {

        if (this.getPosY() < -64.0D) {
            this.outOfWorld();
        }

        if (!this.hasNoGravity()) {
            this.setMotion(this.getMotion().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getMotion());
        this.setMotion(this.getMotion().scale(0.98D));
        if (this.onGround) {
            this.setMotion(this.getMotion().mul(0.7D, -0.5D, 0.7D));
        }

        this.func_233566_aG_();
        if (this.world.isRemote) {
       //     this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY() + 0.5D, this.getPosZ(), 0.0D, 0.0D, 0.0D);
        }

    }

    /**
     * Called every tick the minecart is on an activator rail.
     */
    public void onActivatorRailPass(int x, int y, int z, boolean receivingPower) {
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readAdditional(CompoundNBT compound) {

    }

    protected void writeAdditional(CompoundNBT compound) {
    }

    /**
     * Applies a velocity to the entities, to push them away from eachother.
     */
    public void applyEntityCollision(Entity entityIn) {
        super.applyEntityCollision(entityIn);
    }

    public int getDisplayTileOffset() {
        return 6;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (!this.world.isRemote && !this.removed) {
//            this.setForwardDirection(-this.getForwardDirection());
 //           this.setTimeSinceHit(10);
            this.setDamageTaken(this.getDamageTaken() + amount * 10.0F);
            this.markVelocityChanged();
            boolean flag = source.getTrueSource() instanceof PlayerEntity && ((PlayerEntity)source.getTrueSource()).abilities.isCreativeMode;
            if (flag || this.getDamageTaken() > 40.0F) {
                if (!flag && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                    this.entityDropItem(new ItemStack(ModItems.TNT_DRONE));
                }

                this.remove();
            }

            return true;
        } else {
            return true;
        }
    }


    /**
     * Sets the damage taken from the last hit.
     */
    public void setDamageTaken(float damageTaken) {
        this.dataManager.set(DAMAGE, damageTaken);
    }

    /**
     * Gets the damage taken from the last hit.
     */
    public float getDamageTaken() {
        return this.dataManager.get(DAMAGE);
    }




    @Override
    public ActionResultType processInitialInteract(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (stack.getItem() == ModItems.REMOTE) {
            if (player.world.isRemote) {
                Entity renderView = Minecraft.getInstance().renderViewEntity;
                if (renderView instanceof PlayerEntity) {
                    Minecraft.getInstance().setRenderViewEntity(this);
                }
            } else {
                stack.getOrCreateTag().putUniqueId("target",this.getUniqueID());
                stack.getTag().putString("level",this.world.getDimensionKey().getLocation().toString());
            }
        }
        return ActionResultType.SUCCESS;
    }
}

