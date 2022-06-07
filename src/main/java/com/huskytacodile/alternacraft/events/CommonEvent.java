package com.huskytacodile.alternacraft.events;

import com.huskytacodile.alternacraft.Alternacraft;
import com.huskytacodile.alternacraft.entities.ModVillagers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Alternacraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvent {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModVillagers.registerHuskyPOI();
            ModVillagers.registerCylixrPOI();
            ModVillagers.registerKaupenPOI();
        });
    }
}
