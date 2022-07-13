package com.huskytacodile.alternacraft.entities.ai;

import com.huskytacodile.alternacraft.entities.dinos.AlternaDinoEntity;

import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;

public class SleepingRandomLookAroundGoal extends RandomLookAroundGoal {
	
	AlternaDinoEntity entity;
	
	public SleepingRandomLookAroundGoal(AlternaDinoEntity entity) {
		super(entity);
		this.entity = entity;
	}
	
	public boolean canUse() {
		return super.canUse() && !entity.isAsleep();
	}
	
	public boolean canContinueToUse() {
		return super.canContinueToUse() && !entity.isAsleep();
	}

}
