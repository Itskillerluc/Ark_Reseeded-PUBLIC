package com.huskytacodile.alternacraft.client.render.entity;

import com.google.common.collect.Maps;
import com.huskytacodile.alternacraft.client.model.entity.SimplifiedSpinoModel;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.semiaquatic.SimplifiedSpinoEntity;
import com.huskytacodile.alternacraft.entities.variant.GenderVariant;
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

public class SimplifiedSpinoRenderer extends GeoEntityRenderer<SimplifiedSpinoEntity>
{
    public SimplifiedSpinoRenderer(EntityRendererProvider.Context entityRendererProvider) {
        super(entityRendererProvider, new SimplifiedSpinoModel());
        this.shadowRadius = 4.0F;
    }
    public static final Map<GenderVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(GenderVariant.class), (p_114874_) -> {
                p_114874_.put(GenderVariant.MALE, new ResourceLocation("alternacraft:textures/entity/pce_male_spinosaurus.png"));
                p_114874_.put(GenderVariant.FEMALE, new ResourceLocation("alternacraft:textures/entity/pce_female_spinosaurus.png"));
            });
    @Override
    public ResourceLocation getTextureLocation(SimplifiedSpinoEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public RenderType getRenderType(SimplifiedSpinoEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource multiBufferSource, VertexConsumer vertexConsumer, int packedLightIn,
                                    ResourceLocation textureLocation)
    {
        if(animatable.isBaby()) {
            stack.scale(0.4F, 0.4F, 0.4F);
        } else {
            stack.scale(0.8F, 0.8F, 0.8F);
        }
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
    @Override
    protected float getDeathMaxRotation(SimplifiedSpinoEntity entityLivingBaseIn){
        return 0.0F;
    }
}
