package com.huskytacodile.alternacraft.entities.attackgoal;

import com.huskytacodile.alternacraft.entities.other.FireEntity;
import com.huskytacodile.alternacraft.entities.wyverns.WyvernEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.function.Supplier;

public class WyvernFireAttackGoal extends Goal {
    private WyvernEntity entity;
    private int animCounter = 0;
    private double animTickLength = 23.4;
    private LivingEntity enemy;
    private Supplier<FireEntity> projectile;

    public WyvernFireAttackGoal(WyvernEntity pMob, Supplier<FireEntity> projectile) {
        entity = pMob;
        this.projectile = projectile;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    protected void checkAndPerformAttack() {
        if (enemy == null) {
            if (entity.getFireAnimation() == 1 && animCounter > animTickLength) {
                if (entity != null) {
                    entity.setFireAnimation(2);
                }
            } else if (entity.getFireAnimation() == 0) {
                entity.setFireAnimation(1);
            }
        } else{
            if (entity.getFireAnimation() == 1 && animCounter > animTickLength) {
                if (entity != null) {
                    entity.setFireAnimation(2);
                }
            } else if (entity.getFireAnimation() == 0) {
                entity.setFireAnimation(1);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (entity.level.isClientSide()){
            return;
        }
        checkAndPerformAttack();
        if (entity.getFireAnimation() == 1 && animCounter <= animTickLength){
            animCounter++;
        } else if (entity.getFireAnimation() == 2) {
            if (enemy != null) {
                if (entity.tickCount % 3 == 0) {
                    var proj = projectile.get();
                    proj.shoot(enemy.getX(), enemy.getY(), enemy.getZ(), 1, 1);
                    entity.level.addFreshEntity(proj);
                }
            } else {
                if (entity.tickCount % 3 == 0){
                    var proj = projectile.get();
                    if (entity.getTarget() != null) {
                        proj.shoot(entity.getTarget().getX(), entity.getTarget().getY(), entity.getTarget().getZ(), 1, 1);
                    }
                    entity.level.addFreshEntity(proj);
                }
            }
            entity.setFireAnimation(3);
            entity.setFireCharge(entity.getFireCharge()-1);
        }
        if (entity.getFireCharge() <=0){
            this.stop();
        }
    }

    @Override
    public boolean canUse() {
        return (((entity.getTarget() != null && entity.distanceTo(entity.getTarget()) < 10 && entity.distanceTo(entity.getTarget()) > 3) || entity.getLevel().getNearestPlayer(entity, 10) != null && entity.getLevel().getNearestPlayer(entity, 10).distanceTo(entity) > 3) && entity.getFireCharge() == 100) || ((entity.getTarget() != null && entity.distanceTo(entity.getTarget()) < 10 && entity.distanceTo(entity.getTarget()) > 3) && entity.getFireAnimation() > 0);
    }

    @Override
    public void start() {
        enemy = entity.getLevel().getNearestPlayer(entity, 10);
        checkAndPerformAttack();
    }

    @Override
    public void stop() {
        animCounter = 0;
        if (entity.getFireCharge() < 1) entity.setFireAnimation(3);
        super.stop();
    }
}
