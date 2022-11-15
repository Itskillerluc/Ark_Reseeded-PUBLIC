package com.huskytacodile.alternacraft.entities;

import com.huskytacodile.alternacraft.data.AnimatedTexture;
import net.minecraft.resources.ResourceLocation;

public interface AnimatedTextureEntity {
    String getTextureId();
    ResourceLocation getFrame(float interval, AnimatedTexture texture);
}
