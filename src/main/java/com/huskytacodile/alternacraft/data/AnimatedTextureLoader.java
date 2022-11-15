package com.huskytacodile.alternacraft.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AnimatedTextureLoader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    private static final Map<ResourceLocation, AnimatedTexture> textures = new HashMap<>();

    public AnimatedTextureLoader() {
        super(GSON, "animated_textures");
    }

    public static Map<ResourceLocation, AnimatedTexture> getTextures() {
        return Map.copyOf(textures);
    }

    public static AnimatedTexture get(ResourceLocation location){
        return textures.get(location);
    }

    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> pObject, @NotNull ResourceManager pResourceManager, @NotNull ProfilerFiller pProfiler) {
        for (Map.Entry<ResourceLocation, JsonElement> entry : pObject.entrySet()) {
            var entries = entry.getValue().getAsJsonObject().getAsJsonObject("entries");
            var values = AnimatedTexture.ANIMATED_TEXTURE_CODEC.parse(JsonOps.INSTANCE, entries).get();
            if (values.left().isPresent()) textures.putIfAbsent(entry.getKey(), values.left().get());
        }
    }
}
