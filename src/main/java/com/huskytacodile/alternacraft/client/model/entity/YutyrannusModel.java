package com.huskytacodile.alternacraft.client.model.entity;

import com.huskytacodile.alternacraft.Alternacraft;
import com.huskytacodile.alternacraft.client.render.entity.YutyrannusRenderer;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.large.YutyrannusEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class YutyrannusModel extends AnimatedGeoModel<YutyrannusEntity> {
    @Override
    public ResourceLocation getAnimationFileLocation(YutyrannusEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "animations/yutyrannus.animation.json");
    }

    @Override
    public ResourceLocation getModelLocation(YutyrannusEntity entity) {
        return new ResourceLocation(Alternacraft.MOD_ID, "geo/yutyrannus.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(YutyrannusEntity entity)    {
        return YutyrannusRenderer.LOCATION_BY_VARIANT.get(entity.getVariant());
    }
}
