package com.huskytacodile.alternacraft.client.render.entity;

import com.huskytacodile.alternacraft.client.model.entity.BlueModel;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.medium.raptor.BlueEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class BlueRenderer extends GeoEntityRenderer<BlueEntity>
{
    public BlueRenderer(EntityRendererProvider.Context entityRendererProvider) {
        super(entityRendererProvider, new BlueModel());
        this.shadowRadius = 1.2F;
    }
    @Override
    public RenderType getRenderType(BlueEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource multiBufferSource, VertexConsumer vertexConsumer, int packedLightIn,
                                    ResourceLocation textureLocation)
    {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
    @Override
    protected float getDeathMaxRotation(BlueEntity entityLivingBaseIn){
        return 0.0F;
    }
}
