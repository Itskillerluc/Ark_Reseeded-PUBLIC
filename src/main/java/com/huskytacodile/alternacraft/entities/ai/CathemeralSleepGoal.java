package com.huskytacodile.alternacraft.entities.ai;

import com.huskytacodile.alternacraft.config.AlternacraftConfig;
import com.huskytacodile.alternacraft.entities.dinos.AlternaDinoEntity;

import net.minecraft.world.entity.ai.goal.Goal;

public class CathemeralSleepGoal extends Goal {
	
	public AlternaDinoEntity entity;
	private int sleepTimer;
	
	public CathemeralSleepGoal(AlternaDinoEntity sleeper) {
		super();
		this.entity = sleeper;
	}
	
	@Override
	public boolean canUse() {
		if (AlternacraftConfig.sleepingAi = true && entity.getRandom().nextInt(1000) == 0 && entity.getLastHurtByMob() == null && entity.getTarget() == null && !entity.isInPowderSnow && !entity.isInWater() && !entity.isTame()) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean canContinueToUse() {
		if (sleepTimer >= 6000 || entity.getLastHurtByMob() != null || !super.canContinueToUse() || entity.isInWater() || entity.isInPowderSnow || entity.getTarget() != null || entity.isTame()) {
			stop();
			return false;
		} else return true;
	}
	
	public void tick() {
		super.tick();
		sleepTimer++;
		if (sleepTimer >= 6000 || entity.getLastHurtByMob() != null || entity.getTarget() != null || entity.isInWater() || entity.isInPowderSnow || entity.isTame()) {
			stop();
		}
	}
	
	@Override
	public void start() {
		sleepTimer = 0;
		entity.setAsleep(true);
		entity.setNaturallySitting(false);
		entity.getNavigation().stop();
	}
	
	@Override
	public void stop() {
		entity.setAsleep(false);
	}

}
