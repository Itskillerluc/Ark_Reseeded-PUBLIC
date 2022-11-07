package com.huskytacodile.alternacraft.entities.attackgoal;

import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class WyvernBreathFireAttackGoal extends RangedAttackGoal {
    public WyvernBreathFireAttackGoal(RangedAttackMob pRangedAttackMob, double pSpeedModifier, int pAttackInterval, float pAttackRadius) {
        super(pRangedAttackMob, pSpeedModifier, pAttackInterval, pAttackRadius);
    }
}
