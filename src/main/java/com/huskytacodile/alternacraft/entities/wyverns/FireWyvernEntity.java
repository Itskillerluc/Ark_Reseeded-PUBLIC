package com.huskytacodile.alternacraft.entities.wyverns;

import com.huskytacodile.alternacraft.entities.ModEntityTypes;
import com.huskytacodile.alternacraft.entities.variant.GenderVariant;
import com.huskytacodile.alternacraft.entities.variant.IVariant;
import com.huskytacodile.alternacraft.entities.variant.RarityVariant;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class FireWyvernEntity extends WyvernEntity{
    public FireWyvernEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_, @Nullable CompoundTag p_146750_) {
        GenderVariant variant = Util.getRandom(GenderVariant.values(), this.random);
        setVariant(variant);
        return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
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
