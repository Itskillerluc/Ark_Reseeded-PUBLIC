package com.huskytacodile.alternacraft.entities.dinos;

import com.huskytacodile.alternacraft.entities.Sleeping;
import com.huskytacodile.alternacraft.entities.variant.IVariant;
import com.huskytacodile.alternacraft.item.ModItems;
import net.minecraft.commands.arguments.EntityAnchorArgument;
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
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class AlternaDinoEntity extends TamableAnimal implements GeoAnimatable, Sleeping {
	
	private static final EntityDataAccessor<Boolean> ASLEEP = SynchedEntityData.defineId(AlternaDinoEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> KNOCKOUT = SynchedEntityData.defineId(AlternaDinoEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> NATURAL_SITTING = SynchedEntityData.defineId(AlternaDinoEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> TAMING = SynchedEntityData.defineId(AlternaDinoEntity.class, EntityDataSerializers.INT);
	
    protected AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

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

        return super.finalizeSpawn(world, difficulty, mobSpawnType, groupData, tag);
    }

    @Override
    public void tick() {
        super.tick();
        if (getKnockout() > 0){
            setAsleep(true);
            setTaming(getTaming() + 1);
            setKnockout(Math.max(getKnockout()-1, 0));
            if (getTaming() >= this.getTameTime()){
                this.setKnockout(0);
                this.setAsleep(false);
            }
            if (getKnockout() == 0){
                setAsleep(false);
            }
        }
    }

    protected Item getTamingItem() {
        return ModItems.TOTEM_OF_HUGO.get();
    }

    @SuppressWarnings("deprecation")
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

    @Override
    public void lookAt(EntityAnchorArgument.Anchor pAnchor, Vec3 pTarget) {
        super.lookAt(pAnchor, pTarget);
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
    public void setKnockout(int knockoutTime){
        this.entityData.set(KNOCKOUT, knockoutTime);
    }

    public int getKnockout(){
        return this.entityData.get(KNOCKOUT);
    }

    public void setTaming(int taming){
        this.entityData.set(TAMING, taming);
    }
    public int getTaming(){
        return this.entityData.get(TAMING);
    }

    public void setSitting(boolean sitting) {
        this.entityData.set(SITTING, sitting);
        this.setOrderedToSit(sitting);
    }

    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }

    //TODO: make abstract
    public int getTameTime() {
        return 2000;
    }
    protected void setVariant(IVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.getTypeVariant());
        tag.putBoolean("IsAsleep", this.isAsleep());
        tag.putBoolean("NaturallySitting", this.isNaturallySitting());
        tag.putInt("knockoutTime", this.getKnockout());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
        this.entityData.set(ASLEEP, tag.getBoolean("IsAsleep"));
        this.entityData.set(KNOCKOUT, tag.getInt("knockoutTime"));
        this.entityData.set(NATURAL_SITTING, tag.getBoolean("NaturallySitting"));
    }

    @Override
    public boolean isAsleep() {
    	return this.entityData.get(ASLEEP);
    }

    @Override
    public void setAsleep(boolean isAsleep) {
    	this.entityData.set(ASLEEP, isAsleep);
    }
    
    public boolean isNaturallySitting() {
    	return this.entityData.get(NATURAL_SITTING);
    }
    
    public void setNaturallySitting(boolean isSitting) {
    	this.entityData.set(NATURAL_SITTING, isSitting);
    }

    public IVariant getVariant() {
        return null;
    }

    protected int getTypeVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
        this.entityData.define(SITTING, false);
    	this.entityData.define(ASLEEP, false);
    	this.entityData.define(NATURAL_SITTING, false);
        this.entityData.define(KNOCKOUT, 0);
    }

    public abstract String getAnimationName();

    protected  <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
        if (!(animationSpeed > -0.10F && animationSpeed < 0.05F)) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation." + getAnimationName() + ".walk"));
            return PlayState.CONTINUE;
        }
        if (this.isAggressive() && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation." + getAnimationName() + ".attack"));
            return PlayState.CONTINUE;
        }
        if (this.isSitting() || this.getHealth() < 0.01 || this.isDeadOrDying() || this.isNaturallySitting()) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation." + getAnimationName() + ".sit"));
            return PlayState.CONTINUE;
        }
        if (this.isSwimming() && !(animationSpeed > -0.10F && animationSpeed < 0.05F) && !this.isAggressive()) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation." + getAnimationName() + ".walk"));
            return PlayState.CONTINUE;
        }
        if (this.isAsleep() || this.getHealth() < 0.01 || this.isDeadOrDying()) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation." + getAnimationName() + ".sleep"));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(RawAnimation.begin().thenLoop("animation." + getAnimationName() + ".idle"));

        return PlayState.CONTINUE;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
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

    @Override
    public double getTick(Object object) {
        return this.tickCount;
    }
}
