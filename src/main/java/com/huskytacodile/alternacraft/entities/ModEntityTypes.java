package com.huskytacodile.alternacraft.entities;

import com.huskytacodile.alternacraft.Alternacraft;

import com.huskytacodile.alternacraft.entities.dinos.carnivore.large.*;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.large.hybrid.IndominusEntity;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.large.hybrid.IndoraptorEntity;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.large.hybrid.ScorpiusEntity;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.medium.MegaloEntity;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.medium.raptor.BlueEntity;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.semiaquatic.*;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.small.CompsognathusEntity;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.ENTITIES, Alternacraft.MOD_ID);

    public static final RegistryObject<EntityType<SpinoEntity>> SPINO =
            ENTITY_TYPES.register("spino",
                    () -> EntityType.Builder.of(SpinoEntity::new,
                                    MobCategory.CREATURE).sized(4f, 4f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "spino").toString()));

    public static final RegistryObject<EntityType<JPSpinoEntity>> JPSPINO =
            ENTITY_TYPES.register("jpspino",
                    () -> EntityType.Builder.of(JPSpinoEntity::new,
                                    MobCategory.CREATURE).sized(4f, 4f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "jpspino").toString()));

    public static final RegistryObject<EntityType<SimplifiedSpinoEntity>> SIMPLIFIED_SPINO =
            ENTITY_TYPES.register("simplified_spino",
                    () -> EntityType.Builder.of(SimplifiedSpinoEntity::new,
                                    MobCategory.CREATURE).sized(4f, 4f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "simplified_spino").toString()));


    public static final RegistryObject<EntityType<AlternasaurusEntity>> ALTERNASAURUS =
            ENTITY_TYPES.register("alternasaurus",
                    () -> EntityType.Builder.of(AlternasaurusEntity::new,
                                    MobCategory.CREATURE).sized(4f, 4f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "alternasaurus").toString()));

    public static final RegistryObject<EntityType<AlioramusEntity>> ALIORAMUS =
            ENTITY_TYPES.register("alioramus",
                    () -> EntityType.Builder.of(AlioramusEntity::new,
                                    MobCategory.CREATURE).sized(2.25f, 2.25f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "alioramus").toString()));
    

    public static final RegistryObject<EntityType<TylosaurusEntity>> TYLOSAURUS =
            ENTITY_TYPES.register("tylosaurus",
                    () -> EntityType.Builder.of(TylosaurusEntity::new,
                                    MobCategory.CREATURE).sized(4f, 4f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "tylosaurus").toString()));

    public static final RegistryObject<EntityType<MosasaurusEntity>> MOSASAURUS =
            ENTITY_TYPES.register("mosasaurus",
                    () -> EntityType.Builder.of(MosasaurusEntity::new,
                                    MobCategory.CREATURE).sized(4f, 4f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "mosasaurus").toString()));

    public static final RegistryObject<EntityType<SuchomimusEntity>> OXALAIA =
            ENTITY_TYPES.register("oxalaia",
                    () -> EntityType.Builder.of(SuchomimusEntity::new,
                                    MobCategory.CREATURE).sized(4f, 4f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "oxalaia").toString()));

    public static final RegistryObject<EntityType<CarchaEntity>> CARCHA =
            ENTITY_TYPES.register("carcha",
                    () -> EntityType.Builder.of(CarchaEntity::new,
                                    MobCategory.CREATURE).sized(4f, 4f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "carcha").toString()));

    public static final RegistryObject<EntityType<YutyrannusEntity>> YUTYRANNUS =
            ENTITY_TYPES.register("yuty",
                    () -> EntityType.Builder.of(YutyrannusEntity::new,
                                    MobCategory.CREATURE).sized(4f, 4f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "yuty").toString()));

    public static final RegistryObject<EntityType<CeratosuchopsEntity>> CERATOSUCHOPS =
            ENTITY_TYPES.register("ceratosuchops",
                    () -> EntityType.Builder.of(CeratosuchopsEntity::new,
                                    MobCategory.CREATURE).sized(3f, 3f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "ceratosuchops").toString()));

    public static final RegistryObject<EntityType<IndominusEntity>> INDOMINUS =
            ENTITY_TYPES.register("indominus",
                    () -> EntityType.Builder.of(IndominusEntity::new,
                                    MobCategory.CREATURE).sized(4f, 4f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "indominus").toString()));

    public static final RegistryObject<EntityType<IndoraptorEntity>> INDORAPTOR =
            ENTITY_TYPES.register("indoraptor",
                    () -> EntityType.Builder.of(IndoraptorEntity::new,
                                    MobCategory.CREATURE).sized(3f, 3f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "indoraptor").toString()));

    public static final RegistryObject<EntityType<AcroEntity>> ACRO =
            ENTITY_TYPES.register("acro",
                    () -> EntityType.Builder.of(AcroEntity::new,
                                    MobCategory.CREATURE).sized(3.5f, 3.5f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "acro").toString()));

    public static final RegistryObject<EntityType<TyrannosaurusEntity>> TYRANNOSAURUS =
            ENTITY_TYPES.register("tyrannosaurus",
                    () -> EntityType.Builder.of(TyrannosaurusEntity::new,
                                    MobCategory.CREATURE).sized(4f, 4f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "tyrannosaurus").toString()));

    public static final RegistryObject<EntityType<ScorpiusEntity>> SCORPIUS =
            ENTITY_TYPES.register("scorpius",
                    () -> EntityType.Builder.of(ScorpiusEntity::new,
                                    MobCategory.CREATURE).sized(3.5f, 3.5f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "scorpius").toString()));

    public static final RegistryObject<EntityType<AllosaurusEntity>> ALLOSAURUS =
            ENTITY_TYPES.register("allosaurus",
                    () -> EntityType.Builder.of(AllosaurusEntity::new,
                                    MobCategory.CREATURE).sized(3.5f, 3.5f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "allosaurus").toString()));

    public static final RegistryObject<EntityType<BaryonyxEntity>> BARYONYX =
            ENTITY_TYPES.register("baryonyx",
                    () -> EntityType.Builder.of(BaryonyxEntity::new,
                                    MobCategory.CREATURE).sized(3.5f, 3.5f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "baryonyx").toString()));

    public static final RegistryObject<EntityType<BaryonyxGen2Entity>> BARYONYX_GEN2 =
            ENTITY_TYPES.register("baryonyx_gen2",
                    () -> EntityType.Builder.of(BaryonyxGen2Entity::new,
                                    MobCategory.CREATURE).sized(3.5f, 3.5f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "baryonyx_gen2").toString()));

    public static final RegistryObject<EntityType<CompsognathusEntity>> COMPY =
            ENTITY_TYPES.register("compy",
                    () -> EntityType.Builder.of(CompsognathusEntity::new,
                                    MobCategory.CREATURE).sized(0.7f, 0.7f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "compy").toString()));

    public static final RegistryObject<EntityType<MegaloEntity>> MEGALO =
            ENTITY_TYPES.register("megalo",
                    () -> EntityType.Builder.of(MegaloEntity::new,
                                    MobCategory.CREATURE).sized(3f, 3f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "megalo").toString()));

    public static final RegistryObject<EntityType<GigaEntity>> GIGA =
            ENTITY_TYPES.register("giga",
                    () -> EntityType.Builder.of(GigaEntity::new,
                                    MobCategory.CREATURE).sized(4f, 4f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "giga").toString()));

    public static final RegistryObject<EntityType<MalusaurusEntity>> MALUSAURUS =
            ENTITY_TYPES.register("malusaurus",
                    () -> EntityType.Builder.of(MalusaurusEntity::new,
                                    MobCategory.CREATURE).sized(4.3f, 4.3f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "malusaurus").toString()));

    public static final RegistryObject<EntityType<BlueEntity>> BLUE =
            ENTITY_TYPES.register("blue",
                    () -> EntityType.Builder.of(BlueEntity::new,
                                    MobCategory.CREATURE).sized(2f, 2f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "blue").toString()));

    public static final RegistryObject<EntityType<BetaEntity>> BETA =
            ENTITY_TYPES.register("beta",
                    () -> EntityType.Builder.of(BetaEntity::new,
                                    MobCategory.CREATURE).sized(1.2f, 1.2f)
                            .build(new ResourceLocation(Alternacraft.MOD_ID, "beta").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
