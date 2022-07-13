package com.huskytacodile.alternacraft.entities.ai;

import com.huskytacodile.alternacraft.entities.dinos.AlternaDinoEntity;

import net.minecraft.world.entity.ai.goal.Goal;

public class DinoSittingGoal extends Goal {
	
	AlternaDinoEntity entity;
	
	public DinoSittingGoal(AlternaDinoEntity sitter) {
		super();
		this.entity = sitter;
	}
	
	@Override
	public boolean canUse() {
		if (!entity.isAsleep() && !entity.isInWater() && !entity.isInPowderSnow && !entity.isTame() && entity.getLastHurtByMob() == null && entity.getTarget() == null && entity.getRandom().nextInt(750) == 0) {
			return true;
		} else return false;
	}
	
	@Override
	public boolean canContinueToUse() {
		if (entity.isAsleep() || entity.isInWater() || entity.isInPowderSnow || entity.isTame() || entity.getLastHurtByMob() != null || entity.getTarget() != null || entity.getRandom().nextInt(750) == 0) {
			stop();
			return false;
		} else return true;
	}
	
	public void tick() {
		super.tick();
		if (entity.isAsleep() || entity.isInWater() || entity.isInPowderSnow || entity.isTame() || entity.getLastHurtByMob() != null || entity.getTarget() != null || entity.getRandom().nextInt(750) == 0) {
			stop();
		}
	}
	
	@Override
	public void start() {
		entity.setNaturallySitting(true);
		entity.getNavigation().stop();
	}
	
	@Override
	public void stop() {
		entity.setNaturallySitting(false);
	}

}
