package com.huskytacodile.alternacraft.client.render.entity;

import com.huskytacodile.alternacraft.entities.other.FireEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class FireRenderer <T extends FireEntity> extends EntityRenderer<T> {
    ItemRenderer renderer;
    public FireRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        renderer = pContext.getItemRenderer();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(T pEntity) {
        return new ResourceLocation("alternacraft:textures/entity/empty.png");
    }

    @Override
    public void render(@NotNull T pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.scale(1F, 1F, 1F);
        pPoseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        renderer.renderStatic(null, pEntity.getItem(), ItemTransforms.TransformType.NONE, false, pPoseStack, pBuffer, pEntity.getLevel(), pPackedLight, OverlayTexture.NO_OVERLAY, 1);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }
}
