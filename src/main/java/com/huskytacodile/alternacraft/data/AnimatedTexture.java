package com.huskytacodile.alternacraft.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import java.util.List;
public record AnimatedTexture(float interval, List<ResourceLocation> locations){

    public static final Codec<AnimatedTexture> ANIMATED_TEXTURE_CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.FLOAT.fieldOf("interval").forGetter(AnimatedTexture::interval),
                    Codec.list(ResourceLocation.CODEC).fieldOf("locations").forGetter(AnimatedTexture::locations)
            ).apply(instance, AnimatedTexture::new));

    public ResourceLocation next(ResourceLocation location){
        int index = locations.indexOf(location);
        if (locations.size() < 1){
            throw new IndexOutOfBoundsException("List has to be greater than 0, List: " + locations);
        } else if (index == -1){
            return null;
        }
        if (locations.size() == 1 || index == locations.size()-1){
            return locations.get(0);
        }
        return locations.get(index+1);
    }

    public ResourceLocation next(int index){
        if (locations.size() < 1 || locations.size() < index){
            throw new IndexOutOfBoundsException("List has to be greater than 0 and index (" + index + ") can't be bigger than the list (" + locations.size() + "). List: " + locations);
        }
        if (locations.size() == 1 || index == locations.size()-1){
            return locations.get(0);
        }
        return locations.get(index+1);
    }
}
