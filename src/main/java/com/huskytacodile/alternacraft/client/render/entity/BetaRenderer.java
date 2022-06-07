package com.huskytacodile.alternacraft.client.render.entity;

import com.huskytacodile.alternacraft.client.model.entity.BetaModel;
import com.huskytacodile.alternacraft.entities.BetaEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class BetaRenderer extends GeoEntityRenderer<BetaEntity>
{
    public BetaRenderer(EntityRendererProvider.Context entityRendererProvider) {
        super(entityRendererProvider, new BetaModel());
        this.shadowRadius = 0.4F;
    }
    @Override
    public RenderType getRenderType(BetaEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource multiBufferSource, VertexConsumer vertexConsumer, int packedLightIn,
                                    ResourceLocation textureLocation)
    {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
    @Override
    protected float getDeathMaxRotation(BetaEntity entityLivingBaseIn){
        return 0.0F;
    }
}
