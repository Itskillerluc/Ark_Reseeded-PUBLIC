package com.huskytacodile.alternacraft.entities.smalldinoai;

import com.huskytacodile.alternacraft.config.AlternacraftConfig;
import com.huskytacodile.alternacraft.entities.dinos.SmallCarnivoreEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

public class SmallDinoNocturnalSleepGoal extends Goal {

	public SmallCarnivoreEntity entity;

	public SmallDinoNocturnalSleepGoal(SmallCarnivoreEntity sleeper) {
		super();
		this.entity = sleeper;
	}
	
	@Override
	public boolean canUse() {
		Level world = entity.level;
		if (AlternacraftConfig.sleepingAi = true && world.getDayTime() >= 0 && world.getDayTime() <= 12000 && entity.getLastHurtByMob() == null && entity.getTarget() == null && !entity.isTame() && !entity.isInPowderSnow && !entity.isInWater()) {
			return true;
		} else return false;
	}
	
	@Override
	public boolean canContinueToUse() {
		Level world = entity.level;
		if (world.getDayTime() >= 12000 && world.getDayTime() < 24000 || entity.getLastHurtByMob() != null || entity.getTarget() != null || entity.isTame() || entity.isInPowderSnow || entity.isInWater()) {
			stop();
			return false;
		} else return true;
	}
	
	@Override
	public void start() {
		entity.setAsleep(true);
		entity.setNaturallySitting(false);
		entity.getNavigation().stop();
	}
	
	public void tick() {
		super.tick();
		Level world = entity.level;
		if (world.getDayTime() >= 12000 && world.getDayTime() < 24000 || entity.getLastHurtByMob() != null || entity.getTarget() != null || entity.isTame() || entity.isInPowderSnow || entity.isInWater()) {
			stop();
		}
	}
	
	@Override
	public void stop() {
		entity.setAsleep(false);
	}

}
