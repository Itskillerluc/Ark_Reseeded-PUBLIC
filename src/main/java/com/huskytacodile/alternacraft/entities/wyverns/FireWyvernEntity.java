package com.huskytacodile.alternacraft.entities.wyverns;

import com.huskytacodile.alternacraft.entities.attackgoal.WyvernFireAttackGoal;
import com.huskytacodile.alternacraft.entities.other.FireEntity;
import com.huskytacodile.alternacraft.entities.variant.GenderVariant;
import com.huskytacodile.alternacraft.entities.variant.IVariant;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FireWyvernEntity extends WyvernEntity{
    public FireWyvernEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor p_146746_, @NotNull DifficultyInstance p_146747_, @NotNull MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_, @Nullable CompoundTag p_146750_) {
        GenderVariant variant = Util.getRandom(GenderVariant.values(), this.random);
        setVariant(variant);
        return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new WyvernFireAttackGoal(this, () -> new FireEntity(this, 0, 0, 0, this.level, this::fireEntityHit, this::fireBlockHit, new ItemStack(Items.FIRE_CHARGE))));
    }

    private void fireBlockHit(FireEntity entity, BlockHitResult result){
        if (!this.level.isClientSide) {
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, entity)) {
                BlockPos blockpos = result.getBlockPos().relative(result.getDirection());
                if (this.level.isEmptyBlock(blockpos)) {
                    this.level.setBlockAndUpdate(blockpos, BaseFireBlock.getState(this.level, blockpos));
                }
            }

        }
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    private void fireEntityHit(FireEntity entity, EntityHitResult result){
        var target = result.getEntity();
        if (!this.level.isClientSide && target.isAlive() && target != entity.getOwner()) {
            target.setSecondsOnFire(5);
        }
    }

    @Override
    public IVariant getVariant() {
        return GenderVariant.byId(this.getTypeVariant() & 255);
    }

    @Override
    public String getAnimationName() {
        return "wyvern";
    }

    public static AttributeSupplier.Builder attributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 92.00D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 9.0D)
                .add(Attributes.FLYING_SPEED, 0.2D);
    }
}
