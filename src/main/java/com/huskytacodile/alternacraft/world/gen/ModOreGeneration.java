package com.huskytacodile.alternacraft.world.gen;

import com.huskytacodile.alternacraft.world.feature.ModPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.List;
import java.util.function.Supplier;


public class ModOreGeneration {
    public static void generateOres(final BiomeLoadingEvent event) {
        List<Holder<PlacedFeature>> base =
                event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);

        base.add(ModPlacedFeatures.TITANIUM_ORE_PLACED);
        base.add(ModPlacedFeatures.PAINITE_ORE_PLACED);
        base.add(ModPlacedFeatures.FOSSIL_ORE_PLACED);
    }
}

