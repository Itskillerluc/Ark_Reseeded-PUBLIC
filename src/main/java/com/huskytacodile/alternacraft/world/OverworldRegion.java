package com.huskytacodile.alternacraft.world;

import com.huskytacodile.alternacraft.Alternacraft;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.levelgen.GenerationStep;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class OverworldRegion extends Region {
    public static final ResourceLocation LOCATION = new ResourceLocation(Alternacraft.MOD_ID, "overworld_primary");

    public OverworldRegion(int weight) {
        super(LOCATION, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        Biome biome = registry.get(new ResourceLocation(Alternacraft.MOD_ID, "trench"));
        addBiome(mapper,
                ParameterUtils.Temperature.NEUTRAL,
                ParameterUtils.Humidity.DRY,
                ParameterUtils.Continentalness.FAR_INLAND,
                ParameterUtils.Erosion.EROSION_3,
                ParameterUtils.Weirdness.LOW_SLICE_VARIANT_ASCENDING,
                ParameterUtils.Depth.UNDERGROUND,
                0, registry.getResourceKey(biome).get());
    }
}
