package com.huskytacodile.alternacraft.client.model.entity;

import com.huskytacodile.alternacraft.Alternacraft;
import com.huskytacodile.alternacraft.client.render.entity.AlioramusRenderer;
import com.huskytacodile.alternacraft.entities.AlioramusEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AlioramusModel extends AnimatedGeoModel<AlioramusEntity> {
    @Override
    public ResourceLocation getAnimationResource(AlioramusEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "animations/alioramus.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(AlioramusEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "geo/alioramus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AlioramusEntity entity)    {
        return AlioramusRenderer.LOCATION_BY_VARIANT.get(entity.getVariant());
    }
}
