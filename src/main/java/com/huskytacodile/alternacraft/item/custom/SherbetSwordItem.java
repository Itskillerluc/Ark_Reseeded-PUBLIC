package com.huskytacodile.alternacraft.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class SherbetSwordItem extends SwordItem {
    public SherbetSwordItem(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pTarget.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100 ), pAttacker);
        pTarget.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 250 ), pAttacker);
        pTarget.addEffect(new MobEffectInstance(MobEffects.WITHER, 250 ), pAttacker);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

}
