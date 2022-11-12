package com.huskytacodile.alternacraft.entities.attackgoal;

import com.huskytacodile.alternacraft.client.render.entity.FireWyvernRenderer;
import com.huskytacodile.alternacraft.entities.ModEntityTypes;
import com.huskytacodile.alternacraft.entities.wyverns.WyvernEntity;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WyvernMeleeAttackGoal extends Goal {
    private WyvernEntity entity;
    private int animCounter = 0;
    private double animTickLength = 32.5;
    private int ticksUntilNextAttack;

    public WyvernMeleeAttackGoal(WyvernEntity pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        entity = pMob;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    protected void checkAndPerformAttack() {
        if (this.entity.position().distanceTo(new Vec3(this.entity.getTarget().getX(), this.entity.getTarget().getY(), this.entity.getTarget().getZ())) < this.entity.getBbWidth()+this.entity.getTarget().getBbWidth() && this.ticksUntilNextAttack <= 0) {
            if(entity != null) {
                entity.setAttacking(true);
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
                entity.getTarget().hurt(DamageSource.mobAttack(this.entity), (float) this.entity.getAttributeBaseValue(Attributes.ATTACK_DAMAGE));
                animCounter = 0;
                entity.setAttacking(false);
            }
        }
        this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
    }

    @Override
    public boolean canUse() {
        return entity.isOnGround() && entity.getTarget() != null && entity.distanceTo(entity.getTarget()) < 5;
    }

    @Override
    public void start() {
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
