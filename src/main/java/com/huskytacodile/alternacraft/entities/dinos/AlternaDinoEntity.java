package com.huskytacodile.alternacraft.entities.dinos;

import com.huskytacodile.alternacraft.entities.variant.GenderVariant;
import com.huskytacodile.alternacraft.entities.variant.IVariant;
import com.huskytacodile.alternacraft.item.ModItems;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class AlternaDinoEntity extends TamableAnimal implements IAnimatable {
    protected AnimationFactory factory = new AnimationFactory(this);

    public AlternaDinoEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    protected static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT =
            SynchedEntityData.defineId(AlternaDinoEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> DATA_SADDLE_ID =
            SynchedEntityData.defineId(AlternaDinoEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> DATA_BOOST_TIME =
            SynchedEntityData.defineId(AlternaDinoEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> SITTING =
            SynchedEntityData.defineId(AlternaDinoEntity.class, EntityDataSerializers.BOOLEAN);

    protected final ItemBasedSteering steering = new ItemBasedSteering(this.entityData, DATA_BOOST_TIME,DATA_SADDLE_ID);


    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty,
                                        MobSpawnType mobSpawnType, @Nullable SpawnGroupData groupData,
                                        @Nullable CompoundTag tag) {
        IVariant variant = Util.getRandom(GenderVariant.values(), this.random);
        setVariant(variant);

        return super.finalizeSpawn(world, difficulty, mobSpawnType, groupData, tag);
    }

    protected Item getTamingItem() {
        return ModItems.TOTEM_OF_HUGO.get();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();

        if (this.level.isClientSide) {
            boolean flag = this.isOwnedBy(player) || this.isTame()
                    || item == getTamingItem() && !this.isTame();
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else {
            if (this.isTame()) {
                if(player.isCrouching() && hand == InteractionHand.MAIN_HAND) {
                    setSitting(!isSitting());
                }

                if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                    if (!player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                    this.heal((float)item.getFoodProperties().getNutrition());
                    return InteractionResult.SUCCESS;
                }

                player.startRiding(this);

            } else if (item == Items.NETHERITE_SWORD && !this.isOnFire()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                if (this.random.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                    this.tame(player);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.setOrderedToSit(true);
                    this.level.broadcastEntityEvent(this, (byte)7);
                } else {
                    this.level.broadcastEntityEvent(this, (byte)6);
                }
                return InteractionResult.SUCCESS;
            }
            return super.mobInteract(player, hand);
        }
    }

    public boolean canBreatheUnderwater() {
        return false;
    }

    public Vec3 getDismountLocationForPassenger(LivingEntity p_230268_1_) {
        Direction direction = this.getMotionDirection();
        if (direction.getAxis() == Direction.Axis.Y) {
            return super.getDismountLocationForPassenger(p_230268_1_);
        } else {
            int[][] aint = DismountHelper.offsetsForDirection(direction);
            BlockPos blockpos = this.blockPosition();
            BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

            for(Pose pose : p_230268_1_.getDismountPoses()) {
                AABB axisalignedbb = p_230268_1_.getLocalBoundsForPose(pose);

                for(int[] aint1 : aint) {
                    blockpos$mutable.set(blockpos.getX() + aint1[0], blockpos.getY(), blockpos.getZ() + aint1[1]);
                    double d0 = this.level.getBlockFloorHeight(blockpos$mutable);
                    if (DismountHelper.isBlockFloorValid(d0)) {
                        Vec3 vec3 = Vec3.upFromBottomCenterOf(blockpos$mutable, d0);
                        if (DismountHelper.canDismountTo(this.level, p_230268_1_, axisalignedbb.move(vec3))) {
                            p_230268_1_.setPose(pose);
                            return vec3;
                        }
                    }
                }
            }

            return super.getDismountLocationForPassenger(p_230268_1_);
        }
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.FISH_SWIM;
    }

    protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
    }

    @javax.annotation.Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    public boolean canBeControlledByRider() {
        Entity entity = this.getControllingPassenger();
        if (!(entity instanceof Player)) {
            return false;
        } else {
            Player playerentity = (Player)entity;
            return playerentity.getMainHandItem().getItem() == Items.NETHERITE_SWORD || playerentity.getOffhandItem().getItem() == Items.NETHERITE_SWORD;
        }
    }

    public void setSitting(boolean sitting) {
        this.entityData.set(SITTING, sitting);
        this.setOrderedToSit(sitting);
    }

    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }

    protected void setVariant(IVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
    }

    public IVariant getVariant() {
        return GenderVariant.byId(this.getTypeVariant() & 255);
    }

    protected int getTypeVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
        this.entityData.define(SITTING, false);
    }

    public abstract String getAnimationName();

    protected  <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (!(animationSpeed > -0.10F && animationSpeed < 0.05F)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation." + getAnimationName() + ".walk", true));
            return PlayState.CONTINUE;
        }
        if (this.isAggressive() && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation." + getAnimationName() + ".attack", true));
            return PlayState.CONTINUE;
        }
        if (this.isOrderedToSit() || this.getHealth() < 0.01 || this.isDeadOrDying()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation." + getAnimationName() + ".idle", true));
            return PlayState.CONTINUE;
        }
        if (this.isSwimming() && !(animationSpeed > -0.10F && animationSpeed < 0.05F) && !this.isAggressive()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation." + getAnimationName() + ".walk", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation." + getAnimationName() + ".idle", true));

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController
                (this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public boolean doHurtTarget(Entity p_70652_1_) {
        boolean flag = super.doHurtTarget(p_70652_1_);
        if (flag) {
            this.doEnchantDamageEffects(this, p_70652_1_);
        }

        return flag;
    }

    public boolean canBeLeashed(Player player){
        return false;
    }
}
