package com.huskytacodile.alternacraft.entities.attackgoal;

import com.huskytacodile.alternacraft.entities.dinos.CarnivoreEntity;
import com.huskytacodile.alternacraft.entities.dinos.SmallCarnivoreEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;


public class MorosMeleeAttackGoal extends MeleeAttackGoal {
    private SmallCarnivoreEntity entity;
    private int animCounter = 0;
    private double animTickLength = 21.6666;

    public MorosMeleeAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen);
        if(mob instanceof SmallCarnivoreEntity c) {
            entity = c;
        }
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity p_25557_, double p_25558_) {
        if (p_25558_ <= this.getAttackReachSqr(p_25557_) && this.getTicksUntilNextAttack() <= 0) {
            if(entity != null) {
                entity.setAttacking(true);
                animCounter = 0;
            }
        }

        super.checkAndPerformAttack(p_25557_, p_25558_);
    }

    @Override
    public void tick() {
        super.tick();
        if(entity.isAttacking()) {
            animCounter++;

            if(animCounter >= animTickLength) {
                animCounter = 0;
                entity.setAttacking(false);
            }
        }
    }

    @Override
    public void stop() {
        animCounter = 0;
        entity.setAttacking(false);
        super.stop();
    }
}
