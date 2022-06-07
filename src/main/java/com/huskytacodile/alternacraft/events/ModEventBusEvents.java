package com.huskytacodile.alternacraft.events;

import com.huskytacodile.alternacraft.Alternacraft;
import com.huskytacodile.alternacraft.entities.*;

import com.huskytacodile.alternacraft.entities.dinos.carnivore.large.*;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.large.hybrid.IndominusEntity;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.large.hybrid.IndoraptorEntity;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.large.hybrid.ScorpiusEntity;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.medium.MegaloEntity;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.medium.raptor.BlueEntity;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.semiaquatic.*;
import com.huskytacodile.alternacraft.entities.dinos.carnivore.small.CompsognathusEntity;
import com.huskytacodile.alternacraft.item.ModItems;
import com.huskytacodile.alternacraft.recipe.FossilGrinderRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Alternacraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.JPSPINO.get(), JPSpinoEntity.attributes().build());
        event.put(ModEntityTypes.INDOMINUS.get(), IndominusEntity.attributes().build());
        event.put(ModEntityTypes.ACRO.get(), AcroEntity.attributes().build());
        event.put(ModEntityTypes.OXALAIA.get(), SuchomimusEntity.attributes().build());
        event.put(ModEntityTypes.INDORAPTOR.get(), IndoraptorEntity.attributes().build());
        event.put(ModEntityTypes.CERATOSUCHOPS.get(), CeratosuchopsEntity.attributes().build());
        event.put(ModEntityTypes.ALTERNASAURUS.get(), AlternasaurusEntity.attributes().build());
        event.put(ModEntityTypes.TYLOSAURUS.get(), TylosaurusEntity.attributes().build());
        event.put(ModEntityTypes.MOSASAURUS.get(), MosasaurusEntity.attributes().build());
        event.put(ModEntityTypes.TYRANNOSAURUS.get(), TyrannosaurusEntity.attributes().build());
        event.put(ModEntityTypes.SCORPIUS.get(), ScorpiusEntity.attributes().build());
        event.put(ModEntityTypes.ALLOSAURUS.get(), AllosaurusEntity.attributes().build());
        event.put(ModEntityTypes.BARYONYX.get(), BaryonyxEntity.attributes().build());
        event.put(ModEntityTypes.BARYONYX_GEN2.get(), BaryonyxGen2Entity.attributes().build());
        event.put(ModEntityTypes.ALIORAMUS.get(), AlioramusEntity.attributes().build());
        event.put(ModEntityTypes.CARCHA.get(), CarchaEntity.attributes().build());
        event.put(ModEntityTypes.YUTYRANNUS.get(), YutyrannusEntity.attributes().build());
        event.put(ModEntityTypes.SPINO.get(), SpinoEntity.attributes().build());
        event.put(ModEntityTypes.COMPY.get(), CompsognathusEntity.attributes().build());
        event.put(ModEntityTypes.MEGALO.get(), MegaloEntity.attributes().build());
        event.put(ModEntityTypes.GIGA.get(), GigaEntity.attributes().build());
        event.put(ModEntityTypes.BLUE.get(), BlueEntity.attributes().build());
        event.put(ModEntityTypes.BETA.get(), BetaEntity.attributes().build());
        event.put(ModEntityTypes.MALUSAURUS.get(), MalusaurusEntity.attributes().build());
        event.put(ModEntityTypes.SIMPLIFIED_SPINO.get(), SimplifiedSpinoEntity.attributes().build());
    }

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        ModItems.onRegisterItems(event.getRegistry());
    }

    @SubscribeEvent
    public static void onRegisterEntities(RegistryEvent.Register<EntityType<?>> event) {
        SpawnEggItem.eggs();
    }

    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, FossilGrinderRecipe.Type.ID, FossilGrinderRecipe.Type.INSTANCE);
    }
}