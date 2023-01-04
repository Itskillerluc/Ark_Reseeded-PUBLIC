package com.huskytacodile.alternacraft.entities.dinos;

import com.huskytacodile.alternacraft.data.DataSerializerRegistry;
import com.huskytacodile.alternacraft.entities.Sleeping;
import com.huskytacodile.alternacraft.entities.variant.IVariant;
import com.huskytacodile.alternacraft.misc.KeyBinds;
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
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
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

import java.util.*;

public abstract class AlternaDinoEntity extends TamableAnimal implements PlayerRideableJumping, GeoAnimatable, Sleeping {
    public enum DinoLevelCategory{
        HEALTH(Attributes.MAX_HEALTH, .054f),
        ATTACK(Attributes.ATTACK_DAMAGE, .017f),
        SPEED(Attributes.MOVEMENT_SPEED, .025f);

        public final Attribute attribute;
        public final float multiplier;
        DinoLevelCategory(Attribute attribute, float multiplier){
            this.attribute = attribute;
            this.multiplier = multiplier;
        }
    }
	private static final EntityDataAccessor<Boolean> ASLEEP = SynchedEntityData.defineId(AlternaDinoEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> KNOCKOUT = SynchedEntityData.defineId(AlternaDinoEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> NATURAL_SITTING = SynchedEntityData.defineId(AlternaDinoEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> TAMING = SynchedEntityData.defineId(AlternaDinoEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DINO_LEVEL = SynchedEntityData.defineId(AlternaDinoEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(AlternaDinoEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(AlternaDinoEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Optional<UUID>> OWNER = SynchedEntityData.defineId(AlternaDinoEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    protected static final EntityDataAccessor<EnumMap<DinoLevelCategory, Double>> ATTRIBUTE_LEVELS = SynchedEntityData.defineId(AlternaDinoEntity.class, DataSerializerRegistry.DINO_LEVEL_CAT.get());
    protected static final EntityDataAccessor<EnumMap<DinoLevelCategory, Double>> ATTRIBUTE_CACHE = SynchedEntityData.defineId(AlternaDinoEntity.class, DataSerializerRegistry.DINO_LEVEL_CAT.get());

    final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public AlternaDinoEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor world, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType mobSpawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag) {
        var random = this.random.nextIntBetweenInclusive(1,150);
        setDinoLevel(random);
        processLevel(random, true, false);
        this.setHealth(getMaxHealth());
        return super.finalizeSpawn(world, difficulty, mobSpawnType, groupData, tag);
    }

    void processLevel(int levels, boolean init, boolean replace){
        if (init) {
            getAttributeCache().put(DinoLevelCategory.HEALTH, attributeSupplier().getValue(Attributes.MAX_HEALTH));
            getAttributeCache().put(DinoLevelCategory.ATTACK, attributeSupplier().getValue(Attributes.ATTACK_DAMAGE));
            getAttributeCache().put(DinoLevelCategory.SPEED, attributeSupplier().getValue(Attributes.MOVEMENT_SPEED));
        }
        List<Integer> levelList = new ArrayList<>();
        for (int i = 0; i < levels; i++) {
            levelList.add(random.nextIntBetweenInclusive(0, 2));
        }
        getAttributeLevels().put(DinoLevelCategory.HEALTH, (int) levelList.stream().filter(i -> i == 0).count() + (replace || init ? 0 : getAttributeLevels().get(DinoLevelCategory.HEALTH)));
        getAttributeLevels().put(DinoLevelCategory.ATTACK, (int) levelList.stream().filter(i -> i == 1).count() + (replace || init ? 0 : getAttributeLevels().get(DinoLevelCategory.ATTACK)));
        getAttributeLevels().put(DinoLevelCategory.SPEED, (int) levelList.stream().filter(i -> i == 2).count() + (replace || init ? 0 : getAttributeLevels().get(DinoLevelCategory.SPEED)));

        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(getAttributeLeveledAttribute(DinoLevelCategory.HEALTH));
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(getAttributeLeveledAttribute(DinoLevelCategory.ATTACK));
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(getAttributeLeveledAttribute(DinoLevelCategory.SPEED));
    }

    public abstract AttributeSupplier attributeSupplier();

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

    public void aiStep() {
        super.aiStep();
        if (this.level.isClientSide()){
            return;
        }
        if (this.isAsleep() || this.isNaturallySitting()) {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0D);
        } else {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(getAttributeLeveledAttribute(DinoLevelCategory.SPEED));
        }
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(getAttributeLeveledAttribute(DinoLevelCategory.HEALTH));
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(getAttributeLeveledAttribute(DinoLevelCategory.ATTACK));
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(getAttributeLeveledAttribute(DinoLevelCategory.SPEED));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
        this.entityData.define(SITTING, false);
        this.entityData.define(ASLEEP, false);
        this.entityData.define(NATURAL_SITTING, false);
        this.entityData.define(OWNER, Optional.empty());
        this.entityData.define(DINO_LEVEL, random.nextIntBetweenInclusive(1,150));
        this.entityData.define(KNOCKOUT, 0);
        this.entityData.define(ATTRIBUTE_LEVELS, new EnumMap<>(DinoLevelCategory.class));
        this.entityData.define(ATTRIBUTE_CACHE, new EnumMap<>(DinoLevelCategory.class));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("DinoLevel", this.getDinoLevel());
        tag.putInt("Variant", this.getTypeVariant());
        tag.putBoolean("IsAsleep", this.isAsleep());
        tag.putBoolean("NaturallySitting", this.isNaturallySitting());
        tag.putInt("KnockoutTime", this.getKnockout());
        var attributeLevel = new CompoundTag();
        for (Map.Entry<DinoLevelCategory, Double> dinoLevelCategoryIntegerEntry : getAttributeLevels().entrySet()) {
            attributeLevel.putDouble(String.valueOf(dinoLevelCategoryIntegerEntry.getKey().ordinal()), dinoLevelCategoryIntegerEntry.getValue());
        }
        tag.put("attributeLevels", attributeLevel);

        var cache = new CompoundTag();
        for (Map.Entry<DinoLevelCategory, Double> cacheEntry : getAttributeLevels().entrySet()) {
            cache.putDouble(String.valueOf(cacheEntry.getKey().ordinal()), cacheEntry.getValue());
        }
        tag.put("cache", cache);

        if (this.getOwnerUUID() != null) {
            tag.putUUID("Owner", this.getOwnerUUID());
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(DINO_LEVEL, tag.getInt("DinoLevel"));
        this.entityData.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
        this.entityData.set(ASLEEP, tag.getBoolean("IsAsleep"));
        this.entityData.set(KNOCKOUT, tag.getInt("KnockoutTime"));
        this.entityData.set(NATURAL_SITTING, tag.getBoolean("NaturallySitting"));
        if (tag.contains("Owner")) setOwner(tag.getUUID("Owner"));
        else if (tag.contains("ForgeData")) {
            setOwner(tag.getCompound("ForgeData").getUUID("Owner"));
        }
        var attributeLevel = tag.getCompound("attributeLevels");
        getAttributeLevels().put(DinoLevelCategory.HEALTH, attributeLevel.getDouble("0"));
        getAttributeLevels().put(DinoLevelCategory.ATTACK, attributeLevel.getDouble("1"));
        getAttributeLevels().put(DinoLevelCategory.SPEED, attributeLevel.getDouble("2"));

        var cache = tag.getCompound("cache");
        getAttributeCache().put(DinoLevelCategory.HEALTH, cache.getDouble("0"));
        getAttributeCache().put(DinoLevelCategory.ATTACK, cache.getDouble("1"));
        getAttributeCache().put(DinoLevelCategory.SPEED, cache.getDouble("2"));
    }


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

    @SuppressWarnings("deprecation")
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
                } else {
                    this.navigation.stop();
                    this.setTarget(null);
                    this.setOrderedToSit(true);
                }
            } else {
                player.startRiding(this);
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onPlayerJump(int pJumpPower){
        net.minecraftforge.common.ForgeHooks.onLivingJump(this);
        this.addDeltaMovement(new Vec3(0, 0.5 + pJumpPower / 300f, 0));
    }

    @Override
    public boolean canJump(@NotNull Player pPlayer){
        return this.isOnGround() && this.getOwner() != null;
    }

    @Override
    public void handleStartJump(int pJumpPower){}
    @Override
    public void handleStopJump() {}


    public @NotNull Vec3 getDismountLocationForPassenger(@NotNull LivingEntity p_230268_1_) {
        Direction direction = this.getMotionDirection();
        if (direction.getAxis() != Direction.Axis.Y) {
            int[][] aint = DismountHelper.offsetsForDirection(direction);
            BlockPos blockpos = this.blockPosition();
            BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

            for (Pose pose : p_230268_1_.getDismountPoses()) {
                AABB axisalignedbb = p_230268_1_.getLocalBoundsForPose(pose);

                for (int[] aint1 : aint) {
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

        }
        return super.getDismountLocationForPassenger(p_230268_1_);
    }

    public void travel(@NotNull Vec3 pTravelVector) {
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
                    super.travel(new Vec3(f, pTravelVector.y, f1));
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

    public void setDinoLevel(int level){
        this.entityData.set(DINO_LEVEL, level);
        this.processLevel(level, false, true);
    }
    public int getDinoLevel(){
        return this.entityData.get(DINO_LEVEL);
    }
    public void increaseDinoLevel(int level){
        this.entityData.set(DINO_LEVEL, this.entityData.get(DINO_LEVEL) + level);
        this.processLevel(level, false, false);
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
    public EnumMap<DinoLevelCategory, Double> getAttributeLevels(){
        return this.entityData.get(ATTRIBUTE_LEVELS);
    }

    public EnumMap<DinoLevelCategory, Double> getAttributeCache(){
        return this.entityData.get(ATTRIBUTE_CACHE);
    }
    protected void setVariant(IVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }
    public IVariant getVariant() {
        return null;
    }
    protected int getTypeVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }
    public void setOwner(@Nullable UUID uuid){
        this.entityData.set(OWNER, Optional.ofNullable(uuid));
    }
    public void setOwner(Player owner){
        this.setOwner(owner.getUUID());
    }
    public Optional<UUID> getOwnerData(){
        return this.entityData.get(OWNER);
    }
    @Override
    public LivingEntity getOwner() {
        try {
            UUID uuid = this.getOwnerUUID();
            return uuid == null ? null : this.level.getPlayerByUUID(uuid);
        } catch (IllegalArgumentException illegalargumentexception) {
            return null;
        }
    }
    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return getOwnerData().orElse(null);
    }

    @Override
    public void setAsleep(boolean isAsleep) {
        this.entityData.set(ASLEEP, isAsleep);
    }
    @Override
    public boolean isAsleep() {
        return this.entityData.get(ASLEEP);
    }
    public void setNaturallySitting(boolean isSitting) {
        this.entityData.set(NATURAL_SITTING, isSitting);
    }
    public boolean isNaturallySitting() {
        return this.entityData.get(NATURAL_SITTING);
    }

    protected @NotNull SoundEvent getSwimSound() {
        return SoundEvents.FISH_SWIM;
    }

    @javax.annotation.Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    //TODO: make abstract
    public int getTameTime() {
        return 2000;
    }

    public abstract String getAnimationName();

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    public boolean doHurtTarget(@NotNull Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        if (flag) {
            this.doEnchantDamageEffects(this, entity);
        }

        return flag;
    }

    public boolean canBeLeashed(@NotNull Player player){
        return false;
    }

    @Override
    public double getTick(Object object) {
        return this.tickCount;
    }

    @Override
    public double getPassengersRidingOffset() {
        return 3d;
    }

    @Override
    public float getStepHeight() {
        return 1f;
    }

    @Override
    protected int calculateFallDamage(float pFallDistance, float pDamageMultiplier) {
        return fallDistance > 4 ? super.calculateFallDamage(pFallDistance, pDamageMultiplier) : 0;
    }

    public double getAttributeLeveledAttribute(DinoLevelCategory category){
        if (getAttributeLevels().size() == 0 || getAttributeCache().size() == 0){
            LogManager.getLogger().warn("attributeLevels and/or AttributeCache is not loaded. This can be caused by loading a world with a previous version of the reseeded protocol." +
                    " If this problem persists and you keep seeing this warning, please report it to the mod author. \n entity:" + this.getName());

            processLevel(getDinoLevel(), true, true);
        }
        return getAttributeLevels().get(category) * category.multiplier * getAttributeCache().get(category);
    }
}
