package com.huskytacodile.alternacraft.recipe;

import com.huskytacodile.alternacraft.Alternacraft;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Alternacraft.MOD_ID);

    public static final RegistryObject<RecipeSerializer<FossilGrinderRecipe>> FOSSIL_GRINDER =
            SERIALIZERS.register("fossil_grinder", () -> FossilGrinderRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
