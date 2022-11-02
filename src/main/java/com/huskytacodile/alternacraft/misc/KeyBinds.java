package com.huskytacodile.alternacraft.misc;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinds {
    public static final String KEY_CATEGORY = "key.category.alternacraft";
    public static final String KEY_FLY_DOWN = "key.alternacraft.fly_down";

    public static final KeyMapping FLY_DOWN_KEY = new KeyMapping(KEY_FLY_DOWN, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_CONTROL, KEY_CATEGORY);
}
