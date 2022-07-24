package com.huskytacodile.alternacraft.entities.smalldinoai;

import com.huskytacodile.alternacraft.entities.dinos.AlternaDinoEntity;
import com.huskytacodile.alternacraft.entities.dinos.SmallCarnivoreEntity;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;

public class SmallDinoSleepingRandomLookAroundGoal extends RandomLookAroundGoal {

	SmallCarnivoreEntity entity;

	public SmallDinoSleepingRandomLookAroundGoal(SmallCarnivoreEntity entity) {
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
