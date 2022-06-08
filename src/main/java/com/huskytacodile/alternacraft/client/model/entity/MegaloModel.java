package com.huskytacodile.alternacraft.client.model.entity;

import com.huskytacodile.alternacraft.Alternacraft;
import com.huskytacodile.alternacraft.client.render.entity.MegaloRenderer;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.medium.MegaloEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MegaloModel extends AnimatedGeoModel<MegaloEntity> {
    @Override
    public ResourceLocation getAnimationResource(MegaloEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "animations/megalo.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(MegaloEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "geo/megalosaurus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MegaloEntity entity)    {
        return MegaloRenderer.LOCATION_BY_VARIANT.get(entity.getVariant());
    }
}
