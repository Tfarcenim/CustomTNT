package tfar.customtnt.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.MinecartModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import tfar.customtnt.entity.TNTDroneEntity;

public class TntDroneRenderer extends EntityRenderer<TNTDroneEntity> {

    private static final ResourceLocation MINECART_TEXTURES = new ResourceLocation("textures/entity/minecart.png");
    protected final EntityModel<TNTDroneEntity> modelMinecart = new MinecartModel<>();

    public TntDroneRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowSize = 0.7F;
    }

    public void render(TNTDroneEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.push();

        float lerpPitch = MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch);
        float lerpYaw = MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw);

        matrixStackIn.translate(0.0D, 0.375D, 0.0D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90.0F - entityYaw));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(-lerpPitch));

        int j = entityIn.getDisplayTileOffset();
        BlockState blockstate = Blocks.TNT.getDefaultState();
        if (blockstate.getRenderType() != BlockRenderType.INVISIBLE) {
            matrixStackIn.push();
            float f4 = 0.75F;
            matrixStackIn.scale(f4, f4, f4);
            matrixStackIn.translate(-0.5D, (float)(j - 8) / 16.0F, 0.5D);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90.0F));
            this.renderBlockState(entityIn, partialTicks, blockstate, matrixStackIn, bufferIn, packedLightIn);
            matrixStackIn.pop();
        }

        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        this.modelMinecart.setRotationAngles(entityIn, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.modelMinecart.getRenderType(this.getEntityTexture(entityIn)));
        this.modelMinecart.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getEntityTexture(TNTDroneEntity entity) {
        return MINECART_TEXTURES;
    }

    protected void renderBlockState(TNTDroneEntity entityIn, float partialTicks, BlockState stateIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(stateIn, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY);
    }
}
