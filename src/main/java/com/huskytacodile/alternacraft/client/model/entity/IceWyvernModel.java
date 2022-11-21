package com.huskytacodile.alternacraft.client.model.entity;

import com.huskytacodile.alternacraft.Alternacraft;
import com.huskytacodile.alternacraft.client.render.entity.FireWyvernRenderer;
import com.huskytacodile.alternacraft.client.render.entity.IceWyvernRenderer;
import com.huskytacodile.alternacraft.entities.wyverns.FireWyvernEntity;
import com.huskytacodile.alternacraft.entities.wyverns.IceWyvernEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class IceWyvernModel extends AnimatedGeoModel<IceWyvernEntity> {
    @Override
    public ResourceLocation getAnimationResource(IceWyvernEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "animations/wyvern.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(IceWyvernEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "geo/wyvern.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(IceWyvernEntity entity)    {
        return IceWyvernRenderer.LOCATION_BY_VARIANT.get(entity.getVariant());
    }
}
