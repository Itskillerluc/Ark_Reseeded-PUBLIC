package com.huskytacodile.alternacraft.entities.wyverns;

import com.huskytacodile.alternacraft.entities.AnimatedTextureEntity;
import com.huskytacodile.alternacraft.entities.ModEntityTypes;
import com.huskytacodile.alternacraft.entities.Sleeping;
import com.huskytacodile.alternacraft.entities.ai.*;
import com.huskytacodile.alternacraft.entities.attackgoal.WyvernMeleeAttackGoal;
import com.huskytacodile.alternacraft.entities.variant.IVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

public abstract class WyvernEntity extends Animal implements FlyingAnimal, IAnimatable, OwnableEntity, PlayerRideableFlying, Sleeping, AnimatedTextureEntity {
    protected static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> BREATHING_FIRE = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> ASLEEP = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Optional<UUID>> OWNER = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    protected static final EntityDataAccessor<Integer> RISK = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> FIRE_CHARGE = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> FIRE_ANIMATION = SynchedEntityData.defineId(WyvernEntity.class, EntityDataSerializers.INT);

    protected AnimationFactory factory = GeckoLibUtil.createFactory(this);

    @Override
    public boolean isAggressive() {
        return super.isAggressive();
    }

    public WyvernEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    @SuppressWarnings("deprecation")
    public boolean isFood(ItemStack stack) {
        Item item = stack.getItem();
        return item.isEdible() && item.getFoodProperties().isMeat();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
        this.entityData.define(ASLEEP, false);
        this.entityData.define(ATTACKING, false);
        this.entityData.define(BREATHING_FIRE, 0);
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

    public void setBreathingFire(int breathing){
        this.entityData.set(BREATHING_FIRE, breathing);
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

    public int isBreathingFire(){
        return this.entityData.get(BREATHING_FIRE);
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
    public AnimationFactory getFactory() {
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
        if (getOwnerUUID() == null){
            return null;
        }
        return ((ServerLevel) this.level).getEntity(getOwnerUUID());
    }

    @Override
    public void onPlayerJump(int p_21696_) {
        this.getMoveControl().setWantedPosition(position().x(), position().y()+0.1f, position().z(), 1);
    }

    @Override
    public boolean canJump() {
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
        this.getMoveControl().setWantedPosition(position().x(), position().y()-0.1f, position().z(), 1);
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

    protected  <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event){
        if (isAttacking() && !isFlying()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation." + getAnimationName() + ".attack_01", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        if (0 < isBreathingFire() && isBreathingFire() < 5){
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation." + getAnimationName() + ".fire_start", ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }

        if (isBreathingFire() > 5){
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation." + getAnimationName() + ".fire_loop", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        if (isBreathingFire() == -1){
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation." + getAnimationName() + ".fire_end", ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }
        if (this.isAggressive()  && !isFlying() && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation." + getAnimationName() + ".attack_01", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    protected  <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (isAttacking() && !isFlying()){
            return PlayState.STOP;
        }
        if (!(animationSpeed > -0.10F && animationSpeed < 0.05F) && !this.isFlying()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation." + getAnimationName() + ".walk", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (!(animationSpeed > -0.10F && animationSpeed < 0.05F) && this.isFlying()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation." + getAnimationName() + ".fly"));
            return PlayState.CONTINUE;
        }
        if (this.isSwimming() && !(animationSpeed > -0.10F && animationSpeed < 0.05F) && !this.isAggressive() && !isFlying()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation." + getAnimationName() + ".walk", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (this.isAsleep() || this.getHealth() < 0.01 || this.isDeadOrDying()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation." + getAnimationName() + ".sleep", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (this.isFlying()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation." + getAnimationName() + ".idle_fly", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation." + getAnimationName() + ".idle", ILoopType.EDefaultLoopTypes.LOOP));

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(
                this, "controller", 0, this::predicate));
        data.addAnimationController(new AnimationController<>(
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
        this.goalSelector.addGoal(2, new WyvernLookoutGoal(this, 1000));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(4, new SleepingRandomLookAroundGoal<>(this));
        this.goalSelector.addGoal(4, new CathemeralSleepGoal<>(this));
        this.goalSelector.addGoal(2, new FloatGoal(this));
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
        if (!level.isClientSide && !level.getEntities(this, AABB.ofSize(this.position(), 5, 5, 5), getPreySelection(this)).isEmpty()){
            Optional<Entity> entity = level.getEntities(this, AABB.ofSize(this.position(), 5, 5, 5), getPreySelection(this)).stream().findFirst();
            this.setTarget((LivingEntity) entity.get());
        }
        if (this.onGround){
            this.setNoGravity(false);
        }
        if (this.goalSelector.getRunningGoals().noneMatch(e -> e.getGoal() instanceof WyvernLookoutGoal) && this.tickCount % 20 == 0) {
            increaseRisk(20);
        }
    }

    static void playerStoleEgg(Player player, Level level){
        var entities = level.getEntitiesOfClass(WyvernEntity.class, AABB.ofSize(player.position(), 100, 100, 100));
        entities.forEach(entity -> entity.setTarget(player));
    }
}
