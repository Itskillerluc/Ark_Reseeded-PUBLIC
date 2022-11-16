package com.huskytacodile.alternacraft.entities.other;

import com.huskytacodile.alternacraft.entities.ModEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class FireEntity extends AbstractHurtingProjectile {
    BiConsumer<FireEntity, EntityHitResult> entityHitResultConsumer;
    BiConsumer<FireEntity, BlockHitResult> blockHitResultConsumer;
    public final ItemStack item;
    public FireEntity(EntityType<? extends FireEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.item = ItemStack.EMPTY;
    }

    public FireEntity(double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel, BiConsumer<FireEntity, EntityHitResult> entityHitResultConsumer, BiConsumer<FireEntity, BlockHitResult> blockHitResultConsumer, ItemStack item) {
        super(ModEntityTypes.FIRE.get(), pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
        this.blockHitResultConsumer = blockHitResultConsumer;
        this.entityHitResultConsumer = entityHitResultConsumer;
        this.item = item;
    }

    public FireEntity(LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel, BiConsumer<FireEntity, EntityHitResult> entityHitResultConsumer, BiConsumer<FireEntity, BlockHitResult> blockHitResultConsumer, ItemStack item) {
        super(ModEntityTypes.FIRE.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
        this.blockHitResultConsumer = blockHitResultConsumer;
        this.entityHitResultConsumer = entityHitResultConsumer;
        this.item = item;
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult pResult) {
        if (blockHitResultConsumer != null){
            blockHitResultConsumer.accept(this, pResult);
        }
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        if (entityHitResultConsumer != null) {
            entityHitResultConsumer.accept(this, pResult);
        }
    }
}
