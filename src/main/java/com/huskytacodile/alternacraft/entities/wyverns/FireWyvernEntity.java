package com.huskytacodile.alternacraft.entities.wyverns;

import com.huskytacodile.alternacraft.entities.variant.IVariant;
import com.huskytacodile.alternacraft.entities.variant.RarityVariant;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public class FireWyvernEntity extends WyvernEntity{
    public FireWyvernEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public IVariant getVariant() {
        return RarityVariant.byId(this.getTypeVariant() & 255);
    }

    @Override
    public String getAnimationName() {
        return "fire";
    }

    public static AttributeSupplier.Builder attributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 92.00D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.ATTACK_DAMAGE, 9.0D)
                .add(Attributes.FLYING_SPEED, 0.2D);
    }
}
