package com.huskytacodile.alternacraft.entities.wyverns;

import com.huskytacodile.alternacraft.block.ModBlocks;
import com.huskytacodile.alternacraft.entities.ModEntityTypes;
import com.huskytacodile.alternacraft.entities.Sleeping;
import com.huskytacodile.alternacraft.entities.ai.*;
import com.huskytacodile.alternacraft.entities.attackgoal.WyvernMeleeAttackGoal;
import com.huskytacodile.alternacraft.entities.variant.IVariant;
import com.huskytacodile.alternacraft.misc.KeyBinds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

public abstract class WyvernEntity extends Animal implements FlyingAnimal, GeoAnimatable, OwnableEntity, PlayerRideableFlying, Sleeping {
    private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ASLEEP = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<UUID>> OWNER = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> RISK = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> FIRE_CHARGE = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> FIRE_ANIMATION = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.INT);

    protected AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    @Override
    public boolean isAggressive() {
        return super.isAggressive();
    }

    public WyvernEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    @Override
    public boolean canBreed() {
        return true;
    }

    @Override
    public boolean canMate(Animal pOtherAnimal) {
        return super.canMate(pOtherAnimal) && ((this.getTypeVariant() % 2 == 0 && ((WyvernEntity) pOtherAnimal).getTypeVariant() % 2 != 0) || (this.getTypeVariant() % 2 != 0 && ((WyvernEntity) pOtherAnimal).getTypeVariant() % 2 == 0));
    }

    @Override
    public void spawnChildFromBreeding(ServerLevel pLevel, Animal pMate) {
        ServerPlayer serverplayer = this.getLoveCause();
        if (serverplayer == null && pMate.getLoveCause() != null) {
            serverplayer = pMate.getLoveCause();
        }

        if (serverplayer != null) {
            serverplayer.awardStat(Stats.ANIMALS_BRED);
        }

        this.setAge(6000);
        pMate.setAge(6000);
        this.resetLove();
        pMate.resetLove();

        pLevel.setBlock(blockPosition(), ModBlocks.FIRE_WYVERN_EGG.get().defaultBlockState(), 2);

        pLevel.broadcastEntityEvent(this, (byte)18);
        if (pLevel.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            pLevel.addFreshEntity(new ExperienceOrb(pLevel, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
        }
    }

    @SuppressWarnings("deprecation")
    public boolean isFood(ItemStack stack) {
        Item item = stack.getItem();
        return item.isEdible() && item.getFoodProperties().isMeat() && this.getOwner() != null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
        this.entityData.define(ASLEEP, false);
        this.entityData.define(ATTACKING, false);
        this.entityData.define(OWNER, Optional.empty());
        this.entityData.define(RISK, 0);
        this.entityData.define(FIRE_CHARGE, 100);
        this.entityData.define(FIRE_ANIMATION, 0);
    }

    public void setOwner(@Nullable UUID uuid){
        this.entityData.set(OWNER, Optional.ofNullable(uuid));
    }

    public Optional<UUID> getOwnerData(){
        return this.entityData.get(OWNER);
    }

    public void setRisk(int risk){
        this.entityData.set(RISK, risk);
    }
    public int increaseRisk(int risk){
        this.entityData.set(RISK, this.entityData.get(RISK) + risk);
        return this.entityData.get(RISK);
    }
    public int getRisk(){
        return this.entityData.get(RISK);
    }
    public void setAttacking(boolean attack) {
        this.entityData.set(ATTACKING, attack);
    }
    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public int getFireCharge(){
        return this.entityData.get(FIRE_CHARGE);
    }
    public int getFireAnimation(){
        return this.entityData.get(FIRE_ANIMATION);
    }
    public void setFireCharge(int charge) {
        this.entityData.set(FIRE_CHARGE, charge);
    }
    public void setFireAnimation(int anim) {
        this.entityData.set(FIRE_ANIMATION, anim);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    @Override
    public boolean isFlying() {
        return !isOnGround() && !onClimbable();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return getOwnerData().orElse(null);
    }


    protected int getTypeVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    protected void setVariant(IVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.getTypeVariant());
        tag.putInt("Risk", this.getRisk());
        tag.putBoolean("IsAsleep", this.isAsleep());
        tag.putInt("charge", this.getFireCharge());
        if (this.getOwnerUUID() != null) {
            tag.putUUID("owner", this.getOwnerUUID());
        }
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, pLevel);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(false);
        return flyingpathnavigation;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
        this.entityData.set(RISK, tag.getInt("Risk"));
        this.entityData.set(ASLEEP, tag.getBoolean("IsAsleep"));
        this.entityData.set(FIRE_CHARGE, tag.getInt("charge"));
        if (tag.contains("owner")) setOwner(tag.getUUID("owner"));
        else if (tag.contains("ForgeData")) {
            setOwner(tag.getCompound("ForgeData").getUUID("owner"));
        }
    }

    public boolean isAsleep() {
        return this.entityData.get(ASLEEP);
    }

    public void setAsleep(boolean isAsleep) {
        this.entityData.set(ASLEEP, isAsleep);
    }

    @Nullable
    @Override
    public Entity getOwner() {
        try {
            UUID uuid = this.getOwnerUUID();
            return uuid == null ? null : this.level.getPlayerByUUID(uuid);
        } catch (IllegalArgumentException illegalargumentexception) {
            return null;
        }
    }

    @Override
    public void positionRider(Entity pPassenger) {
        this.positionRider(pPassenger, Entity::setPos);
    }

    private void positionRider(Entity pPassenger, Entity.MoveFunction pCallback) {
        if (this.hasPassenger(pPassenger)) {
            double d0 = this.getY() + this.getPassengersRidingOffset() + pPassenger.getMyRidingOffset();
            pCallback.accept(pPassenger, this.getX(), d0, this.getZ());
        }
    }

    public void setOwner(Player owner){
        this.setOwner(owner.getUUID());
    }

    @Override
    public void onPlayerJump(int p_21696_) {
    }

    @Override
    public void onJumpHold() {
        super.travel(new Vec3(0,  3, 0));
        setNoGravity(true);
    }

    @Override
    public boolean canJump(Player player) {
        return this.getOwner() != null;
    }

    @Override
    public void handleStartJump(int p_21695_) {
    }

    @Override
    public void handleStopJump() {
    }

    @Override
    public boolean canLower() {
        return this.getOwner() != null;
    }

    public abstract IVariant getVariant();

    @Override
    public void onPlayerLower() {
        super.travel(new Vec3(0,  -3, 0));
    }

    public void travel(Vec3 pTravelVector) {
        if (this.isAlive()) {
            if (this.isVehicle()) {
                LivingEntity livingentity = (LivingEntity)this.getControllingPassenger();
                this.setYRot(livingentity.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(livingentity.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;
                float f = livingentity.xxa * 0.5F;
                float f1 = livingentity.zza;
                if (f1 <= 0.0F) {
                    f1 *= 0.25F;
                }

                if (this.isControlledByLocalInstance()) {
                    this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    super.travel(new Vec3((double)f, pTravelVector.y, (double)f1));
                } else if (livingentity instanceof Player && !KeyBinds.FLY_DOWN_KEY.isDown() && !KeyBinds.FLY_UP_KEY.isDown()) {
                    this.setDeltaMovement(Vec3.ZERO);
                }

                this.calculateEntityAnimation(this, false);
                this.tryCheckInsideBlocks();
            } else {
                super.travel(pTravelVector);
            }
        }
    }

    @Override
    public @NotNull Vec3 getDismountLocationForPassenger(@NotNull LivingEntity p_230268_1_) {
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

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }


    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        super.mobInteract(player, hand);
        if (this.getOwner() != null) {
            if (player.isShiftKeyDown()) {
                var itemStack = player.getItemInHand(hand);
                if (this.isFood(itemStack) && this.getHealth() < this.getMaxHealth()) {
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                    this.heal((float) itemStack.getItem().getFoodProperties().getNutrition());
                    return InteractionResult.SUCCESS;
                }
            } else {
                player.startRiding(this);
            }
        }
        return InteractionResult.PASS;
    }

    public abstract String getAnimationName();

    protected  <E extends GeoAnimatable> PlayState attackPredicate(AnimationState<E> event){
        if (!isAttacking() && getFireAnimation() == 0){
            return PlayState.STOP;
        }

        if (isAttacking() && !isFlying() && getFireAnimation() == 0) {
            event.getController().forceAnimationReset();
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation." + getAnimationName() + ".attack_01"));
            return PlayState.CONTINUE;
        }

        if (getFireAnimation() == 1){
            event.getController().forceAnimationReset();
            event.getController().setAnimation(RawAnimation.begin().thenPlayAndHold("animation." + getAnimationName() + ".fire_start"));
            return PlayState.CONTINUE;
        }

        if (getFireAnimation() == 2 && getFireCharge() > 0){
            event.getController().forceAnimationReset();
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation." + getAnimationName() + ".fire_loop"));
            return PlayState.CONTINUE;
        }

        if (getFireAnimation() == 3){
            event.getController().forceAnimationReset();
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation." + getAnimationName() + ".fire_end"));
            setFireAnimation(0);
            return PlayState.CONTINUE;
        }
        if (this.isAggressive()  && !isFlying() && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())  && getFireAnimation() == 0) {
            event.getController().forceAnimationReset();
            event.getController().setAnimation(RawAnimation.begin().thenPlay("animation." + getAnimationName() + ".attack_01"));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;

    }

    protected  <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
        if (isAttacking() || getFireAnimation() > 0){
            return PlayState.STOP;
        }

        if (!(animationSpeed > -0.10F && animationSpeed < 0.05F) && !this.isFlying()) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation." + getAnimationName() + ".walk"));
            return PlayState.CONTINUE;
        }
        if (!(animationSpeed > -0.10F && animationSpeed < 0.05F) && this.isFlying()){
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation." + getAnimationName() + ".fly"));
            return PlayState.CONTINUE;
        }
        if (this.isSwimming() && !(animationSpeed > -0.10F && animationSpeed < 0.05F) && !this.isAggressive() && !isFlying()) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation." + getAnimationName() + ".walk"));
            return PlayState.CONTINUE;
        }
        if (this.isAsleep() || this.getHealth() < 0.01 || this.isDeadOrDying()) {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation." + getAnimationName() + ".sleep"));
            return PlayState.CONTINUE;
        }
        if (this.isFlying()){
            event.getController().setAnimation(RawAnimation.begin().thenLoop("animation." + getAnimationName() + ".idle_fly"));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(RawAnimation.begin().thenLoop("animation." + getAnimationName() + ".idle"));

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(
                this, "controller", 0, this::predicate));
        data.add(new AnimationController<>(
                this, "attackController", 0, this::attackPredicate
        ));
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    protected Predicate<Entity> getPreySelection(Entity entity) {
        Set<EntityType<?>> prey = Set.of(ModEntityTypes.TYRANNOSAURUS.get(), ModEntityTypes.GIGA.get(),
                ModEntityTypes.ALLOSAURUS.get(), ModEntityTypes.SPINO.get(), ModEntityTypes.OXALAIA.get(),
                ModEntityTypes.INDOMINUS.get(), ModEntityTypes.MALUSAURUS.get(), ModEntityTypes.INDORAPTOR.get(),
                ModEntityTypes.SCORPIUS.get());

        return e -> e.getType() != entity.getType() && prey.contains(e.getType());
    }

    protected Predicate<LivingEntity> getPreySelectionLiving(Entity entity) {
        Set<EntityType<?>> prey = Set.of(ModEntityTypes.TYRANNOSAURUS.get(), ModEntityTypes.GIGA.get(),
                ModEntityTypes.ALLOSAURUS.get(), ModEntityTypes.SPINO.get(), ModEntityTypes.OXALAIA.get(),
                ModEntityTypes.INDOMINUS.get(), ModEntityTypes.MALUSAURUS.get(), ModEntityTypes.INDORAPTOR.get(),
                ModEntityTypes.SCORPIUS.get());

        return e -> e.getType() != entity.getType() && prey.contains(e.getType());
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new WyvernStrollGoal(this, 1));
        this.goalSelector.addGoal(4, new WyvernLookoutGoal(this, 1000));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(4, new SleepingRandomLookAroundGoal<>(this));
        this.goalSelector.addGoal(4, new CathemeralSleepGoal<>(this));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(5, new BreedGoal(this, 1));
        this.goalSelector.addGoal(2, new WyvernFollowGoal(this, 1.0, 40, (float) Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).getBaseValue()));
        this.goalSelector.addGoal(3, new WyvernMeleeAttackGoal(this, 1, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Animal.class, true, getPreySelectionLiving(this)));
    }
    public void aiStep() {
        super.aiStep();
        if (this.isAsleep()) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0D);
            this.getAttribute(Attributes.FLYING_SPEED).setBaseValue(0.0D);
        } else {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2D);
            this.getAttribute(Attributes.FLYING_SPEED).setBaseValue(1D);
        }
    }

    @Override
    protected int calculateFallDamage(float pFallDistance, float pDamageMultiplier) {
        return 0;
    }

    @Override
    public void tick() {
        super.tick();
        if (getFireAnimation() == 3){
            setFireAnimation(0);
        }
        if (getFireCharge() < 0){
            setFireAnimation(0);
        }
        if (getFireAnimation() < 1 && getFireCharge() < 200 && this.tickCount % 2 == 0){
            this.setFireCharge(getFireCharge()+1);
        }
        if (!level.isClientSide && !level.getEntities(this, AABB.ofSize(this.position(), 5, 5, 5), getPreySelection(this)).isEmpty()){
            Optional<Entity> entity = level.getEntities(this, AABB.ofSize(this.position(), 5, 5, 5), getPreySelection(this)).stream().findFirst();
            this.setTarget((LivingEntity) entity.get());
        }
        if (this.onGround){
            this.setNoGravity(false);
        }
        if (this.goalSelector.getRunningGoals().noneMatch(e -> e.getGoal() instanceof WyvernLookoutGoal) && this.tickCount % 20 == 0) {
            increaseRisk(1);
        }
    }

    static void playerStoleEgg(Player player, Level level){
        var entities = level.getEntitiesOfClass(WyvernEntity.class, AABB.ofSize(player.position(), 100, 100, 100));
        entities.forEach(entity -> entity.setTarget(player));
    }

    @Override
    public double getTick(Object object) {
        return this.tickCount;
    }

    @Override
    public Player getPassenger() {
        return this.getControllingPassenger() instanceof Player ? ((Player) this.getControllingPassenger()) : null;
    }
}
