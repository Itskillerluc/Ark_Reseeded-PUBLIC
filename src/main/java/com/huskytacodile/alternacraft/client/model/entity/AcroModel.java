package com.huskytacodile.alternacraft.client.model.entity;

import com.huskytacodile.alternacraft.Alternacraft;
import com.huskytacodile.alternacraft.client.render.entity.AcroRenderer;
import com.huskytacodile.alternacraft.client.render.entity.AllosaurusRenderer;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.large.AcroEntity;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.large.AllosaurusEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AcroModel extends AnimatedGeoModel<AcroEntity> {
    @Override
    public ResourceLocation getAnimationResource(AcroEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "animations/acrocanthosaurus.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(AcroEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "geo/acrocanthosaurus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AcroEntity entity)    {
        return AcroRenderer.LOCATION_BY_VARIANT.get(entity.getVariant());
    }
}
