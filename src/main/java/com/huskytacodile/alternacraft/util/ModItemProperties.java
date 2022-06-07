package com.huskytacodile.alternacraft.util;

import com.huskytacodile.alternacraft.Alternacraft;
import com.huskytacodile.alternacraft.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;

import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.Map;

public class ModItemProperties {
    public static void makeBow(Item item) {
        ItemProperties.register(item, new ResourceLocation("pull"), (p_239429_0_, p_239429_1_, p_239429_2_,p_239429_3_) -> {
            if (p_239429_2_ == null) {
                return 0.0F;
            } else {
                return p_239429_2_.getUseItem() != p_239429_0_ ? 0.0F : (float)(p_239429_0_.getUseDuration() - p_239429_2_.getUseItemRemainingTicks()) / 5.0F;
            }
        });

        ItemProperties.register(item, new ResourceLocation("pulling"), (p_239428_0_, p_239428_1_, p_239428_2_,p_239429_3_) -> {
            return p_239428_2_ != null && p_239428_2_.isUsingItem() && p_239428_2_.getUseItem() == p_239428_0_ ? 1.0F : 0.0F;
        });
    }
}
