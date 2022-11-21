package com.huskytacodile.alternacraft.entities.attackgoal;

import com.huskytacodile.alternacraft.entities.other.FireEntity;
import com.huskytacodile.alternacraft.entities.wyverns.WyvernEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public class WyvernFireAttackGoal extends Goal {
    private WyvernEntity entity;
    private int animCounter = 0;
    private double animTickLength = 24;
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
        if (entity.level.isClientSide()) {
            return;
        }
        checkAndPerformAttack();
        if (entity.getFireAnimation() == 1 && animCounter <= animTickLength) {
            animCounter++;
        } else if (entity.getFireAnimation() == 2) {
            if (enemy != null) {
                if (entity.tickCount % 2 == 0) {
                    var proj = projectile.get();
                    proj.setPos(entity.position().add(Vec3.directionFromRotation(0, entity.getYHeadRot())));
                    entity.lookAt(enemy, 60, 60);
                    proj.shoot(enemy.getX() - entity.getX(), enemy.getEyeY() -1 - entity.getY(), enemy.getZ() - entity.getZ(), 0.5f, 15f);
                    entity.level.addFreshEntity(proj);
                }
                entity.setFireCharge(entity.getFireCharge() - 2);
            } else {
                if (entity.tickCount % 2 == 0) {
                    var proj = projectile.get();
                    if (entity.getTarget() != null) {
                        entity.lookAt(enemy, 60, 60);
                        proj.shoot(enemy.getX() - entity.getX(), enemy.getEyeY() -1 - entity.getY(), enemy.getZ() - entity.getZ(), 0.5f, 15f);
                        entity.level.addFreshEntity(proj);
                    }
                }
                entity.setFireCharge(entity.getFireCharge() - 2);
            }
            if (entity.getFireCharge() <= 0) {
                entity.setFireAnimation(3);
                this.stop();
            }
        }
    }

    @Override
    public boolean canUse() {
        return (((entity.getTarget() != null && entity.distanceTo(entity.getTarget()) < 10 && entity.distanceTo(entity.getTarget()) > 3) || entity.getLevel().getNearestPlayer(entity, 10) != null && entity.getLevel().getNearestPlayer(entity, 10).distanceTo(entity) > 3) && entity.getFireCharge() == 200) || ((entity.getTarget() != null && entity.distanceTo(entity.getTarget()) < 10 && entity.distanceTo(entity.getTarget()) > 3) && entity.getFireAnimation() > 0) && isAlive();
    }

    @Override
    public void start() {
        enemy = entity.getLevel().getNearestPlayer(entity, 10);

        checkAndPerformAttack();
    }

    public boolean isAlive(){
        if (entity.getTarget() != null){
            return entity.getTarget().isAlive();
        } else return enemy.isAlive();
    }

    @Override
    public void stop() {
        animCounter = 0;
        if (entity.getFireCharge() < 1) entity.setFireAnimation(3);
        super.stop();
    }
}
