package com.huskytacodile.alternacraft.entities.other;

import com.huskytacodile.alternacraft.entities.ModEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.function.Consumer;

public class FireEntity extends AbstractHurtingProjectile {
    Consumer<EntityHitResult> entityHitResultConsumer;
    Consumer<BlockHitResult> blockHitResultConsumer;
    public FireEntity(EntityType<? extends FireEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public FireEntity(double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel, Consumer<EntityHitResult> entityHitResultConsumer, Consumer<BlockHitResult> blockHitResultConsumer) {
        super(ModEntityTypes.FIRE.get(), pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
        this.blockHitResultConsumer = blockHitResultConsumer;
        this.entityHitResultConsumer = entityHitResultConsumer;
    }

    public FireEntity(LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel, Consumer<EntityHitResult> entityHitResultConsumer, Consumer<BlockHitResult> blockHitResultConsumer) {
        super(ModEntityTypes.FIRE.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
        this.blockHitResultConsumer = blockHitResultConsumer;
        this.entityHitResultConsumer = entityHitResultConsumer;
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if (blockHitResultConsumer != null){
            blockHitResultConsumer.accept(pResult);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if (entityHitResultConsumer != null) {
            entityHitResultConsumer.accept(pResult);
        }
    }
}
