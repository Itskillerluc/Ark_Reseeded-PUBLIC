package com.huskytacodile.alternacraft.client.model.entity;

import com.huskytacodile.alternacraft.Alternacraft;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.medium.raptor.BlueEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BlueModel extends AnimatedGeoModel<BlueEntity> {
    @Override
    public ResourceLocation getAnimationResource(BlueEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "animations/blue.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(BlueEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "geo/blue.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BlueEntity entity)    {
        return new ResourceLocation(Alternacraft.MOD_ID, "textures/entity/blue.png");
    }
}
