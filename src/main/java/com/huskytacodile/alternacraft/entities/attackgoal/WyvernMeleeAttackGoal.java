package com.huskytacodile.alternacraft.entities.attackgoal;

import com.huskytacodile.alternacraft.entities.wyverns.WyvernEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class WyvernMeleeAttackGoal extends Goal {
    private WyvernEntity entity;
    private int animCounter = 0;
    private double animTickLength = 32.5;
    private int ticksUntilNextAttack;
    private LivingEntity enemy;

    public WyvernMeleeAttackGoal(WyvernEntity pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        entity = pMob;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    protected void checkAndPerformAttack() {
        if (enemy == null) {
            if (this.ticksUntilNextAttack <= 0) {
                if (entity != null) {
                    entity.setAttacking(true);
                }
            }
        } else{
            if (this.ticksUntilNextAttack <= 0) {
                if (entity != null) {
                    entity.setAttacking(true);
                }
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        checkAndPerformAttack();
        if(entity.isAttacking()) {
            animCounter++;

            if(animCounter >= animTickLength) {
                /**you can add a second timer to damage at a specific time if you want to**/
                if (enemy == null) {
                    entity.getTarget().hurt(DamageSource.mobAttack(this.entity), (float) this.entity.getAttributeBaseValue(Attributes.ATTACK_DAMAGE));
                } else {
                    enemy.hurt(DamageSource.mobAttack(this.entity), (float) this.entity.getAttributeBaseValue(Attributes.ATTACK_DAMAGE));
                }
                animCounter = 0;
                entity.setAttacking(false);
            }
        }
        this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
    }

    @Override
    public boolean canUse() {
        return ((entity.getTarget() != null && !entity.getTarget().getUUID().equals(entity.getOwnerUUID()) && entity.distanceTo(entity.getTarget()) < 5) || entity.getLevel().getNearestPlayer(entity, 5) != null && !entity.getLevel().getNearestPlayer(entity, 5).getUUID().equals(entity.getOwnerUUID()));
    }

    @Override
    public void start() {
        enemy = entity.getLevel().getNearestPlayer(entity, 5);
        ticksUntilNextAttack = 1;
        checkAndPerformAttack();
    }

    @Override
    public void stop() {
        animCounter = 0;
        entity.setAttacking(false);
        super.stop();
    }
}
