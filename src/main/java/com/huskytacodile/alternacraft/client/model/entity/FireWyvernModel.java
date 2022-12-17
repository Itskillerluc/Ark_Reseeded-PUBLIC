package com.huskytacodile.alternacraft.client.model.entity;

import com.huskytacodile.alternacraft.Alternacraft;
import com.huskytacodile.alternacraft.client.render.entity.FireWyvernRenderer;
import com.huskytacodile.alternacraft.entities.wyverns.FireWyvernEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FireWyvernModel extends GeoModel<FireWyvernEntity> {
    @Override
    public ResourceLocation getAnimationResource(FireWyvernEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "animations/wyvern.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(FireWyvernEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "geo/wyvern.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FireWyvernEntity entity)    {
        return FireWyvernRenderer.LOCATION_BY_VARIANT.get(entity.getVariant());
    }
}
