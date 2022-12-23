package com.huskytacodile.alternacraft.client.screen;

import com.huskytacodile.alternacraft.Alternacraft;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerInventoryScreen extends InventoryScreen {
    public static final ResourceLocation INVENTORY_LOCATION = new ResourceLocation(Alternacraft.MOD_ID, "textures/gui/playerinv.png");


    public PlayerInventoryScreen(Player pPlayer) {
        super(pPlayer);
        this.getRecipeBookComponent().updateScreenPosition(100, 100);
    }

    @Override
    protected void renderBg(@NotNull PoseStack pPoseStack, float pPartialTick, int pX, int pY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, INVENTORY_LOCATION);
        int i = this.leftPos;
        int j = this.topPos;
        this.imageWidth = 333;
        this.imageHeight = 240;
        this.blit(pPoseStack, i, j, 0, -640, this.imageWidth, this.imageHeight, 332, 352);
        this.minecraft.screen.resize(this.minecraft, this.minecraft.getWindow().getGuiScaledWidth(), this.minecraft.getWindow().getGuiScaledHeight());
    }
}
