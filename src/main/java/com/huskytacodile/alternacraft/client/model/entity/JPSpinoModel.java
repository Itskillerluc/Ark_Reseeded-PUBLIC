package com.huskytacodile.alternacraft.client.model.entity;

import com.huskytacodile.alternacraft.Alternacraft;
import com.huskytacodile.alternacraft.client.render.entity.JPSpinoRenderer;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.semiaquatic.JPSpinoEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class JPSpinoModel extends AnimatedGeoModel<JPSpinoEntity> {
    @Override
    public ResourceLocation getAnimationResource(JPSpinoEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "animations/jpspino.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(JPSpinoEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "geo/jpspino.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(JPSpinoEntity entity)    {
        return JPSpinoRenderer.LOCATION_BY_VARIANT.get(entity.getVariant());
    }
}
