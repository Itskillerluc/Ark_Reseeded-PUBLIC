package com.huskytacodile.alternacraft;

import com.huskytacodile.alternacraft.block.ModBlocks;
import com.huskytacodile.alternacraft.block.entity.ModBlockEntities;
import com.huskytacodile.alternacraft.client.render.entity.*;
import com.huskytacodile.alternacraft.enchantment.ModEnchantments;
import com.huskytacodile.alternacraft.entities.ModEntityTypes;
import com.huskytacodile.alternacraft.entities.ModVillagers;
import com.huskytacodile.alternacraft.item.ModItems;
import com.huskytacodile.alternacraft.misc.ModPOIs;
import com.huskytacodile.alternacraft.recipe.ModRecipes;
import com.huskytacodile.alternacraft.screen.DNAInsertionTableScreen;
import com.huskytacodile.alternacraft.screen.FossilGrinderScreen;
import com.huskytacodile.alternacraft.screen.ModMenuTypes;
import com.huskytacodile.alternacraft.util.ModItemProperties;
import com.huskytacodile.alternacraft.util.ModSoundEvents;
import com.huskytacodile.alternacraft.world.dimension.ModDimensions;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Blocks;
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
        ModPOIs.register(eventBus);

        ModVillagers.VILLAGER_PROFESSIONS.register(eventBus);
        ModVillagers.POINT_OF_INTEREST_TYPES.register(eventBus);
        ModDimensions.register();
        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::registerRenderers);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        GeckoLib.initialize();

    }
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void registerRenderers(final EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(ModEntityTypes.JPSPINO.get(), manager -> new JPSpinoRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.INDOMINUS.get(), manager -> new IndominusRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.ACRO.get(), manager -> new AcroRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.OXALAIA.get(), manager -> new OxalaiaRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.INDORAPTOR.get(), manager -> new IndoraptorRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.CERATOSUCHOPS.get(), manager -> new CeratosuchopsRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.ALTERNASAURUS.get(), manager -> new AlternasaurusRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.TYLOSAURUS.get(), manager -> new TylosaurusRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.MOSASAURUS.get(), manager -> new MosasaurusRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.TYRANNOSAURUS.get(), manager -> new TyrannosaurusRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.SCORPIUS.get(), manager -> new ScorpiusRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.ALLOSAURUS.get(), manager -> new AllosaurusRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.BARYONYX.get(), manager -> new BaryonyxRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.BARYONYX_GEN2.get(), manager -> new BaryonyxGen2Renderer(manager));
        event.registerEntityRenderer(ModEntityTypes.ALIORAMUS.get(), manager -> new AlioramusRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.CARCHA.get(), manager -> new CarchaRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.YUTYRANNUS.get(), manager -> new YutyrannusRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.SPINO.get(), manager -> new SpinoRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.COMPY.get(), manager -> new CompsognathusRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.MEGALO.get(), manager -> new MegaloRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.GIGA.get(), manager -> new GigaRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.BLUE.get(), manager -> new BlueRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.BETA.get(), manager -> new BetaRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.MALUSAURUS.get(), manager -> new MalusaurusRenderer(manager));
        event.registerEntityRenderer(ModEntityTypes.SIMPLIFIED_SPINO.get(), manager -> new SimplifiedSpinoRenderer(manager));

        ModItemProperties.makeBow(ModItems.PAINITE_BOW.get());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DNA_SEQUENCER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ALTERNA_RADIATOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DNA_COMBINATOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.DNA_EXTRACTOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CLADOPHLEBIS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.JW_BANNER.get(),RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ALTERNA_PORTAL.get(), RenderType.translucent());
    }

    private void setup(final FMLCommonSetupEvent event)
    {

    }

    private void clientSetup(final FMLClientSetupEvent event)
    {
        MenuScreens.register(ModMenuTypes.FOSSIL_GRINDER.get(), FossilGrinderScreen::new);
        MenuScreens.register(ModMenuTypes.DNA_INSERTION_TABLE.get(), DNAInsertionTableScreen::new);
    }
}
