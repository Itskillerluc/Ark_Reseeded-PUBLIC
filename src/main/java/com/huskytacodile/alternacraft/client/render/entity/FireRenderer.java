package com.huskytacodile.alternacraft.client.render.entity;

import com.huskytacodile.alternacraft.entities.other.FireEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

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
        var vec = new Vector3f(0.0F, 1.0F, 0.0F);
        pPoseStack.mulPose(new Quaternionf(vec.x(), vec.y(), vec.z(), 180f));
        renderer.renderStatic(null, pEntity.getItem(), ItemTransforms.TransformType.NONE, false, pPoseStack, pBuffer, pEntity.getLevel(), pPackedLight, OverlayTexture.NO_OVERLAY, 1);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }
}
