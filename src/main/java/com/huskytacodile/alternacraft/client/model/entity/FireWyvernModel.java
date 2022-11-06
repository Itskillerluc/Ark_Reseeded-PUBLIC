package com.huskytacodile.alternacraft.client.model.entity;

import com.huskytacodile.alternacraft.Alternacraft;
import com.huskytacodile.alternacraft.client.render.entity.AcroRenderer;
import com.huskytacodile.alternacraft.client.render.entity.FireWyvernRenderer;
import com.huskytacodile.alternacraft.entities.wyverns.FireWyvernEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FireWyvernModel extends AnimatedGeoModel<FireWyvernEntity> {
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
