package com.huskytacodile.alternacraft;

import com.huskytacodile.alternacraft.block.ModBlocks;
import com.huskytacodile.alternacraft.block.entity.ModBlockEntities;
import com.huskytacodile.alternacraft.client.render.entity.*;
import com.huskytacodile.alternacraft.enchantment.ModEnchantments;
import com.huskytacodile.alternacraft.entities.ModEntityTypes;
import com.huskytacodile.alternacraft.item.ModItems;
import com.huskytacodile.alternacraft.misc.ModPOIs;
import com.huskytacodile.alternacraft.recipe.ModRecipes;
import com.huskytacodile.alternacraft.screen.DNAInsertionTableScreen;
import com.huskytacodile.alternacraft.screen.FossilGrinderScreen;
import com.huskytacodile.alternacraft.screen.ModMenuTypes;
import com.huskytacodile.alternacraft.util.ModItemProperties;
import com.huskytacodile.alternacraft.util.ModSoundEvents;
import com.huskytacodile.alternacraft.world.dimension.ModDimensions;
import com.huskytacodile.alternacraft.world.feature.ModPlacedFeatures;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Alternacraft.MOD_ID)
public class Alternacraft {
    public static final String MOD_ID = "alternacraft";
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public Alternacraft() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModEntityTypes.register(eventBus);

        ModSoundEvents.register(eventBus);
        ModEnchantments.register(eventBus);

        ModMenuTypes.register(eventBus);
        ModRecipes.register(eventBus);

        ModBlockEntities.register(eventBus);
        ModDimensions.register();

        ModPOIs.register(eventBus);
        ModPlacedFeatures.register(eventBus);

        eventBus.addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        GeckoLib.initialize();

    }

    private void setup(final FMLCommonSetupEvent event)
    {

    }
}
