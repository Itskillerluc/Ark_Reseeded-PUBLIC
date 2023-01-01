package com.huskytacodile.alternacraft.client.render.entity;

import com.huskytacodile.alternacraft.entities.dinos.AlternaDinoEntity;
import com.huskytacodile.alternacraft.util.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public abstract class AlternaDinoRenderer <T extends AlternaDinoEntity & GeoAnimatable> extends GeoEntityRenderer<T> {
    public AlternaDinoRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> model) {
        super(renderManager, model);
    }

    @Override
    public void postRender(PoseStack poseStack, T animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.postRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        if (animatable.getKnockout() > 0) {
            poseStack.pushPose();
            poseStack.translate(0, 1.5, 0);
            poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation().rotateAxis(((float) (Math.PI)), new Vector3f(1, 0, 0)));

            if (entityRenderDispatcher.crosshairPickEntity == animatable) {
                float size = 0.01f;
                VertexConsumer builder = bufferSource.getBuffer(AlternaCraftRenderTypes.KNOCKOUT_TEXTURE_TYPE);
                Utils.draw(poseStack, 2, 88, 2, 64, size, 1, 1, packedLight, builder, 90, 104, 0);

                Utils.draw(poseStack, 8, 30, 81, 103, size, 1, 1 - (animatable.getHealth() / animatable.getMaxHealth()), packedLight, builder , 90, 104, 1);
                Utils.draw(poseStack, 34, 56, 81, 103, size, 1, animatable.getHealth() / animatable.getMaxHealth(), packedLight, builder , 90, 104, 0);

                poseStack.translate(0, 0.42, 0);
                Utils.draw(poseStack, 8, 82, 77, 81, size, 1f - (Math.min(animatable.getKnockout() / ((float) animatable.getTameTime()), 1)), 1, packedLight, builder, 90, 104, 1);
                Utils.draw(poseStack, 8, 82, 66, 70, size, Math.min(animatable.getKnockout() / ((float) animatable.getTameTime()), 1), 1, packedLight, builder, 90, 104, 0);
                poseStack.translate(0, -0.42, 0);

                poseStack.translate(0, 0.56, 0);
                Utils.draw(poseStack, 8, 82, 77, 81, size, (Math.min(animatable.getKnockout() / ((float) animatable.getTameTime()), 1)), 1, packedLight, builder, 90, 104, 1);
                Utils.draw(poseStack, 8, 82, 73, 77, size, 1f - Math.min(animatable.getKnockout() / ((float) animatable.getTameTime()), 1), 1, packedLight, builder, 90, 104, 0);
                poseStack.translate(0, -0.56, 0);
            }
            poseStack.popPose();
        }
    }


}
