package com.huskytacodile.alternacraft.client.model.entity;

import com.huskytacodile.alternacraft.Alternacraft;
import com.huskytacodile.alternacraft.client.render.entity.SimplifiedSpinoRenderer;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.semiaquatic.SimplifiedSpinoEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SimplifiedSpinoModel extends AnimatedGeoModel<SimplifiedSpinoEntity> {
    @Override
    public ResourceLocation getAnimationFileLocation(SimplifiedSpinoEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "animations/pce_spinosaurus.animation.json");
    }

    @Override
    public ResourceLocation getModelLocation(SimplifiedSpinoEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "geo/pce_spinosaurus.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(SimplifiedSpinoEntity entity) {
        return SimplifiedSpinoRenderer.LOCATION_BY_VARIANT.get(entity.getVariant());
    }
}
