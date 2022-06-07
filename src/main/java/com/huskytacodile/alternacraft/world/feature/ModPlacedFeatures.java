package com.huskytacodile.alternacraft.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModPlacedFeatures {
    public static final Holder<PlacedFeature> TITANIUM_ORE_PLACED = PlacementUtils.register("titanium_ore_placed",
            ModConfiguredFeature.TITANIUM_ORE, ModOrePlacement.commonOrePlacement(8, // VeinsPerChunk
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));

    public static final Holder<PlacedFeature> PAINITE_ORE_PLACED = PlacementUtils.register("painite_ore_placed",
            ModConfiguredFeature.PAINITE_ORE, ModOrePlacement.commonOrePlacement(4, // VeinsPerChunk
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));

    public static final Holder<PlacedFeature> FOSSIL_ORE_PLACED = PlacementUtils.register("fossil_ore_placed",
            ModConfiguredFeature.FOSSIL_ORE, ModOrePlacement.commonOrePlacement(7, // VeinsPerChunk
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
}
