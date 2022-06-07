package com.huskytacodile.alternacraft.entities.dinos;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;

public abstract class AquaticEntity extends AlternaDinoEntity {
    public AquaticEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }


}
