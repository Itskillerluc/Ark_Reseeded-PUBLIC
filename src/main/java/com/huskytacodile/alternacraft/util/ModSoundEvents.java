package com.huskytacodile.alternacraft.util;

import com.huskytacodile.alternacraft.Alternacraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Alternacraft.MOD_ID);

    public static final RegistryObject<SoundEvent> SPINO_ROAR1 =
            registerSoundEvent("spino_roar1");
    public static final RegistryObject<SoundEvent> SPINO_ROAR2 =
            registerSoundEvent("spino_roar2");
    public static final RegistryObject<SoundEvent> SPINO_ROAR3 =
            registerSoundEvent("spino_roar3");
    public static final RegistryObject<SoundEvent> SATELLITE_SPINO =
            registerSoundEvent("satellite_spino");
    public static final RegistryObject<SoundEvent> INDOMINUS_REX_ROAR1 =
            registerSoundEvent("indominus_rex_roar1");
    public static final RegistryObject<SoundEvent> INDOMINUS_REX_ROAR2 =
            registerSoundEvent("indominus_rex_roar2");
    public static final RegistryObject<SoundEvent> INDOMINUS_REX_GROWL =
            registerSoundEvent("indominus_rex_growl");
    public static final RegistryObject<SoundEvent> ACRO_GROWL =
            registerSoundEvent("acro_growl");
    public static final RegistryObject<SoundEvent> ACRO_ROAR1 =
            registerSoundEvent("acro_roar1");
    public static final RegistryObject<SoundEvent> ACRO_ROAR2 =
            registerSoundEvent("acro_roar2");
    public static final RegistryObject<SoundEvent> INDORAPTOR_ROAR_1 =
            registerSoundEvent("indoraptor_roar_1");
    public static final RegistryObject<SoundEvent> INDORAPTOR_ROAR_2 =
            registerSoundEvent("indoraptor_roar_2");
    public static final RegistryObject<SoundEvent> INDORAPTOR_ROAR_3 =
            registerSoundEvent("indoraptor_roar_3");
    public static final RegistryObject<SoundEvent> SPINOSAURUS_IDLE_ROAR =
            registerSoundEvent("spinosaurus_idle_roar");
    public static final RegistryObject<SoundEvent> SPINOSAURUS_HURT =
            registerSoundEvent("spinosaurus_hurt");
    public static final RegistryObject<SoundEvent> SPINOSAURUS_DEATH =
            registerSoundEvent("spinosaurus_death");
    public static final RegistryObject<SoundEvent> T_AMBIENT =
            registerSoundEvent("t_ambient");
    public static final RegistryObject<SoundEvent> T_HURT =
            registerSoundEvent("t_hurt");
    public static final RegistryObject<SoundEvent> T_DEATH =
            registerSoundEvent("t_death");
    public static final RegistryObject<SoundEvent> CERATOSUCHOPS_IDLE_ROAR =
            registerSoundEvent("ceratosuchops_idle_roar");
    public static final RegistryObject<SoundEvent> CERATOSUCHOPS_HURT =
            registerSoundEvent("ceratosuchops_hurt");
    public static final RegistryObject<SoundEvent> CERATOSUCHOPS_DEATH =
            registerSoundEvent("ceratosuchops_death");
    public static final RegistryObject<SoundEvent> ALTERNASAURUS_IDLE =
            registerSoundEvent("alternasaurus_idle");
    public static final RegistryObject<SoundEvent> ALTERNASAURUS_HURT =
            registerSoundEvent("alternasaurus_hurt");
    public static final RegistryObject<SoundEvent> ALTERNASAURUS_DEATH =
            registerSoundEvent("alternasaurus_death");
    public static final RegistryObject<SoundEvent> SCORPIUS_AMBIENT =
            registerSoundEvent("scorpius_ambient");
    public static final RegistryObject<SoundEvent> SCORPIUS_HURT =
            registerSoundEvent("scorpius_hurt");
    public static final RegistryObject<SoundEvent> SCORPIUS_DEATH =
            registerSoundEvent("scorpius_death");
    public static final RegistryObject<SoundEvent> BARY_AMBIENT =
            registerSoundEvent("bary_ambient");
    public static final RegistryObject<SoundEvent> BARY_HURT =
            registerSoundEvent("bary_hurt");
    public static final RegistryObject<SoundEvent> BARY_DEATH =
            registerSoundEvent("bary_death");
    public static final RegistryObject<SoundEvent> ALLO_AMBIENT =
            registerSoundEvent("allo_ambient");
    public static final RegistryObject<SoundEvent> ALLO_HURT =
            registerSoundEvent("allo_hurt");
    public static final RegistryObject<SoundEvent> ALLO_DEATH =
            registerSoundEvent("allo_death");
    public static final RegistryObject<SoundEvent> TYLO_AMBIENT =
            registerSoundEvent("tylo_ambient");
    public static final RegistryObject<SoundEvent> TYLO_HURT =
            registerSoundEvent("tylo_hurt");
    public static final RegistryObject<SoundEvent> TYLO_DEATH =
            registerSoundEvent("tylo_death");
    public static final RegistryObject<SoundEvent> MOSA_AMBIENT =
            registerSoundEvent("mosa_ambient");
    public static final RegistryObject<SoundEvent> MOSA_HURT =
            registerSoundEvent("mosa_hurt");
    public static final RegistryObject<SoundEvent> MOSA_DEATH =
            registerSoundEvent("mosa_death");
    public static final RegistryObject<SoundEvent> ALIO_AMBIENT =
            registerSoundEvent("alio_ambient");
    public static final RegistryObject<SoundEvent> ALIO_HURT =
            registerSoundEvent("alio_hurt");
    public static final RegistryObject<SoundEvent> ALIO_DEATH =
            registerSoundEvent("alio_death");
    public static final RegistryObject<SoundEvent> CARCHA_AMBIENT =
            registerSoundEvent("carcha_ambient");
    public static final RegistryObject<SoundEvent> CARCHA_HURT =
            registerSoundEvent("carcha_hurt");
    public static final RegistryObject<SoundEvent> CARCHA_DEATH =
            registerSoundEvent("carcha_death");
    public static final RegistryObject<SoundEvent> RSPINO_AMBIENT =
            registerSoundEvent("rspino_ambient");
    public static final RegistryObject<SoundEvent> RSPINO_HURT =
            registerSoundEvent("rspino_hurt");
    public static final RegistryObject<SoundEvent> RSPINO_DEATH =
            registerSoundEvent("rspino_death");
    public static final RegistryObject<SoundEvent> YUTY_AMBIENT =
            registerSoundEvent("yuty_ambient");
    public static final RegistryObject<SoundEvent> YUTY_HURT =
            registerSoundEvent("yuty_hurt");
    public static final RegistryObject<SoundEvent> YUTY_DEATH =
            registerSoundEvent("yuty_death");
    public static final RegistryObject<SoundEvent> GIGA_AMBIENT =
            registerSoundEvent("giga_ambient");
    public static final RegistryObject<SoundEvent> GIGA_HURT =
            registerSoundEvent("giga_hurt");
    public static final RegistryObject<SoundEvent> GIGA_DEATH =
            registerSoundEvent("giga_death");
    public static final RegistryObject<SoundEvent> COMPY_AMBIENT =
            registerSoundEvent("compy_ambient");
    public static final RegistryObject<SoundEvent> COMPY_HURT =
            registerSoundEvent("compy_hurt");
    public static final RegistryObject<SoundEvent> COMPY_DEATH =
            registerSoundEvent("compy_death");
    public static final RegistryObject<SoundEvent> BETA_AMBIENT =
            registerSoundEvent("beta_ambient");
    public static final RegistryObject<SoundEvent> BLUE_AMBIENT =
            registerSoundEvent("blue_ambient");
    public static final RegistryObject<SoundEvent> BLUE_HURT =
            registerSoundEvent("blue_hurt");
    public static final RegistryObject<SoundEvent> BLUE_DEATH =
            registerSoundEvent("blue_death");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(Alternacraft.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
