package com.huskytacodile.alternacraft.entities;

import com.google.common.collect.ImmutableSet;
import com.huskytacodile.alternacraft.Alternacraft;
import com.huskytacodile.alternacraft.block.ModBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.InvocationTargetException;

public class ModVillagers {

    //Kaupen Villager

    public static final DeferredRegister<PoiType> POINT_OF_INTEREST_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Alternacraft.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, Alternacraft.MOD_ID);

    public static final RegistryObject<PoiType> KAUPEN_POI = POINT_OF_INTEREST_TYPES.register("kaupenjoe",
            () -> new PoiType("kaupenjoe", PoiType.getBlockStates(ModBlocks.DNA_EXTRACTOR.get()), 1, 1));
    public static final RegistryObject<VillagerProfession> KAUPEN = VILLAGER_PROFESSIONS.register("kaupenjoe",
            () -> new VillagerProfession("kaupenjoe", KAUPEN_POI.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_CARTOGRAPHER));
    public static void registerKaupenPOI() {
        try {
            ObfuscationReflectionHelper.findMethod(PoiType.class, "registerBlockStates", PoiType.class).invoke(null, KAUPEN_POI.get());
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    //Cylixr Villager
    public static final RegistryObject<PoiType> CYLIXR_POI = POINT_OF_INTEREST_TYPES.register("cylixr",
            () -> new PoiType("cylixr", PoiType.getBlockStates(ModBlocks.DNA_SEQUENCER.get()), 1, 1));
    public static final RegistryObject<VillagerProfession> CYLIXR = VILLAGER_PROFESSIONS.register("cylixr",
            () -> new VillagerProfession("cylixr", CYLIXR_POI.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_BUTCHER));
    public static void registerCylixrPOI() {
        try {
            ObfuscationReflectionHelper.findMethod(PoiType.class, "registerBlockStates", PoiType.class).invoke(null, CYLIXR_POI.get());
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //Husky Villager
    public static final RegistryObject<PoiType> HUSKY_POI = POINT_OF_INTEREST_TYPES.register("husky",
            () -> new PoiType("husky", PoiType.getBlockStates(ModBlocks.DNA_COMBINATOR.get()), 1, 1));
    public static final RegistryObject<VillagerProfession> HUSKY = VILLAGER_PROFESSIONS.register("husky",
            () -> new VillagerProfession("husky", HUSKY_POI.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_LEATHERWORKER));
    public static void registerHuskyPOI() {
        try {
            ObfuscationReflectionHelper.findMethod(PoiType.class, "registerBlockStates", PoiType.class).invoke(null, HUSKY_POI.get());
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

