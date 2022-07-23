package com.huskytacodile.alternacraft.util;

import com.huskytacodile.alternacraft.Alternacraft;
import net.minecraft.ChatFormatting;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.item.Rarity;

public enum EggTier {
    COMMON,
    UNCOMMON,
    RARE,
    VERY_RARE,
    EPIC,
    MYTHICALLY_RARE;

    public Rarity getRarity() {
        return switch (this) {
            case UNCOMMON -> Rarity.UNCOMMON;
            case RARE -> Rarity.RARE;
            case VERY_RARE -> Rarity.create("very_rare", ChatFormatting.GREEN);
            case EPIC -> Rarity.EPIC;
            case MYTHICALLY_RARE -> Rarity.create("mythically_rare", ChatFormatting.GOLD);
            default -> Rarity.COMMON;
        };
    }
}
