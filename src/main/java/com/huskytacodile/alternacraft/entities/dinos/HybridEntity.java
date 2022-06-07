package com.huskytacodile.alternacraft.entities.dinos;

import com.huskytacodile.alternacraft.entities.ModEntityTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public abstract class HybridEntity extends LargeCarnivoreEntity {
    public HybridEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected Predicate<LivingEntity> getPreySelection(Entity entity) {
        final boolean isTamed;
        if(entity instanceof TamableAnimal tamableAnimal) {
            isTamed = tamableAnimal.isTame();
        } else {
            isTamed = false;
        }

        return (e) -> (e.getType() != entity.getType() && !isTamed) && (e.getType() == EntityType.SHEEP || e.getType() == EntityType.RABBIT ||
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
}
