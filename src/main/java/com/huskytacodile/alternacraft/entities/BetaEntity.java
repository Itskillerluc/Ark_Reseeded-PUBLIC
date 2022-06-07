package com.huskytacodile.alternacraft.entities;

import com.huskytacodile.alternacraft.item.ModItems;
import com.huskytacodile.alternacraft.util.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
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

import java.util.function.Predicate;

public class BetaEntity extends TamableAnimal implements IAnimatable, ItemSteerable {
    private AnimationFactory factory = new AnimationFactory(this);
    private static final EntityDataAccessor<Boolean> DATA_SADDLE_ID = SynchedEntityData.defineId(Pig.class, EntityDataSerializers.BOOLEAN);
    private final ItemBasedSteering steering = new ItemBasedSteering(this.entityData, DATA_BOOST_TIME,DATA_SADDLE_ID);
    private static final EntityDataAccessor<Integer> DATA_BOOST_TIME = SynchedEntityData.defineId(Pig.class, EntityDataSerializers.INT);
    public static final Predicate<LivingEntity> PREY_SELECTOR = (p_30437_) -> {
        EntityType<?> entitytype = p_30437_.getType();
        return entitytype == EntityType.SHEEP || entitytype == EntityType.RABBIT
                || entitytype == EntityType.COW || entitytype == EntityType.CHICKEN || entitytype == EntityType.PIG;
    };
    private static final EntityDataAccessor<Boolean> SITTING =
            SynchedEntityData.defineId(BetaEntity.class, EntityDataSerializers.BOOLEAN);

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_, @Nullable CompoundTag p_146750_) {
        return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_21815_) {
        super.readAdditionalSaveData(p_21815_);
    }
    protected BetaEntity(EntityType<? extends TamableAnimal> p_i48575_1_, Level p_i48575_2_) {
        super(p_i48575_1_, p_i48575_2_);
        this.setTame(false);
    }
    public boolean isFood(ItemStack p_70877_1_) {
        Item item = p_70877_1_.getItem();
        return item.isEdible() && item.getFoodProperties().isMeat();
    }


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SITTING, false);
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return ModSoundEvents.BETA_AMBIENT.get();
    }


    @Override
    protected SoundEvent getDeathSound()
    {
        return ModSoundEvents.BLUE_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return ModSoundEvents.BLUE_HURT.get();
    }
    public boolean doHurtTarget(Entity p_70652_1_) {
        boolean flag = super.doHurtTarget(p_70652_1_);
        if (flag) {
            this.doEnchantDamageEffects(this, p_70652_1_);
        }

        return flag;
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (!(animationSpeed > -0.10F && animationSpeed < 0.05F) && !this.isAggressive() && event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.blue.run", true));
            return PlayState.CONTINUE;
        }
        if (this.isAggressive() && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.blue.attack", true));
            return PlayState.CONTINUE;
        }
        if (this.isSitting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.blue.sitting", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.blue.idle", true));

        return PlayState.CONTINUE;
    }

    public void setSitting(boolean sitting) {
        this.entityData.set(SITTING, sitting);
        this.setOrderedToSit(sitting);
    }

    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<BetaEntity>
                (this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public static AttributeSupplier.Builder attributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.00D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }
    public InteractionResult mobInteract(Player p_230254_1_, InteractionHand p_230254_2_) {
        ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
        Item item = itemstack.getItem();
        if (this.level.isClientSide) {
            boolean flag = this.isOwnedBy(p_230254_1_) || this.isTame() || item == ModItems.TOTEM_OF_HUGO.get() && !this.isTame();
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;

        } else {
            if (this.isTame()) {

                if(p_230254_1_.isCrouching() && p_230254_2_ == InteractionHand.MAIN_HAND) {
                    setSitting(!isSitting());
                }

                if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                    if (!p_230254_1_.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    this.heal((float)item.getFoodProperties().getNutrition());
                    return InteractionResult.SUCCESS;
                }
                p_230254_1_.startRiding(this);
            } else if (item == Items.NETHERITE_SWORD && !this.isOnFire()) {
                if (!p_230254_1_.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                if (this.random.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, p_230254_1_)) {
                    this.tame(p_230254_1_);
                    this.navigation.stop();

                    this.setTarget((LivingEntity)null);
                    this.setOrderedToSit(true);
                    this.level.broadcastEntityEvent(this, (byte)7);
                } else {
                    this.level.broadcastEntityEvent(this, (byte)6);
                }

                return InteractionResult.SUCCESS;
            }

            return super.mobInteract(p_230254_1_, p_230254_2_);

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
    public void travel(Vec3 p_213352_1_) {
        this.travel(this, this.steering, p_213352_1_);
    }

    public float getSteeringSpeed() {
        return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.550F;
    }

    @Override
    public boolean travel(Mob p_233622_1_, ItemBasedSteering p_233622_2_, Vec3 p_233622_3_) {
        return ItemSteerable.super.travel(p_233622_1_, p_233622_2_, p_233622_3_);
    }

    @Override
    public boolean boost() {
        return false;
    }

    public void travelWithInput(Vec3 p_230267_1_) {
        super.travel(p_230267_1_);

    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(Items.NETHERITE_SWORD), false));
        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this,0,1));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
    }
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }
}