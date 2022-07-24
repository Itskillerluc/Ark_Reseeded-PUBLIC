package com.huskytacodile.alternacraft.events;

import com.huskytacodile.alternacraft.Alternacraft;
import com.huskytacodile.alternacraft.block.ModBlocks;
import com.huskytacodile.alternacraft.client.render.entity.*;
import com.huskytacodile.alternacraft.entities.ModEntityTypes;
import com.huskytacodile.alternacraft.item.ModItems;
import com.huskytacodile.alternacraft.screen.DNAInsertionTableScreen;
import com.huskytacodile.alternacraft.screen.FossilGrinderScreen;
import com.huskytacodile.alternacraft.screen.ModMenuTypes;
import com.huskytacodile.alternacraft.util.ModItemProperties;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Alternacraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents {
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(ModMenuTypes.FOSSIL_GRINDER.get(), FossilGrinderScreen::new);
        MenuScreens.register(ModMenuTypes.DNA_INSERTION_TABLE.get(), DNAInsertionTableScreen::new);
        ModItemProperties.makeBow(ModItems.PAINITE_BOW.get());

        EntityRenderers.register(ModEntityTypes.JPSPINO.get(), JPSpinoRenderer::new);
        EntityRenderers.register(ModEntityTypes.INDOMINUS.get(), IndominusRenderer::new);
        EntityRenderers.register(ModEntityTypes.ACRO.get(), AcroRenderer::new);
        EntityRenderers.register(ModEntityTypes.OXALAIA.get(), OxalaiaRenderer::new);
        EntityRenderers.register(ModEntityTypes.INDORAPTOR.get(), IndoraptorRenderer::new);
        EntityRenderers.register(ModEntityTypes.CERATOSUCHOPS.get(), CeratosuchopsRenderer::new);
        EntityRenderers.register(ModEntityTypes.ALTERNASAURUS.get(), AlternasaurusRenderer::new);
        EntityRenderers.register(ModEntityTypes.TYLOSAURUS.get(), TylosaurusRenderer::new);
        EntityRenderers.register(ModEntityTypes.MOSASAURUS.get(), MosasaurusRenderer::new);
        EntityRenderers.register(ModEntityTypes.TYRANNOSAURUS.get(), TyrannosaurusRenderer::new);
        EntityRenderers.register(ModEntityTypes.SCORPIUS.get(), ScorpiusRenderer::new);
        EntityRenderers.register(ModEntityTypes.ALLOSAURUS.get(), AllosaurusRenderer::new);
        EntityRenderers.register(ModEntityTypes.BARYONYX.get(), BaryonyxRenderer::new);
        EntityRenderers.register(ModEntityTypes.BARYONYX_GEN2.get(), BaryonyxGen2Renderer::new);
        EntityRenderers.register(ModEntityTypes.ALIORAMUS.get(), AlioramusRenderer::new);
        EntityRenderers.register(ModEntityTypes.CARCHA.get(), CarchaRenderer::new);
        EntityRenderers.register(ModEntityTypes.YUTYRANNUS.get(), YutyrannusRenderer::new);
        EntityRenderers.register(ModEntityTypes.SPINO.get(), SpinoRenderer::new);
        EntityRenderers.register(ModEntityTypes.COMPY.get(), CompsognathusRenderer::new);
        EntityRenderers.register(ModEntityTypes.MEGALO.get(), MegaloRenderer::new);
        EntityRenderers.register(ModEntityTypes.GIGA.get(), GigaRenderer::new);
        EntityRenderers.register(ModEntityTypes.BLUE.get(), BlueRenderer::new);
        EntityRenderers.register(ModEntityTypes.MALUSAURUS.get(), MalusaurusRenderer::new);
        EntityRenderers.register(ModEntityTypes.SIMPLIFIED_SPINO.get(), SimplifiedSpinoRenderer::new);
        EntityRenderers.register(ModEntityTypes.MOROS.get(), MorosRenderer::new);
        EntityRenderers.register(ModEntityTypes.DEINONYCHUS.get(), DeinonychusRenderer::new);
    }
}
