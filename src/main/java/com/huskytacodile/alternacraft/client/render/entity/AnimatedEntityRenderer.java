package com.huskytacodile.alternacraft.client.render.entity;

import com.huskytacodile.alternacraft.Alternacraft;
import com.huskytacodile.alternacraft.data.AnimatedTexture;
import com.huskytacodile.alternacraft.data.AnimatedTextureLoader;
import com.huskytacodile.alternacraft.entities.AnimatedTextureEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public abstract class AnimatedEntityRenderer <T extends Entity & AnimatedTextureEntity> extends EntityRenderer<T> {
    private final int resolution;
    private final float interval;
    private float tick;
    AnimatedTexture texture;

    protected AnimatedEntityRenderer(EntityRendererProvider.Context pContext, int resolution, float interval) {
        super(pContext);
        this.resolution = resolution;
        this.interval = interval;
    }

    private void renderAnimation(PoseStack pMatrixStack, MultiBufferSource pBuffer, Entity pEntity) {

    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        texture = AnimatedTextureLoader.getTextures().get(new ResourceLocation(Alternacraft.MOD_ID, pEntity.getTextureId()));
        this.tick = Mth.lerp(pPartialTick, 0, 1/texture.interval());
        this.render
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        return Mth.lerp();
    }
}
