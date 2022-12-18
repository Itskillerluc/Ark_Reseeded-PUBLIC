package com.huskytacodile.alternacraft.client.render.entity;

import com.google.common.collect.Maps;
import com.huskytacodile.alternacraft.client.model.entity.FireWyvernModel;
import com.huskytacodile.alternacraft.client.model.entity.IceWyvernModel;
import com.huskytacodile.alternacraft.entities.variant.GenderVariant;
import com.huskytacodile.alternacraft.entities.wyverns.FireWyvernEntity;
import com.huskytacodile.alternacraft.entities.wyverns.IceWyvernEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class IceWyvernRenderer extends GeoEntityRenderer<IceWyvernEntity>
{
    public IceWyvernRenderer(EntityRendererProvider.Context entityRendererProvider) {
        super(entityRendererProvider, new IceWyvernModel());
        this.shadowRadius = 4.0F;
    }
    public static final Map<GenderVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(GenderVariant.class), (p_114874_) -> {
                p_114874_.put(GenderVariant.MALE, new ResourceLocation("alternacraft:textures/entity/ice_wyvern_male.png"));
                p_114874_.put(GenderVariant.FEMALE, new ResourceLocation("alternacraft:textures/entity/ice_wyvern_female.png"));
            });
    @Override
    public @NotNull ResourceLocation getTextureLocation(IceWyvernEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public RenderType getRenderType(IceWyvernEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));

    }

    @Override
    protected float getDeathMaxRotation(IceWyvernEntity entityLivingBaseIn){
        return 0.0F;
    }
}
