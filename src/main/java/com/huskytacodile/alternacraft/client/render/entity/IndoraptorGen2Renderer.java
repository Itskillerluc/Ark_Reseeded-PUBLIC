package com.huskytacodile.alternacraft.client.render.entity;

import com.google.common.collect.Maps;
import com.huskytacodile.alternacraft.client.model.entity.IndoraptorGen2Model;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.large.hybrid.IndoraptorGen2Entity;
import com.huskytacodile.alternacraft.entities.variant.GenderVariant;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class IndoraptorGen2Renderer extends GeoEntityRenderer<IndoraptorGen2Entity>
{
    public IndoraptorGen2Renderer(EntityRendererProvider.Context entityRendererProvider) {
        super(entityRendererProvider, new IndoraptorGen2Model());
        this.shadowRadius = 4.0F;
    }
    public static final Map<GenderVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(GenderVariant.class), (p_114874_) -> {
                p_114874_.put(GenderVariant.MALE, new ResourceLocation("alternacraft:textures/entity/indoraptor_male.png"));
                p_114874_.put(GenderVariant.FEMALE, new ResourceLocation("alternacraft:textures/entity/indoraptor_female.png"));
            });
    @Override
    public ResourceLocation getTextureLocation(IndoraptorGen2Entity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public void render(IndoraptorGen2Entity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.2F, 0.2F, 0.2F);
            this.shadowRadius = 1.0F;
        } else {
            poseStack.scale(1.0F, 1.0F, 1.0F);
            this.shadowRadius = 4.0F;
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

    }

    @Override
    public RenderType getRenderType(IndoraptorGen2Entity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    protected float getDeathMaxRotation(IndoraptorGen2Entity entityLivingBaseIn){
        return 0.0F;
    }
}
