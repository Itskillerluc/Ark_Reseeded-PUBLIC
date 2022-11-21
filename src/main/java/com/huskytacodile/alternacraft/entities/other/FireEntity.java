package com.huskytacodile.alternacraft.entities.other;

import com.huskytacodile.alternacraft.entities.ModEntityTypes;
import com.huskytacodile.alternacraft.entities.wyverns.WyvernEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.BiConsumer;

public class FireEntity extends AbstractHurtingProjectile {
    BiConsumer<FireEntity, EntityHitResult> entityHitResultConsumer;
    BiConsumer<FireEntity, BlockHitResult> blockHitResultConsumer;
    protected static final EntityDataAccessor<ItemStack> ITEM = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.ITEM_STACK);

    public FireEntity(EntityType<? extends FireEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public FireEntity(double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel, BiConsumer<FireEntity, EntityHitResult> entityHitResultConsumer, BiConsumer<FireEntity, BlockHitResult> blockHitResultConsumer, ItemStack item) {
        super(ModEntityTypes.FIRE.get(), pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
        this.blockHitResultConsumer = blockHitResultConsumer;
        this.entityHitResultConsumer = entityHitResultConsumer;
        this.setItem(item);
    }

    public FireEntity(LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel, BiConsumer<FireEntity, EntityHitResult> entityHitResultConsumer, BiConsumer<FireEntity, BlockHitResult> blockHitResultConsumer, ItemStack item) {
        super(ModEntityTypes.FIRE.get(), pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
        this.blockHitResultConsumer = blockHitResultConsumer;
        this.entityHitResultConsumer = entityHitResultConsumer;
        this.setItem(item);
    }

    public ItemStack getItem(){
        return this.entityData.get(ITEM);
    }

    public void setItem(ItemStack item){
        this.entityData.set(ITEM, item);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ITEM, new ItemStack(Items.AIR));
    }
    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        var tag = getItem().save(new CompoundTag());
        pCompound.put("item", tag);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        setItem(ItemStack.of(pCompound.getCompound("item")));
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    public void tick() {
        Entity entity = this.getOwner();
        if (this.level.isClientSide || (entity == null || !entity.isRemoved()) && this.level.hasChunkAt(this.blockPosition())) {
            super.tick();
            if (Math.abs(this.getDeltaMovement().x()) + Math.abs(this.getDeltaMovement().y()) + Math.abs(this.getDeltaMovement().z()) < 0.1) {
                this.remove(RemovalReason.DISCARDED);
            }
        }
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
