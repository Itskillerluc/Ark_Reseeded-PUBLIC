package com.huskytacodile.alternacraft.client.render.entity;

import com.huskytacodile.alternacraft.entities.other.TranqDartEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class TranqDartRenderer extends ArrowRenderer<TranqDartEntity> {
    public static final ResourceLocation SPECTRAL_ARROW_LOCATION = new ResourceLocation("textures/entity/projectiles/spectral_arrow.png");


    public TranqDartRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(TranqDartEntity pEntity) {
        return SPECTRAL_ARROW_LOCATION;
    }
}
