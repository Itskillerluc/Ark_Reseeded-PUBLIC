package com.huskytacodile.alternacraft.entities.ai;

import com.huskytacodile.alternacraft.entities.wyverns.WyvernEntity;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;

public class WyvernFollowGoal extends MoveTowardsTargetGoal {
    private final WyvernEntity entity;
    private final float within;
    private final float range;
    private final double speed;

    public WyvernFollowGoal(WyvernEntity pMob, double pSpeedModifier, float pWithin, float range) {
        super(pMob, pSpeedModifier, pWithin);
        within = pWithin;
        this.range = range;
        speed = pSpeedModifier;
        entity = pMob;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.entity.getNavigation().isDone() && this.entity.getTarget() != null && this.entity.getTarget().isAlive() && this.entity.distanceTo(this.entity.getTarget()) > 5 && this.entity.getTarget().distanceToSqr(this.entity) > (double)(this.within * this.within) && this.entity.getTarget().distanceToSqr(this.entity) < (double)(this.range * this.range);
    }

    @Override
    public boolean canUse() {
        return entity.getOwner() == null && entity.getTarget() != null && entity.getTarget().distanceTo(entity) < within && entity.getTarget().distanceTo(entity) < range;
    }

    @Override
    public void start() {
        entity.getNavigation().moveTo(entity.getTarget().getX(), entity.getTarget().getY()-1, entity.getTarget().getZ(), speed);
    }

    @Override
    public void stop() {
        if (entity.level.isClientSide()){
            return;
        }
        entity.setNoGravity(false);
    }
}
