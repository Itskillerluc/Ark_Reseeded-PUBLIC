package com.huskytacodile.alternacraft.entities.dinos.carnivore.large;

import net.minecraft.world.entity.ai.goal.*;
import org.jetbrains.annotations.Nullable;

import com.huskytacodile.alternacraft.entities.ai.DinoSittingGoal;
import com.huskytacodile.alternacraft.entities.ai.DiurnalSleepGoal;
import com.huskytacodile.alternacraft.entities.ai.SleepingRandomLookAroundGoal;
import com.huskytacodile.alternacraft.entities.dinos.LargeCarnivoreEntity;
import com.huskytacodile.alternacraft.util.ModSoundEvents;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class AcroEntity extends LargeCarnivoreEntity {
    public AcroEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.setTame(false);
    }

    public static AttributeSupplier.Builder attributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 82.00D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(4, new SleepingRandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new DinoSittingGoal(this));
        this.goalSelector.addGoal(4, new DiurnalSleepGoal(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(Items.NETHERITE_SWORD), false));
        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this,0,1));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(this, Animal.class,
                false, getPreySelection(this)));
    }
    
    public void aiStep() {
    	super.aiStep();
    	if (this.isAsleep() || this.isNaturallySitting()) {
    		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0D);
    	} else {
    		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2D);
    	}
    }

    @Override
    public String getAnimationName() {
        return "acrocanthosaurus";
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isAsleep() ? null : ModSoundEvents.ACRO_GROWL.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.ACRO_ROAR1.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSoundEvents.ACRO_ROAR2.get();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        return null;
    }
}