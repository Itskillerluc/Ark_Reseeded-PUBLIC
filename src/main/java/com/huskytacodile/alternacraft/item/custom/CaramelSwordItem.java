package com.huskytacodile.alternacraft.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class CaramelSwordItem extends SwordItem {
    public CaramelSwordItem(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100 ), pAttacker);
        pTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 250 ), pAttacker);
        pTarget.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 250 ), pAttacker);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

}
