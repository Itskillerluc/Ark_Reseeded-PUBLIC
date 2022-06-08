package com.huskytacodile.alternacraft.client.model.entity;

import com.huskytacodile.alternacraft.Alternacraft;
import com.huskytacodile.alternacraft.client.render.entity.AlternasaurusRenderer;
import com.huskytacodile.alternacraft.entities.AlternasaurusEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AlternasaurusModel extends AnimatedGeoModel<AlternasaurusEntity> {
    @Override
    public ResourceLocation getAnimationResource(AlternasaurusEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "animations/alternasaurus.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(AlternasaurusEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "geo/alternasaurus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AlternasaurusEntity entity)    {
        return AlternasaurusRenderer.LOCATION_BY_VARIANT.get(entity.getVariant());
    }
}
