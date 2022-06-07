package com.huskytacodile.alternacraft.client.render.entity;

import com.google.common.collect.Maps;
import com.huskytacodile.alternacraft.client.model.entity.IndominusModel;

import com.huskytacodile.alternacraft.entities.dinos.carnivore.large.hybrid.IndominusEntity;

import com.huskytacodile.alternacraft.entities.variant.QuintiVariant;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;
import java.util.Map;

public class IndominusRenderer extends GeoEntityRenderer<IndominusEntity>
{
    public IndominusRenderer(EntityRendererProvider.Context entityRendererProvider) {
        super(entityRendererProvider, new IndominusModel());
        this.shadowRadius = 4.0F;
    }
    public static final Map<QuintiVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(QuintiVariant.class), (p_114874_) -> {
                p_114874_.put(QuintiVariant.MALE, new ResourceLocation("alternacraft:textures/entity/indominus_male.png"));
                p_114874_.put(QuintiVariant.FEMALE, new ResourceLocation("alternacraft:textures/entity/indominus_female.png"));
                p_114874_.put(QuintiVariant.MALE2, new ResourceLocation("alternacraft:textures/entity/indominus_male2.png"));
                p_114874_.put(QuintiVariant.FEMALE2, new ResourceLocation("alternacraft:textures/entity/indominus_female2.png"));
                p_114874_.put(QuintiVariant.FEMALE3, new ResourceLocation("alternacraft:textures/entity/indominus_female3.png"));
            });
    @Override
    public ResourceLocation getTextureLocation(IndominusEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }
    @Override
    public RenderType getRenderType(IndominusEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource multiBufferSource, VertexConsumer vertexConsumer, int packedLightIn,
                                    ResourceLocation textureLocation)
    {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
    @Override
    protected float getDeathMaxRotation(IndominusEntity entityLivingBaseIn){
        return 0.0F;
    }
}
