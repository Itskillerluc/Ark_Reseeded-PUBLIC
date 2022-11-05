package com.huskytacodile.alternacraft.entities.dinos;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public abstract class SemiAquaticEntity extends CarnivoreEntity implements ItemSteerable {
    public SemiAquaticEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    protected Predicate<LivingEntity> getPreySelection(Entity entity) {
        return (e) -> e.getType() != entity.getType() && (e.getType() == EntityType.TROPICAL_FISH || e.getType() == EntityType.SALMON
        || e.getType() == EntityType.COD || e.getType() == EntityType.PUFFERFISH || e.getType() == EntityType.FROG || e.getType() == EntityType.DROWNED);
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
