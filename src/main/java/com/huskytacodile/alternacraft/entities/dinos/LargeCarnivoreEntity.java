package com.huskytacodile.alternacraft.entities.dinos;

import com.huskytacodile.alternacraft.entities.ModEntityTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public abstract class LargeCarnivoreEntity extends CarnivoreEntity implements ItemSteerable {
    public LargeCarnivoreEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected Predicate<LivingEntity> getPreySelection(Entity entity) {
        return (e) -> e.getType() != entity.getType() && (e.getType() == EntityType.SHEEP || e.getType() == EntityType.RABBIT ||
                e.getType() == EntityType.COW || e.getType() == EntityType.CHICKEN || e.getType() == EntityType.PIG ||
                e.getType() == ModEntityTypes.JPSPINO.get() || e.getType() == ModEntityTypes.OXALAIA.get() ||
                e.getType() == ModEntityTypes.TYRANNOSAURUS.get() || e.getType() == ModEntityTypes.YUTYRANNUS.get() ||
                e.getType() == ModEntityTypes.CARCHA.get() || e.getType() == ModEntityTypes.ALIORAMUS.get() ||
                e.getType() == ModEntityTypes.BARYONYX.get() || e.getType() == ModEntityTypes.BARYONYX_GEN2.get() ||
                e.getType() == ModEntityTypes.TYLOSAURUS.get() || e.getType() == ModEntityTypes.MOSASAURUS.get() ||
                e.getType() == ModEntityTypes.CERATOSUCHOPS.get() || e.getType() == ModEntityTypes.INDOMINUS.get() ||
                e.getType() == ModEntityTypes.INDORAPTOR.get() || e.getType() == ModEntityTypes.SCORPIUS.get() ||
                e.getType() == ModEntityTypes.GIGA.get() || e.getType() == ModEntityTypes.ALTERNASAURUS.get());
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
