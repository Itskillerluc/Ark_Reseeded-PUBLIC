package com.huskytacodile.alternacraft.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
	
	public final ForgeConfigSpec.BooleanValue sleepingAi;
	
	public CommonConfig(final ForgeConfigSpec.Builder builder) {
		builder.push("general");
		sleepingAi = buildBoolean(builder, "Sleep AI", "all", true, "Whether entities will sleep or not. Default is true.");
	}
	
	private static ForgeConfigSpec.BooleanValue buildBoolean(ForgeConfigSpec.Builder builder, String name, String category, boolean defaultValue, String comment) {
		return builder.comment(comment).translation(name).define(name, defaultValue);
	}

}
