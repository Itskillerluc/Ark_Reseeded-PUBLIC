package com.huskytacodile.alternacraft.client.render.entity;

import com.google.common.collect.Maps;
import com.huskytacodile.alternacraft.client.model.entity.AcroModel;
import com.huskytacodile.alternacraft.client.model.entity.FireWyvernModel;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.large.AcroEntity;
import com.huskytacodile.alternacraft.entities.variant.TripleVariant;
import com.huskytacodile.alternacraft.entities.wyverns.FireWyvernEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;
import java.util.Map;

public class FireWyvernRenderer extends GeoEntityRenderer<FireWyvernEntity>
{
    public FireWyvernRenderer(EntityRendererProvider.Context entityRendererProvider) {
        super(entityRendererProvider, new FireWyvernModel());
        this.shadowRadius = 4.0F;
    }
    public static final Map<TripleVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(TripleVariant.class), (p_114874_) -> {
                p_114874_.put(TripleVariant.MALE, new ResourceLocation("alternacraft:textures/entity/fire_wyvern_male.png"));
                p_114874_.put(TripleVariant.FEMALE, new ResourceLocation("alternacraft:textures/entity/fire_wyvern_female.png"));
            });
    @Override
    public @NotNull ResourceLocation getTextureLocation(FireWyvernEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }
    @Override
    public RenderType getRenderType(FireWyvernEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource multiBufferSource, VertexConsumer vertexConsumer, int packedLightIn,
                                    ResourceLocation textureLocation)
    {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
    @Override
    protected float getDeathMaxRotation(FireWyvernEntity entityLivingBaseIn){
        return 0.0F;
    }
}
