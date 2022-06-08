package com.huskytacodile.alternacraft.client.model.entity;

import com.huskytacodile.alternacraft.Alternacraft;
import com.huskytacodile.alternacraft.client.render.entity.IndoraptorRenderer;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.large.hybrid.IndoraptorEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class IndoraptorModel extends AnimatedGeoModel<IndoraptorEntity> {
    @Override
    public ResourceLocation getAnimationResource(IndoraptorEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "animations/indoraptor.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(IndoraptorEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "geo/indoraptor.geo.json");
    }
    @Override
    public ResourceLocation getTextureResource(IndoraptorEntity entity)    {
        return IndoraptorRenderer.LOCATION_BY_VARIANT.get(entity.getVariant());
    }
}
