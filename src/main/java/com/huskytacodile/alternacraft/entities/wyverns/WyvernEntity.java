package com.huskytacodile.alternacraft.entities.wyverns;

import com.huskytacodile.alternacraft.entities.dinos.AlternaDinoEntity;
import com.huskytacodile.alternacraft.entities.dinos.CarnivoreEntity;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Optional;
import java.util.UUID;

public class WyvernEntity extends Animal implements FlyingAnimal, IAnimatable, OwnableEntity, PlayerRideableFlying {
    protected static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ASLEEP = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> KNOCKOUT = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> DATA_SADDLE_ID = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Optional<UUID>> OWNER = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    protected AnimationFactory factory = GeckoLibUtil.createFactory(this);


    public WyvernEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @SuppressWarnings("deprecation")
    public boolean isFood(ItemStack stack) {
        Item item = stack.getItem();
        return item.isEdible() && item.getFoodProperties().isMeat();
    }

    public void setOwner(@Nullable UUID uuid){
        this.entityData.set(OWNER, Optional.ofNullable(uuid));
    }

    public Optional<UUID> getOwnerData(){
        return this.entityData.get(OWNER);
    }

    public void setAttacking(boolean attack) {
        this.entityData.set(ATTACKING, attack);
    }
    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    @Override
    public boolean isFlying() {
        return false;
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return null;
    }

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return getOwnerData().orElse(null);
    }

    @Nullable
    @Override
    public Entity getOwner() {
        if (getOwnerUUID() == null){
            return null;
        }
        return ((ServerLevel) this.level).getEntity(getOwnerUUID());
    }

    @Override
    public void onPlayerJump(int p_21696_) {

    }

    @Override
    public boolean canJump() {
        return false;
    }

    @Override
    public void handleStartJump(int p_21695_) {

    }

    @Override
    public void handleStopJump() {

    }

    @Override
    public boolean canLower() {
        return false;
    }

    @Override
    public void onPlayerLower() {

    }
}
