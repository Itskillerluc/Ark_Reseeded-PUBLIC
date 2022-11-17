package com.huskytacodile.alternacraft.entities.other;

import com.huskytacodile.alternacraft.entities.ModEntityTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
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
    public boolean displayFireAnimation() {
        return false;
    }

    public void tick() {
        Entity entity = this.getOwner();
        if (this.level.isClientSide || (entity == null || !entity.isRemoved()) && this.level.hasChunkAt(this.blockPosition())) {
            super.tick();
            if (this.shouldBurn()) {
                this.setSecondsOnFire(1);
            }

            HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
            if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
            }

            this.checkInsideBlocks();
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = this.getX() + vec3.x;
            double d1 = this.getY() + vec3.y;
            double d2 = this.getZ() + vec3.z;
            ProjectileUtil.rotateTowardsMovement(this, 0.2F);
            float f = this.getInertia();
            if (this.isInWater()) {
                for(int i = 0; i < 4; ++i) {
                    float f1 = 0.25F;
                    this.level.addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * 0.25D, d1 - vec3.y * 0.25D, d2 - vec3.z * 0.25D, vec3.x, vec3.y, vec3.z);
                }

                f = 0.8F;
            }

            this.setDeltaMovement(vec3.add(this.xPower, this.yPower, this.zPower).scale((double)f));
            this.setPos(d0, d1, d2);
        } else {
            this.discard();
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
