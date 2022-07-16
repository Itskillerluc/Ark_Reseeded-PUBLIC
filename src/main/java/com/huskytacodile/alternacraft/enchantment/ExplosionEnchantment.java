package com.huskytacodile.alternacraft.enchantment;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class ExplosionEnchantment extends Enchantment {

    protected ExplosionEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        if(!pAttacker.level.isClientSide()) {
            ServerLevel world = ((ServerLevel) pAttacker.level);
            ServerPlayer player = ((ServerPlayer) pAttacker);
            BlockPos position = pTarget.blockPosition();

            if(pLevel == 1) {
                EntityType.TNT_MINECART.spawn(world, null, player, position,
                        MobSpawnType.TRIGGERED, true, true);

                EntityType.TNT_MINECART.spawn(world, null, player, position,
                        MobSpawnType.TRIGGERED, true, true);
            }

            if(pLevel == 2) {
                EntityType.TNT_MINECART.spawn(world, null, player, position,
                        MobSpawnType.TRIGGERED, true, true);

                EntityType.TNT_MINECART.spawn(world, null, player, position,
                        MobSpawnType.TRIGGERED, true, true);

                EntityType.TNT_MINECART.spawn(world, null, player, position,
                        MobSpawnType.TRIGGERED, true, true);
            }

            if(pLevel == 3) {
                EntityType.TNT_MINECART.spawn(world, null, player, position,
                        MobSpawnType.TRIGGERED, true, true);

                EntityType.TNT_MINECART.spawn(world, null, player, position,
                        MobSpawnType.TRIGGERED, true, true);

                EntityType.TNT_MINECART.spawn(world, null, player, position,
                        MobSpawnType.TRIGGERED, true, true);

                EntityType.TNT_MINECART.spawn(world, null, player, position,
                        MobSpawnType.TRIGGERED, true, true);
            }
        }

        super.doPostAttack(pAttacker, pTarget, pLevel);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
