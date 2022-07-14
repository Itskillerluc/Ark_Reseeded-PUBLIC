package com.huskytacodile.alternacraft.config;

import com.huskytacodile.alternacraft.Alternacraft;

import net.minecraftforge.fml.config.ModConfig;

public class AlternacraftConfig {
	
	public static boolean sleepingAi = true;
	
	public static void bake(ModConfig config) {
		try {
			sleepingAi = AlternacraftConfigHolder.COMMON.sleepingAi.get();
		} catch (Exception e) {
			Alternacraft.LOGGER.warn("An exception was caused trying to load the config for Alternacraft!");
			e.printStackTrace();
		}
	}

}
