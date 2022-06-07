package com.huskytacodile.alternacraft.entities.dinos;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class MediumCarnivoreEntity extends CarnivoreEntity implements ItemSteerable {
    public MediumCarnivoreEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean travel(Mob mob, ItemBasedSteering itemBasedSteering, Vec3 vec3) {
        return ItemSteerable.super.travel(mob, itemBasedSteering, vec3);
    }

    @Override
    public boolean boost() {
        return false;
    }

    @Override
    public void travel(Vec3 vec3) {
        this.travel(this, this.steering, vec3);
    }

    @Override
    public float getSteeringSpeed() {
        return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.550F;
    }

    @Override
    public void travelWithInput(Vec3 direction) {
        super.travel(direction);
    }
}
