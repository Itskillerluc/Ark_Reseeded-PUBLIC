package com.huskytacodile.alternacraft.entities.dinos;

import java.util.function.Predicate;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public abstract class CarnivoreEntity extends AlternaDinoEntity {

    public CarnivoreEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    protected Predicate<LivingEntity> getPreySelection(Entity entity) {
        return (e) -> e.getType() != entity.getType() && (e.getType() == EntityType.SHEEP || e.getType() == EntityType.RABBIT
                || e.getType() == EntityType.COW || e.getType() == EntityType.CHICKEN || e.getType() == EntityType.PIG);
    }

    @SuppressWarnings("deprecation")
	public boolean isFood(ItemStack stack) {
        Item item = stack.getItem();
        return item.isEdible() && item.getFoodProperties().isMeat();
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if(this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)){
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation." + this.getAnimationName() + ".attack", false));
            this.swinging = false;
        }
            return PlayState.CONTINUE;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void registerControllers(AnimationData data) {
        super.registerControllers(data);
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
        data.addAnimationController(new AnimationController
                (this, "attackController", 0, this::attackPredicate));
    }

}
