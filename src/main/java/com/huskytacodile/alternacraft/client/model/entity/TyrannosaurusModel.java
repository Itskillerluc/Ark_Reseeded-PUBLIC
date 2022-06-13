package com.huskytacodile.alternacraft.client.model.entity;

import com.huskytacodile.alternacraft.Alternacraft;
import com.huskytacodile.alternacraft.client.render.entity.TyrannosaurusRenderer;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.large.TyrannosaurusEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TyrannosaurusModel extends AnimatedGeoModel<TyrannosaurusEntity> {
    @Override
    public ResourceLocation getAnimationResource(TyrannosaurusEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "animations/tyrannosaur.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(TyrannosaurusEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "geo/tyrannosaurus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TyrannosaurusEntity entity)    {
        return TyrannosaurusRenderer.LOCATION_BY_VARIANT.get(entity.getVariant());
    }
}
