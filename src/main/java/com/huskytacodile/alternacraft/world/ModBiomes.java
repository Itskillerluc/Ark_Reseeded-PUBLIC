package com.huskytacodile.alternacraft.world;

import terrablender.api.Regions;

public class ModBiomes {
    public static void setupTerraBlender(){
        Regions.register(new OverworldRegion(1));
    }
}
