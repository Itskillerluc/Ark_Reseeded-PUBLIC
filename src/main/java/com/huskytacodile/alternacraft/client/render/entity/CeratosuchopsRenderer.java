package com.huskytacodile.alternacraft.client.render.entity;


import com.google.common.collect.Maps;
import com.huskytacodile.alternacraft.client.model.entity.CeratosuchopsModel;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.semiaquatic.CeratosuchopsEntity;
import com.huskytacodile.alternacraft.entities.variant.GenderVariant;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class CeratosuchopsRenderer extends GeoEntityRenderer<CeratosuchopsEntity>
{
    public CeratosuchopsRenderer(EntityRendererProvider.Context entityRendererProvider) {
        super(entityRendererProvider, new CeratosuchopsModel());
        this.shadowRadius = 4.0F;
    }
    public static final Map<GenderVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(GenderVariant.class), (p_114874_) -> {
                p_114874_.put(GenderVariant.MALE, new ResourceLocation("alternacraft:textures/entity/ceratosuchops_male.png"));
                p_114874_.put(GenderVariant.FEMALE, new ResourceLocation("alternacraft:textures/entity/ceratosuchops_female.png"));
            });
    @Override
    public ResourceLocation getTextureLocation(CeratosuchopsEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public RenderType getRenderType(CeratosuchopsEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    protected float getDeathMaxRotation(CeratosuchopsEntity entityLivingBaseIn){
        return 0.0F;
    }
}
