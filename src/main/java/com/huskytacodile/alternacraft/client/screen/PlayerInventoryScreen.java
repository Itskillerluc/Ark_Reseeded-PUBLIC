package com.huskytacodile.alternacraft.client.screen;

import com.huskytacodile.alternacraft.Alternacraft;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class PlayerInventoryScreen extends InventoryScreen {
    public static final ResourceLocation INVENTORY_LOCATION = new ResourceLocation(Alternacraft.MOD_ID, "textures/gui/playerinv.png");


    public PlayerInventoryScreen(Player pPlayer) {
        super(pPlayer);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pX, int pY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, INVENTORY_LOCATION);
        int i = this.leftPos;
        int j = this.topPos;
        this.imageWidth = 255;
        this.imageHeight = 200;
        this.blit(pPoseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }
}
